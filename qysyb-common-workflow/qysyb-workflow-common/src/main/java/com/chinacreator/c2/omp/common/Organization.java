package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 部门信息
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.Organization", table = "td_sm_organization", ds = "newDS")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1489548875005952L;
	/**
	 *机构ID
	 */
	@Column(id = "org_id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String orgId;

	/**
	 *机构排序ID
	 */
	@Column(id = "org_sn", datatype = "int")
	private java.lang.Integer orgSn;

	/**
	 *机构名称
	 */
	@Column(id = "org_name", datatype = "string128")
	private java.lang.String orgName;

	/**
	 *机构显示名称
	 */
	@Column(id = "org_showname", datatype = "string512")
	private java.lang.String orgShowname;

	/**
	 *父机构ID
	 */
	@Column(id = "parent_id", datatype = "string512")
	private java.lang.String parentId;

	/**
	 *路径
	 */
	@Column(id = "path", datatype = "string5")
	private java.lang.String path;

	/**
	 *层（阶次）
	 */
	@Column(id = "layer", datatype = "string1024")
	private java.lang.String layer;

	/**
	 *子机构
	 */
	@Column(id = "children", datatype = "string5")
	private java.lang.String children;

	/**
	 *机构代号
	 */
	@Column(id = "code", datatype = "string512")
	private java.lang.String code;

	/**
	 *简拼
	 */
	@Column(id = "jp", datatype = "string128")
	private java.lang.String jp;

	/**
	 *全拼
	 */
	@Column(id = "qp", datatype = "string128")
	private java.lang.String qp;

	/**
	 *创建时间
	 */
	@Column(id = "creatingtime", datatype = "timestamp")
	private java.sql.Timestamp creatingtime;

	/**
	 *创建人
	 */
	@Column(id = "creator", datatype = "string512")
	private java.lang.String creator;

	/**
	 *机构编号
	 */
	@Column(id = "orgnumber", datatype = "string512")
	private java.lang.String orgnumber;

	/**
	 *机构描述
	 */
	@Column(id = "orgdesc", datatype = "string1024")
	private java.lang.String orgdesc;

	/**
	 *主管机构
	 */
	@Column(id = "chargeorgid", datatype = "string256")
	private java.lang.String chargeorgid;

	/**
	 *机构行政级别，1：省级，2：市州级，3：县区级，4：科所级
	 */
	@Column(id = "org_level", datatype = "string5")
	private java.lang.String orgLevel;

	/**
	 *行政区码
	 */
	@Column(id = "org_xzqm", datatype = "string64")
	private java.lang.String orgXzqm;

	/**
	 *是否直属局 0-不是，缺省值 1-是
	 */
	@Column(id = "isdirectlyparty", datatype = "tinyint")
	private java.lang.Integer isdirectlyparty;

	/**
	 *是否涉外局 0-是，缺省值 1-不是
	 */
	@Column(id = "isforeignparty", datatype = "tinyint")
	private java.lang.Integer isforeignparty;

	/**
	 *是否稽查局 0-不是，缺省值 1-是
	 */
	@Column(id = "isjichaparty", datatype = "tinyint")
	private java.lang.Integer isjichaparty;

	/**
	 *是否直接管户单位 0-不是，缺省值 1-是
	 */
	@Column(id = "isdirectguanhu", datatype = "tinyint")
	private java.lang.Integer isdirectguanhu;

	/**
	 *备用字段1
	 */
	@Column(id = "remark1", datatype = "string512")
	private java.lang.String remark1;

	/**
	 *备用字段2
	 */
	@Column(id = "remark2", datatype = "string512")
	private java.lang.String remark2;

	/**
	 *备用字段3
	 */
	@Column(id = "remark3", datatype = "string512")
	private java.lang.String remark3;

	/**
	 *备用字段4
	 */
	@Column(id = "remark4", datatype = "string512")
	private java.lang.String remark4;

	/**
	 *备用字段5
	 */
	@Column(id = "remark5", datatype = "string512")
	private java.lang.String remark5;

	/**
	 *备用字段6
	 */
	@Column(id = "remark6", datatype = "string512")
	private java.lang.String remark6;

	/**
	 *备用字段7
	 */
	@Column(id = "remark7", datatype = "string512")
	private java.lang.String remark7;

	/**
	 *备用字段8
	 */
	@Column(id = "remark8", datatype = "string512")
	private java.lang.String remark8;

	/**
	 *备用字段9
	 */
	@Column(id = "remark9", datatype = "string512")
	private java.lang.String remark9;

	/**
	 *备用字段10
	 */
	@Column(id = "remark10", datatype = "string512")
	private java.lang.String remark10;

	/**
	 * 设置机构ID
	 */
	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 获取机构ID
	 */
	public java.lang.String getOrgId() {
		return orgId;
	}

	/**
	 * 设置机构排序ID
	 */
	public void setOrgSn(java.lang.Integer orgSn) {
		this.orgSn = orgSn;
	}

	/**
	 * 获取机构排序ID
	 */
	public java.lang.Integer getOrgSn() {
		return orgSn;
	}

	/**
	 * 设置机构名称
	 */
	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 获取机构名称
	 */
	public java.lang.String getOrgName() {
		return orgName;
	}

	/**
	 * 设置机构显示名称
	 */
	public void setOrgShowname(java.lang.String orgShowname) {
		this.orgShowname = orgShowname;
	}

	/**
	 * 获取机构显示名称
	 */
	public java.lang.String getOrgShowname() {
		return orgShowname;
	}

	/**
	 * 设置父机构ID
	 */
	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取父机构ID
	 */
	public java.lang.String getParentId() {
		return parentId;
	}

	/**
	 * 设置路径
	 */
	public void setPath(java.lang.String path) {
		this.path = path;
	}

	/**
	 * 获取路径
	 */
	public java.lang.String getPath() {
		return path;
	}

	/**
	 * 设置层（阶次）
	 */
	public void setLayer(java.lang.String layer) {
		this.layer = layer;
	}

	/**
	 * 获取层（阶次）
	 */
	public java.lang.String getLayer() {
		return layer;
	}

	/**
	 * 设置子机构
	 */
	public void setChildren(java.lang.String children) {
		this.children = children;
	}

	/**
	 * 获取子机构
	 */
	public java.lang.String getChildren() {
		return children;
	}

	/**
	 * 设置机构代号
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}

	/**
	 * 获取机构代号
	 */
	public java.lang.String getCode() {
		return code;
	}

	/**
	 * 设置简拼
	 */
	public void setJp(java.lang.String jp) {
		this.jp = jp;
	}

	/**
	 * 获取简拼
	 */
	public java.lang.String getJp() {
		return jp;
	}

	/**
	 * 设置全拼
	 */
	public void setQp(java.lang.String qp) {
		this.qp = qp;
	}

	/**
	 * 获取全拼
	 */
	public java.lang.String getQp() {
		return qp;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreatingtime(java.sql.Timestamp creatingtime) {
		this.creatingtime = creatingtime;
	}

	/**
	 * 获取创建时间
	 */
	public java.sql.Timestamp getCreatingtime() {
		return creatingtime;
	}

	/**
	 * 设置创建人
	 */
	public void setCreator(java.lang.String creator) {
		this.creator = creator;
	}

	/**
	 * 获取创建人
	 */
	public java.lang.String getCreator() {
		return creator;
	}

	/**
	 * 设置机构编号
	 */
	public void setOrgnumber(java.lang.String orgnumber) {
		this.orgnumber = orgnumber;
	}

	/**
	 * 获取机构编号
	 */
	public java.lang.String getOrgnumber() {
		return orgnumber;
	}

	/**
	 * 设置机构描述
	 */
	public void setOrgdesc(java.lang.String orgdesc) {
		this.orgdesc = orgdesc;
	}

	/**
	 * 获取机构描述
	 */
	public java.lang.String getOrgdesc() {
		return orgdesc;
	}

	/**
	 * 设置主管机构
	 */
	public void setChargeorgid(java.lang.String chargeorgid) {
		this.chargeorgid = chargeorgid;
	}

	/**
	 * 获取主管机构
	 */
	public java.lang.String getChargeorgid() {
		return chargeorgid;
	}

	/**
	 * 设置机构行政级别，1：省级，2：市州级，3：县区级，4：科所级
	 */
	public void setOrgLevel(java.lang.String orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * 获取机构行政级别，1：省级，2：市州级，3：县区级，4：科所级
	 */
	public java.lang.String getOrgLevel() {
		return orgLevel;
	}

	/**
	 * 设置行政区码
	 */
	public void setOrgXzqm(java.lang.String orgXzqm) {
		this.orgXzqm = orgXzqm;
	}

	/**
	 * 获取行政区码
	 */
	public java.lang.String getOrgXzqm() {
		return orgXzqm;
	}

	/**
	 * 设置是否直属局 0-不是，缺省值 1-是
	 */
	public void setIsdirectlyparty(java.lang.Integer isdirectlyparty) {
		this.isdirectlyparty = isdirectlyparty;
	}

	/**
	 * 获取是否直属局 0-不是，缺省值 1-是
	 */
	public java.lang.Integer getIsdirectlyparty() {
		return isdirectlyparty;
	}

	/**
	 * 设置是否涉外局 0-是，缺省值 1-不是
	 */
	public void setIsforeignparty(java.lang.Integer isforeignparty) {
		this.isforeignparty = isforeignparty;
	}

	/**
	 * 获取是否涉外局 0-是，缺省值 1-不是
	 */
	public java.lang.Integer getIsforeignparty() {
		return isforeignparty;
	}

	/**
	 * 设置是否稽查局 0-不是，缺省值 1-是
	 */
	public void setIsjichaparty(java.lang.Integer isjichaparty) {
		this.isjichaparty = isjichaparty;
	}

	/**
	 * 获取是否稽查局 0-不是，缺省值 1-是
	 */
	public java.lang.Integer getIsjichaparty() {
		return isjichaparty;
	}

	/**
	 * 设置是否直接管户单位 0-不是，缺省值 1-是
	 */
	public void setIsdirectguanhu(java.lang.Integer isdirectguanhu) {
		this.isdirectguanhu = isdirectguanhu;
	}

	/**
	 * 获取是否直接管户单位 0-不是，缺省值 1-是
	 */
	public java.lang.Integer getIsdirectguanhu() {
		return isdirectguanhu;
	}

	/**
	 * 设置备用字段1
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取备用字段1
	 */
	public java.lang.String getRemark1() {
		return remark1;
	}

	/**
	 * 设置备用字段2
	 */
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取备用字段2
	 */
	public java.lang.String getRemark2() {
		return remark2;
	}

	/**
	 * 设置备用字段3
	 */
	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	/**
	 * 获取备用字段3
	 */
	public java.lang.String getRemark3() {
		return remark3;
	}

	/**
	 * 设置备用字段4
	 */
	public void setRemark4(java.lang.String remark4) {
		this.remark4 = remark4;
	}

	/**
	 * 获取备用字段4
	 */
	public java.lang.String getRemark4() {
		return remark4;
	}

	/**
	 * 设置备用字段5
	 */
	public void setRemark5(java.lang.String remark5) {
		this.remark5 = remark5;
	}

	/**
	 * 获取备用字段5
	 */
	public java.lang.String getRemark5() {
		return remark5;
	}

	/**
	 * 设置备用字段6
	 */
	public void setRemark6(java.lang.String remark6) {
		this.remark6 = remark6;
	}

	/**
	 * 获取备用字段6
	 */
	public java.lang.String getRemark6() {
		return remark6;
	}

	/**
	 * 设置备用字段7
	 */
	public void setRemark7(java.lang.String remark7) {
		this.remark7 = remark7;
	}

	/**
	 * 获取备用字段7
	 */
	public java.lang.String getRemark7() {
		return remark7;
	}

	/**
	 * 设置备用字段8
	 */
	public void setRemark8(java.lang.String remark8) {
		this.remark8 = remark8;
	}

	/**
	 * 获取备用字段8
	 */
	public java.lang.String getRemark8() {
		return remark8;
	}

	/**
	 * 设置备用字段9
	 */
	public void setRemark9(java.lang.String remark9) {
		this.remark9 = remark9;
	}

	/**
	 * 获取备用字段9
	 */
	public java.lang.String getRemark9() {
		return remark9;
	}

	/**
	 * 设置备用字段10
	 */
	public void setRemark10(java.lang.String remark10) {
		this.remark10 = remark10;
	}

	/**
	 * 获取备用字段10
	 */
	public java.lang.String getRemark10() {
		return remark10;
	}
}
