package com.chinacreator.c2.qyb.workflow.activiti.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.advanced.role.service.RoleService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.flow.WfApiFactory;
import com.chinacreator.c2.flow.api.WfRuntimeService;
import com.chinacreator.c2.flow.cmd.JumpActivityCmd;
import com.chinacreator.c2.flow.detail.WfConstants;
import com.chinacreator.c2.flow.detail.WfOperator;
import com.chinacreator.c2.flow.detail.WfResult;
import com.chinacreator.c2.flow.detail.WfTaskAction;
import com.chinacreator.c2.flow.util.LoggerManager;
import com.chinacreator.c2.flow.util.LoggerManager.LoggerType;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.exception.ServiceException;
import com.chinacreator.c2.qyb.workflow.activiti.cmd.DelTaskCandidatesCmd;
import com.chinacreator.c2.qyb.workflow.activiti.cmd.FindTaskEntityCmd;
import com.chinacreator.c2.qyb.workflow.activiti.cmd.JumpActivityByTakeTransitionCmd;
import com.chinacreator.c2.qyb.workflow.activiti.cmd.RecoverTaskCmd;
import com.chinacreator.c2.qyb.workflow.activiti.inf.IFormOperate;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;
import com.chinacreator.c2.qyb.workflow.form.entity.Form;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.form.impl.FormFieldService;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;
import com.chinacreator.c2.qyb.workflow.group.impl.UserJobService;
import com.chinacreator.c2.qyb.workflow.inform.impl.InformService;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;
import com.chinacreator.c2.qyb.workflow.sla.entity.ServiceAgreement;
import com.chinacreator.c2.qyb.workflow.sla.impl.ServiceAgreementService;
import com.chinacreator.c2.web.controller.ResponseFactory;

/**
 * 
 * <p>
 * 事件工单流程控制服务类
 * </p>
 * 
 * @author qiao.li
 * @version 1.1
 * @date 2015-5-22
 */
@Service
public class WorkProcess {
	@Autowired
	private IdentityService identityService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private WfRuntimeService wfRuntimeService;
	@Autowired
	private FormFieldService ffs;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private InformService informService;

	public final static String HANDLE_TYPE_KEY = "handleKey";
	public final static String HANDLE_VALUE_KEY = "handleValue";
	public final static Boolean AUTORUN_FIRST_ACT = true;
	/**
	 * 流程启动
	 * 
	 * @param wfOperator
	 * @param bussinessId
	 * @param processDefinitionId
	 * @param variables
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public WfResult startFlow(WfOperator wfOperator, String bussinessId,
			String processDefinitionId, Map variables) {
		WfResult wfResult = null;
		try {
			wfResult = WfApiFactory.getWfRuntimeService()
					.startProcessInstanceById(wfOperator, bussinessId,
							processDefinitionId, variables);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfResult;
	}

	/**
	 * 处理任务
	 * 
	 * @param wfOperator
	 * @param taskId
	 * @param action
	 * @param userToDelegateTo
	 * @param variables
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public WfResult operateTask(WfOperator wfOperator, String taskId,
			String action, String userToDelegateTo, Map variables) {
		WfResult wfResult = null;
		try {
			// entity.setCreatedate(null);
			// entity.setTaskid(taskId);
			WfTaskAction wfaction = null;
			if (action.equals("claim") || action.equals("CLAIM")) {
				wfaction = WfTaskAction.CLAIM;
			} else if (action.equals("complete") || action.equals("COMPLETE")) {
				wfaction = WfTaskAction.COMPLETE;
			} else if (action.equals("claim_complete")
					|| action.equals("CLAIM_COMPLETE")) {
				wfaction = WfTaskAction.CLAIM_COMPLETE;
			} else {
				wfResult = new WfResult();
				wfResult.setResult("300");
				return wfResult;
			}

			wfResult = WfApiFactory.getWfRuntimeService().operateTask(
					wfOperator, taskId, wfaction, userToDelegateTo, variables);
		} catch (Exception e) {
			e.printStackTrace();
			wfResult = new WfResult();
			wfResult.setResult("404");
			return wfResult;
		}
		return wfResult;
	}

	/**
	 * 挂起流程实例
	 * 
	 * @author qiao.li
	 * @param entity
	 *            业务实体
	 * @param wfOperator
	 *            操作者信息，必须参数
	 * @param processInstanceId
	 *            流程实例id,必须参数
	 * @return 流程操作结果对象
	 * @throws Exception
	 */

	@Transactional
	public WfResult suspendProcessInstanceImp(WfOperator wfOperator,
			String processInstanceId) throws Exception {
		WfResult wfresult = WfApiFactory.getWfRuntimeService()
				.suspendProcessInstance(wfOperator, processInstanceId);
		// entity.setTasknextid(wfresult.getNextTaskId());
		// eventWorkService.addEventWork(entity);
		return wfresult;
	}

	/**
	 * 回退流程活动，注意:如果被驳回活动有多个来源活动,且没有制定目标驳回活动的时候,随机驳回
	 * 
	 * @param wfOperator
	 *            操作者信息，必须参数
	 * @param currenTaskId
	 *            当前任务id, 必须参数
	 * @param rejectMessage
	 *            驳回信息
	 * @param variables
	 *            流程参数， 可以为null
	 * @return 流程操作结果对象
	 * @throws Exception
	 */
	@Transactional
	public WfResult rejectImp(WfOperator wfOperator, String currenTaskId,
			String rejectMessage, Map<String, Object> variables) {

		// entity.setTaskid(currenTaskId);
		WfResult wfresult = null;
		try {
			wfresult = WfApiFactory.getWfRuntimeService().reject(wfOperator,
					currenTaskId, rejectMessage, variables);

			// entity.setExecutor(wfOperator.getUserId());
			// entity.setTasknextid(wfresult.getNextTaskId());
			// eventWorkService.addEventWork(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			wfresult = new WfResult();
			wfresult.setResult("404");
			return wfresult;
		}
		return wfresult;
	}

