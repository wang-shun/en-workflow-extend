package com.chinacreator.c2.omp.service.manage.workflowcommon.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.runtime.InterpretableExecution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;

import com.chinacreator.c2.flow.detail.WfConstants;
import com.chinacreator.c2.flow.detail.WfResult;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.workflowcommon.bean.ActivityInstancePersistentObject;

/**
 * @author qiao.li
 * @version 创建时间：2016年5月13日 上午10:05:48 类说明
 */
public class RecoverTaskCmd implements Command<WfResult> {

	private String processInstanceId;
	private String recoverReason;
	private String recoverToActivityId;
	private Map variables;
	private String userId;

	public RecoverTaskCmd(String processInstanceId, String recoverReason,
			String recoverToActivityId, Map variables, String userId) {
		this.variables = variables;
		this.recoverToActivityId = recoverToActivityId;
		this.recoverReason = recoverReason;
		this.processInstanceId = processInstanceId;
		this.userId = userId;
	}

	@Override
	public WfResult execute(CommandContext commandContext) {
		WfResult wfresult = new WfResult();
		ExecutionEntity executionEntity = commandContext
				.getExecutionEntityManager().findExecutionById(
						processInstanceId);

		this.destroyScope(executionEntity, recoverReason);

		ProcessDefinitionImpl processDefinition = executionEntity
				.getProcessDefinition();
		ActivityImpl activity = processDefinition
				.findActivity(recoverToActivityId);

		if (variables != null) {
			executionEntity.setVariables(variables);
		}
		executionEntity.setActive(true);
		executionEntity.executeActivity(activity);
		// commandContext.getTaskEntityManager().findTasksByExecutionId(executionEntity.getId());
		List<TaskEntity> listTask = executionEntity.getTasks();
		if (listTask != null && listTask.size() == 1) {
			TaskEntity taskEntity = listTask.get(0);
			Set<IdentityLink> links = taskEntity.getCandidates();
			for (IdentityLink l : links) {
				taskEntity.deleteIdentityLink(l.getUserId(), l.getGroupId(),
						l.getType());
			}
			taskEntity.addCandidateUser(userId);
			wfresult.setNextTaskId(taskEntity.getId());

		}
		wfresult.setProcessInstanceId(processInstanceId);
		wfresult.setResult(WfConstants.WF_CONTROL_EXE_SUCCESS);

		return wfresult;
	}

	public void destroyScope(ExecutionEntity executionEntity, String reason) {
		// remove all child executions and sub process instances:
		List<InterpretableExecution> executions = new ArrayList<InterpretableExecution>(
				executionEntity.getExecutions());
		for (InterpretableExecution childExecution : executions) {
			if (childExecution.getSubProcessInstance() != null) {
				childExecution.getSubProcessInstance().deleteCascade(reason);
			}
			childExecution.deleteCascade(reason);
		}

		removeTasks(executionEntity, reason);
		removeJobs(executionEntity);
		// Daniel thought this would be needed, but it seems not:
		// removeEventSubscriptions();
	}

	private void removeTasks(ExecutionEntity executionEntity, String reason) {
		if (reason == null) {
			reason = TaskEntity.DELETE_REASON_DELETED;
		}
		for (TaskEntity task : executionEntity.getTasks()) {
			if (executionEntity.getReplacedBy() != null) {
				if (task.getExecution() == null
						|| task.getExecution() != executionEntity
								.getReplacedBy()) {
					// All tasks should have been moved when "replacedBy" has
					// been set. Just in case tasks where added,
					// wo do an additional check here and move it
					task.setExecution(executionEntity.getReplacedBy());
					executionEntity.getReplacedBy().addTask(task);
				}
			} else {
				Context.getCommandContext().getTaskEntityManager();
				this.deleteTask(Context.getCommandContext()
						.getTaskEntityManager(), task, reason, true);
			}
			executionEntity.removeTask(task);
		}

	}

	private void removeJobs(ExecutionEntity executionEntity) {
		for (Job job : executionEntity.getJobs()) {
			if (executionEntity.getReplacedBy() != null) {
				((JobEntity) job)
						.setExecution((ExecutionEntity) executionEntity
								.getReplacedBy());
			} else {
				((JobEntity) job).delete();
			}
		}
	}

	public void deleteTask(TaskEntityManager taskEntityManager,
			TaskEntity task, String deleteReason, boolean cascade) {
		if (!task.isDeleted()) {
			task.fireEvent(TaskListener.EVENTNAME_DELETE);
			task.setDeleted(true);

			CommandContext commandContext = Context.getCommandContext();
			String taskId = task.getId();

			List<Task> subTasks = taskEntityManager
					.findTasksByParentTaskId(taskId);
			for (Task subTask : subTasks) {
				taskEntityManager.deleteTask((TaskEntity) subTask,
						deleteReason, cascade);
			}

			commandContext.getIdentityLinkEntityManager()
					.deleteIdentityLinksByTaskId(taskId);

			commandContext.getVariableInstanceEntityManager()
					.deleteVariableInstanceByTask(task);

			if (cascade) {
				commandContext.getHistoricTaskInstanceEntityManager()
						.deleteHistoricTaskInstanceById(taskId);

				HistoryService historyService = ApplicationContextManager
						.getContext().getBean(HistoryService.class);
				List<HistoricActivityInstance> list = historyService
						.createHistoricActivityInstanceQuery()
						.processInstanceId(processInstanceId).unfinished()
						.list();
				for (HistoricActivityInstance hai : list) {
					if (hai.getTaskId().equals(taskId)) {
						PersistentObject persistentObject = new ActivityInstancePersistentObject();
						persistentObject.setId(hai.getId());
/*						commandContext
								.getHistoricActivityInstanceEntityManager()
								.delete(persistentObject);*/
						DbSqlSession session = Context.getCommandContext().getSession(
								DbSqlSession.class);
						session.selectList("org.activiti.engine.impl.persistence.entity.CustomHistoricActivityInstanceEntity.deleteHistoricActivityInstancesByTaskId", taskId);
					}

				}
				int i = 0;
/*				 commandContext.getHistoricActivityInstanceEntityManager().f.delete(persistentObject);
				 .deleteHistoricTaskInstanceById(taskId);*/
			} else {
				commandContext.getHistoryManager().recordTaskEnd(taskId,
						deleteReason);
			}
			DbSqlSession session = Context.getCommandContext().getSession(
					DbSqlSession.class);
			session.delete(task);

			if (commandContext.getEventDispatcher().isEnabled()) {
				commandContext.getEventDispatcher().dispatchEvent(
						ActivitiEventBuilder.createEntityEvent(
								ActivitiEventType.ENTITY_DELETED, task));
			}
		}
	}

}
