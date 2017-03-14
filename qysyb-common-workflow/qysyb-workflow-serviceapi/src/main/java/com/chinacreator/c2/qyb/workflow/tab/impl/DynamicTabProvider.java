package com.chinacreator.c2.qyb.workflow.tab.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.common.bean.TabDescriptionWithTabId;
import com.chinacreator.c2.qyb.workflow.config.entity.ActivityConfig;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.tab.entity.RelToTab;
import com.chinacreator.c2.qyb.workflow.tab.entity.Tab;
/**
 * 
 * @author l
 *
 */
public class DynamicTabProvider {
	/**
	 * 获取产品关联到的tab页 TabDescriptionWithTabId
	 * @param productId
	 * @param params
	 * @return
	 */
	public List<TabDescriptionWithTabId> generateProductTab(String productId,Map params){
		List<TabDescriptionWithTabId> tabList = new ArrayList<TabDescriptionWithTabId>();
		Dao<RelToTab> dao = DaoFactory.create(RelToTab.class);
		RelToTab rel = new RelToTab();
		rel.setBusinessKey(productId);
		List<RelToTab> list = dao.select(rel);
		for(RelToTab r:list){
			TabDescriptionWithTabId tab = new TabDescriptionWithTabId();
//			tab.setTitle(r.getTab().getTitle());
//			tab.setUrl(r.getTab().getUrl());
//			tab.setParams(params);
			tab.setTabId(r.getTab().getTabId());
			tabList.add(tab);
		}
		return tabList;
	}
	/**
	 * 获取产品关联到的tab页
	 * @param productId
	 * @return
	 */
	public List<Tab> getProductTab(String productId){
		List<Tab> result = new ArrayList<Tab>();
		Dao<RelToTab> dao = DaoFactory.create(RelToTab.class);
		RelToTab rel = new RelToTab();
		rel.setBusinessKey(productId);
		List<RelToTab> list = dao.select(rel);
		for(RelToTab r:list){
			result.add(r.getTab());
		}
		return result;
	}
	/**
	 * 获取任务环节的TAB标签
	 * @param productId
	 * @param params
	 * @param activityId
	 * @return
	 */
	public List<TabDescriptionWithTabId> generateProductTabForActivity(String productId,Map params,String activityId){
		List<TabDescriptionWithTabId> result = new ArrayList<TabDescriptionWithTabId>();
		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
		ActivityConfig ac = new ActivityConfig();
		ac.setTaskDefId(activityId);
		ac.setModuleId(productId);
		ActivityConfig config = daoac.selectOne(ac);
		if(config!=null){
			String idstr = config.getIncludeTabs();
			if(idstr!=null){
				String[] ids = idstr.split(",");
				List<TabDescriptionWithTabId> list = this.generateProductTab(productId, params);
				for(TabDescriptionWithTabId tab:list){
					String tabId = tab.getTabId();
					for(String s:ids){
						if(tabId.equals(s)){
							result.add(tab);
						}
					}
				}				
			}			
		}

		return result;
	}
	
	/**
	 * 获取查看页面的tab标签页
	 * @param moduleId
	 * @param params
	 * @param activityId
	 * @return
	 */
	public List<TabDescriptionWithTabId> generateProductTabForView(String moduleId,Map params,String activityId){	
		if(activityId == null){
			WorkFlowService wfs = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
			activityId = wfs.getEndActivityByModuleId(moduleId).getId();
			return generateProductTabForActivity(moduleId, params, activityId);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取查看页面的tab标签页
	 * @param productId
	 * @param params
	 * @param activityId
	 * @return
	 */
	public List<TabDescriptionWithTabId> generateProductTabForView(ServiceProduct serviceProduct,Map params,String activityId){
		WorkFlowService wfs = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
		List<TabDescriptionWithTabId> result = new ArrayList<TabDescriptionWithTabId>();
		Dao<ActivityConfig> daoac = DaoFactory.create(ActivityConfig.class);
		ActivityConfig ac = new ActivityConfig();
		ac.setTaskDefId(wfs.getEndActivityByModuleId(serviceProduct.getProductId()).getId());
		ac.setModuleId(serviceProduct.getProductId());
		ActivityConfig config = daoac.selectOne(ac);
		if(config!=null){
			String idstr = config.getIncludeTabs();
			if(idstr!=null){
				String[] ids = idstr.split(",");
				List<TabDescriptionWithTabId> list = this.generateProductTab(serviceProduct.getProductId(), params);
				for(TabDescriptionWithTabId tab:list){
					String tabId = tab.getTabId();
					for(String s:ids){
						if(tabId.equals(s)){
							result.add(tab);
						}
					}
				}				
			}			
		}

		return result;
	}

}
