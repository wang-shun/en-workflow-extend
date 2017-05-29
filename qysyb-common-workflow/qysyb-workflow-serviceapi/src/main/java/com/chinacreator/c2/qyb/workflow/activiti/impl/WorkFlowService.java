package com.chinacreator.c2.qyb.workflow.activiti.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.SubProcessActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.flow.WfApiFactory;
import com.chinacreator.c2.flow.api.WfHistoryService;
import com.chinacreator.c2.flow.api.WfManagerService;
import com.chinacreator.c2.flow.api.WfRuntimeService;
import com.chinacreator.c2.flow.detail.WfOperator;
import com.chinacreator.c2.flow.detail.WfProcessDefinition;
import com.chinacreator.c2.flow.detail.WfResult;
import com.chinacreator.c2.flow.util.WfUtils;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.DictDataInfo;
import com.chinacreator.c2.omp.common.DictTypeInfo;
import com.chinacreator.c2.qyb.orgext.entity.Orgext;
import com.chinacreator.c2.qyb.orgext.impl.OrgextService;
import com.chinacreator.c2.qyb.workflow.activiti.query.impl.MTaskQueryImpl;
import com.chinacreator.c2.qyb.workflow.activiti.taskquery.impl.TaskRetrievalService;
import com.chinacreator.c2.qyb.workflow.activiti.taskquery.impl.TodoWorkService;
import com.chinacreator.c2.qyb.workflow.common.bean.WfProcessDefEntity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowComment;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;
import com.chinacreator.c2.qyb.workflow.config.impl.ActivityConfigService;
import com.chinacreator.c2.qyb.workflow.config.impl.UserConcernedConfigService;
import com.chinacreator.c2.qyb.workflow.customretrieval.entity.RetrieveItem;
import com.chinacreator.c2.qyb.workflow.customretrieval.impl.RetrieveItemService;
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

