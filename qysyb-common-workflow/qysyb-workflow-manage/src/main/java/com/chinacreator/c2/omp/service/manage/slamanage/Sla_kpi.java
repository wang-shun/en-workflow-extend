package com.chinacreator.c2.omp.service.manage.slamanage;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * Slakpi项
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.slamanage.Sla_kpi", table = "SERVICE_SLA_KPI", ds = "oracDB")
public class Sla_kpi implements Serializable {
	private static final long serialVersionUID = 1398688273350656L;
	/**
	 *ID
	 */
	@Column(id = "kpi_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String kpiId;

	/**
	 *kpi编码
	 */
	@Column(id = "kpi_no", datatype = "string128")
	private java.lang.String kpiNo;

	/**
	 *ָName
	 */
	@Column(id = "kpi_name", datatype = "string256")
	private java.lang.String kpiName;

	/**
	 *备注
	 */
	@Column(id = "remark", datatype = "string1024")
	private java.lang.String remark;

	/**
	 *默认值
	 */
	@Column(id = "default_value", datatype = "string256")
	private java.lang.String defaultValue;

	/**
	 * 设置ID
	 */
	public void setKpiId(java.lang.String kpiId) {
		this.kpiId = kpiId;
	}

	/**
	 * 获取ID
	 */
	public java.lang.String getKpiId() {
		return kpiId;
	}

	/**
	 * 设置kpi编码
	 */
	public void setKpiNo(java.lang.String kpiNo) {
		this.kpiNo = kpiNo;
	}

	/**
	 * 获取kpi编码
	 */
	public java.lang.String getKpiNo() {
		return kpiNo;
	}

	/**
	 * 设置ָName
	 */
	public void setKpiName(java.lang.String kpiName) {
		this.kpiName = kpiName;
	}

	/**
	 * 获取ָName
	 */
	public java.lang.String getKpiName() {
		return kpiName;
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

	/**
	 * 设置默认值
	 */
	public void setDefaultValue(java.lang.String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * 获取默认值
	 */
	public java.lang.String getDefaultValue() {
		return defaultValue;
	}
}
