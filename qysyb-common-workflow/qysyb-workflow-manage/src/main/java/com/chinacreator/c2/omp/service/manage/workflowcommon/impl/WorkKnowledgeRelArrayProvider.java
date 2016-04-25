package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
//import com.chinacreator.c2.monitor.knowledge.entity.KnowledgeManage;

import com.chinacreator.c2.omp.service.manage.workflowcommon.WorkKnowledgeRel;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class WorkKnowledgeRelArrayProvider implements ArrayContentProvider {

	@Override
	public Page<?> getElements(ArrayContext context) {
//		// TODO Auto-generated method stub
//		List<KnowledgeManage> listcontent = new ArrayList<KnowledgeManage>();
//		//如果知识已经不存在 则需要delete
//		List<WorkKnowledgeRel> dellist = new ArrayList<WorkKnowledgeRel>();
//		Dao<WorkKnowledgeRel> dao = DaoFactory.create(WorkKnowledgeRel.class);
//		Dao<KnowledgeManage> daok = DaoFactory.create(KnowledgeManage.class);
//		Pageable pageable = context.getPageable();
//		Map con = context.getCondition();	
//		String workId = (String) con.get("workId");
//		WorkKnowledgeRel wkr = new WorkKnowledgeRel();
//		wkr.setWorkId(workId);
//		List<WorkKnowledgeRel> listwkr = dao.select(wkr);
//		for(WorkKnowledgeRel w:listwkr){
//			if(w.getKnowledgeId()==null){
//				dellist.add(w);
//			}else{
//				//关联查询查询不出被关联的实体中关联的另一个实体信息
//				KnowledgeManage k = new KnowledgeManage();
////				k.setKnId(w.getKnowledgeId().getKnId());
//				k = daok.selectOne(k);
//				listcontent.add(k);
//			}
//		}
//		dao.deleteBatch(dellist);
//		int listrsize = listcontent.size();
//		int size = pageable.getPageSize();
//		int index = pageable.getPageIndex()-1;
//		int offset = pageable.getOffset();
//		int low = index*size> listrsize?listrsize:index*size;
//		int up = index*size+size>listrsize?listrsize:index*size+size;
//		Page<KnowledgeManage> page = new Page<KnowledgeManage>(pageable.getPageIndex(), pageable.getPageSize(), listcontent.size(), listcontent.subList(low, up));
//		return page;
		return null;
	}

}
