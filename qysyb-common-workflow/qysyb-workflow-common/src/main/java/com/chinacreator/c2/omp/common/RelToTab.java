package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 业务key关联到动态tab
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.RelToTab", table = "REL_TAB", ds = "oracDB")
public class RelToTab implements Serializable {
	private static final long serialVersionUID = 1555819614208000L;
	/**
	 *主键
	 */
	@Column(id = "rel_id", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String relId;

	/**
	 *动态tab 页签ID
	 */
	@Column(id = "tab", association = true)
	private Tab tab;

	@Column(id = "remark1", datatype = "string64")
	private java.lang.String remark1;

	@Column(id = "remark2", datatype = "long")
	private java.lang.Long remark2;

	/**
	 *关联动态tab页的ID
	 */
	@Column(id = "business_key", datatype = "string64")
	private java.lang.String businessKey;

	/**
	 * 设置主键
	 */
	public void setRelId(java.lang.String relId) {
		this.relId = relId;
	}

	/**
	 * 获取主键
	 */
	public java.lang.String getRelId() {
		return relId;
	}

	/**
	 * 设置动态tab 页签ID
	 */
	public void setTab(Tab tab) {
		this.tab = tab;
	}

	/**
	 * 获取动态tab 页签ID
	 */
	public Tab getTab() {
		return tab;
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
	public void setRemark2(java.lang.Long remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Long getRemark2() {
		return remark2;
	}

	/**
	 * 设置关联动态tab页的ID
	 */
	public void setBusinessKey(java.lang.String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 获取关联动态tab页的ID
	 */
	public java.lang.String getBusinessKey() {
		return businessKey;
	}
}
