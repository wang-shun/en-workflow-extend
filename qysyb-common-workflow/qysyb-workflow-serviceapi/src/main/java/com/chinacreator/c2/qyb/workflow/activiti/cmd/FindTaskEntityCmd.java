package com.chinacreator.c2.qyb.workflow.activiti.cmd;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年3月21日 下午2:16:03 
 * 类说明 
 */
public class FindTaskEntityCmd implements Command<TaskEntity>{

	private String taskId = null;
	public FindTaskEntityCmd(String taskId){
		this.taskId = taskId;
	}
	@Override
	public TaskEntity execute(CommandContext commandContext) {
	    TaskEntity task = Context
	    	      .getCommandContext()
	    	      .getTaskEntityManager()
	    	      .findTaskById(taskId);
	    task.getExecution().getActivity();
	    task.getVariables();
	    return task;
	}

}
