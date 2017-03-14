package com.chinacreator.c2.qyb.workflow.read.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;
import com.chinacreator.c2.annotation.SortType;

/**
 * 实例阅读记录
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.read.entity.ProcInsReadRecord", table = "SERVICE_PROC_READING", ds = "oracDB")
public class ProcInsReadRecord implements Serializable {
	private static final long serialVersionUID = 2072495695036416L;
	@Column(id = "id", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String id;

	/**
	 *发送人ID
	 */
	@Column(id = "send_user_id", datatype = "string64")
	private java.lang.String sendUserId;

	/**
	 *发送人名字
	 */
	@Column(id = "send_user_name", datatype = "string256")
	private java.lang.String sendUserName;

	/**
	 *接收人ID
	 */
	@Column(id = "receive_user_id", datatype = "string64")
	private java.lang.String receiveUserId;

	/**
	 *接收人名字
	 */
	@Column(id = "receive_user_name", datatype = "string256")
	private java.lang.String receiveUserName;

	/**
	 *接收部门ID
	 */
	@Column(id = "receive_org_id", datatype = "string64")
	private java.lang.String receiveOrgId;

	/**
	 *接收部门名字
	 */
	@Column(id = "receive_org_name", datatype = "string256")
	private java.lang.String receiveOrgName;

	/**
	 *要查阅的流程实例
	 */
	@Column(id = "proc_inst_id", datatype = "string64")
	private java.lang.String procInstId;

	/**
	 *要查阅的实例businessKey
	 */
	@Column(id = "business_key", datatype = "string256")
	private java.lang.String businessKey;

	/**
	 *0 未读 1 已读 
	 */
	@Column(id = "read_status", datatype = "tinyint")
	private java.lang.Integer readStatus;

	/**
	 *0 未签收 1 已签收
	 */
	@Column(id = "sign_status", datatype = "tinyint")
	private java.lang.Integer signStatus;

	/**
	 *0 未回执 1 已发回执
	 */
	@Column(id = "receipt_status", datatype = "tinyint")
	private java.lang.Integer receiptStatus;

	/**
	 *阅读时间
	 */
	@Column(id = "read_time", datatype = "long")
	private java.lang.Long readTime;

	/**
	 *签收时间
	 */
	@Column(id = "sign_time", datatype = "long")
	private java.lang.Long signTime;

	/**
	 *回执时间
	 */
	@Column(id = "receipt_time", datatype = "long")
	private java.lang.Long receiptTime;

	/**
	 *
	 */
	@Column(id = "remark1", datatype = "string256")
	private java.lang.String remark1;

	@Column(id = "remark2", datatype = "string256")
	private java.lang.String remark2;

	@Column(id = "remark3", datatype = "string256")
	private java.lang.String remark3;

	@Column(id = "remark4", datatype = "string256")
	private java.lang.String remark4;

	@Column(id = "remark5", datatype = "string256")
	private java.lang.String remark5;

	/**
	 *任务环节ID
	 */
	@Column(id = "send_activity_id", datatype = "string64")
	private java.lang.String sendActivityId;

	/**
	 *任务环节名称
	 */
	@Column(id = "send_activity_name", datatype = "string256")
	private java.lang.String sendActivityName;

	/**
	 *事项ID
	 */
	@Column(id = "send_module_id", datatype = "string64")
	private java.lang.String sendModuleId;

	/**
	 *事项名称
	 */
	@Column(id = "send_module_name", datatype = "string256")
	private java.lang.String sendModuleName;

	/**
	 *传阅时间
	 */
	@Column(id = "send_time", datatype = "timestamp", sort = SortType.desc)
	private java.sql.Timestamp sendTime;

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
	 * 设置发送人ID
	 */
	public void setSendUserId(java.lang.String sendUserId) {
		this.sendUserId = sendUserId;
	}

	/**
	 * 获取发送人ID
	 */
	public java.lang.String getSendUserId() {
		return sendUserId;
	}

	/**
	 * 设置发送人名字
	 */
	public void setSendUserName(java.lang.String sendUserName) {
		this.sendUserName = sendUserName;
	}

	/**
	 * 获取发送人名字
	 */
	public java.lang.String getSendUserName() {
		return sendUserName;
	}

	/**
	 * 设置接收人ID
	 */
	public void setReceiveUserId(java.lang.String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	/**
	 * 获取接收人ID
	 */
	public java.lang.String getReceiveUserId() {
		return receiveUserId;
	}

	/**
	 * 设置接收人名字
	 */
	public void setReceiveUserName(java.lang.String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	/**
	 * 获取接收人名字
	 */
	public java.lang.String getReceiveUserName() {
		return receiveUserName;
	}

	/**
	 * 设置接收部门ID
	 */
	public void setReceiveOrgId(java.lang.String receiveOrgId) {
		this.receiveOrgId = receiveOrgId;
	}

	/**
	 * 获取接收部门ID
	 */
	public java.lang.String getReceiveOrgId() {
		return receiveOrgId;
	}

	/**
	 * 设置接收部门名字
	 */
	public void setReceiveOrgName(java.lang.String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * 获取接收部门名字
	 */
	public java.lang.String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * 设置要查阅的流程实例
	 */
	public void setProcInstId(java.lang.String procInstId) {
		this.procInstId = procInstId;
	}

	/**
	 * 获取要查阅的流程实例
	 */
	public java.lang.String getProcInstId() {
		return procInstId;
	}

	/**
	 * 设置要查阅的实例businessKey
	 */
	public void setBusinessKey(java.lang.String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 获取要查阅的实例businessKey
	 */
	public java.lang.String getBusinessKey() {
		return businessKey;
	}

	/**
	 * 设置0 未读 1 已读 
	 */
	public void setReadStatus(java.lang.Integer readStatus) {
		this.readStatus = readStatus;
	}

	/**
	 * 获取0 未读 1 已读 
	 */
	public java.lang.Integer getReadStatus() {
		return readStatus;
	}

	/**
	 * 设置0 未签收 1 已签收
	 */
	public void setSignStatus(java.lang.Integer signStatus) {
		this.signStatus = signStatus;
	}

	/**
	 * 获取0 未签收 1 已签收
	 */
	public java.lang.Integer getSignStatus() {
		return signStatus;
	}

	/**
	 * 设置0 未回执 1 已发回执
	 */
	public void setReceiptStatus(java.lang.Integer receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	/**
	 * 获取0 未回执 1 已发回执
	 */
	public java.lang.Integer getReceiptStatus() {
		return receiptStatus;
	}

	/**
	 * 设置阅读时间
	 */
	public void setReadTime(java.lang.Long readTime) {
		this.readTime = readTime;
	}

	/**
	 * 获取阅读时间
	 */
	public java.lang.Long getReadTime() {
		return readTime;
	}

	/**
	 * 设置签收时间
	 */
	public void setSignTime(java.lang.Long signTime) {
		this.signTime = signTime;
	}

	/**
	 * 获取签收时间
	 */
	public java.lang.Long getSignTime() {
		return signTime;
	}

	/**
	 * 设置回执时间
	 */
	public void setReceiptTime(java.lang.Long receiptTime) {
		this.receiptTime = receiptTime;
	}

	/**
	 * 获取回执时间
	 */
	public java.lang.Long getReceiptTime() {
		return receiptTime;
	}

	/**
	 * 设置
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取
	 */
	public java.lang.String getRemark1() {
		return remark1;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark2() {
		return remark2;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark3() {
		return remark3;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark4(java.lang.String remark4) {
		this.remark4 = remark4;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark4() {
		return remark4;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark5(java.lang.String remark5) {
		this.remark5 = remark5;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark5() {
		return remark5;
	}

	/**
	 * 设置任务环节ID
	 */
	public void setSendActivityId(java.lang.String sendActivityId) {
		this.sendActivityId = sendActivityId;
	}

	/**
	 * 获取任务环节ID
	 */
	public java.lang.String getSendActivityId() {
		return sendActivityId;
	}

	/**
	 * 设置任务环节名称
	 */
	public void setSendActivityName(java.lang.String sendActivityName) {
		this.sendActivityName = sendActivityName;
	}

	/**
	 * 获取任务环节名称
	 */
	public java.lang.String getSendActivityName() {
		return sendActivityName;
	}

	/**
	 * 设置事项ID
	 */
	public void setSendModuleId(java.lang.String sendModuleId) {
		this.sendModuleId = sendModuleId;
	}

	/**
	 * 获取事项ID
	 */
	public java.lang.String getSendModuleId() {
		return sendModuleId;
	}

	/**
	 * 设置事项名称
	 */
	public void setSendModuleName(java.lang.String sendModuleName) {
		this.sendModuleName = sendModuleName;
	}

	/**
	 * 获取事项名称
	 */
	public java.lang.String getSendModuleName() {
		return sendModuleName;
	}

	/**
	 * 设置传阅时间
	 */
	public void setSendTime(java.sql.Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 获取传阅时间
	 */
	public java.sql.Timestamp getSendTime() {
		return sendTime;
	}
}
