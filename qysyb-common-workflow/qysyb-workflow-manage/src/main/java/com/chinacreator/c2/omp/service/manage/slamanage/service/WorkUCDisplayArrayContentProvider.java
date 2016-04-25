package com.chinacreator.c2.omp.service.manage.slamanage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
//import com.chinacreator.c2.monitor.basic.entity.MonitorObject;
import com.chinacreator.c2.omp.service.manage.slamanage.ServiceAgreement;
import com.chinacreator.c2.omp.service.manage.slamanage.ServiceProductUCRel;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class WorkUCDisplayArrayContentProvider implements ArrayContentProvider {

	@Override
	public Page<ServiceProductUCRel> getElements(ArrayContext context) {
		// TODO Auto-generated method stub
		List<ServiceProductUCRel> listcontent = new ArrayList<ServiceProductUCRel>();
		Pageable pageable = context.getPageable();
		Map con = context.getCondition();	
		String serviceProductNo = (String) con.get("serviceProductNo");
		ServiceAgreementService sas = ApplicationContextManager.getContext().getBean(ServiceAgreementService.class);
		listcontent = sas.getUCByServiceProductNo(serviceProductNo);
		int listrsize = listcontent.size();
		int size = pageable.getPageSize();
		int index = pageable.getPageIndex()-1;
		int offset = pageable.getOffset();
		int low = index*size> listrsize?listrsize:index*size;
		int up = index*size+size>listrsize?listrsize:index*size+size;
		Page<ServiceProductUCRel> page = new Page<ServiceProductUCRel>(pageable.getPageIndex(), pageable.getPageSize(), listcontent.size(), listcontent.subList(low, up));
		return page;
	}

}
