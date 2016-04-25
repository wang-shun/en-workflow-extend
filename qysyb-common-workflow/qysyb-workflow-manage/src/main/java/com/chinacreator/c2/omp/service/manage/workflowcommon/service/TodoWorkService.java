package com.chinacreator.c2.omp.service.manage.workflowcommon.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.serviceproductmanage.ServiceProduct;
import com.chinacreator.c2.omp.service.manage.serviceproductmanage.service.ServiceProductService;
import com.chinacreator.c2.omp.service.manage.slamanage.ServiceAgreement;
import com.chinacreator.c2.omp.service.manage.slamanage.service.ServiceAgreementService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Form;
import com.chinacreator.c2.omp.service.manage.workflowcommon.FormField;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.service.UserConcernedConfigService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.RetrieveItem;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.impl.MTaskQueryImpl;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.service.RetrieveItemService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.service.TaskRetrievalService;

@Service
public class TodoWorkService {

	@Autowired
	RuntimeService runtimeService;
	@Autowired
	ServiceProductService sps;
	@Autowired
	ServiceAgreementService sams;
	@Autowired
	FormService fs;
	@Autowired
	FormFieldService ffs;
	@SuppressWarnings("unchecked")
	public List<Map> getTodoWorkWithExternalStorage(String retrieveKey,Map con,String userId,int offset,int limit){
		List<Map> result = new ArrayList<>();

		
		TaskRetrievalService taskRetrievalService = ApplicationContextManager.getContext().getBean(TaskRetrievalService.class);
		List<Map> list = taskRetrievalService.getSortableTasksEXT(this.generateMTaskQueryImpl(retrieveKey, con, userId, offset, limit),(Sortable)con.get("sortable"));	
		
		ProcessInstanceQuery  piq = runtimeService.createProcessInstanceQuery();
		AccessControlService acc=new AccessControlServiceImpl();
		String curUserId = acc.getUserID();
		for(Map map:list){
//			[{SUSPENSION_STATE_=1, NAME_=承办部门负责人审核, REV_=1, TASK_DEF_KEY_=sid-3462D6F5-333D-4A5E-9B98-02CD1D0E35D3, 
//			PROC_DEF_ID_=documentdispatch:4:FE2F24BE375F4B788AA70629CA0A4353, RNUM=1, ID_=D8DED88F9EAC4D37BB847E1C4A5DC1DF, 
//			CREATE_TIME_=oracle.sql.TIMESTAMP@7992ea84, PRIORITY_=50, EXECUTION_ID_=01DB2197118245589E48F1A3BA2BAE00, PROC_INST_ID_=01DB2197118245589E48F1A3BA2BAE00}]
			String taskName = (String) map.get("NAME_"); 
			String taskDefKey = (String) map.get("TASK_DEF_KEY_"); 
			String procDefId = (String) map.get("PROC_DEF_ID_");
			String procInstId = (String) map.get("PROC_INST_ID_");
			String executionId = (String) map.get("EXECUTION_ID_");
			String taskId = (String) map.get("ID_");
			ProcessInstance pi = piq.processInstanceId(procInstId).includeProcessVariables().singleResult();
			Map mapvariable = pi.getProcessVariables();				
			String businessKey = pi.getBusinessKey();
//			Map<String,Object> map = new HashMap<String,Object>();
			//流程变量 前端是需要的
			map.putAll(mapvariable);
			
			ServiceProduct sp = new ServiceProduct();
			if(mapvariable.get(WorkFlowService.PRODUCTNO)!=null){
				String productNo1 = (String) mapvariable.get(WorkFlowService.PRODUCTNO);
				ServiceProductService sps = ApplicationContextManager.getContext().getBean(ServiceProductService.class);			
				sp = sps.getServiceProductByNo(productNo1);	
			}
				
			//得控制服务产品还存在未结束流程时 不允许删除服务产品
			if(sp!=null){
				map.put("serviceTypeId", sp.getServiceTypeId());
				map.put("businessKey", businessKey);
				map.put("proinsid", procInstId);
				map.put("prodefid", procDefId);
				map.put("workStage", taskName);
				map.put("taskId", taskId);
				map.put("workId", businessKey);
				map.put("taskdefid", taskDefKey);
				map.put(WorkFlowService.PRODUCTNAMEKEY,sp.getProductName());
				
//				Map mapvariable = runtimeService.getVariables(t.getExecutionId());//获取流程变量
				List<ServiceAgreement> listsla = sps.getSLABySP(sp);
				ServiceAgreement sla = sams.chooseAServiceAgreement(listsla);
				if(sla!=null){
					int acceptLimit = sams.getKpiValueBySla("FWXYSJ", sla);//分钟
					long acceptLimitl = 60000*acceptLimit;
					int handleLimit = sams.getKpiValueBySla("FWJJSJ", sla);
					long handleLimitl = 60000*handleLimit;
//					int interruptLimit = slas.getKpiValueBySla("FWZDSJ", sla);
//					long interruptLimitl = 60000*interruptLimit;
					long happenedTimel;
					long acceptTimel;
					if(mapvariable.get(WorkFlowService.HAPPENEDTIMEL)!=null){
						Object o = mapvariable.get(WorkFlowService.HAPPENEDTIMEL);
						if(o instanceof String){
							happenedTimel = Long.valueOf((String) o);
						}else if(o instanceof Long){
							happenedTimel = (long) o;
						}else{
							happenedTimel = -1;
						}
					}else{
						happenedTimel = -1;
					}
					if(mapvariable.get(WorkFlowService.ACCEPTTIMEL)!=null){
						Object o = mapvariable.get(WorkFlowService.ACCEPTTIMEL);
						if(o instanceof String){
							acceptTimel = Long.valueOf((String)o);
						}else if(o instanceof Long){
							acceptTimel = (long) o;
						}else{
							acceptTimel = -1;
						}								
					}else{
						acceptTimel = -1;
					}
					if(acceptTimel == -1&&happenedTimel!=-1&&System.currentTimeMillis()<happenedTimel+acceptLimitl){
						map.put("isRecieveTimeout", false);
					}else if(acceptTimel == -1&&happenedTimel!=-1&&System.currentTimeMillis()>happenedTimel+acceptLimitl){
						map.put("isRecieveTimeout", true);
					}else if(acceptTimel != -1&&new Timestamp(acceptTimel).after(new Date(happenedTimel+acceptLimitl))){
						map.put("isRecieveTimeout", true);
					}else if(acceptTimel != -1&&new Timestamp(acceptTimel).before(new Date(happenedTimel+acceptLimitl))){
						map.put("isRecieveTimeout", false);
					}else{
						map.put("isRecieveTimeout", null);
					}
					if(acceptTimel != -1&&System.currentTimeMillis()<acceptTimel+handleLimitl){
						map.put("isHandleTimeout", false);
					}else if(acceptTimel != -1&&System.currentTimeMillis()>acceptTimel+handleLimitl){
						map.put("isHandleTimeout", true);
					}else if(acceptTimel == -1&&happenedTimel != -1&&System.currentTimeMillis()>acceptLimitl+handleLimitl+happenedTimel){
						map.put("isHandleTimeout", true);
					}else if(acceptTimel == -1&&happenedTimel != -1&&System.currentTimeMillis()<acceptLimitl+handleLimitl+happenedTimel){
						map.put("isHandleTimeout", false);
					}else{
						map.put("isHandleTimeout", null);
					}
					if(happenedTimel != -1){
						long leftl = acceptLimitl+handleLimitl+happenedTimel-System.currentTimeMillis();
						int leftm = (int) (leftl/60000);
						map.put(WorkFlowService.SLA_REMAIN_TIME, leftm);
					}else{
						map.put(WorkFlowService.SLA_REMAIN_TIME, null);
					}
				}else{
					map.put("isRecieveTimeout", null);
					map.put("isHandleTimeout", null);
					map.put(WorkFlowService.SLA_REMAIN_TIME, null);
				}	
				//把工单被关注的状态加上去
				UserConcernedConfigService uccService = ApplicationContextManager.getContext().getBean(UserConcernedConfigService.class);
				map.putAll(uccService.getConcernStatusInfo(curUserId, procInstId, sp.getProductId()));		
			}else{
//				map.put("serviceTypeId", sp.getServiceTypeId());
				map.put("businessKey", businessKey);
				map.put("proinsid", procInstId);
				map.put("prodefid", pi.getProcessDefinitionId());
				map.put("workStage", taskName);
				map.put("taskId", taskId);
				map.put("workId", businessKey);
				map.put("isRecieveTimeout", null);
				map.put("isHandleTimeout", null);
				map.put(WorkFlowService.SLA_REMAIN_TIME, null);
			}
			
			result.add(map);
					
		}
		return result;
	}
	
