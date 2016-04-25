package com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.HistoricProcessInstanceQueryImpl;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.QueryOperator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.variable.VariableTypes;

import com.chinacreator.c2.dao.mybatis.enhance.Order;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Form;
import com.chinacreator.c2.omp.service.manage.workflowcommon.FormField;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.ExternalTableQueryVariableValue;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年3月24日 下午2:45:05 
 * 类说明 
 */
public class MHistoricProcessInstanceQueryImpl extends HistoricProcessInstanceQueryImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1792663999706715535L;
	public Sortable sortable;
	public ArrayList<SortableVariableValue> sortableValues = new ArrayList<SortableVariableValue>();
	public Sortable getSortable() {
		return sortable;
	}
	public void setSortable(Sortable sortable) {
		this.sortable = sortable;
	}

	public void sortableValuesInitial(){	
		if(sortable!=null){
			for(Order order:sortable.getOrders()){
				SortableVariableValue value = new SortableVariableValue(order.getOrderProperty(),order.getOrderDirection());
				VariableTypes types = Context.getProcessEngineConfiguration().getVariableTypes();
//				value.initialize(types);
				value.initial(types);
				sortableValues.add(value);
			}			
		}
	}
	
	ArrayList<Form> forms = new ArrayList<Form>();
	
	public void addForm(Form form){
		if(form!=null){
			forms.add(form);
		}
	}
	
	public ArrayList<Form> getForms(){
		return forms;
	}

	public MHistoricProcessInstanceQueryImpl setPage(int firstResult,int maxResults){
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		return this;
	}
	
	public List<ExternalTableQueryVariableValue> queriableFields = new ArrayList<ExternalTableQueryVariableValue>();
	
	public void externalTableQueryVariableValueEqual(FormField form,Object value){
//		queriableFields.add(new ExternalTableQueryVariableValue(form, value, QueryOperator.EQUALS));
		addQueriableField(form, value, QueryOperator.EQUALS);
	}
	
	public void externalTableQueryVariableValueLike(FormField formField,Object value){
		addQueriableField(formField,value,QueryOperator.LIKE);
	}
	
	public void externalTableQueryVariableValueLessThanOrEqual(FormField formField,Object value) {
		addQueriableField(formField, value, QueryOperator.LESS_THAN_OR_EQUAL);

	}
	
	public void externalTableQueryVariableValueGreaterThanOrEqual(FormField formField,Object value) {
		addQueriableField(formField, value, QueryOperator.GREATER_THAN_OR_EQUAL);

	}
	
	public List<ExternalTableQueryVariableValue> getQueriableFields() {
		return queriableFields;
	}
	public void setQueriableFields(
			List<ExternalTableQueryVariableValue> queriableFields) {
		this.queriableFields = queriableFields;
	}
	
	
	public void addQueriableField(FormField formField, Object value, QueryOperator operator){
	    if(formField == null) {
	      throw new ActivitiIllegalArgumentException("formField is null");
	    }
	    if(value == null || testBoolean(value)) {
	      // Null-values and booleans can only be used in EQUALS and NOT_EQUALS
	      switch(operator) {
	      case GREATER_THAN:
	        throw new ActivitiIllegalArgumentException("Booleans and null cannot be used in 'greater than' condition");
	      case LESS_THAN:
	        throw new ActivitiIllegalArgumentException("Booleans and null cannot be used in 'less than' condition");
	      case GREATER_THAN_OR_EQUAL:
	        throw new ActivitiIllegalArgumentException("Booleans and null cannot be used in 'greater than or equal' condition");
	      case LESS_THAN_OR_EQUAL:
	        throw new ActivitiIllegalArgumentException("Booleans and null cannot be used in 'less than or equal' condition");
	      }
	      
	      if(operator == QueryOperator.EQUALS_IGNORE_CASE && (value == null || !(value instanceof String)))
	      {
	        throw new ActivitiIllegalArgumentException("Only string values can be used with 'equals ignore case' condition");
	      }
	      
	      if(operator == QueryOperator.NOT_EQUALS_IGNORE_CASE && (value == null || !(value instanceof String)))
	      {
	        throw new ActivitiIllegalArgumentException("Only string values can be used with 'not equals ignore case' condition");
	      }
	      
	      if(operator == QueryOperator.LIKE && (value == null || !(value instanceof String)))
	      {
	        throw new ActivitiIllegalArgumentException("Only string values can be used with 'like' condition");
	      }
	    }
	    queriableFields.add(new ExternalTableQueryVariableValue(formField, value, operator));
		
	}
	private boolean testBoolean(Object value) {
		    if (value == null) {
		      return false;
		    }
		    return Boolean.class.isAssignableFrom(value.getClass()) || boolean.class.isAssignableFrom(value.getClass());
	}

}
