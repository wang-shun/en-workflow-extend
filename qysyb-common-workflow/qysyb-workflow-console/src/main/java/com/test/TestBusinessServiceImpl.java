package com.test;

import java.util.List;
import java.util.Map;

import com.chinacreator.c2.qyb.workflow.activiti.impl.FormOperate;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;

public class TestBusinessServiceImpl extends FormOperate {

	@Override
	public int addOrUpdateEntity(String json, String businessKey, String proInsId, String moduleId,
			WorkFlowActivity curActivity, String curUserId, WorkFlowActivity nextActivity, Map map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addOrUpdateEntityAfterTaskExcu(String json, String businessKey, String proInsId, String moduleId,
			WorkFlowActivity lastActivity, String curUserId, WorkFlowActivity nextActivity, Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> getEntityByBusinessKey(String businessKey, String proInsId, String moduleId,
			WorkFlowActivity curActivity, String curUserId, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAssigneeList(String businessJson, String businessKey, String proInsId, String moduleId,
			WorkFlowActivity curActivity, WorkFlowActivity nextActivity, WorkFlowTransition workFlowTransition,
			String curUserId, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getTaskHandler(String businessJson, String businessKey, String proInsId, String moduleId,
			WorkFlowActivity lastActivity, WorkFlowActivity curActivity, String curTaskId, String curUserId, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> setProsVariableBeforeTaskExcu(String businessJson, String businessKey, String proInsId,
			String moduleId, Map variables, WorkFlowActivity curActivity, WorkFlowActivity nextActivity,
			WorkFlowTransition workFlowTransition, String curUserId, Map map) {
		// TODO Auto-generated method stub
		return null;
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
