package com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.QueryVariableValue;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.variable.VariableTypes;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.impl.MTaskQueryImpl;

@Service
public class TaskRetrievalService {
	private ManagementService managementService;
	private TaskQueryImpl taskQuery;
	private Sortable sortable;
	public List<Task> getSortableTasks(TaskQuery tq,Sortable sort){
		setmTaskQuery(tq);	
		this.sortable = sort;
		managementService = ApplicationContextManager.getContext().getBean(ManagementService.class);

		Command<List<Task>> command = new Command<List<Task>>(){  
		    @SuppressWarnings("unchecked")  
		    @Override  
		    public List<Task> execute(CommandContext commandContext) { 
		    	SqlSessionFactory f = commandContext.getDbSqlSession().getDbSqlSessionFactory().getSqlSessionFactory();
		    	Configuration configuration = f.getConfiguration();
		    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyActivitiTaskMapper.xml");
	    		try {
	    			new XMLMapperBuilder(inputStream, configuration,
	    					"sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyActivitiTaskMapper.xml",
	    					configuration.getSqlFragments()).parse();
	    		} catch (Throwable e) {
	
	    		}		    	 

		    	List<QueryVariableValue> queryVariableValues1 = taskQuery.getQueryVariableValues();
				VariableTypes types1 = Context.getProcessEngineConfiguration().getVariableTypes();
			    for (QueryVariableValue var : queryVariableValues1) {
			    	var.initialize(types1);
			    }
	    
		    	((MTaskQueryImpl) taskQuery).setSortable(sortable);
		    	((MTaskQueryImpl) taskQuery).sortableValuesInitial();
		    	List<Task> list = (List<Task>) commandContext.getDbSqlSession().selectList("org.activiti.engine.impl.persistence.entity.MTaskEntity.selectCustomTaskList",taskQuery);  
		    	return list;
		    }  
		};
		
		List<Task> tasks = managementService.executeCommand(command);
		return tasks;

	}
	
	public int countSortableTasks(TaskQuery tq,Sortable sort){
		setmTaskQuery(tq);	
		this.sortable = sort;
		managementService = ApplicationContextManager.getContext().getBean(ManagementService.class);

		Command<Integer> command = new Command<Integer>(){  
		    @SuppressWarnings("unchecked")  
		    @Override  
		    public Integer execute(CommandContext commandContext) {  
//		    	String path = "E:\\c2_workplace\\c2-omp-console-parent\\c2-omp-service-parent\\c2-omp-service-manage\\target\\classes\\sqlmap\\com\\chinacreator\\c2\\omp\\service\\manage\\workflowcommon\\retrieval\\MyActivitiTaskMapper.xml";
//		    	File file = new File(path);
		    	SqlSessionFactory f = commandContext.getDbSqlSession().getDbSqlSessionFactory().getSqlSessionFactory();
		    	Configuration configuration = f.getConfiguration();
		    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyActivitiTaskMapper.xml");
	    		try {
	    			new XMLMapperBuilder(inputStream, configuration,
	    					"sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyActivitiTaskMapper.xml",
	    					configuration.getSqlFragments()).parse();
	    		} catch (Throwable e) {
	
	    		}
//		    	if (file.exists()) {
//		    		try {
//		    			new XMLMapperBuilder(new FileInputStream(file), configuration,
//		    					file.toURI().toString(),
//		    					configuration.getSqlFragments()).parse();
//		    		} catch (Throwable e) {
//
//		    		}
//		    	}	   

		    	List<QueryVariableValue> queryVariableValues1 = taskQuery.getQueryVariableValues();
				VariableTypes types1 = Context.getProcessEngineConfiguration().getVariableTypes();
			    for (QueryVariableValue var : queryVariableValues1) {
			    	var.initialize(types1);
			    }
	    
		    	((MTaskQueryImpl) taskQuery).setSortable(sortable);
		    	((MTaskQueryImpl) taskQuery).sortableValuesInitial();
		    	List list = commandContext.getDbSqlSession().selectList("org.activiti.engine.impl.persistence.entity.MTaskEntity.selectCustomTaskCount",taskQuery);  
		    	return (int)(long)list.get(0);
		    }  
		};
		
		int i = managementService.executeCommand(command);
		return i;		
	}

