package com.chinacreator.c2.jp.car.manage.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 驾驶员信息
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.jp.car.manage.entity.driver", table = "TA_OFFICE_DRIVERINFO", ds = "oracDB")
public class Driver implements Serializable {
	private static final long serialVersionUID = 1724301666385920L;
	/**
	 *主键
	 */
	@Column(id = "oid", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String oid;

	/**
	 *驾驶证号
	 */
	@Column(id = "driver_number", datatype = "string64")
	private java.lang.String driverNumber;

	/**
	 *驾驶员姓名
	 */
	@Column(id = "driver_name", datatype = "string64")
	private java.lang.String driverName;

	/**
	 *驾驶员性别
	 */
	@Column(id = "driver_sex", datatype = "string64")
	private java.lang.String driverSex;

	/**
	 *驾驶员身份证
	 */
	@Column(id = "driver_idcard", datatype = "string64")
	private java.lang.String driverIdcard;

	/**
	 *驾驶员电话
	 */
	@Column(id = "driver_mobiletel", datatype = "string64")
	private java.lang.String driverMobiletel;

	/**
	 *工作经历
	 */
	@Column(id = "work_experience", datatype = "string128")
	private java.lang.String workExperience;

	/**
	 * 设置主键
	 */
	public void setOid(java.lang.String oid) {
		this.oid = oid;
	}

	/**
	 * 获取主键
	 */
	public java.lang.String getOid() {
		return oid;
	}

	/**
	 * 设置驾驶证号
	 */
	public void setDriverNumber(java.lang.String driverNumber) {
		this.driverNumber = driverNumber;
	}

	/**
	 * 获取驾驶证号
	 */
	public java.lang.String getDriverNumber() {
		return driverNumber;
	}

	/**
	 * 设置驾驶员姓名
	 */
	public void setDriverName(java.lang.String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取驾驶员姓名
	 */
	public java.lang.String getDriverName() {
		return driverName;
	}

	/**
	 * 设置驾驶员性别
	 */
	public void setDriverSex(java.lang.String driverSex) {
		this.driverSex = driverSex;
	}

	/**
	 * 获取驾驶员性别
	 */
	public java.lang.String getDriverSex() {
		return driverSex;
	}

	/**
	 * 设置驾驶员身份证
	 */
	public void setDriverIdcard(java.lang.String driverIdcard) {
		this.driverIdcard = driverIdcard;
	}

	/**
	 * 获取驾驶员身份证
	 */
	public java.lang.String getDriverIdcard() {
		return driverIdcard;
	}

	/**
	 * 设置驾驶员电话
	 */
	public void setDriverMobiletel(java.lang.String driverMobiletel) {
		this.driverMobiletel = driverMobiletel;
	}

	/**
	 * 获取驾驶员电话
	 */
	public java.lang.String getDriverMobiletel() {
		return driverMobiletel;
	}

	/**
	 * 设置工作经历
	 */
	public void setWorkExperience(java.lang.String workExperience) {
		this.workExperience = workExperience;
	}

	/**
	 * 获取工作经历
	 */
	public java.lang.String getWorkExperience() {
		return workExperience;
	}
}
