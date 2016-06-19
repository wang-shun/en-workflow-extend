package com.chinacreator.c2.qyb.workflow.config.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.qyb.workflow.config.entity.UserConcernedConfig;

/**
 * 
 * @author qiao
 *
 */
@Service
public class UserConcernedConfigService {
	final static String CONCERNTHISWORK = "已关注此工单";
	final static String CONCERNTHISSP = "已关注此服务";
	
	final static String concernthiswork = "关注此工单";	//关注此工单
	final static String concernthisactivityinthiswork = "关注此工单此环节";	//关注此工单此环节
	final static String concerntheproduct = "关注此服务产品";		//关注此服务产品
	final static String concernthisactivityintheproduct = "关注此服务产品此环节";	//关注此服务产品此环节
	
	final static String ACTIVITY_CONCERN_CONFIG = "activiyconfig";
	/**
	 * select
	 * @param ucc
	 * @return
	 */
	public List<UserConcernedConfig> getUserConcernedConfig(UserConcernedConfig ucc){
		if(ucc==null){
			return null;
		}
		Dao<UserConcernedConfig> dao = DaoFactory.create(UserConcernedConfig.class);
		return dao.select(ucc);
	}
	
	public Map<String,String> getConcernStatusInfo(String userId,String proInsId,String proDefId){
		Dao<UserConcernedConfig> dao = DaoFactory.create(UserConcernedConfig.class);
		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> resultmap = new HashMap<String,String>();
		map.put("processDefId", proDefId);
		map.put("userId", userId);
		List<UserConcernedConfig> list = dao.getSession().selectList("com.chinacreator.c2.qyb.workflow.config.entity.UserConcernedConfigMapper.selectForStatus", map);
		for(UserConcernedConfig u:list){
			if(u.getProcessInsId()==null){
				resultmap.put("ConcernStatus", UserConcernedConfigService.CONCERNTHISSP);
			}
			if(u.getProcessInsId()!=null&&u.getProcessInsId().equals(proInsId)){
				resultmap.put("ConcernStatus", UserConcernedConfigService.CONCERNTHISWORK);
				break;
			}
		}
//		map.put("processInsId", proInsId);
		return resultmap;
	}
	/**
	 * 做用户关注工单的添加和删除
	 * @param ucc
	 * @param userId
	 * @param concernStatus
	 * @param action
	 * @return
	 */
	public synchronized UserConcernedConfig operateUserconcernConfig(UserConcernedConfig ucc,String userId,String concernStatus,String action){
		Dao<UserConcernedConfig> dao = DaoFactory.create(UserConcernedConfig.class);
		String informType = ucc.getInformType()==null?"":ucc.getInformType();	
		/*concernStatus不为空表示删除配置*/
		if(concernStatus!=null&&!concernStatus.equals("undefined")){
			switch(concernStatus){
			case UserConcernedConfigService.CONCERNTHISWORK:
				//设置过滤条件
				if(ucc.getProcessInsId()==null||ucc.getObserverId()==null||ucc.getProcessDefId()==null) break;
				ucc.setTaskDefId(null);
				ucc.setInformType(null);
				
				List<UserConcernedConfig> list = dao.select(ucc);
				
				List<UserConcernedConfig> listdel = new ArrayList<UserConcernedConfig>();
				for(UserConcernedConfig u:list){
					//就是一个环节的设置 不用删除
					if(u.getTaskDefId()!=null){
						continue;
					}
					listdel.add(u);
				}
				dao.deleteBatch(listdel);
				break;
			case UserConcernedConfigService.CONCERNTHISSP:
				//过滤条件
				if(ucc.getObserverId()==null||ucc.getProcessDefId()==null) break;
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("processDefId", ucc.getProcessDefId());
				map.put("userId", ucc.getObserverId());
				List<UserConcernedConfig> list1 = dao.getSession().selectList("com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.UserConcernedConfigMapper.selectForStatus", map);
			
				List<UserConcernedConfig> listdel1 = new ArrayList<UserConcernedConfig>();
				for(UserConcernedConfig u:list1){
					// 就是针对一个工单或一个环节的设置 不用删除
					if(u.getProcessInsId()!=null||u.getTaskDefId()!=null){
						continue;
					}
/*					//环节配置的组关注需不需要被组中的一个人在工单查看页面取消关注
					if(u.getRemark1()!=null){
						continue;
					}*/
					listdel1.add(u);
				}
				dao.deleteBatch(listdel1);
				break;
			}

		}else if(concernStatus==null||concernStatus.equals("undefined")){//关注操作	
			switch(action){
			case UserConcernedConfigService.concernthiswork:
				if(ucc.getProcessInsId()==null||ucc.getObserverId()==null||ucc.getProcessDefId()==null) break;
				ucc.setTaskDefId(null);
				ucc.setInformType(null);
				
				List<UserConcernedConfig> list = dao.select(ucc);
				if(list.size()==0){
					ucc.setInformType(informType);
					dao.insert(ucc);
					return ucc;
				}else{
					boolean needInsert = true;
					for(UserConcernedConfig u:list){
						//表示有一个配置项是一样了,需要更新配置
						if(u.getTaskDefId() == null){
							needInsert = false;
							u.setInformType(informType);
							dao.update(u);
							break;
						}
					}
					if(needInsert){
						ucc.setInformType(informType);
						dao.insert(ucc);
						return ucc;
					}
				}
				break;
			case UserConcernedConfigService.concerntheproduct:
				//过滤条件
				if(ucc.getObserverId()==null||ucc.getProcessDefId()==null) break;
				ucc.setProcessInsId(null);
				ucc.setTaskDefId(null);
				ucc.setInformType(null);
				
				List<UserConcernedConfig> list1 = dao.select(ucc);
				if(list1.size()==0){
					ucc.setInformType(informType);
					dao.insert(ucc);
					return ucc;
				}else{
					boolean needInsert = true;
					for(UserConcernedConfig u:list1){
						//表示有一个配置项是一样了
						if(u.getTaskDefId() == null&&u.getProcessInsId() == null){
							needInsert = false;
							u.setInformType(informType);
							dao.update(u);
							break;
						}
					}
					if(needInsert){
						ucc.setInformType(informType);
						dao.insert(ucc);
						return ucc;
					}
				}
				break;
			}

			
		}
		return null;
	}
	
