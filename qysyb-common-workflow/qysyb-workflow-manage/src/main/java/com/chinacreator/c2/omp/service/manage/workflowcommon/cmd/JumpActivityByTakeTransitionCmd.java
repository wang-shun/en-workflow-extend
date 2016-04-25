package com.chinacreator.c2.omp.service.manage.workflowcommon.cmd;


import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLinkType;
/**
 * 
 * @author qiao.li
 * @version 1.0
 * @date 2016年3月21日
 */
public class JumpActivityByTakeTransitionCmd implements Command<String> {
	private String processDefinitionId;
	private String bussinessKey;
	private String jumpToActivityId;
	private String processInstanceId;
	private String jumpReason;
	private String curTaskId;
	private String transition;
	private Map<String, Object> variables;

	public JumpActivityByTakeTransitionCmd(String processDefinitionId,String bussinessKey,
			String processInstanceId, String jumpToActivityId,
			Map<String, Object> variables) {
		this(processDefinitionId, bussinessKey, processInstanceId, jumpToActivityId, "jump",
				variables);
	}

	public JumpActivityByTakeTransitionCmd(String processDefinitionId,String bussinessKey,
			String processInstanceId, String jumpToActivityId,
			String jumpReason, Map<String, Object> variables) {
		this.processDefinitionId = processDefinitionId;
		this.bussinessKey = bussinessKey;
		this.jumpToActivityId = jumpToActivityId;
		this.processInstanceId = processInstanceId;
		this.jumpReason = jumpReason;
		this.variables = variables;
	}
	
	public JumpActivityByTakeTransitionCmd(String processDefinitionId,String bussinessKey,
			String processInstanceId,String curTaskId, String jumpToActivityId,String transition,
			String jumpReason, Map<String, Object> variables) {
		this.processDefinitionId = processDefinitionId;
		this.bussinessKey = bussinessKey;
		this.jumpToActivityId = jumpToActivityId;
		this.transition = transition;
		this.curTaskId = curTaskId;
		this.processInstanceId = processInstanceId;
		this.jumpReason = jumpReason;
		this.variables = variables;
	}

	public String execute(CommandContext commandContext) {
		ExecutionEntity executionEntity = null;
		
		if (processDefinitionId != null) {

			// 获得当前流程的定义模型
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)commandContext.getProcessEngineConfiguration().getRepositoryService().getProcessDefinition(processDefinitionId);
					
			ActivityImpl activityImpl = processDefinition
					.findActivity(jumpToActivityId);
			executionEntity = processDefinition
					.createProcessInstance(bussinessKey, activityImpl);
			if (variables != null)
				executionEntity.setVariables(variables);
			executionEntity.start();
		} else if (processInstanceId != null) {
			doCompleteTask();
			executionEntity = commandContext
					.getExecutionEntityManager().findExecutionById(
							processInstanceId);
			//如此 则推测有子流程 TODO 优化。 暂不考虑子流程。TODO需要重写平台自由流 流转后把active设置为true
//			if(!executionEntity.isActive()){
//				executionEntity = commandContext.getExecutionEntityManager().findChildExecutionsByParentExecutionId(processInstanceId).get(0);
//			}
			List<PvmTransition> list = executionEntity.getActivity().getOutgoingTransitions();
			PvmTransition takeTransition = null;
			for(PvmTransition p:list){
				if(p.getId().equals(transition)){
					takeTransition = p;
				}
			}
//			executionEntity.destroyScope(jumpReason);
//			不知道这里为什么要initialize。如果initialize 则结束流程时会报错。
//			executionEntity.initialize();
			if (variables != null) {
				executionEntity.setVariables(variables);
			}
			
			executionEntity.take(takeTransition);
//			executionEntity.performOperation(AtomicOperation.TRANSITION_DESTROY_SCOPE);
//			ProcessDefinitionImpl processDefinition = executionEntity
//					.getProcessDefinition();
//			ActivityImpl activity = processDefinition
//					.findActivity(jumpToActivityId);


//			executionEntity.setActive(true);
//			executionEntity.setActivity(activity);
//			executionEntity.performOperation(AtomicOperation.TRANSITION_CREATE_SCOPE);
//			executionEntity.executeActivity(activity);
		}


		return executionEntity.getProcessInstanceId();
	}

	private boolean doCompleteTask(){
	    TaskEntity task = Context
	    	      .getCommandContext()
	    	      .getTaskEntityManager()
	    	      .findTaskById(curTaskId);
	  	if (task.getDelegationState() != null && task.getDelegationState().equals(DelegationState.PENDING)) {
	  		throw new ActivitiException("A delegated task cannot be completed, but should be resolved instead.");
	  	}
	  	
	  	task.fireEvent(TaskListener.EVENTNAME_COMPLETE);

	    if (Authentication.getAuthenticatedUserId() != null && task.getProcessInstanceId() != null) {
	    	task.getProcessInstance().involveUser(Authentication.getAuthenticatedUserId(), IdentityLinkType.PARTICIPANT);
	    }
	    
	    if(Context.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
	    	Context.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(
	    	ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_COMPLETED, this));
	    }
	 
	    Context
	      .getCommandContext()
	      .getTaskEntityManager()
	      .deleteTask(task, TaskEntity.DELETE_REASON_COMPLETED, false);
	    
	    if (task.getExecutionId()!=null) {
	      ExecutionEntity execution = task.getExecution();
	      execution.removeTask(task);
	    }
	    return true;
	}
}
