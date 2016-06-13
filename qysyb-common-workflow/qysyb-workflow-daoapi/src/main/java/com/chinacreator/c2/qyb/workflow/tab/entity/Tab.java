package com.chinacreator.c2.qyb.workflow.tab.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 动态表单Tab描述
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.tab.entity.Tab", table = "SERVICE_TAB", ds = "oracDB")
public class Tab implements Serializable {
	private static final long serialVersionUID = 1555811862544384L;
	@Column(id = "title", datatype = "string128")
	private java.lang.String title;

	@Column(id = "tab_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String tabId;

	@Column(id = "icon", datatype = "string128")
	private java.lang.String icon;

	@Column(id = "url", datatype = "string128")
	private java.lang.String url;

	@Column(id = "content", datatype = "string512")
	private java.lang.String content;

	@Column(id = "describe", datatype = "string256")
	private java.lang.String describe;

	/**
	 * 设置${field.desc}
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getTitle() {
		return title;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setTabId(java.lang.String tabId) {
		this.tabId = tabId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getTabId() {
		return tabId;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getIcon() {
		return icon;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getContent() {
		return content;
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
}
