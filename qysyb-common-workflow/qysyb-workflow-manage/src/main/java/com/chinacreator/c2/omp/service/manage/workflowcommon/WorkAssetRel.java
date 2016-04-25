package com.chinacreator.c2.omp.service.manage.workflowcommon;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 工单资产关联
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.WorkAssetRel", table = "kcomp_asset_work_rel", ds = "newDS")
public class WorkAssetRel implements Serializable {
	private static final long serialVersionUID = 1398941071228928L;
	/**
	 *
	 */
	@Column(id = "asset_work_rel_id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String assetWorkRelId;

	/**
	 *
	 */
	@Column(id = "asset_id", datatype = "string256")
	private java.lang.String assetId;

	/**
	 *
	 */
	@Column(id = "work_id", datatype = "string256")
	private java.lang.String workId;

	/**
	 *
	 */
	@Column(id = "work_type", datatype = "string256")
	private java.lang.String workType;

	/**
	 *
	 */
	@Column(id = "remark", datatype = "string256")
	private java.lang.String remark;

	/**
	 * 设置
	 */
	public void setAssetWorkRelId(java.lang.String assetWorkRelId) {
		this.assetWorkRelId = assetWorkRelId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getAssetWorkRelId() {
		return assetWorkRelId;
	}

	/**
	 * 设置
	 */
	public void setAssetId(java.lang.String assetId) {
		this.assetId = assetId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getAssetId() {
		return assetId;
	}

	/**
	 * 设置
	 */
	public void setWorkId(java.lang.String workId) {
		this.workId = workId;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkId() {
		return workId;
	}

	/**
	 * 设置
	 */
	public void setWorkType(java.lang.String workType) {
		this.workType = workType;
	}

	/**
	 * 获取
	 */
	public java.lang.String getWorkType() {
		return workType;
	}

	/**
	 * 设置
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/**
	 * 获取
	 */
	public java.lang.String getRemark() {
		return remark;
	}
}
