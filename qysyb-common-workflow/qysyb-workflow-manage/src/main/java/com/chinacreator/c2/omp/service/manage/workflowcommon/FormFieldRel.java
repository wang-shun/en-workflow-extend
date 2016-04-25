package com.chinacreator.c2.omp.service.manage.workflowcommon;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 表单字段关联表
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.FormFieldRel", table = "WORKFLOW_FORM_FIELD_REL", ds = "oracDB", cache = false)
public class FormFieldRel {
	/**
	 *ID
	 */
	@Column(id = "rel_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String relId;

	/**
	 *form ID
	 */
	@Column(id = "form_id", association = true)
	private Form formId;

	/**
	 *field ID
	 */
	@Column(id = "field_id", association = true)
	private FormField fieldId;

	/**
	 *字段类别 问题信息 基本信息 申请人信息等
	 */
	@Column(id = "categoty_id", datatype = "string128")
	private java.lang.String categotyId;

	@Column(id = "rorder", datatype = "mediumdouble")
	private java.lang.Double rorder;

	/**
	 *描述
	 */
	@Column(id = "rel_describe", datatype = "string1024")
	private java.lang.String relDescribe;

	/**
	 * 设置ID
	 */
	public void setRelId(java.lang.String relId) {
		this.relId = relId;
	}

	/**
	 * 获取ID
	 */
	public java.lang.String getRelId() {
		return relId;
	}

	/**
	 * 设置form ID
	 */
	public void setFormId(Form formId) {
		this.formId = formId;
	}

	/**
	 * 获取form ID
	 */
	public Form getFormId() {
		return formId;
	}

	/**
	 * 设置field ID
	 */
	public void setFieldId(FormField fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * 获取field ID
	 */
	public FormField getFieldId() {
		return fieldId;
	}

	/**
	 * 设置字段类别 问题信息 基本信息 申请人信息等
	 */
	public void setCategotyId(java.lang.String categotyId) {
		this.categotyId = categotyId;
	}

	/**
	 * 获取字段类别 问题信息 基本信息 申请人信息等
	 */
	public java.lang.String getCategotyId() {
		return categotyId;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRorder(java.lang.Double rorder) {
		this.rorder = rorder;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Double getRorder() {
		return rorder;
	}

	/**
	 * 设置描述
	 */
	public void setRelDescribe(java.lang.String relDescribe) {
		this.relDescribe = relDescribe;
	}

	/**
	 * 获取描述
	 */
	public java.lang.String getRelDescribe() {
		return relDescribe;
	}
}
