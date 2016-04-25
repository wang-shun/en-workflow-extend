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
	 * 
	 * @param o
	 * @return
	 */
	public ActivityConfig getActivityConfigOne(ActivityConfig o){
		if(o.getId()==null){
			return new ActivityConfig();
		}
		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
		ActivityConfig ac = daoac.selectOne(o);
		if(ac==null){
			return new ActivityConfig();
		}else{
			return ac;
		}
	}
	/**
	 * 获取环节的操作
	 * @param activityId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getActivityActions(String activityId){
		Map action = new HashMap<String,Object>();
		ActivityConfig ac = this.getActivityConfigById(activityId);
		if(ac!=null&&ac.getRemark2()!=null&&ac.getRemark2()!=""){
			String actionStr = ac.getRemark2();
			String[] actions = actionStr.split(",");
			for(String s:actions){
				switch(s){
				case "return":
					Map actionPorperty = new HashMap<String,String>();
					actionPorperty.put("returnTo", ac.getRemark3());
					action.put(s, actionPorperty);
					break;
				case "freechoose":
					Map actionPorperty1 = new HashMap<String,String>();
					actionPorperty1.put("openUrl", ac.getRemark4());
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
