package com.chinacreator.c2.qyb.workflow.read.entity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinacreator.c2.dao.mybatis.enhance.RowBounds4Page;

public interface ProcInsReadRecordMapper {

	public List<Map> getMyPages(Map con,RowBounds4Page rb);
	
}
