package com.chinacreator.c2.omp.service.manage.workflowcommon.cmd;

import java.util.Set;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年4月26日 下午4:10:30 
 * 类说明 
 */
public class DelTaskCandidatesCmd implements Command<TaskEntity> {
	private String taskId = null;
	public DelTaskCandidatesCmd(String taskId){
		this.taskId = taskId;
	}
	@Override
	public TaskEntity execute(CommandContext commandContext) {
	    TaskEntity taskEntity = Context
	    	      .getCommandContext()
	    	      .getTaskEntityManager()
	    	      .findTaskById(taskId);
		Set<IdentityLink> links = taskEntity.getCandidates();
		for(IdentityLink l:links){
			taskEntity.deleteIdentityLink(l.getUserId(), l.getGroupId(), l.getType());
		}	
	    return taskEntity;
	}
}
