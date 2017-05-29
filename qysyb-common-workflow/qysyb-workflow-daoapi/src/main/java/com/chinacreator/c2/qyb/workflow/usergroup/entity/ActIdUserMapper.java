package com.chinacreator.c2.qyb.workflow.usergroup.entity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ActIdUserMapper {

	public List<ActIdUser> getActIdUsersByGroup(@Param("groupId") String groupId);
	
}
