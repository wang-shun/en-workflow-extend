package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 用户个性化关注工单配置
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.UserConcernedConfig", table = "WORKFLOW_USERCONCERNED_SETTING", ds = "oracDB")
public class UserConcernedConfig implements Serializable {
	private static final long serialVersionUID = 1468237716340736L;
	@Column(id = "id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String id;

	/**
	 *观察者ID
	 */
	@Column(id = "observer_id", datatype = "string1024")
	private java.lang.String observerId;

	/**
	 *观察者类别 用户 或组
	 */
	@Column(id = "catogory", datatype = "string128")
	private java.lang.String catogory;

	/**
	 *流程实例
	 */
	@Column(id = "process_ins_id", datatype = "string256")
	private java.lang.String processInsId;

	/**
	 *流程定义id
	 */
	@Column(id = "process_def_id", datatype = "string128")
	private java.lang.String processDefId;

	/**
	 *字段分类名称
	 */
	@Column(id = "task_def_id", datatype = "string256")
	private java.lang.String taskDefId;

	/**
	 *事件名称
	 */
	@Column(id = "action", datatype = "string256")
	private java.lang.String action;

	/**
	 *通知类型
	 */
	@Column(id = "inform_type", datatype = "string512")
	private java.lang.String informType;

	/**
	 *1
	 */
	@Column(id = "remark1", datatype = "string256")
	private java.lang.String remark1;

	/**
	 *2
	 */
	@Column(id = "remark2", datatype = "string256")
	private java.lang.String remark2;

	/**
	 *3
	 */
	@Column(id = "remark3", datatype = "string256")
	private java.lang.String remark3;

	/**
	 *4
	 */
	@Column(id = "remark4", datatype = "string256")
	private java.lang.String remark4;

	/**
	 * 设置${field.desc}
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * 设置观察者ID
	 */
	public void setObserverId(java.lang.String observerId) {
		this.observerId = observerId;
	}

	/**
	 * 获取观察者ID
	 */
	public java.lang.String getObserverId() {
		return observerId;
	}

	/**
	 * 设置观察者类别 用户 或组
	 */
	public void setCatogory(java.lang.String catogory) {
		this.catogory = catogory;
	}

	/**
	 * 获取观察者类别 用户 或组
	 */
	public java.lang.String getCatogory() {
		return catogory;
	}

	/**
	 * 设置流程实例
	 */
	public void setProcessInsId(java.lang.String processInsId) {
		this.processInsId = processInsId;
	}

	/**
	 * 获取流程实例
	 */
	public java.lang.String getProcessInsId() {
		return processInsId;
	}

	/**
	 * 设置流程定义id
	 */
	public void setProcessDefId(java.lang.String processDefId) {
		this.processDefId = processDefId;
	}

	/**
	 * 获取流程定义id
	 */
	public java.lang.String getProcessDefId() {
		return processDefId;
	}

	/**
	 * 设置字段分类名称
	 */
	public void setTaskDefId(java.lang.String taskDefId) {
		this.taskDefId = taskDefId;
	}

	/**
	 * 获取字段分类名称
	 */
	public java.lang.String getTaskDefId() {
		return taskDefId;
	}

	/**
	 * 设置事件名称
	 */
	public void setAction(java.lang.String action) {
		this.action = action;
	}

	/**
	 * 获取事件名称
	 */
	public java.lang.String getAction() {
		return action;
	}

	/**
	 * 设置通知类型
	 */
	public void setInformType(java.lang.String informType) {
		this.informType = informType;
	}

	/**
	 * 获取通知类型
	 */
	public java.lang.String getInformType() {
		return informType;
	}

	/**
	 * 设置1
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取1
	 */
	public java.lang.String getRemark1() {
		return remark1;
	}

	/**
	 * 设置2
	 */
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取2
	 */
	public java.lang.String getRemark2() {
		return remark2;
	}

	/**
	 * 设置3
	 */
	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	/**
	 * 获取3
	 */
	public java.lang.String getRemark3() {
		return remark3;
	}

	/**
	 * 设置4
	 */
	public void setRemark4(java.lang.String remark4) {
		this.remark4 = remark4;
	}

	/**
	 * 获取4
	 */
	public java.lang.String getRemark4() {
		return remark4;
	}
}
