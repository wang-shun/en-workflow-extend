package com.chinacreator.c2.omp.common.service;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.chinacreator.c2.util.UUIDUtil;

public class UUIDGuidService {

	/**
	 *生成uuid服务类	
	 * @return uuid
	 */
	
	public String guidUUID(){
		String uuid= UUIDUtil.createUUID();		
		return uuid;	
	}
	
	/*public static void main(String[] args) {
	    UUIDGuidService dd=new UUIDGuidService();
		String uuid=dd.guidUUID();
		System.out.println(uuid);
	}*/
}
