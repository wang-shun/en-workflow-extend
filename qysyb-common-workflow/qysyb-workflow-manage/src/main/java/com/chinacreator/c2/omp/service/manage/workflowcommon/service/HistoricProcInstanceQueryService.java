package com.chinacreator.c2.omp.service.manage.workflowcommon.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.TIMESTAMP;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Form;
import com.chinacreator.c2.omp.service.manage.workflowcommon.FormField;
import com.chinacreator.c2.omp.service.manage.workflowcommon.cmd.CountHistoricProcessInstanceCmd;
import com.chinacreator.c2.omp.service.manage.workflowcommon.cmd.SelectHistoricProcessInstanceCmd;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.RetrieveItem;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.impl.MHistoricProcessInstanceQueryImpl;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.service.RetrieveItemService;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年3月24日 下午3:16:45 
 * 类说明 
 */
@Service
public class HistoricProcInstanceQueryService {
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
			TIMESTAMP time = (TIMESTAMP) map.get("START_TIME_");
			try {
				java.sql.Timestamp tt = (java.sql.Timestamp) time.toJdbc();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
				map.put("START_TIME_",sdf.format(new Date(tt.getTime())));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		//可以让其查出所有
		if(offset<0||limit<0){
//			historicInstanceQuery.setPage(0, 1000);
		}else{
			historicInstanceQuery.setPage(offset, limit);
		}
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
