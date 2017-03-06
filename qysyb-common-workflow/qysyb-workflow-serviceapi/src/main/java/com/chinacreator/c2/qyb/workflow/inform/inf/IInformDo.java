package com.chinacreator.c2.qyb.workflow.inform.inf;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;

import com.chinacreator.c2.qyb.workflow.config.entity.ActivityConfig;
import com.chinacreator.c2.qyb.workflow.config.entity.UserConcernedConfig;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;

public interface IInformDo {
	/**
	 * 一个通知循环开始前
	 * @return
	 */
	public abstract boolean before();
	/**
	 * 一次通知开始
	 * @return
	 */
	public abstract boolean beforeDo();
	/**
	 * 
	 * @param serviceProduct
	 * @param taskEvent
	 * @param ac 环节配置
	 * @param uccs 用户关注工单设置 一个用户关注就是一个记录 所以是一个list
	 * @param entity
	 */
	public abstract void TaskInformDo(ServiceProduct serviceProduct, ActivitiEntityEvent taskEvent, ActivityConfig ac,
			List<UserConcernedConfig> uccs, Map entity);
	/**
	 * 
	 * @param serviceProduct
	 * @param taskEvent
	 * @param ac //环节配置
	 * @param uccs 用户关注工单设置 一个用户关注就是一个记录 所以是一个list
	 * @param entity
	 */
	public abstract void CommentInformDo(ServiceProduct serviceProduct, ActivitiEntityEvent taskEvent,
			ActivityConfig ac, List<UserConcernedConfig> uccs, Map entity);
	/**
	 * 
	 * 一次通知循环结束
	 * @return
	 */
	public abstract boolean after();
	/**
	 * 一次通知结束
	 * @return
	 */
	public abstract boolean afterDo();
}
