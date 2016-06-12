package com.chinacreator.c2.qyb.workflow.sla.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.sla.entity.ServiceAgreement;

/**
 * sla服务产品关联
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.sla.entity.SlaServiceRroductRel", table = "SERVICE_SLA_SP_REL", ds = "oracDB")
public class SlaServiceRroductRel implements Serializable {
	private static final long serialVersionUID = 1398688272449536L;
	@Column(id = "rel_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String relId;

	/**
	 *产品ID
	 */
	@Column(id = "product_id", association = true)
	private ServiceProduct productId;

	/**
	 *SLA_ID
	 */
	@Column(id = "sla_id", association = true)
	private ServiceAgreement slaId;

	/**
	 *描述
	 */
	@Column(id = "product_describe", datatype = "string1024")
	private java.lang.String productDescribe;

	/**
	 *服务类型
	 */
	@Column(id = "service_type_id", datatype = "string128")
	private java.lang.String serviceTypeId;

	/**
	 *分组
	 */
	@Column(id = "group_id", datatype = "string128")
	private java.lang.String groupId;

	/**
	 *关联流程
	 */
	@Column(id = "wf_processdefid", datatype = "string128")
	private java.lang.String wfProcessdefid;

	/**
	 * 设置${field.desc}
	 */
	public void setRelId(java.lang.String relId) {
		this.relId = relId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRelId() {
		return relId;
	}

	/**
	 * 设置产品ID
	 */
	public void setProductId(ServiceProduct productId) {
		this.productId = productId;
	}

	/**
	 * 获取产品ID
	 */
	public ServiceProduct getProductId() {
		return productId;
	}

	/**
	 * 设置SLA_ID
	 */
	public void setSlaId(ServiceAgreement slaId) {
		this.slaId = slaId;
	}

	/**
	 * 获取SLA_ID
	 */
	public ServiceAgreement getSlaId() {
		return slaId;
	}

	/**
	 * 设置描述
	 */
	public void setProductDescribe(java.lang.String productDescribe) {
		this.productDescribe = productDescribe;
	}

	/**
	 * 获取描述
	 */
	public java.lang.String getProductDescribe() {
		return productDescribe;
	}

	/**
	 * 设置服务类型
	 */
	public void setServiceTypeId(java.lang.String serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	/**
	 * 获取服务类型
	 */
	public java.lang.String getServiceTypeId() {
		return serviceTypeId;
	}

	/**
	 * 设置分组
	 */
	public void setGroupId(java.lang.String groupId) {
		this.groupId = groupId;
	}

	/**
	 * 获取分组
	 */
	public java.lang.String getGroupId() {
		return groupId;
	}

	/**
	 * 设置关联流程
	 */
	public void setWfProcessdefid(java.lang.String wfProcessdefid) {
		this.wfProcessdefid = wfProcessdefid;
	}

	/**
	 * 获取关联流程
	 */
	public java.lang.String getWfProcessdefid() {
		return wfProcessdefid;
	}
}
