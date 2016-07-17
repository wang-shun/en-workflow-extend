package com.chinacreator.c2.jp.car.apply.entity;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 车辆申请表
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.jp.car.apply.entity.carapply", table = "TA_OFFICE_USECAR_APPLY", ds = "oracDB")
public class Carapply implements Serializable {
	private static final long serialVersionUID = 1724535108091904L;
	/**
	 *主键
	 */
	@Column(id = "oid", type = ColumnType.uuid, datatype = "string64")
	private java.lang.String oid;

	/**
	 *申请部门id
	 */
	@Column(id = "apply_dept_id", datatype = "string64")
	private java.lang.String applyDeptId;

	/**
	 *申请人
	 */
	@Column(id = "apply_user_name", datatype = "string64")
	private java.lang.String applyUserName;

	/**
	 *申请时间
	 */
	@Column(id = "apply_date", datatype = "long")
	private java.lang.Long applyDate;

	/**
	 *出发地点
	 */
	@Column(id = "start_place", datatype = "string128")
	private java.lang.String startPlace;

	/**
	 *返回地点
	 */
	@Column(id = "arrived_place", datatype = "string128")
	private java.lang.String arrivedPlace;

	/**
	 *出发时间
	 */
	@Column(id = "start_date", datatype = "long")
	private java.lang.Long startDate;

	/**
	 *乘车人姓名
	 */
	@Column(id = "passengers_name", datatype = "string64")
	private java.lang.String passengersName;

	/**
	 *乘车人数量
	 */
	@Column(id = "passenger_number", datatype = "tinyint")
	private java.lang.Integer passengerNumber;

	/**
	 *联系人
	 */
	@Column(id = "contact_name", datatype = "string64")
	private java.lang.String contactName;

	/**
	 *联系人电话
	 */
	@Column(id = "contact_phone", datatype = "string64")
	private java.lang.String contactPhone;

	/**
	 *要求到达时间
	 */
	@Column(id = "require_arrived_date", datatype = "long")
	private java.lang.Long requireArrivedDate;

	/**
	 *用车时长
	 */
	@Column(id = "usecar_hours", datatype = "string10")
	private java.lang.String usecarHours;

	/**
	 *申请事由
	 */
	@Column(id = "apply_reason", datatype = "string256")
	private java.lang.String applyReason;

	/**
	 *车辆id
	 */
	@Column(id = "car_id", datatype = "string64")
	private java.lang.String carId;

	/**
	 *驾驶员id
	 */
	@Column(id = "driver_id", datatype = "string64")
	private java.lang.String driverId;

	/**
	 *返回时间
	 */
	@Column(id = "back_time", datatype = "long")
	private java.lang.Long backTime;

	/**
	 *出发公里数
	 */
	@Column(id = "start_km", datatype = "int")
	private java.lang.Integer startKm;

	/**
	 *返回公里数
	 */
	@Column(id = "back_km", datatype = "int")
	private java.lang.Integer backKm;

	/**
	 *加油金额
	 */
	@Column(id = "refuel_money", datatype = "int")
	private java.lang.Integer refuelMoney;

	/**
	 *关联流程实例id
	 */
	@Column(id = "business_key", datatype = "string64")
	private java.lang.String businessKey;

	/**
	 *申请部门名称
	 */
	@Column(id = "apply_dept_name", datatype = "string64")
	private java.lang.String applyDeptName;

	/**
	 *申请人ID
	 */
	@Column(id = "apply_user_id", datatype = "string64")
	private java.lang.String applyUserId;

	/**
	 *状态
	 */
	@Column(id = "status", datatype = "string10")
	private java.lang.String status;

	@Column(id = "driver_name", datatype = "string64")
	private java.lang.String driverName;

	@Column(id = "car_num", datatype = "string64")
	private java.lang.String carNum;

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
	 * 设置申请部门id
	 */
	public void setApplyDeptId(java.lang.String applyDeptId) {
		this.applyDeptId = applyDeptId;
	}

	/**
	 * 获取申请部门id
	 */
	public java.lang.String getApplyDeptId() {
		return applyDeptId;
	}

	/**
	 * 设置申请人
	 */
	public void setApplyUserName(java.lang.String applyUserName) {
		this.applyUserName = applyUserName;
	}

	/**
	 * 获取申请人
	 */
	public java.lang.String getApplyUserName() {
		return applyUserName;
	}

