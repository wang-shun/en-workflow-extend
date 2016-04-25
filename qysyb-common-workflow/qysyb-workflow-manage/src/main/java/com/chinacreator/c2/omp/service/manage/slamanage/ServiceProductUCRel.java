package com.chinacreator.c2.omp.service.manage.slamanage;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;
import com.chinacreator.c2.omp.service.manage.serviceproductmanage.ServiceProduct;

/**
 * 服务产品UC关联
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.slamanage.ServiceProductUCRel", table = "SERVICE_UC_SP_REL", ds = "oracDB")
public class ServiceProductUCRel implements Serializable {
	private static final long serialVersionUID = 1451125386592256L;
	@Column(id = "rel_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String relId;

	/**
	 *产品ID
	 */
	@Column(id = "product_id", association = true)
	private ServiceProduct productId;

	/**
	 *UC_ID
	 */
	@Column(id = "uc_id", association = true)
	private UnderpinContract ucId;

	/**
	 *描述
	 */
	@Column(id = "rel_describe", datatype = "string1024")
	private java.lang.String relDescribe;

	/**
	 *服务类型
	 */
	@Column(id = "rel_type_id", datatype = "string128")
	private java.lang.String relTypeId;

	/**
	 * 设置${field.desc}
	 */
	public void setRelId(java.lang.String relId) {
		this.relId = relId;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRelId() {
		return relId;
	}

	/**
	 * 设置产品ID
	 */
	public void setProductId(ServiceProduct productId) {
		this.productId = productId;
	}

	/**
	 * 获取产品ID
	 */
	public ServiceProduct getProductId() {
		return productId;
	}

	/**
	 * 设置UC_ID
	 */
	public void setUcId(UnderpinContract ucId) {
		this.ucId = ucId;
	}

	/**
	 * 获取UC_ID
	 */
	public UnderpinContract getUcId() {
		return ucId;
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
	 * 设置服务类型
	 */
	public void setRelTypeId(java.lang.String relTypeId) {
		this.relTypeId = relTypeId;
	}

	/**
	 * 获取服务类型
	 */
	public java.lang.String getRelTypeId() {
		return relTypeId;
	}
}
