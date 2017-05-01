package com.chinacreator.c2.qyb.workflow.process.provider;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.activiti.taskquery.impl.TodoWorkService;
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
@Service("tasktodo")
public class WorkServiceArrayContentProviderImp implements
		ArrayContentProvider {

	@Autowired
	WorkFlowService wfs;
	@Autowired
	TodoWorkService todoWorkService;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Map> getElements(ArrayContext arraycontext) {
		// TODO Auto-generated method stub	
		Map map = arraycontext.getCondition();
		map.put("sortable", arraycontext.getSortable());
		Pageable pageable = arraycontext.getPageable();
		String retrieveId = (String) map.get(WorkFlowService.RETRIEVE_KEY);
		AccessControlService acc=ApplicationContextManager.getContext().getBean(AccessControlService.class);;
		String userId = acc.getUserID();

		List<Map> list = todoWorkService.getTodoWorkByCon(retrieveId,map,userId,pageable.getOffset(),pageable.getPageSize());
		int total = todoWorkService.getTodoWorkTotalByCon(retrieveId,map,userId);						
		Page<Map> page = new Page<Map>(pageable.getPageIndex(), pageable.getPageSize(), total, list);
		return page;
	}

}
