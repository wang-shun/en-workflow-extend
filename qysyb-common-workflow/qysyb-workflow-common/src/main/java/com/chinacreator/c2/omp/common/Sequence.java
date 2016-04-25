package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 自动编号
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.Sequence", table = "kcomp_sequence", ds = "newDS")
public class Sequence implements Serializable {
	private static final long serialVersionUID = 1363684815241216L;
	/**
	 *NAME
	 */
	@Column(id = "name", datatype = "string1024")
	private java.lang.String name;

	/**
	 *TYPE
	 */
	@Column(id = "type", datatype = "string256")
	private java.lang.String type;

	/**
	 *CURVALUE
	 */
	@Column(id = "curvalue", datatype = "string256")
	private java.lang.String curvalue;

	/**
	 *INCREMENT
	 */
	@Column(id = "increment", datatype = "int")
	private java.lang.Integer increment;

	/**
	 *ID
	 */
	@Column(id = "id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String id;

	/**
	 * 设置NAME
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 获取NAME
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * 设置TYPE
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * 获取TYPE
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 * 设置CURVALUE
	 */
	public void setCurvalue(java.lang.String curvalue) {
		this.curvalue = curvalue;
	}

	/**
	 * 获取CURVALUE
	 */
	public java.lang.String getCurvalue() {
		return curvalue;
	}

	/**
	 * 设置INCREMENT
	 */
	public void setIncrement(java.lang.Integer increment) {
		this.increment = increment;
	}

	/**
	 * 获取INCREMENT
	 */
	public java.lang.Integer getIncrement() {
		return increment;
	}

	/**
	 * 设置ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 获取ID
	 */
	public java.lang.String getId() {
		return id;
	}
}
