package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 用户信息
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.UserInfo", table = "td_sm_user", ds = "newDS")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1489497805307904L;
	/**
	 *用户ID
	 */
	@Column(id = "user_id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String userId;

	/**
	 *用户帐号
	 */
	@Column(id = "user_name", datatype = "string1024")
	private java.lang.String userName;

	/**
	 *用户密码
	 */
	@Column(id = "user_password", datatype = "string1536")
	private java.lang.String userPassword;

	/**
	 *用户实名
	 */
	@Column(id = "user_realname", datatype = "string512")
	private java.lang.String userRealname;

	/**
	 *拼音
	 */
	@Column(id = "user_pinyin", datatype = "string512")
	private java.lang.String userPinyin;

	/**
	 *性别
	 */
	@Column(id = "user_sex", datatype = "string512")
	private java.lang.String userSex;

	/**
	 *家庭电话
	 */
	@Column(id = "user_hometel", datatype = "string512")
	private java.lang.String userHometel;

	/**
	 *公司电话
	 */
	@Column(id = "user_worktel", datatype = "string512")
	private java.lang.String userWorktel;

	/**
	 *公司地址
	 */
	@Column(id = "user_workaddress", datatype = "string512")
	private java.lang.String userWorkaddress;

	/**
	 *手机1
	 */
	@Column(id = "user_mobiletel1", datatype = "string512")
	private java.lang.String userMobiletel1;

	/**
	 *手机2
	 */
	@Column(id = "user_mobiletel2", datatype = "string512")
	private java.lang.String userMobiletel2;

	/**
	 *传真
	 */
	@Column(id = "user_fax", datatype = "string512")
	private java.lang.String userFax;

	/**
	 *OICQ
	 */
	@Column(id = "user_oicq", datatype = "string512")
	private java.lang.String userOicq;

	/**
	 *注册日期
	 */
	@Column(id = "user_regdate", datatype = "timestamp")
	private java.sql.Timestamp userRegdate;

	/**
	 *生日
	 */
	@Column(id = "user_birthday", datatype = "timestamp")
	private java.sql.Timestamp userBirthday;

	/**
	 *邮箱
	 */
	@Column(id = "user_email", datatype = "string512")
	private java.lang.String userEmail;

	/**
	 *住址
	 */
	@Column(id = "user_address", datatype = "string1024")
	private java.lang.String userAddress;

	/**
	 *邮编
	 */
	@Column(id = "user_postalcode", datatype = "string32")
	private java.lang.String userPostalcode;

	/**
	 *身份证
	 */
	@Column(id = "user_idcard", datatype = "string256")
	private java.lang.String userIdcard;

	/**
	 *是否使用
	 */
	@Column(id = "user_isvalid", datatype = "int")
	private java.lang.Integer userIsvalid;

	/**
	 *登陆次数
	 */
	@Column(id = "user_logincount", datatype = "int")
	private java.lang.Integer userLogincount;

	/**
	 *用户类型
	 */
	@Column(id = "user_type", datatype = "string512")
	private java.lang.String userType;

	/**
	 *过期时间
	 */
	@Column(id = "past_time", datatype = "timestamp")
	private java.sql.Timestamp pastTime;

	/**
	 *开通时间
	 */
	@Column(id = "dredge_time", datatype = "string256")
	private java.lang.String dredgeTime;

	/**
	 *用户最后登陆时间
	 */
	@Column(id = "lastlogin_date", datatype = "timestamp")
	private java.sql.Timestamp lastloginDate;

	/**
	 *工作年限
	 */
	@Column(id = "worklength", datatype = "string256")
	private java.lang.String worklength;

	/**
	 *政治面貌
	 */
	@Column(id = "politics", datatype = "string512")
	private java.lang.String politics;

	/**
	 *登录IP
	 */
	@Column(id = "login_ip", datatype = "string64")
	private java.lang.String loginIp;

	/**
	 *证书key的唯一标识
	 */
	@Column(id = "cert_sn", datatype = "string256")
	private java.lang.String certSn;

	/**
	 *用户排序号
	 */
	@Column(id = "user_sn", datatype = "int")
	private java.lang.Integer userSn;

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
	 * 设置用户ID
	 */
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	/**
	 * 获取用户ID
	 */
	public java.lang.String getUserId() {
		return userId;
	}

	/**
	 * 设置用户帐号
	 */
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	/**
	 * 获取用户帐号
	 */
	public java.lang.String getUserName() {
		return userName;
	}

	/**
	 * 设置用户密码
	 */
	public void setUserPassword(java.lang.String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * 获取用户密码
	 */
	public java.lang.String getUserPassword() {
		return userPassword;
	}

	/**
	 * 设置用户实名
	 */
	public void setUserRealname(java.lang.String userRealname) {
		this.userRealname = userRealname;
	}

	/**
	 * 获取用户实名
	 */
	public java.lang.String getUserRealname() {
		return userRealname;
	}

	/**
	 * 设置拼音
	 */
	public void setUserPinyin(java.lang.String userPinyin) {
		this.userPinyin = userPinyin;
	}

	/**
	 * 获取拼音
	 */
	public java.lang.String getUserPinyin() {
		return userPinyin;
	}

	/**
	 * 设置性别
	 */
	public void setUserSex(java.lang.String userSex) {
		this.userSex = userSex;
	}

	/**
	 * 获取性别
	 */
	public java.lang.String getUserSex() {
		return userSex;
	}

	/**
	 * 设置家庭电话
	 */
	public void setUserHometel(java.lang.String userHometel) {
		this.userHometel = userHometel;
	}

	/**
	 * 获取家庭电话
	 */
	public java.lang.String getUserHometel() {
		return userHometel;
	}

	/**
	 * 设置公司电话
	 */
	public void setUserWorktel(java.lang.String userWorktel) {
		this.userWorktel = userWorktel;
	}

	/**
	 * 获取公司电话
	 */
	public java.lang.String getUserWorktel() {
		return userWorktel;
	}

	/**
	 * 设置公司地址
	 */
	public void setUserWorkaddress(java.lang.String userWorkaddress) {
		this.userWorkaddress = userWorkaddress;
	}

	/**
	 * 获取公司地址
	 */
	public java.lang.String getUserWorkaddress() {
		return userWorkaddress;
	}

	/**
	 * 设置手机1
	 */
	public void setUserMobiletel1(java.lang.String userMobiletel1) {
		this.userMobiletel1 = userMobiletel1;
	}

	/**
	 * 获取手机1
	 */
	public java.lang.String getUserMobiletel1() {
		return userMobiletel1;
	}

	/**
	 * 设置手机2
	 */
	public void setUserMobiletel2(java.lang.String userMobiletel2) {
		this.userMobiletel2 = userMobiletel2;
	}

	/**
	 * 获取手机2
	 */
	public java.lang.String getUserMobiletel2() {
		return userMobiletel2;
	}

	/**
	 * 设置传真
	 */
	public void setUserFax(java.lang.String userFax) {
		this.userFax = userFax;
	}

	/**
	 * 获取传真
	 */
	public java.lang.String getUserFax() {
		return userFax;
	}

	/**
	 * 设置OICQ
	 */
	public void setUserOicq(java.lang.String userOicq) {
		this.userOicq = userOicq;
	}

	/**
	 * 获取OICQ
	 */
	public java.lang.String getUserOicq() {
		return userOicq;
	}

	/**
	 * 设置注册日期
	 */
	public void setUserRegdate(java.sql.Timestamp userRegdate) {
		this.userRegdate = userRegdate;
	}

	/**
	 * 获取注册日期
	 */
	public java.sql.Timestamp getUserRegdate() {
		return userRegdate;
	}

	/**
	 * 设置生日
	 */
	public void setUserBirthday(java.sql.Timestamp userBirthday) {
		this.userBirthday = userBirthday;
	}

	/**
	 * 获取生日
	 */
	public java.sql.Timestamp getUserBirthday() {
		return userBirthday;
	}

	/**
	 * 设置邮箱
	 */
	public void setUserEmail(java.lang.String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * 获取邮箱
	 */
	public java.lang.String getUserEmail() {
		return userEmail;
	}

	/**
	 * 设置住址
	 */
	public void setUserAddress(java.lang.String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * 获取住址
	 */
	public java.lang.String getUserAddress() {
		return userAddress;
	}

	/**
	 * 设置邮编
	 */
	public void setUserPostalcode(java.lang.String userPostalcode) {
		this.userPostalcode = userPostalcode;
	}

	/**
	 * 获取邮编
	 */
	public java.lang.String getUserPostalcode() {
		return userPostalcode;
	}

	/**
	 * 设置身份证
	 */
	public void setUserIdcard(java.lang.String userIdcard) {
		this.userIdcard = userIdcard;
	}

	/**
	 * 获取身份证
	 */
	public java.lang.String getUserIdcard() {
		return userIdcard;
	}

	/**
	 * 设置是否使用
	 */
	public void setUserIsvalid(java.lang.Integer userIsvalid) {
		this.userIsvalid = userIsvalid;
	}

	/**
	 * 获取是否使用
	 */
	public java.lang.Integer getUserIsvalid() {
		return userIsvalid;
	}

	/**
	 * 设置登陆次数
	 */
	public void setUserLogincount(java.lang.Integer userLogincount) {
		this.userLogincount = userLogincount;
	}

	/**
	 * 获取登陆次数
	 */
	public java.lang.Integer getUserLogincount() {
		return userLogincount;
	}

	/**
	 * 设置用户类型
	 */
	public void setUserType(java.lang.String userType) {
		this.userType = userType;
	}

	/**
	 * 获取用户类型
	 */
	public java.lang.String getUserType() {
		return userType;
	}

	/**
	 * 设置过期时间
	 */
	public void setPastTime(java.sql.Timestamp pastTime) {
		this.pastTime = pastTime;
	}

	/**
	 * 获取过期时间
	 */
	public java.sql.Timestamp getPastTime() {
		return pastTime;
	}

	/**
	 * 设置开通时间
	 */
	public void setDredgeTime(java.lang.String dredgeTime) {
		this.dredgeTime = dredgeTime;
	}

	/**
	 * 获取开通时间
	 */
	public java.lang.String getDredgeTime() {
		return dredgeTime;
	}

	/**
	 * 设置用户最后登陆时间
	 */
	public void setLastloginDate(java.sql.Timestamp lastloginDate) {
		this.lastloginDate = lastloginDate;
	}

	/**
	 * 获取用户最后登陆时间
	 */
	public java.sql.Timestamp getLastloginDate() {
		return lastloginDate;
	}

	/**
	 * 设置工作年限
	 */
	public void setWorklength(java.lang.String worklength) {
		this.worklength = worklength;
	}

	/**
	 * 获取工作年限
	 */
	public java.lang.String getWorklength() {
		return worklength;
	}

	/**
	 * 设置政治面貌
	 */
	public void setPolitics(java.lang.String politics) {
		this.politics = politics;
	}

	/**
	 * 获取政治面貌
	 */
	public java.lang.String getPolitics() {
		return politics;
	}

	/**
	 * 设置登录IP
	 */
	public void setLoginIp(java.lang.String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 获取登录IP
	 */
	public java.lang.String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设置证书key的唯一标识
	 */
	public void setCertSn(java.lang.String certSn) {
		this.certSn = certSn;
	}

	/**
	 * 获取证书key的唯一标识
	 */
	public java.lang.String getCertSn() {
		return certSn;
	}

	/**
	 * 设置用户排序号
	 */
	public void setUserSn(java.lang.Integer userSn) {
		this.userSn = userSn;
	}

	/**
	 * 获取用户排序号
	 */
	public java.lang.Integer getUserSn() {
		return userSn;
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
