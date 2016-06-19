package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.workflowcommon.WorkService;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.workrel.entity.WorkRel;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class WorkRelWorkArrayContentProvider implements ArrayContentProvider {

	@Override
	public Page<Map<String,Object>> getElements(ArrayContext context) {
		// TODO Auto-generated method stub
		Map conmap = context.getCondition();
		Pageable pageable = new Pageable();
		String workId = (String) conmap.get("workId");
		String formId = (String) conmap.get("formId");
		Dao<WorkRel> dao = DaoFactory.create(WorkRel.class);
		WorkRel wr = new WorkRel();
		wr.setWorkId(workId);
		List<WorkRel> listwr = dao.select(wr);
		List<WorkService> listws = new ArrayList<WorkService>();
		List<Map<String,Object>> listmap = new ArrayList<Map<String,Object>>();
		WorkFlowService sfs = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
		for(WorkRel w:listwr){
//			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> map = sfs.getWorkServiceByBK(w.getWworkId());
			if(map!=null){
				listmap.add(map);
			}
		}
		int psize = pageable.getPageSize();
		int index = pageable.getPageIndex();
		int total = listmap.size();
		int low = ((index-1)*psize)>total?total:((index-1)*psize);
		int up = (((index-1)*psize)+psize)>total?total:(((index-1)*psize)+psize);
		Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageable.getPageIndex(), pageable.getPageSize(), total, listmap.subList(low, up));
		return page;
	}

}
