package com.chinacreator.c2.qyb.workflow.module.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.flow.detail.WfProcessDefinition;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.common.bean.DropDownListOption;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.sla.entity.ServiceAgreement;
import com.chinacreator.c2.qyb.workflow.sla.entity.SlaServiceRroductRel;
/**
 * 
 * @author qiao
 *
 */
@Service
public class ServiceProductService {
	/**
	 * RT
	 * @return
	 */
	public List<ServiceProduct> getAllServiceProduct(){
		Dao<ServiceProduct> dao = DaoFactory.create(ServiceProduct.class);
		return dao.selectAll();
	}
	/**
	 * RT
	 * @param path
	 * @return
	 */
	public List<DropDownListOption> getServiceProductIconNames(String path){
		List<DropDownListOption> list = new ArrayList<DropDownListOption>();
		if(path==null){
			path = "/assets/images/serviceproducticon";	
		}
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		String abspath = servletContext.getRealPath("/")+path;
		File file = new File(abspath);
		File[] files = file.listFiles();
		for(File f:files){
			DropDownListOption ddlo = new DropDownListOption();
			ddlo.setId(path+File.separator+f.getName());
			ddlo.setName(f.getName());
			list.add(ddlo);
		}
		return list;

	}
	/**
	 * RT
	 * @param key
	 * @return
	 */
	public List<ServiceProduct> getSpByProcessKey(String key){
		Dao<ServiceProduct> dao = DaoFactory.create(ServiceProduct.class);
		ServiceProduct con = new ServiceProduct();
		con.setWfProcessdefid(key);
		return dao.select(con);
	}
	/**
	 * RT
	 * @param productNo
	 * @return
	 */
	public ServiceProduct getServiceProductByNo(String productNo){
		Dao<ServiceProduct> dao = DaoFactory.create(ServiceProduct.class);
		ServiceProduct con = new ServiceProduct();
		con.setProductNo(productNo);
		return dao.selectOne(con);
	}
	/**
	 * RT
	 * @param sp
	 * @return
	 */
	public List<ServiceAgreement> getSLABySP(ServiceProduct sp){
		List<ServiceAgreement> result = new ArrayList<ServiceAgreement>();
		Dao<SlaServiceRroductRel> dao = DaoFactory.create(SlaServiceRroductRel.class);
		Dao<ServiceAgreement> daosla = DaoFactory.create(ServiceAgreement.class);
		SlaServiceRroductRel ssrr = new SlaServiceRroductRel();
		ssrr.setProductId(sp);
		List<SlaServiceRroductRel> list = dao.select(ssrr);
		for(SlaServiceRroductRel o:list){
//			ServiceAgreement sa = new ServiceAgreement();
//			sa.setServiceAgreementId(o.getSlaId());
//			result.add(daosla.selectByID(sa));
			if(o.getSlaId()!=null){
				result.add(o.getSlaId());
			}
		}
		return result;
	}
	/**
	 * RT
	 * @param serviceType
	 * @return
	 */
	
	public List<ServiceProduct> getSPByserviceType(String serviceType){
		Dao<ServiceProduct> dao = DaoFactory.create(ServiceProduct.class);
		ServiceProduct con = new ServiceProduct();
		con.setServiceTypeId(serviceType);
		return dao.select(con);
	}
	/**
	 * RT
	 * @param id
	 * @return
	 */
	public ServiceProduct getServiceProductById(String id){
		Dao<ServiceProduct> dao = DaoFactory.create(ServiceProduct.class);
		ServiceProduct con = new ServiceProduct();
		con.setProductId(id);
		return dao.selectByID(con);
	}
	/**
	 * 获取已经绑定了流程定义的服务产品
	 * @return
	 */
	public List<ServiceProduct> getAllServiceProductWithModule(ServiceProduct consp){
		List<ServiceProduct> listr = new ArrayList<ServiceProduct>();
		WorkFlowService sps = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
		Dao<ServiceProduct> dao = DaoFactory.create(ServiceProduct.class);
		List<ServiceProduct> list;
		if(consp==null){
			list = dao.selectAll();
		}else{
			list = dao.select(consp);
		}
		for(ServiceProduct sp:list){
//			Object o = sps.getBindProcessByModuleId(sp.getProductId());
			if(sps.getBindProcessByModuleId(sp.getProductId()).getId()!=null&&sp.getFormId()!=null){
				String ico=sp.getIco();
				sp.setIco(ico);
				listr.add(sp);
			}
		}
		return listr;
	}
	/**
	 * RT
	 * @param moduleids
	 * @return
	 */
	public Map<String,WfProcessDefinition> getWfProcessDefinitionByModuleIds(String[] moduleids){
		Map<String,WfProcessDefinition> map = new HashMap<String,WfProcessDefinition>();
		WorkFlowService sps = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
		if(moduleids==null){
			
			Dao<ServiceProduct> dao = DaoFactory.create(ServiceProduct.class);
			List<ServiceProduct> list =dao.selectAll();
			for(ServiceProduct sp:list){
//				Object o = sps.getBindProcessByModuleId(sp.getProductId());
				WfProcessDefinition wfp = sps.getBindProcessByModuleId(sp.getProductId());
				if(wfp != null && wfp.getId()!=null){
					map.put(sp.getProductId(), wfp);
				}
			}
		}else{
			for(String s:moduleids){
//				Object o = sps.getBindProcessByModuleId(sp.getProductId());
				WfProcessDefinition wfp = sps.getBindProcessByModuleId(s);
				if(wfp.getId()!=null){
					map.put(s, wfp);
				}
			}
		}
		return map;
	}
	/**
	 * 看此产品是否有未完成工单
	 * @param sp
	 * @return
	 */
	public ServiceProduct hasUnfinishedWorkByProduct(ServiceProduct sp){
		WorkFlowService sps = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
		if(sps.hasUnfinishedServiceProductWork(sp.getProductNo())){
			return null;
		}else{
			return sp;
		}
	}
}