	/**
	 * 根据流程实例id删除流程实例
	 * 
	 * @param wfOperator
	 *            操作者信息，必须参数
	 * @param deleteReason
	 *            删除原因
	 * @param processInstanceId
	 *            流程实例id,必须参数
	 * @return 200-操作成功、300-参数不正确、400-操作失败、404-操作对象不存在
	 * @throws Exception
	 */
	public String deleteProcessInstancesById(WfOperator wfOperator,
			String deleteReason, String processInstanceId) throws Exception {
		String result = WfApiFactory.getWfRuntimeService()
				.deleteProcessInstancesById(wfOperator, deleteReason,
						processInstanceId);
		// 应当保留业务数据，不应删除
		// if (result == "200") {
		// eventWorkService.deleteEventWorkByBusId(entity.getBusinesskey());
		// }
		return result;
	}

	/**
	 * 
	 * @param paramsMap
	 * @return
	 */
	public Object recoverTask(Map<String, Object> paramsMap) {
		String processInstanceId = (String) paramsMap.get("proInsId");
		String recoverReason = (String) paramsMap.get("recoverReason");
		String recoverToActivityId = (String) paramsMap.get("recoverToActivityId");
		String variablesStr = (String) paramsMap.get("variables");
		String userId = (String) paramsMap.get("userId");
		String ccInformJsonStr = (String) paramsMap.get("ccInform");
		Map variables = JSONObject.parseObject(variablesStr, Map.class);
		RecoverTaskCmd recoverTaskCmd = new RecoverTaskCmd(processInstanceId,
				recoverReason, recoverToActivityId, variables,userId);
		WfResult wfresult = managementService.executeCommand(recoverTaskCmd);
		/* 通知处理 */
		//informService = ApplicationContextManager.getContext().getBean(
		//		InformService.class);
		//informService.setCcInformsInfo(ccInformJsonStr);
		//informService.informDo();
		return new ResponseFactory().createResponseBodyJSONObject(JSON
				.toJSONString(wfresult));
	}

	/**
	 * 
	 * @param processInstanceId
	 * @param recoverReason
	 * @param recoverToActivityId
	 * @param variables
	 * @return
	 */
	public WfResult recoverTask(String processInstanceId, String recoverReason,
			String recoverToActivityId, Map variables,String userId) {
		RecoverTaskCmd recoverTaskCmd = new RecoverTaskCmd(processInstanceId,
				recoverReason, recoverToActivityId, variables,userId);
		WfResult wfresult = managementService.executeCommand(recoverTaskCmd);
		return wfresult;
	}

	/**
	 * 这个自由流就是完全自由流，任意节点跳跃。先摧毁当前execution，然后执行新的execution。
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object goAnyWhere(Map<String, Object> paramsMap) throws Exception {
		/* candidateUsers candidateGroup assignee */
		WfResult wfresult = null;
		/* candidateUsers candidateGroup assignee */
		String entity = (String) paramsMap.get("entity");
		String formId = (String) paramsMap.get("formId");
		String wfOperatorStr = (String) paramsMap.get("wfOperator");
		String bussinessKey = (String) paramsMap.get("businessKey");
		String currenTaskId = (String) paramsMap.get("currenTaskId");
		String destTaskDefinitionKey = (String) paramsMap
				.get("destTaskDefinitionKey");
		// String taskDefKey = paramsMap.get("taskDefKey");
		String processDefinitionId = (String) paramsMap
				.get("processDefinitionId");
		String variablesStr = (String) paramsMap.get("variables");
		String opinion = (String) paramsMap.get("opinion");
		String proInsId = (String) paramsMap.get("proInsId");
		String transitionStr = (String) paramsMap.get("transition");
		String curActivityStr = (String) paramsMap.get("curActivity");
		WorkFlowTransition wfTransition = JSONObject.parseObject(transitionStr,
				WorkFlowTransition.class);
		WorkFlowActivity curActivity = JSONObject.parseObject(curActivityStr,
				WorkFlowActivity.class);
		WfOperator wfOperator = JSONObject.parseObject(wfOperatorStr,
				WfOperator.class);
		Map variables = JSONObject.parseObject(variablesStr, Map.class);
		
		WorkFlowActivity nextActivity = new WorkFlowActivity();
		nextActivity.setId(destTaskDefinitionKey);
		// 处理人选择
		chooseHandleTypeValue(variables);

		Map entitymap = JSONObject.parseObject(entity, Map.class);
		FormService formService = ApplicationContextManager.getContext()
				.getBean(FormService.class);
		Form form = formService.getFormById(formId);

