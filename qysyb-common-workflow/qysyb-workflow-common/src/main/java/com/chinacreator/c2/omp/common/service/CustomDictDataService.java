package com.chinacreator.c2.omp.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.omp.common.DictDataInfo;
import com.chinacreator.c2.omp.common.DictTypeInfo;

/**
 * 
 * @author qiao.li
 *
 */
@Service
public class CustomDictDataService {
	/**
	 * 批量获取字典数据，与平台提供的方法不同 这里作为map返回，前端可以通过 。 操作符 获取指定字典指定字典编号的数据 
	 * 
	 * @param dictName
	 * @return
	 */
	public Map<String,Map<String,String>> getDictDataValueByDictTypeName(String[] dictTypeName){
		Map<String,Map<String,String>> mapdicts = new HashMap<String,Map<String,String>>();
		
		Dao<DictTypeInfo> daodt = DaoFactory.create(DictTypeInfo.class);
		Dao<DictDataInfo> daodd = DaoFactory.create(DictDataInfo.class);
		for(String d:dictTypeName){
			Map<String,String> map = new HashMap<String,String>();
			DictTypeInfo dt = new DictTypeInfo();
			dt.setDicttypeName(d);		
			List<DictTypeInfo> listdt = daodt.select(dt);
			if(listdt.size()==1){
				dt = listdt.get(0);	
				DictDataInfo dd = new DictDataInfo();
				dd.setDicttypeId(dt);
				List<DictDataInfo> ddlist = daodd.select(dd);
				
				for(DictDataInfo dd1:ddlist){
					map.put(dd1.getDictdataName(), dd1.getDictdataValue());
				}
				mapdicts.put(d, map);
			}
		}
	
		return mapdicts;
	}
	
	/**
	 *  批量获取字典数据
	 * @param dictTypeName
	 * @return
	 */
	public List<DictDataInfo> getDictName(String dictTypeName){
		Dao<DictTypeInfo> daodt = DaoFactory.create(DictTypeInfo.class);
		Dao<DictDataInfo> daodd = DaoFactory.create(DictDataInfo.class);
		Map<String,String> map = new HashMap<String,String>();
		DictTypeInfo dt = new DictTypeInfo();
		dt.setDicttypeName(dictTypeName);		
		List<DictTypeInfo> listdt = daodt.select(dt);
		if(listdt.size()==1){
			dt = listdt.get(0);	
			DictDataInfo dd = new DictDataInfo();
			dd.setDicttypeId(dt);
			return daodd.select(dd);
		}
		return null;
		
	}
}
