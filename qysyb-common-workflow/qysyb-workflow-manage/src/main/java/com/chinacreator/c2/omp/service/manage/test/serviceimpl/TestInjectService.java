package com.chinacreator.c2.omp.service.manage.test.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.omp.service.manage.test.service.IServiceAgreementService;

@Service
public class TestInjectService {
	@Autowired
	IServiceAgreementService serviceAgreementService;
	
	public void testIntection(){
		serviceAgreementService.getServiceAgreementByNo();
	}
	
}
