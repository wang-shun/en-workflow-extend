package com.chinacreator.c2.omp.service.manage.workflowcommon.bean;

import java.util.Map;

import org.activiti.engine.impl.form.StartFormHandler;
import org.activiti.engine.impl.task.TaskDefinition;

public class WfProcessDefEntity {
	private static final long serialVersionUID = 1L;

    protected String key;
    protected String name;
    protected int revision = 1;
    protected int version;
    protected String category;
    protected String deploymentId;
    protected String resourceName;
    protected String tenantId;
    protected Integer historyLevel;
    protected StartFormHandler startFormHandler;
    protected String diagramResourceName;
    protected boolean isGraphicalNotationDefined;
    protected Map<String, TaskDefinition> taskDefinitions;
    protected Map<String, Object> variables;
    protected boolean hasStartFormKey;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getHistoryLevel() {
		return historyLevel;
	}
	public void setHistoryLevel(Integer historyLevel) {
		this.historyLevel = historyLevel;
	}
	public StartFormHandler getStartFormHandler() {
		return startFormHandler;
	}
	public void setStartFormHandler(StartFormHandler startFormHandler) {
		this.startFormHandler = startFormHandler;
	}
	public String getDiagramResourceName() {
		return diagramResourceName;
	}
	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}
	public boolean isGraphicalNotationDefined() {
		return isGraphicalNotationDefined;
	}
	public void setGraphicalNotationDefined(boolean isGraphicalNotationDefined) {
		this.isGraphicalNotationDefined = isGraphicalNotationDefined;
	}
	public Map<String, TaskDefinition> getTaskDefinitions() {
		return taskDefinitions;
	}
	public void setTaskDefinitions(Map<String, TaskDefinition> taskDefinitions) {
		this.taskDefinitions = taskDefinitions;
	}
	public Map<String, Object> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	public boolean isHasStartFormKey() {
		return hasStartFormKey;
	}
	public void setHasStartFormKey(boolean hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
