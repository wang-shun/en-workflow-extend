package com.chinacreator.c2.omp.service.manage.workflowcommon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.workflowcommon.FieldPermission;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Form;
import com.chinacreator.c2.omp.service.manage.workflowcommon.FormField;

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
	public Map<String,Map<String,Object>> getFieldPermissionData(String formId,String businessKey){
		Dao<FieldPermission> daofp = DaoFactory.create(FieldPermission.class);
		FormService fs = ApplicationContextManager.getContext().getBean(FormService.class);
		Map<String,Map<String,Object>> mapresult = new HashMap<String,Map<String,Object>>();
		Form f = new Form();
		f.setFormId(formId);
		List<FormField> listff = fs.getFormField(formId);
		for(FormField ff:listff){
			HashMap<String,Object> hm = new HashMap<String,Object>();
			hm.put("fieldNo", ff.getFieldNo());
			FieldPermission fp = new FieldPermission();
			fp.setFieldId(ff);
			fp.setFormId(f);
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
	 * save
	 * @param formId
	 * @param businessKey
	 * @param map
	 * @return
	 */
	public int saveFieldPermissionData(String formId,String businessKey,Map<String,Map<String,Object>> map){
		Dao<FieldPermission> daofp = DaoFactory.create(FieldPermission.class);
		FormService fs = ApplicationContextManager.getContext().getBean(FormService.class);
		List<FormField> listff = fs.getFormField(formId);
		List<FieldPermission> listfpinsert = new ArrayList<FieldPermission>();
		List<FieldPermission> listfpupdate = new ArrayList<FieldPermission>();
		Form f = new Form();
		f.setFormId(formId);
		for(FormField ff:listff){
			FieldPermission fp = new FieldPermission();
			Map<String,Object> map1 = map.get(ff.getFieldNo());
			if(map1!=null){

				fp.setBusinessKey(businessKey);
				fp.setFieldId(ff);
				fp.setFormId(f);
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
