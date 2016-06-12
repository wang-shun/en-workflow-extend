package com.chinacreator.c2.qyb.workflow.group.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
/**
 * 
 * @author Work
 *
 */
@Service
public class UserJobService {

//	UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);
	/**
	 * 获取工作组用户
	 * @param jobid
	 * @return
	 */
	public List<UserDTO> getAllUserJob(String jobid){
		JobService jobService = ApplicationContextManager.getContext().getBean(JobService.class);
		List<UserDTO> list = jobService.queryUsers(jobid);
		return list;
	}
	@Autowired
	UserService userService;
	/**
	 * 
	 * @param jobId 需要过滤的岗位
	 * @param curUserId  过滤条件
	 * @return
	 */
	public List<UserDTO> getAllUserFromJobWithSameOrg(String jobId,String curUserId){
		List<UserDTO> result = new ArrayList<UserDTO>();
		List<UserDTO> list = this.getAllUserJob(jobId);
		OrgDTO con = userService.queryMainOrg(curUserId);
		for(UserDTO userDTO:list){
			OrgDTO org = userService.queryMainOrg(userDTO.getUserId());
			if(con.getOrgId().equals(org.getOrgId())){
				result.add(userDTO);
			}
		}
		return result;
	}
}
