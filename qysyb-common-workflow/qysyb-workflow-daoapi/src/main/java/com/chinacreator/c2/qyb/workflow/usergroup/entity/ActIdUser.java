package com.chinacreator.c2.qyb.workflow.usergroup.entity;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 工作流用户
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.qyb.workflow.usergroup.entity.ActIdUser", table = "ACT_ID_USER", ds = "oracDB", cache = false)
public class ActIdUser {
	@Column(id = "id_", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String id;

	@Column(id = "rev_", datatype = "bigdouble")
	private java.lang.Double rev;

	@Column(id = "first_", datatype = "string256")
	private java.lang.String first;

	@Column(id = "last_", datatype = "string128")
	private java.lang.String last;

	@Column(id = "email_", datatype = "string128")
	private java.lang.String email;

	@Column(id = "pwd_", datatype = "string512")
	private java.lang.String pwd;

	@Column(id = "picture_id_", datatype = "string64")
	private java.lang.String pictureId;

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
	public void setFirst(java.lang.String first) {
		this.first = first;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getFirst() {
		return first;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setLast(java.lang.String last) {
		this.last = last;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getLast() {
		return last;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getEmail() {
		return email;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setPwd(java.lang.String pwd) {
		this.pwd = pwd;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getPwd() {
		return pwd;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setPictureId(java.lang.String pictureId) {
		this.pictureId = pictureId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getPictureId() {
		return pictureId;
	}
}
