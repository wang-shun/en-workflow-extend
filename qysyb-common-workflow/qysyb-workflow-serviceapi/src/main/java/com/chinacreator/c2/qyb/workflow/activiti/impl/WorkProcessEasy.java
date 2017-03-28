package com.chinacreator.c2.qyb.workflow.activiti.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.flow.detail.WfBusinessData;
import com.chinacreator.c2.flow.detail.WfConstants;
import com.chinacreator.c2.flow.detail.WfOperator;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.message.channal.MessageChannel;
import com.chinacreator.c2.qyb.workflow.activiti.inf.IPersistType;
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
	
	public final static String PERSIST_KEY = "persistKey";
	public final static String PERSIST_TYPE = "persistType";
	public final static String UI_TYPE_AUDIT_NO = "taskaudit";
	
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
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object goAnyWhereTakeTransition(String userId, String taskId,
			Object entity, String transition, String handler,
			String ccInformJsonStr) throws Exception {
		Map paramsMap = new HashMap();
		Task task = taskService.createTaskQuery().taskId(taskId)
				.includeProcessVariables().singleResult();
		Map processVariables = task.getProcessVariables();

		if(userId == null){
			AccessControlServiceImpl access = ApplicationContextManager.getContext().getBean(AccessControlServiceImpl.class);
			userId = access.getUserID();
		}
		WfBusinessData wfData = (WfBusinessData) processVariables.get(WfConstants.WF_BUSINESS_DATA_KEY);
		
		
		String formId = (String) processVariables.get("formId");
		String businessKey = wfData.getBusinessKey();
		String moduleId = (String) processVariables.get("moduleId");
		ServiceProduct serviceProduct = serviceProductService
				.getServiceProductById(moduleId);
		if( formId == null){
			formId = serviceProduct.getFormId();
		}		
		WfOperator wfOperator = new WfOperator();
		wfOperator.setUserId(userId);
		wfOperator.setBusinessData(wfData);
		
		ObjectMapper mapper = new ObjectMapper();
		String wfOperatorStr = mapper.writeValueAsString(wfOperator);
		String currenTaskId = taskId;
		WorkFlowTransition wfTransition = JSONObject.parseObject(transition,
				WorkFlowTransition.class);
		String destTaskDefinitionKey = wfTransition.getDest().getId();
		String processDefinitionId = task.getProcessDefinitionId();
		String handlerVariablesStr = handler;

		String proInsId = task.getProcessInstanceId();
		String transitionId = wfTransition.getId();
		if (ccInformJsonStr == null) {
			ccInformJsonStr = "{}";
		}
		Map entityold = formService.getFormDataByFkFromProcessVariable(formId,
				null, businessKey, proInsId);
		Map entitymap = parseEntityFromType(entity, task, entityold);
		if(entitymap == null){
			entitymap = new HashMap();
		}
		String opinion = entitymap.get("opinion") == null ? ""
				: (String) entitymap.get("opinion");
		entityold.putAll(entitymap);
		entity = new JSONObject(entityold).toString();
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
		paramsMap.put("transition", JSON.toJSONString(wfTransition));
		paramsMap.put("ccInform", ccInformJsonStr);
		return workProcess.goAnyWhereTakeTransition(paramsMap);
	}
	
	private Map parseEntityFromType(Object o, Task task, Map entityOld){
		if(o == null){
			return null;
		}
		if(o instanceof Map){
			return (Map) o;
		}else if(o instanceof List){
			List list = (List) o;
			for(int i=0;i<list.size();i++){
				Map jo = (Map) list.get(i);
				Map field = (Map) jo.get("field");
				Object value = jo.get("value");
				String type = (String) field.get("persistType");
				Map<String, IPersistType> types = ApplicationContextManager
						.getContext().getBeansOfType(IPersistType.class);
				for (IPersistType persistType : types.values()) {
					String name = persistType.getName();
					if (type.equals(name)) {
						Map map = persistType.parseTypeValue(value, field, task, entityOld);
						return map;
					} 
				}			
			}
		}
		return null;
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
		String productNo = (String) processVariables.get("productNo");
		String moduleId = (String) processVariables.get("moduleId");
		String formId = (String) processVariables.get("formId");
		WfBusinessData wfData = (WfBusinessData) processVariables.get(WfConstants.WF_BUSINESS_DATA_KEY);

		
		ServiceProduct serviceProduct = serviceProductService
				.getServiceProductById(moduleId);
		if( formId == null){
			formId = serviceProduct.getFormId();
		}
		String businessKey = wfData.getBusinessKey();
		String processDefinitionId = task.getProcessDefinitionId();
		String taskDefId = task.getTaskDefinitionKey();
		Map entity = formService.getFormDataByFkFromProcessVariable(formId,
				null, businessKey, proInsId);
		result.put("entity", entity);
		List<Map> fields = this.getFields(serviceProduct, taskDefId);
		result.put("fields", fields);
		List<WorkFlowTransition> transitions = workFlowService
				.getOutTransition(null, processDefinitionId, taskDefId);
		//获取下一步的节点的一些属性
		for(int i=0;i<transitions.size();i++){
			WorkFlowTransition wft = transitions.get(i);
			WorkFlowActivity destActivity = wft.getDest();
			String destActId = destActivity.getId();
			Map destActions = acfs.getActivityActions(moduleId, destActId);
			Map proper = destActivity.getPorperties();
			proper.put("actions", destActions);
			destActivity.setPorperties(proper);
		}
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
	private List<Map> getFields(ServiceProduct serviceProduct,
			String businessKey) {
		Dao<FieldPermission> daofp = DaoFactory.create(FieldPermission.class);
		FormService fs = ApplicationContextManager.getContext().getBean(
				FormService.class);
		List<Map> mapresult = new ArrayList<Map>();
		List<FormField> listff = fs.getFormField(serviceProduct.getFormId());
		// 此处的逻辑是不是有点可以优化的意思？
		for (FormField ff : listff) {
			FieldPermission fp = new FieldPermission();
			fp.setFieldId(ff);
			fp.setModuleId(serviceProduct.getProductId());
			fp.setBusinessKey(businessKey);
			fp = daofp.selectOne(fp);
			Map hm = getMapField(ff, fp);
			if(hm != null){
				mapresult.add(hm);
				continue;
			}			

		}
		return mapresult;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getMapField(FormField f, FieldPermission fp){
		if(fp == null){
			return null;
		}
		
		if(fp.isWritePermission() && fp.isFillNecessary() 
				&& !f.getWebDisplayTypeId().equals(UI_TYPE_AUDIT_NO)){
			return null;
		}
		//通用审核表单  可写 就表示需要填写意见 --历史问题
		if(fp.isWritePermission() && f.getWebDisplayTypeId().equals(UI_TYPE_AUDIT_NO)){
			Map hm = new HashMap();
			hm.put("fieldNo", f.getFieldNo());
			hm.put("fieldName", f.getFieldName());
//			hm.put("readPermission", fp.isReadPermission());
//			hm.put("writePermission", fp.isWritePermission());
//			hm.put("visible", fp.isVisible());
//			hm.put("fillnecessary", fp.isFillNecessary());
			hm.put("webDisplayType",f.getWebDisplayTypeId());
			//  
			// 放入IPersistType处理
//			if(UI_TYPE_AUDIT_NO.equals(f.getWebDisplayTypeId())){
//				hm.put(PERSIST_KEY, WorkProcess.INLINE_AUDIT_KEY);
//			}else{
//				hm.put(PERSIST_KEY,f.getFieldNo());
//			}
			String pType = f.getFieldType();
			if(pType == null){
				pType = "STR";
			}
			hm.put(PERSIST_TYPE,pType);	
			return hm;			
		}
		return null;
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
	
	@SuppressWarnings("unchecked")
	public Object rejectGoAnyWhere(String taskId,
			String entity,String destTaskDefinitionKey) throws Exception{
		Map paramsMap = new HashMap();
		Task task = taskService.createTaskQuery().taskId(taskId)
				.includeProcessVariables().singleResult();
		Map entitymap = JSONObject.parseObject(entity, Map.class);
		Map processVariables = task.getProcessVariables();
		String formId = (String) processVariables.get("formId");
		String businessKey = (String) processVariables.get("businessKey");
		String moduleId = (String) processVariables.get("moduleId");
		String processDefinitionId = task.getProcessDefinitionId();
		String proInsId = task.getProcessInstanceId();
		ServiceProduct serviceProduct = serviceProductService
				.getServiceProductById(moduleId);
		String userId = null;
		if(userId == null){
			AccessControlServiceImpl access = ApplicationContextManager.getContext().getBean(AccessControlServiceImpl.class);
			userId = access.getUserID();
		}
		if( formId == null){
			formId = serviceProduct.getFormId();
		}	
		WfOperator wfOperator = new WfOperator();
		wfOperator.setUserId(userId);
		WfBusinessData businessData = new WfBusinessData();
		businessData.setBusinessKey(businessKey);
		businessData.setModuleId(moduleId);
		wfOperator.setBusinessData(businessData);
		ObjectMapper mapper = new ObjectMapper();
		String wfOperatorStr = mapper.writeValueAsString(wfOperator);
		WorkFlowActivity wfact = new WorkFlowActivity();
		wfact.setId(task.getTaskDefinitionKey());
		wfact.setName(task.getName());
		// wfact.setPorperties(task.);
		String curActivityStr = mapper.writeValueAsString(wfact);
		String opinion = entitymap.get("opinion") == null ? ""
				: (String) entitymap.get("opinion");
//		WorkFlowTransition wfTransition = JSONObject.parseObject(transition,
//				WorkFlowTransition.class);
//		String destTaskDefinitionKey = wfTransition.getDest().getId();
		
		paramsMap.put("entity", entity);
		paramsMap.put("formId", formId);
		paramsMap.put("wfOperator", wfOperatorStr);
		paramsMap.put("businessKey", businessKey);
		paramsMap.put("currenTaskId", taskId);
		paramsMap.put("destTaskDefinitionKey", destTaskDefinitionKey);
		paramsMap.put("processDefinitionId", processDefinitionId);
		paramsMap.put("currenTaskId", taskId);
		//paramsMap.put("variables", handler);
		paramsMap.put("opinion", opinion);
		paramsMap.put("proInsId", proInsId);
		//paramsMap.put("moduleId", moduleId);
		//paramsMap.put("transitionId", transitionId);
		paramsMap.put("curActivity", curActivityStr);
		//paramsMap.put("transition", transition);
		//paramsMap.put("ccInform", ccInformJsonStr);
		
		return workProcess.goAnyWhere(paramsMap);
	}
	/**
	 * 获取统一待办的formId
	 * @return
	 */
	public String getUnionFormId(){
		//TODO 配置化
		return "PAqyQdT0SmKJ9Cj2O9-elA";
	}
}