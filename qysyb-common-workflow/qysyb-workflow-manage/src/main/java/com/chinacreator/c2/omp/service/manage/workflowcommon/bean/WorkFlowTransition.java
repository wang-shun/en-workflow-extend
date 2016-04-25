package com.chinacreator.c2.omp.service.manage.workflowcommon.bean;

import java.util.Map;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class WorkFlowTransition {
	public PvmTransition pvmTransition;
	public String id;
	public Map properties;
	public String name;
	public WorkFlowActivity dest;
	public WorkFlowActivity src;
	public PvmTransition getPvmTransition() {
		return pvmTransition;
	}
	public void setPvmTransition(PvmTransition pvmTransition) {
		this.pvmTransition = pvmTransition;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map getProperties() {
		return properties;
	}
	public void setProperties(Map properties) {
		this.properties = properties;
	}
	public WorkFlowActivity getDest() {
		return dest;
	}
	public void setDest(WorkFlowActivity dest) {
		this.dest = dest;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WorkFlowActivity getSrc() {
		return src;
	}
	public void setSrc(WorkFlowActivity src) {
		this.src = src;
	}
}
