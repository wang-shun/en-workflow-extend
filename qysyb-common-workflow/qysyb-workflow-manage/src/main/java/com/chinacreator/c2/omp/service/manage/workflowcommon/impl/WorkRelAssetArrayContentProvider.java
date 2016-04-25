package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
//import com.chinacreator.c2.monitor.basic.entity.MonitorObject;
//import com.chinacreator.c2.monitor.basic.service.impl.MonitorObjectServiceImpl;
import com.chinacreator.c2.omp.service.manage.workflowcommon.WorkAssetRel;
import com.chinacreator.c2.omp.service.manage.workflowcommon.WorkService;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;
/**
 * 
 * @author qiao
 *
 */
public class WorkRelAssetArrayContentProvider implements ArrayContentProvider {

	@Override
	public Page<?> getElements(ArrayContext context) {
//		// TODO Auto-generated method stub
//		Map conmap = context.getCondition();
//
//		String businessKey = (String) conmap.get("businessKey");
//		
////		MonitorObjectServiceImpl mos = ApplicationContextManager.getContext().getBean(MonitorObjectServiceImpl.class);
//		MonitorObjectServiceImpl mos = new MonitorObjectServiceImpl();
//		List<MonitorObject> listr = new ArrayList<MonitorObject>();
//		Dao<WorkAssetRel> dao = DaoFactory.create(WorkAssetRel.class);
//		WorkAssetRel con = new WorkAssetRel();
//		con.setWorkId(businessKey);
//		List<WorkAssetRel> list = dao.select(con);
//		for(WorkAssetRel war:list){
//			listr.add(mos.getMonitorObjectById(war.getAssetId()));
//		}
//		Pageable pageable = context.getPageable();
//		int listrsize = listr.size();
//		int size = pageable.getPageSize();
//		int index = pageable.getPageIndex()-1;
//		int offset = pageable.getOffset();
//		int low = index*size> listrsize?listrsize:index*size;
//		int up = index*size+size>listrsize?listrsize:index*size+size;
//		Page<MonitorObject> page = new Page<MonitorObject>(pageable.getPageIndex(), pageable.getPageSize(), listr.size(), listr.subList(low, up));
//		return page;
		return null;
	}

}