		// 业务数据吧上一次保存的一些流程变量去掉
		entitymap.remove(WorkFlowService.TYPE_ASSIGNEE);
		entitymap.remove(WorkFlowService.TYPE_CANDIDATEUSERS);
		entitymap.remove(WorkFlowService.TYPE_CANDIDATEGROUPS);
		entitymap.remove(WorkFlowService.TYPE_ASSIGNEELIST);
		entitymap.remove(HANDLE_TYPE_KEY);
		entitymap.remove(HANDLE_VALUE_KEY);
		/* 业务数据持久化 */
		if (form != null && form.isIsTableStorage() != null
				&& form.isIsTableStorage()) {
			formService.updateFormDataWithExternalTable(bussinessKey, proInsId,
					entity, curActivity, form, wfOperator.getUserId(),nextActivity,paramsMap);
			List<FormField> list = formService.getFormField(formId);
			for (FormField ff : list) {
				if (ffs.isFieldStorageProcessVariable(ff)) { // 字段是否存在流程变量中
					String fieldNo = ff.getFieldNo();
					Object value= entitymap.get(fieldNo);
					if(value!=null){
						//空字符串不存 也意味着不能把流程变量更新为空字符串！！
						if((value instanceof String&&!value.equals(""))){
							variables.put(fieldNo,value);
						}else{
							//对象存入流程变量了
							variables.put(fieldNo,value);
						}
					}
				}				
			}
		} else {
			/* 业务数据作为流程变量保存 */
			variables.putAll(entitymap);
		}

		if (opinion != null) {
			Authentication.setAuthenticatedUserId(wfOperator.getUserId());
			taskService.addComment(currenTaskId, proInsId, opinion);
		}
		variables.put("userId", wfOperator.getUserId());
		
		//设置last activity 信息
		setLastHandlerInfo(wfOperator.getUserId(), wfOperator.getUserCName()
				, variables, curActivity);
		
