package com.chinacreator.c2.omp.service.manage.workflowcommon;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 字段权限
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.FieldPermission", table = "WORKFLOW_FORM_FIELD_PERMISSON", ds = "oracDB")
public class FieldPermission implements Serializable {
	private static final long serialVersionUID = 1427373070450688L;
	@Column(id = "id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String id;

	/**
	 *字段ID
	 */
	@Column(id = "field_id", association = true)
	private FormField fieldId;

	/**
	 *表单ID
	 */
	@Column(id = "form_id", association = true)
	private Form formId;

	/**
	 *业务key
	 */
	@Column(id = "business_key", datatype = "string256")
	private java.lang.String businessKey;

	@Column(id = "business_key2", datatype = "string512")
	private java.lang.String businessKey2;

	/**
	 *读权限
	 */
	@Column(id = "read_permission", datatype = "boolean")
	private java.lang.Boolean readPermission;

	/**
	 *写权限
	 */
	@Column(id = "write_permission", datatype = "boolean")
	private java.lang.Boolean writePermission;

	/**
	 *是否必填项
	 */
	@Column(id = "fill_necessary", datatype = "boolean")
	private java.lang.Boolean fillNecessary;

	/**
	 *是否可见
	 */
	@Column(id = "visible", datatype = "boolean")
	private java.lang.Boolean visible;

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
	 * 设置字段ID
	 */
	public void setFieldId(FormField fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * 获取字段ID
	 */
	public FormField getFieldId() {
		return fieldId;
	}

	/**
	 * 设置表单ID
	 */
	public void setFormId(Form formId) {
		this.formId = formId;
	}

	/**
	 * 获取表单ID
	 */
	public Form getFormId() {
		return formId;
	}

	/**
	 * 设置业务key
	 */
	public void setBusinessKey(java.lang.String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 获取业务key
	 */
	public java.lang.String getBusinessKey() {
		return businessKey;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setBusinessKey2(java.lang.String businessKey2) {
		this.businessKey2 = businessKey2;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getBusinessKey2() {
		return businessKey2;
	}

	/**
	 * 设置读权限
	 */
	public void setReadPermission(java.lang.Boolean readPermission) {
		this.readPermission = readPermission;
	}

	/**
	 * 获取读权限
	 */
	public java.lang.Boolean isReadPermission() {
		return readPermission;
	}

	/**
	 * 设置写权限
	 */
	public void setWritePermission(java.lang.Boolean writePermission) {
		this.writePermission = writePermission;
	}

	/**
	 * 获取写权限
	 */
	public java.lang.Boolean isWritePermission() {
		return writePermission;
	}

	/**
	 * 设置是否必填项
	 */
	public void setFillNecessary(java.lang.Boolean fillNecessary) {
		this.fillNecessary = fillNecessary;
	}

	/**
	 * 获取是否必填项
	 */
	public java.lang.Boolean isFillNecessary() {
		return fillNecessary;
	}

	/**
	 * 设置是否可见
	 */
	public void setVisible(java.lang.Boolean visible) {
		this.visible = visible;
	}

	/**
	 * 获取是否可见
	 */
	public java.lang.Boolean isVisible() {
		return visible;
	}
}
