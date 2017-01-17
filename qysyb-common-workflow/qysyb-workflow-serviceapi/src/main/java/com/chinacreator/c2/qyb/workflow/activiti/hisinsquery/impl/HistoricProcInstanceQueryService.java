package com.chinacreator.c2.qyb.workflow.activiti.hisinsquery.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.cmd.CountHistoricProcessInstanceCmd;
import com.chinacreator.c2.qyb.workflow.activiti.cmd.SelectHistoricProcessInstanceCmd;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.activiti.query.impl.MHistoricProcessInstanceQueryImpl;
import com.chinacreator.c2.qyb.workflow.customretrieval.entity.RetrieveItem;
import com.chinacreator.c2.qyb.workflow.customretrieval.impl.RetrieveItemService;
import com.chinacreator.c2.qyb.workflow.form.entity.Form;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.form.impl.FormFieldService;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;

import oracle.sql.TIMESTAMP;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年3月24日 下午3:16:45 
 * 类说明 
 */
@Service
public class HistoricProcInstanceQueryService {
	static final String EXCLUDE_TODO = "excludeTodo";
	@Autowired
	FormService fs;
	@Autowired
	FormFieldService ffs;
	@Autowired
	ManagementService manageService;
	@Autowired
	HistoryService historyService;
	/**
	 * 分页查询流程实例 
	 * @param retrieveKey
	 * @param con
	 * @param starttime
	 * @param endtime
	 * @param offset
	 * @param limit
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getHistoricInstanceWithExternalStorage(String retrieveKey,Map con,Timestamp starttime,Timestamp endtime,int offset,int limit){
		List<Map> list = manageService.executeCommand(new SelectHistoricProcessInstanceCmd(this.generateHistoricInstanceQuery(retrieveKey, con, starttime, endtime, offset, limit)));
		for(Map map:list){
			//TODO dao层去处理
			Object o = map.get("START_TIME_");
			if(o instanceof Timestamp){
				Timestamp time = (Timestamp) map.get("START_TIME_");
				map.put("START_TIME_",time.toString());				
			}else if(o instanceof TIMESTAMP){
				TIMESTAMP time = (TIMESTAMP) map.get("START_TIME_");
				map.put("START_TIME_",time.toString());	
			}

//			String procDefId = (String) map.get("PROC_DEF_ID_");
			String procInstId = (String) map.get("PROC_INST_ID_");
			HistoricProcessInstance hisProcessIns = historyService.createHistoricProcessInstanceQuery().includeProcessVariables().processInstanceId(procInstId).singleResult();
			Map variables = hisProcessIns.getProcessVariables();
			/*流程变量返回前端*/
			map.putAll(variables);
		}
		return list;
	}
	public int countHistoricInstanceWithExternalStorage(String retrieveKey,Map con,Timestamp starttime,Timestamp endtime){
		int total = manageService.executeCommand(new CountHistoricProcessInstanceCmd(this.generateHistoricInstanceQuery(retrieveKey, con, starttime, endtime, -1, -1)));
		return total;
	}
	/**
	 * 生成查询对象
	 * @param retrieveKey
	 * @param con
	 * @param starttime
	 * @param endtime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public MHistoricProcessInstanceQueryImpl generateHistoricInstanceQuery(String retrieveKey,Map con,Timestamp starttime,Timestamp endtime,int offset,int limit){
		if(starttime==null||endtime==null){
			endtime = new Timestamp(System.currentTimeMillis());
			starttime = new Timestamp(System.currentTimeMillis()-2592000000l); //最近一个月
		}
		MHistoricProcessInstanceQueryImpl historicInstanceQuery = new MHistoricProcessInstanceQueryImpl();
		/*Form 外部的数据库表*/
		Form form = null;
		if(con!=null&&con.get(WorkFlowService.FORMIDSTR)!=null){
			String formId = (String) con.get(WorkFlowService.FORMIDSTR);
			form = fs.getFormById(formId);
			historicInstanceQuery.addForm(form);
		}
 		/*服务产品过滤*/	
 		String productNo = null;
 		if(con!=null&&con.get(WorkFlowService.PRODUCTNO)!=null){
 			productNo = (String) con.get(WorkFlowService.PRODUCTNO);
 			historicInstanceQuery.variableValueEquals(WorkFlowService.PRODUCTNO,productNo);
 		}
 		/*服务类型过滤*/
 		String serviceType = null;
 		if(con!=null&&con.get(WorkFlowService.SERVICETYPEKEY)!=null){
 			serviceType = (String) con.get(WorkFlowService.SERVICETYPEKEY);
 			historicInstanceQuery.variableValueEquals(WorkFlowService.SERVICETYPEKEY, serviceType);
 		}
		Boolean needFinished = null;
		if(con!=null&&con.get(WorkFlowService.NEEDFINISHED)!=null){
			needFinished = (Boolean) con.get(WorkFlowService.NEEDFINISHED);
		}
		Boolean needUnFinished = null;
		if(con!=null&&con.get(WorkFlowService.NEEDUNFINISHED)!=null){
			needUnFinished = (Boolean) con.get(WorkFlowService.NEEDUNFINISHED);
		}
		boolean needInvolved = false;
		if(con!=null&&con.get(WorkFlowService.NEEDINVOLVEDKEY)!=null){
			needInvolved = (boolean) con.get(WorkFlowService.NEEDINVOLVEDKEY);
		}
		String userId = null;
		if(con!=null&&con.get(WorkFlowService.USERIDKEY)!=null){
			userId = (String) con.get(WorkFlowService.USERIDKEY);
		}
		if(con!=null&&con.get("sortable")!=null){
			Sortable sortable = (Sortable) con.get("sortable");
			historicInstanceQuery.setSortable(sortable);
		}		

		if(needFinished!=null&&needFinished==true){
			historicInstanceQuery.finished();
		}
		if(needUnFinished!=null&&needUnFinished==true){
			historicInstanceQuery.unfinished();
		}
		if(needInvolved==true&&userId!=null){
			historicInstanceQuery.involvedUser(userId);
		}
 		//是否排除待办记录
 		boolean excludeTodo = false;
 		if(con!=null&&con.get(EXCLUDE_TODO)!=null&&userId!=null){
 			excludeTodo = (boolean) con.get(EXCLUDE_TODO);
 			if(excludeTodo){
 				historicInstanceQuery.setExcludeTodo(true);
 				historicInstanceQuery.involvedUser(userId);
 			}
 		}		
 		
		//可以让其查出所有
		if(offset<0||limit<0){
//			historicInstanceQuery.setPage(0, 1000);
		}else{
			historicInstanceQuery.setPage(offset, limit);
		}
 		//默认未完成工单在前
 		historicInstanceQuery.orderByProcessInstanceEndTime().desc();
		//默认开始时间排序
		historicInstanceQuery.orderByProcessInstanceStartTime().desc();
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
								historicInstanceQuery.externalTableQueryVariableValueLike(field, "%"+o+"%");
							}else{
								historicInstanceQuery.externalTableQueryVariableValueEqual(field, o);
							}	
						}
						//TODO
						if(field.getFieldType()!=null&&field.getFieldType().equals("TIME")){
							JSONObject json = (JSONObject)o;
							long startl = json.getLongValue("startTime");
							long endl = json.getLongValue("endTime");
							historicInstanceQuery.externalTableQueryVariableValueGreaterThanOrEqual(field, startl);	
							historicInstanceQuery.externalTableQueryVariableValueLessThanOrEqual(field, endl);
						}						
					}else{
						if (field.getFieldType()==null||field.getFieldType().equals("STR")) {
							if(item.isIsLike()){//模糊查询
								historicInstanceQuery.variableValueLike(field.getFieldNo(), "%"+o+"%");
							}else{
								historicInstanceQuery.variableValueEquals(field.getFieldNo(), (String) o);
							}	
						}
						if(field.getFieldType()!=null&&field.getFieldType().equals("TIME")){
							JSONObject json = (JSONObject)o;
							long startl = json.getLongValue("startTime");
							long endl = json.getLongValue("endTime");
							historicInstanceQuery.variableValueGreaterThan(field.getFieldNo(), startl).variableValueLessThan(field.getFieldNo(), endl);	
						}						
					}

				}
			}			
		}
		return historicInstanceQuery;
	}
}
