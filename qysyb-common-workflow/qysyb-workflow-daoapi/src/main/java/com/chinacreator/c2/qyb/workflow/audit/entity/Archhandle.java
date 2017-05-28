package com.chinacreator.c2.qyb.workflow.audit.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;
import com.chinacreator.c2.omp.common.Organization;
import com.chinacreator.c2.omp.common.UserInfo;

/**
 * 意见
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.audit.entity.Archhandle", sn="wfarchhandle", table = "TA_OFFICE_ARCHHANDLE", ds = "bspf")
public class Archhandle implements Serializable {
	private static final long serialVersionUID = 1625356999278592L;
	/**
	 *OID
	 */
	@Column(id = "oid", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String oid;

	/**
	 *流程主键OID
	 */
	@Column(id = "cc_form_instanceid", datatype = "string64")
	private java.lang.String ccFormInstanceid;

	/**
	 *意见填写人ID
	 */
	@Column(id = "auditer_id", association = true)
	private UserInfo auditerId;

	/**
	 *意见填写时间
	 */
	@Column(id = "audit_time", datatype = "timestamp")
	private java.sql.Timestamp auditTime;

	/**
	 *意见内容
	 */
	@Column(id = "opinion_content", datatype = "string5")
	private java.lang.String opinionContent;

	/**
	 *逻辑删除(1:已删除;0:未删除)
	 */
	@Column(id = "logical_delete", datatype = "tinyint")
	private java.lang.Integer logicalDelete;

	/**
	 *意见填写环节名称
	 */
	@Column(id = "activity_name", datatype = "string64")
	private java.lang.String activityName;

	/**
	 *流程定义ID或者module_id
	 */
	@Column(id = "proc_ins_id", datatype = "string128")
	private java.lang.String procInsId;

	/**
	 *意见填写环节ID
	 */
	@Column(id = "activity_id", datatype = "string128")
	private java.lang.String activityId;

	/**
	 *审核人部门ID
	 */
	@Column(id = "audit_org_id", association = true)
	private Organization auditOrgId;

	/**
	 *业务id
	 */
	@Column(id = "business_key", datatype = "string128")
	private java.lang.String businessKey;

	/**
	 *审核状态
	 */
	@Column(id = "audit_state", datatype = "string64")
	private java.lang.String auditState;

	/**
	 * 设置OID
	 */
	public void setOid(java.lang.String oid) {
		this.oid = oid;
	}

	/**
	 * 获取OID
	 */
	public java.lang.String getOid() {
		return oid;
	}

	/**
	 * 设置流程主键OID
	 */
	public void setCcFormInstanceid(java.lang.String ccFormInstanceid) {
		this.ccFormInstanceid = ccFormInstanceid;
	}

	/**
	 * 获取流程主键OID
	 */
	public java.lang.String getCcFormInstanceid() {
		return ccFormInstanceid;
	}

	/**
	 * 设置意见填写人ID
	 */
	public void setAuditerId(UserInfo auditerId) {
		this.auditerId = auditerId;
	}

	/**
	 * 获取意见填写人ID
	 */
	public UserInfo getAuditerId() {
		return auditerId;
	}

	/**
	 * 设置意见填写时间
	 */
	public void setAuditTime(java.sql.Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 获取意见填写时间
	 */
	public java.sql.Timestamp getAuditTime() {
		return auditTime;
	}

	/**
	 * 设置意见内容
	 */
	public void setOpinionContent(java.lang.String opinionContent) {
		this.opinionContent = opinionContent;
	}

	/**
	 * 获取意见内容
	 */
	public java.lang.String getOpinionContent() {
		return opinionContent;
	}

	/**
	 * 设置逻辑删除(1:已删除;0:未删除)
	 */
	public void setLogicalDelete(java.lang.Integer logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * 获取逻辑删除(1:已删除;0:未删除)
	 */
	public java.lang.Integer getLogicalDelete() {
		return logicalDelete;
	}

	/**
	 * 设置意见填写环节名称
	 */
	public void setActivityName(java.lang.String activityName) {
		this.activityName = activityName;
	}

	/**
	 * 获取意见填写环节名称
	 */
	public java.lang.String getActivityName() {
		return activityName;
	}

	/**
	 * 设置流程定义ID或者module_id
	 */
	public void setProcInsId(java.lang.String procInsId) {
		this.procInsId = procInsId;
	}

	/**
	 * 获取流程定义ID或者module_id
	 */
	public java.lang.String getProcInsId() {
		return procInsId;
	}

	/**
	 * 设置意见填写环节ID
	 */
	public void setActivityId(java.lang.String activityId) {
		this.activityId = activityId;
	}

	/**
	 * 获取意见填写环节ID
	 */
	public java.lang.String getActivityId() {
		return activityId;
	}

	/**
	 * 设置审核人部门ID
	 */
	public void setAuditOrgId(Organization auditOrgId) {
		this.auditOrgId = auditOrgId;
	}

	/**
	 * 获取审核人部门ID
	 */
	public Organization getAuditOrgId() {
		return auditOrgId;
	}

	/**
	 * 设置业务id
	 */
	public void setBusinessKey(java.lang.String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 获取业务id
	 */
	public java.lang.String getBusinessKey() {
		return businessKey;
	}

	/**
	 * 设置审核状态
	 */
	public void setAuditState(java.lang.String auditState) {
		this.auditState = auditState;
	}

	/**
	 * 获取审核状态
	 */
	public java.lang.String getAuditState() {
		return auditState;
	}
}
