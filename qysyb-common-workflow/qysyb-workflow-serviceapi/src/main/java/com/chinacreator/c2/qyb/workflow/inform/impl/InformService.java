package com.chinacreator.c2.qyb.workflow.inform.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.config.entity.ActivityConfig;
import com.chinacreator.c2.qyb.workflow.config.entity.UserConcernedConfig;
import com.chinacreator.c2.qyb.workflow.config.impl.ActivityConfigService;
import com.chinacreator.c2.qyb.workflow.config.impl.UserConcernedConfigService;
import com.chinacreator.c2.qyb.workflow.group.impl.UserJobService;
import com.chinacreator.c2.qyb.workflow.inform.inf.EmailTask;
import com.chinacreator.c2.qyb.workflow.inform.inf.InformTask;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;

/**
 * 
 * @author qiao
 * 
 */
@Service
public class InformService {
	public final static String WORKSTATGEKEY = "stage";
	public final static String COMMENT_KEY = "comment";
	public final static String CATEGORY_KEY = "category";
	public final static String COMMENT_USERID_KEY = "commentUserId";
	public final static String COMMENT_MODULE_KEY = "workModule";
	public final static String COMMENT_TITLE_KEY = "workTitle";
	public final static String CHANNEL_KEY = "channel";

	public final static String TEMPLATE_TASK_INFORM_NO = "templateTaskInform";
	public final static String TEMPLATE_COMMENT_INFORM_NO = "templateCommentInform";
	public final static String STR_TEMPLATE_KEY = "templateStr";

	public final static String INFORM_TYPE_EMAIL = "email";
	public final static String INFORM_TYPE_PHONE_MSG = "phone_msg";
	public final static String INFORM_TYPE_INNER_MSG = "sys_inner_msg";

	public final static String EVENT_TYPE_COMMENT = "commentEvent";
	public final static String EVENT_TYPE_TASK = "taskEvent";

	public final static String CC_INFORM_TYPES = "ccInformTypes";
	public final static String CC_INFORM_USERIDS = "ccToUserIds";

	public final static String TASK_INFORM_EMAIL_TEMPLATE = "亲爱的 %s:\n有一个工单处于 "
			+ "%s 阶段,需要您的签收或处理。\n可以点击如下链接登录系统:"
			+ "%s\n如果您的email程序不支持链接点击，请将上面的地址拷贝至您的浏览器的地址栏进入。\n  "
			+ "如果不希望接收到这封邮件，可以进入系统后进行取消通知的操作（如果有权限的话）。\n如有疑问，请联系管理员。" + "谢谢！\n"
			+ "-----------------------  \n(这是一封自动产生的email，请勿回复。)  ";
	public final static String COMMENT_INFORM_EMAIL_TEMPLATE = "亲爱的 %s:\n你关注的工单在 %s 环节有了一个新评论:\n%s created by %s\n "
			+ "\n可以点击如下链接登录系统查看:"
			+ "%s\n如果您的email程序不支持链接点击，请将上面的地址拷贝至您的浏览器的地址栏进入。\n  "
			+ "如果不希望接收到这封邮件，可以进入系统后进行取消通知的操作（如果有权限的话）。\n如有疑问，请联系管理员。"
			+ "谢谢！\n"
			+ "-----------------------  \n(这是一封自动产生的email，请勿回复。)  ";
	public final static String COMMENT_INFORM_INNER_TEMPLATE = "您有一条未读通知提醒!";
	// 监听器注册进来的事件
	private List<ActivitiEntityEvent> taskEvents = new ArrayList<ActivitiEntityEvent>();
	private List<ActivitiEntityEvent> commentEvents = new ArrayList<ActivitiEntityEvent>();
	// 防止重复发送邮件
	private List<InformTask> listTaskTodo = new ArrayList<InformTask>();

	private Map busientity = new HashMap();
	private String moduleId;
	@Autowired
	private ActivityConfigService activityConfigService;
	@Autowired
	private UserConcernedConfigService userConcernedConfigService;
	@Autowired
	private ServiceProductService productService;
	private RuntimeService runtimService;
	@Autowired
	private TaskService taskService;
	private HistoryService historyService;

	private String ccInformJsonStr;

	public synchronized void informDo(String moduleId, Map entity) {
		this.busientity = entity;
		this.moduleId = moduleId;
		informDo();
	}

