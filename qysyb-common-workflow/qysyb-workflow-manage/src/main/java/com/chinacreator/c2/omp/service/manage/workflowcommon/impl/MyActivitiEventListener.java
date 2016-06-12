package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;

import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.inform.impl.InformService;

public class MyActivitiEventListener implements ActivitiEventListener {
//	InformService informService = new InformService();
/*	ActivityConfigService activityConfigService = new ActivityConfigService();
	UserConcernedConfigService userConcernedConfigService = new UserConcernedConfigService();*/
	InformService informService;
/*	private ManagementService managementService;
	private TaskService taskService;
	private RepositoryService repositoryService;
	private RuntimeService runtimService;
	private IdentityService identityService;*/
	@Override
	public void onEvent(ActivitiEvent event) {
		// TODO Auto-generated method stub
		if (isValidEvent(event)) {
			informService = ApplicationContextManager.getContext().getBean(InformService.class);
/*			managementService = event.getEngineServices().getManagementService();
			taskService = event.getEngineServices().getTaskService();
			repositoryService = event.getEngineServices().getRepositoryService();
			runtimService = event.getEngineServices().getRuntimeService();
			identityService = event.getEngineServices().getIdentityService();*/
			ActivitiEntityEvent activityEntityEvent = (org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl) event;
			if (event.getType() == ActivitiEventType.ENTITY_CREATED)
				onCreate(activityEntityEvent);
			else if (event.getType() == ActivitiEventType.ENTITY_INITIALIZED)
				onInitialized(activityEntityEvent);
			else if (event.getType() == ActivitiEventType.ENTITY_DELETED)
				onDelete(activityEntityEvent);
			else if (event.getType() == ActivitiEventType.ENTITY_UPDATED)
				onUpdate(activityEntityEvent);
			else if (event.getType() == ActivitiEventType.ENTITY_SUSPENDED)
				onSuspend(activityEntityEvent);
			else if (event.getType() == ActivitiEventType.ENTITY_ACTIVATED)
				onActivated(activityEntityEvent);
			else
				onEntityEvent(activityEntityEvent);
		}	
	}

	private void onEntityEvent(ActivitiEntityEvent event) {
		// TODO Auto-generated method stub
		
	}

	private void onActivated(ActivitiEntityEvent event) {
		// TODO Auto-generated method stub
		
	}

	private void onSuspend(ActivitiEntityEvent event) {
		// TODO Auto-generated method stub
		
	}

	private void onUpdate(ActivitiEntityEvent event) {
		// TODO Auto-generated method stub
		
	}

	private void onDelete(ActivitiEntityEvent event) {
		// TODO Auto-generated method stub
		Object o = event.getEntity();
		if(o instanceof org.activiti.engine.impl.persistence.entity.CommentEntity){
			informService.delCommentEvents(event);
/*			org.activiti.engine.impl.persistence.entity.CommentEntity commentEntity = (CommentEntity)o;
			System.out.printf("Comment add",commentEntity.toString());*/
		}else if(o instanceof org.activiti.engine.impl.persistence.entity.TaskEntity){
			informService.delTaskEvents(event);
/*			org.activiti.engine.impl.persistence.entity.TaskEntity taskEntity = (TaskEntity)o;
			List<Task> task = taskService.createTaskQuery().executionId(event.getExecutionId()).list();
			ProcessInstance processinstance = runtimService.createProcessInstanceQuery().processInstanceId(event.getProcessInstanceId()).singleResult();
			Execution e = runtimService.createExecutionQuery().executionId(event.getExecutionId()).singleResult();
			List<IdentityLink> listIdentity = taskService.getIdentityLinksForTask(taskEntity.getId());
			String s = e.getActivityId();
			String s1 = processinstance.getActivityId();
			System.out.printf("task add",taskEntity.toString());*/
		}else if(o instanceof IdentityLinkEntity){
			
		}		
	}

	private void onInitialized(ActivitiEntityEvent event) {
		// TODO Auto-generated method stub
		Object o = event.getEntity();
		if(o instanceof org.activiti.engine.impl.persistence.entity.CommentEntity){
			informService.addCommentEvents(event);
/*			org.activiti.engine.impl.persistence.entity.CommentEntity commentEntity = (CommentEntity)o;
			System.out.printf("Comment add",commentEntity.toString());*/
		}else if(o instanceof org.activiti.engine.impl.persistence.entity.TaskEntity){
			informService.addTaskEvents(event);
/*			org.activiti.engine.impl.persistence.entity.TaskEntity taskEntity = (TaskEntity)o;
			List<Task> task = taskService.createTaskQuery().executionId(event.getExecutionId()).list();
			ProcessInstance processinstance = runtimService.createProcessInstanceQuery().processInstanceId(event.getProcessInstanceId()).singleResult();
			Execution e = runtimService.createExecutionQuery().executionId(event.getExecutionId()).singleResult();
			List<IdentityLink> listIdentity = taskService.getIdentityLinksForTask(taskEntity.getId());
			String s = e.getActivityId();
			String s1 = processinstance.getActivityId();
			System.out.printf("task add",taskEntity.toString());*/
		}else if(o instanceof IdentityLinkEntity){
			
		}
	}

	private void onCreate(ActivitiEntityEvent event) {
		// TODO Auto-generated method stub
/*		Object o = event.getEntity();
		if(o instanceof org.activiti.engine.impl.persistence.entity.CommentEntity){
			org.activiti.engine.impl.persistence.entity.CommentEntity commentEntity = (CommentEntity)o;
			System.out.printf("Comment add",commentEntity.toString());
		}else if(o instanceof org.activiti.engine.impl.persistence.entity.TaskEntity){
			org.activiti.engine.impl.persistence.entity.TaskEntity taskEntity = (TaskEntity)o;
			
			Task task = taskService.createTaskQuery().taskId(taskEntity.getId()).singleResult();
			System.out.printf("task add",taskEntity.toString());
		}*/
	}

	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected boolean isValidEvent(ActivitiEvent event) {
		boolean valid = false;
		if (event instanceof org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl)
			valid = true;
		return valid;
	}
}
