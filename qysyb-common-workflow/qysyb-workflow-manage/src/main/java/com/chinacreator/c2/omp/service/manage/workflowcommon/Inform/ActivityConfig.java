package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform;

import java.io.Serializable;

import com.chinacreator.c2.annotation.Column;
import com.chinacreator.c2.annotation.ColumnType;
import com.chinacreator.c2.annotation.Entity;

/**
 * 节点配置
 * @author 
 * @generated
 */
@Entity(id = "entity:com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.ActivityConfig", table = "WORKFLOW_ACTIVITY_SETTING", ds = "oracDB")
public class ActivityConfig implements Serializable {
	private static final long serialVersionUID = 1468232196112384L;
	@Column(id = "id", type = ColumnType.uuid, datatype = "string128")
	private java.lang.String id;

	/**
	 *事项Id(服务产品Id)
	 */
	@Column(id = "module_id", datatype = "string128")
	private java.lang.String moduleId;

	/**
	 *环节
	 */
	@Column(id = "task_def_id", datatype = "string256")
	private java.lang.String taskDefId;

	@Column(id = "action", datatype = "string256")
	private java.lang.String action;

	/**
	 *通知类型
	 */
	@Column(id = "inform_type", datatype = "string256")
	private java.lang.String informType;

	/**
	 *需要签收
	 */
	@Column(id = "needclaim", datatype = "boolean")
	private java.lang.Boolean needclaim;

	/**
	 *是否计入处理时间
	 */
	@Column(id = "is_sla_time", datatype = "boolean")
	private java.lang.Boolean isSlaTime;

	/**
	 *用来设置节点包含哪些tab页
	 */
	@Column(id = "include_tabs", datatype = "string2000")
	private java.lang.String includeTabs;

	/**
	 *节点进行的操作
	 */
	@Column(id = "include_actions", datatype = "string256")
	private java.lang.String includeActions;

	/**
	 *退回操作退回到环节的ID
	 */
	@Column(id = "return_activity", datatype = "string256")
	private java.lang.String returnActivity;

	/**
	 *自由选择的url
	 */
	@Column(id = "freechoose_url", datatype = "string256")
	private java.lang.String freechooseUrl;

	@Column(id = "remark1", datatype = "string1024")
	private java.lang.String remark1;

	@Column(id = "remark2", datatype = "string1024")
	private java.lang.String remark2;

	/**
	 *
	 */
	@Column(id = "remark3", datatype = "boolean")
	private java.lang.Boolean remark3;

	@Column(id = "remark4", datatype = "smallint")
	private java.lang.Integer remark4;

	/**
	 * 设置${field.desc}
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * 设置事项Id(服务产品Id)
	 */
	public void setModuleId(java.lang.String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 获取事项Id(服务产品Id)
	 */
	public java.lang.String getModuleId() {
		return moduleId;
	}

	/**
	 * 设置环节
	 */
	public void setTaskDefId(java.lang.String taskDefId) {
		this.taskDefId = taskDefId;
	}

	/**
	 * 获取环节
	 */
	public java.lang.String getTaskDefId() {
		return taskDefId;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setAction(java.lang.String action) {
		this.action = action;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getAction() {
		return action;
	}

	/**
	 * 设置通知类型
	 */
	public void setInformType(java.lang.String informType) {
		this.informType = informType;
	}

	/**
	 * 获取通知类型
	 */
	public java.lang.String getInformType() {
		return informType;
	}

	/**
	 * 设置需要签收
	 */
	public void setNeedclaim(java.lang.Boolean needclaim) {
		this.needclaim = needclaim;
	}

	/**
	 * 获取需要签收
	 */
	public java.lang.Boolean isNeedclaim() {
		return needclaim;
	}

	/**
	 * 设置是否计入处理时间
	 */
	public void setIsSlaTime(java.lang.Boolean isSlaTime) {
		this.isSlaTime = isSlaTime;
	}

	/**
	 * 获取是否计入处理时间
	 */
	public java.lang.Boolean isIsSlaTime() {
		return isSlaTime;
	}

	/**
	 * 设置用来设置节点包含哪些tab页
	 */
	public void setIncludeTabs(java.lang.String includeTabs) {
		this.includeTabs = includeTabs;
	}

	/**
	 * 获取用来设置节点包含哪些tab页
	 */
	public java.lang.String getIncludeTabs() {
		return includeTabs;
	}

	/**
	 * 设置节点进行的操作
	 */
	public void setIncludeActions(java.lang.String includeActions) {
		this.includeActions = includeActions;
	}

	/**
	 * 获取节点进行的操作
	 */
	public java.lang.String getIncludeActions() {
		return includeActions;
	}

	/**
	 * 设置退回操作退回到环节的ID
	 */
	public void setReturnActivity(java.lang.String returnActivity) {
		this.returnActivity = returnActivity;
	}

	/**
	 * 获取退回操作退回到环节的ID
	 */
	public java.lang.String getReturnActivity() {
		return returnActivity;
	}

	/**
	 * 设置自由选择的url
	 */
	public void setFreechooseUrl(java.lang.String freechooseUrl) {
		this.freechooseUrl = freechooseUrl;
	}

	/**
	 * 获取自由选择的url
	 */
	public java.lang.String getFreechooseUrl() {
		return freechooseUrl;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark1() {
		return remark1;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.String getRemark2() {
		return remark2;
	}

	/**
	 * 设置
	 */
	public void setRemark3(java.lang.Boolean remark3) {
		this.remark3 = remark3;
	}

	/**
	 * 获取
	 */
	public java.lang.Boolean isRemark3() {
		return remark3;
	}

	/**
	 * 设置${field.desc}
	 */
	public void setRemark4(java.lang.Integer remark4) {
		this.remark4 = remark4;
	}

	/**
	 * 获取${field.desc}
	 */
	public java.lang.Integer getRemark4() {
		return remark4;
	}
}
