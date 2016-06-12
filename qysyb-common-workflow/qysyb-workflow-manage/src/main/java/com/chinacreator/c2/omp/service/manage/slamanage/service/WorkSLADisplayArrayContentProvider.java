package com.chinacreator.c2.omp.service.manage.slamanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.sla.entity.ServiceAgreement;
import com.chinacreator.c2.qyb.workflow.sla.entity.Sla_kpi_rel;
import com.chinacreator.c2.qyb.workflow.sla.impl.ServiceAgreementService;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class WorkSLADisplayArrayContentProvider implements ArrayContentProvider {

	@Override
	public Page<Map<String,Object>> getElements(ArrayContext context) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> listcontent = new ArrayList<Map<String,Object>>();
		Pageable pageable = context.getPageable();
		Map con = context.getCondition();	
		String serviceProductNo = (String) con.get("serviceProductNo");
		ServiceAgreementService sas = ApplicationContextManager.getContext().getBean(ServiceAgreementService.class);
		ServiceAgreement sla = sas.getTheWorkedSLAByProductNo(serviceProductNo);
		if(sla!=null){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("slaName", sla.getServiceAgreementName());
			map.put("startTime", sla.getStartDate());
			map.put("endTime", sla.getEndDate());
			Dao<Sla_kpi_rel> daorel = DaoFactory.create(Sla_kpi_rel.class);
			Sla_kpi_rel rel = new Sla_kpi_rel();
			rel.setServiceAgreementId(sla.getServiceAgreementId());
			List<Sla_kpi_rel> listrel = daorel.select(rel);
			Map<String,String> slamap = new HashMap<String,String>();
			for(Sla_kpi_rel r:listrel){
				slamap.put(r.getKpiId().getKpiName(),r.getVlaue());
			}
			map.put("slaInfo", slamap);
			listcontent.add(map);
		}
		Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageable.getPageIndex(), pageable.getPageSize(), 1, listcontent);
		return page;
	}

}
