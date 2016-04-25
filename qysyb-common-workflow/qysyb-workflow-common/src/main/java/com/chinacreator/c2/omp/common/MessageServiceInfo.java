package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 消息服务实体
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.MessageServiceInfo", table = "td_monitor_service", ds = "newDS")
public class MessageServiceInfo implements Serializable {
	private static final long serialVersionUID = 1482440280621056L;
	/**
	 *消息服务id
	 */
	@Column(id = "id", type = ColumnType.uuid, datatype = "char20")
	private java.lang.String id;

	/**
	 *消息服务名称
	 */
	@Column(id = "name", datatype = "string256")
	private java.lang.String name;

	/**
	 *消息服务分类id
	 */
	@Column(id = "category_id", datatype = "char20")
	private java.lang.String categoryId;

	/**
	 *描述
	 */
	@Column(id = "description", datatype = "string1536")
	private java.lang.String description;

	/**
	 *url
	 */
	@Column(id = "url", datatype = "string512")
	private java.lang.String url;

	/**
	 *推送方式
	 */
	@Column(id = "method", datatype = "string128")
	private java.lang.String method;

	/**
	 *消息服务编码
	 */
	@Column(id = "service_code", datatype = "string64")
	private java.lang.String serviceCode;

	/**
	 * 设置消息服务id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 获取消息服务id
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * 设置消息服务名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 获取消息服务名称
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * 设置消息服务分类id
	 */
	public void setCategoryId(java.lang.String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 获取消息服务分类id
	 */
	public java.lang.String getCategoryId() {
		return categoryId;
	}

	/**
	 * 设置描述
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 * 获取描述
	 */
	public java.lang.String getDescription() {
		return description;
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
	 * 设置推送方式
	 */
	public void setMethod(java.lang.String method) {
		this.method = method;
	}

	/**
	 * 获取推送方式
	 */
	public java.lang.String getMethod() {
		return method;
	}

	/**
	 * 设置消息服务编码
	 */
	public void setServiceCode(java.lang.String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * 获取消息服务编码
	 */
	public java.lang.String getServiceCode() {
		return serviceCode;
	}
}
