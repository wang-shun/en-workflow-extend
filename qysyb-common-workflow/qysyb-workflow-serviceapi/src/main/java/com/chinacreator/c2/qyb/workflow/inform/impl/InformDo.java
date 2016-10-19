package com.chinacreator.c2.qyb.workflow.inform.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.chinacreator.c2.qyb.workflow.config.entity.ActivityConfig;
import com.chinacreator.c2.qyb.workflow.config.entity.UserConcernedConfig;
import com.chinacreator.c2.qyb.workflow.inform.inf.IInformDo;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;

public class InformDo implements IInformDo {
	String ccInformStr;
	//环节配置
	ActivityConfig ac;
	//用户关注工单设置 一个用户关注就是一个记录 所以是一个list
	List<UserConcernedConfig> uccs;
	@Override
	public void TaskInformDo(ServiceProduct serviceProduct,
			ActivitiEntityEvent taskEvent, Map entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void CommentInformDo(ServiceProduct serviceProduct,
			ActivitiEntityEvent commentEvent, Map entity) {
		// TODO Auto-generated method stub

	}

	protected TaskEntity getTaskEntity(ActivitiEntityEvent taskEvent){
		return (TaskEntity) taskEvent.getEntity();
	}
	
	protected CommentEntity getCommentEntity(ActivitiEntityEvent commentEvent){
		return (CommentEntity) commentEvent.getEntity();
	}

	public String getCcInformStr() {
		return ccInformStr;
	}

	public void setCcInformStr(String ccInformStr) {
		this.ccInformStr = ccInformStr;
	}

	public ActivityConfig getAc() {
		return ac;
	}

	public void setAc(ActivityConfig ac) {
		this.ac = ac;
	}

	public List<UserConcernedConfig> getUccs() {
		return uccs;
	}

	public void setUccs(List<UserConcernedConfig> uccs) {
		this.uccs = uccs;
	}

	@Override
	public boolean before() {
		return true;
	}

	@Override
	public boolean after() {
		return true;
	}

	@Override
	public boolean beforeDo() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean afterDo() {
		ccInformStr = "{}";
		ac = null;
		uccs = null;
		return true;
	}	
}
