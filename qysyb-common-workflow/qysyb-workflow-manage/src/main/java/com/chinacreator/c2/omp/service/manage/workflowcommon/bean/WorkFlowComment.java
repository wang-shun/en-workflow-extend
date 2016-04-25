package com.chinacreator.c2.omp.service.manage.workflowcommon.bean;

import java.util.Date;

public class WorkFlowComment {
	String message;
	Date date;
	String taskId;
	String userId;
	String userName;
	String taskDef;
	String taskName;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTaskDef() {
		return taskDef;
	}
	public void setTaskDef(String taskDef) {
		this.taskDef = taskDef;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
