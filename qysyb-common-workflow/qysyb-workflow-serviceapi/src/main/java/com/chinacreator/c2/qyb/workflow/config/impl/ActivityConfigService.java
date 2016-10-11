package com.chinacreator.c2.qyb.workflow.config.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.qyb.workflow.config.entity.ActivityConfig;
@Service
public class ActivityConfigService {
	public final static String ACTION_FILTERTYPE = "filtertype";
	public final static String ACTION_RETURN = "return";
	public final static String ACTION_FREECHOOSE = "freechoose";
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
					break;
				default:
					String[] kvs = s.split(":");
					if(kvs.length==2){
						String key = kvs[0];
						String values = kvs[1];
						String[] vs = values.split("\\|");
						List list = new ArrayList();
						for(String ss : vs){
							list.add(ss);
						}
//						Map actionPorperty2 = new HashMap<String,String>();	
//						actionPorperty2.put(key, list);
						action.put(key, list);
 					}else if(kvs.length==1){
 						String key = kvs[0];
 						action.put(key, true);						
					}

				}
			}			
		}
		return action;
	}
	/**
 	 * 获取特定的
 	 * @param moduleId
 	 * @param taskDefKey
 	 * @param acntionKey
 	 * @return
 	 */
 	public String getActivityAction(String moduleId,String taskDefKey,String acntionKey){
 		Map actions = getActivityActions(moduleId, taskDefKey);
 		if(actions.containsKey(acntionKey)){
 			if(actions.get(acntionKey) instanceof List){
 				List action = (List) actions.get(acntionKey);
 				if(action.size()==1){
 					return (String) action.get(0);
 				}			
 			}else{//TODO
 				
 			}
 
 		}	
 		return null;
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
 	/**
 	 * 
 	 * @param moduleId
 	 * @param taskDefKey
 	 * @return
 	 */
 	public ActivityConfig getActivityConfig(String moduleId, String taskDefKey){
 		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
 		ActivityConfig con = new ActivityConfig();
 		con.setModuleId(moduleId);
 		con.setTaskDefId(taskDefKey);		
 		ActivityConfig ac = daoac.selectOne(con);	
 		return ac;
 	}	
}
