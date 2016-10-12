//package com.chinacreator.c2.omp.common.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.chinacreator.c2.dao.mybatis.enhance.Page;
//import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
//import com.chinacreator.c2.ioc.ApplicationContextManager;
//import com.chinacreator.c2.omp.common.UploadFile;
//import com.chinacreator.c2.web.ds.ArrayContentProvider;
//import com.chinacreator.c2.web.ds.ArrayContext;
//
//public class UploadFileContentProvider implements ArrayContentProvider {
//	
//	private UploadFileService uploadfileservice;
//	private UploadFileService getUploadFileService(){
//        if (uploadfileservice == null) {
//        	uploadfileservice = ApplicationContextManager.getContext().getBean(
//                    UploadFileService.class);
//        }
//
//        return uploadfileservice;
//	}
//	@Override
//	public Page<UploadFile> getElements(ArrayContext context) {
//		Map map;
//		Page<UploadFile> page = new Page<UploadFile>(new Pageable(), new ArrayList());
//		List<UploadFile> content = new ArrayList<UploadFile>();	
//		if(context != null){
//			map = context.getCondition();
//			String businesskey = (String) map.get("businessKey");
//			String businessKey2 = (String) map.get("businessKey2");
//			String businessType = (String) map.get("businessType");
//			Pageable pageable = context.getPageable();
//			return getUploadFileService().getFilesPage(businessType,businesskey, businessKey2, pageable);
//		}
//		return page;
//	}
//
//}