	/**
	 * 
	 */
	public synchronized void informDo() {

		ServiceProduct sp = productService.getServiceProductById(moduleId);
		try {
			/* 通知出错不要导致流程回滚,抓获异常 */
			InformDo informDo = ApplicationContextManager.getContext().getBean(
					InformDo.class);			
			informDo.before();
			for (ActivitiEntityEvent e : taskEvents) {
				TaskEntity entity = (TaskEntity) e.getEntity();
				if (informDo != null) {
					if (ccInformJsonStr != null) {
						informDo.setCcInformStr(ccInformJsonStr);
					}
					ActivityConfig con = new ActivityConfig();
					con.setModuleId(sp.getProductId());
					con.setTaskDefId(entity.getTaskDefinitionKey());
					ActivityConfig ac = activityConfigService
							.getActivityConfigOne(con);
					if (ac != null) {
						informDo.setAc(ac);
					}
					UserConcernedConfig ucc = new UserConcernedConfig();
					ucc.setProcessDefId(sp.getProductId());
					List<UserConcernedConfig> uccs = userConcernedConfigService
							.getUserConcernedConfig(ucc);
					if (uccs != null) {
						informDo.setUccs(uccs);
					}
					informDo.beforeDo();
					informDo.TaskInformDo(sp, e, busientity);
					informDo.afterDo();
				}
				// informCcInformsTask(e);
				// informActivityConfigTask(e);
				// informUserConcernedConfigTask(e);
			}
			for (ActivitiEntityEvent e : commentEvents) {
				CommentEntity entity = (CommentEntity) e.getEntity();
				if (informDo != null) {
					if (ccInformJsonStr != null) {
						informDo.setCcInformStr(ccInformJsonStr);
					}
					// ActivityConfig con = new ActivityConfig();
					// con.setModuleId(sp.getProductId());
					// con.setTaskDefId(entity.getTaskDefinitionKey());
					// ActivityConfig ac =
					// activityConfigService.getActivityConfigOne(con);
					// if(ac != null){
					// informDo.setAc(ac);
					// }
					UserConcernedConfig ucc = new UserConcernedConfig();
					ucc.setProcessDefId(sp.getProductId());
					List<UserConcernedConfig> uccs = userConcernedConfigService
							.getUserConcernedConfig(ucc);
					if (uccs != null) {
						informDo.setUccs(uccs);
					}
					informDo.beforeDo();
					informDo.CommentInformDo(sp, e, busientity);
					informDo.afterDo();
				}
				// informActivityConfigComment(e);
				// informUserConcernedConfigComment(e);
			}
			informDo.after();
		} catch (Exception e) {
			System.out.println("执行流程通知异常,或项目没有流程通知实现");
			e.printStackTrace();
			listTaskTodo.clear();
			taskEvents.clear();
			commentEvents.clear();
		}
		for (InformTask t : listTaskTodo) {
			t.start();
		}
		listTaskTodo.clear();
		taskEvents.clear();
		commentEvents.clear();
		// 全局实例化后无法销毁，暂时设置为空
		this.setCcInformsInfo("{}");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void informCcInformsTask(ActivitiEntityEvent e) {
		Map ccInformsInfo = JSONObject.parseObject(ccInformJsonStr, Map.class);
		if (ccInformsInfo != null && ccInformsInfo.get(CC_INFORM_TYPES) != null
				&& ccInformsInfo.get(CC_INFORM_USERIDS) != null) {
			JSONArray ccTypes = null;
			JSONArray ccUserIds = null;
			Map<String, String> contentMap = null;
			try {
				ccTypes = (JSONArray) ccInformsInfo.get(CC_INFORM_TYPES);

				ccUserIds = (JSONArray) ccInformsInfo.get(CC_INFORM_USERIDS);
				// TaskEntity entity = (TaskEntity) e.getEntity();

				// String content = entity.getName();
				JSONArray curUsers = (JSONArray) ccInformsInfo.get("curUser");
				String moduleName = ccInformsInfo.get("moduleName").toString();
				String title = ccInformsInfo.get("title").toString();
				contentMap = new HashMap<String, String>();
				// contentMap.put(InformService.WORKSTATGEKEY, content);
				contentMap.put(InformService.COMMENT_USERID_KEY, curUsers
						.get(0).toString());
				contentMap.put(InformService.COMMENT_MODULE_KEY, moduleName);
				contentMap.put(InformService.COMMENT_TITLE_KEY, title);

				// contentMap.put(InformService.CATEGORY_KEY,"嗯 这里是什么意思？");
				contentMap.put(InformService.STR_TEMPLATE_KEY,
						InformService.COMMENT_INFORM_INNER_TEMPLATE);
			} catch (Exception e1) {
				e1.getStackTrace();
			}

			for (int i = 0; i < ccTypes.size(); i++) {
				String type = ccTypes.getString(i);
				for (int j = 0; j < ccUserIds.size(); j++) {
					String userId = ccUserIds.getJSONObject(j).getString("id");
					System.out.println("userId :" + userId);
					switch (type) {
					case InformService.INFORM_TYPE_EMAIL:
						InformTask task = new MEmailTask(userId, null,
								contentMap);
						task.init();
						if (!listTaskTodo.contains(task)) {
							listTaskTodo.add(task);
						}
						break;
					// 短信通知实现
					case InformService.INFORM_TYPE_PHONE_MSG:
						InformTask task1 = new MphoneTask(userId, null,
								contentMap);
						task1.init();
						// if(!listTaskTodo.contains(task1)){
						listTaskTodo.add(task1);
						// }
						// 站内消息实现
						break;
					case InformService.INFORM_TYPE_INNER_MSG:
						InformTask task2 = new MwebInnerTask(userId, null,
								contentMap);
						task2.init();
						// if(!listTaskTodo.contains(task2)){
						listTaskTodo.add(task2);
						// }
						break;
					}
				}
			}
		}

	}

	/* 环节配置任务的通知 */
	private void informActivityConfigTask(ActivitiEntityEvent e)
			throws Exception {

		TaskEntity entity = (TaskEntity) e.getEntity();
		String taskId = entity.getId();
		activityConfigService = ApplicationContextManager.getContext().getBean(
				ActivityConfigService.class);

		String productNo = (String) taskService.getVariable(taskId,
				WorkFlowService.PRODUCTNOKEY);
		ServiceProduct sp = productService.getServiceProductByNo(productNo);
		ActivityConfig con = new ActivityConfig();
		con.setModuleId(sp.getProductId());
		con.setTaskDefId(entity.getTaskDefinitionKey());
		ActivityConfig ac = activityConfigService.getActivityConfigOne(con);
		if (ac != null && ac.getInformType() != null) {
			taskService = ApplicationContextManager.getContext().getBean(
					TaskService.class);
			List<IdentityLink> identity = taskService
					.getIdentityLinksForTask(taskId);
			// taskService.
			String content = entity.getName();
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put(InformService.WORKSTATGEKEY, content);
			contentMap.put(InformService.CATEGORY_KEY, "嗯 这里是什么意思？");
			contentMap.put(InformService.STR_TEMPLATE_KEY,
					InformService.TASK_INFORM_EMAIL_TEMPLATE);
			this.addAcTasks(ac, identity, contentMap);
		}
	}

	@SuppressWarnings("unchecked")
	private void addAcTasks(ActivityConfig ac, List<IdentityLink> identity,
			Map contentMap) {
		if (identity != null && identity.size() > 0) {
			for (IdentityLink i : identity) {
				String type = i.getType();
				switch (type) {
				case "candidate":
					String userId = i.getUserId();
					String groupId = i.getGroupId();
					if (userId != null && groupId == null) {
						if (ac.getInformType() != null) {
							String[] types = ac.getInformType().split(",");
							for (String type1 : types) {
								switch (type1) {
								case InformService.INFORM_TYPE_EMAIL:
									InformTask task = new MEmailTask(userId,
											null, contentMap);
									task.init();
									if (!listTaskTodo.contains(task)) {
										listTaskTodo.add(task);
									}
									break;
								case InformService.INFORM_TYPE_PHONE_MSG:
									// 短信通知实现
								}

							}
						}
					}
				}
			}
		}
	}

	/* 用户关注任务的通知 */
	public List<EmailTask> informUserConcernedConfigTask(ActivitiEntityEvent e)
			throws Exception {
		taskService = ApplicationContextManager.getContext().getBean(
				TaskService.class);
		runtimService = ApplicationContextManager.getContext().getBean(
				RuntimeService.class);
		productService = ApplicationContextManager.getContext().getBean(
				ServiceProductService.class);

		List<EmailTask> emailTasks = new ArrayList<EmailTask>();
		Dao<UserConcernedConfig> daoucc = DaoFactory
				.create(UserConcernedConfig.class);
		List<UserConcernedConfig> uccsdel = new ArrayList<UserConcernedConfig>();// 关注的流程实例结束了
																					// ，id不存在了时
																					// 需要删除配置
		TaskEntity entity = (TaskEntity) e.getEntity();
		String taskId = entity.getId();
		String proInsId = entity.getProcessInstanceId();
		String proDefId = entity.getProcessDefinitionId();
		String taskDefId = entity.getTaskDefinitionKey();

		String productNo = (String) taskService.getVariable(taskId,
				WorkFlowService.PRODUCTNOKEY);
		ServiceProduct sp = productService.getServiceProductByNo(productNo);

		UserConcernedConfig ucc = new UserConcernedConfig();
		ucc.setProcessDefId(sp.getProductId());

		userConcernedConfigService = ApplicationContextManager.getContext()
				.getBean(UserConcernedConfigService.class);
		List<UserConcernedConfig> uccs = userConcernedConfigService
				.getUserConcernedConfig(ucc);

		for (UserConcernedConfig ucc1 : uccs) {
			if (ucc1.getInformType() == null) {
				continue;
			}
			// 表示有人关注了这个工单,且不分环节
			boolean isConcernedWork = ucc1.getProcessInsId() != null
					&& ucc1.getProcessInsId().equals(proInsId)
					&& ucc1.getTaskDefId() == null;
			// 这是有人关注了 流程产品了
			boolean isConcernedSP = ucc1.getProcessInsId() == null
					&& ucc1.getTaskDefId() == null;
			// 有关注产品一个环节
			boolean isConcernedActivity = ucc1.getProcessInsId() == null
					&& ucc1.getTaskDefId() != null
					&& ucc1.getTaskDefId() == taskDefId;
			if (isConcernedWork || isConcernedSP || isConcernedActivity) {
				ProcessInstance p = runtimService.createProcessInstanceQuery()
						.includeProcessVariables().processInstanceId(proInsId)
						.singleResult();
				// 实例不存在了 删除配置
				if (p == null) {
					uccsdel.add(ucc1);
				} else {
					Map variable = p.getProcessVariables();
					String content = entity.getName();
					Map<String, String> contentMap = new HashMap<String, String>();
					contentMap.put(InformService.WORKSTATGEKEY, content);
					contentMap.put(InformService.CATEGORY_KEY, "嗯 这里是什么意思？");
					contentMap.put(InformService.STR_TEMPLATE_KEY,
							TASK_INFORM_EMAIL_TEMPLATE);
					contentMap
							.put(WorkFlowService.WORKTITLEKEY,
									(String) variable
											.get(WorkFlowService.WORKTITLEKEY));
					this.addUccTasks(ucc1, contentMap);
				}
			}
		}
		/*
		 * for(EmailTask t:emailTasks){ t.run(); }
		 */
		daoucc.deleteBatch(uccsdel);
		return emailTasks;
	}

	// 环节配置的评论事件通知，此处只有用户关注了 才会有评论通知，不然不知道通知给谁。所以这里不要处理
	public void informActivityConfigComment(ActivitiEntityEvent e)
			throws Exception {/*
							 * List<EmailTask> emailTasks = new
							 * ArrayList<EmailTask>(); CommentEntity entity =
							 * (CommentEntity) e.getEntity();
							 * entity.getUserId(); String taskId =
							 * entity.getTaskId(); historyService =
							 * ApplicationContextManager
							 * .getContext().getBean(HistoryService.class);
							 * taskService =
							 * ApplicationContextManager.getContext
							 * ().getBean(TaskService.class);
							 * HistoricTaskInstance task =
							 * historyService.createHistoricTaskInstanceQuery
							 * ().taskId(taskId).singleResult();
							 * 
							 * activityConfigService =
							 * ApplicationContextManager.
							 * getContext().getBean(ActivityConfigService
							 * .class); ActivityConfig ac =
							 * activityConfigService
							 * .getActivityConfigById(task.getTaskDefinitionKey
							 * ()); if(ac!=null){ //是否有此事件配置。有时候我只要通知任务事件
							 * 不需要通知评论事件 if(ac.getAction()==null){ return; }
							 * String[] events = ac.getAction().split(",");
							 * for(String event:events){
							 * if(event.equals(InformService
							 * .EVENT_TYPE_COMMENT)){ break; } return; } //
							 * historyService.cre // List<IdentityLink> identity
							 * = taskService.getIdentityLinksForTask(taskId);
							 * String message = entity.getMessage();
							 * Map<String,String> contentMap = new
							 * HashMap<String,String>();
							 * contentMap.put(InformService.WORKSTATGEKEY,
							 * message);
							 * contentMap.put(InformService.CATEGORY_KEY
							 * ,"嗯 这里是什么意思？");
							 * contentMap.put(InformService.STR_TEMPLATE_KEY,
							 * InformService.TASK_INFORM_EMAIL_TEMPLATE);
							 * if(identity!=null&&identity.size()>0){
							 * for(IdentityLink i:identity){ String type =
							 * i.getType(); switch(type){ case "candidate":
							 * String userId = i.getUserId(); String groupId =
							 * i.getGroupId(); if(userId!=null&&groupId==null){
							 * String[] types = ac.getInformType().split(",");
							 * for(String type1:types){ switch(type1){ case
							 * InformService.INFORM_TYPE_EMAIL: EmailTask task1
							 * = new MEmailTask(false,userId,null,contentMap);
							 * task1.init(); emailTasks.add(task1); break; case
							 * InformService.INFORM_TYPE_PHONE_MSG: //短信通知实现 } }
							 * }
							 * 
							 * }
							 * 
							 * 
							 * }
							 * 
							 * } } for(EmailTask etask:emailTasks){
							 * etask.start(); }
							 */
	}

	// 用户关注设置的评论事件通知
	public List<EmailTask> informUserConcernedConfigComment(
			ActivitiEntityEvent e) throws Exception {
		taskService = ApplicationContextManager.getContext().getBean(
				TaskService.class);
		runtimService = ApplicationContextManager.getContext().getBean(
				RuntimeService.class);
		productService = ApplicationContextManager.getContext().getBean(
				ServiceProductService.class);
		historyService = ApplicationContextManager.getContext().getBean(
				HistoryService.class);

		Dao<UserConcernedConfig> daoucc = DaoFactory
				.create(UserConcernedConfig.class);

		List<EmailTask> emailTasks = new ArrayList<EmailTask>();
		List<UserConcernedConfig> uccsdel = new ArrayList<UserConcernedConfig>();// 关注的流程实例结束了
																					// ，id不存在了时
																					// 需要删除配置
		CommentEntity entity = (CommentEntity) e.getEntity();
		String taskId = entity.getTaskId();
		HistoricTaskInstance task = historyService
				.createHistoricTaskInstanceQuery().includeProcessVariables()
				.taskId(taskId).singleResult();
		String stage = task.getName();
		String taskDef = task.getTaskDefinitionKey();
		// String message = entity.getMessage();
		String proInsId = entity.getProcessInstanceId();

		String productNo = (String) task.getProcessVariables().get(
				WorkFlowService.PRODUCTNOKEY);
		ServiceProduct sp = productService.getServiceProductByNo(productNo);

		UserConcernedConfig ucc = new UserConcernedConfig();
		ucc.setProcessDefId(sp.getProductId());

		userConcernedConfigService = ApplicationContextManager.getContext()
				.getBean(UserConcernedConfigService.class);
		List<UserConcernedConfig> uccs = userConcernedConfigService
				.getUserConcernedConfig(ucc);
		for (UserConcernedConfig ucc1 : uccs) {
			if (ucc1.getInformType() == null) {
				continue;
			}
			// 表示有人关注了这个工单,且不分环节
			boolean isConcernedWork = ucc1.getProcessInsId() != null
					&& ucc1.getProcessInsId().equals(proInsId)
					&& ucc1.getTaskDefId() == null;
			// 这是有人关注了 流程产品了
			boolean isConcernedSP = ucc1.getProcessInsId() == null
					&& ucc1.getTaskDefId() == null;
			// 有关注产品一个环节
			boolean isConcernedActivity = ucc1.getProcessInsId() == null
					&& ucc1.getTaskDefId().equals(taskDef);
			if (isConcernedWork || isConcernedSP || isConcernedActivity) {
				Map<String, String> contentMap = new HashMap<String, String>();
				// 此处还没调试，工单结束环节。此时应该没有实例了
				if (proInsId != null) {
					WorkFlowService wfs = ApplicationContextManager
							.getContext().getBean(WorkFlowService.class);
					String workTitle = (String) wfs.getHistoricVariableByIdKey(
							proInsId, WorkFlowService.WORKTITLEKEY);
					contentMap.put(WorkFlowService.WORKTITLEKEY, workTitle);
					ProcessInstance p = runtimService
							.createProcessInstanceQuery()
							.processInstanceId(proInsId).singleResult();
					// 实例不存在了 并且不是环节的配置 则删除配置
					if (p == null && ucc1.getRemark1() == null) {
						uccsdel.add(ucc1);
					}
				}
				String comment = entity.getMessage();
				contentMap.put(InformService.COMMENT_KEY, comment);
				contentMap.put(WORKSTATGEKEY, stage);
				contentMap.put(CATEGORY_KEY, "嗯 这里是什么意思？");
				contentMap.put(STR_TEMPLATE_KEY, COMMENT_INFORM_EMAIL_TEMPLATE);
				contentMap.put(COMMENT_USERID_KEY, entity.getUserId());
				addUccTasks(ucc1, contentMap);
			}
		}
		/*
		 * for(EmailTask t:emailTasks){ t.run(); }
		 */
		daoucc.deleteBatch(uccsdel);
		return emailTasks;
	}

	@Autowired
	UserJobService userJobService;

	/**
	 * 
	 * @param ucc1
	 * @param content
	 */
	private void addUccTasks(UserConcernedConfig ucc1, Map content) {
		List<InformTask> emailTasks = new ArrayList<InformTask>();
		switch (ucc1.getCatogory()) {
		case "user":
			String[] userIds = ucc1.getObserverId().split(",");
			for (String userId : userIds) {
				if (userId != null) {
					String[] types = ucc1.getInformType().split(",");
					for (String type1 : types) {
						switch (type1) {
						case InformService.INFORM_TYPE_EMAIL:
							InformTask task1 = new MEmailTask(userId, null,
									content);
							task1.init();
							if (!listTaskTodo.contains(task1)) {
								listTaskTodo.add(task1);
								emailTasks.add(task1);
							}
							break;
						// 短信通知实现
						case InformService.INFORM_TYPE_PHONE_MSG:

						}

					}
				}
			}
			break;
		case "group":
			String[] groupIds = ucc1.getObserverId().split(",");
			for (String groupId : groupIds) {
				if (groupId != null) {
					if (ucc1.getInformType() != null) {
						String[] types = ucc1.getInformType().split(",");
						for (String type1 : types) {
							switch (type1) {
							case InformService.INFORM_TYPE_EMAIL:
								List<UserDTO> us = userJobService
										.getAllUserJob(groupId);
								for (UserDTO user : us) {
									MEmailTask task = new MEmailTask(user,
											null, content);
									task.init();
									if (!listTaskTodo.contains(task)) {
										listTaskTodo.add(task);
									}
								}
								break;
							case InformService.INFORM_TYPE_PHONE_MSG:
								// 短信通知实现
							}

						}
					}
				}
			}
			break;
		}
	}

	public synchronized void informSb(String email, String content) {
		System.out.println("");
	}

	public synchronized void addTaskEvents(ActivitiEntityEvent e) {
		taskEvents.add(e);
	}

	public synchronized void delTaskEvents(ActivitiEntityEvent e) {
		taskEvents.remove(e);
	}

	public synchronized void addCommentEvents(ActivitiEntityEvent e) {
		commentEvents.add(e);
	}

	public synchronized void delCommentEvents(ActivitiEntityEvent e) {
		commentEvents.remove(e);
	}

	public String getCcInformsInfo() {
		return ccInformJsonStr;
	}

	public synchronized void setCcInformsInfo(String ccInformJsonStr) {
		this.ccInformJsonStr = ccInformJsonStr;
	}

	public synchronized void clearEvents() {
		taskEvents.clear();
		commentEvents.clear();
	}
}
