package com.chinacreator.c2.qyb.workflow.module.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 服务产品
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct", sn="workflowserviceproduct", table = "SERVICE_SERVICEPRODUCT", ds = "oracDB")
public class ServiceProduct implements Serializable {
	private static final long serialVersionUID = 1398697341452288L;
	@Column(id = "product_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String productId;

	/**
	 *产品编码
	 */
	@Column(id = "product_no", datatype = "string128")
	private java.lang.String productNo;

	/**
	 *产品名称
	 */
	@Column(id = "product_name", datatype = "string256")
	private java.lang.String productName;

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
	 *流程启动url
	 */
	@Column(id = "wf_processstart_url", datatype = "string512")
	private java.lang.String wfProcessstartUrl;

	/**
	 *表单
	 */
	@Column(id = "form_id", datatype = "string128")
	private java.lang.String formId;

	/**
	 *图标
	 */
	@Column(id = "ico", datatype = "string512")
	private java.lang.String ico;

	/**
	 * 设置${field.desc}
	 */
	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getProductId() {
		return productId;
	}

	/**
	 * 设置产品编码
	 */
	public void setProductNo(java.lang.String productNo) {
		this.productNo = productNo;
	}

	/**
	 * 获取产品编码
	 */
	public java.lang.String getProductNo() {
		return productNo;
	}

	/**
	 * 设置产品名称
	 */
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	/**
	 * 获取产品名称
	 */
	public java.lang.String getProductName() {
		return productName;
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

	/**
	 * 设置流程启动url
	 */
	public void setWfProcessstartUrl(java.lang.String wfProcessstartUrl) {
		this.wfProcessstartUrl = wfProcessstartUrl;
	}

	/**
	 * 获取流程启动url
	 */
	public java.lang.String getWfProcessstartUrl() {
		return wfProcessstartUrl;
	}

	/**
	 * 设置表单
	 */
	public void setFormId(java.lang.String formId) {
		this.formId = formId;
	}

	/**
	 * 获取表单
	 */
	public java.lang.String getFormId() {
		return formId;
	}

	/**
	 * 设置图标
	 */
	public void setIco(java.lang.String ico) {
		this.ico = ico;
	}

	/**
	 * 获取图标
	 */
	public java.lang.String getIco() {
		return ico;
	}
}
