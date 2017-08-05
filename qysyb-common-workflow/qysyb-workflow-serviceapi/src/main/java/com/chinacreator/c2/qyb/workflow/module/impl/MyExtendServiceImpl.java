package com.chinacreator.c2.qyb.workflow.module.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.menu.service.MenuService;
import com.chinacreator.c2.flow.detail.WfModuleBean;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;
import com.chinacreator.c2.workflow.api.WfExtendService;

public class MyExtendServiceImpl implements WfExtendService {
	@Autowired
	ServiceProductService sps;
	final static String ROOTID = "rootid";
	final static String ROOTID_M = "rootidm";
	
	public static final String MODULE_MENU_CODE = "moduleConfig";

	@Autowired
	private MenuService menuService;	
	
	@Override
	public boolean existsChildren(String moduleId) {
		// TODO Auto-generated method stub
		if(moduleId.equals(MyExtendServiceImpl.ROOTID)){
			return true;
		}else{
			return menuService.existsChildMenus(moduleId);
		}	
	}

	@Override
	public boolean existsWfChildren(String moduleId) {
		if(moduleId.equals(MyExtendServiceImpl.ROOTID) || moduleId.equals(MyExtendServiceImpl.ROOTID_M)){
			return true;
		}else{
			MenuDTO mdto = menuService.queryByPK(moduleId);
			if(mdto == null){
				return true;
			}
			String href0 = mdto.getHref();
			if("4".equals(mdto.getType()) && StringUtils.isNotEmpty(href0) && href0.contains("wf/taskHandle")){
				return true;
			}
			List<MenuDTO> menuDTOList = menuService.queryChildren(moduleId, false);

			if (null != menuDTOList && !menuDTOList.isEmpty()) {
				for (MenuDTO menuDTO : menuDTOList) {
					if (null != menuDTO) {
						String type = menuDTO.getType();
						String href = menuDTO.getHref();
						WfModuleBean wmBean = new WfModuleBean();
						String mId = menuDTO.getMenuId();
						wmBean.setId(mId);
						wmBean.setName(menuDTO.getMenuName());
						wmBean.setParentId(menuDTO.getParentId());
						if(("4".equals(type) && StringUtils.isNotEmpty(href) && href.contains("wf/taskHandle"))){
							return true;
						}else if(("4".equals(type) && href == null)){
							boolean ret = existsWfChildren(mId);
							if(ret){
								return ret;
							}
						}
					}
				}
			}
			return false;			
		}

	}

	@Override
	public WfModuleBean queryByMenuCode(String menuCode) {
		// TODO Auto-generated method stub
		WfModuleBean vmb = new WfModuleBean();
		if(sps==null){
			sps = ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		}
		ServiceProduct sp = sps.getServiceProductById(menuCode);
		if(sp != null){
			vmb = new WfModuleBean();
			vmb.setId(menuCode);
			vmb.setName(sp.getProductName());
			vmb.setParentId(MyExtendServiceImpl.ROOTID);
			
			return vmb;			
		}else{
			MenuDTO menuDTO = menuService.queryByMenuCode(menuCode);
			WfModuleBean vmb1 = null;
			if (null != menuDTO) {
				vmb1 = new WfModuleBean();
				vmb1.setId(menuDTO.getMenuId());
				vmb1.setName(menuDTO.getMenuName());
				vmb1.setParentId(menuDTO.getParentId());
			}
			return vmb1;			
		}

	}

	@Override
	public List<WfModuleBean> queryChildren(String moduleId, boolean isRecursive) {
		if(sps==null){
			sps = ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		}
		List<WfModuleBean> listmb = new ArrayList<WfModuleBean>();
		if(moduleId.equals("0")){
			WfModuleBean wmBean = new WfModuleBean();
			wmBean.setId(MyExtendServiceImpl.ROOTID);
			wmBean.setName("自定义事项");
			wmBean.setParentId("0");
			listmb.add(wmBean);

//			MenuDTO menuDto = null;
//			String menuId = "";
//			menuDto = menuService.queryByMenuCode(MODULE_MENU_CODE);
//			menuId = menuDto.getMenuId();
			List<MenuDTO> menuDTOList = menuService.queryChildren("0", false);

			if (null != menuDTOList && !menuDTOList.isEmpty()) {
				for (MenuDTO menuDTO : menuDTOList) {
					if (null != menuDTO) {
						String type = menuDTO.getType();
						String href = menuDTO.getHref();
						WfModuleBean wmBean1 = new WfModuleBean();
						wmBean1.setId(menuDTO.getMenuId());
						wmBean1.setName(menuDTO.getMenuName());
						wmBean1.setParentId(menuDTO.getParentId());
						if(("4".equals(type) && StringUtils.isEmpty(href)) || ("4".equals(type) && href != null && href.contains("wf/taskHandle"))){
							listmb.add(wmBean1);
						}
					}
				}
			}

			return listmb;			
//			WfModuleBean mBean = new WfModuleBean();
//			mBean.setId(MyExtendServiceImpl.ROOTID_M);
//			mBean.setName("菜单事项");
//			mBean.setParentId("0");		
//			listmb.add(mBean);
		}else{
			if(moduleId.equals(MyExtendServiceImpl.ROOTID)){
				List<ServiceProduct> list = sps.getAllServiceProduct();
				for(ServiceProduct sp:list){
					WfModuleBean wmBean = new WfModuleBean();
					wmBean.setId(sp.getProductId());
					wmBean.setName(sp.getProductName());
					wmBean.setParentId(MyExtendServiceImpl.ROOTID);
					listmb.add(wmBean);
				}				
			}else{
				List<WfModuleBean> list = new ArrayList<WfModuleBean>();

				MenuDTO menuDto = null;

				String menuId = "";

				if (null == moduleId || "".equals(moduleId.trim())) {
					menuDto = menuService.queryByMenuCode(MODULE_MENU_CODE);
					menuId = menuDto.getMenuId();
				} else {
					menuId = moduleId;
				}
				
				List<MenuDTO> menuDTOList = menuService.queryChildren(menuId, false);

				if (null != menuDTOList && !menuDTOList.isEmpty()) {
					for (MenuDTO menuDTO : menuDTOList) {
						if (null != menuDTO) {
							String type = menuDTO.getType();
							String href = menuDTO.getHref();
							WfModuleBean wmBean = new WfModuleBean();
							wmBean.setId(menuDTO.getMenuId());
							wmBean.setName(menuDTO.getMenuName());
							wmBean.setParentId(menuDTO.getParentId());
							if(("4".equals(type) && StringUtils.isEmpty(href)) || ("4".equals(type) && href != null && href.contains("wf/taskHandle"))){
								list.add(wmBean);
							}
						}
					}
				}

				return list;			
			}

		}
		return listmb;
	}

}
