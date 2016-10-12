package com.chinacreator.c2.qyb.workflow.form.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 表单
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.form.entity.Form", sn = "workflowform", table = "WORKFLOW_FORM", ds = "oracDB")
public class Form implements Serializable {
	private static final long serialVersionUID = 1407413776711680L;
	@Column(id = "form_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String formId;

	/**
	 *字段标号
	 */
	@Column(id = "form_no", datatype = "string128")
	private java.lang.String formNo;

	/**
	 *表单名称
	 */
	@Column(id = "form_name", datatype = "string256")
	private java.lang.String formName;

	/**
	 *描述
	 */
	@Column(id = "form_describe", datatype = "string1024")
	private java.lang.String formDescribe;

	/**
	 *是否有实体数据库表
	 */
	@Column(id = "is_table_storage", datatype = "boolean")
	private java.lang.Boolean isTableStorage;

	/**
	 *数据库表名
	 */
	@Column(id = "table_name", datatype = "string64")
	private java.lang.String tableName;

	@Column(id = "remark1", datatype = "tinyint")
	private java.lang.Integer remark1;

	@Column(id = "remark2", datatype = "string256")
	private java.lang.String remark2;

	@Column(id = "remark3", datatype = "string256")
	private java.lang.String remark3;

	/**
	 * 设置${field.desc}
	 */
	public void setFormId(java.lang.String formId) {
		this.formId = formId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getFormId() {
		return formId;
	}

	/**
	 * 设置字段标号
	 */
	public void setFormNo(java.lang.String formNo) {
		this.formNo = formNo;
	}

	/**
	 * 获取字段标号
	 */
	public java.lang.String getFormNo() {
		return formNo;
	}

	/**
	 * 设置表单名称
	 */
	public void setFormName(java.lang.String formName) {
		this.formName = formName;
	}

	/**
	 * 获取表单名称
	 */
	public java.lang.String getFormName() {
		return formName;
	}

	/**
	 * 设置描述
	 */
	public void setFormDescribe(java.lang.String formDescribe) {
		this.formDescribe = formDescribe;
	}

	/**
	 * 获取描述
	 */
	public java.lang.String getFormDescribe() {
		return formDescribe;
	}

	/**
	 * 设置是否有实体数据库表
	 */
	public void setIsTableStorage(java.lang.Boolean isTableStorage) {
		this.isTableStorage = isTableStorage;
	}

	/**
	 * 获取是否有实体数据库表
	 */
	public java.lang.Boolean isIsTableStorage() {
		return isTableStorage;
	}

	/**
	 * 设置数据库表名
	 */
	public void setTableName(java.lang.String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 获取数据库表名
	 */
	public java.lang.String getTableName() {
		return tableName;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark1(java.lang.Integer remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Integer getRemark1() {
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
}
