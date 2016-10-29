package com.cool.en.workflow.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;

@RestController
@RequestMapping("flowservice")
public class WorkFlowServiceController {
	@Autowired
	WorkFlowService wfs;
	@RequestMapping("gettransitions")
	public List<WorkFlowTransition> getTransitions(@RequestParam() String procDefId,@RequestParam() String activitiId){
		return wfs.getOutTransition(null, procDefId, activitiId);
	}
	
	@RequestMapping("getactivities")
	public List<WorkFlowActivity> getProcessActivities(@RequestParam() String procDefId){
		return wfs.getActivitiesFromProcessDef(procDefId);
	}
}
