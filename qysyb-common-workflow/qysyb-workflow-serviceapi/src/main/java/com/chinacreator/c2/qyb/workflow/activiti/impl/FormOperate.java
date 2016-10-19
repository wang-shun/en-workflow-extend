package com.chinacreator.c2.qyb.workflow.activiti.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.qyb.workflow.activiti.inf.IFormOperate;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowTransition;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年6月1日 下午2:11:28 
 * 类说明 
 */
public abstract class FormOperate implements IFormOperate {
	/**
	 * save entity 要使用这个方法如下方法必须要有  getOid getBusinessKey setBusinessKey setStatus
	 * @param clazz
	 * @param json
	 * @param businessKey
	 * @param proInsId
	 * @return
	 * @throws Exception
	 */
	protected <T> int saveEntity(Class<T> clazz,String json,String businessKey,String proInsId) throws Exception{
		try{
			Dao<T> dao = DaoFactory.create(clazz);
			T ob = (T) JSONObject.parseObject(json, clazz);
			Method methodgetUId = clazz.getDeclaredMethod("getOid");
			Method methodsetUId = clazz.getDeclaredMethod("setOid",String.class);
			Method methodgetBusinessKey = clazz.getDeclaredMethod("getBusinessKey"); 
			Method methodsetBusinessKey = clazz.getDeclaredMethod("setBusinessKey",String.class); 
			Method methodsetStatus = clazz.getDeclaredMethod("setStatus",String.class);
			//反射获取主键
			String uId = (String) methodgetUId.invoke(ob);
			String businessId = (String) methodgetBusinessKey.invoke(ob);
			//有主键就update操作
			if(uId!=null&&!uId.equals("")&&businessId!=null&&!businessId.equals("")){
				//如果这两个id不相等，以入参proInsId为准。因为实体里面的businessKey有可能是草稿造成的
				if(proInsId!=null&&!businessId.equals(proInsId)){
					methodsetBusinessKey.invoke(ob,proInsId); 
					//保存草稿时不更新状态，提交才更新
					methodsetStatus.invoke(ob,"处理中");

				}
				return dao.update(ob);
			//没有主键 但是有流程实例值 那就是先看有没有这里businessKey的记录 没有就把流程实例id放到businesskey里面新增了
			}else if(proInsId!=null&&!proInsId.equals("")){
				T condition = clazz.newInstance();
				methodsetBusinessKey.invoke(condition, proInsId);
				condition = dao.selectOne(condition);
				//判断businesskey是否存在,如果存在不新增
				if(condition==null){
					methodsetBusinessKey.invoke(ob, proInsId);
	//				officeNotice.setBusinessKey(proInsId);
					methodsetStatus.invoke(ob,"处理中");
					return dao.insert(ob);
				}else{
					methodsetUId.invoke(ob, methodgetUId.invoke(condition));
 					return dao.update(ob);
				}
			//没有主键 流程实例Id也为空 这里一般就是草稿保存了吧
			}else if(businessKey!=null){
				T condition = clazz.newInstance();
				methodsetBusinessKey.invoke(condition, businessKey);
				condition = dao.selectOne(condition);
				//判断businesskey是否存在,如果存在不新增
				if(condition==null){
					methodsetBusinessKey.invoke(ob, businessKey);
	//				officeNotice.setBusinessKey(businessKey);
					methodsetStatus.invoke(ob,"draft");
					return dao.insert(ob);
				}else{
					//TODO 已经有businessKey的情况 目前没有遇到这里情形。。
					return 0;
				}
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} 
		return 0;
	}
	/**
	 * save entity 要使用这个方法如下方法必须要有  getOid getBusinessKey setBusinessKey setStatus
	 * @param clazz
	 * @param ob
	 * @param businessKey
	 * @param proInsId
	 * @return
	 * @throws Exception
	 */
	protected <T> int saveEntity(Class<T> clazz,T ob,String businessKey,String proInsId) throws Exception{
		try{
			Dao<T> dao = (Dao<T>) DaoFactory.create(clazz);
			Method methodgetUId = clazz.getDeclaredMethod("getOid");
			Method methodsetUId = clazz.getDeclaredMethod("setOid",String.class);
			
			Method methodgetBusinessKey = clazz.getDeclaredMethod("getBusinessKey"); 
			Method methodsetBusinessKey = clazz.getDeclaredMethod("setBusinessKey",String.class); 
			Method methodsetStatus = clazz.getDeclaredMethod("setStatus",String.class);
			//反射获取主键
			String uId = (String) methodgetUId.invoke(ob);
			String businessId = (String) methodgetBusinessKey.invoke(ob);
			//有主键就update操作
			if(uId!=null&&!uId.equals("")&&businessId!=null&&!businessId.equals("")){
				//如果这两个id不相等，以入参proInsId为准。因为实体里面的businessKey有可能是草稿造成的
				if(proInsId!=null&&!businessId.equals(proInsId)){
					methodsetBusinessKey.invoke(ob,proInsId);
					// 更新业务数据状态 由草稿变为提交状态了 
					methodsetStatus.invoke(ob,"CLZ");
				}
				return dao.update(ob);
			//没有主键 但是有流程实例值 那就是先看有没有这里businessKey的记录 没有就把流程实例id放到businesskey里面新增了
			}else if(proInsId!=null&&!proInsId.equals("")){
				T condition = clazz.newInstance();
				methodsetBusinessKey.invoke(condition, proInsId);
				condition = dao.selectOne(condition);
				//判断businesskey是否存在,如果存在不新增
				if(condition==null){
					methodsetBusinessKey.invoke(ob, proInsId);
					methodsetStatus.invoke(ob,"CLZ");
	//				officeNotice.setBusinessKey(proInsId);
					return dao.insert(ob);
				}else{
 					methodsetUId.invoke(ob, methodgetUId.invoke(condition));
 					return dao.update(ob);
				}
			//没有主键 流程实例Id也为空 这里一般就是草稿保存了吧
			}else if(businessKey!=null){
				T condition = clazz.newInstance();
				methodsetBusinessKey.invoke(condition, businessKey);
				condition = dao.selectOne(condition);
				//判断businesskey是否存在,如果存在不新增
				if(condition==null){
					methodsetBusinessKey.invoke(ob, businessKey);
					methodsetStatus.invoke(ob,"draft");
	//				officeNotice.setBusinessKey(businessKey);
					return dao.insert(ob);
				}else{
					//TODO 已经有businessKey的情况 目前没有遇到这里情形。。
					return 0;
				}
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} 
		return 0;		
	} 
	@SuppressWarnings("unchecked")
	protected <T> Map<String,Object> getEntity(Class<T> clazz,String businessKey,
			String proInsId){
		try {
			Dao<T> dao = DaoFactory.create(clazz);
			T condition = clazz.newInstance();
			String businessK = proInsId==null?businessKey:proInsId;
			Method methodsetBusinessKey = clazz.getDeclaredMethod("setBusinessKey",String.class); 
			methodsetBusinessKey.invoke(condition, businessK);
			condition = dao.selectOne(condition);
			String jsonO = JSONObject.toJSONString(condition);
			Map<String,Object> eMap = JSONObject.parseObject(jsonO,Map.class);	
			return eMap;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	};

 	protected <T> T parseObjectFromJson(Class<T> clazz,String jsonStr){
 		T t = JSONObject.parseObject(jsonStr, clazz);
 		return t;
 	}
}
