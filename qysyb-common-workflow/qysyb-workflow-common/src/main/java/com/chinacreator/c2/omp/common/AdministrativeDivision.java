package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 全国行政区域划分
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.administrativeDivision", table = "kcomp_config_xzqh", ds = "newDS")
public class AdministrativeDivision implements Serializable {
	private static final long serialVersionUID = 1367535918727168L;
	/**
	 *行政区域代码
	 */
	@Column(id = "code", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String code;

	/**
	 *行政区域名称
	 */
	@Column(id = "name", datatype = "string256")
	private java.lang.String name;

	/**
	 *行政区域全名
	 */
	@Column(id = "fullname", datatype = "string512")
	private java.lang.String fullname;

	/**
	 *上级编码
	 */
	@Column(id = "parent_code", datatype = "string64")
	private java.lang.String parentCode;

	/**
	 *备注字段
	 */
	@Column(id = "remark", datatype = "string512")
	private java.lang.String remark;

	/**
	 * 设置行政区域代码
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}

	/**
	 * 获取行政区域代码
	 */
	public java.lang.String getCode() {
		return code;
	}

	/**
	 * 设置行政区域名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 获取行政区域名称
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * 设置行政区域全名
	 */
	public void setFullname(java.lang.String fullname) {
		this.fullname = fullname;
	}

	/**
	 * 获取行政区域全名
	 */
	public java.lang.String getFullname() {
		return fullname;
	}

	/**
	 * 设置上级编码
	 */
	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * 获取上级编码
	 */
	public java.lang.String getParentCode() {
		return parentCode;
	}

	/**
	 * 设置备注字段
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/**
	 * 获取备注字段
	 */
	public java.lang.String getRemark() {
		return remark;
	}
}