/**
 * 
 * 流程服务篿可操作activiti原生接口
 * 
 * @author qiao.li
 * @version 1.0
 * @date 2015-8-18
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class WorkFlowService {
	/* 通过MAP传值时约定的一些key */
	public final static String TOTAL = "total";
	public final static String PRIKEY = "priId";
	public final static String WORKTITLEKEY = "workTitle";
	public final static String SERVICETYPEKEY = "serviceTypeId";
	public final static String APPLYBRANCHKEY = "applyBranchId";
	public final static String STARTER = "starterId";
	public final static String STARTER_NAME = "starterName";
	public final static String STARTER_ORG = "starterOrg";
	public final static String STARTER_ORG_NAME = "starterOrgName";
	public final static String PRODUCTNOKEY = "productNo";
	public final static String PRODUCTNAMEKEY = "productName";
	public final static String USERIDKEY = "userId";
	public final static String RETRIEVE_KEY = "retrieveId";
	public final static String ACCEPT_LIMIT_L = "acceptLimitl";
	public final static String HANDLE_LIMIT_L = "handleLimitl";
	public final static String MODULE_ID_KEY = "moduleId";
	public final static String IS_EXTERNAL_STORAGE_KEY = "isExternalStorage";

	public final static String PRODUCTNO = "productNo";
	public final static String HAPPENEDTIMEL = "happenedtimel";
	public final static String SLA_REMAIN_TIME = "slaRemaintime";

	public final static String ACCEPTTIMEL = "accepttimel";
	public final static String SERVICETYPE = "服务分类";
	public static final String FORMIDSTR = "formId";
	public static final String NEEDFINISHED = "needFinished";
	public static final String NEEDUNFINISHED = "needUnFinished";
	public static final String NEEDINVOLVEDKEY = "needInvolved";

	public final static String TYPE_ASSIGNEELIST = "assigneeList";
	public final static String TYPE_ASSIGNEE = "assignee";
	public final static String TYPE_CANDIDATEUSERS = "candidateUsers";
	public final static String TYPE_CANDIDATEGROUPS = "candidateGroups";
	
	public final static String DELETE_USER_ID = "deleteUserId";
	public final static String DELETE_USER_NAME = "deleteUserName";
	public final static String DELETE_REASON = "deleteReason";
	/**
	 * 如下是activiti原生接口篿从spring中取bean即可
	 */
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	// private IdentityService identityService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;
	private org.activiti.engine.FormService activitiFormService;
	@Autowired
	private WfHistoryService wfHistoryService;

	private WfRuntimeService wfRuntimeService;
	@Autowired
	private UserService userService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private WfManagerService wfManagerService;
	@Autowired
	private ServiceProductService sps;
	private InformService informService;
	@Autowired
	private FormFieldService ffs;

	/**
	 * 获取定义中的activity
	 * 
	 * @param procDefId
	 * @return
	 */
	public List<WorkFlowActivity> getActivitiesFromProcessDef(String procDefId) {
		List<WorkFlowActivity> list = new ArrayList<WorkFlowActivity>();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(procDefId);
		List<ActivityImpl> activitiList = def.getActivities();
		for (ActivityImpl activity : activitiList) {
			WorkFlowActivity cusAct = new WorkFlowActivity();
			cusAct.setId(activity.getId());
			cusAct.setName((String) activity.getProperty("name"));
			// cusAct.setPorperties(activity.getProperties());
			list.add(cusAct);
		}
		return list;
	}

	/**
	 * 获取一个结束节点
	 * 
	 * @param sp
	 * @return
	 */
	public ActivityImpl getEndActivityByModuleId(String moduleId) {

		try {
			WfProcessDefinition wfProDef = wfManagerService
					.getBindProcessByModuleId(moduleId);
			ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(wfProDef.getId());
			List<ActivityImpl> activitiList = def.getActivities();
			for (ActivityImpl activityImpl : activitiList) {
				String activityType = (String) activityImpl.getProperty("type");
				if (activityType.equals("endEvent")) {
					return activityImpl;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获取一个开始节点
	 * 
	 * @param sp
	 * @return
	 */
	public ActivityImpl getStartActivityByModuleId(String moduleId) {

		try {
			WfProcessDefinition wfProDef = wfManagerService
					.getBindProcessByModuleId(moduleId);
			ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(wfProDef.getId());
			List<ActivityImpl> activitiList = def.getActivities();
			for (ActivityImpl activityImpl : activitiList) {
				String activityType = (String) activityImpl.getProperty("type");
				if (activityType.equals("startEvent")) {
					return activityImpl;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}	
	
	/**
	 * 获取节点的出去分撿
	 * 
	 * @param procInstanceId
	 * @param procDefId
	 * @param activitiId
	 * @return
	 */
	public List<WorkFlowTransition> getOutTransition(String procInstanceId,
			String procDefId, String activitiId) {
		List<WorkFlowTransition> outWfTransitions = new ArrayList<WorkFlowTransition>();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(procDefId);
		List<ActivityImpl> activitiList = def.getActivities();
		for (ActivityImpl activityImpl : activitiList) {
			// 是否子流程
			if (activityImpl.getActivityBehavior() instanceof SubProcessActivityBehavior) {
				List<WorkFlowTransition> subResult = getOutTransitionInsub(
						activityImpl, activitiId);
				if (subResult != null && subResult.size() > 0) {
					return subResult;
				}
			}
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				//是否自动流转第一个环节
				if(WorkProcess.AUTORUN_FIRST_ACT){
					//判断是不是start节点
					if(activityImpl.getProperty("type")!=null&&activityImpl.getProperty("type").equals("startEvent")){
						List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
						if(outTransitions!=null&&outTransitions.size()==1){
							activityImpl = (ActivityImpl)outTransitions.get(0).getDestination();
						}
					}
				}
				// System.out.println("当前任务ﺿ+activityImpl.getProperty("name"));
				// //输出某个节点的某种属徿
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的懿线路
				// Execution eq =
				// runtimeService.createExecutionQuery().activityId("a").singleResult();
				// historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(arg0)

				for (PvmTransition tr : outTransitions) {
					WorkFlowTransition wkt = new WorkFlowTransition();
					wkt.setId(tr.getId());
					wkt.setName((String) tr.getProperty("name"));
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节瀿
					WorkFlowActivity wka = new WorkFlowActivity();
					wka.setId(ac.getId());
					wka.setName((String) ac.getProperty("name"));
					Map map = new HashMap();
					map.put("multiInstance", ac.getProperty("multiInstance"));
					map.put("type", ac.getProperty("type"));
					wka.setPorperties(map);// 并行分支带 multiInstance 参数
					wkt.setDest(wka);
					WorkFlowActivity wkasrc = new WorkFlowActivity();
					wkasrc.setId(activityImpl.getId());
					wkasrc.setName((String) activityImpl.getProperty("name"));
					Map map1 = new HashMap();
					map1.put("multiInstance",
							activityImpl.getProperty("multiInstance"));
					map1.put("type", activityImpl.getProperty("type"));
					wkasrc.setPorperties(map1);
					wkt.setSrc(wkasrc);
					outWfTransitions.add(wkt);
					System.out.println("下一步任务任务：" + ac.getProperty("name"));
				}
				return outWfTransitions;

			}
		}
		return null;
	}

	/**
	 * 获取流程图中子流程图的出线
	 * 
	 * @param subAct
	 * @param activitiId
	 * @return
	 */
	public List<WorkFlowTransition> getOutTransitionInsub(ActivityImpl subAct,
			String activitiId) {
		if (!(subAct.getActivityBehavior() instanceof SubProcessActivityBehavior)) {
			return null;
		}
		List<WorkFlowTransition> outWfTransitions = new ArrayList<WorkFlowTransition>();
		List<ActivityImpl> activitiList = subAct.getActivities();
		for (ActivityImpl activityImpl : activitiList) {
			// 是否子流程
			if (activityImpl.getActivityBehavior() instanceof SubProcessActivityBehavior) {
				return getOutTransitionInsub(activityImpl, activitiId);
			}
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				// System.out.println("当前任务ﺿ+activityImpl.getProperty("name"));
				// //输出某个节点的某种属徿
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的懿线路
				// Execution eq =
				// runtimeService.createExecutionQuery().activityId("a").singleResult();
				// historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(arg0)

				for (PvmTransition tr : outTransitions) {
					WorkFlowTransition wkt = new WorkFlowTransition();
					wkt.setId(tr.getId());
					wkt.setName((String) tr.getProperty("name"));
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节瀿
					WorkFlowActivity wka = new WorkFlowActivity();
					wka.setId(ac.getId());
					wka.setName((String) ac.getProperty("name"));
					Map map = new HashMap();
					map.put("multiInstance", ac.getProperty("multiInstance"));
					map.put("type", ac.getProperty("type"));
					wka.setPorperties(map);// 并行分支带 multiInstance 参数
					wkt.setDest(wka);
					WorkFlowActivity wkasrc = new WorkFlowActivity();
					wkasrc.setId(activityImpl.getId());
					wkasrc.setName((String) activityImpl.getProperty("name"));
					Map map1 = new HashMap();
					map1.put("multiInstance",
							activityImpl.getProperty("multiInstance"));
					map1.put("type", activityImpl.getProperty("type"));
					wkasrc.setPorperties(map1);
					wkt.setSrc(wkasrc);
					outWfTransitions.add(wkt);
					System.out.println("下一步任务任务：" + ac.getProperty("name"));
				}
				return outWfTransitions;

			}
		}
		return null;
	}

	/**
	 * 获取taskDefKey 下 id为transitionId 的出去分支
	 * 
	 * @param taskDefKey
	 * @param transitionId
	 * @return
	 */

	public PvmTransition getPvmTransitionById(String procDefId,
			String taskDefKey, String transitionId) {
		repositoryService = ApplicationContextManager.getContext().getBean(
				RepositoryService.class);
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(procDefId);
		List<ActivityImpl> activitiList = def.getActivities();
		for (ActivityImpl activityImpl : activitiList) {
			// 是否子流程
			if (activityImpl.getActivityBehavior() instanceof SubProcessActivityBehavior) {
				PvmTransition pvmTransition = getPvmTransitionByIdInsub(
						activityImpl, taskDefKey, transitionId);
				if (pvmTransition != null) {
					return pvmTransition;
				}
			}
			String id = activityImpl.getId();
			if (taskDefKey.equals(id)) {
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的懿线路
				for (PvmTransition tr : outTransitions) {
					if (tr.getId().equals(transitionId))
						;
					return tr;
				}
			}
		}
		return null;

	}

	public PvmTransition getPvmTransitionByIdInsub(ActivityImpl activity,
			String taskDefKey, String transitionId) {
		// repositoryService =
		// ApplicationContextManager.getContext().getBean(RepositoryService.class);
		// ProcessDefinitionEntity def = (ProcessDefinitionEntity)
		// ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(procDefId);
		List<ActivityImpl> activitiList = activity.getActivities();
		for (ActivityImpl activityImpl : activitiList) {
			// 是否子流程
			if (activityImpl.getActivityBehavior() instanceof SubProcessActivityBehavior) {
				PvmTransition pvmTransition = getPvmTransitionByIdInsub(
						activityImpl, taskDefKey, transitionId);
				if (pvmTransition != null) {
					return pvmTransition;
				}
			}
			String id = activityImpl.getId();
			if (taskDefKey.equals(id)) {
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的懿线路
				for (PvmTransition tr : outTransitions) {
					if (tr.getId().equals(transitionId))
						;
					return tr;
				}
			}
		}
		return null;
	}

	/**
	 * 返回环节的䀩执行潍
	 * 
	 * @param processDefinitionId
	 * @param moduleId
	 * @param taskDefKey
	 * @return
	 */
	public List<Map> getActivityCandidates(
			String processDefinitionId, String moduleId, String taskDefKey) {
		Map map = new HashMap();
		List<Map> ddlolist = new ArrayList<Map>();
		List<Map> list;
		map.put("processDefinitionId", processDefinitionId);
		map.put("moduleId", moduleId);
		map.put("taskDefKey", taskDefKey);
		Dao dao = DaoFactory.create(Object.class);
		SqlSession sqlsession = dao.getSession();
		list = sqlsession
				.selectList(
						"com.chinacreator.c2.qyb.workflow.usergroup.WorkFlowMapper.getActivityCandidates",
						map);
		for (Map map1 : list) {
			String performer = (String) map1.get("PERFORMER");
			String group_performer = (String) map1.get("GROUP_PERFORMER");

			if (performer != null && !performer.isEmpty()) {
				String[] performers = performer.split(",");
				for (String s : performers) {
					Map ddlo = new HashMap();
					ddlo.put("id", s);
					map1.put("performer", s);
					List<Map> list1 = sqlsession
							.selectList(
									"com.chinacreator.c2.qyb.workflow.usergroup.WorkFlowMapper.getUserInfoById",
									map1);
//					ddlo.setName((String) list1.get(0).get("LAST_"));
					ddlo.put("name", (String) list1.get(0).get("LAST_"));
					ddlo.put("category", "user");
					ddlolist.add(ddlo);
				}

			}
			if (group_performer != null && !group_performer.isEmpty()) {

				String[] groups = group_performer.split(",");
				for (String s : groups) {
					Map ddlo = new HashMap();
//					ddlo.setId(s);
					ddlo.put("id", WfUtils.parseToGroupId(s));
					map1.put("group_performer", WfUtils.parseToGroupId(s));
					List<Map> list1 = sqlsession
							.selectList(
									"com.chinacreator.c2.qyb.workflow.usergroup.WorkFlowMapper.getGroupInfoById",
									map1);
//					ddlo.setName((String) list1.get(0).get("NAME_"));
					ddlo.put("name", (String) list1.get(0).get("NAME_"));
					ddlo.put("category", "group");
					ddlolist.add(ddlo);
				}
			}
		}
		return ddlolist;
	}

	/**
	 * 获取流程懿评论
	 * 
	 * @param proinsid
	 * @return
	 * @throws Exception
	 */
	public List<WorkFlowComment> getAllHistoricProcessInstanceComments(
			String proinsid) throws Exception {
		if (wfHistoryService == null) {
			wfHistoryService = ApplicationContextManager.getContext().getBean(
					WfHistoryService.class);
		}
		if (userService == null) {
			userService = ApplicationContextManager.getContext().getBean(
					UserService.class);
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		historyService = ApplicationContextManager.getContext().getBean(
				HistoryService.class);
		List<WorkFlowComment> list = new ArrayList<WorkFlowComment>();
		// List<WfComment> wfcs =
		// wfHistoryService.getAllHistoricProcessInstanceComments(proinsid);
		List<Comment> coms = taskService.getProcessInstanceComments(proinsid);
		for (Comment wfc : coms) {
			WorkFlowComment com = new WorkFlowComment();
			com.setDate(wfc.getTime());
			com.setUserId(wfc.getUserId());
			com.setMessage(wfc.getFullMessage());
			com.setTaskId(wfc.getTaskId());
			UserDTO ud = userService.queryByPK(wfc.getId());
			if (ud != null) {
				com.setUserName(ud.getUserName());
			}
			HistoricTaskInstance histask = historyService
					.createHistoricTaskInstanceQuery().taskId(wfc.getTaskId())
					.singleResult();
			com.setTaskDef(histask.getTaskDefinitionKey());
			com.setTaskName(histask.getName());
			list.add(com);
		}

		Collections.sort(list, new Comparator<WorkFlowComment>() {
			@Override
			public int compare(WorkFlowComment arg0, WorkFlowComment arg1) {
				// TODO Auto-generated method stub
				return arg0.getDate().compareTo(arg1.getDate());
			}

		});
		return list;
	}

	/**
	 * RT
	 * 
	 * @param taskId
	 * @return
	 */
	public String getTaskDefById(String taskId) {
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return task.getTaskDefinitionKey();
	}

	/**
	 * 获取服务产品的待办数
	 * 
	 * @param userId
	 * @param type
	 *            :eventworkflow etc
	 * @return
	 */
	public int getTodoWorkTotalBySP(ServiceProduct sp, String userId) {
		String productNo = null;
		String proccessDefKey = null;
		if (sp != null) {
			proccessDefKey = sp.getWfProcessdefid();
			productNo = sp.getProductNo();
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		long l;
		if (userId != null && productNo != null) {
			l = taskService
					.createTaskQuery()
					.processVariableValueEquals(WorkFlowService.PRODUCTNO,
							productNo).taskCandidateOrAssigned(userId)
					.processDefinitionKeyLike(proccessDefKey).count();
		} else if (userId != null && productNo == null) {
			l = taskService.createTaskQuery().taskCandidateOrAssigned(userId)
					.processDefinitionKeyLike(proccessDefKey).count();
		} else if (userId == null && productNo != null) {
			l = taskService
					.createTaskQuery()
					.processVariableValueEquals(WorkFlowService.PRODUCTNO,
							productNo).processDefinitionKeyLike(proccessDefKey)
					.count();
		} else {
			l = taskService.createTaskQuery()
					.processDefinitionKeyLike(proccessDefKey).count();
		}
		return new Long(l).intValue();
	}

	/**
	 * 批量获取服务类型的待办事项数量
	 * 
	 * @param types
	 * @param userId
	 * @return
	 */
	public Map<String, Integer> getTodoWorkTotalbyTypes(String[] serviceType,
			Map con, String userId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (userId == null) {
			AccessControlService acc = new AccessControlServiceImpl();
			userId = acc.getUserID();
		}
 		if(serviceType!=null&&serviceType.length==1&&serviceType[0].equals("all")){
 			con.remove(SERVICETYPEKEY);
 			map.put("all",
 					todoWorkService.getTodoWorkTotalByCon(null, con, userId));
 			return map;
 		}
		if (serviceType == null) {
			Dao<DictDataInfo> daod = DaoFactory.create(DictDataInfo.class);
			Dao<DictTypeInfo> daot = DaoFactory.create(DictTypeInfo.class);
			DictTypeInfo cont = new DictTypeInfo();
			DictDataInfo cond = new DictDataInfo();
			cont.setDicttypeName("服务分类");
			cont = daot.selectOne(cont);
			cond.setDicttypeId(cont);
			List<DictDataInfo> list = daod.select(cond);
			for (DictDataInfo d : list) {
				if (con == null) {
					con = new HashMap();
					con.put(WorkFlowService.SERVICETYPEKEY, d.getDictdataName());
				} else {
					con.put(WorkFlowService.SERVICETYPEKEY, d.getDictdataName());
				}
				map.put(d.getDictdataName(),
						todoWorkService.getTodoWorkTotalByCon(null, con, userId));
			}
			return map;
		} else {
			for (String s : serviceType) {
				if (con == null) {
					con = new HashMap();
					con.put(WorkFlowService.SERVICETYPEKEY, s);
				} else {
					con.put(WorkFlowService.SERVICETYPEKEY, s);
				}
				map.put(s, todoWorkService.getTodoWorkTotalByCon(null, con, userId));
			}
			return map;
		}
	}

 	/**
	
 	 * 批量获取服务类型的待办事项数量
 	 * 
 	 * @param types
 	 * @param userId
 	 * @return
 	 */
 	public Map<String, Integer> getTodoWorkTotalbyTypes(String[] serviceType, String formId) {
 		Map<String, Integer> map = new HashMap<String, Integer>();
 		try{
 	 		Map con = new HashMap();
 	 		AccessControlService acc = ApplicationContextManager.getContext().getBean(AccessControlService.class);
 	 		String userId = acc.getUserID();
 	 		
 			con.put("isExternalStorage", true);
 			con.put("formId", formId);
 			map.put("all",
 					todoWorkService.getTodoWorkTotalByCon(null, con, userId)); 			
 		}catch(Exception e){
 			//用户未登录，不要终止之后的，重定向登录页面流程
 			map.put("all",-1);
 		}	
		return map;
 	}	
	
	/**
	 * 获取服务产品的待办
	 * 
	 * @param type
	 *            ：eventworkflow etc
	 * @param userId
	 * @return
	 */
	public List<Map> getTodoWorkBySP(ServiceProduct sp, String userId,
			int offset, int limit) {
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		if (runtimeService == null) {
			runtimeService = ApplicationContextManager.getContext().getBean(
					RuntimeService.class);
		}
		if (repositoryService == null) {
			repositoryService = ApplicationContextManager.getContext().getBean(
					RepositoryService.class);
		}
		if (sps == null) {
			sps = ApplicationContextManager.getContext().getBean(
					ServiceProductService.class);
		}
		String productNo = null;
		String proccessDefKey = null;
		if (sp != null) {
			proccessDefKey = sp.getWfProcessdefid();
			productNo = sp.getProductNo();
		}
		ServiceAgreementService slas = ApplicationContextManager.getContext()
				.getBean(ServiceAgreementService.class);
		ProcessInstanceQuery piq = runtimeService.createProcessInstanceQuery();
		// ProcessDefinitionQuery pdq =
		// repositoryService.createProcessDefinitionQuery();

		List<Map> content1 = new ArrayList<Map>();

		List<Task> list;
		if (userId != null && productNo != null) {
			list = taskService
					.createTaskQuery()
					.processVariableValueEquals(WorkFlowService.PRODUCTNO,
							productNo).taskCandidateOrAssigned(userId)
					.processDefinitionKeyLike(proccessDefKey).orderByDueDate()
					.asc().listPage(offset, limit);
		} else if (userId != null && productNo == null) {
			list = taskService.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.processDefinitionKeyLike(proccessDefKey).orderByDueDate()
					.asc().listPage(offset, limit);
		} else if (userId == null && productNo != null) {
			list = taskService
					.createTaskQuery()
					.processVariableValueEquals(WorkFlowService.PRODUCTNO,
							productNo).processDefinitionKeyLike(proccessDefKey)
					.orderByDueDate().asc().listPage(offset, limit);
		} else {
			list = taskService.createTaskQuery()
					.processDefinitionKeyLike(proccessDefKey).orderByDueDate()
					.asc().listPage(offset, limit);
		}
		ServiceAgreementService sams = ApplicationContextManager.getContext()
				.getBean(ServiceAgreementService.class);
		for (Task t : list) {
			ProcessInstance pi = piq
					.processInstanceId(t.getProcessInstanceId()).singleResult();
			String businessKey = pi.getBusinessKey();
			Map<String, Object> map = new HashMap<String, Object>();

			FormService formService = ApplicationContextManager.getContext()
					.getBean(FormService.class);

			map = formService.getFormDataByFkFromProcessVariable(
					sp.getFormId(), false, businessKey, pi.getId());
			// map = formService.getFormDataByFk(sp.getFormId(), businessKey);

			map.put("serviceTypeId", sp.getServiceTypeId());
			map.put("businessKey", businessKey);
			map.put("proinsid", t.getProcessInstanceId());
			map.put("prodefid", pi.getProcessDefinitionId());
			map.put("workStage", t.getName());
			map.put("taskId", t.getId());
			map.put("workId", businessKey);
			// 1117test
			// work = daocw.selectOne(work);
			// 出现没有businessKey对应的业务数据的情况，则把流程关了吧
			// if(work == null){
			// WfOperator wfo = new WfOperator();
			// wfo.setUserId(userId);
			// WfBusinessData wfbd = new WfBusinessData();
			// wfbd.setBusinessKey(businessKey);
			// wfo.setBusinessData(new WfBusinessData());
			// try {
			// if(wfRuntimeService==null){
			// wfRuntimeService =
			// ApplicationContextManager.getContext().getBean(WfRuntimeService.class);
			// }
			// wfRuntimeService.deleteProcessInstancesById(wfo,
			// "no data in business form",t.getProcessInstanceId());
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// continue;
			// }
			// continue;
			// }
			// if(sp==null){
			// String productNo1 = (String)
			// mapvariable.get(WorkFlowService.PRODUCTNO);
			// if(productNo1!=null){
			// sp = sps.getServiceProductByNo(productNo1);
			// }else{
			// sp = null;
			// }
			// }
			if (sp != null) {
				List<ServiceAgreement> listsla = sps.getSLABySP(sp);
				ServiceAgreement sla = sams.chooseAServiceAgreement(listsla);
				if (sla != null) {
					int acceptLimit = slas.getKpiValueBySla("FWXYSJ", sla);// 分钟
					long acceptLimitl = 60000 * acceptLimit;
					int handleLimit = slas.getKpiValueBySla("FWJJSJ", sla);
					long handleLimitl = 60000 * handleLimit;
					// int interruptLimit = slas.getKpiValueBySla("FWZDSJ",
					// sla);
					// long interruptLimitl = 60000*interruptLimit;
					long happenedTimel;
					long acceptTimel;
					if (map.get(WorkFlowService.HAPPENEDTIMEL) != null) {
						Object o = map.get(WorkFlowService.HAPPENEDTIMEL);
						if (o instanceof String) {
							happenedTimel = Long.valueOf((String) o);
						} else if (o instanceof Long) {
							happenedTimel = (long) o;
						} else {
							happenedTimel = -1;
						}
					} else {
						happenedTimel = -1;
					}
					if (map.get(WorkFlowService.ACCEPTTIMEL) != null) {
						Object o = map.get(WorkFlowService.ACCEPTTIMEL);
						if (o instanceof String) {
							acceptTimel = Long.valueOf((String) o);
						} else if (o instanceof Long) {
							acceptTimel = (long) o;
						} else {
							acceptTimel = -1;
						}
					} else {
						acceptTimel = -1;
					}
					if (acceptTimel == -1
							&& happenedTimel != -1
							&& System.currentTimeMillis() < happenedTimel
									+ acceptLimitl) {
						map.put("isRecieveTimeout", false);
					} else if (acceptTimel == -1
							&& happenedTimel != -1
							&& System.currentTimeMillis() > happenedTimel
									+ acceptLimitl) {
						map.put("isRecieveTimeout", true);
					} else if (acceptTimel != -1
							&& new Timestamp(acceptTimel).after(new Date(
									happenedTimel + acceptLimitl))) {
						map.put("isRecieveTimeout", true);
					} else if (acceptTimel != -1
							&& new Timestamp(acceptTimel).before(new Date(
									happenedTimel + acceptLimitl))) {
						map.put("isRecieveTimeout", false);
					} else {
						map.put("isRecieveTimeout", null);
					}
					if (acceptTimel != -1
							&& System.currentTimeMillis() < acceptTimel
									+ handleLimitl) {
						map.put("isHandleTimeout", false);
					} else if (acceptTimel != -1
							&& System.currentTimeMillis() > acceptTimel
									+ handleLimitl) {
						map.put("isHandleTimeout", true);
					} else if (acceptTimel == -1
							&& happenedTimel != -1
							&& System.currentTimeMillis() > acceptLimitl
									+ handleLimitl + happenedTimel) {
						map.put("isHandleTimeout", true);
					} else if (acceptTimel == -1
							&& happenedTimel != -1
							&& System.currentTimeMillis() < acceptLimitl
									+ handleLimitl + happenedTimel) {
						map.put("isHandleTimeout", false);
					} else {
						map.put("isHandleTimeout", null);
					}
					if (happenedTimel != -1) {
						long leftl = acceptLimitl + handleLimitl
								+ happenedTimel - System.currentTimeMillis();
						int leftm = (int) (leftl / 60000);
						map.put(SLA_REMAIN_TIME, leftm);
					} else {
						map.put("slaRemaintime", null);
					}
				} else {
					map.put("isRecieveTimeout", null);
					map.put("isHandleTimeout", null);
					map.put(SLA_REMAIN_TIME, null);
				}
			}
			content1.add(map);
		}
		return content1;
	}

	@Autowired
	private TodoWorkService todoWorkService;

	/**
	 * 根据流程实例id删除流程实例
	 * @param json 业务数据
	 * @param wfOperator
	 *            操作者信息，必须参数
	 * @param deleteReason
	 *            删除原因
	 * @param processInstanceId
	 *            流程实例id,必须参数
	 * @param formId
	 *            formId
	 * @return 200-操作成功⾿00-参数不正确ὴ00-操作失败⾿04-操作对象不存嚿
	 * @throws Exception
	 */
	@Transactional
	public String deleteProcessInstancesById(String json,WfOperator wfOperator,
 			String deleteReason, String processInstanceId, String formId, boolean deleteHistory)
			throws Exception {
		String result = WfApiFactory.getWfRuntimeService()
				.deleteProcessInstancesById(wfOperator, deleteReason,
						processInstanceId);
/*		Map wfVariable = new HashMap();
		wfVariable.put(DELETE_USER_ID, wfOperator.getUserId());
		wfVariable.put(DELETE_USER_NAME, wfOperator.getUserName());
		wfVariable.put(DELETE_REASON, deleteReason);		
		runtimeService.setVariables(processInstanceId, wfVariable);*/
		
		FormService formService = ApplicationContextManager.getContext()
				.getBean(FormService.class);
		Form form = formService.getFormById(formId);
		String beanName = form.getOperateBean();
		FormOperate formOperate = (FormOperate) ApplicationContextManager
				.getContext().getBean(beanName);
		formOperate.onProcessDelete(json, wfOperator.getBusinessData().getBusinessKey(), processInstanceId, 
				wfOperator.getBusinessData().getModuleId(), null, 
				null, deleteReason, new HashMap());
 		if(deleteHistory){
 			historyService.deleteHistoricProcessInstance(processInstanceId);
 		}
 		return result;
 	}
 
 	/**
 	 * 根据流程实例id删除流程实例
 	 * @param processInstanceId
 	 *            流程实例id,必须参数
 	 * @return 200-操作成功⾿00-参数不正确ὴ00-操作失败⾿04-操作对象不存嚿
 	 * @throws Exception
 	 */
 	@Transactional
 	public String deleteHisProcessInstancesById(String processInstanceId)
 			throws Exception {
 		String result = WfApiFactory.getWfHistoryService().deleteHistoricProcessInstance(processInstanceId);
 		return result;
 	}	
 	
	/**
	 * 获取所有流程定义
	 * 
	 * @return
	 */
	public List<WfProcessDefEntity> getAllProcessDef() {
		if (repositoryService == null) {
			repositoryService = ApplicationContextManager.getContext().getBean(
					RepositoryService.class);
		}
		List<WfProcessDefEntity> listwf = new ArrayList<WfProcessDefEntity>();
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery().active().latestVersion().list();
		for (ProcessDefinition p : list) {
			WfProcessDefEntity wfp = new WfProcessDefEntity();
			wfp.setKey(p.getKey());
			wfp.setName(p.getName());
			wfp.setVersion(p.getVersion());
			listwf.add(wfp);
		}
		return listwf;
	}

	/**
	 * RT
	 * 
	 * @param businesskey
	 * @param taskid
	 * @param clazzstr
	 *            class or form name
	 */
	public Object queryWorkEntityByBK(String businesskey, String clazzstr) {
		if (businesskey == null) {
			return new Object();
		}
		try {

			FormService formService = ApplicationContextManager.getContext()
					.getBean(FormService.class);
			return formService.getFormDataByFk(clazzstr, businesskey);

			/*
			 * clazz = Class.forName(clazzstr); Dao dao =
			 * DaoFactory.create(clazz); Object o = clazz.newInstance(); Method
			 * m = clazz.getMethod("setBusinessKey", String.class); m.invoke(o,
			 * businesskey); return dao.selectOne(o);
			 */

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RT 按服务分类获取总的工单数量
	 * 
	 * @param servicetype
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public Map<String, Integer> getWorkTotalBySTS(String[] serviceType,
			Map con, Timestamp starttime, Timestamp endtime) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = 0;
		if (starttime == null || endtime == null) {
			endtime = new Timestamp(System.currentTimeMillis());
			starttime = new Timestamp(System.currentTimeMillis() - 2592000000l); // 最近一个月
		}
		if (serviceType == null) {
			Dao<DictDataInfo> daod = DaoFactory.create(DictDataInfo.class);
			Dao<DictTypeInfo> daot = DaoFactory.create(DictTypeInfo.class);
			DictTypeInfo cont = new DictTypeInfo();
			DictDataInfo cond = new DictDataInfo();
			cont.setDicttypeName("服务分类");
			cont = daot.selectOne(cont);
			cond.setDicttypeId(cont);
			List<DictDataInfo> list = daod.select(cond);
			for (DictDataInfo d : list) {
				int num = this.getWorkTotalByST(d.getDictdataName(), con,
						starttime, endtime);
				total = total + num;
				map.put(d.getDictdataName(), num);
			}
			map.put("total", total);
			return map;
		} else {
			for (String s : serviceType) {
				int num = this.getWorkTotalByST(s, con, starttime, endtime);
				total = total + num;
				map.put(s, num);
			}
			map.put("total", total);
			return map;
		}
	}

	/**
	 * RT此处和待办事项 应该还是有区别的 待办的只是任务，此处看流程实例的数量
	 * 
	 * @param sp
	 * @return
	 */
	public int getWorkTotalBySP(ServiceProduct sp, Map con,
			Timestamp starttime, Timestamp endtime) {
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		return (int) historyService
				.createHistoricProcessInstanceQuery()
				.variableValueEquals(WorkFlowService.PRIKEY, priority)
				.variableValueEquals(WorkFlowService.PRODUCTNO,
						sp.getProductNo()).startedAfter(starttime)
				.startedBefore(endtime).count();
		// return (int)
		// runtimeService.createProcessInstanceQuery().active().variableValueEquals("productNo",
		// sp.getProductNo()).count();
	}

	/**
	 * 根据服务分类获取工单数量
	 * 
	 * @param serviceType
	 * @param userId
	 * @return
	 */
	public int getWorkTotalByST(String serviceType, Map con,
			Timestamp starttime, Timestamp endtime) {
		// int total = 0;
		// List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		if (runtimeService == null) {
			runtimeService = ApplicationContextManager.getContext().getBean(
					RuntimeService.class);
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		String workTitle = null;
		if (con != null && con.get(WorkFlowService.WORKTITLEKEY) != null) {
			workTitle = (String) con.get(WorkFlowService.WORKTITLEKEY);
		}
		String branchId = null;
		if (con != null && con.get(WorkFlowService.APPLYBRANCHKEY) != null) {
			branchId = (String) con.get(WorkFlowService.APPLYBRANCHKEY);
		}
		Boolean needFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDFINISHED) != null) {
			needFinished = (Boolean) con.get(WorkFlowService.NEEDFINISHED);
		}
		Boolean needUnFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDUNFINISHED) != null) {
			needUnFinished = (Boolean) con.get(WorkFlowService.NEEDUNFINISHED);
		}
		HistoricProcessInstanceQuery hiq = historyService
				.createHistoricProcessInstanceQuery();
		if (starttime != null && endtime != null) {
			hiq = hiq.startedAfter(starttime).startedBefore(endtime);
		}
		if (serviceType != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.SERVICETYPEKEY,
					serviceType);
		}
		if (priority != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.PRIKEY, priority);
		}
		if (branchId != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.APPLYBRANCHKEY,
					branchId);
		}
		if (needFinished != null && needFinished == true) {
			hiq = hiq.finished();
		}
		if (needUnFinished != null && needUnFinished == true) {
			hiq = hiq.unfinished();
		}
		// if(){
		//
		// }
		return (int) hiq.count();
		// return (int)
		// historyService.createHistoricProcessInstanceQuery().variableValueEquals(WorkFlowService.PRIKEY,
		// priority)
		// .variableValueEquals(WorkFlowService.SERVICETYPEKEY, serviceType)
		// // .variableValueLike(WorkFlowService.WORKTITLEKEY, workTitle)
		// .variableValueEquals(WorkFlowService.APPLYBRANCHKEY,
		// branchId).startedAfter(starttime).startedBefore(endtime).count();
		// if(sps==null){
		// sps =
		// ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		// }
		// List<ServiceProduct> list = sps.getSPByserviceType(serviceType);
		// for(ServiceProduct sp:list){
		// total = total + this.getWorkTotalBySP(sp, con,starttime,endtime);
		// }
		// return total;
	}

	public int getAllHisWorkCount(String serviceType, Map con,
			Timestamp starttime, Timestamp endtime) {
		if (starttime == null || endtime == null) {
			endtime = new Timestamp(System.currentTimeMillis());
			starttime = new Timestamp(System.currentTimeMillis() - 2592000000l); // 最近一个月
		}
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		if (runtimeService == null) {
			runtimeService = ApplicationContextManager.getContext().getBean(
					RuntimeService.class);
		}
		if (serviceType == null && con != null
				&& con.get(WorkFlowService.SERVICETYPEKEY) != null) {
			serviceType = (String) con.get(WorkFlowService.SERVICETYPEKEY);
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		String workTitle = null;
		if (con != null && con.get(WorkFlowService.WORKTITLEKEY) != null) {
			workTitle = (String) con.get(WorkFlowService.WORKTITLEKEY);
		}
		String branchId = null;
		if (con != null && con.get(WorkFlowService.APPLYBRANCHKEY) != null) {
			branchId = (String) con.get(WorkFlowService.APPLYBRANCHKEY);
		}
		Boolean needFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDFINISHED) != null) {
			needFinished = (Boolean) con.get(WorkFlowService.NEEDFINISHED);
		}
		Boolean needUnFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDUNFINISHED) != null) {
			needUnFinished = (Boolean) con.get(WorkFlowService.NEEDUNFINISHED);
		}
		boolean needInvolved = false;
		if (con != null && con.get(WorkFlowService.NEEDINVOLVEDKEY) != null) {
			needInvolved = (boolean) con.get(WorkFlowService.NEEDINVOLVEDKEY);
		}
		String userId = null;
		if (con != null && con.get(WorkFlowService.USERIDKEY) != null) {
			userId = (String) con.get(WorkFlowService.USERIDKEY);
		}
		HistoricProcessInstanceQuery hiq = historyService
				.createHistoricProcessInstanceQuery().startedAfter(starttime)
				.startedBefore(endtime);
		if (serviceType != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.SERVICETYPEKEY,
					serviceType);
		}
		if (workTitle != null) {
			hiq = hiq.variableValueLike(WorkFlowService.WORKTITLEKEY, "%"
					+ workTitle + "%");
		}
		if (priority != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.PRIKEY, priority);
		}
		if (branchId != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.APPLYBRANCHKEY,
					branchId);
		}
		if (needFinished != null && needFinished == true) {
			hiq = hiq.finished();
		}
		if (needUnFinished != null && needUnFinished == true) {
			hiq = hiq.unfinished();
		}
		if (needInvolved == true && userId != null) {
			hiq = hiq.involvedUser(userId);
		}
		return (int) hiq.count();
	}

	/**
	 * 
	 * @param serviceType
	 * @param con
	 * @param starttime
	 * @param endtime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getAllWorkIns(String serviceType, Map con,
			Timestamp starttime, Timestamp endtime, int offset, int limit) {
		// List<Map<String,Object>> listr = new ArrayList<Map<String,Object>>();
		if (starttime == null || endtime == null) {
			endtime = new Timestamp(System.currentTimeMillis());
			starttime = new Timestamp(System.currentTimeMillis() - 2592000000l); // 最近一个月
		}
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		if (runtimeService == null) {
			runtimeService = ApplicationContextManager.getContext().getBean(
					RuntimeService.class);
		}
		if (serviceType == null && con != null
				&& con.get(WorkFlowService.SERVICETYPEKEY) != null) {
			serviceType = (String) con.get(WorkFlowService.SERVICETYPEKEY);
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		String workTitle = null;
		if (con != null && con.get(WorkFlowService.WORKTITLEKEY) != null) {
			workTitle = (String) con.get(WorkFlowService.WORKTITLEKEY);
		}
		String branchId = null;
		if (con != null && con.get(WorkFlowService.APPLYBRANCHKEY) != null) {
			branchId = (String) con.get(WorkFlowService.APPLYBRANCHKEY);
		}
		Boolean needFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDFINISHED) != null) {
			needFinished = (Boolean) con.get(WorkFlowService.NEEDFINISHED);
		}
		Boolean needUnFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDUNFINISHED) != null) {
			needUnFinished = (Boolean) con.get(WorkFlowService.NEEDUNFINISHED);
		}
		boolean needInvolved = false;
		if (con != null && con.get(WorkFlowService.NEEDINVOLVEDKEY) != null) {
			needInvolved = (boolean) con.get(WorkFlowService.NEEDINVOLVEDKEY);
		}
		String userId = null;
		if (con != null && con.get(WorkFlowService.USERIDKEY) != null) {
			userId = (String) con.get(WorkFlowService.USERIDKEY);
		}
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		HistoricProcessInstanceQuery hiq = historyService
				.createHistoricProcessInstanceQuery().startedAfter(starttime)
				.startedBefore(endtime);
		if (serviceType != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.SERVICETYPEKEY,
					serviceType);
		}
		if (workTitle != null) {
			hiq = hiq.variableValueLike(WorkFlowService.WORKTITLEKEY, "%"
					+ workTitle + "%");
		}
		if (priority != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.PRIKEY, priority);
		}
		if (branchId != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.APPLYBRANCHKEY,
					branchId);
		}
		if (needFinished != null && needFinished == true) {
			hiq = hiq.finished();
		}
		if (needUnFinished != null && needUnFinished == true) {
			hiq = hiq.unfinished();
		}
		if (needInvolved == true && userId != null) {
			hiq = hiq.involvedUser(userId);
		}
		List<HistoricProcessInstance> listhpi = null;
		/* 这样查询的话 会是内存分页 */
		// if(offset<0||limit<0){
		// listhpi =
		// hiq.includeProcessVariables().orderByProcessInstanceStartTime().desc().list();
		// }else{
		// listhpi =
		// hiq.includeProcessVariables().orderByProcessInstanceStartTime().desc().listPage(offset,
		// limit);
		// }

		if (offset < 0 || limit < 0) {
			listhpi = hiq.orderByProcessInstanceStartTime().desc().list();
		} else {
			listhpi = hiq.orderByProcessInstanceStartTime().desc()
					.listPage(offset, limit);
		}
		for (HistoricProcessInstance hpi : listhpi) {
			String businessKey = hpi.getBusinessKey();
			// Date endtime1 = hpi.getEndTime();
			String PorcessInstanceId = hpi.getId();
			Map<String, Object> map = new HashMap<String, Object>();

			FormService fs = ApplicationContextManager.getContext().getBean(
					FormService.class);

			Map variablesmap = fs.getFormDataByFkFromProcessVariable(null,
					false, businessKey, null);
			map.putAll(variablesmap);
			/*
			 * if(variablesmap.get(WorkFlowService.FORMIDSTR)!=null){ String
			 * formId = (String) variablesmap.get(WorkFlowService.FORMIDSTR);
			 * FormService fs =
			 * ApplicationContextManager.getContext().getBean(FormService
			 * .class); map = fs.getFormDataByFk(formId, businessKey);
			 * map.put(WorkFlowService.FORMIDSTR,
			 * variablesmap.get(WorkFlowService.FORMIDSTR)); }
			 */
			if (variablesmap.get(WorkFlowService.SERVICETYPEKEY) != null) {
				map.put("serviceTypeId",
						variablesmap.get(WorkFlowService.SERVICETYPEKEY));
			}

			/*
			 * List<HistoricVariableInstance> variables =
			 * historyService.createHistoricVariableInstanceQuery
			 * ().processInstanceId(PorcessInstanceId).list();
			 * for(HistoricVariableInstance hvi:variables){
			 * if(hvi.getVariableName().equals(WorkFlowService.FORMIDSTR)){
			 * String formId = (String) hvi.getValue(); FormService fs =
			 * ApplicationContextManager
			 * .getContext().getBean(FormService.class); map =
			 * fs.getFormDataByFk(formId, businessKey);
			 * map.put(WorkFlowService.FORMIDSTR, hvi.getValue());
			 * 
			 * } } for(HistoricVariableInstance hvi:variables){
			 * if(hvi.getVariableName().equals(WorkFlowService.SERVICETYPEKEY)){
			 * if(hvi.getValue()!=null){ map.put("serviceTypeId",
			 * hvi.getValue()); } } }
			 */
			List<Task> listtask = taskService.createTaskQuery()
					.processInstanceId(PorcessInstanceId).list();
			if (listtask.size() > 0) {
				StringBuilder activiname = new StringBuilder();
				for (Task t : listtask) {
					activiname.append(t.getName()).append(",");
				}

				map.put("workStage",
						activiname.substring(0, activiname.length() - 1));
			}

			listmap.add(map);
		}
		return listmap;
	}

	/**
	 * 
	 * @param serviceType
	 * @param starttime
	 * @param endtime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getWorkInsBySTS(String[] serviceType,
			Map con, Timestamp starttime, Timestamp endtime, int offset,
			int limit) {
		List<Map<String, Object>> listr = new ArrayList<Map<String, Object>>();
		if (starttime == null || endtime == null) {
			endtime = new Timestamp(System.currentTimeMillis());
			starttime = new Timestamp(System.currentTimeMillis() - 2592000000l); // 最近一个月
		}
		if (serviceType == null) {
			Dao<DictDataInfo> daod = DaoFactory.create(DictDataInfo.class);
			Dao<DictTypeInfo> daot = DaoFactory.create(DictTypeInfo.class);
			DictTypeInfo cont = new DictTypeInfo();
			DictDataInfo cond = new DictDataInfo();
			cont.setDicttypeName("服务分类");
			cont = daot.selectOne(cont);
			cond.setDicttypeId(cont);
			List<DictDataInfo> list = daod.select(cond);
			for (DictDataInfo d : list) {
				listr.addAll(this.getWorkInsByST(d.getDictdataName(), con,
						starttime, endtime, offset, limit));
			}
			return listr;
		} else {
			for (String s : serviceType) {
				listr.addAll(this.getWorkInsByST(s, con, starttime, endtime,
						offset, limit));
				return listr;
			}
		}
		return listr;
	}

	/**
	 * 获取所有类型工单
	 * 
	 * @param serviceType
	 * @param starttime
	 * @param endtime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getWorkInsByST(String serviceType,
			Map con, Timestamp starttime, Timestamp endtime, int offset,
			int limit) {
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		if (runtimeService == null) {
			runtimeService = ApplicationContextManager.getContext().getBean(
					RuntimeService.class);
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		String workTitle = null;
		if (con != null && con.get(WorkFlowService.WORKTITLEKEY) != null) {
			workTitle = (String) con.get(WorkFlowService.WORKTITLEKEY);
		}
		String branchId = null;
		if (con != null && con.get(WorkFlowService.APPLYBRANCHKEY) != null) {
			branchId = (String) con.get(WorkFlowService.APPLYBRANCHKEY);
		}
		Boolean needFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDFINISHED) != null) {
			needFinished = (Boolean) con.get(WorkFlowService.NEEDFINISHED);
		}
		Boolean needUnFinished = null;
		if (con != null && con.get(WorkFlowService.NEEDUNFINISHED) != null) {
			needUnFinished = (Boolean) con.get(WorkFlowService.NEEDUNFINISHED);
		}
		if (serviceType == null && con != null
				&& con.get(WorkFlowService.SERVICETYPEKEY) != null) {
			serviceType = (String) con.get(WorkFlowService.SERVICETYPEKEY);
		}
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		HistoricProcessInstanceQuery hiq = historyService
				.createHistoricProcessInstanceQuery();
		if (starttime != null && endtime != null) {
			hiq = hiq.startedAfter(starttime).startedBefore(endtime);
		}
		if (serviceType != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.SERVICETYPEKEY,
					serviceType);
		}
		if (workTitle != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.WORKTITLEKEY,
					workTitle);
		}
		if (priority != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.PRIKEY, priority);
		}
		if (branchId != null) {
			hiq = hiq.variableValueEquals(WorkFlowService.APPLYBRANCHKEY,
					branchId);
		}
		if (needFinished != null && needFinished == true) {
			hiq = hiq.finished();
		}
		if (needUnFinished != null && needUnFinished == true) {
			hiq = hiq.unfinished();
		}
		// 如果外面查询包含流程变量 则分页为内存分页，数据多了 不止会不会有问题，所以现在外面查询是不包含变量，吧list查出来以后
		// 再对list中的各个值查询流程变量
		List<HistoricProcessInstance> listhpi = hiq
				.orderByProcessInstanceStartTime().desc()
				.listPage(offset, limit);
		for (HistoricProcessInstance hpi : listhpi) {
			String businessKey = hpi.getBusinessKey();
			String PorcessInstanceId = hpi.getId();
			Map<String, Object> map = new HashMap<String, Object>();
			// ProcessInstance pi =
			// piq.processInstanceId(PorcessInstanceId).singleResult();
			// Map map =
			// runtimeService.getVariables(t.getExecutionId());//获取流程变量
			// historyService.createHistoricProcessInstanceQuery().includeProcessVariables().processInstanceId(PorcessInstanceId).singleResult();

			FormService fs = ApplicationContextManager.getContext().getBean(
					FormService.class);
			map = fs.getFormDataByFkFromProcessVariable(null, false,
					businessKey, null);
			/*
			 * List<HistoricVariableInstance> variables =
			 * historyService.createHistoricVariableInstanceQuery
			 * ().processInstanceId(PorcessInstanceId).list(); FormService
			 * formService =
			 * ApplicationContextManager.getContext().getBean(FormService
			 * .class); for(HistoricVariableInstance hvi:variables){
			 * if(hvi.getVariableName().equals(WorkFlowService.FORMIDSTR)){
			 * String formId = (String) hvi.getValue(); FormService fs =
			 * ApplicationContextManager
			 * .getContext().getBean(FormService.class); map =
			 * fs.getFormDataByFk(formId, businessKey);
			 * map.put(WorkFlowService.FORMIDSTR, hvi.getValue());
			 * 
			 * } }
			 */

			List<Task> listtask = taskService.createTaskQuery()
					.processInstanceId(PorcessInstanceId).list();
			if (listtask.size() > 0) {
				StringBuilder activiname = new StringBuilder();
				for (Task t : listtask) {
					activiname.append(t.getName()).append(",");
				}

				map.put("workStage",
						activiname.substring(0, activiname.length() - 1));
			}
			map.put("serviceTypeId", serviceType);
			listmap.add(map);
			// }
		}
		return listmap;
		// if(sps==null){
		// sps =
		// ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		// }
		// List<ServiceProduct> listsp = sps.getSPByserviceType(serviceType);
		// for(ServiceProduct sp:listsp){
		// list.addAll(this.getWorkInsBySP(sp, con,starttime, endtime, offset,
		// limit));
		// }
		// return list;
	}

	/**
	 * 按服务产品获取所有的工单
	 * 
	 * @param sp
	 * @param starttime
	 * @param endtime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getWorkInsBySP(ServiceProduct sp, Map con,
			Timestamp starttime, Timestamp endtime, int offset, int limit) {
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		if (runtimeService == null) {
			runtimeService = ApplicationContextManager.getContext().getBean(
					RuntimeService.class);
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		// ProcessInstanceQuery piq =
		// runtimeService.createProcessInstanceQuery();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		// List list =
		// runtimeService.createProcessInstanceQuery().variableValueEquals(WorkFlowService.PRODUCTNO,
		// sp.getProductNo()).list();
		List<HistoricProcessInstance> listhpi = historyService
				.createHistoricProcessInstanceQuery()
				.variableValueEquals(WorkFlowService.PRIKEY, priority)
				.variableValueEquals(WorkFlowService.PRODUCTNO,
						sp.getProductNo()).startedAfter(starttime)
				.startedBefore(endtime).orderByProcessInstanceStartTime()
				.desc().listPage(offset, limit);
		for (HistoricProcessInstance hpi : listhpi) {
			String businessKey = hpi.getBusinessKey();
			// Date endtime1 = hpi.getEndTime();
			String PorcessInstanceId = hpi.getId();
			// WorkService work = new WorkService();
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put(WorkFlowService.PRODUCTNO, value)
			// ProcessInstance pi =
			// piq.processInstanceId(PorcessInstanceId).singleResult();
			// Map map =
			// runtimeService.getVariables(t.getExecutionId());//获取流程变量
			List<HistoricVariableInstance> variables = historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(PorcessInstanceId).list();
			for (HistoricVariableInstance hvi : variables) {
				if (hvi.getVariableName().equals(WorkFlowService.FORMIDSTR)) {
					String formId = (String) hvi.getValue();
					FormService fs = ApplicationContextManager.getContext()
							.getBean(FormService.class);
					map = fs.getFormDataByFk(formId, businessKey);
					map.put(WorkFlowService.FORMIDSTR, hvi.getValue());
				}
			}

			List<Task> listtask = taskService.createTaskQuery()
					.processInstanceId(PorcessInstanceId).list();
			if (listtask.size() > 0) {
				StringBuilder activiname = new StringBuilder();
				for (Task t : listtask) {
					activiname.append(t.getName()).append(",");
				}

				map.put("workStage",
						activiname.substring(0, activiname.length() - 1));
			}
			listmap.add(map);
			// }
		}
		return listmap;
	}

	/**
	 * RT此处和待办事项 应该还是有区别的 待办的只是任务，此处看流程实例的数量
	 * 
	 * @param sp
	 * @return
	 */
	public int getWorkInHandleTotalBySP(ServiceProduct sp, Map con,
			Timestamp starttime, Timestamp endtime) {
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		HistoricProcessInstanceQuery hpi = historyService
				.createHistoricProcessInstanceQuery().unfinished()
				.startedAfter(starttime).startedBefore(endtime);
		if (sp != null) {
			hpi = hpi.variableValueEquals("productNo", sp.getProductNo());
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		String workTitle = null;
		if (con != null && con.get(WorkFlowService.WORKTITLEKEY) != null) {
			workTitle = (String) con.get(WorkFlowService.WORKTITLEKEY);
		}
		String branchId = null;
		if (con != null && con.get(WorkFlowService.APPLYBRANCHKEY) != null) {
			branchId = (String) con.get(WorkFlowService.APPLYBRANCHKEY);
		}
		if (priority != null) {
			hpi = hpi.variableValueEquals(WorkFlowService.PRIKEY, priority);
		}
		if (branchId != null) {
			hpi = hpi.variableValueEquals(WorkFlowService.APPLYBRANCHKEY,
					branchId);
		}
		return (int) hpi.count();
		// return (int)
		// runtimeService.createProcessInstanceQuery().active().variableValueEquals("productNo",
		// sp.getProductNo()).count();
	}

	/**
	 * RT
	 * 
	 * @param sp
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public int getWorkClosedTotalBySP(ServiceProduct sp, Map con,
			Timestamp starttime, Timestamp endtime) {
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		HistoricProcessInstanceQuery hpi = historyService
				.createHistoricProcessInstanceQuery().finished()
				.startedAfter(starttime).startedBefore(endtime);
		if (sp != null) {
			hpi = hpi.variableValueEquals("productNo", sp.getProductNo());
		}
		String priority = null;
		if (con != null && con.get(WorkFlowService.PRIKEY) != null) {
			priority = (String) con.get(WorkFlowService.PRIKEY);
		}
		String workTitle = null;
		if (con != null && con.get(WorkFlowService.WORKTITLEKEY) != null) {
			workTitle = (String) con.get(WorkFlowService.WORKTITLEKEY);
		}
		String branchId = null;
		if (con != null && con.get(WorkFlowService.APPLYBRANCHKEY) != null) {
			branchId = (String) con.get(WorkFlowService.APPLYBRANCHKEY);
		}
		if (priority != null) {
			hpi = hpi.variableValueEquals(WorkFlowService.PRIKEY, priority);
		}
		if (branchId != null) {
			hpi = hpi.variableValueEquals(WorkFlowService.APPLYBRANCHKEY,
					branchId);
		}
		return (int) hpi.count();
	}

	/**
	 * RT
	 * 
	 * @param variable
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public List<HistoricProcessInstance> getProcessInstancesByProcessVariable(
			Map variable, Timestamp starttime, Timestamp endtime) {
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		HistoricProcessInstanceQuery hq = historyService
				.createHistoricProcessInstanceQuery();
		if (variable != null) {
			Set<String> keys = variable.keySet();
			for (String k : keys) {
				hq.variableValueEquals(k, variable.get(k));
			}
		}
		Boolean needFinished = null;
		if (variable != null
				&& variable.get(WorkFlowService.NEEDFINISHED) != null) {
			needFinished = (Boolean) variable.get(WorkFlowService.NEEDFINISHED);
			if (needFinished) {
				hq = hq.finished();
			}
		}
		Boolean needUnFinished = null;
		if (variable != null
				&& variable.get(WorkFlowService.NEEDUNFINISHED) != null) {
			needUnFinished = (Boolean) variable
					.get(WorkFlowService.NEEDUNFINISHED);
			if (needUnFinished) {
				hq = hq.unfinished();
			}
		}
		if (starttime != null && endtime != null) {
			hq = hq.startedAfter(starttime).startedBefore(endtime);
		}
		return hq.includeProcessVariables().list();
	}

	// 获取实例流程变量
	public List<HistoricVariableInstance> getHistoricVariableByInsId(
			String processInstanceId) {
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		List<HistoricVariableInstance> list = historyService
				.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId).list();
		return list;
	}

	/**
	 * 获取历史流程中的流程变量
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	public Object getHistoricVariableByIdKey(String processInstanceId,
			String key) {

		List<HistoricVariableInstance> list = this
				.getHistoricVariableByInsId(processInstanceId);
		for (HistoricVariableInstance hvi : list) {
			if (hvi.getVariableName().equals(key)) {
				return hvi.getValue();
			}
		}
		return null;
	}

	/**
	 * RT获取个部门工单数据 把流程流程实例全部查询出来，查出来的流程实例中key 为branchKey的流程变量的值就是部门Id
	 * 
	 * @param branchKey
	 *            部门流程变量的key
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public Map<String, Integer> getWorkBranchStatistic(String branchKey,
			Timestamp starttime, Timestamp endtime) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(WorkFlowService.TOTAL, 0);
		OrgService orgService = ApplicationContextManager.getContext().getBean(
				OrgService.class);
		if (starttime == null || endtime == null) {
			endtime = new Timestamp(System.currentTimeMillis());
			starttime = new Timestamp(System.currentTimeMillis() - 2592000000l); // 最近一个月
		}
		if (branchKey == null) {
			branchKey = "applyBranchId";
		}
		Map<String, String> branches = new HashMap(); // 缓存部门数据，这样就不用每次查数据库了
		List<HistoricProcessInstance> list = this
				.getProcessInstancesByProcessVariable(null, starttime, endtime);
		for (HistoricProcessInstance hp : list) {
			Map variables = hp.getProcessVariables();
			Object o = variables.get(WorkFlowService.APPLYBRANCHKEY);
			Object o1 = variables.get(WorkFlowService.PRODUCTNOKEY);// 查看是否是服务产品发起的流程
			String s = null;
			if (o != null && o.getClass().equals(String.class)) {
				s = (String) o;
			}
			if (o1 != null && s != null && branches.get(s) != null) {
				String orgname = branches.get(s);
				if (map.containsKey(orgname)) {
					map.put(orgname, map.get(orgname) + 1);
				} else {
					map.put(orgname, 1);
				}
				map.put(WorkFlowService.TOTAL,
						map.get(WorkFlowService.TOTAL) + 1);
			} else if (o1 != null && s != null && branches.get(s) == null
					&& orgService.queryByPK(s) != null) {
				branches.put(s, orgService.queryByPK(s).getOrgShowName());
				String orgname = branches.get(s);
				if (map.containsKey(orgname)) {
					map.put(orgname, map.get(orgname) + 1);
				} else {
					map.put(orgname, 1);
				}
				map.put(WorkFlowService.TOTAL,
						map.get(WorkFlowService.TOTAL) + 1);
			} else if (o1 != null
					&& (s == null || orgService.queryByPK(s) == null)) {
				if (map.containsKey("未知")) {
					map.put("未知", map.get("未知") + 1);
				} else {
					map.put("未知", 1);
				}
				map.put(WorkFlowService.TOTAL,
						map.get(WorkFlowService.TOTAL) + 1);
			}
		}
		return map;
	}

	/**
	 * RT
	 * 
	 * @param moduleId
	 * @return
	 */
	public WfProcessDefinition getBindProcessByModuleId(String moduleId) {
		if (wfManagerService == null) {
			wfManagerService = ApplicationContextManager.getContext().getBean(
					WfManagerService.class);
		}
		WfProcessDefinition wfProcessDefinition = null;
		try {
			wfProcessDefinition = wfManagerService
					.getBindProcessByModuleId(moduleId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wfProcessDefinition;
	}

/*	*//**
	 * 
	 * @param businessKey
	 * @param workType
	 * @param ids
	 * @return
	 *//*
	public int addWorkAssetRel(String businessKey, String workType, String[] ids) {
		Dao<WorkAssetRel> dao = DaoFactory.create(WorkAssetRel.class);
		List<WorkAssetRel> list = new ArrayList<WorkAssetRel>();
		for (String s : ids) {
			WorkAssetRel war = new WorkAssetRel();
			war.setAssetId(s);
			war.setWorkId(businessKey);
			List listw = dao.select(war);
			if (listw != null && listw.size() > 0) {
				continue;
			}
			war.setWorkType(workType);
			list.add(war);
		}
		return dao.insertBatch(list);
	}

	*//**
	 * RT
	 * 
	 * @param ids
	 * @return
	 *//*
	public int deleteWorkAssetRel(String businessKey, String[] ids) {
		Dao<WorkAssetRel> dao = DaoFactory.create(WorkAssetRel.class);
		List<WorkAssetRel> list = new ArrayList<WorkAssetRel>();
		for (String s : ids) {
			WorkAssetRel war = new WorkAssetRel();
			war.setAssetId(s);
			war.setWorkId(businessKey);
			list.addAll(dao.select(war));
		}
		return dao.deleteBatch(list);
	}

	*//**
	 * 
	 * @param workId
	 * @param workIds
	 * @return
	 *//*
	public int addWorkRel(String workId, String[] workIds) {
		Dao<WorkRel> dao = DaoFactory.create(WorkRel.class);
		List<WorkRel> list = new ArrayList<WorkRel>();
		for (String s : workIds) {
			WorkRel wr = new WorkRel();
			wr.setWorkId(workId);
			wr.setWworkId(s);
			List listw = dao.select(wr);
			if (listw != null && listw.size() > 0) {
				continue;
			}
			list.add(wr);
		}
		return dao.insertBatch(list);
	}

	*//**
	 * 
	 * @param workId
	 * @param workIds
	 * @return
	 *//*
	public int delWorkRel(String workId, String[] workIds) {
		Dao<WorkRel> dao = DaoFactory.create(WorkRel.class);
		List<WorkRel> list = new ArrayList<WorkRel>();
		for (String s : workIds) {
			WorkRel wr = new WorkRel();
			wr.setWorkId(workId);
			wr.setWworkId(s);
			list.addAll(dao.select(wr));
		}
		return dao.deleteBatch(list);
	}
*/
	/**
	 * 根据工单businessKey 获取工单数据
	 * 
	 * @param wworkId
	 * @return
	 */
	public Map<String, Object> getWorkServiceByBK(String wworkId) {
		// TODO Auto-generated method stub
		if (historyService == null) {
			historyService = ApplicationContextManager.getContext().getBean(
					HistoryService.class);
		}
		if (taskService == null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
		}
		if (sps == null) {
			sps = ApplicationContextManager.getContext().getBean(
					ServiceProductService.class);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// work = daows.selectOne(work);
		HistoricProcessInstance hpi = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(wworkId).singleResult();
		if (hpi == null) {
			return null;
		}
		String PorcessInstanceId = hpi.getId();
		List<HistoricVariableInstance> variables = historyService
				.createHistoricVariableInstanceQuery()
				.processInstanceId(PorcessInstanceId).list();
		// 得先把 formId给找出来
		for (HistoricVariableInstance hvi : variables) {
			// if(hvi.getVariableName().equals(WorkFlowService.FORMIDSTR)){
			// FormService fs =
			// ApplicationContextManager.getContext().getBean(FormService.class);
			// map = fs.getFormDataByFk((String) hvi.getValue(), wworkId);
			// map.put(WorkFlowService.FORMIDSTR, hvi.getValue());
			// }
			// 流程变量及业务数据
			map.put(hvi.getVariableName(), hvi.getValue());
			if (hvi.getVariableName().equals(WorkFlowService.PRODUCTNO)) {
				ServiceProduct sp = sps.getServiceProductByNo((String) hvi
						.getValue());
				map.put("serviceTypeId", sp.getServiceTypeId());
			}
			if (hvi.getVariableName().equals(WorkFlowService.HAPPENEDTIMEL)) {
				Object o = hvi.getValue();
				if (o instanceof String) {
					map.put("happenDate",
							new Timestamp(Long.valueOf((String) hvi.getValue())));
				} else if (o instanceof Long) {
					map.put("happenDate", new Timestamp((long) o));
				}

			}
		}
		List<Task> listtask = taskService.createTaskQuery()
				.processInstanceId(PorcessInstanceId).list();
		if (listtask != null && listtask.size() > 0) {
			StringBuilder activiname = new StringBuilder();
			for (Task t : listtask) {
				activiname.append(t.getName()).append(",");
			}
			map.put("workStage",
					activiname.substring(0, activiname.length() - 1));
		}

		return map;
	}

	/**
	 * 
	 * @param productNo
	 * @return
	 */
	public boolean hasUnfinishedServiceProductWork(String productNo) {
		Map variable = new HashMap();
		variable.put(WorkFlowService.PRODUCTNOKEY, productNo);
		variable.put(WorkFlowService.NEEDUNFINISHED, true);
		List list = this.getProcessInstancesByProcessVariable(variable, null,
				null);
		return list.size() > 0 ? true : false;
	}

	/**
	 * 获取追回的目的环节id
	 * @param proInsId
	 * @param taskId
	 * @param userId
	 * @param businessId
	 * @return
	 */
	public String getRecoverToTaskDefId(String proInsId, String taskId,
			String userId, String businessId) {
 		if(taskId == null||taskId.equals("")){
 			return null;
 		}
		HistoricActivityInstanceQuery htiQuery = historyService
				.createHistoricActivityInstanceQuery();
		List<HistoricActivityInstance> htiList = htiQuery
				.processInstanceId(proInsId).finished()
				.orderByHistoricActivityInstanceEndTime().desc().listPage(0, 5);
		HistoricActivityInstance lastActivity = null;
		//处理网关的情形
		for(int i=0; i<htiList.size(); i++){
			HistoricActivityInstance activity = htiList.get(i);
			if(activity.getActivityType() != null
					&& activity.getActivityType().equals("userTask")){
				//找到最后一个 userTask 就好了 
				lastActivity = activity;
				break;
			}
		}
		if(lastActivity == null){
			return null;
		}
		String processDefinitionId = lastActivity.getProcessDefinitionId();
		ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) repositoryService.getProcessDefinition(processDefinitionId);
		ActivityImpl activity = processDefinition.findActivity(lastActivity.getActivityId());	
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (lastActivity.getAssignee() != null
				&& lastActivity.getAssignee().equals(userId)
				&& activity.getProperty("multiInstance")==null
//				&& !activity.getProperty("multiInstance").equals("parallel")
				&& lastActivity.getActivityType() != null
				&& lastActivity.getActivityType().equals("userTask")
				&& !task.getTaskDefinitionKey().equals(lastActivity.getActivityId())) {//最后完成任务定义和当前任务定义一样表示是被追回过的任务了，或者是正在会签中
			return lastActivity.getActivityId();
		}
		return null;
	}

	@Autowired
	UserJobService userJobService;
	@Autowired
	ActivityConfigService activityConfigService;
	public List<Map> getHandlerFormCandidateWithFilter(
			String processDefinitionId, String moduleId, String taskDefKey,
			String filterType, String curUserId) {
		if(filterType==null){
			Map actions = activityConfigService.getActivityActions(moduleId, taskDefKey);
			if(actions.containsKey(ActivityConfigService.ACTION_FILTERTYPE)){
				List filterTypes = (List) actions.get(ActivityConfigService.ACTION_FILTERTYPE);
				//TODO
				if(filterTypes.size()==1){
					filterType = (String) filterTypes.get(0);
				}
			}
		}
		List<Map> result = new ArrayList<Map>();
		List<Map> list = this.getActivityCandidates(processDefinitionId,
				moduleId, taskDefKey);
		OrgDTO orgDTO = userService.queryMainOrg(curUserId);
		String curOrgName = orgDTO == null?"系统":orgDTO.getOrgName();
//		String curOrgName = userService.queryMainOrg(curUserId).getOrgName();
		for (Map map : list) {
			String category = (String) map.get("category");
			String id = (String) map.get("id");
			if (category.equals("group")) {
				//则不需要过滤  返回组内所有人还是返回组呢？？
				if(filterType==null){
					List<UserDTO> userList = userJobService.getAllUserJob(id);
					for(UserDTO user:userList){
						OrgDTO uorgDTO = userService.queryMainOrg(user.getUserId());
						String orgName = orgDTO == null?"系统":uorgDTO.getOrgName();						
//						String orgName = userService.queryMainOrg(user.getUserId()).getOrgName();
						Map map1 = new HashMap();
						map1.put("id", user.getUserId());
						map1.put("name", user.getUserRealname());
						map1.put("type", "user");
						map1.put("category", orgName);
						result.add(map1);
					}
					continue;
				}			
				switch(filterType){
				case "orgfilter":
					
					List<UserDTO> userList = userJobService
					.getAllUserFromJobWithSameOrg(id, curUserId);	
					for(UserDTO user:userList){
						Map map1 = new HashMap();
						map1.put("id", user.getUserId());
						map1.put("name", user.getUserRealname());
						map1.put("type", "user");
						map1.put("category", curOrgName);
						result.add(map1);
					}
					break;
				case "orgbossfilter":
					break;
//				default:
//					Map map1 = new HashMap();
//					map1.put("id", id);
//					map1.put("name",(String) map.get("name"));
//					map1.put("type", "group");
//					map1.put("category", orgName);
//					result.add(map1);	
//					break;
				}

			} else if (category.equals("user")) {
				UserDTO user = userService.queryByPK(id);
				Map map1 = new HashMap();
				map1.put("id", user.getUserId());
				map1.put("name", user.getUserRealname());
				map1.put("type", "user");
				map1.put("category", curOrgName);
				result.add(map1);
				continue;
			}
		}
		return result;
	}
	
	public List<Map> getHandlerFormCandidateWithFilter1(
			String processDefinitionId, String proInsId, String moduleId, String taskDefKey,
			String filterType, String curUserId) {
		if(filterType==null){
			Map actions = activityConfigService.getActivityActions(moduleId, taskDefKey);
			if(actions.containsKey(ActivityConfigService.ACTION_FILTERTYPE)){
				List filterTypes = (List) actions.get(ActivityConfigService.ACTION_FILTERTYPE);
				//TODO
				if(filterTypes.size()==1){
					filterType = (String) filterTypes.get(0);
				}
			}
		}
		List<Map> result = new ArrayList<Map>();
		List<Map> list = this.getActivityCandidates(processDefinitionId,
				moduleId, taskDefKey);
		OrgDTO orgDTO = userService.queryMainOrg(curUserId);
		String curOrgName = orgDTO == null?"系统":orgDTO.getOrgName();
		for (Map map : list) {
			String category = (String) map.get("category");
			String id = (String) map.get("id");
			if (category.equals("group")) {
				//则不需要过滤  返回组内所有人还是返回组呢？？
				if(filterType==null){
					List<UserDTO> userList = userJobService.getAllUserJob(id);
					for(UserDTO user:userList){
						OrgDTO uorgDTO = userService.queryMainOrg(user.getUserId());
						String orgName = orgDTO == null?"系统":uorgDTO.getOrgName();
//						String orgName = userService.queryMainOrg(user.getUserId()).getOrgName();
						Map map1 = new HashMap();
						map1.put("id", user.getUserId());
						map1.put("name", user.getUserRealname());
						map1.put("type", "user");
						map1.put("category", orgName);
						result.add(map1);
					}
					continue;
				}			
				switch (filterType) {
				case "orgfilter":

					List<UserDTO> userList = userJobService
							.getAllUserFromJobWithSameOrg(id, curUserId);
					for (UserDTO user : userList) {
						Map map1 = new HashMap();
						map1.put("id", user.getUserId());
						map1.put("name", user.getUserRealname());
						map1.put("type", "user");
						map1.put("category", curOrgName);
						result.add(map1);
					}
					break;
				case "orgbossfilter":
					String startId = null;
					if(proInsId == null || "".equals(proInsId)){
						startId = curUserId;
					}else{
						ProcessInstance ins = runtimeService
								.createProcessInstanceQuery()
								.processInstanceId(proInsId)
								.includeProcessVariables().singleResult();
						Map vmap = ins.getProcessVariables();
						startId = (String) vmap.get(STARTER);						
					}
					UserService userService = ApplicationContextManager
							.getContext().getBean(UserService.class);
					OrgDTO org = userService.queryMainOrg(startId);
					OrgextService extService = ApplicationContextManager
							.getContext().getBean(OrgextService.class);
					Orgext ext = extService.getOneOrgextByOrgId(org.getOrgId());
					if(ext.getId() != null){
						UserDTO user = userService.queryByPK(ext.getOrgSuperviserId());
						OrgDTO orgSv = userService.queryMainOrg(ext.getOrgSuperviserId());
						Map map1 = new HashMap();
						map1.put("id", user.getUserId());
						map1.put("name", user.getUserRealname());
						map1.put("type", "user");
						map1.put("category", orgSv.getOrgName());
						result.add(map1);
					}
					break;
				// default:
				// Map map1 = new HashMap();
				// map1.put("id", id);
				// map1.put("name",(String) map.get("name"));
				// map1.put("type", "group");
				// map1.put("category", orgName);
				// result.add(map1);
				// break;
				}

			} else if (category.equals("user")) {
				UserDTO user = userService.queryByPK(id);
				Map map1 = new HashMap();
				map1.put("id", user.getUserId());
				map1.put("name", user.getUserRealname());
				map1.put("type", "user");
				map1.put("category", curOrgName);
				result.add(map1);
				continue;
			}
		}
		return result;
	}	
	
	/**
	 * 根据流程实例id获取一些流程必要的信息如 formId businesskey ext..
	 * @param procInsId
	 * @return
	 */
	public Map<String, Object> getProcInfoByProcInsId(String procInsId) {
//		Map<String, Object> result = new HashMap<String, Object>();
		HistoricProcessInstance ins = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(procInsId).includeProcessVariables()
				.singleResult();
		Map variables = ins.getProcessVariables();
		variables.put("businessKey",ins.getBusinessKey());
		variables.put("processDefinitionId", ins.getProcessDefinitionId());
		return variables;
	}
	    
	public Form getFormByServiceType(String ServiceTypeId) {
		List<ServiceProduct> list = sps.getSPByserviceType(ServiceTypeId);
		if (list.size() == 1) {
			ServiceProduct sp = list.get(0);
			String formId = sp.getFormId();
			FormService formService = ApplicationContextManager.getContext()
					.getBean(FormService.class);
			Form form = formService.getFormById(formId);
			return form;
		}
		return null;
	}

	/**
	 * 根据流程实例id删除流程实例
	 * @param json 业务数据
	 * @param wfOperator
	 *            操作者信息，必须参数
	 * @param deleteReason
	 *            删除原因
	 * @param processInstanceId
	 *            流程实例id,必须参数
	 * @param formId
	 *            formId
	 * @return 200-操作成功⾿00-参数不正确ὴ00-操作失败⾿04-操作对象不存嚿
	 * @throws Exception
	 */
	@Transactional
	public WfResult suspendProcessInstancesById(String json,WfOperator wfOperator,
 			String suspendReason, String processInstanceId, String formId, boolean updateBusinessForm)
			throws Exception {
		WfResult result = suspendHisProcessInstancesById(wfOperator,processInstanceId);
 		return result;
 	}
 
 	/**
 	 * 根据流程实例id删除流程实例
 	 * @param processInstanceId
 	 *            流程实例id,必须参数
 	 * @return 200-操作成功⾿00-参数不正确ὴ00-操作失败⾿04-操作对象不存嚿
 	 * @throws Exception
 	 */
 	@Transactional
 	public WfResult suspendHisProcessInstancesById(WfOperator wfOperator,String processInstanceId)
 			throws Exception {
 		WfResult result = WfApiFactory.getWfRuntimeService().suspendProcessInstance(wfOperator, processInstanceId);
 		return result;
 	}	

 	/**
 	 * 根据流程实例id删除流程实例
 	 * @param processInstanceId
 	 *            流程实例id,必须参数
 	 * @return 200-操作成功⾿00-参数不正确ὴ00-操作失败⾿04-操作对象不存嚿
 	 * @throws Exception
 	 */
 	@Transactional
 	public boolean suspendHisProcessInstancesById(String processInstanceId)
 			throws Exception {
 		try{
 	 		runtimeService.suspendProcessInstanceById(processInstanceId);
 		}catch(Exception e){
 			return false;
 		}
 		return true;
 	}
 	
}
