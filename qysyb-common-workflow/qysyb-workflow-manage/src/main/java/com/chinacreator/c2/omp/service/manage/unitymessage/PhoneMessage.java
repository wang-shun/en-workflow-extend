package com.chinacreator.c2.omp.service.manage.unitymessage;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 短信消息信息
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.unitymessage.PhoneMessage", table = "TA_SEND_MESSAGE", ds = "oracDB")
public class PhoneMessage implements Serializable {
	private static final long serialVersionUID = 1695903381454848L;
	@Column(id = "oid", type = ColumnType.uuid, datatype = "int")
	private java.lang.Integer oid;

	@Column(id = "mobile_num", datatype = "string20")
	private java.lang.String mobileNum;

	@Column(id = "message_content", datatype = "string512")
	private java.lang.String messageContent;

	@Column(id = "send_state", datatype = "tinyint")
	private java.lang.Integer sendState;

	@Column(id = "create_date", datatype = "date")
	private java.sql.Date createDate;

	/**
	 * 设置${field.desc}
	 */
	public void setOid(java.lang.Integer oid) {
		this.oid = oid;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Integer getOid() {
		return oid;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setMobileNum(java.lang.String mobileNum) {
		this.mobileNum = mobileNum;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getMobileNum() {
		return mobileNum;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setMessageContent(java.lang.String messageContent) {
		this.messageContent = messageContent;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getMessageContent() {
		return messageContent;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setSendState(java.lang.Integer sendState) {
		this.sendState = sendState;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Integer getSendState() {
		return sendState;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setCreateDate(java.sql.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.sql.Date getCreateDate() {
		return createDate;
	}
}
