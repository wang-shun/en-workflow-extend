package com.cool.en.workflow.web.controller;

import java.util.List;
import java.util.Map;

import org.restlet.data.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;
import com.chinacreator.c2.qyb.workflow.config.impl.ActivityConfigService;
import com.chinacreator.c2.qyb.workflow.form.entity.WebDisplayCategory;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;

@RestController
@RequestMapping("flowservice")
public class WorkFlowServiceController {
	@Autowired
	WorkFlowService wfs;
	@Autowired
	FormService formService;
	@Autowired
	ActivityConfigService activityConfigService;
	
	@RequestMapping("gettransitions")
	public List<WorkFlowTransition> getTransitions(@RequestParam() String procDefId,@RequestParam() String activitiId){
		return wfs.getOutTransition(null, procDefId, activitiId);
	}
	
	@RequestMapping("getactivities")
	public List<WorkFlowActivity> getProcessActivities(@RequestParam() String procDefId){
		return wfs.getActivitiesFromProcessDef(procDefId);
	}
	
	@RequestMapping("gettemplatesmap")
	public Map<String, Object> getTemplates(){
		return formService.getAllWebDisplayCategoryMap();
	}
	
	@RequestMapping("gettemplateslist")
	public List<WebDisplayCategory> getTemplates2(){
		return formService.getAllWebDisplayCategoryList();
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getfields",method = RequestMethod.POST)
	public Map<String, List<Map>> getFields(@RequestBody() JSONObject jsonObject){
		String formId = jsonObject.getString("formId");
		String[] fieldType = jsonObject.getJSONArray("fieldType").toArray(new String[0]);
		boolean isClassify = jsonObject.getBooleanValue("isClassify");
		return formService.getFormField(formId, fieldType, isClassify);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getcandidate2",method = RequestMethod.POST)
	public List<Map> getCandidate2(@RequestBody() JSONObject jsonObject){
		String processDefinitionId = jsonObject.getString("processDefinitionId");
		String proInsId = jsonObject.getString("proInsId");
		String moduleId = jsonObject.getString("moduleId");
		String taskDefKey = jsonObject.getString("taskDefKey");
		String curUserId = jsonObject.getString("curUserId");
		return wfs.getHandlerFormCandidateWithFilter1(processDefinitionId, proInsId, 
				moduleId, taskDefKey, null, curUserId);
	}	
	
	@RequestMapping(value = "getactions",method = RequestMethod.POST)
	public Map getActions(@RequestBody() JSONObject jsonObject){
		String activityId = jsonObject.getString("activityId");
		String moduleId = jsonObject.getString("moduleId");
		return activityConfigService.getActivityActions(moduleId, activityId);
	}	
}
