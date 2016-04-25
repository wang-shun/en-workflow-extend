package com.chinacreator.c2.omp.service.manage.notificationmanage;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 通知公告
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.notificationmanage.noticeannouncement", table = "KCOMP_NOTICE_ANNOUNCEMENT", ds = "oracDB")
public class Noticeannouncement implements Serializable {
	private static final long serialVersionUID = 1401598210031616L;
	/**
	 *Id
	 */
	@Column(id = "notify_id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String notifyId;

	/**
	 *编号
	 */
	@Column(id = "notify_no", datatype = "string64")
	private java.lang.String notifyNo;

	/**
	 *标题
	 */
	@Column(id = "notify_name", datatype = "string256")
	private java.lang.String notifyName;

	/**
	 *内容
	 */
	@Column(id = "notify_content", datatype = "text")
	private java.lang.String notifyContent;

	/**
	 *开始时间
	 */
	@Column(id = "start_date", datatype = "date")
	private java.sql.Date startDate;

	/**
	 *结束时间
	 */
	@Column(id = "end_date", datatype = "date")
	private java.sql.Date endDate;

	/**
	 *创建人ID
	 */
	@Column(id = "creator_id", datatype = "mediumdouble")
	private java.lang.Double creatorId;

	/**
	 *发布者ID
	 */
	@Column(id = "issuer_id", datatype = "string128")
	private java.lang.String issuerId;

	/**
	 *发布时间
	 */
	@Column(id = "issue_date", datatype = "date")
	private java.sql.Date issueDate;

	/**
	 *审核人ID
	 */
	@Column(id = "reviewer_id", datatype = "mediumdouble")
	private java.lang.Double reviewerId;

	/**
	 *是否审核
	 */
	@Column(id = "is_reviewed", datatype = "string512")
	private java.lang.String isReviewed;

	/**
	 *审核时间
	 */
	@Column(id = "review_date", datatype = "date")
	private java.sql.Date reviewDate;

	/**
	 *状态
	 */
	@Column(id = "state", datatype = "smallint")
	private java.lang.Integer state;

	/**
	 *备注
	 */
	@Column(id = "remark", datatype = "string512")
	private java.lang.String remark;

	/**
	 *����ID
	 */
	@Column(id = "org_id", datatype = "string128")
	private java.lang.String orgId;

	/**
	 *�û�ID
	 */
	@Column(id = "user_id", datatype = "string128")
	private java.lang.String userId;

	/**
	 *创建日期
	 */
	@Column(id = "createdate", datatype = "date")
	private java.sql.Date createdate;

	/**
	 *������ID
	 */
	@Column(id = "mender_id", datatype = "string128")
	private java.lang.String menderId;

	/**
	 *����������
	 */
	@Column(id = "mender_name", datatype = "string256")
	private java.lang.String menderName;

	/**
	 *����ʱ��
	 */
	@Column(id = "mend_date", datatype = "date")
	private java.sql.Date mendDate;

	/**
	 *通知类型
	 */
	@Column(id = "notify_type_id", datatype = "string128")
	private java.lang.String notifyTypeId;

	/**
	 *范围
	 */
	@Column(id = "scope_id", datatype = "string128")
	private java.lang.String scopeId;

	/**
	 * 设置Id
	 */
	public void setNotifyId(java.lang.String notifyId) {
		this.notifyId = notifyId;
	}

	/**
	 * 获取Id
	 */
	public java.lang.String getNotifyId() {
		return notifyId;
	}

	/**
	 * 设置编号
	 */
	public void setNotifyNo(java.lang.String notifyNo) {
		this.notifyNo = notifyNo;
	}

	/**
	 * 获取编号
	 */
	public java.lang.String getNotifyNo() {
		return notifyNo;
	}

	/**
	 * 设置标题
	 */
	public void setNotifyName(java.lang.String notifyName) {
		this.notifyName = notifyName;
	}

	/**
	 * 获取标题
	 */
	public java.lang.String getNotifyName() {
		return notifyName;
	}

	/**
	 * 设置内容
	 */
	public void setNotifyContent(java.lang.String notifyContent) {
		this.notifyContent = notifyContent;
	}

	/**
	 * 获取内容
	 */
	public java.lang.String getNotifyContent() {
		return notifyContent;
	}

	/**
	 * 设置开始时间
	 */
	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取开始时间
	 */
	public java.sql.Date getStartDate() {
		return startDate;
	}

	/**
	 * 设置结束时间
	 */
	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取结束时间
	 */
	public java.sql.Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置创建人ID
	 */
	public void setCreatorId(java.lang.Double creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 获取创建人ID
	 */
	public java.lang.Double getCreatorId() {
		return creatorId;
	}

	/**
	 * 设置发布者ID
	 */
	public void setIssuerId(java.lang.String issuerId) {
		this.issuerId = issuerId;
	}

	/**
	 * 获取发布者ID
	 */
	public java.lang.String getIssuerId() {
		return issuerId;
	}

	/**
	 * 设置发布时间
	 */
	public void setIssueDate(java.sql.Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * 获取发布时间
	 */
	public java.sql.Date getIssueDate() {
		return issueDate;
	}

	/**
	 * 设置审核人ID
	 */
	public void setReviewerId(java.lang.Double reviewerId) {
		this.reviewerId = reviewerId;
	}

	/**
	 * 获取审核人ID
	 */
	public java.lang.Double getReviewerId() {
		return reviewerId;
	}

	/**
	 * 设置是否审核
	 */
	public void setIsReviewed(java.lang.String isReviewed) {
		this.isReviewed = isReviewed;
	}

	/**
	 * 获取是否审核
	 */
	public java.lang.String getIsReviewed() {
		return isReviewed;
	}

	/**
	 * 设置审核时间
	 */
	public void setReviewDate(java.sql.Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	/**
	 * 获取审核时间
	 */
	public java.sql.Date getReviewDate() {
		return reviewDate;
	}

	/**
	 * 设置状态
	 */
	public void setState(java.lang.Integer state) {
		this.state = state;
	}

	/**
	 * 获取状态
	 */
	public java.lang.Integer getState() {
		return state;
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
	 * 设置����ID
	 */
	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 获取����ID
	 */
	public java.lang.String getOrgId() {
		return orgId;
	}

	/**
	 * 设置�û�ID
	 */
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	/**
	 * 获取�û�ID
	 */
	public java.lang.String getUserId() {
		return userId;
	}

	/**
	 * 设置创建日期
	 */
	public void setCreatedate(java.sql.Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * 获取创建日期
	 */
	public java.sql.Date getCreatedate() {
		return createdate;
	}

	/**
	 * 设置������ID
	 */
	public void setMenderId(java.lang.String menderId) {
		this.menderId = menderId;
	}

	/**
	 * 获取������ID
	 */
	public java.lang.String getMenderId() {
		return menderId;
	}

	/**
	 * 设置����������
	 */
	public void setMenderName(java.lang.String menderName) {
		this.menderName = menderName;
	}

	/**
	 * 获取����������
	 */
	public java.lang.String getMenderName() {
		return menderName;
	}

	/**
	 * 设置����ʱ��
	 */
	public void setMendDate(java.sql.Date mendDate) {
		this.mendDate = mendDate;
	}

	/**
	 * 获取����ʱ��
	 */
	public java.sql.Date getMendDate() {
		return mendDate;
	}

	/**
	 * 设置通知类型
	 */
	public void setNotifyTypeId(java.lang.String notifyTypeId) {
		this.notifyTypeId = notifyTypeId;
	}

	/**
	 * 获取通知类型
	 */
	public java.lang.String getNotifyTypeId() {
		return notifyTypeId;
	}

	/**
	 * 设置范围
	 */
	public void setScopeId(java.lang.String scopeId) {
		this.scopeId = scopeId;
	}

	/**
	 * 获取范围
	 */
	public java.lang.String getScopeId() {
		return scopeId;
	}
}
