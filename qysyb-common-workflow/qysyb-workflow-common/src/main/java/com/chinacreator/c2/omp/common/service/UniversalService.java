package com.chinacreator.c2.omp.common.service;


import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;

import com.chinacreator.c2.ioc.ApplicationContextManager;

/**
 * 
 * @author tao.wang
 *
 * 日期：2015-5-20
 *文件名称：公共服务类
 */
public class UniversalService {
	/**
	 * 查询机构信息
	 * @return
	 * @throws 
	 * 
	 */
	public OrgDTO getOrgInfo(){
		OrgDTO orgInfo=new OrgDTO();
		AccessControlService acc=new AccessControlServiceImpl();
		String userId = acc.getUserID();
		if (null != userId && !userId.trim().equals(""))
		{
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);
			orgInfo= userService.queryMainOrg(userId);
		}
		
		return orgInfo;
	}
}
