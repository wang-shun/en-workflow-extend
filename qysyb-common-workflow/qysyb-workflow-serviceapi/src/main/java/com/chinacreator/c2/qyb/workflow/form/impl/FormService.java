package com.chinacreator.c2.qyb.workflow.form.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.DictDataInfo;
import com.chinacreator.c2.omp.common.DictTypeInfo;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.activiti.inf.IFormOperate;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.form.entity.Form;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.form.entity.FormFieldRel;
import com.chinacreator.c2.qyb.workflow.form.entity.FormFieldValue;
import com.chinacreator.c2.qyb.workflow.form.entity.WebDisplayCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 表单服务接口
 * 
 * @author qiao.li
 * @version 1.0
 * @date 2015-8-18
 */
//此处和工作流的 formservice 冲突了，注意。以后要用工作流的formservice得使用完全限定
@Service("FormService")
public class FormService {
	
	public final static String TYPESTRING = "STR";
	public final static String TYPEINTEGER = "INT";
	public final static String TYPETIME = "TIME";
	public final static String WFFIELDTYPEDICTNAME = "工作流_字段类别";
	/**
	 * 
	 * @return
	 */
	public List<Form> getAllForm(){
		Dao<Form> dao = DaoFactory.create(Form.class);
		return dao.selectAll();
	}
	/**
	 * RT
	 * 
	 * @param formId
	 * @param fieldIds
	 * @return
	 */
	public int addFormFieldRel(String fieldType,String formId,String[] fieldIds){
		Dao<FormFieldRel> dao = DaoFactory.create(FormFieldRel.class);
		List<FormFieldRel> listffr = new ArrayList<FormFieldRel>();
		Form f = new Form();
		f.setFormId(formId);
		for(String id:fieldIds){
			FormField ff = new FormField();
			ff.setFieldId(id);
			FormFieldRel ffr = new FormFieldRel();
			ffr.setFieldId(ff);
			ffr.setFormId(f);
			ffr.setCategotyId(fieldType);
			List<FormFieldRel> list = dao.select(ffr);
			if(list!=null&&list.size()==0){
				listffr.add(ffr);
			}
		}
		return dao.insertBatch(listffr);
	}
	/**
	 * RT
	 * @param formId
	 * @param fieldIds
	 * @return
	 */
	public int delFormFieldRel(String formId,String[] fieldIds){
		Dao<FormFieldRel> dao = DaoFactory.create(FormFieldRel.class);
		List<FormFieldRel> listffr = new ArrayList<FormFieldRel>();
		
		Form f = new Form();
		f.setFormId(formId);
		for(String fieldId:fieldIds){
			FormField ff = new FormField();
			ff.setFieldId(fieldId);
			FormFieldRel ffr = new FormFieldRel();
			ffr.setFieldId(ff);
			ffr.setFormId(f);
			listffr.addAll(dao.select(ffr));
		}
		return dao.deleteBatch(listffr);
	}
	//把表单的值筛选出来
	public Map<String,Object> filterFormDataFormMapValue(String formId,Map<String,Object> values){
		if(formId==null){
			formId = (String) values.get(WorkFlowService.FORMIDSTR);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<FormField> fields = this.getFormField(formId);
		for(FormField formFiled:fields){
			String fieldNo = formFiled.getFieldNo();
			if(values.containsKey(fieldNo)){
				map.put(fieldNo, values.get(fieldNo));
			}
		}
		return map;
	}
	
	/**
	 * 从流程变量或外部表中取得业务数据
	 * @param formId 如果是外部表 则是必需参数
	 * @param filter
	 * @param businessKey
	 * @param proInsId
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getFormDataByFkFromProcessVariable(String formId,Boolean filter,String businessKey,String proInsId){
 		Map con = new HashMap();
		Map map = new HashMap();
		//此时表示需要一份空的数据
		if(businessKey==null&&proInsId==null){
			List<FormField> listff = this.getFormField(formId);
			for(FormField o:listff){
				if(o.isIsObject()!=null&&o.isIsObject()){
					map.put(o.getFieldNo(), this.getFormDataByFk(o.getObjectClass(), businessKey));
				}else{
					map.put(o.getFieldNo(), "");
				}
			}
			return map;			
		}
		
		Form form = this.getFormById(formId);

		if(proInsId!=null&&!proInsId.equals("null")&&!proInsId.equals("undefined")){
			//业务数据存储外部表单
			if(form!=null&&form.isIsTableStorage()!=null&&form.isIsTableStorage()){
				RuntimeService runtimeService = ApplicationContextManager.getContext().getBean(RuntimeService.class);
				ProcessInstanceQuery  piq = runtimeService.createProcessInstanceQuery();
				ProcessInstance proins = piq.processInstanceId(proInsId).includeProcessVariables().singleResult();
				if(proins!=null){//这里如果为null的话 应该是已结束的实例了。那么流程变量这里是没有拿出来了。Q 这里需不需要把流程变量返回出去呢					
					map = proins.getProcessVariables();  //流程变量也给返回出去
				} else { //结束的实例流程变量的查询
					HistoryService historyService = ApplicationContextManager.getContext().getBean(HistoryService.class);					
					HistoricProcessInstance hisins = historyService.createHistoricProcessInstanceQuery().includeProcessVariables()
							.processInstanceId(proInsId).singleResult();		
					if(hisins!=null){
						map = hisins.getProcessVariables();
					}					
				}
				
 				Map busMap = this.getFormDataWithExternalTable(businessKey,
 						proInsId, null, form,con); //业务表中存流程实例				if(busMap!=null)
					map.putAll(busMap);//业务数据
				return map;
			}
			//之前没拿过流程变量
			if(map.size()==0){
				RuntimeService runtimeService = ApplicationContextManager.getContext().getBean(RuntimeService.class);
				ProcessInstanceQuery  piq = runtimeService.createProcessInstanceQuery();
				ProcessInstance proins = piq.processInstanceId(proInsId).includeProcessVariables().singleResult();
				map = proins.getProcessVariables();				
			}

			if(formId==null){
				formId = (String) map.get(WorkFlowService.FORMIDSTR);
			}
			if(filter!=null&&filter.equals(true)){
				return this.filterFormDataFormMapValue(formId, map);
			}else{
				return map;
			}
			
		}		
		/*没有流程实例 则是取历史数据*/
		if(businessKey!=null){
			/**
			 * 这里不应该要去取历史流程实例数据。只有businessKey 那么就是认为从一个草稿发起流程
			 */
//			HistoryService historyService = ApplicationContextManager.getContext().getBean(HistoryService.class);
//			HistoricProcessInstance hisins = historyService.createHistoricProcessInstanceQuery().includeProcessVariables()
//					.processInstanceBusinessKey(businessKey).singleResult();
//			Map<String,Object> hismap = null;
//			//如果没有历史实例，说明是个草稿！！！
//			if(hisins!=null){
//				hismap = hisins.getProcessVariables();
//				map.putAll(hismap);
//				if(formId==null){
//					formId = (String) hismap.get(WorkFlowService.FORMIDSTR);
//				}
//			}
			
			form=this.getFormById(formId);
			if(form!=null&&form.isIsTableStorage()!=null&&form.isIsTableStorage()){  //外部表单存储业务数据时获取历史数据
//				String hisproInsId = hisins==null?businessKey:hisins.getId()
				String hisproInsId = null;
 				Map busMap = this.getFormDataWithExternalTable(businessKey,
 						hisproInsId, null, form,con);				
 				if(busMap!=null)
					map.putAll(busMap);
				return map;
			}
			if(filter!=null&&filter ==  true){
				return this.filterFormDataFormMapValue(formId, map);
			}else{
				return map;
			}
		}
		return null;
	}
	//保存流程变量 一般是作为业务数据的流程变量
	public boolean saveProcessInsVariable(String taskId,String key,java.lang.Object values){
		RuntimeService runtimeService = ApplicationContextManager.getContext().getBean(RuntimeService.class);
		TaskService taskService = ApplicationContextManager.getContext().getBean(TaskService.class);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//		runtimeService.setVariables(task.getExecutionId(), values);
		
		runtimeService.setVariable(task.getExecutionId(), key, values);
		return true;
	}
	/**
	 * 从自己的值表中取得业务数据
	 * @param formId
	 * @param businessKey
	 * @return
	 */
	public Map<String,Object> getFormDataByFk(String formId, String businessKey) {

		// TODO Auto-generated method stub
//		System.out.println(formId+"---liqiao---"+businessKey);
		Map<String,Object> map = new HashMap<String,Object>();
		if(formId==null){
			return map;
		}
		if(businessKey==null){
			List<FormField> listff = this.getFormField(formId);
			for(FormField o:listff){
				if(o.isIsObject()!=null&&o.isIsObject()){
					map.put(o.getFieldNo(), this.getFormDataByFk(o.getObjectClass(), businessKey));
				}else{
					map.put(o.getFieldNo(), "");
				}
			}
			return map;
		}
		
		Dao<FormFieldValue> daoffv = DaoFactory.create(FormFieldValue.class); 
		FormFieldValue ffv = new FormFieldValue();
		ffv.setFormBusinessKey(businessKey);
		Form form = new Form();
		form.setFormId(formId);
		ffv.setFormId(form);
		List<FormFieldValue> listffv = daoffv.select(ffv);

		for(FormFieldValue o:listffv){
			FormField ff = o.getFieldId();
			String type = ff.getFieldType();
			if(ff.isIsObject()!=null&&ff.isIsObject()&&o.getFieldValue()!=null){
				map.put(ff.getFieldNo(), this.getFormDataByFk(ff.getObjectClass(), o.getFieldValue()));
			}else if(ff.isIsObject()!=null&&ff.isIsObject()&&o.getFieldValue()==null){
				map.put(ff.getFieldNo(), this.getFormDataByFk(ff.getObjectClass(),businessKey));
			}else if(type==null||type.equals(FormService.TYPESTRING)){
				ffv.setFieldValue((String) map.get(ff.getFieldNo()));
				map.put(ff.getFieldNo(),o.getFieldValue());
			}else if(type.equals(FormService.TYPEINTEGER)){
				map.put(ff.getFieldNo(),o.getFieldValueInt());
			}else if(type.equals(FormService.TYPETIME)){
				map.put(ff.getFieldNo(),o.getFieldValueTimestamp());
			}
		}
		return map;
	}
	/**
	 * 
	 * @param formId
	 * @param businessKey
	 * @param map
	 * @return
	 */
	
