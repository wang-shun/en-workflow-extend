package com.chinacreator.c2.qyb.workflow.activiti.query.impl;

import java.io.Serializable;

import org.activiti.engine.impl.QueryOperator;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.variable.VariableTypes;

import com.chinacreator.c2.qyb.workflow.form.entity.FormField;


public class ExternalTableQueryVariableValue implements Serializable{
	  private static final long serialVersionUID = 1L;
	  private FormField formField;
	  private Object value;
	  private QueryOperator operator;
	  
	  private VariableInstanceEntity variableInstanceEntity;
	    
	  public ExternalTableQueryVariableValue(FormField formField, Object value, QueryOperator operator) {
		this.formField = formField;
	    this.value = value;
	    this.operator = operator;

	  }
	  
	  public String getTableName(){
		  if(formField!=null){
			  return formField.getRemark6();
		  }
		  return null;
	  }
	  
	  public String getTableCol(){
		  if(formField!=null){
			  return formField.getRemark5();
		  }
		  return null;	  
	  }
	  
	  public Object getValue(){
		  return value;
	  }
	  
	  public String getOperator() {
		  if(operator != null) {
			  return operator.toString();      
		  }
		  return QueryOperator.EQUALS.toString();
	  }
	  
	  public void initialize(VariableTypes types) {
//	    if(variableInstanceEntity == null) {
//	      VariableType type = types.findVariableType(value);
//	      if(type instanceof ByteArrayType) {
//	        throw new ActivitiIllegalArgumentException("Variables of type ByteArray cannot be used to query");
//	      } else if(type instanceof JPAEntityVariableType && operator != QueryOperator.EQUALS) {
//	        throw new ActivitiIllegalArgumentException("JPA entity variables can only be used in 'variableValueEquals'");
//	      } else {
//	        // Type implementation determines which fields are set on the entity
//	        variableInstanceEntity = VariableInstanceEntity.create(name, type, value);
//	      }
//	    }
	  }
}
