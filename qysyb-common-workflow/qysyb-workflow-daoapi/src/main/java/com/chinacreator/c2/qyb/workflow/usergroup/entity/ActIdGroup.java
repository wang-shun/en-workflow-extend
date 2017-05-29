package com.chinacreator.c2.qyb.workflow.usergroup.entity;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 工作流用户组
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.usergroup.entity.ActIdGroup", table = "ACT_ID_GROUP", ds = "oracDB", cache = false)
public class ActIdGroup {
	@Column(id = "id_", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String id;

	@Column(id = "rev_", datatype = "bigdouble")
	private java.lang.Double rev;

	@Column(id = "name_", datatype = "string128")
	private java.lang.String name;

	@Column(id = "type_", datatype = "char60")
	private java.lang.String type;

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
	 * 设置${field.desc}
	 */
	public void setRev(java.lang.Double rev) {
		this.rev = rev;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Double getRev() {
		return rev;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getType() {
		return type;
	}
}
