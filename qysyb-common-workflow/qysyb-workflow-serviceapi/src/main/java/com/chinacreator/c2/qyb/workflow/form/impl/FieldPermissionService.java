package com.chinacreator.c2.qyb.workflow.form.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.form.entity.FieldPermission;
import com.chinacreator.c2.qyb.workflow.form.entity.Form;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;

@Service
public class FieldPermissionService {
	public final static String READPERMISSIONKEY = "readPermission";
	public final static String WRITEPERMISSIONKEY = "writePermission";
	public final static String VISIBLEKEY = "visible";
	public final static String FILLNECESSARYKEY = "fillnecessary";
	/**
	 * get
	 * @param formId
	 * @param businessKey
	 * @return
	 */
	public Map<String,Map<String,Object>> getFieldPermissionData(ServiceProduct serviceProduct,String businessKey){
		Dao<FieldPermission> daofp = DaoFactory.create(FieldPermission.class);
		FormService fs = ApplicationContextManager.getContext().getBean(FormService.class);
		Map<String,Map<String,Object>> mapresult = new HashMap<String,Map<String,Object>>();
//		Form f = new Form();
//		f.setFormId(formId);
		List<FormField> listff = fs.getFormField(serviceProduct.getFormId());
		
/*		FieldPermission con = new FieldPermission();
//		fp.setFieldId(ff);
		con.setFormId(f);
		con.setBusinessKey(businessKey);
		List<FieldPermission> listfp = daofp.select(con);*/
		//此处的逻辑是不是有点可以优化的意思？
		for(FormField ff:listff){
			HashMap<String,Object> hm = new HashMap<String,Object>();
			hm.put("fieldNo", ff.getFieldNo());
			FieldPermission fp = new FieldPermission();
			fp.setFieldId(ff);
			fp.setModuleId(serviceProduct.getProductId());
			fp.setBusinessKey(businessKey);
			fp = daofp.selectOne(fp);
			if(fp!=null){
				hm.put("readPermission", fp.isReadPermission());
				hm.put("writePermission", fp.isWritePermission());
				hm.put("visible", fp.isVisible());
				hm.put("fillnecessary", fp.isFillNecessary());	
			}else{
				hm.put("readPermission", true);
				hm.put("writePermission", true);
				hm.put("visible", true);	
				hm.put("fillnecessary", false);	
			}
			mapresult.put(ff.getFieldNo(), hm);
		}
		return mapresult;	
	}
	/**
	 * 获取查看的权限 目前查看暂时就是去结束节点的权限 TODO 完善
	 * @param serviceProduct
	 * @param businessKey
	 * @return
	 */
	public Map<String,Map<String,Object>> getFieldPermissionDataForView(ServiceProduct serviceProduct,String businessKey){
		Dao<FieldPermission> daofp = DaoFactory.create(FieldPermission.class);
		FormService fs = ApplicationContextManager.getContext().getBean(FormService.class);
		WorkFlowService wfs = ApplicationContextManager.getContext().getBean(WorkFlowService.class);
		Map<String,Map<String,Object>> mapresult = new HashMap<String,Map<String,Object>>();		
		
		Form f = new Form();
		f.setFormId(serviceProduct.getFormId());
		
		FieldPermission con = new FieldPermission();
		con.setModuleId(serviceProduct.getProductId());
		ActivityImpl act = wfs.getEndActivityByModuleId(serviceProduct.getProductId());
		con.setBusinessKey(act.getId());
		List<FieldPermission> listfp = daofp.select(con);
		List<FormField> listff = fs.getFormField(serviceProduct.getFormId());
		for(FormField ff:listff){
			HashMap<String,Object> hm = new HashMap<String,Object>();
			FieldPermission fp = this.getFieldPermissionFromList(listfp, ff, serviceProduct.getProductId());
			if(fp!=null){
				hm.put("readPermission", fp.isReadPermission());
				hm.put("writePermission", fp.isWritePermission());
				hm.put("visible", fp.isVisible());
				hm.put("fillnecessary", fp.isFillNecessary());	
			}else{
				hm.put("readPermission", true);
				hm.put("writePermission", true);
				hm.put("visible", true);	
				hm.put("fillnecessary", false);	
			}
			mapresult.put(ff.getFieldNo(), hm);
		}
		return mapresult;
	}
	/**
	 * 辅助方法
	 * @param listfp
	 * @param formField
	 * @param form
	 * @return
	 */
	public FieldPermission getFieldPermissionFromList(List<FieldPermission> listfp,FormField formField,String moduleId){
		for(FieldPermission fp:listfp){
			FormField fField = fp.getFieldId();
			if(fField == null){
				continue;
			}
			if(!fField.getFieldNo().equals(formField.getFieldNo())){
				continue;
			}
			if(!moduleId.equals(fp.getModuleId())){
				continue;
			}
			return fp;
		}
		return null;
	}
	/**
	 * save
	 * @param formId
	 * @param businessKey
	 * @param map
	 * @return
	 */
	public int saveFieldPermissionData(ServiceProduct serviceProduct,String businessKey,Map<String,Map<String,Object>> map){
		Dao<FieldPermission> daofp = DaoFactory.create(FieldPermission.class);
		FormService fs = ApplicationContextManager.getContext().getBean(FormService.class);
		List<FormField> listff = fs.getFormField(serviceProduct.getFormId());
		List<FieldPermission> listfpinsert = new ArrayList<FieldPermission>();
		List<FieldPermission> listfpupdate = new ArrayList<FieldPermission>();
		for(FormField ff:listff){
			FieldPermission fp = new FieldPermission();
			Map<String,Object> map1 = map.get(ff.getFieldNo());
			if(map1!=null){

				fp.setBusinessKey(businessKey);
				fp.setFieldId(ff);
				fp.setModuleId(serviceProduct.getProductId());
				FieldPermission fpp = daofp.selectOne(fp);
				boolean readPermission = (boolean) (map1.get(FieldPermissionService.READPERMISSIONKEY)==null?true:map1.get(FieldPermissionService.READPERMISSIONKEY));
				boolean writePermission = (boolean) (map1.get(FieldPermissionService.WRITEPERMISSIONKEY)==null?true:map1.get(FieldPermissionService.WRITEPERMISSIONKEY));
				boolean visible = (boolean) (map1.get(FieldPermissionService.VISIBLEKEY)==null?true:map1.get(FieldPermissionService.VISIBLEKEY));
				boolean fillNecessary = (boolean) (map1.get(FieldPermissionService.FILLNECESSARYKEY)==null?true:map1.get(FieldPermissionService.FILLNECESSARYKEY));
				fp.setReadPermission(readPermission);
				fp.setWritePermission(writePermission);
				fp.setVisible(visible);
				fp.setFillNecessary(fillNecessary);
				if(fpp==null){			
					listfpinsert.add(fp);
				}else{
					fp.setId(fpp.getId());
					listfpupdate.add(fp);
				}				
			}
		}
		return daofp.updateBatch(listfpupdate)+daofp.insertBatch(listfpinsert);
	}
}
