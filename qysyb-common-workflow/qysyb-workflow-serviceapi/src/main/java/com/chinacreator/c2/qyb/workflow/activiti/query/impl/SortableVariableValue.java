package com.chinacreator.c2.qyb.workflow.activiti.query.impl;

import java.io.Serializable;
import java.util.Date;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.variable.ByteArrayType;
import org.activiti.engine.impl.variable.JPAEntityVariableType;
import org.activiti.engine.impl.variable.VariableType;
import org.activiti.engine.impl.variable.VariableTypes;

import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.form.entity.FormField;
import com.chinacreator.c2.qyb.workflow.form.impl.FormFieldService;
import com.chinacreator.c2.qyb.workflow.form.impl.FormService;


/**
 * Represents a variable value used in sorts.
 * 
 * @author qiao li
 */
public class SortableVariableValue implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String TYPE_STRING = "string";
  private static final String TYPE_LONG = "long";
  private static final String TYPE_DATE = "date";
  private static final String TYPE_INTEGER = "integer";
  
  private String orderProperty; 
  private String orderDirection;
  private Object o;
  private VariableInstanceEntity variableInstanceEntity;
  
  private FormField formField;
  private boolean isFieldStorageExt = false;
  
  public String getTableCol(){
	  if(formField!=null){
		  return formField.getFieldColName();
	  }
	  return null;	  
  }
  
  public boolean getIsFieldStorageExt(){
	  return isFieldStorageExt;
  }

  private FormFieldService ffs = ApplicationContextManager.getContext().getBean(FormFieldService.class);
    
  public SortableVariableValue(String orderProperty, String orderDirection) {
    this.orderProperty = orderProperty;
    this.orderDirection = orderDirection;
  }
  
  public void initial(VariableTypes types){
	  //单独处理。。。。
	  if(orderProperty.equals("productName")){
		  isFieldStorageExt = false;
		  initialize(types);
		  return;
	  }
	  formField = ffs.getFormFieldByFieldNo(orderProperty);
	  if(formField==null){
		  isFieldStorageExt = true;
	  }else{
		  isFieldStorageExt = ffs.isFieldStorageEXT(formField);
	  }  
	  if(!isFieldStorageExt){
		  initialize(types);
	  }else{
		  
	  }
  }
  
  public void initialize(VariableTypes types) {
    if(variableInstanceEntity == null) {
      VariableType type = findPropertyType(types,orderProperty);
      if(o!=null){
    	  if(type instanceof ByteArrayType) {
              throw new ActivitiIllegalArgumentException("Variables of type ByteArray cannot be used to query");
    	  } else if(type instanceof JPAEntityVariableType ) {
              throw new ActivitiIllegalArgumentException("JPA entity variables can only be used in 'variableValueEquals'");
    	  } else {
              // Type implementation determines which fields are set on the entity
            	 variableInstanceEntity = VariableInstanceEntity.create(orderProperty, type, o);
    	  }
      }

    }
  }
  
  
  
  private VariableType findPropertyType(VariableTypes types, String orderProperty) {
	// TODO Auto-generated method stub
	  /*如果排序列为SLA剩余时间处理*/
	  if(orderProperty.equals(WorkFlowService.SLA_REMAIN_TIME)){
		 /* 因为happenedtimel在流程变量中是做为字符串存储的。。。。。*/
		  	this.orderProperty = WorkFlowService.HAPPENEDTIMEL;
			String s = "string";
			VariableType stype = types.findVariableType(s);
			VariableType type = types.getVariableType(SortableVariableValue.TYPE_STRING);
			if(type.equals(stype)){
				o = s;
				return type;
			}
	  }
	  
	FormFieldService formFieldService = ApplicationContextManager.getContext().getBean(FormFieldService.class);
	FormField formField = formFieldService.getFormFieldByFieldNo(orderProperty);
	//需要把工作流VariableType与自定义信息项上面的type统一起来
	if(formField==null){
		/*默认返回一个字符类型*/
		String s = "string";
		o = s;
		VariableType stype = types.findVariableType(s);		
		return stype;
	}
	String fieldType = formField.getFieldType();
	VariableType type = null;
	if(fieldType==null||fieldType.equals(FormService.TYPESTRING)){
		String s = "string";
		VariableType stype = types.findVariableType(s);
		type = types.getVariableType(SortableVariableValue.TYPE_STRING);
		if(type.equals(stype)){
			o = s;
			return type;
		}
		
	}else if(fieldType.equals(FormService.TYPEINTEGER)){
		Integer i = 0;
		VariableType stype = types.findVariableType(i);
		type = types.getVariableType(SortableVariableValue.TYPE_INTEGER);
		if(type.equals(stype)){
			o = i;
			return type;
		}
	}else if(fieldType.equals(FormService.TYPETIME)){
		Date date = new Date();
		VariableType stype = types.findVariableType(date);
		type = types.getVariableType(SortableVariableValue.TYPE_DATE);
		if(type.equals(stype)){
			o = date;
			return type;
		}
	}
	return type;
}

public String getOrderProperty() {
    return orderProperty;
  }
  
  public String getOrderDirection() {
	    return orderDirection;
	  }
  
  public String getTextValue() {
    if(variableInstanceEntity != null) {
      return variableInstanceEntity.getTextValue();
    }
    return null;
  }
  
  public Long getLongValue() {
    if(variableInstanceEntity != null) {
      return variableInstanceEntity.getLongValue();
    }
    return null;
  }
  
  public Double getDoubleValue() {
    if(variableInstanceEntity != null) {
      return variableInstanceEntity.getDoubleValue();
    }
    return null;
  }
  
  public String getTextValue2() {
    if(variableInstanceEntity != null) {
      return variableInstanceEntity.getTextValue2();
    }
    return null;
  }

  public String getType() {
    if(variableInstanceEntity != null) {
      return variableInstanceEntity.getType().getTypeName();
    }
    return null;
  }
  
}