		wfresult = this.goAnyWhere(wfOperator, false, bussinessKey,
				processDefinitionId, currenTaskId, destTaskDefinitionKey, true,
				variables);
		/* 通知处理 */
		informService = ApplicationContextManager.getContext().getBean(
				InformService.class);
		informService.informDo();
		return new ResponseFactory().createResponseBodyJSONObject(JSON
				.toJSONString(wfresult));

	}

	@Transactional
	public WfResult goAnyWhere(WfOperator wfOperator, boolean isStart,
			String bussinessKey, String processDefinitionId, String taskId,
			java.lang.String destTaskDefinitionKey, boolean useHisAssignee,
			java.util.Map<java.lang.String, java.lang.Object> variables)
			throws Exception {

		WfResult result = new WfResult();
		String userId = wfOperator.getUserId();
		try {
			// 用来设置操作流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(userId);

			if (variables == null) {
				variables = new HashMap<String, Object>();
			}
			if (variables != null) {
				variables.put(WfConstants.WF_BUSINESS_DATA_KEY,
						wfOperator.getBusinessData());
				for (String key : variables.keySet()) {
					if ("".equals(variables.get(key))) {
						variables.put(key, null);
					}
				}
			}

			if (isStart) {
				String processInstanceId = null;
				String nextTaskIds = taskTransferOutLine(processDefinitionId,
						bussinessKey, processInstanceId, destTaskDefinitionKey,
						WfConstants.JUMPREASON_GOANYWHERE, null, variables);

				result.setResult(WfConstants.WF_CONTROL_EXE_SUCCESS);
				result.setProcessInstanceId(processInstanceId);
				result.setNextTaskId(nextTaskIds);
			} else {
				// 先声明可以避免流程图上处理人出现null
				taskService.claim(taskId, userId);
				// 获得当前任务的对应实列
				Task taskEntity = taskService.createTaskQuery().taskId(taskId)
						.singleResult();
				String destAssignee = "";
				if (useHisAssignee) {// 设置历史执行人
					// 获得当前流程实例的历史任务,按照任务id排序，这样就可以只取上一环节最近的任务实例
					String processInstanceId = taskEntity
							.getProcessInstanceId();
					List<HistoricTaskInstance> historicTaskInstances = historyService
							.createHistoricTaskInstanceQuery()
							.taskDefinitionKey(destTaskDefinitionKey)
							.processInstanceId(processInstanceId)
							.orderByHistoricTaskInstanceEndTime().desc().list();

					destAssignee = historicTaskInstances.get(0).getAssignee();

				}
				// 因为流程定义环节有candidateUsers 变量，这个值不能为空。TODO
				// 把流程定义中对candidateusers的依赖给去掉
				// variables.put("candidateUsers",destAssignee);

				String nextTaskIds = taskTransferOutLine(null, null,
						taskEntity.getProcessInstanceId(),
						destTaskDefinitionKey,
						WfConstants.JUMPREASON_GOANYWHERE, destAssignee,
						variables);
				result.setNextTaskId(nextTaskIds);
				result.setProcessInstanceId(taskEntity.getProcessInstanceId());
			}
			result.setResult(WfConstants.WF_CONTROL_EXE_SUCCESS);
			LoggerManager
					.log(getClass(),
							LoggerType.DEBUG,
							wfOperator,
							null,
							"自由流转成功：isStart={}, bussinessKey={}, processDefinitionId={}, taskId={}, destTaskDefinitionKey={}, useHisAssignee={}, variables={}",
							isStart, bussinessKey, processDefinitionId, taskId,
							destTaskDefinitionKey, useHisAssignee, variables);
		} catch (Exception e) {
			LoggerManager
					.log(getClass(),
							LoggerType.ERROR,
							wfOperator,
							e,
							"自由流转失败：isStart={}, bussinessKey={}, processDefinitionId={}, taskId={}, destTaskDefinitionKey={}, useHisAssignee={}, variables={}",
							isStart, bussinessKey, processDefinitionId, taskId,
							destTaskDefinitionKey, useHisAssignee, variables);
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;

	}

	/**
	 * 当流程定义ID不为空时代表启动流程自由流
	 * 
	 * @param processDefinitionId
	 * @param bussinessKey
	 * @param processInstanceId
	 * @param jumpToActivityId
	 * @param jumpReason
	 * @param destAssignee
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	private String taskTransferOutLine(String processDefinitionId,
			String bussinessKey, String processInstanceId,
			String jumpToActivityId, String jumpReason, String destAssignee,
			Map<String, Object> variables) throws Exception {
		String nextTaskIds = null;
		try {
			// jump
			JumpActivityCmd jumpActivityCmd = new JumpActivityCmd(
					processDefinitionId, bussinessKey, processInstanceId,
					jumpToActivityId, jumpReason, variables);
			processInstanceId = managementService
					.executeCommand(jumpActivityCmd);
			nextTaskIds = wfRuntimeService
					.getCurrentActiveTaskIds(processInstanceId);
			if (destAssignee != null && !"".equals(destAssignee)) {
				// 先把之前的候选给去掉 有可能是平台赋的值
				managementService.executeCommand(new DelTaskCandidatesCmd(
						nextTaskIds));
				taskService.addCandidateUser(nextTaskIds, destAssignee);
			}
		} catch (Exception e) {
			throw e;
		}
		return nextTaskIds;
	}

	/**
	 * 选定路径的自由流
	 * 
	 * @param wfOperator
	 * @param isStart
	 * @param bussinessKey
	 * @param processDefinitionId
	 * @param taskId
	 * @param transition
	 *            路径ID
	 * @param destTaskDefinitionKey
	 * @param useHisAssignee
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	public WfResult goAnyWhereTakeTransition(WfOperator wfOperator,
			boolean isStart, String bussinessKey, String processDefinitionId,
			String taskId, String transition, String destTaskDefinitionKey,
			boolean useHisAssignee, Map<String, Object> variables)
			throws Exception {
		WfResult result = new WfResult();
		String userId = wfOperator.getUserId();
		try {
			// 用来设置操作流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(userId);

			if (variables == null) {
				variables = new HashMap<String, Object>();
			}
			if (variables != null) {
				variables.put(WfConstants.WF_BUSINESS_DATA_KEY,
						wfOperator.getBusinessData());
				for (String key : variables.keySet()) {
					if ("".equals(variables.get(key))) {
						variables.put(key, null);
					}
				}
			}

			if (isStart) {
				// String processInstanceId = null;
				// String nextTaskIds = taskTransferOutLine(
				// processDefinitionId, bussinessKey, processInstanceId,
				// destTaskDefinitionKey,
				// WfConstants.JUMPREASON_GOANYWHERE, null, variables);
				//
				// result.setResult(WfConstants.WF_CONTROL_EXE_SUCCESS);
				// result.setProcessInstanceId(processInstanceId);
				// result.setNextTaskId(nextTaskIds);
			} else {
				// 获得当前任务的对应实列
				Task taskEntity = taskService.createTaskQuery().taskId(taskId)
						.singleResult();
				String destAssignee = "";
				if (useHisAssignee) {// 设置历史执行人
					// 获得当前流程实例的历史任务,按照任务id排序，这样就可以只取上一环节最近的任务实例
					String processInstanceId = taskEntity
							.getProcessInstanceId();
					List<HistoricTaskInstance> historicTaskInstances = historyService
							.createHistoricTaskInstanceQuery()
							.processInstanceId(processInstanceId)
							.orderByTaskId().desc().list();

					for (HistoricTaskInstance hti : historicTaskInstances) {
						if (destTaskDefinitionKey.equals(hti
								.getTaskDefinitionKey())) {
							destAssignee = hti.getAssignee();
						}
					}
				}

				String nextTaskIds = taskTransferOutLineWithTransition(null,
						null, taskEntity.getProcessInstanceId(), taskId,
						destTaskDefinitionKey, transition,
						WfConstants.JUMPREASON_GOANYWHERE, destAssignee,
						variables);
				result.setNextTaskId(nextTaskIds);
				result.setProcessInstanceId(taskEntity.getProcessInstanceId());
			}
			result.setResult(WfConstants.WF_CONTROL_EXE_SUCCESS);
			LoggerManager
					.log(getClass(),
							LoggerType.DEBUG,
							wfOperator,
							null,
							"自由流转成功：isStart={}, bussinessKey={}, processDefinitionId={}, taskId={}, destTaskDefinitionKey={}, useHisAssignee={}, variables={}",
							isStart, bussinessKey, processDefinitionId, taskId,
							destTaskDefinitionKey, useHisAssignee, variables);
		} catch (Exception e) {
			LoggerManager
					.log(getClass(),
							LoggerType.ERROR,
							wfOperator,
							e,
							"自由流转失败：isStart={}, bussinessKey={}, processDefinitionId={}, taskId={}, destTaskDefinitionKey={}, useHisAssignee={}, variables={}",
							isStart, bussinessKey, processDefinitionId, taskId,
							destTaskDefinitionKey, useHisAssignee, variables);
			throw e;
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;

	}

	/**
	 * 执行跳转
	 * 
	 * @param processDefinitionId
	 * @param bussinessKey
	 * @param processInstanceId
	 * @param jumpToActivityId
	 * @param transition
	 * @param jumpReason
	 * @param destAssignee
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	private String taskTransferOutLineWithTransition(
			String processDefinitionId, String bussinessKey,
			String processInstanceId, String taskId, String jumpToActivityId,
			String transition, String jumpReason, String destAssignee,
			Map<String, Object> variables) throws Exception {
		String nextTaskIds = null;
		try {
			// jump
			JumpActivityByTakeTransitionCmd jumpActivityCmd = new JumpActivityByTakeTransitionCmd(
					processDefinitionId, bussinessKey, processInstanceId,
					taskId, jumpToActivityId, transition, jumpReason, variables);
			processInstanceId = managementService
					.executeCommand(jumpActivityCmd);

			nextTaskIds = wfRuntimeService
					.getCurrentActiveTaskIds(processInstanceId);
			if (destAssignee != null && !"".equals(destAssignee)) {
				taskService.setAssignee(nextTaskIds, destAssignee);
			}
		} catch (Exception e) {
			throw e;
		}
		return nextTaskIds;
	}

	/**
	 * 这个自由流有一定的限制，必须指定出走的线路，即transition。并且线路为当前环节出去的线路
	 * 
	 * @param paramsMap
	 *            字符串化后的参数
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public Object goAnyWhereTakeTransition(Map<String, Object> paramsMap)
			throws Exception {

		WfResult wfresult = null;
		/* candidateUsers candidateGroup assignee */
		String entity = (String) paramsMap.get("entity");
		String isStart = paramsMap.get("isStart").toString();
		String formId = (String) paramsMap.get("formId");
		String wfOperatorStr = (String) paramsMap.get("wfOperator");
		String bussinessKey = (String) paramsMap.get("businessKey");
		String currenTaskId = (String) paramsMap.get("currenTaskId");
		String destTaskDefinitionKey = (String) paramsMap
				.get("destTaskDefinitionKey");
		// String taskDefKey = paramsMap.get("taskDefKey");
		String processDefinitionId = (String) paramsMap
				.get("processDefinitionId");
		String variablesStr = (String) paramsMap.get("variables");
		String opinion = (String) paramsMap.get("opinion");
		String proInsId = (String) paramsMap.get("proInsId");
		String moduleId = (String) paramsMap.get("moduleId");
		String transitionId = (String) paramsMap.get("transitionId");
		String transitionStr = (String) paramsMap.get("transition");
		String ccInformJsonStr = (String) paramsMap.get("ccInform");
		WorkFlowTransition wfTransition = JSONObject.parseObject(transitionStr,
				WorkFlowTransition.class);

		WfOperator wfOperator = JSONObject.parseObject(wfOperatorStr,
				WfOperator.class);
		Map variables = JSONObject.parseObject(variablesStr, Map.class);
		Map entitymap = JSONObject.parseObject(entity, Map.class);
		
		//任务处理人
		chooseHandleTypeValue(variables);
		
		FormService formService = ApplicationContextManager.getContext()
				.getBean(FormService.class);
		Form form = formService.getFormById(formId);
		String beanName = form.getRemark2();
		IFormOperate formOperate = (IFormOperate) ApplicationContextManager
				.getContext().getBean(beanName);

		// 业务数据吧上一次保存的一些流程变量去掉
		entitymap.remove(WorkFlowService.TYPE_ASSIGNEE);
		entitymap.remove(WorkFlowService.TYPE_CANDIDATEUSERS);
		entitymap.remove(WorkFlowService.TYPE_CANDIDATEGROUPS);
		entitymap.remove(WorkFlowService.TYPE_ASSIGNEELIST);
		entitymap.remove(HANDLE_TYPE_KEY);
		entitymap.remove(HANDLE_VALUE_KEY);
		/* 业务数据持久化 */
		if (form != null && form.isIsTableStorage() != null
				&& form.isIsTableStorage()) {
			formService
					.updateFormDataWithExternalTable(bussinessKey, proInsId,
							entity, wfTransition.getSrc(), form,
							wfOperator.getUserId(),wfTransition.getDest(),paramsMap);
			List<FormField> list = formService.getFormField(formId);
			for (FormField ff : list) {
				if (ffs.isFieldStorageProcessVariable(ff)) { // 字段是否存在流程变量中
					String fieldNo = ff.getFieldNo();
					Object value= entitymap.get(fieldNo);
					if(value!=null){
						//空字符串不存 也意味着不能把流程变量更新为空字符串！！
						if((value instanceof String&&!value.equals(""))){
							variables.put(fieldNo,value);
						}else{
							//对象存入流程变量了
							variables.put(fieldNo,value);
						}
					}
				}
			}
//			variables.putAll(entitymap); // 一些并没有在表单中的数据 或许在流程变量中更新
		} else {
			/* 业务数据作为流程变量保存  不再使用这种方法 */
			variables.putAll(entitymap);
		}

		// 添加意见
		if (opinion != null) {
			Authentication.setAuthenticatedUserId(wfOperator.getUserId());
			taskService.addComment(currenTaskId, proInsId, opinion);
		}
		variables.put("userId", wfOperator.getUserId());

		// sla用到了 此时variables应该要有全部的流程变量
		if (variables.get(WorkFlowService.ACCEPTTIMEL) == null) {
			variables.put(WorkFlowService.ACCEPTTIMEL,
					String.valueOf(System.currentTimeMillis()));
		}
		// 业务模块流程变量设置
		Map businessVariable = formOperate.setProsVariableBeforeTaskExcu(
				entity, bussinessKey, null, moduleId, variables,
				wfTransition.getSrc(), wfTransition.getDest(), wfTransition,
				wfOperator.getUserId(),paramsMap);
		if (businessVariable != null && businessVariable.size() > 0) {
			variables.putAll(businessVariable);
		}
		TaskEntity taskEntity = managementService
				.executeCommand(new FindTaskEntityCmd(currenTaskId));
		ActivityImpl activityImpl = taskEntity.getExecution().getActivity();
		/* JumpActivityByTakeTransitionCmd 自由流时会签任务处理， */
		if (activityImpl.getActivityBehavior() instanceof ParallelMultiInstanceBehavior) {
			// 先声明接撿然后再走自由泿解决流程图处理人出现null的问頿,
			// 声明后 assignee便有了值。
			taskService.claim(currenTaskId, wfOperator.getUserId());
			taskService.complete(currenTaskId);
			wfresult = new WfResult();
			wfresult.setResult(WfConstants.WF_CONTROL_EXE_SUCCESS);
			int nrOfCompletedInstances = (int) taskEntity.getVariables().get(
					"nrOfCompletedInstances");
			int nrOfInstances = (int) taskEntity.getVariables().get(
					"nrOfInstances");
			// 表示会签完成
			if (nrOfCompletedInstances == nrOfInstances - 1) {			
				//设置last activity 信息
				Map wfVariable = new HashMap();
				setLastHandlerInfo(wfOperator.getUserId(), wfOperator.getUserCName()
						, wfVariable, wfTransition.getSrc());
				runtimeService.setVariables(taskEntity.getProcessInstanceId(), wfVariable);
				
				String nextTaskId = wfRuntimeService
						.getCurrentActiveTaskIds(proInsId);
				Map<String, String> valuemap = formOperate.getTaskHandler(
						entity, bussinessKey, wfresult.getProcessInstanceId(),
						moduleId, wfTransition.getSrc(),wfTransition.getDest(), nextTaskId,
						wfOperator.getUserId(),paramsMap);
				setTaskHandler(valuemap, variables, nextTaskId);
			}
			/* 通知处理 */
			informService.setCcInformsInfo(ccInformJsonStr);
			informService.informDo();
			return new ResponseFactory().createResponseBodyJSONObject(JSON
					.toJSONString(wfresult));
		}

		// 先声明接撿然后再走自由泿解决流程图处理人出现null的问頿,
		// 声明后 assignee便有了值。
		taskService.claim(currenTaskId, wfOperator.getUserId());

		//设置last activity 信息
		setLastHandlerInfo(wfOperator.getUserId(), wfOperator.getUserCName()
				, variables, wfTransition.getSrc());
		
		// JumpActivityByTakeTransitionCmd 自由流
		wfresult = this.goAnyWhereTakeTransition(wfOperator,
				isStart.equals("true") ? true : false, bussinessKey,
				processDefinitionId, currenTaskId, transitionId,
				destTaskDefinitionKey, false, variables);
		
		Object multiInstancePor = wfTransition.getDest().getPorperties()
				.get("multiInstance");
		if (multiInstancePor != null
				&& ((String) multiInstancePor).equals("parallel")) {// 下一步是并行会签
			// 不要选择处理人

		} else if (multiInstancePor == null) {// 下一步是普通任务
			String nextTaskId = wfresult.getNextTaskId();
			Map<String, String> valuemap = formOperate.getTaskHandler(entity,
					bussinessKey, wfresult.getProcessInstanceId(), moduleId,
					wfTransition.getSrc(),wfTransition.getDest(), nextTaskId, wfOperator.getUserId(),
					paramsMap);
			setTaskHandler(valuemap, variables, nextTaskId);
		}

		/* 通知处理 */
		informService = ApplicationContextManager.getContext().getBean(
				InformService.class);
		informService.setCcInformsInfo(ccInformJsonStr);
		informService.informDo();

		return new ResponseFactory().createResponseBodyJSONObject(JSON
				.toJSONString(wfresult));
	}

	/**
	 * 
	 * @param paramsMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Object startWorkFlow(Map<String, Object> paramsMap) throws Exception {
		WfResult wf = null;
		String entity = (String) paramsMap.get("entity");
		// String isStart = paramsMap.get("isStart").toString();
		String formId = (String) paramsMap.get("formId");
		String wfOperatorStr = (String) paramsMap.get("wfOperator");
		String businessKey = (String) paramsMap.get("businessKey");
		// String currenTaskId = (String) paramsMap.get("currenTaskId");
		// String destTaskDefinitionKey = (String)
		// paramsMap.get("destTaskDefinitionKey");
		// String taskDefKey = paramsMap.get("taskDefKey");
		String processDefinitionId = (String) paramsMap
				.get("processDefinitionId");
		String variablesStr = (String) paramsMap.get("variables");
		// String opinion = (String) paramsMap.get("opinion");
		// String proInsId = (String) paramsMap.get("proInsId");
		// String transitionId = (String) paramsMap.get("transitionId");
		String moduleId = (String) paramsMap.get("moduleId");
		String transitionStr = (String) paramsMap.get("transition");
		WorkFlowTransition wfTransition = JSONObject.parseObject(transitionStr,
				WorkFlowTransition.class);
		WfOperator wfOperator = JSONObject.parseObject(wfOperatorStr,
				WfOperator.class);
		Map<String, Object> variables = JSONObject.parseObject(variablesStr,
				Map.class);
		String ccInformJsonStr = (String) paramsMap.get("ccInform");
		// happentimel 用来计算sla。
		variables.put(WorkFlowService.HAPPENEDTIMEL,
				String.valueOf(System.currentTimeMillis()));

		// 任务处理人
		chooseHandleTypeValue(variables);
		try {
			Map<String, Object> mapentity = JSONObject.parseObject(entity,
					Map.class);
			FormService formService = ApplicationContextManager.getContext()
					.getBean(FormService.class);
			Form form = formService.getFormById(formId);
//			if ((form != null && form.isIsTableStorage() == null)
//					|| !form.isIsTableStorage()) {
//				// 业务数据作为流程变量保存
//				variables.putAll(mapentity);
//			}

			ServiceProductService sps = ApplicationContextManager.getContext()
					.getBean(ServiceProductService.class);
			ServiceProduct sp = sps.getServiceProductById(moduleId);
			List<ServiceAgreement> listsla = sps.getSLABySP(sp);

			List<FormField> list = formService.getFormField(formId);
			for (FormField ff : list) {
				if (ffs.isFieldStorageProcessVariable(ff)) { // 字段是否存在流程变量中
					String fieldNo = ff.getFieldNo();
					Object value= mapentity.get(fieldNo);
					if(value!=null){
						//空字符串不存 也意味着不能把流程变量更新为空字符串！！
						if((value instanceof String&&!value.equals(""))){
							variables.put(fieldNo,value);
						}else{
							//对象存入流程变量了
							variables.put(fieldNo,value);
						}
					}
				}
			}			
			
//			/* 如果业务数据实体有值没在form中 存入流程变量 */
//			for (String s : mapentity.keySet()) {
//				FormField ff = ffs.getFormFieldByFieldNo(s);
//				if (ff != null && !ffs.isFieldStorageEXT(ff)) {
//					variables.put(s, mapentity.get(s));
//				}
//			}
			/* 为了能够按产品名称排序，等。传入流程变量 */
			variables.put(WorkFlowService.PRODUCTNAMEKEY, sp.getProductName());
			variables.put(WorkFlowService.MODULE_ID_KEY, moduleId);
			variables.put(WorkFlowService.PRODUCTNOKEY, sp.getProductNo());
			variables
					.put(WorkFlowService.SERVICETYPEKEY, sp.getServiceTypeId());
			// SLA
			if (listsla != null && listsla.size() > 0) {
				ServiceAgreementService sams = ApplicationContextManager
						.getContext().getBean(ServiceAgreementService.class);
				ServiceAgreement sla = sams.chooseAServiceAgreement(listsla);
				if (sla != null) {
					int acceptLimit = sams.getKpiValueBySla("FWXYSJ", sla);// 分钟
					long acceptLimitl = 60000 * acceptLimit;
					int handleLimit = sams.getKpiValueBySla("FWJJSJ", sla);
					long handleLimitl = 60000 * handleLimit;
					variables.put(WorkFlowService.ACCEPT_LIMIT_L, acceptLimitl);
					variables.put(WorkFlowService.HANDLE_LIMIT_L, handleLimitl);
				}
			}
			String beanName = form.getRemark2();
			IFormOperate formOperate = (IFormOperate) ApplicationContextManager
					.getContext().getBean(beanName);
			// 业务模块流程变量设置
			Map businessVariable = formOperate.setProsVariableBeforeTaskExcu(
					entity, businessKey, null, moduleId, variables,
					wfTransition.getSrc(), wfTransition.getDest(),
					wfTransition, wfOperator.getUserId(),paramsMap);
			if (businessVariable != null && businessVariable.size() > 0) {
				variables.putAll(businessVariable);
			}
			
			//设置last activity 信息
			setLastHandlerInfo(wfOperator.getUserId(), wfOperator.getUserCName()
					, variables, wfTransition.getSrc());
			
			wf = this.startFlow(wfOperator, businessKey, processDefinitionId,
					variables);
			if (form.isIsTableStorage() != null && form.isIsTableStorage()) { // 业务数据存储到外部表
				formService.updateFormDataWithExternalTable(businessKey,
						wf.getProcessInstanceId(), entity,
						wfTransition.getSrc(), form, wfOperator.getUserId(),
						wfTransition.getDest(),paramsMap);
			}
			if(AUTORUN_FIRST_ACT){
				taskService.claim(wf.getNextTaskId(), wfOperator.getUserId());
				informService.clearEvents();
				wf = this.goAnyWhereTakeTransition(wfOperator, false, businessKey, 
						processDefinitionId, wf.getNextTaskId(), 
						wfTransition.id, wfTransition.getDest().id, false, variables);
			}

