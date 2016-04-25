package com.chinacreator.c2.omp.service.manage.workflowcommon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
//import com.chinacreator.c2.monitor.knowledge.entity.KnowledgeManage;
import com.chinacreator.c2.omp.service.manage.workflowcommon.WorkKnowledgeRel;
/**
 * 
 * @author qiao.li
 *
 */
@Service
public class WorkRelService {
	/**
	 * add知识工单关联
	 * @param kns
	 * @param workId
	 * @return
	 */
//	public int addWorkKnowledgeRel(KnowledgeManage[] kns,String workId){
//		Dao<WorkKnowledgeRel> dao = DaoFactory.create(WorkKnowledgeRel.class);
//		List<WorkKnowledgeRel> list = new ArrayList<WorkKnowledgeRel>();
//
//		for(KnowledgeManage s:kns){
//			WorkKnowledgeRel wkr = new WorkKnowledgeRel();
//			wkr.setWorkId(workId);
////			wkr.setKnowledgeId(s);
//			List<WorkKnowledgeRel> list1 = dao.select(wkr);
//			if((list1.size()==0)){
//				list.add(wkr);
//			}
//		}
//		return dao.insertBatch(list);
//	}
	/**
	 * 知识工单关联
	 * @param knIds
	 * @param workId
	 * @return
	 */
	public int addWorkKnowledgeRel(String[] knIds,String workId){
		Dao<WorkKnowledgeRel> dao = DaoFactory.create(WorkKnowledgeRel.class);
		List<WorkKnowledgeRel> list = new ArrayList<WorkKnowledgeRel>();

//		for(String s:knIds){
//			WorkKnowledgeRel wkr = new WorkKnowledgeRel();
//			KnowledgeManage wk = new KnowledgeManage();
//			wk.setKnId(s);
//			wkr.setWorkId(workId);
////			wkr.setKnowledgeId(wk);
//			List<WorkKnowledgeRel> list1 = dao.select(wkr);
//			if((list1.size()==0)){
//				list.add(wkr);
//			}
//		}
		return dao.insertBatch(list);
	}
	/**
	 * del知识工单关联
	 * @param kns
	 * @param workId
	 * @return
	 */
	public int delWorkKnowledgeRel(String[] kns,String workId){
		Dao<WorkKnowledgeRel> dao = DaoFactory.create(WorkKnowledgeRel.class);
		List<WorkKnowledgeRel> list = new ArrayList<WorkKnowledgeRel>();
//		for(String s:kns){
//			WorkKnowledgeRel wkr = new WorkKnowledgeRel();
//			wkr.setWorkId(workId);
//			KnowledgeManage km = new KnowledgeManage();
//			km.setKnId(s);
////			wkr.setKnowledgeId(km);
//			List<WorkKnowledgeRel> list1 = dao.select(wkr);
//			if((list1.size()>0)){
//				list.addAll(list1);
//			}
//		}
		return dao.deleteBatch(list);
	}
}
