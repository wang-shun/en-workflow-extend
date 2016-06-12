package com.chinacreator.c2.qyb.workflow.sla.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 服务协议
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.sla.entity.ServiceAgreement", table = "SERVICE_AGREEMENT", ds = "oracDB")
public class ServiceAgreement implements Serializable {
	private static final long serialVersionUID = 1398688271368192L;
	/**
	 *ID
	 */
	@Column(id = "service_agreement_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String serviceAgreementId;

	/**
	 *NAME
	 */
	@Column(id = "service_agreement_name", datatype = "string256")
	private java.lang.String serviceAgreementName;

	/**
	 *�������ID
	 */
	@Column(id = "service_level_org_id", datatype = "string64")
	private java.lang.String serviceLevelOrgId;

	/**
	 *开始时间
	 */
	@Column(id = "start_date", datatype = "date")
	private java.sql.Date startDate;

	/**
	 *结束时间
	 */
	@Column(id = "end_date", datatype = "date")
	private java.sql.Date endDate;

	/**
	 *优先级
	 */
	@Column(id = "pri_level", datatype = "string64")
	private java.lang.String priLevel;

	/**
	 *状态
	 */
	@Column(id = "state", datatype = "smallint")
	private java.lang.Integer state;

	/**
	 *备注
	 */
	@Column(id = "ramark", datatype = "string1024")
	private java.lang.String ramark;

	/**
	 * 设置ID
	 */
	public void setServiceAgreementId(java.lang.String serviceAgreementId) {
		this.serviceAgreementId = serviceAgreementId;
	}

	/**
	 * 获取ID
	 */
	public java.lang.String getServiceAgreementId() {
		return serviceAgreementId;
	}

	/**
	 * 设置NAME
	 */
	public void setServiceAgreementName(java.lang.String serviceAgreementName) {
		this.serviceAgreementName = serviceAgreementName;
	}

	/**
	 * 获取NAME
	 */
	public java.lang.String getServiceAgreementName() {
		return serviceAgreementName;
	}

	/**
	 * 设置�������ID
	 */
	public void setServiceLevelOrgId(java.lang.String serviceLevelOrgId) {
		this.serviceLevelOrgId = serviceLevelOrgId;
	}

	/**
	 * 获取�������ID
	 */
	public java.lang.String getServiceLevelOrgId() {
		return serviceLevelOrgId;
	}

	/**
	 * 设置开始时间
	 */
	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取开始时间
	 */
	public java.sql.Date getStartDate() {
		return startDate;
	}

	/**
	 * 设置结束时间
	 */
	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取结束时间
	 */
	public java.sql.Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置优先级
	 */
	public void setPriLevel(java.lang.String priLevel) {
		this.priLevel = priLevel;
	}

	/**
	 * 获取优先级
	 */
	public java.lang.String getPriLevel() {
		return priLevel;
	}

	/**
	 * 设置状态
	 */
	public void setState(java.lang.Integer state) {
		this.state = state;
	}

	/**
	 * 获取状态
	 */
	public java.lang.Integer getState() {
		return state;
	}

	/**
	 * 设置备注
	 */
	public void setRamark(java.lang.String ramark) {
		this.ramark = ramark;
	}

	/**
	 * 获取备注
	 */
	public java.lang.String getRamark() {
		return ramark;
	}
}
