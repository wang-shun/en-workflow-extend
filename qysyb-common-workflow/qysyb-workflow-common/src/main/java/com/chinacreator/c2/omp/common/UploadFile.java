package com.chinacreator.c2.omp.common;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 上传的文件
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.common.UploadFile", table = "KCOMP_FILE_UPLOAD", ds = "newDS")
public class UploadFile implements Serializable {
	private static final long serialVersionUID = 1165479809466368L;
	/**
	 *上传文件的id
	 */
	@Column(id = "file_id", type = ColumnType.uuid, datatype = "string256")
	private java.lang.String fileId;

	/**
	 *文件路径
	 */
	@Column(id = "file_path", datatype = "string1024")
	private java.lang.String filePath;

	/**
	 *文件名
	 */
	@Column(id = "file_name", datatype = "string1024")
	private java.lang.String fileName;

	/**
	 *文件大小
	 */
	@Column(id = "file_size", datatype = "bigint")
	private java.math.BigInteger fileSize;

	/**
	 *文件类型
	 */
	@Column(id = "file_type", datatype = "string256")
	private java.lang.String fileType;

	/**
	 *业务类型
	 */
	@Column(id = "business_type", datatype = "string64")
	private java.lang.String businessType;

	/**
	 *业务ID
	 */
	@Column(id = "business_key", datatype = "string256")
	private java.lang.String businessKey;

	/**
	 *活动定义ID
	 */
	@Column(id = "task_id", datatype = "string256")
	private java.lang.String taskId;

	/**
	 *存储方式：1文件存储、2FTP存储、3数据库存储
	 */
	@Column(id = "store_type", datatype = "char20")
	private java.lang.String storeType;

	/**
	 *原始文件路径
	 */
	@Column(id = "file_src_path", datatype = "text")
	private java.lang.String fileSrcPath;

	/**
	 *原始文件名
	 */
	@Column(id = "file_src_name", datatype = "text")
	private java.lang.String fileSrcName;

	/**
	 *上传时间
	 */
	@Column(id = "upload_time", datatype = "timestamp")
	private java.sql.Timestamp uploadTime;

	/**
	 *机构ID
	 */
	@Column(id = "org_id", datatype = "string256")
	private java.lang.String orgId;

	/**
	 *用户ID
	 */
	@Column(id = "user_id", datatype = "string256")
	private java.lang.String userId;

	/**
	 *FILE_DESCRIBE
	 */
	@Column(id = "file_describe", datatype = "string1024")
	private java.lang.String fileDescribe;

	/**
	 *REMARK1
	 */
	@Column(id = "remark1", datatype = "string32")
	private java.lang.String remark1;

	/**
	 *REMARK2
	 */
	@Column(id = "remark2", datatype = "string32")
	private java.lang.String remark2;

	/**
	 *文件显示名，默认为原始文件名
	 */
	@Column(id = "display_name", datatype = "text")
	private java.lang.String displayName;

	/**
	 * 设置上传文件的id
	 */
	public void setFileId(java.lang.String fileId) {
		this.fileId = fileId;
	}

	/**
	 * 获取上传文件的id
	 */
	public java.lang.String getFileId() {
		return fileId;
	}

	/**
	 * 设置文件路径
	 */
	public void setFilePath(java.lang.String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 获取文件路径
	 */
	public java.lang.String getFilePath() {
		return filePath;
	}

	/**
	 * 设置文件名
	 */
	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取文件名
	 */
	public java.lang.String getFileName() {
		return fileName;
	}

	/**
	 * 设置文件大小
	 */
	public void setFileSize(java.math.BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 获取文件大小
	 */
	public java.math.BigInteger getFileSize() {
		return fileSize;
	}

	/**
	 * 设置文件类型
	 */
	public void setFileType(java.lang.String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 获取文件类型
	 */
	public java.lang.String getFileType() {
		return fileType;
	}

	/**
	 * 设置业务类型
	 */
	public void setBusinessType(java.lang.String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 获取业务类型
	 */
	public java.lang.String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置业务ID
	 */
	public void setBusinessKey(java.lang.String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 获取业务ID
	 */
	public java.lang.String getBusinessKey() {
		return businessKey;
	}

	/**
	 * 设置活动定义ID
	 */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 获取活动定义ID
	 */
	public java.lang.String getTaskId() {
		return taskId;
	}

	/**
	 * 设置存储方式：1文件存储、2FTP存储、3数据库存储
	 */
	public void setStoreType(java.lang.String storeType) {
		this.storeType = storeType;
	}

	/**
	 * 获取存储方式：1文件存储、2FTP存储、3数据库存储
	 */
	public java.lang.String getStoreType() {
		return storeType;
	}

	/**
	 * 设置原始文件路径
	 */
	public void setFileSrcPath(java.lang.String fileSrcPath) {
		this.fileSrcPath = fileSrcPath;
	}

	/**
	 * 获取原始文件路径
	 */
	public java.lang.String getFileSrcPath() {
		return fileSrcPath;
	}

	/**
	 * 设置原始文件名
	 */
	public void setFileSrcName(java.lang.String fileSrcName) {
		this.fileSrcName = fileSrcName;
	}

	/**
	 * 获取原始文件名
	 */
	public java.lang.String getFileSrcName() {
		return fileSrcName;
	}

	/**
	 * 设置上传时间
	 */
	public void setUploadTime(java.sql.Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * 获取上传时间
	 */
	public java.sql.Timestamp getUploadTime() {
		return uploadTime;
	}

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
	 * 设置FILE_DESCRIBE
	 */
	public void setFileDescribe(java.lang.String fileDescribe) {
		this.fileDescribe = fileDescribe;
	}

	/**
	 * 获取FILE_DESCRIBE
	 */
	public java.lang.String getFileDescribe() {
		return fileDescribe;
	}

	/**
	 * 设置REMARK1
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取REMARK1
	 */
	public java.lang.String getRemark1() {
		return remark1;
	}

	/**
	 * 设置REMARK2
	 */
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取REMARK2
	 */
	public java.lang.String getRemark2() {
		return remark2;
	}

	/**
	 * 设置文件显示名，默认为原始文件名
	 */
	public void setDisplayName(java.lang.String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 获取文件显示名，默认为原始文件名
	 */
	public java.lang.String getDisplayName() {
		return displayName;
	}
}
