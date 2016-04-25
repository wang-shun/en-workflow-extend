package com.chinacreator.c2.omp.service.manage.workflowcommon.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
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
}
