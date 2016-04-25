package com.chinacreator.c2.omp.service.manage.workflowcommon.cmd;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.QueryVariableValue;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.variable.VariableTypes;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.impl.MHistoricProcessInstanceQueryImpl;

/** 
 * @author qiao.li 
 * @version 创建时间：2016年3月25日 下午1:37:52 
 * 类说明 
 */
public class CountHistoricProcessInstanceCmd implements org.activiti.engine.impl.interceptor.Command<Integer>{
	private MHistoricProcessInstanceQueryImpl queryIns;

	public CountHistoricProcessInstanceCmd(MHistoricProcessInstanceQueryImpl queryIns){
		this.queryIns = queryIns;
	}
	@Override
	public Integer execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
    	SqlSessionFactory f = commandContext.getDbSqlSession().getDbSqlSessionFactory().getSqlSessionFactory();
    	Configuration configuration = f.getConfiguration();
    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyHistoricProcessInstanceQueryMapper.xml");
		try {
			new XMLMapperBuilder(inputStream, configuration,
					"sqlmap/com/chinacreator/c2/omp/service/manage/workflowcommon/retrieval/MyHistoricProcessInstanceQueryMapper.xml",
					configuration.getSqlFragments()).parse();
		} catch (Throwable e) {

		}	
    	List<QueryVariableValue> queryVariableValues1 = queryIns.getQueryVariableValues();
		VariableTypes types1 = Context.getProcessEngineConfiguration().getVariableTypes();
	    for (QueryVariableValue var : queryVariableValues1) {
	    	var.initialize(types1);
	    }
	    queryIns.sortableValuesInitial();
	    List list = commandContext.getDbSqlSession().selectList("org.activiti.engine.impl.persistence.entity.MyHistoricProcessInstanceEntity.selectCustomHistoricProcessInstanceCountByQueryCriteria",queryIns);  
	    return (int)(long)list.get(0);
	}

}