//			taskService.complete(wf.getNextTaskId());
			String nextTaskId = wf.getNextTaskId();
			Map<String, String> valuemap = formOperate.getTaskHandler(entity,
					businessKey, wf.getProcessInstanceId(), moduleId,
					wfTransition.getSrc(),wfTransition.getDest(), nextTaskId, 
					wfOperator.getUserId(),paramsMap);

			setTaskHandler(valuemap, variables, nextTaskId);
			/* 通知处理 */
			informService.setCcInformsInfo(ccInformJsonStr);
			informService.informDo();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		;
		return new ResponseFactory().createResponseBodyJSONObject(JSON
				.toJSONString(wf));

	}

	/**
	 * 设置ren'wu处理人
	 * 
	 * @param valuemap
	 * @param taskId
	 */
	public void setTaskHandler(Map<String, String> valuemap, Map variables,
			String taskId) {
		if (taskId == null) {
			return;
		}
		if (valuemap == null || valuemap.size() == 0) {
			valuemap = new HashMap<String, String>();
			if (variables.get(HANDLE_TYPE_KEY) != null
					&& variables.get(HANDLE_VALUE_KEY) != null) {
				valuemap.put((String) variables.get(HANDLE_TYPE_KEY),
						(String) variables.get(HANDLE_VALUE_KEY));
			}
		}
		if (valuemap != null && valuemap.size() == 1) {
			// 先把之前的候选给去掉 有可能是平台赋的值
			managementService.executeCommand(new DelTaskCandidatesCmd(taskId));
			for (String key : valuemap.keySet()) {
				switch (key) {
				case WorkFlowService.TYPE_ASSIGNEE:
					taskService.setAssignee(taskId, valuemap.get(key));
					break;
				case WorkFlowService.TYPE_CANDIDATEUSERS:
					String value = valuemap.get(key);
					if (value != null && !value.equals("")) {
						String[] values = value.split(",");
						for (String s : values) {
							taskService.addCandidateUser(taskId, s);
						}
					}
					break;
				case WorkFlowService.TYPE_CANDIDATEGROUPS:
					taskService.addCandidateGroup(taskId, valuemap.get(key));
					break;
				}
			}
		}

	}

	/**
	 * 任务处理人
	 * 
	 * @param variables
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map chooseHandleTypeValue(Map variables) {
		RoleService roleService = ApplicationContextManager.getContext()
				.getBean(RoleService.class);
		UserJobService userJobService = ApplicationContextManager.getContext()
				.getBean(UserJobService.class);
		List assigneList = new ArrayList();
		if (variables.get(WorkFlowService.TYPE_ASSIGNEE) != null) { // 分派人
			String s = (String) variables.get(WorkFlowService.TYPE_ASSIGNEE);
			variables.put(HANDLE_TYPE_KEY, WorkFlowService.TYPE_ASSIGNEE);
			variables.put(HANDLE_VALUE_KEY, s);
			if (s.length() == 0) {
				throw new RuntimeException();
			}
			variables.put("candidateUsers", s.substring(0, s.length() - 1));
			// assigneList.add(s.substring(0, s.length()-1));
		} else if (variables.get(WorkFlowService.TYPE_CANDIDATEUSERS) != null) { // 候选人
			String s = (String) variables.get("candidateUsers");
			if (s.length() == 0) {
				throw new RuntimeException();
				/*
				 * WfResult wfs = new WfResult(); wfs.setResult("303"); return
				 * new
				 * ResponseFactory().createResponseBodyJSONObject(JSON.toJSONString
				 * (wfs));
				 */
			}
			variables.put(HANDLE_TYPE_KEY, WorkFlowService.TYPE_CANDIDATEUSERS);
			variables.put(HANDLE_VALUE_KEY, s);
			// candidateUsers = s.substring(0, s.length()-1);
			variables.put("candidateUsers", s.substring(0, s.length() - 1));
			assigneList.addAll(Arrays.asList((s.substring(0, s.length() - 1))
					.split(",")));
			variables.put(WorkFlowService.TYPE_ASSIGNEELIST, assigneList);

		} else if (variables.get(WorkFlowService.TYPE_CANDIDATEGROUPS) != null) { // 候选组

			variables
					.put(HANDLE_TYPE_KEY, WorkFlowService.TYPE_CANDIDATEGROUPS);
			variables.put(HANDLE_VALUE_KEY,
					(String) variables.get("candidateGroups"));

			String jobid = (String) variables.get("candidateGroups");
			List<UserDTO> us = userJobService.getAllUserJob(jobid);
			StringBuilder sb = new StringBuilder();
			if (us.size() == 0 && roleService != null) {
				us = roleService.queryUsers(jobid);
			}
			for (UserDTO u : us) {
				sb.append(u.getUserId()).append(",");
				// assigneList.add(u.getUserId());
			}

			if (sb.length() != 0) {
				assigneList.addAll(Arrays.asList((sb.substring(0,
						sb.length() - 1)).split(",")));
				variables.put(WorkFlowService.TYPE_ASSIGNEELIST, assigneList);
				variables.put("candidateUsers",
						sb.substring(0, sb.length() - 1));
				// throw new RuntimeException();
				/*
				 * WfResult wfs = new WfResult(); wfs.setResult("303"); return
				 * new
				 * ResponseFactory().createResponseBodyJSONObject(JSON.toJSONString
				 * (wfs));
				 */
			}

		} else if (variables.get(WorkFlowService.TYPE_ASSIGNEELIST) != null) {// assigneeList
																				// 多任务实例
			String value = (String) variables
					.get(WorkFlowService.TYPE_ASSIGNEELIST);
			// if(value.endsWith(","))
			String[] assignees = value.split(",");
			variables.put("assigneeList", Arrays.asList(assignees));
		}
		return variables;
	}
	/**
	 * 设置最后处理人信息
	 */
	public static final String LAST_HANDLER_USER_ID_KEY = "lhUserId";
	public static final String LAST_HANDLER_USER_NAME_KEY = "lhUserName";
	public static final String LAST_HANDLER_TIME_M_KEY = "lhTimel";
	public static final String LAST_HANDLER_ACT_ID_KEY = "lhActId";
	public static final String LAST_HANDLER_ACT_NAME_KEY = "lhActName";
	@SuppressWarnings("unchecked")
	public void setLastHandlerInfo(String userId,String userName,Map variables,WorkFlowActivity ac){
		if(userId==null||variables==null){
			return ;
		}
		if(userName == null){
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);
			UserDTO user = userService.queryByPK(userId);
			userName = user!=null?user.getUserRealname():null;
		}
		variables.put(LAST_HANDLER_USER_ID_KEY, userId);
		variables.put(LAST_HANDLER_USER_NAME_KEY, userName);
		variables.put(LAST_HANDLER_TIME_M_KEY, Calendar.getInstance().getTimeInMillis());
		if(ac!=null){
			variables.put(LAST_HANDLER_ACT_ID_KEY, ac.getId());
			variables.put(LAST_HANDLER_ACT_NAME_KEY, ac.getName());
		}
		return ;
	}
	/*
	 * public void removeNullValueInMap(Map<String,Object> map){ List set = new
	 * ArrayList(); for(String key:map.keySet()){
	 * if(map.get(key)==null||map.get(key).equals("")){ set.add(key); } } }
	 */
}