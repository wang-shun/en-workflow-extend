package com.chinacreator.c2.omp.service.manage.workflowcommon.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.ioc.ApplicationContextManager;
//import com.chinacreator.c2.monitor.basic.entity.MonitorObject;
//import com.chinacreator.c2.monitor.basic.service.impl.MonitorObjectServiceImpl;
import com.chinacreator.c2.omp.common.DictDataInfo;
import com.chinacreator.c2.omp.common.service.CustomDictDataService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.service.WorkFlowStatisticService;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
@Service
public class WorkFlowStatisticServiceimpl implements WorkFlowStatisticService {
	WorkFlowService wfs;
	final static String SERVICETYPEDICTNAME = "服务分类";
	final static String TYPEFW  = "1";
	final static String TYPEZC  = "2";
	final static String TYPEBM  = "3";
	public WorkFlowService getWorkFlowService(){
		if(wfs==null){
			return ApplicationContextManager.getContext().getBean(WorkFlowService.class);
		}
		return wfs;
	}

	public CustomDictDataService getDictService(){
		return ApplicationContextManager.getContext().getBean(CustomDictDataService.class);
	}




	@Override
	public List<Map<String, Object>> getWorkHandleStatus(String serviceType, String priId,int offset,
			int limit, long startDate, long endDate) {
		// TODO Auto-generated method stub
		wfs = this.getWorkFlowService();
		Map map = new HashMap<String,String>();
		map.put(WorkFlowService.SERVICETYPEKEY, serviceType);
		map.put(WorkFlowService.PRIKEY, priId);
		return wfs.getWorkInsBySTS(null, map,startDate==-1?null:new Timestamp(startDate), endDate==-1?null:new Timestamp(endDate),offset, limit);
	}


	@Override
	public int getWorkHandleStatusNum(String serviceType,
			String priId, long startDate, long endDate) {
		// TODO Auto-generated method stub
		Map map = new HashMap<String,String>();
		map.put(WorkFlowService.SERVICETYPEKEY, serviceType);
		map.put(WorkFlowService.PRIKEY, priId);
		return (int)wfs.getWorkTotalBySTS(null, map,startDate==-1?null:new Timestamp(startDate), endDate==-1?null:new Timestamp(endDate)).get("total");
	}


	@Override
	public List<Map> getWorkCompleteStatus(String serviceType, int offset,
			int limit, long startDate, long endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	


	@Override
	public List<Map> getWorkClassStatistic(String classId,long startDate, long endDate) {
		List<Map> listresult = new ArrayList<Map>();
		wfs = this.getWorkFlowService();
		if(classId!=null){
			//按服务分类
			if(classId.equals(WorkFlowStatisticServiceimpl.TYPEFW)){
				Map<String, Integer> totalmap = wfs.getWorkTotalBySTS(null, null,startDate==-1?null:new Timestamp(startDate), endDate==-1?null:new Timestamp(endDate));
				int total = totalmap.get("total");
				List<DictDataInfo> list = this.getDictService().getDictName(WorkFlowStatisticServiceimpl.SERVICETYPEDICTNAME);
				for(DictDataInfo d:list){
					HashMap<String,Object> map1= new HashMap<String,Object>();
					map1.put("class", d.getDictdataValue());
					int num = wfs.getWorkTotalByST(d.getDictdataName(),null, startDate==-1?null:new Timestamp(startDate), endDate==-1?null:new Timestamp(endDate));
					map1.put("num", num);
					if(total==0){
						map1.put("percent",0);
					}else{
						map1.put("percent", new BigDecimal(num*100.00/total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					}
					listresult.add(map1);
				}
				return listresult;
				
//				Map<String, Integer> totalmap = wfs.getWorkTotalBySTS(null, null,startDate==-1?null:new Timestamp(startDate), endDate==-1?null:new Timestamp(endDate));
//				int total = totalmap.get("total");
//				List<DictData> list = this.getDictService().getDictDataList(WorkFlowStatisticServiceimpl.SERVICETYPEDICTNAME);
//				for(DictData d:list){
//					HashMap<String,Object> map1= new HashMap<String,Object>();
//					map1.put("class", d.getDictdataName());
//					int num = wfs.getWorkTotalByST(d.getDictdataName(), null,startDate==-1?null:new Timestamp(startDate), endDate==-1?null:new Timestamp(endDate));
//					map1.put("num", num);
//					map1.put("percent", num/total);
//					listresult.add(map1);
//				}
//				return listresult;
			//按请求部门分类
			}else if(classId.equals(WorkFlowStatisticServiceimpl.TYPEBM)){
				wfs = this.getWorkFlowService();
				Map map = wfs.getWorkBranchStatistic(null,startDate==-1?null:new Timestamp(startDate), endDate==-1?null:new Timestamp(endDate));
				int total = (int) map.get(WorkFlowService.TOTAL);
				map.remove("total");
				Set<Entry> set = map.entrySet();
				for(Entry e:set){
					HashMap<String,Object> map1= new HashMap<String,Object>();
					map1.put("class", e.getKey());
					map1.put("num", e.getValue());
					map1.put("percent", new BigDecimal(((int)e.getValue())*100.00/total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					listresult.add(map1);
				}
				return listresult;
			//按资产统计
//			}else if(classId.equals(WorkFlowStatisticServiceimpl.TYPEZC)){
//				MonitorObjectServiceImpl mos = new MonitorObjectServiceImpl();
//				Dao<WorkAssetRel> dao = DaoFactory.create(WorkAssetRel.class);
//				List<WorkAssetRel> list = dao.selectAll();
//				wfs = this.getWorkFlowService();
////				wfs.getHistoricVariableByIdKey(processInstanceId, key)
//				HashMap<String,Integer> map1= new HashMap<String,Integer>();
//				int total = 0;
//				for(WorkAssetRel war:list){
//					MonitorObject o = mos.getMonitorObjectById(war.getAssetId());
//					Map map = wfs.getWorkServiceByBK(war.getWorkId());
//					Timestamp oo =  map!=null?map.get("happenDate")==null?null:(Timestamp)map.get("happenDate"):null;
//					if(oo!=null&&oo.after(new Date(startDate))&&oo.before(new Date(endDate))){
//						if(map!=null&&o!=null){
//							if(map1.containsKey(o.getName())){
//								map1.put(o.getName(), map1.get(o.getName())+1);
//								total++;
//							}else{
//								map1.put(o.getName(), 1);
//								total++;
//							}							
//						}	
//					}
//					//时间未输入
//					if(startDate==-1||endDate==-1){
//						if(map!=null&&o!=null){
//							if(map1.containsKey(o.getName())){
//								map1.put(o.getName(), map1.get(o.getName())+1);
//								total++;
//							}else{
//								map1.put(o.getName(), 1);
//								total++;
//							}							
//						}	
//					}
//				}
//				for(Entry e:map1.entrySet()){
//					HashMap<String,Object> map2= new HashMap<String,Object>();
//					map2.put("class", e.getKey());
//					map2.put("num", e.getValue());
//					map2.put("percent", new BigDecimal(((int)e.getValue())*100.00/total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//					listresult.add(map2);
//				}
//				return listresult;
			}
		}
		return null;
	}






}
