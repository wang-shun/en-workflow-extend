package com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.omp.service.manage.workflowcommon.FormField;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.RetrieveItem;

@Service
public class RetrieveItemService {
	
	public List<RetrieveItem> getRetrieveItemsByRetrieveKey(String retrieveKey){
		if(retrieveKey==null){
			return new ArrayList<RetrieveItem>();
		}
		Dao<RetrieveItem> dao = DaoFactory.create(RetrieveItem.class);
		RetrieveItem item = new RetrieveItem();
		item.setRetrieveKey(retrieveKey);
		return dao.select(item);
	}
	
	public void addRetrieveItemOnRetrieve(String retrieveKey,List items){
		Dao<RetrieveItem> dao = DaoFactory.create(RetrieveItem.class);
		List<RetrieveItem> needaddItems = new ArrayList<RetrieveItem>();
		
		for(int i=0;i<items.size();i++){
			RetrieveItem item = new RetrieveItem();
			FormField field = new FormField();
			item.setRetrieveKey(retrieveKey);
			JSONObject json = (JSONObject)items.get(i);

			field.setFieldId((String)json.get("fieldId"));
			item.setFieldId(field);
			if(dao.select(item).size()>0){
				continue;
			}
			item.setIsLike(json.get("isLike").equals("Yes")?true:false);
			needaddItems.add(item);
		}
		dao.insertBatch(needaddItems);
	}
}