	/**
	 * 设置申请时间
	 */
	public void setApplyDate(java.lang.Long applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * 获取申请时间
	 */
	public java.lang.Long getApplyDate() {
		return applyDate;
	}

	/**
	 * 设置出发地点
	 */
	public void setStartPlace(java.lang.String startPlace) {
		this.startPlace = startPlace;
	}

	/**
	 * 获取出发地点
	 */
	public java.lang.String getStartPlace() {
		return startPlace;
	}

	/**
	 * 设置返回地点
	 */
	public void setArrivedPlace(java.lang.String arrivedPlace) {
		this.arrivedPlace = arrivedPlace;
	}

	/**
	 * 获取返回地点
	 */
	public java.lang.String getArrivedPlace() {
		return arrivedPlace;
	}

	/**
	 * 设置出发时间
	 */
	public void setStartDate(java.lang.Long startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取出发时间
	 */
	public java.lang.Long getStartDate() {
		return startDate;
	}

	/**
	 * 设置乘车人姓名
	 */
	public void setPassengersName(java.lang.String passengersName) {
		this.passengersName = passengersName;
	}

	/**
	 * 获取乘车人姓名
	 */
	public java.lang.String getPassengersName() {
		return passengersName;
	}

	/**
	 * 设置乘车人数量
	 */
	public void setPassengerNumber(java.lang.Integer passengerNumber) {
		this.passengerNumber = passengerNumber;
	}

	/**
	 * 获取乘车人数量
	 */
	public java.lang.Integer getPassengerNumber() {
		return passengerNumber;
	}

	/**
	 * 设置联系人
	 */
	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}

	/**
	 * 获取联系人
	 */
	public java.lang.String getContactName() {
		return contactName;
	}

	/**
	 * 设置联系人电话
	 */
	public void setContactPhone(java.lang.String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * 获取联系人电话
	 */
	public java.lang.String getContactPhone() {
		return contactPhone;
	}

	/**
	 * 设置要求到达时间
	 */
	public void setRequireArrivedDate(java.lang.Long requireArrivedDate) {
		this.requireArrivedDate = requireArrivedDate;
	}

	/**
	 * 获取要求到达时间
	 */
	public java.lang.Long getRequireArrivedDate() {
		return requireArrivedDate;
	}

	/**
	 * 设置用车时长
	 */
	public void setUsecarHours(java.lang.String usecarHours) {
		this.usecarHours = usecarHours;
	}

	/**
	 * 获取用车时长
	 */
	public java.lang.String getUsecarHours() {
		return usecarHours;
	}

	/**
	 * 设置申请事由
	 */
	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}

	/**
	 * 获取申请事由
	 */
	public java.lang.String getApplyReason() {
		return applyReason;
	}

	/**
	 * 设置车辆id
	 */
	public void setCarId(java.lang.String carId) {
		this.carId = carId;
	}

	/**
	 * 获取车辆id
	 */
	public java.lang.String getCarId() {
		return carId;
	}

	/**
	 * 设置驾驶员id
	 */
	public void setDriverId(java.lang.String driverId) {
		this.driverId = driverId;
	}

	/**
	 * 获取驾驶员id
	 */
	public java.lang.String getDriverId() {
		return driverId;
	}

	/**
	 * 设置返回时间
	 */
	public void setBackTime(java.lang.Long backTime) {
		this.backTime = backTime;
	}

	/**
	 * 获取返回时间
	 */
	public java.lang.Long getBackTime() {
		return backTime;
	}

	/**
	 * 设置出发公里数
	 */
	public void setStartKm(java.lang.Integer startKm) {
		this.startKm = startKm;
	}

	/**
	 * 获取出发公里数
	 */
	public java.lang.Integer getStartKm() {
		return startKm;
	}

	/**
	 * 设置返回公里数
	 */
	public void setBackKm(java.lang.Integer backKm) {
		this.backKm = backKm;
	}

	/**
	 * 获取返回公里数
	 */
	public java.lang.Integer getBackKm() {
		return backKm;
	}

	/**
	 * 设置加油金额
	 */
	public void setRefuelMoney(java.lang.Integer refuelMoney) {
		this.refuelMoney = refuelMoney;
	}

	/**
	 * 获取加油金额
	 */
	public java.lang.Integer getRefuelMoney() {
		return refuelMoney;
	}

	/**
	 * 设置关联流程实例id
	 */
	public void setBusinessKey(java.lang.String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 获取关联流程实例id
	 */
	public java.lang.String getBusinessKey() {
		return businessKey;
	}

	/**
	 * 设置申请部门名称
	 */
	public void setApplyDeptName(java.lang.String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}

	/**
	 * 获取申请部门名称
	 */
	public java.lang.String getApplyDeptName() {
		return applyDeptName;
	}

	/**
	 * 设置申请人ID
	 */
	public void setApplyUserId(java.lang.String applyUserId) {
		this.applyUserId = applyUserId;
	}

	/**
	 * 获取申请人ID
	 */
	public java.lang.String getApplyUserId() {
		return applyUserId;
	}

	/**
	 * 设置状态
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 获取状态
	 */
	public java.lang.String getStatus() {
		return status;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setDriverName(java.lang.String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getDriverName() {
		return driverName;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setCarNum(java.lang.String carNum) {
		this.carNum = carNum;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getCarNum() {
		return carNum;
	}
}