	public TaskQueryImpl getmTaskQuery() {
		return taskQuery;
	}
	public void setmTaskQuery(TaskQuery tq) {
		this.taskQuery = (TaskQueryImpl) tq;
	}
	
	public List<Map> getSortableTasksEXT(TaskQuery tq,Sortable sort){
		setmTaskQuery(tq);	
		this.sortable = sort;
		managementService = ApplicationContextManager.getContext().getBean(ManagementService.class);

		Command<List<Map>> command = new Command<List<Map>>(){  
		    @SuppressWarnings("unchecked")  
		    @Override  
		    public List<Map> execute(CommandContext commandContext) { 
		    	SqlSessionFactory f = commandContext.getDbSqlSession().getDbSqlSessionFactory().getSqlSessionFactory();
		    	Configuration configuration = f.getConfiguration();
		    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyActivitiTaskExternalTableMapper.xml");
	    		try {
	    			new XMLMapperBuilder(inputStream, configuration,
	    					"MyActivitiTaskExternalTableMapper.xml",
	    					configuration.getSqlFragments()).parse();
	    		} catch (Throwable e) {
	
	    		}		    	 

		    	List<QueryVariableValue> queryVariableValues1 = taskQuery.getQueryVariableValues();
				VariableTypes types1 = Context.getProcessEngineConfiguration().getVariableTypes();
			    for (QueryVariableValue var : queryVariableValues1) {
			    	var.initialize(types1);
			    }
	    
		    	((MTaskQueryImpl) taskQuery).setSortable(sortable);
		    	((MTaskQueryImpl) taskQuery).sortableValuesInitial();
		    	List<Map> list = (List<Map>) commandContext.getDbSqlSession().selectList("org.activiti.engine.impl.persistence.entity.taskretrievalexternaltable.selectETCustomTaskList",taskQuery);  
		    	return list;
		    }  

		};	
		List<Map> tasks = managementService.executeCommand(command);
		return tasks;
	}
	
	public int countSortableTasksEXT(TaskQuery tq,Sortable sort){
		setmTaskQuery(tq);	
		this.sortable = sort;
		managementService = ApplicationContextManager.getContext().getBean(ManagementService.class);	
		Command<Integer> command = new Command<Integer>(){  
		    @SuppressWarnings("unchecked")  
		    @Override  
		    public Integer execute(CommandContext commandContext) { 
		    	SqlSessionFactory f = commandContext.getDbSqlSession().getDbSqlSessionFactory().getSqlSessionFactory();
		    	Configuration configuration = f.getConfiguration();
		    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyActivitiTaskExternalTableMapper.xml");
	    		try {
	    			new XMLMapperBuilder(inputStream, configuration,
	    					"MyActivitiTaskExternalTableMapper.xml",
	    					configuration.getSqlFragments()).parse();
	    		} catch (Throwable e) {
	
	    		}		    	 

		    	List<QueryVariableValue> queryVariableValues1 = taskQuery.getQueryVariableValues();
				VariableTypes types1 = Context.getProcessEngineConfiguration().getVariableTypes();
			    for (QueryVariableValue var : queryVariableValues1) {
			    	var.initialize(types1);
			    }
	    
		    	((MTaskQueryImpl) taskQuery).setSortable(sortable);
		    	((MTaskQueryImpl) taskQuery).sortableValuesInitial();
		    	List list = (List<Map>) commandContext.getDbSqlSession().selectList("org.activiti.engine.impl.persistence.entity.taskretrievalexternaltable.selectETCustomTaskCount",taskQuery);  
		    	return (int)(long)list.get(0);
		    }  

		};	
		return managementService.executeCommand(command);
	}
}
