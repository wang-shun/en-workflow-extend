package com.chinacreator.c2.qyb.workflow.inform.inf;

import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;

import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;


public interface IInformDo {
	public abstract boolean before();
	public abstract boolean beforeDo();
	public abstract void TaskInformDo(ServiceProduct serviceProduct,ActivitiEntityEvent taskEvent,Map entity);
	public abstract void CommentInformDo(ServiceProduct serviceProduct,ActivitiEntityEvent commentEvent,Map entity);
	public abstract boolean after();
	public abstract boolean afterDo();
}
