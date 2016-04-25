package com.chinacreator.c2.omp.service.manage.workflowcommon;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 表单值存储
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.FormFieldValue", table = "WORKFLOW_FORM_FIELD_VALUE", ds = "oracDB")
public class FormFieldValue implements Serializable {
	private static final long serialVersionUID = 1407534226964480L;
	@Column(id = "id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String id;

	/**
	 *表单
	 */
	@Column(id = "form_id", association = true)
	private Form formId;

	/**
	 *表单业务Key
	 */
	@Column(id = "form_business_key", datatype = "string256")
	private java.lang.String formBusinessKey;

	/**
	 *字段
	 */
	@Column(id = "field_id", association = true)
	private FormField fieldId;

	/**
	 *字段值
	 */
	@Column(id = "field_value", datatype = "string2000")
	private java.lang.String fieldValue;

	@Column(id = "field_value_int", datatype = "int")
	private java.lang.Integer fieldValueInt;

	@Column(id = "field_value_timestamp", datatype = "date")
	private java.sql.Date fieldValueTimestamp;

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
	 * 设置表单
	 */
	public void setFormId(Form formId) {
		this.formId = formId;
	}

	/**
	 * 获取表单
	 */
	public Form getFormId() {
		return formId;
	}

	/**
	 * 设置表单业务Key
	 */
	public void setFormBusinessKey(java.lang.String formBusinessKey) {
		this.formBusinessKey = formBusinessKey;
	}

	/**
	 * 获取表单业务Key
	 */
	public java.lang.String getFormBusinessKey() {
		return formBusinessKey;
	}

	/**
	 * 设置字段
	 */
	public void setFieldId(FormField fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * 获取字段
	 */
	public FormField getFieldId() {
		return fieldId;
	}

	/**
	 * 设置字段值
	 */
	public void setFieldValue(java.lang.String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * 获取字段值
	 */
	public java.lang.String getFieldValue() {
		return fieldValue;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setFieldValueInt(java.lang.Integer fieldValueInt) {
		this.fieldValueInt = fieldValueInt;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Integer getFieldValueInt() {
		return fieldValueInt;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setFieldValueTimestamp(java.sql.Date fieldValueTimestamp) {
		this.fieldValueTimestamp = fieldValueTimestamp;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.sql.Date getFieldValueTimestamp() {
		return fieldValueTimestamp;
	}
}
