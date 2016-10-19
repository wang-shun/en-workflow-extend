package com.cool.en.workflow.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinacreator.c2.qyb.workflow.module.entity.ServiceProduct;
import com.chinacreator.c2.qyb.workflow.module.impl.ServiceProductService;

@RestController()
@RequestMapping("cs")
public class ServiceProductController {
	@Autowired
	ServiceProductService sps;
	@RequestMapping("getavaliableproducts")
	public List<ServiceProduct> getAvaliableProducts(@RequestBody(required=false) ServiceProduct sp){
		if(sp == null){
			sp = new ServiceProduct();
		}
		return sps.getAllServiceProductWithModule(sp);
	}
}