	public int countTodoWorkWithExternalStorage(String retrieveKey,Map con,String userId){
		TaskRetrievalService taskRetrievalService = ApplicationContextManager.getContext().getBean(TaskRetrievalService.class);
		return taskRetrievalService.countSortableTasksEXT(this.generateMTaskQueryImpl(retrieveKey, con, userId, -1, -1), null);
	}
	
	/**
	 * 生成查询对象
	 * @param retrieveKey
	 * @param con
	 * @param userId
	 * @param offset
	 * @param limit
	 * @return
	 */
	public MTaskQueryImpl generateMTaskQueryImpl(String retrieveKey,Map con,String userId,int offset,int limit){
		MTaskQueryImpl mTaskQuery = new MTaskQueryImpl();
		
		/*Form 外部的数据库表*/
		Form form = null;
		if(con!=null&&con.get(WorkFlowService.FORMIDSTR)!=null){
			String formId = (String) con.get(WorkFlowService.FORMIDSTR);
			form = fs.getFormById(formId);
			mTaskQuery.addForm(form);
		}
		
		/*部门过滤，统计那里应该用到了*/
		String branchId = null;
		if(con!=null&&con.get(WorkFlowService.APPLYBRANCHKEY)!=null){
			branchId = (String) con.get(WorkFlowService.APPLYBRANCHKEY);
			mTaskQuery = (MTaskQueryImpl) mTaskQuery.processVariableValueEquals(WorkFlowService.APPLYBRANCHKEY,branchId);
		}	
		
		/*服务产品过滤*/
		String productNo = null;
		if(con!=null&&con.get(WorkFlowService.PRODUCTNO)!=null){
			productNo = (String) con.get(WorkFlowService.PRODUCTNO);
			mTaskQuery = (MTaskQueryImpl) mTaskQuery.processVariableValueEquals(WorkFlowService.PRODUCTNO,productNo);
		}
		/*是否是涉及到的工单 服务跟踪*/
		boolean needInvolved = false;
		if(con!=null&&con.get(WorkFlowService.NEEDINVOLVEDKEY)!=null){
			needInvolved = (boolean) con.get(WorkFlowService.NEEDINVOLVEDKEY);
		}		
		if(userId!=null&&needInvolved==true){
			mTaskQuery = (MTaskQueryImpl) mTaskQuery.taskInvolvedUser(userId);
		}else if(userId!=null&&needInvolved==false){
			mTaskQuery = (MTaskQueryImpl) mTaskQuery.taskCandidateOrAssigned(userId);
		}
		
		/*服务类型过滤*/
		String serviceType = null;
		if(con!=null&&con.get(WorkFlowService.SERVICETYPEKEY)!=null){
			serviceType = (String) con.get(WorkFlowService.SERVICETYPEKEY);
			mTaskQuery = (MTaskQueryImpl) mTaskQuery.processVariableValueEquals(WorkFlowService.SERVICETYPEKEY, serviceType);
		}
		
		RetrieveItemService retrieveItemService = ApplicationContextManager.getContext().getBean(RetrieveItemService.class);
		List<RetrieveItem> itemlist = retrieveItemService.getRetrieveItemsByRetrieveKey(retrieveKey);
		/*检索器项过滤*/
		if(con!=null){
			for(RetrieveItem item:itemlist){
				FormField field = item.getFieldId();
				Object o = con.get(field.getFieldNo());
				if(o!=null){
					if(ffs.isFieldStorageEXT(field)){
						if (field.getFieldType()==null||field.getFieldType().equals("STR")) {
							if(item.isIsLike()){//模糊查询
								mTaskQuery.externalTableQueryVariableValueLike(field, "%"+o+"%");
							}else{
								mTaskQuery.externalTableQueryVariableValueEqual(field, o);
							}	
						}
						//todo
						if(field.getFieldType()!=null&&field.getFieldType().equals("TIME")){
							JSONObject json = (JSONObject)o;
							long startl = json.getLongValue("startTime");
							long endl = json.getLongValue("endTime");
							mTaskQuery = (MTaskQueryImpl) mTaskQuery.processVariableValueGreaterThan(field.getFieldNo(), startl).processVariableValueLessThan(field.getFieldNo(), endl);	
						}						
					}else{
						if (field.getFieldType()==null||field.getFieldType().equals("STR")) {
							if(item.isIsLike()){//模糊查询
								mTaskQuery.processVariableValueEquals(field.getFieldNo(), "%"+o+"%");
							}else{
								mTaskQuery.processVariableValueLike(field.getFieldNo(), (String) o);
							}	
						}
						if(field.getFieldType()!=null&&field.getFieldType().equals("TIME")){
							JSONObject json = (JSONObject)o;
							long startl = json.getLongValue("startTime");
							long endl = json.getLongValue("endTime");
							mTaskQuery = (MTaskQueryImpl) mTaskQuery.processVariableValueGreaterThan(field.getFieldNo(), startl).processVariableValueLessThan(field.getFieldNo(), endl);	
						}						
					}

				}
			}			
		}		
		mTaskQuery.orderByTaskCreateTime().desc();
		if(offset>=0&&limit>0){
			mTaskQuery = mTaskQuery.setPage(offset, limit);	
		}
		
		return mTaskQuery;
	}
}
