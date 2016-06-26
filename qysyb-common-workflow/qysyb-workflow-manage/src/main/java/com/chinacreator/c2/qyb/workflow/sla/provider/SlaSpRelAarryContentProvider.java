package com.chinacreator.c2.qyb.workflow.sla.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.qyb.workflow.sla.entity.ServiceAgreement;
import com.chinacreator.c2.qyb.workflow.sla.entity.SlaServiceRroductRel;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class SlaSpRelAarryContentProvider implements ArrayContentProvider {

	@Override
	public Page<SlaServiceRroductRel> getElements(ArrayContext context) {
		// TODO Auto-generated method stub
		Dao dao = DaoFactory.create(SlaServiceRroductRel.class);
		Map conMap = context.getCondition();
		Pageable pageable = context.getPageable();
		ServiceAgreement sla = JSONObject.parseObject(conMap.get("slaId").toString(), ServiceAgreement.class);
//		String slaId = (String) conMap.get("slaId");
		SlaServiceRroductRel rel = new SlaServiceRroductRel();
//		ServiceAgreement sla = new ServiceAgreement();
//		sla.setServiceAgreementId(slaId);
		rel.setSlaId(sla);
		List<SlaServiceRroductRel> listcontent = new ArrayList<SlaServiceRroductRel>();
		List<SlaServiceRroductRel> listDel = new ArrayList<SlaServiceRroductRel>();  //服务产品已被删除了 关系要删除
		List<SlaServiceRroductRel> list = dao.select(rel);
		for(SlaServiceRroductRel r:list){
			if(r.getProductId()!=null){
				listcontent.add(r);
			}else{
				listDel.add(r);
			}
		}
		dao.deleteBatch(listDel);
		int psize = pageable.getPageSize();
		int index = pageable.getPageIndex();
		int total = listcontent.size();
		int low = ((index-1)*psize)>total?total:((index-1)*psize);
		int up = (((index-1)*psize)+psize)>total?total:(((index-1)*psize)+psize);
		Page<SlaServiceRroductRel> page = new Page<SlaServiceRroductRel>(pageable.getPageIndex(), pageable.getPageSize(), total, listcontent.subList(low, up));
		return page;
	}

}
