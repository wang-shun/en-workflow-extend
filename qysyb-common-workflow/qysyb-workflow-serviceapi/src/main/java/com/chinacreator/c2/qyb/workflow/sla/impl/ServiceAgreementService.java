package com.chinacreator.c2.qyb.workflow.sla.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;
import com.chinacreator.c2.qyb.workflow.sla.entity.ServiceAgreement;
import com.chinacreator.c2.qyb.workflow.sla.entity.ServiceProductUCRel;
import com.chinacreator.c2.qyb.workflow.sla.entity.SlaServiceRroductRel;
import com.chinacreator.c2.qyb.workflow.sla.entity.Sla_kpi;
import com.chinacreator.c2.qyb.workflow.sla.entity.Sla_kpi_rel;
import com.chinacreator.c2.qyb.workflow.sla.entity.UnderpinContract;

/**
 * 
 * @author qiao
 *
 */
@Service
public class ServiceAgreementService {
	/**
	 * 
	 * @param kpis
	 * @param slaId
	 */
	public void addSLAKpiRel(Sla_kpi[] kpis,String slaId){
		Dao<Sla_kpi_rel> dao = DaoFactory.create(Sla_kpi_rel.class);
		for(Sla_kpi kpi:kpis){
			Sla_kpi_rel skr = new Sla_kpi_rel();
			skr.setServiceAgreementId(slaId);
			skr.setKpiId(kpi);
			if(dao.select(skr).size()==0){
				skr.setVlaue(kpi.getDefaultValue());
				dao.insert(skr);
			}
		}
	}
	/**
	 * 
	 * @param skrs
	 */
	public void saveSLAKpiRel(Sla_kpi_rel[] skrs){
		Dao<Sla_kpi_rel> dao = DaoFactory.create(Sla_kpi_rel.class);
		for(Sla_kpi_rel skr:skrs){
			dao.update(skr);
		}
	}
	/**
	 * 
	 * @param spIds
	 * @param slaId
	 */
	public void saveSlaServiceProductRel(String[] spIds,String slaId){
		Dao<SlaServiceRroductRel> dao = DaoFactory.create(SlaServiceRroductRel.class);
		for(String s:spIds){
			SlaServiceRroductRel ssrr = new SlaServiceRroductRel();
			ServiceProduct sp = new ServiceProduct();
			sp.setProductId(s);
			ssrr.setProductId(sp);
			ServiceAgreement sla = new ServiceAgreement();
			sla.setServiceAgreementId(slaId);
			ssrr.setSlaId(sla);
			if(dao.select(ssrr).size()==0){
				dao.insert(ssrr);				
			}
		}
	}
	/**
	 * RT
	 * @param sla
	 * @return Sla_kpi_rel这个对象里面才会带真正的value值 所以返回这个
	 */
	public List<Sla_kpi_rel> getKPIBySla(ServiceAgreement sla){
		Dao<Sla_kpi_rel> dao = DaoFactory.create(Sla_kpi_rel.class);
		Sla_kpi_rel skr = new Sla_kpi_rel();
		skr.setServiceAgreementId(sla.getServiceAgreementId());
		List<Sla_kpi_rel> listkpi = dao.select(skr);
		return listkpi;
	}
	/**
	 * RT
	 * @param kpiNo
	 * @param sla
	 * @return
	 */
	public int getKpiValueBySla(String kpiNo,ServiceAgreement sla){
		List<Sla_kpi_rel> list = this.getKPIBySla(sla);
		for(Sla_kpi_rel o:list){
			if(o.getKpiId().getKpiNo()!=null&&o.getKpiId().getKpiNo().equals(kpiNo)){
				return Integer.valueOf(o.getVlaue());
			}
		}
		return -1;
	}
	/**
	 * 按服务产品编码获取生效服务级别协议
	 * @param serviceProductNo
	 * @return
	 */
	public ServiceAgreement getTheWorkedSLAByProductNo(String serviceProductNo){
		ServiceProductService sps = ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		ServiceProduct sp = new ServiceProduct();
		sp = sps.getServiceProductByNo(serviceProductNo);
		if(sp!=null){
			List<ServiceAgreement> listsla = sps.getSLABySP(sp);
			return this.chooseAServiceAgreement(listsla);
		}
		return null;
		
	} 
	/**
	 * 选一个SLA if more than one
	 * @param list
	 * @return
	 */
	public ServiceAgreement chooseAServiceAgreement(List<ServiceAgreement> list){
		for(ServiceAgreement sa:list){
			long curl = System.currentTimeMillis();
			//暂未 根据优先级选择sla
			if(sa.getStartDate().getTime()<curl&&sa.getEndDate().getTime()>curl){
				return sa;
			}
		}
		return null;
	}
	/**
	 * 
	 * @param ucId
	 * @param spIds
	 * @return
	 */
	public int addServiceProductUCRel(String ucId,String[] spIds){
		Dao<ServiceProductUCRel> dao = DaoFactory.create(ServiceProductUCRel.class);
		List<ServiceProductUCRel> list = new ArrayList<ServiceProductUCRel>();
		UnderpinContract uc = new UnderpinContract();
		uc.setUcId(ucId);
		for(String s:spIds){
			ServiceProductUCRel spucr = new ServiceProductUCRel();
			spucr.setUcId(uc);
			ServiceProduct sp = new ServiceProduct();
			sp.setProductId(s);
			spucr.setProductId(sp);
			if((dao.select(spucr).size()==0)){
				list.add(spucr);
			}	
		}
		return dao.insertBatch(list);
	}
	/**
	 * 返回服务产品关联的UC信息
	 * @param productNo
	 * @return
	 */
	public List<ServiceProductUCRel> getUCByServiceProductNo(String productNo){
		List<ServiceProductUCRel> result = new ArrayList<ServiceProductUCRel>();
		//uc已被删除但是关联表中还有 需要记录下来并删除
		List<ServiceProductUCRel> dellist = new ArrayList<ServiceProductUCRel>();
		ServiceProductService sps = ApplicationContextManager.getContext().getBean(ServiceProductService.class);
		ServiceProduct sp = new ServiceProduct();
		sp = sps.getServiceProductByNo(productNo);
		Dao<ServiceProductUCRel> dao = DaoFactory.create(ServiceProductUCRel.class);
		ServiceProductUCRel spur = new ServiceProductUCRel();
		spur.setProductId(sp);
		List<ServiceProductUCRel> list = dao.select(spur);
		for(ServiceProductUCRel l:list){
			if(l.getUcId()==null){
				dellist.add(l);
			}else{
				result.add(l);
			}
		}
		dao.deleteBatch(dellist);
		return result;
	}
}
