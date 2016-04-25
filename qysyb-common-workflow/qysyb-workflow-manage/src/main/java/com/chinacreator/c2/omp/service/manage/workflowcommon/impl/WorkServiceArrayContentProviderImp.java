package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.serviceproductmanage.ServiceProduct;
import com.chinacreator.c2.omp.service.manage.workflowcommon.WorkService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.service.WorkFlowService;
import com.chinacreator.c2.sysmgr.AuthenticationProvider;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;
/**
 * 
 * <p>RT</p>
 * @author qiao.li
 * @version 1.0
 * @date 2015-10-10
 */
public class WorkServiceArrayContentProviderImp implements
		ArrayContentProvider {
	final static String VIEWTYPEKEY = "viewType";
	final static String VIEWTYPEALL = "viewAll";
	WorkFlowService wfs;
	AuthenticationProvider authenticationProvider;
	public WorkServiceArrayContentProviderImp() {
		wfs = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
	}

	@Override
	public Page<Map> getElements(ArrayContext arraycontext) {
		// TODO Auto-generated method stub	
		Map map = arraycontext.getCondition();
		map.put("sortable", arraycontext.getSortable());
		Pageable pageable = arraycontext.getPageable();
		String retrieveId = (String) map.get(WorkFlowService.RETRIEVE_KEY);
		String viewType = (String) map.get(WorkServiceArrayContentProviderImp.VIEWTYPEKEY);
		AccessControlService acc=new AccessControlServiceImpl();
		String userId = acc.getUserID();
		if(viewType!=null&&viewType.equals(WorkServiceArrayContentProviderImp.VIEWTYPEALL)){
			userId = null;
		}
//		if(ServiceType==null){
//			ServiceType = "-1";//没有值 不让查出东西来。
//		}
		List<Map> list = wfs.getTodoWorkByST(retrieveId,map,userId,pageable.getOffset(),pageable.getPageSize());
		int total = wfs.getTodoWorkTotalByST(retrieveId,map,userId);						
		Page<Map> page = new Page<Map>(pageable.getPageIndex(), pageable.getPageSize(), total, list);
		return page;
	}

}
