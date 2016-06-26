package com.chinacreator.c2.qyb.workflow.workrelwork.service;

import org.springframework.stereotype.Service;
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

}
