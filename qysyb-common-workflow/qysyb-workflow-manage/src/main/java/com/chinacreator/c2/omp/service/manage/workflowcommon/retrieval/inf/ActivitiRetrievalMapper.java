package com.chinacreator.c2.omp.service.manage.workflowcommon.retrieval.inf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface ActivitiRetrievalMapper {
	  @Select("SELECT ID_ as id, NAME_ as name, CREATE_TIME_ as createTime FROM ACT_RU_TASK")
	  List<Map<String, Object>> selectTasks();
}
