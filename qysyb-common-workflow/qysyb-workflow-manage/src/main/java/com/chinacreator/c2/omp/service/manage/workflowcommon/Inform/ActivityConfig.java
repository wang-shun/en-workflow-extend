package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 节点配置
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.ActivityConfig", table = "WORKFLOW_ACTIVITY_SETTING", ds = "oracDB")
public class ActivityConfig implements Serializable {
	private static final long serialVersionUID = 1468232196112384L;
	@Column(id = "id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String id;

	/**
	 *流程定义id
	 */
	@Column(id = "process_def_id", datatype = "string128")
	private java.lang.String processDefId;

	/**
	 *环节
	 */
	@Column(id = "task_def_id", datatype = "string256")
	private java.lang.String taskDefId;

	@Column(id = "action", datatype = "string256")
	private java.lang.String action;

	/**
	 *是否邮件通知
	 */
	@Column(id = "inform_type", datatype = "string256")
	private java.lang.String informType;

	/**
	 *需要签收
	 */
	@Column(id = "needclaim", datatype = "smallint")
	private java.lang.Integer needclaim;

	/**
	 *是否计入处理时间
	 */
	@Column(id = "is_sla_time", datatype = "smallint")
	private java.lang.Integer isSlaTime;

	/**
	 *用来设置节点包含哪些tab页
	 */
	@Column(id = "remark1", datatype = "string2000")
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
	 * 设置环节
	 */
	public void setTaskDefId(java.lang.String taskDefId) {
		this.taskDefId = taskDefId;
	}

	/**
	 * 获取环节
	 */
	public java.lang.String getTaskDefId() {
		return taskDefId;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setAction(java.lang.String action) {
		this.action = action;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getAction() {
		return action;
	}

	/**
	 * 设置是否邮件通知
	 */
	public void setInformType(java.lang.String informType) {
		this.informType = informType;
	}

	/**
	 * 获取是否邮件通知
	 */
	public java.lang.String getInformType() {
		return informType;
	}

	/**
	 * 设置需要签收
	 */
	public void setNeedclaim(java.lang.Integer needclaim) {
		this.needclaim = needclaim;
	}

	/**
	 * 获取需要签收
	 */
	public java.lang.Integer getNeedclaim() {
		return needclaim;
	}

	/**
	 * 设置是否计入处理时间
	 */
	public void setIsSlaTime(java.lang.Integer isSlaTime) {
		this.isSlaTime = isSlaTime;
	}

	/**
	 * 获取是否计入处理时间
	 */
	public java.lang.Integer getIsSlaTime() {
		return isSlaTime;
	}

	/**
	 * 设置用来设置节点包含哪些tab页
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取用来设置节点包含哪些tab页
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
