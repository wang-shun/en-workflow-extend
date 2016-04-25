package com.chinacreator.c2.omp.service.manage.workflowcommon.bean;

import java.util.Map;

public class WorkFlowActivity {
	public String id;
	public String name;
	public Map porperties;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map getPorperties() {
		return porperties;
	}
	public void setPorperties(Map porperties) {
		this.porperties = porperties;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
