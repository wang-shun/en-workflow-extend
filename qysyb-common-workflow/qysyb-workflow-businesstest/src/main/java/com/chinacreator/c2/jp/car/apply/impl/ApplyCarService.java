package com.chinacreator.c2.jp.car.apply.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.chinacreator.c2.calendar.entity.CalendarEvent;
//import com.chinacreator.c2.calendar.impl.CalendarEventService;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.jp.car.apply.entity.Carapply;
import com.chinacreator.c2.jp.car.manage.entity.Car;
import com.chinacreator.c2.qyb.workflow.activiti.impl.FormOperate;
import com.chinacreator.c2.qyb.workflow.audit.impl.ArchhandleServiceImpl;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年6月27日 下午3:04:11 
 * 类说明 
 */
public class ApplyCarService extends FormOperate {
	@Autowired
	ArchhandleServiceImpl archhandleServiceImpl;
	final static String CALENDAR_EVENT = "calendarEvent"; 
	//审核意见
	final static String AUDIT_OPINION_KEY = "auditOpinion"; 
	final static String OFFICE_PRINCIPAL_AUDIT_KEY = "officePrincipalComment";
	final static String PRINCIPAL_AUDIT_OPINION_KEY= "principalAudit";
//	@Autowired
//	CalendarEventService calendarEventService;
	@Override
	public int addOrUpdateEntity(String json, String businessKey,
			String proInsId, String moduleId, WorkFlowActivity curActivity,
			String curUserId, WorkFlowActivity nextActivity, Map map)
			throws Exception {
		JSONObject jsonObject = JSON.parseObject(json);
		//存储日历事件
//		if(jsonObject.getObject(CALENDAR_EVENT, CalendarEvent.class)!=null){
//			calendarEventService.saveResourceEventByBusinesskey(jsonObject.getObject(CALENDAR_EVENT, CalendarEvent.class));
//		}
		//审核意见存储
		archhandleServiceImpl.saveArchhandles(jsonObject, 
				new String[]{AUDIT_OPINION_KEY,OFFICE_PRINCIPAL_AUDIT_KEY,PRINCIPAL_AUDIT_OPINION_KEY}, 
				proInsId, curActivity==null?null:curActivity.getId());	
		Carapply carapply = parseObjectFromJson(Carapply.class,json);
		//会议发布
/*		if(nextActivity!=null&&MEETING_END_ACTIVITY_ID.equals(nextActivity.getId())
				&&MEETING_RELEASE_ACTIVITY_ID.equals(curActivity.getId())){
			meeting.setStatus("YFB");
			meeting.setPublishStatus(1);
		}*/
		return saveEntity(Carapply.class, carapply, businessKey, proInsId);
	}

	@Override
	public int addOrUpdateEntityAfterTaskExcu(String json, String businessKey,
			String proInsId, String moduleId, WorkFlowActivity lastActivity,
			String curUserId, WorkFlowActivity nextActivity, Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> getEntityByBusinessKey(String businessKey,
			String proInsId, String moduleId, WorkFlowActivity curActivity,
			String curUserId, Map map) {
		Map<String,Object> result = new HashMap<String,Object>();
		Map eMap = this.getEntity(Carapply.class, businessKey, proInsId);
		if(eMap != null){
			result.putAll(eMap);
		}
		
		Map<String, Object> aMap = archhandleServiceImpl
				.getArchhandle(new String[]{AUDIT_OPINION_KEY,OFFICE_PRINCIPAL_AUDIT_KEY,PRINCIPAL_AUDIT_OPINION_KEY}, 
				businessKey, proInsId, curActivity==null?null:curActivity.getId());	
		result.putAll(aMap);
		return result;
	}

	@Override
	public List<String> getAssigneeList(String businessJson,
			String businessKey, String proInsId, String moduleId,
			WorkFlowActivity curActivity, WorkFlowActivity nextActivity,
			WorkFlowTransition workFlowTransition, String curUserId, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getTaskHandler(String businessJson,
			String businessKey, String proInsId, String moduleId,
			WorkFlowActivity lastActivity, WorkFlowActivity curActivity,
			String curTaskId, String curUserId, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> setProsVariableBeforeTaskExcu(
			String businessJson, String businessKey, String proInsId,
			String moduleId, Map variables, WorkFlowActivity curActivity,
			WorkFlowActivity nextActivity,
			WorkFlowTransition workFlowTransition, String curUserId, Map map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void deleteCar(List<String> ids){
		Dao<Car> dao = DaoFactory.create(Car.class);
		for(String id : ids){
			Car condition =  dao.selectByID(id);
			dao.delete(condition);
		}
	}

	@Override
	public void onTaskReject(String json, String businessKey, String proInsId,
			String moduleId, WorkFlowActivity curActivity, String curUserId,
			WorkFlowActivity nextActivity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProcessDelete(String json, String businessKey,
			String proInsId, String moduleId, WorkFlowActivity curActivity,
			String curUserId, String reason, Map map) {
		// TODO Auto-generated method stub
		
	}

}
