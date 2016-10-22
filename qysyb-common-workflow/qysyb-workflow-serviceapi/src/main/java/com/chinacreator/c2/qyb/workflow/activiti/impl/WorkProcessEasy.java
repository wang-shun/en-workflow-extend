package com.chinacreator.c2.qyb.workflow.activiti.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.flow.detail.WfBusinessData;
import com.chinacreator.c2.flow.detail.WfOperator;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;
import com.chinacreator.c2.qyb.workflow.config.impl.ActivityConfigService;
import com.chinacreator.c2.qyb.workflow.form.entity.FieldPermission;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WorkProcessEasy {
	@Autowired
	TaskService taskService;
	@Autowired
	WorkProcess workProcess;
	@Autowired
	WorkFlowService workFlowService;
	@Autowired
	FormService formService;
	@Autowired
	ServiceProductService serviceProductService;
	@Autowired
	ActivityConfigService acfs;

	/**
	 * 流程流转
	 * 
	 * @param userId
	 * @param taskId
	 * @param entity
	 * @param transition
	 * @param handler
	 * @param ccInformJsonStr
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void goAnyWhereTakeTransition(String userId, String taskId,
			String entity, String transition, String handler,
			String ccInformJsonStr) throws Exception {
		Map paramsMap = new HashMap();

		Task task = taskService.createTaskQuery().taskId(taskId)
				.includeProcessVariables().singleResult();
		Map processVariables = task.getProcessVariables();
		// String entity = (String) paramsMap.get("entity");
		// String isStart = paramsMap.get("isStart").toString();
		// String formId = (String) paramsMap.get("formId");
		// String wfOperatorStr = (String) paramsMap.get("wfOperator");
		// String bussinessKey = (String) paramsMap.get("businessKey");
		// String currenTaskId = (String) paramsMap.get("currenTaskId");
		// String destTaskDefinitionKey = (String) paramsMap
		// .get("destTaskDefinitionKey");
		// String taskDefKey = paramsMap.get("taskDefKey");
		// String processDefinitionId = (String) paramsMap
		// .get("processDefinitionId");
		// String handlerVariablesStr = (String) paramsMap.get("variables");
		// String opinion = (String) paramsMap.get("opinion");
		// String proInsId = (String) paramsMap.get("proInsId");
		// String moduleId = (String) paramsMap.get("moduleId");
		// String transitionId = (String) paramsMap.get("transitionId");
		// String transitionStr = (String) paramsMap.get("transition");
		// String ccInformJsonStr = (String) paramsMap.get("ccInform");

		Map entitymap = JSONObject.parseObject(entity, Map.class);

		String formId = (String) processVariables.get("formId");
		String businessKey = (String) processVariables.get("businessKey");
		String moduleId = (String) processVariables.get("moduleId");
		WfOperator wfOperator = new WfOperator();
		wfOperator.setUserId(userId);
		WfBusinessData businessData = new WfBusinessData();
		businessData.setBusinessKey(businessKey);
		businessData.setModuleId(moduleId);
		wfOperator.setBusinessData(businessData);

		ObjectMapper mapper = new ObjectMapper();
		String wfOperatorStr = mapper.writeValueAsString(wfOperator);
		String currenTaskId = taskId;
		WorkFlowTransition wfTransition = JSONObject.parseObject(transition,
				WorkFlowTransition.class);
		String destTaskDefinitionKey = wfTransition.getDest().getId();
		String processDefinitionId = task.getProcessDefinitionId();
		String handlerVariablesStr = handler;
		String opinion = entitymap.get("opinion") == null ? ""
				: (String) entitymap.get("opinion");
		String proInsId = task.getProcessInstanceId();
		String transitionId = wfTransition.getId();
		if (ccInformJsonStr == null) {
			ccInformJsonStr = "{}";
		}
		paramsMap.put("entity", entity);
		paramsMap.put("formId", formId);
		paramsMap.put("wfOperator", wfOperatorStr);
		paramsMap.put("businessKey", businessKey);
		paramsMap.put("currenTaskId", taskId);
		paramsMap.put("destTaskDefinitionKey", destTaskDefinitionKey);
		paramsMap.put("processDefinitionId", processDefinitionId);
		paramsMap.put("currenTaskId", currenTaskId);
		paramsMap.put("variables", handlerVariablesStr);
		paramsMap.put("opinion", opinion);
		paramsMap.put("proInsId", proInsId);
		paramsMap.put("moduleId", moduleId);
		paramsMap.put("transitionId", transitionId);
		paramsMap.put("transition", transition);
		paramsMap.put("ccInform", ccInformJsonStr);
		workProcess.goAnyWhereTakeTransition(paramsMap);
	}

	/**
	 * 获取一个任务处理前置信息
	 * 
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getTaskHandleInfo(String taskId) {
		Map result = new HashMap();
		Task task = taskService.createTaskQuery().taskId(taskId)
				.includeProcessVariables().singleResult();
		Map processVariables = task.getProcessVariables();
		String proInsId = task.getProcessInstanceId();

		Map entity = formService.getFormDataByFkFromProcessVariable(taskId,
				null, taskId, taskId);
		result.put("entity", entity);
		String formId = (String) processVariables.get("formId");
		String moduleId = (String) processVariables.get("moduleId");
		String businessKey = (String) processVariables.get("businessKey");
		String processDefinitionId = task.getProcessDefinitionId();
		String taskDefId = task.getTaskDefinitionKey();

		ServiceProduct serviceProduct = serviceProductService
				.getServiceProductById(moduleId);
		List<FormField> fields = this.getFields(serviceProduct, businessKey);
		result.put("fields", fields);
		List<WorkFlowTransition> transitions = workFlowService
				.getOutTransition(null, processDefinitionId, taskDefId);
		result.put("transitions", transitions);
		Map actions = acfs.getActivityActions(moduleId, taskDefId);
		result.put("actions", actions);
		return result;
	}

	/**
	 * 获取需要的字段。。
	 * 
	 * @param serviceProduct
	 * @param businessKey
	 * @return
	 */
	private List<FormField> getFields(ServiceProduct serviceProduct,
			String businessKey) {
		Dao<FieldPermission> daofp = DaoFactory.create(FieldPermission.class);
		FormService fs = ApplicationContextManager.getContext().getBean(
				FormService.class);
		Map<String, Map<String, Object>> mapresult = new HashMap<String, Map<String, Object>>();
		List<FormField> listff = fs.getFormField(serviceProduct.getFormId());
		List<FormField> listffresult = new ArrayList<FormField>();
		// 此处的逻辑是不是有点可以优化的意思？
		for (FormField ff : listff) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("fieldNo", ff.getFieldNo());
			FieldPermission fp = new FieldPermission();
			fp.setFieldId(ff);
			fp.setModuleId(serviceProduct.getProductId());
			fp.setBusinessKey(businessKey);
			fp = daofp.selectOne(fp);
			if (fp.isFillNecessary()
					&& ff.getWebDisplayTypeId().equals("AUDIT_OPINION")) {
				listffresult.add(ff);
			}
			/*
			 * if(fp!=null){ hm.put("readPermission", fp.isReadPermission());
			 * hm.put("writePermission", fp.isWritePermission());
			 * hm.put("visible", fp.isVisible()); hm.put("fillnecessary",
			 * fp.isFillNecessary()); }else{ hm.put("readPermission", true);
			 * hm.put("writePermission", true); hm.put("visible", true);
			 * hm.put("fillnecessary", false); }
			 */
			mapresult.put(ff.getFieldNo(), hm);
		}
		return listffresult;
	}

	@SuppressWarnings("unchecked")
	public void goAnyWhere(String userId, String taskId, String entity,
			String destTaskDefinitionKey, boolean useHisassigne,
			String handler, String ccInformJsonStr) throws Exception {
		Map paramsMap = new HashMap();
		// String entity = (String) paramsMap.get("entity");
		// String formId = (String) paramsMap.get("formId");
		// String wfOperatorStr = (String) paramsMap.get("wfOperator");
		// String bussinessKey = (String) paramsMap.get("businessKey");
		// String currenTaskId = (String) paramsMap.get("currenTaskId");
		// String destTaskDefinitionKey = (String) paramsMap
		// .get("destTaskDefinitionKey");
		// String taskDefKey = paramsMap.get("taskDefKey");
		// String processDefinitionId = (String) paramsMap
		// .get("processDefinitionId");
		// String variablesStr = (String) paramsMap.get("variables");
		// String opinion = (String) paramsMap.get("opinion");
		// String proInsId = (String) paramsMap.get("proInsId");
		// String transitionStr = (String) paramsMap.get("transition");
		// String curActivityStr = (String) paramsMap.get("curActivity");

		Map entitymap = JSONObject.parseObject(entity, Map.class);
		Task task = taskService.createTaskQuery().taskId(taskId)
				.includeProcessVariables().singleResult();
		Map processVariables = task.getProcessVariables();

		String formId = (String) processVariables.get("formId");
		String businessKey = (String) processVariables.get("businessKey");
		String moduleId = (String) processVariables.get("moduleId");
		String processDefinitionId = task.getProcessDefinitionId();
		String opinion = entitymap.get("opinion") == null ? ""
				: (String) entitymap.get("opinion");

		WorkFlowActivity wfact = new WorkFlowActivity();
		wfact.setId(task.getTaskDefinitionKey());
		wfact.setName(task.getName());
		// wfact.setPorperties(task.);
		ObjectMapper mapper = new ObjectMapper();
		String curActivityStr = mapper.writeValueAsString(wfact);
		WfOperator wfOperator = new WfOperator();
		wfOperator.setUserId(userId);
		WfBusinessData businessData = new WfBusinessData();
		businessData.setBusinessKey(businessKey);
		businessData.setModuleId(moduleId);
		wfOperator.setBusinessData(businessData);
		String wfOperatorStr = mapper.writeValueAsString(wfOperator);

		paramsMap.put("entity", entity);
		paramsMap.put("formId", formId);
		paramsMap.put("businessKey", businessKey);
		paramsMap.put("moduleId", moduleId);
		paramsMap.put("destTaskDefinitionKey", destTaskDefinitionKey);
		paramsMap.put("currenTaskId", taskId);
		paramsMap.put("variables", handler);
		paramsMap.put("opinion", opinion);
		paramsMap.put("proInsId", task.getProcessInstanceId());
		paramsMap.put("curActivity", curActivityStr);
		paramsMap.put("wfOperator", wfOperatorStr);
		workProcess.goAnyWhere(paramsMap);
	}
}
