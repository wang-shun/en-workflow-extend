package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

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
import com.chinacreator.c2.omp.service.manage.workflowcommon.Archhandle;

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
	public int deleteArchhandleById(String id) {
		Dao<Archhandle> dao = DaoFactory.create(Archhandle.class);
		Archhandle archhandle = new Archhandle();
		archhandle.setOid(id);
		return dao.delete(archhandle);
	}


	public List<Archhandle> getArchhandle(Archhandle archhandle) {
		Dao<Archhandle> dao = DaoFactory.create(Archhandle.class);
		return dao.select(archhandle,new Sortable(new Order("auditTime","desc")));
	}
	
	public List<Archhandle> getArchhandle(Archhandle archhandle,String businessKey) {
		archhandle.setBusinessKey(businessKey);
		return this.getArchhandle(archhandle);
	}
}
