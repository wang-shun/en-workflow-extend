package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 数据字典信息
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.DictDataInfo", table = "td_sm_dictdata", ds = "newDS")
public class DictDataInfo implements Serializable {
	private static final long serialVersionUID = 1451360007684096L;
	/**
	 *字典ID
	 */
	@Column(id = "dictdata_id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String dictdataId;

	/**
	 *字典类型ID
	 */
	@Column(id = "dicttype_id", association = true)
	private DictTypeInfo dicttypeId;

	/**
	 *字典真实值
	 */
	@Column(id = "dictdata_name", datatype = "string512")
	private java.lang.String dictdataName;

	/**
	 *字典显示值
	 */
	@Column(id = "dictdata_value", datatype = "string512")
	private java.lang.String dictdataValue;

	/**
	 *字典排序号
	 */
	@Column(id = "dictdata_order", datatype = "int")
	private java.lang.Integer dictdataOrder;

	/**
	 *是否默认值（0：否，1：是）
	 */
	@Column(id = "dictdata_isdefault", datatype = "char20")
	private java.lang.String dictdataIsdefault;

	/**
	 * 设置字典ID
	 */
	public void setDictdataId(java.lang.String dictdataId) {
		this.dictdataId = dictdataId;
	}

	/**
	 * 获取字典ID
	 */
	public java.lang.String getDictdataId() {
		return dictdataId;
	}

	/**
	 * 设置字典类型ID
	 */
	public void setDicttypeId(DictTypeInfo dicttypeId) {
		this.dicttypeId = dicttypeId;
	}

	/**
	 * 获取字典类型ID
	 */
	public DictTypeInfo getDicttypeId() {
		return dicttypeId;
	}

	/**
	 * 设置字典真实值
	 */
	public void setDictdataName(java.lang.String dictdataName) {
		this.dictdataName = dictdataName;
	}

	/**
	 * 获取字典真实值
	 */
	public java.lang.String getDictdataName() {
		return dictdataName;
	}

	/**
	 * 设置字典显示值
	 */
	public void setDictdataValue(java.lang.String dictdataValue) {
		this.dictdataValue = dictdataValue;
	}

	/**
	 * 获取字典显示值
	 */
	public java.lang.String getDictdataValue() {
		return dictdataValue;
	}

	/**
	 * 设置字典排序号
	 */
	public void setDictdataOrder(java.lang.Integer dictdataOrder) {
		this.dictdataOrder = dictdataOrder;
	}

	/**
	 * 获取字典排序号
	 */
	public java.lang.Integer getDictdataOrder() {
		return dictdataOrder;
	}

	/**
	 * 设置是否默认值（0：否，1：是）
	 */
	public void setDictdataIsdefault(java.lang.String dictdataIsdefault) {
		this.dictdataIsdefault = dictdataIsdefault;
	}

	/**
	 * 获取是否默认值（0：否，1：是）
	 */
	public java.lang.String getDictdataIsdefault() {
		return dictdataIsdefault;
	}
}