	public synchronized UserConcernedConfig operateUserconcernConfigForActivity(UserConcernedConfig u,String action){
		Dao<UserConcernedConfig> dao = DaoFactory.create(UserConcernedConfig.class);
		//通过节点设置 设置的用户关注，与用户主动点击关注区分开来
		if(u.getRemark1()==null||!u.getRemark1().equals(ACTIVITY_CONCERN_CONFIG)){
			return null;
		}
		switch(action){
		case UserConcernedConfigService.concernthisactivityintheproduct:
			if(u.getTaskDefId()==null||u.getProcessDefId()==null){
				return null;
			}
			UserConcernedConfig con = new UserConcernedConfig();
			con.setRemark1(ACTIVITY_CONCERN_CONFIG);
			con.setTaskDefId(u.getTaskDefId());
			con.setProcessDefId(u.getProcessDefId());
			con = dao.selectOne(con);
			if(con==null){
				dao.insert(u);
			}else{
				con.setCatogory(u.getCatogory()==null?"":u.getCatogory());
				con.setObserverId(u.getObserverId()==null?"":u.getObserverId());
				con.setRemark2(u.getRemark2()==null?"":u.getRemark2());
				con.setInformType(u.getInformType()==null?"":u.getInformType());
				dao.update(con);
			}
			return u;
		case UserConcernedConfigService.concerntheproduct:
			if(u.getProcessDefId()==null){
				return null;
			}
			UserConcernedConfig con1 = new UserConcernedConfig();
			con1.setRemark1(ACTIVITY_CONCERN_CONFIG);
			con1.setProcessDefId(u.getProcessDefId());
			//会把环节的关注配置选出来 需要过滤
			List<UserConcernedConfig> list = dao.select(con1);
			boolean insert = true;
			for(UserConcernedConfig ucc:list){
				if(ucc==null||(ucc!=null&&ucc.getTaskDefId()!=null)){
					continue;
				}
				//表示不需要插入，只要更新了
				if(ucc!=null&&ucc.getTaskDefId()==null){
					con1.setId(ucc.getId());
					insert = false;
					break;
				}
			}
			if(insert){
				u.setTaskDefId(null);
				dao.insert(u);
			}else{
				con1.setCatogory(u.getCatogory()==null?"":u.getCatogory());
				con1.setObserverId(u.getObserverId()==null?"":u.getObserverId());
				con1.setRemark2(u.getRemark2()==null?"":u.getRemark2());
				con1.setInformType(u.getInformType()==null?"":u.getInformType());
				dao.update(con1);
			}
		}
		return null;
		
	}
	
	public UserConcernedConfig getUserconcernInfoForActivity(UserConcernedConfig u){
		Dao<UserConcernedConfig> dao = DaoFactory.create(UserConcernedConfig.class);
		//通过节点设置 设置的用户关注，与用户主动点击关注区分开来
		if(u.getRemark1()==null||!u.getRemark1().equals(ACTIVITY_CONCERN_CONFIG)){
			return null;
		}
		UserConcernedConfig con = new UserConcernedConfig();
		con.setRemark1(ACTIVITY_CONCERN_CONFIG);
		
		if(u.getTaskDefId()!=null){
			con.setTaskDefId(u.getTaskDefId());
		}
		if(u.getProcessDefId()!=null){
			con.setProcessDefId(u.getProcessDefId());
		}
		//会把环节的关注配置选出来 需要过滤
		List<UserConcernedConfig> list = dao.select(con);
		if(list.size()>1){
			for(UserConcernedConfig ucc:list){
				if(ucc==null||(ucc!=null&&ucc.getTaskDefId()!=null)){
					continue;
				}
				//表示有记录了
				if(ucc!=null&&ucc.getTaskDefId()==null){
					return ucc;
				}
			}
			//走到这里说明 还没有产品关注的配置
			u.setInformType(null);
			return u;			
		}else if(list.size()==1){
			return list.get(0);
		}else{
			u.setInformType(null);
			return u;	
		}


	}
}
