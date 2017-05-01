package com.chinacreator.c2.qyb.workflow.activiti.inf;

import java.util.Map;

import org.activiti.engine.task.Task;

public interface IPersistType {

	public String getName();
	
	public Map parseTypeValue(Object value,Object field, Task task, Map entityOld);
}
