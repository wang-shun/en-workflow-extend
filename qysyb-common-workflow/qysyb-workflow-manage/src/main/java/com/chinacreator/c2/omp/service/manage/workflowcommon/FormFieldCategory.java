package com.chinacreator.c2.omp.service.manage.workflowcommon;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 字段分类
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.FormFieldCategory", table = "kcomp_workflow_form_field_category", ds = "newDS")
public class FormFieldCategory implements Serializable {
	private static final long serialVersionUID = 1428653025296384L;
	/**
	 *
	 */
	@Column(id = "category_id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String categoryId;

	/**
	 *字段分类编码
	 */
	@Column(id = "category_no", datatype = "string256")
	private java.lang.String categoryNo;

	/**
	 *字段分类名称
	 */
	@Column(id = "category_name", datatype = "string512")
	private java.lang.String categoryName;

	/**
	 *字段分类描述
	 */
	@Column(id = "category_describe", datatype = "string1536")
	private java.lang.String categoryDescribe;

	/**
	 * 设置
	 */
	public void setCategoryId(java.lang.String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getCategoryId() {
		return categoryId;
	}

	/**
	 * 设置字段分类编码
	 */
	public void setCategoryNo(java.lang.String categoryNo) {
		this.categoryNo = categoryNo;
	}

	/**
	 * 获取字段分类编码
	 */
	public java.lang.String getCategoryNo() {
		return categoryNo;
	}

	/**
	 * 设置字段分类名称
	 */
	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * 获取字段分类名称
	 */
	public java.lang.String getCategoryName() {
		return categoryName;
	}

	/**
	 * 设置字段分类描述
	 */
	public void setCategoryDescribe(java.lang.String categoryDescribe) {
		this.categoryDescribe = categoryDescribe;
	}

	/**
	 * 获取字段分类描述
	 */
	public java.lang.String getCategoryDescribe() {
		return categoryDescribe;
	}
}
