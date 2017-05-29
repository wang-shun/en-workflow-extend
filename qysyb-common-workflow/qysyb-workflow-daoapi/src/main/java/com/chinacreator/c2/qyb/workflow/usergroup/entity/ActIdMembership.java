package com.chinacreator.c2.qyb.workflow.usergroup.entity;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 组用户关系表
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.usergroup.entity.ActIdMembership", table = "ACT_ID_MEMBERSHIP", ds = "oracDB", cache = false)
public class ActIdMembership {
	@Column(id = "user_id_", association = true)
	private ActIdUser userId;

	@Column(id = "group_id_", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String groupId;

	/**
	 * 设置${field.desc}
	 */
	public void setUserId(ActIdUser userId) {
		this.userId = userId;
	}

	/**
	 * 获取${field.desc}
	 */
	public ActIdUser getUserId() {
		return userId;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setGroupId(java.lang.String groupId) {
		this.groupId = groupId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getGroupId() {
		return groupId;
	}
}
