package com.chinacreator.c2.jp.car.provider;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.plaf.synth.Region;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.jp.car.manage.entity.Car;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class CarListContentProvider implements ArrayContentProvider {

	@Override
	public Page<Car> getElements(ArrayContext arg0) {
		Dao<Car> eDao = DaoFactory.create(Car.class);
		String carno = getConditionParam(arg0, "carno");
		String carname = getConditionParam(arg0, "carname");
		String cartype = getConditionParam(arg0, "cartype");
		
		List<Car> rso = new ArrayList<Car>();
		rso = eDao.selectAll();
		
		List<Car> results = new ArrayList<Car>();
		
		// 车牌号过滤
		if (carno != null) {
			for (Car r : rso) {
				if (r.getCarNumber().contains(carno))
					results.add(r);
			}
			rso.clear();
			rso.addAll(results);
		}

		// 品牌过滤
		if (carname != null) {
			results.clear();
			for (Car r : rso) {
				if (r.getCarName().contains(carname))
					results.add(r);
			}
			rso.clear();
			rso.addAll(results);
		}

		// 车辆类型过滤
		if (cartype != null) {
			results.clear();
			for (Car r : rso) {
				if (r.getCarType().contains(cartype))
					results.add(r);
			}
			rso.clear();
			rso.addAll(results);
		}
		
		List<Car> rso2 = new ArrayList<Car>();
		// 构造分页对象
		Pageable pageable = arg0.getPageable();
		int page = pageable.getPageIndex();
		int size = pageable.getPageSize();
		int begin1 = (page - 1) * size;
		int begin = begin1 < rso.size() ? begin1 : 0;
		int end1 = page * size;
		int end = end1 > rso.size() ? rso.size() : end1;
		for (int i = begin; i < end; i++) {
			rso2.add(rso.get(i));
		}
		Page<Car> outPage = new Page<Car>(page, size,
				rso.size(), rso2);
		return outPage;
	}
	
	private String getConditionParam(ArrayContext arg0,String paraName){
		String result = null;
		Object paraObj = arg0.getCondition().get(paraName);
		
		if (paraObj != null){
			result = paraObj.toString();
		}
		
		return result;
	}

}
