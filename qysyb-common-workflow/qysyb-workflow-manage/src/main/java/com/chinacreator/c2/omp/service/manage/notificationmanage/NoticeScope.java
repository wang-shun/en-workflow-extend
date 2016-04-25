package com.chinacreator.c2.omp.service.manage.notificationmanage;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 通知范围表
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.notificationmanage.noticeScope", table = "KCOMP_NOTICE_SCOPE", ds = "oracDB")
public class NoticeScope implements Serializable {
	private static final long serialVersionUID = 1401597826514944L;
	/**
	 *֪ͨ��Χ���
	 */
	@Column(id = "notify_scope_xh", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String notifyScopeXh;

	/**
	 *֪ͨ���
	 */
	@Column(id = "notify_xh", datatype = "string128")
	private java.lang.String notifyXh;

	/**
	 *֪ͨ����ID
	 */
	@Column(id = "notify_org_id", datatype = "string128")
	private java.lang.String notifyOrgId;

	/**
	 *֪ͨ��ID
	 */
	@Column(id = "notify_user_id", datatype = "string128")
	private java.lang.String notifyUserId;

	/**
	 * 设置֪ͨ��Χ���
	 */
	public void setNotifyScopeXh(java.lang.String notifyScopeXh) {
		this.notifyScopeXh = notifyScopeXh;
	}

	/**
	 * 获取֪ͨ��Χ���
	 */
	public java.lang.String getNotifyScopeXh() {
		return notifyScopeXh;
	}

	/**
	 * 设置֪ͨ���
	 */
	public void setNotifyXh(java.lang.String notifyXh) {
		this.notifyXh = notifyXh;
	}

	/**
	 * 获取֪ͨ���
	 */
	public java.lang.String getNotifyXh() {
		return notifyXh;
	}

	/**
	 * 设置֪ͨ����ID
	 */
	public void setNotifyOrgId(java.lang.String notifyOrgId) {
		this.notifyOrgId = notifyOrgId;
	}

	/**
	 * 获取֪ͨ����ID
	 */
	public java.lang.String getNotifyOrgId() {
		return notifyOrgId;
	}

	/**
	 * 设置֪ͨ��ID
	 */
	public void setNotifyUserId(java.lang.String notifyUserId) {
		this.notifyUserId = notifyUserId;
	}

	/**
	 * 获取֪ͨ��ID
	 */
	public java.lang.String getNotifyUserId() {
		return notifyUserId;
	}
}
