package com.chinacreator.c2.qyb.workflow.activiti.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.flow.Exception.C2FlowRuntimeException;
import com.chinacreator.c2.flow.api.WfFormService;
import com.chinacreator.c2.flow.api.WfManagerService;
import com.chinacreator.c2.flow.api.WfRepositoryService;
import com.chinacreator.c2.flow.detail.WfActivity;
import com.chinacreator.c2.flow.detail.WfBusinessData;
import com.chinacreator.c2.flow.detail.WfConstants;
import com.chinacreator.c2.flow.detail.WfModuleBean;
import com.chinacreator.c2.flow.detail.WfOperator;
import com.chinacreator.c2.flow.detail.WfProcessConfigProperty;
import com.chinacreator.c2.flow.detail.WfProcessDefinition;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.inf.IPersistType;
import com.chinacreator.c2.qyb.workflow.common.bean.TabDescriptionWithTabId;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;
import com.chinacreator.c2.qyb.workflow.config.impl.ActivityConfigService;
import com.chinacreator.c2.qyb.workflow.form.entity.FieldPermission;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.form.impl.FieldPermissionService;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;
import com.chinacreator.c2.qyb.workflow.tab.impl.DynamicTabProvider;
import com.chinacreator.c2.workflow.api.WfExtendService;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class WorkProcessEasy {
	
	public final static String PERSIST_KEY = "persistKey";
	public final static String PERSIST_TYPE = "persistType";
	public final static String UI_TYPE_AUDIT_NO = "taskaudit";
	
	public final static String MOBILE_TASK_HANDLE_URL = "workflow/mobile/taskHandle.html";
	
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
	@Autowired
	FieldPermissionService fps;
	@Autowired
	DynamicTabProvider dynamicTabProvider;
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

	public Map<String, Object> taskHandle(String moduleId, String formBusinessKey, String taskId) throws Exception {
		//发起
		if(taskId == null){
			return getModuleStartInfo(moduleId, formBusinessKey);
		}else{
			return getTaskHandleInfo(taskId);
		}
	}

	@Autowired
	WfExtendService wfExtendService;
	@Autowired
	WfManagerService wfManagerService;
	private Map<String, Object> getModuleStartInfo(String moduleId, String formBusinessKey) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 启动流程
		Map<String, String> body = new HashMap<String, String>();
		body.put("moduleId",moduleId);
		
		WfModuleBean wfModuleBean = wfExtendService.queryByMenuCode(moduleId);
		if(null==wfModuleBean)  throw new C2FlowRuntimeException("不存在流程事项菜单["+moduleId+"]");
		
		WfProcessDefinition wfProcessDefinition = wfManagerService.getBindProcessByModuleId(moduleId);
		if(null==wfProcessDefinition) throw new C2FlowRuntimeException("事项菜单["+wfModuleBean.getName()+"]还未和任何流程定义进行绑定！");
		String processDefinitionId = wfProcessDefinition.getId();
		
		String taskDefKey = null;
		ActivityImpl act = workFlowService.getStartActivityByModuleId(moduleId);
		if(act != null){
			taskDefKey = act.getId();
		}		
		
		ServiceProduct serviceProduct = serviceProductService
				.getServiceProductById(moduleId);
		result.put("serviceProduct", serviceProduct);		

		Map params = new HashMap();
		params.put("moduleId", moduleId);
		params.put("formId", serviceProduct.getFormId());
		params.put("processDefinitionId", processDefinitionId);
		params.put("taskDefId", taskDefKey);
		result.put("params",params);		
		
		Map permissonData = fps.getFieldPermissionData(serviceProduct, taskDefKey);
		result.put("permissonData",permissonData);
		
		List<TabDescriptionWithTabId> tabs = dynamicTabProvider.generateProductTabForActivity(moduleId, params, taskDefKey);
		result.put("tabs",tabs);
		
		Map entity = formService.getFormDataByFkFromProcessVariable(serviceProduct.getFormId(),
				null, formBusinessKey, null);
		result.put("entity", entity);
		List<Map> fields = this.getFields(serviceProduct, taskDefKey);
		result.put("fields", fields);
		List<WorkFlowTransition> transitions = workFlowService
				.getOutTransition(null, processDefinitionId, taskDefKey);
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
		Map actions = acfs.getActivityActions(moduleId, taskDefKey);
		result.put("actions", actions);		
		
		return result;
/*		WfProcessConfigProperty wfProcessConfigProperty=findProcessStartConfig(moduleId, processDefinitionId);
//		if(null==wfProcessConfigProperty||StringUtils.isEmpty(wfProcessConfigProperty.getBindForm())) throw new C2FlowRuntimeException("事项["+wfModuleBean.getName()+"]起始节点表单配置为空，无法自动进入启动表单！");
		
		String alias = wfProcessConfigProperty.getAlias();
		String bindForm = wfProcessConfigProperty.getBindForm();
		String configId = wfProcessConfigProperty.getConfigId();
		Integer duration = wfProcessConfigProperty.getDuration();
		String durationUnit = wfProcessConfigProperty.getDurationUnit();
		String performer = wfProcessConfigProperty.getPerformer();
		String taskDefKey = wfProcessConfigProperty.getTaskDefKey();
		String groupPerformer = wfProcessConfigProperty.getGroupPerformer();
		if (!StringUtils.isEmpty(bindForm)) {
			paramMap.put("bindForm",bindForm);
		}
		
		if (!StringUtils.isEmpty(taskDefKey)) {
			paramMap.put("taskDefKey", taskDefKey);
		}
		
		if (!StringUtils.isEmpty(groupPerformer)) {
			paramMap.put("groupPerformer", groupPerformer);
		}
		
		if (!StringUtils.isEmpty(configId)) {
			paramMap.put("configId", configId);
		}
		
		if (!StringUtils.isEmpty(alias)) {
			paramMap.put("alias", alias);
		}
		if (!StringUtils.isEmpty(duration)) {
			paramMap.put("duration", duration);
		}
		if (!StringUtils.isEmpty(durationUnit)) {
			paramMap.put("durationUnit", durationUnit);
		}
		if (!StringUtils.isEmpty(performer)) {
			paramMap.put("performer", performer);
		}
		if (!StringUtils.isEmpty(processDefinitionId)) {
			paramMap.put("processDefinitionId", processDefinitionId);
		}
		
		if (!StringUtils.isEmpty(moduleId)) {
			paramMap.put("moduleId", moduleId);
		}
		
		return paramMap;	
*/			
	}
	
	@Autowired
	WfRepositoryService wfRepositoryService;
	@Autowired
	WfFormService wfFormService;
	/**
	 * 获取开始节点流程配置
	 * @param moduleId
	 * @param processDefinitionId
	 * @return
	 * @throws Exception
	 */
	private WfProcessConfigProperty findProcessStartConfig(String moduleId,String processDefinitionId) throws Exception {
		
		WfProcessConfigProperty wfProcessConfigProperty = null;
		List<WfActivity> wfActivityList = wfRepositoryService.getActivitiesByDefinition(processDefinitionId);
		for (WfActivity wfActivity : wfActivityList) {
			if ("startEvent".equals(wfActivity.getProperties().get("type"))){
				wfProcessConfigProperty = wfManagerService.findProcessConfigProperty(processDefinitionId, moduleId,wfActivity.getId());
			}
		}
		
		// 如果流程定义图中配置了表单，如果外围配置没配置表单，可以用流程定义中的表单，但是外围配置表单优先级>流程定义的表单
		if (null==wfProcessConfigProperty){
			wfProcessConfigProperty = new WfProcessConfigProperty();
		}
		
		if(StringUtils.isEmpty(wfProcessConfigProperty.getBindForm())){
			// 以流程定义中的表单为准
			String bindFormInDefinition = wfFormService.getStartFormKey(processDefinitionId);
			wfProcessConfigProperty.setBindForm(bindFormInDefinition);
		}
		return wfProcessConfigProperty;
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
		result.put("serviceProduct", serviceProduct);
		if( formId == null){
			formId = serviceProduct.getFormId();
		}
		String businessKey = wfData.getBusinessKey();
		String processDefinitionId = task.getProcessDefinitionId();
		String taskDefId = task.getTaskDefinitionKey();
		
		Map params = new HashMap();
		params.put("moduleId", moduleId);
		params.put("formId", formId);
		params.put("businessKey", businessKey);
		params.put("proInsId", proInsId);
		params.put("processDefinitionId", processDefinitionId);
		params.put("taskDefId", taskDefId);
		result.put("params",params);		
		
		Map permissonData = fps.getFieldPermissionData(serviceProduct, taskDefId);
		result.put("permissonData",permissonData);
		
		List<TabDescriptionWithTabId> tabs = dynamicTabProvider.generateProductTabForActivity(moduleId, params, taskDefId);
		result.put("tabs",tabs);
		
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
		if(fp.isWritePermission() && f.getWebDisplayTypeId() != null && f.getWebDisplayTypeId().equals(UI_TYPE_AUDIT_NO)){
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