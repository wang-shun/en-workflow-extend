package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.hisinsquery.impl.HistoricProcInstanceQueryService;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class WorkInsArrayContentProvider implements ArrayContentProvider {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<Map> getElements(ArrayContext context) {
		// TODO Auto-generated method stub
		Map conmap = context.getCondition();
		conmap.put("sortable", context.getSortable());
		Pageable pageable = context.getPageable();
		
//		String serviceType[] = (String[]) conmap.get("serviceType");
		WorkFlowService wfs = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
//		int total = wfs.getAllHisWorkCount(null, conmap, null, null);
//		List<Map<String,Object>> list = wfs.getWorkInsBySTS(serviceType, conmap,null, null, pageable.getOffset(),pageable.getPageSize());
//		List<Map<String,Object>> list = wfs.getAllWorkIns(null, conmap,null, null, pageable.getOffset(),pageable.getPageSize());
		
		HistoricProcInstanceQueryService historicProcInstanceQueryService = ApplicationContextManager.getContext().getBean(HistoricProcInstanceQueryService.class);
		String retrieveId = (String) conmap.get(WorkFlowService.RETRIEVE_KEY);
		List<Map> list = historicProcInstanceQueryService.getHistoricInstanceWithExternalStorage(retrieveId, conmap, null, null, pageable.getOffset(),pageable.getPageSize());
		int total = historicProcInstanceQueryService.countHistoricInstanceWithExternalStorage(retrieveId, conmap, null, null);
		Page<Map> page = new Page<Map>(pageable.getPageIndex(), pageable.getPageSize(), total, list);
		return page;
	}

}
