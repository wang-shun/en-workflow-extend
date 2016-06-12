package com.chinacreator.c2.qyb.workflow.customretrieval.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;

/**
 * 检索器项
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.customretrieval.entity.RetrieveItem", table = "WORKFLOW_RETRIEVAL_ITEM", ds = "oracDB")
public class RetrieveItem implements Serializable {
	private static final long serialVersionUID = 1499414005514240L;
	@Column(id = "item_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String itemId;

	/**
	 *检索器id
	 */
	@Column(id = "retrieve_key", datatype = "string256")
	private java.lang.String retrieveKey;

	/**
	 *字段id
	 */
	@Column(id = "field_id", association = true)
	private FormField fieldId;

	/**
	 *是否模糊检索
	 */
	@Column(id = "is_like", datatype = "boolean")
	private java.lang.Boolean isLike;

	/**
	 *左边操作符
	 */
	@Column(id = "right_operate", datatype = "string256")
	private java.lang.String rightOperate;

	/**
	 *右边操作符
	 */
	@Column(id = "left_operate", datatype = "string256")
	private java.lang.String leftOperate;

	/**
	 *1
	 */
	@Column(id = "remark1", datatype = "string256")
	private java.lang.String remark1;

	/**
	 *2
	 */
	@Column(id = "remark2", datatype = "string256")
	private java.lang.String remark2;

	/**
	 *3
	 */
	@Column(id = "remark3", datatype = "string2000")
	private java.lang.String remark3;

	/**
	 *4
	 */
	@Column(id = "remark4", datatype = "smallint")
	private java.lang.Integer remark4;

	/**
	 * 设置${field.desc}
	 */
	public void setItemId(java.lang.String itemId) {
		this.itemId = itemId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getItemId() {
		return itemId;
	}

	/**
	 * 设置检索器id
	 */
	public void setRetrieveKey(java.lang.String retrieveKey) {
		this.retrieveKey = retrieveKey;
	}

	/**
	 * 获取检索器id
	 */
	public java.lang.String getRetrieveKey() {
		return retrieveKey;
	}

	/**
	 * 设置字段id
	 */
	public void setFieldId(FormField fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * 获取字段id
	 */
	public FormField getFieldId() {
		return fieldId;
	}

	/**
	 * 设置是否模糊检索
	 */
	public void setIsLike(java.lang.Boolean isLike) {
		this.isLike = isLike;
	}

	/**
	 * 获取是否模糊检索
	 */
	public java.lang.Boolean isIsLike() {
		return isLike;
	}

	/**
	 * 设置左边操作符
	 */
	public void setRightOperate(java.lang.String rightOperate) {
		this.rightOperate = rightOperate;
	}

	/**
	 * 获取左边操作符
	 */
	public java.lang.String getRightOperate() {
		return rightOperate;
	}

	/**
	 * 设置右边操作符
	 */
	public void setLeftOperate(java.lang.String leftOperate) {
		this.leftOperate = leftOperate;
	}

	/**
	 * 获取右边操作符
	 */
	public java.lang.String getLeftOperate() {
		return leftOperate;
	}

	/**
	 * 设置1
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取1
	 */
	public java.lang.String getRemark1() {
		return remark1;
	}

	/**
	 * 设置2
	 */
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取2
	 */
	public java.lang.String getRemark2() {
		return remark2;
	}

	/**
	 * 设置3
	 */
	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	/**
	 * 获取3
	 */
	public java.lang.String getRemark3() {
		return remark3;
	}

	/**
	 * 设置4
	 */
	public void setRemark4(java.lang.Integer remark4) {
		this.remark4 = remark4;
	}

	/**
	 * 获取4
	 */
	public java.lang.Integer getRemark4() {
		return remark4;
	}
}
