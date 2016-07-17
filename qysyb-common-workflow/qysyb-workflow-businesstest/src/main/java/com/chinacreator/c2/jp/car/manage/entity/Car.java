package com.chinacreator.c2.jp.car.manage.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 车辆信息
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.jp.car.manage.entity.car", table = "TA_OFFICE_CARINFO", ds = "oracDB")
public class Car implements Serializable {
	private static final long serialVersionUID = 1724299661705216L;
	/**
	 *主键
	 */
	@Column(id = "oid", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String oid;

	/**
	 *车牌号
	 */
	@Column(id = "car_number", datatype = "string64")
	private java.lang.String carNumber;

	/**
	 *车辆品牌
	 */
	@Column(id = "car_name", datatype = "string64")
	private java.lang.String carName;

	/**
	 *车辆类型
	 */
	@Column(id = "car_type", datatype = "string64")
	private java.lang.String carType;

	/**
	 *购买时间
	 */
	@Column(id = "buy_date", datatype = "date")
	private java.sql.Date buyDate;

	/**
	 *驾驶人id
	 */
	@Column(id = "driver", datatype = "string64")
	private java.lang.String driver;

	/**
	 *驾驶人姓名
	 */
	@Column(id = "drivername", datatype = "string64")
	private java.lang.String drivername;

	/**
	 *车辆状态
	 */
	@Column(id = "status", datatype = "string64")
	private java.lang.String status;

	/**
	 *录入时间
	 */
	@Column(id = "create_date", datatype = "date")
	private java.sql.Date createDate;

	/**
	 *录入人
	 */
	@Column(id = "create_user", datatype = "string64")
	private java.lang.String createUser;

	/**
	 *备注
	 */
	@Column(id = "remark", datatype = "string5")
	private java.lang.String remark;

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
	 * 设置车牌号
	 */
	public void setCarNumber(java.lang.String carNumber) {
		this.carNumber = carNumber;
	}

	/**
	 * 获取车牌号
	 */
	public java.lang.String getCarNumber() {
		return carNumber;
	}

	/**
	 * 设置车辆品牌
	 */
	public void setCarName(java.lang.String carName) {
		this.carName = carName;
	}

	/**
	 * 获取车辆品牌
	 */
	public java.lang.String getCarName() {
		return carName;
	}

	/**
	 * 设置车辆类型
	 */
	public void setCarType(java.lang.String carType) {
		this.carType = carType;
	}

	/**
	 * 获取车辆类型
	 */
	public java.lang.String getCarType() {
		return carType;
	}

	/**
	 * 设置购买时间
	 */
	public void setBuyDate(java.sql.Date buyDate) {
		this.buyDate = buyDate;
	}

	/**
	 * 获取购买时间
	 */
	public java.sql.Date getBuyDate() {
		return buyDate;
	}

	/**
	 * 设置驾驶人id
	 */
	public void setDriver(java.lang.String driver) {
		this.driver = driver;
	}

	/**
	 * 获取驾驶人id
	 */
	public java.lang.String getDriver() {
		return driver;
	}

	/**
	 * 设置驾驶人姓名
	 */
	public void setDrivername(java.lang.String drivername) {
		this.drivername = drivername;
	}

	/**
	 * 获取驾驶人姓名
	 */
	public java.lang.String getDrivername() {
		return drivername;
	}

	/**
	 * 设置车辆状态
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 获取车辆状态
	 */
	public java.lang.String getStatus() {
		return status;
	}

	/**
	 * 设置录入时间
	 */
	public void setCreateDate(java.sql.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取录入时间
	 */
	public java.sql.Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置录入人
	 */
	public void setCreateUser(java.lang.String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 获取录入人
	 */
	public java.lang.String getCreateUser() {
		return createUser;
	}

	/**
	 * 设置备注
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/**
	 * 获取备注
	 */
	public java.lang.String getRemark() {
		return remark;
	}
}
