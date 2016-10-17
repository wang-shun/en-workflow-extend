package com.chinacreator.c2.qyb.workflow.form.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 表单字段
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.form.entity.FormField", table = "WORKFLOW_FORM_FIELD", ds = "oracDB")
public class FormField implements Serializable {
	private static final long serialVersionUID = 1407415065837568L;
	@Column(id = "field_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String fieldId;

	/**
	 *字段编码
	 */
	@Column(id = "field_no", datatype = "string128")
	private java.lang.String fieldNo;

	/**
	 *字段名称
	 */
	@Column(id = "field_name", datatype = "string256")
	private java.lang.String fieldName;

	/**
	 *持久数据类型
	 */
	@Column(id = "field_type", datatype = "string128")
	private java.lang.String fieldType;

	/**
	 *是否为内嵌表单
	 */
	@Column(id = "is_object", datatype = "boolean")
	private java.lang.Boolean isObject;

	/**
	 *内嵌表单ID
	 */
	@Column(id = "object_class", datatype = "string128")
	private java.lang.String objectClass;

	/**
	 *描述
	 */
	@Column(id = "field_describe", datatype = "string1024")
	private java.lang.String fieldDescribe;

	/**
	 *分类Id 基本信息 事件信息等
	 */
	@Column(id = "category_id", datatype = "string128")
	private java.lang.String categoryId;

	/**
	 *前端控件类型
	 */
	@Column(id = "web_display_type_id", datatype = "string128")
	private java.lang.String webDisplayTypeId;

	@Column(id = "forder", datatype = "mediumdouble")
	private java.lang.Double forder;

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

	@Column(id = "remark6", datatype = "string256")
	private java.lang.String remark6;

	@Column(id = "ramark7", datatype = "string1024")
	private java.lang.String ramark7;

	@Column(id = "remark8", datatype = "string1024")
	private java.lang.String remark8;

	@Column(id = "remark9", datatype = "mediumdouble")
	private java.lang.Double remark9;

	@Column(id = "remark10", datatype = "mediumdouble")
	private java.lang.Double remark10;

	/**
	 *字典名 字典下拉框等生效
	 */
	@Column(id = "dict_name", datatype = "string256")
	private java.lang.String dictName;

	/**
	 *数据库表字段名 字段以单独业务表组织时使用
	 */
	@Column(id = "field_col_name", datatype = "string256")
	private java.lang.String fieldColName;

	/**
	 *是否存入流程变量保存
	 */
	@Column(id = "is_proc_var", datatype = "boolean")
	private java.lang.Boolean isProcVar;

	/**
	 *控件占位数
	 */
	@Column(id = "display_span", datatype = "mediumdouble")
	private java.lang.Double displaySpan;

	/**
	 *label宽度 UI模板可选择去实现此字段以达到更好的复用
	 */
	@Column(id = "label_width", datatype = "mediumdouble")
	private java.lang.Double labelWidth;

	/**
	 * 设置${field.desc}
	 */
	public void setFieldId(java.lang.String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getFieldId() {
		return fieldId;
	}

	/**
	 * 设置字段编码
	 */
	public void setFieldNo(java.lang.String fieldNo) {
		this.fieldNo = fieldNo;
	}

	/**
	 * 获取字段编码
	 */
	public java.lang.String getFieldNo() {
		return fieldNo;
	}

	/**
	 * 设置字段名称
	 */
	public void setFieldName(java.lang.String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 获取字段名称
	 */
	public java.lang.String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置持久数据类型
	 */
	public void setFieldType(java.lang.String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * 获取持久数据类型
	 */
	public java.lang.String getFieldType() {
		return fieldType;
	}

	/**
	 * 设置是否为内嵌表单
	 */
	public void setIsObject(java.lang.Boolean isObject) {
		this.isObject = isObject;
	}

	/**
	 * 获取是否为内嵌表单
	 */
	public java.lang.Boolean isIsObject() {
		return isObject;
	}

	/**
	 * 设置内嵌表单ID
	 */
	public void setObjectClass(java.lang.String objectClass) {
		this.objectClass = objectClass;
	}

	/**
	 * 获取内嵌表单ID
	 */
	public java.lang.String getObjectClass() {
		return objectClass;
	}

	/**
	 * 设置描述
	 */
	public void setFieldDescribe(java.lang.String fieldDescribe) {
		this.fieldDescribe = fieldDescribe;
	}

	/**
	 * 获取描述
	 */
	public java.lang.String getFieldDescribe() {
		return fieldDescribe;
	}

	/**
	 * 设置分类Id 基本信息 事件信息等
	 */
	public void setCategoryId(java.lang.String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 获取分类Id 基本信息 事件信息等
	 */
	public java.lang.String getCategoryId() {
		return categoryId;
	}

	/**
	 * 设置前端控件类型
	 */
	public void setWebDisplayTypeId(java.lang.String webDisplayTypeId) {
		this.webDisplayTypeId = webDisplayTypeId;
	}

	/**
	 * 获取前端控件类型
	 */
	public java.lang.String getWebDisplayTypeId() {
		return webDisplayTypeId;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setForder(java.lang.Double forder) {
		this.forder = forder;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Double getForder() {
		return forder;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取${field.desc}
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
	 * 设置${field.desc}
	 */
	public void setRemark6(java.lang.String remark6) {
		this.remark6 = remark6;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark6() {
		return remark6;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRamark7(java.lang.String ramark7) {
		this.ramark7 = ramark7;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRamark7() {
		return ramark7;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark8(java.lang.String remark8) {
		this.remark8 = remark8;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark8() {
		return remark8;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark9(java.lang.Double remark9) {
		this.remark9 = remark9;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Double getRemark9() {
		return remark9;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark10(java.lang.Double remark10) {
		this.remark10 = remark10;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Double getRemark10() {
		return remark10;
	}

	/**
	 * 设置字典名 字典下拉框等生效
	 */
	public void setDictName(java.lang.String dictName) {
		this.dictName = dictName;
	}

	/**
	 * 获取字典名 字典下拉框等生效
	 */
	public java.lang.String getDictName() {
		return dictName;
	}

	/**
	 * 设置数据库表字段名 字段以单独业务表组织时使用
	 */
	public void setFieldColName(java.lang.String fieldColName) {
		this.fieldColName = fieldColName;
	}

	/**
	 * 获取数据库表字段名 字段以单独业务表组织时使用
	 */
	public java.lang.String getFieldColName() {
		return fieldColName;
	}

	/**
	 * 设置是否存入流程变量保存
	 */
	public void setIsProcVar(java.lang.Boolean isProcVar) {
		this.isProcVar = isProcVar;
	}

	/**
	 * 获取是否存入流程变量保存
	 */
	public java.lang.Boolean isIsProcVar() {
		return isProcVar;
	}

	/**
	 * 设置控件占位数
	 */
	public void setDisplaySpan(java.lang.Double displaySpan) {
		this.displaySpan = displaySpan;
	}

	/**
	 * 获取控件占位数
	 */
	public java.lang.Double getDisplaySpan() {
		return displaySpan;
	}

	/**
	 * 设置label宽度 UI模板可选择去实现此字段以达到更好的复用
	 */
	public void setLabelWidth(java.lang.Double labelWidth) {
		this.labelWidth = labelWidth;
	}

	/**
	 * 获取label宽度 UI模板可选择去实现此字段以达到更好的复用
	 */
	public java.lang.Double getLabelWidth() {
		return labelWidth;
	}
}
