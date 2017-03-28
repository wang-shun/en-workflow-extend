package com.chinacreator.c2.qyb.workflow.audit.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Order;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.Organization;
import com.chinacreator.c2.omp.common.UserInfo;
import com.chinacreator.c2.omp.common.service.UserInfoService;
import com.chinacreator.c2.qyb.workflow.audit.entity.Archhandle;

@Service
public class ArchhandleServiceImpl  {

	public int addArchhandle(Archhandle archhandle) {
		Dao<Archhandle> dao = DaoFactory.create(Archhandle.class);
		Dao<UserInfo> udao = DaoFactory.create(UserInfo.class);
		Dao<Organization> odao = DaoFactory.create(Organization.class);
		UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);
		
		UserInfoService uis = new UserInfoService();
		UserDTO user = uis.getUserInfo();
		
		OrgDTO odto = userService.queryMainOrg(user.getUserId());
		Timestamp d = new Timestamp(System.currentTimeMillis()); 
		archhandle.setAuditTime(d);
		archhandle.setAuditerId(udao.selectByID(user.getUserId()));
		archhandle.setAuditOrgId(odao.selectByID(odto.getOrgId()));
		return dao.insert(archhandle);
	}
	/**
	 * 存入审核意见
	 * @param jsonObject
	 * @return
	 */
	public int addArchhandle(JSONObject jsonObject,String businessKey){
		jsonObject.put("auditTime", null);
		Archhandle archhandle = JSONObject.toJavaObject(jsonObject, Archhandle.class);
		archhandle.setProcInsId(businessKey);
		return this.addArchhandle(archhandle);
	}
	/**
	 * 存入审核意见 list 
	 * @param jsonArray
	 * @param businessKey
	 * @param businessKey2
	 * @return
	 */
	public int saveArchhandles(JSONArray jsonArray,String proInsId,String activityId,String businessKey){
		List<Archhandle> archhandleDel = new ArrayList<Archhandle>();
		List<Archhandle> archhandleAdd = new ArrayList<Archhandle>();
		// 审核意见 can not be update
//		List archhandleUpdate = new ArrayList<Archhandle>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			//这里表示是新增的
			boolean isLocal = jsonObject.get("local")==null?false:(boolean) jsonObject.get("local");
			if(isLocal){
				jsonObject.put("auditTime", null);
				Archhandle archhandle = JSONObject.toJavaObject(jsonObject, Archhandle.class);
				archhandle.setActivityId(activityId);
				archhandle.setBusinessKey(businessKey);
				archhandle.setProcInsId(proInsId);
				archhandleAdd.add(archhandle);				
			}else{
				//TODO 要不要更新 删除
			}

		}
		int r = 0;
		for(Archhandle arc:archhandleAdd){
			r = r + addArchhandle(arc);
		}
		return r;
	}
	
	/**
	 * 存入审核意见 
	 * @param jsonArray
	 * @param businessKey
	 * @param businessKey2
	 * @return
	 */
	public int saveArchhandle(JSONObject jsonObject,String proInsId,String activityId,String activityName,String businessKey){
		List<Archhandle> archhandleDel = new ArrayList<Archhandle>();
		List<Archhandle> archhandleAdd = new ArrayList<Archhandle>();

		jsonObject.put("auditTime", null);
		Archhandle archhandle = JSONObject.toJavaObject(jsonObject, Archhandle.class);
		archhandle.setActivityId(activityId);
		archhandle.setActivityName(activityName);
		archhandle.setBusinessKey(businessKey);
		archhandle.setProcInsId(proInsId);
		archhandleAdd.add(archhandle);				

		int r = 0;
		for(Archhandle arc:archhandleAdd){
			r = r + addArchhandle(arc);
		}
		return r;
	}	
	
	public int deleteArchhandleById(String id) {
		Dao<Archhandle> dao = DaoFactory.create(Archhandle.class);
		Archhandle archhandle = new Archhandle();
		archhandle.setOid(id);
		return dao.delete(archhandle);
	}
	/**
	 * 获取 审核意见list
	 * @param businessKey
	 * @param proInsId
	 * @return
	 */
	public List<Archhandle> getArchhandle(String businessKey,String proInsId,String fieldNo) {
		String businessK = proInsId==null?businessKey:proInsId;
		Archhandle con = new Archhandle();
		con.setProcInsId(businessK);
		con.setBusinessKey(fieldNo);
		return getArchhandle(con);
	}

	public List<Archhandle> getArchhandle(Archhandle archhandle) {
		Dao<Archhandle> dao = DaoFactory.create(Archhandle.class);
		return dao.select(archhandle,new Sortable(new Order("auditTime","asc")));
	}
	
	public List<Archhandle> getArchhandle(Archhandle archhandle,String businessKey) {
		archhandle.setBusinessKey(businessKey);
		return this.getArchhandle(archhandle);
	}
	/**
	 * 存入jsonObject里面的审核意见
	 * @param jsonObject
	 * @param keys
	 * @param proInsId
	 * @param activityId
	 * @return
	 */
	public int saveArchhandles(JSONObject jsonObject,String[] keys,String proInsId,String activityId){
		int i = 0;
		for(String key:keys){
			if (jsonObject.get(key) instanceof JSONArray) {
				JSONArray auditList = (JSONArray)jsonObject.get(key);
				if(auditList!=null&&auditList.size()>0){
					i = i + saveArchhandles(auditList,proInsId,activityId,key);
				}
			}			
		}
		return i;
	}
	/**
	 * 获取审核意见by keys
	 * @param keys
	 * @param businessKey
	 * @param proInsId
	 * @param activityId
	 * @param fieldNo
	 * @return
	 */
	public Map<String,Object> getArchhandle(String[] keys,String businessKey,String proInsId,String activityId){
		Map<String,Object> result = new HashMap<String,Object>();
		for(String key:keys){
			List<Archhandle> listA = getArchhandle(businessKey,proInsId,key);
			result.put(key, listA);
		}
		return result;
	}
	/**
	 * 获取jsonObject里面的Archhandle
	 * @param jsonO
	 * @param key
	 * @param local 是否需要前台标记为local的意见 表示是这一步新添加的意见而不是历史数据
	 * @return
	 */
	public List<Archhandle> getArchhandlesFromJSONObject(JSONObject jsonO,String key,boolean local){
		List<Archhandle> archhandles = new ArrayList<Archhandle>();
		if (jsonO.get(key) instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)jsonO.get(key);
			if(jsonArray!=null&&jsonArray.size()>0){
				for(int i=0;i<jsonArray.size();i++){
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					//这里表示是新增的
					boolean isLocal = jsonObject.get("local")==null?false:(boolean) jsonObject.get("local");
					if(!local){
						archhandles.add(JSONObject.toJavaObject(jsonObject, Archhandle.class));							
					}else if(local&&isLocal){
						archhandles.add(JSONObject.toJavaObject(jsonObject, Archhandle.class));	
					}
				}
			}
		}	
		return archhandles;
	}
	/**
	 * 获取一条审核意见 从jsonObject中
	 * @param jsonO
	 * @param key
	 * @param local
	 * @return
	 */
	public Archhandle getOneArchhandleFromJSONObject(JSONObject jsonO,String key,boolean local){
		List<Archhandle> archhandles = getArchhandlesFromJSONObject(jsonO,key,local);
		if(archhandles!=null&&archhandles.size()==1){
			return archhandles.get(0);
		}
		return null;
	}
}
