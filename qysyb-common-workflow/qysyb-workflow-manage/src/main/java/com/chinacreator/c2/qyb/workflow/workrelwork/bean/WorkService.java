package com.chinacreator.c2.qyb.workflow.workrelwork.bean;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 工单
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.WorkService", table = "v_kcomp_work", ds = "newDS", cache = false)
public class WorkService {
	/**
	 *
	 */
	@Column(id = "work_id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String workId;

	/**
	 *
	 */
	@Column(id = "work_no", datatype = "string256")
	private java.lang.String workNo;

	/**
	 *
	 */
	@Column(id = "work_title", datatype = "string512")
	private java.lang.String workTitle;

	/**
	 *
	 */
	@Column(id = "work_describe", datatype = "string1536")
	private java.lang.String workDescribe;

	/**
	 *
	 */
	@Column(id = "applicant_id", datatype = "string256")
	private java.lang.String applicantId;

	/**
	 *
	 */
	@Column(id = "applicant_name", datatype = "string512")
	private java.lang.String applicantName;

	/**
	 *
	 */
	@Column(id = "applicant_contact_way", datatype = "string128")
	private java.lang.String applicantContactWay;

	/**
	 *
	 */
	@Column(id = "apply_branch_id", datatype = "string256")
	private java.lang.String applyBranchId;

	/**
	 *
	 */
	@Column(id = "apply_branch_name", datatype = "string512")
	private java.lang.String applyBranchName;

	/**
	 *
	 */
	@Column(id = "incident_level_id", datatype = "string256")
	private java.lang.String incidentLevelId;

	/**
	 *
	 */
	@Column(id = "urgent_level_id", datatype = "string256")
	private java.lang.String urgentLevelId;

	/**
	 *
	 */
	@Column(id = "affect_scope_id", datatype = "string256")
	private java.lang.String affectScopeId;

	/**
	 *
	 */
	@Column(id = "pri_id", datatype = "string256")
	private java.lang.String priId;

	/**
	 *
	 */
	@Column(id = "work_stage", datatype = "string512")
	private java.lang.String workStage;

	/**
	 *
	 */
	@Column(id = "work_state_id", datatype = "string256")
	private java.lang.String workStateId;

	/**
	 *
	 */
	@Column(id = "happen_date", datatype = "timestamp")
	private java.sql.Timestamp happenDate;

	/**
	 *
	 */
	@Column(id = "accept_date", datatype = "timestamp")
	private java.sql.Timestamp acceptDate;

	/**
	 *
	 */
	@Column(id = "assign_date", datatype = "timestamp")
	private java.sql.Timestamp assignDate;

	/**
	 *
	 */
	@Column(id = "business_key", datatype = "string1024")
	private java.lang.String businessKey;

	/**
	 *
	 */
	@Column(id = "task_assignee", datatype = "string256")
	private java.lang.String taskAssignee;

	/**
	 *
	 */
	@Column(id = "task_def", datatype = "string256")
	private java.lang.String taskDef;

	/**
	 *
	 */
	@Column(id = "module_id", datatype = "string256")
	private java.lang.String moduleId;

	/**
	 *
	 */
	@Column(id = "service_type_id", datatype = "char20")
	private java.lang.String serviceTypeId;

	/**
	 *
	 */
	@Column(id = "service_type_name", datatype = "char20")
	private java.lang.String serviceTypeName;

	/**
	 *
	 */
	@Column(id = "proinsid", datatype = "char20")
	private java.lang.String proinsid;

	/**
	 *
	 */
	@Column(id = "prodefid", datatype = "char20")
	private java.lang.String prodefid;

	/**
	 *
	 */
	@Column(id = "task_id", datatype = "char20")
	private java.lang.String taskId;

	/**
	 *
	 */
	@Column(id = "is_recieve_timeout", datatype = "boolean")
	private java.lang.Boolean isRecieveTimeout;

	/**
	 *
	 */
	@Column(id = "is_handle_timeout", datatype = "boolean")
	private java.lang.Boolean isHandleTimeout;

	/**
	 *
	 */
	@Column(id = "sla_remaintime", datatype = "int")
	private java.lang.Integer slaRemaintime;

	/**
	 *
	 */
	@Column(id = "type", datatype = "string64")
	private java.lang.String type;

	/**
	 * 设置
	 */
	public void setWorkId(java.lang.String workId) {
		this.workId = workId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkId() {
		return workId;
	}

	/**
	 * 设置
	 */
	public void setWorkNo(java.lang.String workNo) {
		this.workNo = workNo;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkNo() {
		return workNo;
	}

	/**
	 * 设置
	 */
	public void setWorkTitle(java.lang.String workTitle) {
		this.workTitle = workTitle;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkTitle() {
		return workTitle;
	}

	/**
	 * 设置
	 */
	public void setWorkDescribe(java.lang.String workDescribe) {
		this.workDescribe = workDescribe;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkDescribe() {
		return workDescribe;
	}

	/**
	 * 设置
	 */
	public void setApplicantId(java.lang.String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getApplicantId() {
		return applicantId;
	}

	/**
	 * 设置
	 */
	public void setApplicantName(java.lang.String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * 获取
	 */
	public java.lang.String getApplicantName() {
		return applicantName;
	}

	/**
	 * 设置
	 */
	public void setApplicantContactWay(java.lang.String applicantContactWay) {
		this.applicantContactWay = applicantContactWay;
	}

	/**
	 * 获取
	 */
	public java.lang.String getApplicantContactWay() {
		return applicantContactWay;
	}

	/**
	 * 设置
	 */
	public void setApplyBranchId(java.lang.String applyBranchId) {
		this.applyBranchId = applyBranchId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getApplyBranchId() {
		return applyBranchId;
	}

	/**
	 * 设置
	 */
	public void setApplyBranchName(java.lang.String applyBranchName) {
		this.applyBranchName = applyBranchName;
	}

	/**
	 * 获取
	 */
	public java.lang.String getApplyBranchName() {
		return applyBranchName;
	}

	/**
	 * 设置
	 */
	public void setIncidentLevelId(java.lang.String incidentLevelId) {
		this.incidentLevelId = incidentLevelId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getIncidentLevelId() {
		return incidentLevelId;
	}

	/**
	 * 设置
	 */
	public void setUrgentLevelId(java.lang.String urgentLevelId) {
		this.urgentLevelId = urgentLevelId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getUrgentLevelId() {
		return urgentLevelId;
	}

	/**
	 * 设置
	 */
	public void setAffectScopeId(java.lang.String affectScopeId) {
		this.affectScopeId = affectScopeId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getAffectScopeId() {
		return affectScopeId;
	}

	/**
	 * 设置
	 */
	public void setPriId(java.lang.String priId) {
		this.priId = priId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getPriId() {
		return priId;
	}

	/**
	 * 设置
	 */
	public void setWorkStage(java.lang.String workStage) {
		this.workStage = workStage;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkStage() {
		return workStage;
	}

	/**
	 * 设置
	 */
	public void setWorkStateId(java.lang.String workStateId) {
		this.workStateId = workStateId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkStateId() {
		return workStateId;
	}

	/**
	 * 设置
	 */
	public void setHappenDate(java.sql.Timestamp happenDate) {
		this.happenDate = happenDate;
	}

	/**
	 * 获取
	 */
	public java.sql.Timestamp getHappenDate() {
		return happenDate;
	}

	/**
	 * 设置
	 */
	public void setAcceptDate(java.sql.Timestamp acceptDate) {
		this.acceptDate = acceptDate;
	}

	/**
	 * 获取
	 */
	public java.sql.Timestamp getAcceptDate() {
		return acceptDate;
	}

	/**
	 * 设置
	 */
	public void setAssignDate(java.sql.Timestamp assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * 获取
	 */
	public java.sql.Timestamp getAssignDate() {
		return assignDate;
	}

	/**
	 * 设置
	 */
	public void setBusinessKey(java.lang.String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 获取
	 */
	public java.lang.String getBusinessKey() {
		return businessKey;
	}

	/**
	 * 设置
	 */
	public void setTaskAssignee(java.lang.String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}

	/**
	 * 获取
	 */
	public java.lang.String getTaskAssignee() {
		return taskAssignee;
	}

	/**
	 * 设置
	 */
	public void setTaskDef(java.lang.String taskDef) {
		this.taskDef = taskDef;
	}

	/**
	 * 获取
	 */
	public java.lang.String getTaskDef() {
		return taskDef;
	}

	/**
	 * 设置
	 */
	public void setModuleId(java.lang.String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getModuleId() {
		return moduleId;
	}

	/**
	 * 设置
	 */
	public void setServiceTypeId(java.lang.String serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getServiceTypeId() {
		return serviceTypeId;
	}

	/**
	 * 设置
	 */
	public void setServiceTypeName(java.lang.String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	/**
	 * 获取
	 */
	public java.lang.String getServiceTypeName() {
		return serviceTypeName;
	}

	/**
	 * 设置
	 */
	public void setProinsid(java.lang.String proinsid) {
		this.proinsid = proinsid;
	}

	/**
	 * 获取
	 */
	public java.lang.String getProinsid() {
		return proinsid;
	}

	/**
	 * 设置
	 */
	public void setProdefid(java.lang.String prodefid) {
		this.prodefid = prodefid;
	}

	/**
	 * 获取
	 */
	public java.lang.String getProdefid() {
		return prodefid;
	}

	/**
	 * 设置
	 */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getTaskId() {
		return taskId;
	}

	/**
	 * 设置
	 */
	public void setIsRecieveTimeout(java.lang.Boolean isRecieveTimeout) {
		this.isRecieveTimeout = isRecieveTimeout;
	}

	/**
	 * 获取
	 */
	public java.lang.Boolean isIsRecieveTimeout() {
		return isRecieveTimeout;
	}

	/**
	 * 设置
	 */
	public void setIsHandleTimeout(java.lang.Boolean isHandleTimeout) {
		this.isHandleTimeout = isHandleTimeout;
	}

	/**
	 * 获取
	 */
	public java.lang.Boolean isIsHandleTimeout() {
		return isHandleTimeout;
	}

	/**
	 * 设置
	 */
	public void setSlaRemaintime(java.lang.Integer slaRemaintime) {
		this.slaRemaintime = slaRemaintime;
	}

	/**
	 * 获取
	 */
	public java.lang.Integer getSlaRemaintime() {
		return slaRemaintime;
	}

	/**
	 * 设置
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * 获取
	 */
	public java.lang.String getType() {
		return type;
	}
}
