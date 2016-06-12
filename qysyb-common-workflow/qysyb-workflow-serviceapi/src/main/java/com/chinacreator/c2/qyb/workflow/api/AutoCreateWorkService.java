package com.chinacreator.c2.qyb.workflow.api;

import java.util.Map;

import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.util.UUIDUtil;

public class AutoCreateWorkService {
	/**
	 * 产生一个用于工单的已经关联好业务数据和关联数据的bisinessKey。启动流程时传入返回的key
	 * @param sp
	 * @param map
	 * @param resourseIds
	 * @param workIds
	 * @param knowledgesIds
	 * @return
	 */
	public String createWork(ServiceProduct sp,Map map,String[] resourseIds,String[] workIds,String[] knowledgesIds){
		FormService formService = ApplicationContextManager.getContext().getBean(FormService.class);
		WorkFlowService wfs = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
	//	WorkRelService wrs = ApplicationContextManager.getContext().getBean(WorkRelService.class);
		String businessKey = UUIDUtil.createUUID();
		int i = formService.updateFormDataByFk(sp.getFormId(), businessKey, map);
		if(i>0){
/*			if(resourseIds!=null)
				wfs.addWorkAssetRel(businessKey, null, resourseIds);
			if(workIds!=null)
				wfs.addWorkRel(businessKey, workIds);*/
			if(knowledgesIds!=null)
		//		wrs.addWorkKnowledgeRel(knowledgesIds, businessKey);
			return businessKey;
		}
		return null;
	}
}
