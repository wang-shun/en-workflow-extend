package com.chinacreator.c2.omp.service.manage.workflowcommon.service;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.omp.service.manage.workflowcommon.FormField;

@Service
public class FormFieldService {
	/**
	 * get
	 * @param no
	 * @return
	 */
	public FormField getFormFieldByFieldNo(String no){
		FormField formField = new FormField();
		formField.setFieldNo(no);
		Dao<FormField> dao = DaoFactory.create(FormField.class);
		return dao.selectOne(formField);
	}
	/**
	 * 查看是否存于外部数据库表而不是流程变量
	 * @param ff
	 * @return
	 */
	public boolean isFieldStorageEXT(FormField ff){
		if(ff==null){
			return false;
		}
		//如果不是存入流程变量 则就是在外部作业务数据存储
		if(ff.isRemark9()==null||!ff.isRemark9()){
			return true;
		}
		if(ff.getRemark5()==null){
			return false;
		}

/*		if(ff.getRemark6()==null){
			return false;
		}*/
		return true;
	}
	/**
	 * 字段是否存在流程变量中
	 * @param ff
	 * @return
	 */
	public boolean isFieldStorageProcessVariable(FormField ff){
		if(ff==null){
			return false;
		}
		//如果不是存入流程变量 则就是在外部作业务数据存储
		if(ff.isRemark9()!=null&&ff.isRemark9()){
			return true;
		}
		return false;
	}
}
