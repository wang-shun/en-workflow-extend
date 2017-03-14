package com.chinacreator.c2.qyb.workflow.form.entity;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 表单字段关联表
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.form.entity.FormFieldRel", sn="workflowformfieldrel", table = "WORKFLOW_FORM_FIELD_REL", ds = "oracDB", cache = false)
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
	 *关联时自定义占位符
	 */
	@Column(id = "display_span", datatype = "mediumdouble")
	private java.lang.Double displaySpan;

	/**
	 *关联时自定义LABEL宽度
	 */
	@Column(id = "label_width", datatype = "mediumdouble")
	private java.lang.Double labelWidth;

	/**
	 *关联时自定义 显示名
	 */
	@Column(id = "display_name", datatype = "string128")
	private java.lang.String displayName;

	/**
	 *关联时 他会出现在的位置 用来控制样式
	 */
	@Column(id = "position", datatype = "string128")
	private java.lang.String position;

	/**
	 *关联时 自定义 字典名
	 */
	@Column(id = "dict_name", datatype = "string128")
	private java.lang.String dictName;

	/**
	 *关联时自定义属性1
	 */
	@Column(id = "remark1", datatype = "string128")
	private java.lang.String remark1;

	/**
	 *关联时自定义属性2
	 */
	@Column(id = "remark2", datatype = "string128")
	private java.lang.String remark2;

	/**
	 *关联时自定义属性3
	 */
	@Column(id = "remark3", datatype = "string128")
	private java.lang.String remark3;

	/**
	 *关联时自定义属性4
	 */
	@Column(id = "remark4", datatype = "string128")
	private java.lang.String remark4;

	/**
	 *关联时自定义属性5
	 */
	@Column(id = "remark5", datatype = "boolean")
	private java.lang.Boolean remark5;

	/**
	 *关联时自定义属性6
	 */
	@Column(id = "remark6", datatype = "mediumdouble")
	private java.lang.Double remark6;

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

	/**
	 * 设置关联时自定义占位符
	 */
	public void setDisplaySpan(java.lang.Double displaySpan) {
		this.displaySpan = displaySpan;
	}

	/**
	 * 获取关联时自定义占位符
	 */
	public java.lang.Double getDisplaySpan() {
		return displaySpan;
	}

	/**
	 * 设置关联时自定义LABEL宽度
	 */
	public void setLabelWidth(java.lang.Double labelWidth) {
		this.labelWidth = labelWidth;
	}

	/**
	 * 获取关联时自定义LABEL宽度
	 */
	public java.lang.Double getLabelWidth() {
		return labelWidth;
	}

	/**
	 * 设置关联时自定义 显示名
	 */
	public void setDisplayName(java.lang.String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 获取关联时自定义 显示名
	 */
	public java.lang.String getDisplayName() {
		return displayName;
	}

	/**
	 * 设置关联时 他会出现在的位置 用来控制样式
	 */
	public void setPosition(java.lang.String position) {
		this.position = position;
	}

	/**
	 * 获取关联时 他会出现在的位置 用来控制样式
	 */
	public java.lang.String getPosition() {
		return position;
	}

	/**
	 * 设置关联时 自定义 字典名
	 */
	public void setDictName(java.lang.String dictName) {
		this.dictName = dictName;
	}

	/**
	 * 获取关联时 自定义 字典名
	 */
	public java.lang.String getDictName() {
		return dictName;
	}

	/**
	 * 设置关联时自定义属性1
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取关联时自定义属性1
	 */
	public java.lang.String getRemark1() {
		return remark1;
	}

	/**
	 * 设置关联时自定义属性2
	 */
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取关联时自定义属性2
	 */
	public java.lang.String getRemark2() {
		return remark2;
	}

	/**
	 * 设置关联时自定义属性3
	 */
	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	/**
	 * 获取关联时自定义属性3
	 */
	public java.lang.String getRemark3() {
		return remark3;
	}

	/**
	 * 设置关联时自定义属性4
	 */
	public void setRemark4(java.lang.String remark4) {
		this.remark4 = remark4;
	}

	/**
	 * 获取关联时自定义属性4
	 */
	public java.lang.String getRemark4() {
		return remark4;
	}

	/**
	 * 设置关联时自定义属性5
	 */
	public void setRemark5(java.lang.Boolean remark5) {
		this.remark5 = remark5;
	}

	/**
	 * 获取关联时自定义属性5
	 */
	public java.lang.Boolean isRemark5() {
		return remark5;
	}

	/**
	 * 设置关联时自定义属性6
	 */
	public void setRemark6(java.lang.Double remark6) {
		this.remark6 = remark6;
	}

	/**
	 * 获取关联时自定义属性6
	 */
	public java.lang.Double getRemark6() {
		return remark6;
	}
}
