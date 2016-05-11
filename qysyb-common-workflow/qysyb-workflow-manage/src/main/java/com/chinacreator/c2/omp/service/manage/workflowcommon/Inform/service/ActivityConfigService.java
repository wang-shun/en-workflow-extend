package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.ActivityConfig;
@Service
public class ActivityConfigService {
	
	/**
	 * 
	 * @param ac
	 * @return
	 */
	public List<ActivityConfig> getActivityConfig(ActivityConfig ac){
		
		Dao<ActivityConfig> dao = DaoFactory.create(ActivityConfig.class);
		return dao.select(ac);
	}
	
	public ActivityConfig getActivityConfigById(String id){
		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
		ActivityConfig ac = new ActivityConfig();
		ac.setId(id);
		return daoac.selectOne(ac);
	}
	/**
	 * 按条件获取一个节点配置
	 * @param o
	 * @return
	 */
	public ActivityConfig getActivityConfigOne(ActivityConfig con){
		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
		ActivityConfig ac = daoac.selectOne(con);
		if(ac==null){
			return new ActivityConfig();
		}else{
			return ac;
		}
	}
	/**
	 * update activity config
	 * @param activityConfig
	 * @return
	 */
	public int saveActivityConfig(ActivityConfig activityConfig){
		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
		ActivityConfig con = new ActivityConfig();
		con.setModuleId(activityConfig.getModuleId());
		con.setTaskDefId(activityConfig.getTaskDefId());
		ActivityConfig ac = daoac.selectOne(con);
		if(ac==null){
			return daoac.insert(activityConfig);
		}else{
			activityConfig.setId(ac.getId());
			return daoac.update(activityConfig);
		}
	}
	/**
	 * 获取环节的操作
	 * @param activityId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getActivityActions(String moduleId,String activityId){
		Map action = new HashMap<String,Object>();
		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
		ActivityConfig con = new ActivityConfig();
		con.setModuleId(moduleId);
		con.setTaskDefId(activityId);		
		ActivityConfig ac = daoac.selectOne(con);
		if(ac!=null&&ac.getIncludeActions()!=null&&ac.getIncludeActions()!=""){
			String actionStr = ac.getIncludeActions();
			String[] actions = actionStr.split(",");
			for(String s:actions){
				switch(s){
				case "return":
					Map actionPorperty = new HashMap<String,String>();
					actionPorperty.put("returnTo", ac.getReturnActivity());
					action.put(s, actionPorperty);
					break;
				case "freechoose":
					Map actionPorperty1 = new HashMap<String,String>();
					actionPorperty1.put("openUrl", ac.getFreechooseUrl());
					action.put(s, actionPorperty1);
				}
			}			
		}
		return action;
	}
	/**
	 * RT
	 * @param activityId
	 * @return
	 */
	public ActivityConfig getActivityConfig(String activityId){
		ActivityConfig ac = this.getActivityConfigById(activityId);
		return ac;
	}
}
