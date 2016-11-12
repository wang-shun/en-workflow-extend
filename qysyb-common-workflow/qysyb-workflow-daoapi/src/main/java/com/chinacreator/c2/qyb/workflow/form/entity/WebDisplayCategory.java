package com.chinacreator.c2.qyb.workflow.form.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 前端控件类型
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.form.entity.WebDisplayCategory",sn="webdisplaycategory", table = "WORKFLOW_WEBDISPLAY_CATEGORY", ds = "oracDB")
public class WebDisplayCategory implements Serializable {
	private static final long serialVersionUID = 1625214065623040L;
	@Column(id = "id_", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String id;

	/**
	 *编码
	 */
	@Column(id = "category_no", datatype = "string128")
	private java.lang.String categoryNo;

	/**
	 *名字
	 */
	@Column(id = "category_name", datatype = "string128")
	private java.lang.String categoryName;

	/**
	 *url
	 */
	@Column(id = "url", datatype = "string256")
	private java.lang.String url;

	@Column(id = "describe", datatype = "string1024")
	private java.lang.String describe;

	@Column(id = "remark1", datatype = "boolean")
	private java.lang.Boolean remark1;

	@Column(id = "remark2", datatype = "string256")
	private java.lang.String remark2;

	/**
	 *手机端显示资源路径
	 */
	@Column(id = "mobile_url", datatype = "string256")
	private java.lang.String mobileUrl;

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
	 * 设置编码
	 */
	public void setCategoryNo(java.lang.String categoryNo) {
		this.categoryNo = categoryNo;
	}

	/**
	 * 获取编码
	 */
	public java.lang.String getCategoryNo() {
		return categoryNo;
	}

	/**
	 * 设置名字
	 */
	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * 获取名字
	 */
	public java.lang.String getCategoryName() {
		return categoryName;
	}

	/**
	 * 设置url
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/**
	 * 获取url
	 */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setDescribe(java.lang.String describe) {
		this.describe = describe;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getDescribe() {
		return describe;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark1(java.lang.Boolean remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Boolean isRemark1() {
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
	 * 设置手机端显示资源路径
	 */
	public void setMobileUrl(java.lang.String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}

	/**
	 * 获取手机端显示资源路径
	 */
	public java.lang.String getMobileUrl() {
		return mobileUrl;
	}
}
