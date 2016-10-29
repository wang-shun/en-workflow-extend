package com.cool.en.workflow.form.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.qyb.workflow.form.impl.FieldPermissionService;
import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;

@RestController
@RequestMapping("field")
public class FieldmgtController {
	@Autowired
	FieldPermissionService fps;
	@RequestMapping("getpermissiondata")
	public Map<String, Map<String, Object>> getActivityFieldPermissondata(@RequestBody() JSONObject params){
		ServiceProduct sp = params.getObject("serviceProduct", ServiceProduct.class);
		String businessKey = (String) params.get("businessKey");
		return fps.getFieldPermissionData(sp, businessKey);
	}
	
	@RequestMapping("savepermissiondata")
	public int savePermissionData(@RequestBody() JSONObject jsonObject){
		ServiceProduct sp = jsonObject.getObject("serviceProduct", ServiceProduct.class);
		String businessKey = jsonObject.getString("businessKey");
		Map map = jsonObject.getObject("data", Map.class);
		return fps.saveFieldPermissionData(sp, businessKey, map);
	}
}
