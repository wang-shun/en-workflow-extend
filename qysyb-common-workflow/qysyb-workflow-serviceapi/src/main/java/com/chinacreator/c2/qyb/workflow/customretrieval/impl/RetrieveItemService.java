package com.chinacreator.c2.qyb.workflow.customretrieval.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.qyb.workflow.customretrieval.entity.RetrieveItem;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;

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

	public int deleteRetrieveItem(String itemId){
		Dao<RetrieveItem> dao = DaoFactory.create(RetrieveItem.class);
		RetrieveItem item = new RetrieveItem();
		item.setItemId(itemId);
		return dao.delete(item);		
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
