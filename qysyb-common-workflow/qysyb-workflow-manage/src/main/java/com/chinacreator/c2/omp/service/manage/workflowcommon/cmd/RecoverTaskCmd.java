package com.chinacreator.c2.omp.service.manage.workflowcommon.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.pvm.PvmException;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.ScopeImpl;
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;
import org.activiti.engine.impl.pvm.runtime.InterpretableExecution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;

import com.chinacreator.c2.flow.detail.WfConstants;
import com.chinacreator.c2.flow.detail.WfResult;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/**追回任务类
 * @author qiao.li
 * @version 创建时间：2016年5月13日 上午10:05:48 类说明
 */
public class RecoverTaskCmd implements Command<WfResult> {

	private String processInstanceId;
	private String recoverReason;
	private String recoverToActivityId;
	private Map variables;
	private String userId;
	private String deleteRootId;

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

	public void destroyScope(ExecutionEntity executionEntity,String reason) {
		// remove all child executions and sub process instances:
		List<InterpretableExecution> executions = new ArrayList<InterpretableExecution>(
				executionEntity.getExecutions());
		for (InterpretableExecution childExecution : executions) {
			if (childExecution.getSubProcessInstance() != null) {
				childExecution.getSubProcessInstance().deleteCascade(reason);
			}
			deleteCascadeExecute(childExecution,childExecution.getId(),reason);
//			childExecution.deleteCascade(reason);
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
/*						PersistentObject persistentObject = new ActivityInstancePersistentObject();
						persistentObject.setId(hai.getId());
						commandContext
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

	private void deleteCascadeExecute(InterpretableExecution execution, String rootId, String reason) {
		if(rootId!=null){
			this.deleteRootId = rootId;
		}		
		InterpretableExecution firstLeaf = findFirstLeaf(execution);
//		execution.d.deleteCascade(deleteReason);
		if (firstLeaf.getSubProcessInstance() != null) {
			firstLeaf.getSubProcessInstance().deleteCascade(
					execution.getDeleteReason());
		}

//		firstLeaf.performOperation(AtomicOperation.DELETE_CASCADE_FIRE_ACTIVITY_END);
		eventAtomicOperationExecute(firstLeaf,AtomicOperation.DELETE_CASCADE_FIRE_ACTIVITY_END.toString());
	}

	private void deleteExecute(InterpretableExecution execution, String reason) {
//		execution.deletereason = 
		InterpretableExecution firstLeaf = findFirstLeaf(execution);

		if (firstLeaf.getSubProcessInstance() != null) {
			firstLeaf.getSubProcessInstance().deleteCascade(
					execution.getDeleteReason());
		}

//		firstLeaf.performOperation(AtomicOperation.DELETE_CASCADE_FIRE_ACTIVITY_END);
		eventAtomicOperationExecute(firstLeaf,AtomicOperation.DELETE_CASCADE_FIRE_ACTIVITY_END.toString());
	}
	@SuppressWarnings("unchecked")
	protected InterpretableExecution findFirstLeaf(
			InterpretableExecution execution) {
		List<InterpretableExecution> executions = (List<InterpretableExecution>) execution
				.getExecutions();
		if (executions.size() > 0) {
			return findFirstLeaf(executions.get(0));
		}
		return execution;
	}
	
	private void eventAtomicOperationExecute(InterpretableExecution execution,String eventName){
	    ScopeImpl scope = getScope(execution);
	    List<ExecutionListener> exectionListeners = scope.getExecutionListeners(eventName);
	    int executionListenerIndex = execution.getExecutionListenerIndex();
	    
	    if (exectionListeners.size()>executionListenerIndex) {
	      execution.setEventName(eventName);
	      execution.setEventSource(scope);
	      ExecutionListener listener = exectionListeners.get(executionListenerIndex);
	      try {
	        listener.notify(execution);
	      } catch (RuntimeException e) {
	        throw e;
	      } catch (Exception e) {
	        throw new PvmException("couldn't execute event listener : "+e.getMessage(), e);
	      }
	      execution.setExecutionListenerIndex(executionListenerIndex+1);
	      eventAtomicOperationExecute(execution,eventName);
//	      execution.performOperation(this);

	    } else {
	      execution.setExecutionListenerIndex(0);
	      execution.setEventName(null);
	      execution.setEventSource(null);
	      
	      AtomicOperationDeleteCascadeFireActivityEndeventNotificationsCompleted(execution);
	    }		
	}

	protected void AtomicOperationDeleteCascadeFireActivityEndeventNotificationsCompleted(InterpretableExecution execution) {
		ActivityImpl activity = (ActivityImpl) execution.getActivity();
		if ((execution.isScope()) && (activity != null)
				&& (!activity.isScope())) {
			execution.setActivity(activity.getParentActivity());
			execution
					.performOperation(AtomicOperation.DELETE_CASCADE_FIRE_ACTIVITY_END);

		} else {
			if (execution.isScope()) {
				execution.destroy();
			}

//			execution.remove();
			removeExecution(execution);
			if (Context.getProcessEngineConfiguration() != null
					&& Context.getProcessEngineConfiguration()
							.getEventDispatcher().isEnabled()) {
				Context.getProcessEngineConfiguration()
						.getEventDispatcher()
						.dispatchEvent(
								ActivitiEventBuilder.createEntityEvent(
										ActivitiEventType.ENTITY_DELETED,
										execution));
			}

			if (!execution.getId().equals(deleteRootId)) {
				InterpretableExecution parent = (InterpretableExecution) execution
						.getParent();
				if (parent != null) {
//					parent.performOperation(AtomicOperation.DELETE_CASCADE);
					deleteCascadeExecute(parent,null,null);
				}
			}
		}
	}

	protected ScopeImpl getScope(InterpretableExecution execution) {
		ActivityImpl activity = (ActivityImpl) execution.getActivity();

		if (activity != null) {
			return activity;
		} else {
			InterpretableExecution parent = (InterpretableExecution) execution
					.getParent();
			if (parent != null) {
				return getScope((InterpretableExecution) execution.getParent());
			}
			return execution.getProcessDefinition();
		}
	}
	
	public void removeExecution(InterpretableExecution iexecution) {
		ExecutionEntity execution = (ExecutionEntity) iexecution;
//		ensureParentInitialized((ExecutionEntity) execution);
		if (execution.getParent() != null) {
//			ensureExecutionsInitialized((ExecutionEntity) execution.getParent());
			execution.getParent().getExecutions().remove(execution);
		}

		// delete all the variable instances
//		execution.ensureVariableInstancesInitialized();
		execution.deleteVariablesInstanceForLeavingScope();

		// delete all the tasks
		removeTasks(execution,null);

		// remove all jobs
		removeJobs(execution);

		// remove all event subscriptions for this scope, if the scope has event
		// subscriptions:
		removeEventSubscriptions(execution);

		// remove event scopes:
		removeEventScopes(execution);

		// remove identity links
		execution.removeIdentityLinks();

		// finally delete this execution
		Context.getCommandContext().getDbSqlSession().delete(execution);
	}
	
	protected void ensureParentInitialized(ExecutionEntity execution) {
		if (execution.getParent() == null && execution.getParent().getId() != null) {
			execution.setParent(Context.getCommandContext().getExecutionEntityManager()
					.findExecutionById(execution.getParent().getId()));
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void ensureExecutionsInitialized(ExecutionEntity execution) {
		if (execution.getExecutions() == null) {
			execution.setExecutions(Context.getCommandContext()
					.getExecutionEntityManager()
					.findChildExecutionsByParentExecutionId(execution.getId()));
		}
	}

	private void removeEventSubscriptions(ExecutionEntity execution) {
		for (EventSubscriptionEntity eventSubscription : execution.getEventSubscriptions()) {
			if (execution.getReplacedBy() != null) {
				eventSubscription.setExecution((ExecutionEntity) execution.getReplacedBy());
			} else {
				eventSubscription.delete();
			}
		}
	}
	
	private void removeEventScopes(ExecutionEntity execution) {
		List<InterpretableExecution> childExecutions = new ArrayList<InterpretableExecution>(
				execution.getExecutions());
		for (InterpretableExecution childExecution : childExecutions) {
			if (childExecution.isEventScope()) {
//				log.debug("removing eventScope {}", childExecution);
				childExecution.destroy();
				childExecution.remove();
			}
		}
	}
}
