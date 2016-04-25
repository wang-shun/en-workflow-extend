package com.chinacreator.c2.omp.service.manage.slamanage;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * sla和kpi关联
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.slamanage.Sla_kpi_rel", table = "SERVICE_SLA_KPI_REL", ds = "oracDB")
public class Sla_kpi_rel implements Serializable {
	private static final long serialVersionUID = 1398688274153472L;
	/**
	 *����ID
	 */
	@Column(id = "rel_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String relId;

	/**
	 *SLA ID
	 */
	@Column(id = "service_agreement_id", datatype = "string256")
	private java.lang.String serviceAgreementId;

	/**
	 *KPI项ID
	 */
	@Column(id = "kpi_id", association = true)
	private Sla_kpi kpiId;

	/**
	 *KPI值
	 */
	@Column(id = "vlaue", datatype = "string256")
	private java.lang.String vlaue;

	/**
	 * 设置����ID
	 */
	public void setRelId(java.lang.String relId) {
		this.relId = relId;
	}

	/**
	 * 获取����ID
	 */
	public java.lang.String getRelId() {
		return relId;
	}

	/**
	 * 设置SLA ID
	 */
	public void setServiceAgreementId(java.lang.String serviceAgreementId) {
		this.serviceAgreementId = serviceAgreementId;
	}

	/**
	 * 获取SLA ID
	 */
	public java.lang.String getServiceAgreementId() {
		return serviceAgreementId;
	}

	/**
	 * 设置KPI项ID
	 */
	public void setKpiId(Sla_kpi kpiId) {
		this.kpiId = kpiId;
	}

	/**
	 * 获取KPI项ID
	 */
	public Sla_kpi getKpiId() {
		return kpiId;
	}

	/**
	 * 设置KPI值
	 */
	public void setVlaue(java.lang.String vlaue) {
		this.vlaue = vlaue;
	}

	/**
	 * 获取KPI值
	 */
	public java.lang.String getVlaue() {
		return vlaue;
	}
}
