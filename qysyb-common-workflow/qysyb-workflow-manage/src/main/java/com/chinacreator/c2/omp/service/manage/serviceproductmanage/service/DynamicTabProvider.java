package com.chinacreator.c2.omp.service.manage.serviceproductmanage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.omp.common.RelToTab;
import com.chinacreator.c2.omp.common.Tab;
import com.chinacreator.c2.omp.common.bean.TabDescriptionWithTabId;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.ActivityConfig;
import com.chinacreator.c2.ui.beans.TabDescription;
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
			tab.setTitle(r.getTab().getTitle());
			tab.setUrl(r.getTab().getUrl());
			tab.setParams(params);
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
		ac.setId(activityId);
		ActivityConfig config = daoac.selectOne(ac);
		if(config!=null){
			String idstr = config.getRemark1();
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

}
