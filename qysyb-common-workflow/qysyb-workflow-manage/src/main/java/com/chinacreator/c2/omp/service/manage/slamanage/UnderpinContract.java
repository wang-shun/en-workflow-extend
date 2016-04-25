package com.chinacreator.c2.omp.service.manage.slamanage;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 服务支撑合同
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.slamanage.UnderpinContract", table = "SERVICE_UPDERPIN_CONTRACT", ds = "oracDB")
public class UnderpinContract implements Serializable {
	private static final long serialVersionUID = 1451102860279808L;
	/**
	 *ID
	 */
	@Column(id = "uc_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String ucId;

	/**
	 *UC名称
	 */
	@Column(id = "uc_name", datatype = "string128")
	private java.lang.String ucName;

	/**
	 *合同名称
	 */
	@Column(id = "contract_name", datatype = "string128")
	private java.lang.String contractName;

	/**
	 *供应商名称
	 */
	@Column(id = "supplier_name", datatype = "string1024")
	private java.lang.String supplierName;

	/**
	 *服务有效期开始时间
	 */
	@Column(id = "service_valid_starttime", datatype = "date")
	private java.sql.Date serviceValidStarttime;

	/**
	 *服务有效期结束时间
	 */
	@Column(id = "service_valid_endtime", datatype = "date")
	private java.sql.Date serviceValidEndtime;

	/**
	 *服务时间一周几天
	 */
	@Column(id = "service_time_day_of_week", datatype = "string128")
	private java.lang.String serviceTimeDayOfWeek;

	/**
	 *服务时间一天几小时
	 */
	@Column(id = "service_time_time_of_day", datatype = "string128")
	private java.lang.String serviceTimeTimeOfDay;

	/**
	 *方式
	 */
	@Column(id = "service_type", datatype = "string128")
	private java.lang.String serviceType;

	/**
	 * 设置ID
	 */
	public void setUcId(java.lang.String ucId) {
		this.ucId = ucId;
	}

	/**
	 * 获取ID
	 */
	public java.lang.String getUcId() {
		return ucId;
	}

	/**
	 * 设置UC名称
	 */
	public void setUcName(java.lang.String ucName) {
		this.ucName = ucName;
	}

	/**
	 * 获取UC名称
	 */
	public java.lang.String getUcName() {
		return ucName;
	}

	/**
	 * 设置合同名称
	 */
	public void setContractName(java.lang.String contractName) {
		this.contractName = contractName;
	}

	/**
	 * 获取合同名称
	 */
	public java.lang.String getContractName() {
		return contractName;
	}

	/**
	 * 设置供应商名称
	 */
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * 获取供应商名称
	 */
	public java.lang.String getSupplierName() {
		return supplierName;
	}

	/**
	 * 设置服务有效期开始时间
	 */
	public void setServiceValidStarttime(java.sql.Date serviceValidStarttime) {
		this.serviceValidStarttime = serviceValidStarttime;
	}

	/**
	 * 获取服务有效期开始时间
	 */
	public java.sql.Date getServiceValidStarttime() {
		return serviceValidStarttime;
	}

	/**
	 * 设置服务有效期结束时间
	 */
	public void setServiceValidEndtime(java.sql.Date serviceValidEndtime) {
		this.serviceValidEndtime = serviceValidEndtime;
	}

	/**
	 * 获取服务有效期结束时间
	 */
	public java.sql.Date getServiceValidEndtime() {
		return serviceValidEndtime;
	}

	/**
	 * 设置服务时间一周几天
	 */
	public void setServiceTimeDayOfWeek(java.lang.String serviceTimeDayOfWeek) {
		this.serviceTimeDayOfWeek = serviceTimeDayOfWeek;
	}

	/**
	 * 获取服务时间一周几天
	 */
	public java.lang.String getServiceTimeDayOfWeek() {
		return serviceTimeDayOfWeek;
	}

	/**
	 * 设置服务时间一天几小时
	 */
	public void setServiceTimeTimeOfDay(java.lang.String serviceTimeTimeOfDay) {
		this.serviceTimeTimeOfDay = serviceTimeTimeOfDay;
	}

	/**
	 * 获取服务时间一天几小时
	 */
	public java.lang.String getServiceTimeTimeOfDay() {
		return serviceTimeTimeOfDay;
	}

	/**
	 * 设置方式
	 */
	public void setServiceType(java.lang.String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * 获取方式
	 */
	public java.lang.String getServiceType() {
		return serviceType;
	}
}
