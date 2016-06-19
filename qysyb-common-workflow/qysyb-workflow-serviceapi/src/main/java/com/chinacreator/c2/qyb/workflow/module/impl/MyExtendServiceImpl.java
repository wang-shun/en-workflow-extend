package com.chinacreator.c2.qyb.workflow.module.impl;

import java.util.ArrayList;
import java.util.List;

import com.chinacreator.c2.flow.detail.WfModuleBean;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;
import com.chinacreator.c2.workflow.api.WfExtendService;

public class MyExtendServiceImpl implements WfExtendService {
	ServiceProductService sps;
	final static String ROOTID = "saldifjlsaijdaljdlf";
	@Override
	public boolean existsChildren(String moduleId) {
		// TODO Auto-generated method stub
		if(moduleId.equals(MyExtendServiceImpl.ROOTID)){
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public boolean existsWfChildren(String moduleId) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public WfModuleBean queryByMenuCode(String menuCode) {
		// TODO Auto-generated method stub
		WfModuleBean vmb = new WfModuleBean();
		if(sps==null){
			sps = ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		}
		ServiceProduct sp = sps.getServiceProductById(menuCode);
		vmb = new WfModuleBean();
		vmb.setId(menuCode);
		vmb.setName(sp.getProductName());
		vmb.setParentId(MyExtendServiceImpl.ROOTID);
		return vmb;
	}

	@Override
	public List<WfModuleBean> queryChildren(String moduleId, boolean isRecursive) {
		// TODO Auto-generated method stub
		if(sps==null){
			sps = ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		}
		List<WfModuleBean> listmb = new ArrayList<WfModuleBean>();
		if(moduleId.equals("0")){
			WfModuleBean wmBean = new WfModuleBean();
			wmBean.setId(MyExtendServiceImpl.ROOTID);
			wmBean.setName("服务产品");
			wmBean.setParentId("0");
			listmb.add(wmBean);		
		}else{
			List<ServiceProduct> list = sps.getAllServiceProduct();
			for(ServiceProduct sp:list){
				WfModuleBean wmBean = new WfModuleBean();
				wmBean.setId(sp.getProductId());
				wmBean.setName(sp.getProductName());
				wmBean.setParentId(MyExtendServiceImpl.ROOTID);
				listmb.add(wmBean);
			}
		}
		return listmb;
	}

}