	public int insertFormDataByFK(String formId, String businessKey,Map map) {
		// TODO Auto-generated method stub
		Dao<FormFieldValue> daoffv = DaoFactory.create(FormFieldValue.class); 
		Dao<FormField> daoff = DaoFactory.create(FormField.class);
		Dao<FormFieldRel> daoffr = DaoFactory.create(FormFieldRel.class);
		List<FormFieldValue> listffv = new ArrayList<FormFieldValue>();
		Form f = new Form();
		f.setFormId(formId);
		List<FormField> listff = this.getFormField(formId);
		for(FormField o:listff){
			FormFieldValue ffv = new FormFieldValue();
			ffv.setFormId(f);
			ffv.setFormBusinessKey(businessKey);
			ffv.setFieldId(o);
			String type = o.getFieldType();
			String fieldNo = o.getFieldNo();
			if(o.isIsObject()!=null&&o.isIsObject()){
				String formId1 = o.getObjectClass();
				Map ovalue = (Map)(map.get(o.getFieldNo()));
				String id = (String) ovalue.get("Id");
				if(id==null){
					insertFormDataByFK(formId1,businessKey,ovalue);
					listffv.add(ffv);
				}else{
					insertFormDataByFK(formId1,id,ovalue);
					ffv.setFieldValue(id);
					listffv.add(ffv);
				}
			}else if(type==null||type.equals(FormService.TYPESTRING)){
				//此处会插入空字符串 
				if(map.get(fieldNo)!=null&&!map.get(fieldNo).equals("")){
					ffv.setFieldValue(map.get(o.getFieldNo()).toString());
					listffv.add(ffv);
				}
			}else if(type.equals(FormService.TYPEINTEGER)){
				Object ob = map.get(o.getFieldNo());
				if(ob!=null&&!ob.equals("")){
					ffv.setFieldValueInt( Integer.valueOf((String)ob));
					listffv.add(ffv);
				}		
			}else if(type.equals(FormService.TYPETIME)){
				Object ob = map.get(o.getFieldNo());
				if(ob!=null&&!ob.equals("")){
					Long l;
					if(ob instanceof String){
						l = Long.valueOf((String) ob);
					}else{
						l = (Long) ob;
					}
					ffv.setFieldValueTimestamp(new Date(l));
					listffv.add(ffv);
				}
			}
			
		}
		return daoffv.insertBatch(listffv);
	}
	/**
	 * 获取表单字段
	 * @param formId
	 * @return
	 */
	public List<FormField> getFormField(String formId){
		Dao<FormFieldRel> daoffr = DaoFactory.create(FormFieldRel.class);
		List<FormField> list = new ArrayList<FormField>();
		Form f = new Form();
		f.setFormId(formId);
		FormFieldRel ffr = new FormFieldRel();
		ffr.setFormId(f);
		List<FormFieldRel> listffr = daoffr.select(ffr);
		for(FormFieldRel o:listffr){
			if(o.getFieldId()!=null)
				list.add(o.getFieldId());
		}
		return list;
	}
	/**
	 * 获取字段 以及字段的配置 如果 formfieldrel 里面有自定义的配置 优先使用 formfieldrel里面的配置
	 * @param formId
	 * @param fieldType
	 * @param isClassify 是否按字段类别分类
	 * @return
	 */
	public Map<String,List<Map>> getFormField(String formId,String[] fieldType,boolean isClassify){
		Map<String,List<Map>> mapresult = new HashMap<String,List<Map>>();
		Dao<FormFieldRel> daoffr = DaoFactory.create(FormFieldRel.class);
		Form f = new Form();
		f.setFormId(formId);
		if(!isClassify){
			FormFieldRel ffr = new FormFieldRel();
			ffr.setFormId(f);
			List<FormFieldRel> listffr = daoffr.select(ffr);	
			List<Map> list = new ArrayList<Map>();
			for(FormFieldRel o:listffr){
				if(o.getFieldId()!=null){
					Map map = assembleFieldConfig(o);					
					list.add(map);
				}		
			}
			if(list.size()>0){
				mapresult.put("all", list);
			}
			return mapresult;
		}
		if(fieldType==null){
			Dao<DictDataInfo> daod = DaoFactory.create(DictDataInfo.class);
			Dao<DictTypeInfo> daot = DaoFactory.create(DictTypeInfo.class);
			DictTypeInfo cont = new DictTypeInfo();
			DictDataInfo cond = new DictDataInfo();
			cont.setDicttypeName(FormService.WFFIELDTYPEDICTNAME);
			cont = daot.selectOne(cont);
			cond.setDicttypeId(cont);
			List<DictDataInfo> listd = daod.select(cond);
			for(DictDataInfo d:listd){
				FormFieldRel ffr = new FormFieldRel();
				ffr.setFormId(f);
				ffr.setCategotyId(d.getDictdataName());
				List<FormFieldRel> listffr = daoffr.select(ffr);
				List<Map> list = new ArrayList<Map>();
				for(FormFieldRel o:listffr){
					if(o.getFieldId()!=null){
						Map map = assembleFieldConfig(o);				
						list.add(map);
					}		
				}
				if(list.size()>0){
					mapresult.put(d.getDictdataName(), list);
				}			
			}
		}else{
			for(String d:fieldType){
				FormFieldRel ffr = new FormFieldRel();
				ffr.setFormId(f);
				ffr.setCategotyId(d);
				List<FormFieldRel> listffr = daoffr.select(ffr);
				List<Map> list = new ArrayList<Map>();
				for(FormFieldRel o:listffr){
					if(o.getFieldId()!=null){
						Map map = assembleFieldConfig(o);				
						list.add(map);
					}		
				}
				if(list.size()>0){
					mapresult.put(d, list);
				}		
			}
		}
		return mapresult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map assembleFieldConfig(FormFieldRel o){
		Map map = new HashMap();
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> props = m.convertValue(o.getFieldId(), Map.class);
		if(props != null){
			map.putAll(props);
		}
		Map<String,Object> relprops = m.convertValue(o, Map.class);		
		if(relprops != null){
			Map relpropstrimed = removeNullInMap(relprops);
			map.putAll(relpropstrimed);
		}	
		return map;
	}
	/**
	 * 
	 * @param formId
	 * @param businessKey
	 * @param map
	 * @return
	 */
	public int updateFormDataByFk(String formId, String businessKey,Map map){
		List<FormField> listff = this.getFormField(formId);	
		List<FormFieldValue> listffv = new ArrayList<FormFieldValue>();
		List<FormFieldValue> listffvinsert = new ArrayList<FormFieldValue>();
		Dao<FormFieldValue> daoffv = DaoFactory.create(FormFieldValue.class); 
		Form f = new Form();
		f.setFormId(formId);
		listff = this.getFormField(formId);
		for(FormField o:listff){
			FormFieldValue ffvv = new FormFieldValue();
			ffvv.setFormId(f);
			ffvv.setFormBusinessKey(businessKey);
			ffvv.setFieldId(o);
			FormFieldValue ffv = daoffv.selectOne(ffvv);

			String type = o.getFieldType();
			if(o.isIsObject()!=null&&o.isIsObject()){
				String formId1 = o.getObjectClass();
				if(map.get(o.getFieldNo())!=null){
					Map ovalue = (Map)(map.get(o.getFieldNo()));
					String id = null;
					if(ffv!=null){	
						id = ffv.getFieldValue();
//						listffv.add(ffv);
					}else{
						ffv = ffvv;
						listffvinsert.add(ffv);
					}
					if(id==null){
						updateFormDataByFk(formId1,businessKey,ovalue);
					}else{
						updateFormDataByFk(formId1,id,ovalue);
					}
				}
			}else{
				Object ob = map.get(o.getFieldNo());
				if(ob!=null){
					//插入的话 不插入空字符串 update 则需要更新空字符串 因为可能有人把字段值给清了
					if(ffv==null&&!ob.equals("")){
						ffv = ffvv;
						listffvinsert.add(ffv);
					}else if(ffv!=null){
						listffv.add(ffv);
					}
					if(ffv!=null){
						if(type==null||type.equals(FormService.TYPESTRING)){
							//插入的话 不插入空字符串 update 则需要更新空字符串 因为可能有人把字段值给清了
							ffv.setFieldValue(ob.toString());
						}else if(type.equals(FormService.TYPEINTEGER)){
							ffv.setFieldValueInt( Integer.valueOf((String)ob));
						}else if(type.equals(FormService.TYPETIME)){
							Long l;
							if(ob instanceof String){
								l = Long.valueOf((String) ob);
							}else{
								l = (Long) ob;
							}
							ffv.setFieldValueTimestamp(new Date(l));
						}					
					}
				}else{
					//表示前端删除了这个字段内容 update
					if(ffv!=null){
						ffv.setFieldValue(null);
						ffv.setFieldValueInt(null);
						ffv.setFieldValueTimestamp(null);
						listffv.add(ffv);
					}
				}
			}		
		}
		return daoffv.insertBatch(listffvinsert)+daoffv.updateBatch(listffv);
	}
	/**
	 * map形式返回所有的form
	 * @return
	 */
	public Map<String,Object> getAllFormMap(){
		Dao<Form> dao = DaoFactory.create(Form.class);
		List<Form> list = dao.selectAll();
		Map<String,Object> map = new HashMap<String,Object>();
		for(Form f:list){
			map.put(f.getFormId(), f.getFormName());
		}
		return map;
	}
	/**get record by primary key
	 * 
	 * @param formId
	 * @return
	 */
	public Form getFormById(String formId){
		Dao<Form> dao = DaoFactory.create(Form.class);
		Form form = new Form();
		form.setFormId(formId);
		return dao.selectByID(form);
	}
	/**
	 * 业务数据有自己的表时业务数据的更新
	 * @param businessKey 
	 * @param procInsId 这里是流程实例id 业务表吧流程实例id作为业务key
	 * @param entityjson
	 * @param form
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
 	public void updateFormDataWithExternalTable(String businessKey,
 			String procInsId,String entityjson,WorkFlowActivity curActivity,
 			Form form,String curUserId,WorkFlowActivity nextActivity, Map map)
 					throws Exception{
		String moduleId = (String) map.get("moduleId");
		//表示需要查数据库
		if(form.getFormNo()==null){
			form = this.getFormById((form.getFormId()));
		}	
		String beanName = form.getOperateBean();
		IFormOperate formOperate = 
				(IFormOperate) ApplicationContextManager.getContext()
				.getBean(beanName);
		//TODO 把null值改掉
 		formOperate.addOrUpdateEntity(entityjson,businessKey,procInsId, 
 				moduleId, curActivity, curUserId, nextActivity, map);	}
	/**
	 * 务数据有自己的表时业务数据的获取
	 * @param businessKey 这里是流程实例id 业务表吧流程实例id作为业务key
	 * @param businessKey2
	 * @param form
	 * @return
	 */
	@SuppressWarnings("rawtypes")
 	public Map<String,Object> getFormDataWithExternalTable(String businessKey,
 			String proInsId,
 			String businessKey2,Form form,Map con){
		form = this.getFormById((form.getFormId()));
		String beanName = form.getOperateBean();
		IFormOperate formOperate = 
				(IFormOperate) ApplicationContextManager.getContext()
				.getBean(beanName);
		//TODO 把null值 改掉
 		Map map = formOperate.getEntityByBusinessKey(businessKey,proInsId,
 				null, null, null, con);		return map;
	}
	/**
	 * RT
	 * @return
	 */
	public Map<String,Object> getAllWebDisplayCategoryMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<WebDisplayCategory> list = this.getAllWebDisplayCategoryList();
		for(WebDisplayCategory wd:list){
			map.put(wd.getCategoryNo(), wd);
		}
		return map;
	}
	/**
	 * RT
	 * @return
	 */
	public List<WebDisplayCategory> getAllWebDisplayCategoryList(){
		Dao<WebDisplayCategory> dao = DaoFactory.create(WebDisplayCategory.class);
		List<WebDisplayCategory> list = dao.selectAll();	
		return list;
	}
	/**
	 * 去掉map中的 null 空字符串
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map removeNullInMap(Map map){
		Map result = new HashMap();
		Set<String> set = map.keySet();
		for(String key:set){
			Object o = map.get(key);
			if(o == null){
				continue;
			}
			if(o instanceof String && ((String) o).trim().equals("")){
				continue;
			}
			result.put(key, o);
		}
		return result;
	}
	/**
	 * 
	 * @param page
	 * @param rowNum
	 * @param ids
	 */
	public void sortFormFieldRel(int page,int rowNum,String[] ids){
		Dao<FormFieldRel> dao = DaoFactory.create(FormFieldRel.class);
		if (null != ids && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				FormFieldRel rel = new FormFieldRel();
				rel.setRelId(ids[i]);
				rel.setRorder((double) ((page - 1) * rowNum + i));
				dao.update(rel);
			}
		}		
	}
}
