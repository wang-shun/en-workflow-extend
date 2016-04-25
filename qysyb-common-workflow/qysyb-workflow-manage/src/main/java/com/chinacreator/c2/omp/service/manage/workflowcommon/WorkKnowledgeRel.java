package com.chinacreator.c2.omp.service.manage.workflowcommon;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 工单知识关联
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.WorkKnowledgeRel", table = "SERVICE_WORK_KLG_REL", ds = "oracDB")
public class WorkKnowledgeRel implements Serializable {
	private static final long serialVersionUID = 1451066822197248L;
	/**
	 *ID
	 */
	@Column(id = "rel_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String relId;

	/**
	 *work ID
	 */
	@Column(id = "work_id", datatype = "string128")
	private java.lang.String workId;

	/**
	 *work ID
	 */
	@Column(id = "knowledge_id", datatype = "string128")
	private java.lang.String knowledgeId;

	/**
	 *描述
	 */
	@Column(id = "rel_describe", datatype = "string1024")
	private java.lang.String relDescribe;

	/**
	 *关联类型id
	 */
	@Column(id = "rel_type_id", datatype = "string128")
	private java.lang.String relTypeId;

	/**
	 * 设置ID
	 */
	public void setRelId(java.lang.String relId) {
		this.relId = relId;
	}

	/**
	 * 获取ID
	 */
	public java.lang.String getRelId() {
		return relId;
	}

	/**
	 * 设置work ID
	 */
	public void setWorkId(java.lang.String workId) {
		this.workId = workId;
	}

	/**
	 * 获取work ID
	 */
	public java.lang.String getWorkId() {
		return workId;
	}

	/**
	 * 设置work ID
	 */
	public void setKnowledgeId(java.lang.String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	/**
	 * 获取work ID
	 */
	public java.lang.String getKnowledgeId() {
		return knowledgeId;
	}

	/**
	 * 设置描述
	 */
	public void setRelDescribe(java.lang.String relDescribe) {
		this.relDescribe = relDescribe;
	}

	/**
	 * 获取描述
	 */
	public java.lang.String getRelDescribe() {
		return relDescribe;
	}

	/**
	 * 设置关联类型id
	 */
	public void setRelTypeId(java.lang.String relTypeId) {
		this.relTypeId = relTypeId;
	}

	/**
	 * 获取关联类型id
	 */
	public java.lang.String getRelTypeId() {
		return relTypeId;
	}
}
