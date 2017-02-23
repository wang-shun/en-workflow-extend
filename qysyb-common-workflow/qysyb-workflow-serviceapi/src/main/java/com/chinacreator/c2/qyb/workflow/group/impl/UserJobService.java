package com.chinacreator.c2.qyb.workflow.group.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.qyb.workflow.usergroup.entity.ActIdMembership;
import com.chinacreator.c2.qyb.workflow.usergroup.entity.ActIdUser;
import com.chinacreator.c2.qyb.workflow.usergroup.entity.ActIdUserMapper;
/**
 * 
 * @author Work
 *
 */
@Service
public class UserJobService {

	/**
	 * 获取工作组用户
	 * @param jobid
	 * @return
	 */
	public List<UserDTO> getAllUserJob(String jobid){
		Dao<Object> dao = DaoFactory.create(Object.class);
		ActIdUserMapper mapper = dao.getSession().getMapper(ActIdUserMapper.class);
		List<ActIdUser> list = mapper.getActIdUsersByGroup(jobid);
		List<UserDTO> result = new ArrayList<UserDTO>();
 		for(ActIdUser actIdUser:list){
 			UserDTO ud = new UserDTO();
 			ud.setUserId(actIdUser.getId());
 			ud.setUserName(actIdUser.getFirst());
 			ud.setUserRealname(actIdUser.getLast());
 			ud.setUserEmail(actIdUser.getEmail());
 			result.add(ud);
 		}		
		return result;
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
		
		Dao<Object> dao = DaoFactory.create(Object.class);
		ActIdUserMapper mapper = dao.getSession().getMapper(ActIdUserMapper.class);
		List<ActIdUser> list = mapper.getActIdUsersByGroup(jobId);		
		
		OrgDTO con = userService.queryMainOrg(curUserId);
		for(ActIdUser actIdUser:list){
			OrgDTO org = userService.queryMainOrg(actIdUser.getId());
			if(con.getOrgId().equals(org.getOrgId())){
	 			UserDTO ud = new UserDTO();
	 			ud.setUserId(actIdUser.getId());
	 			ud.setUserName(actIdUser.getFirst());
	 			ud.setUserRealname(actIdUser.getLast());
	 			ud.setUserEmail(actIdUser.getEmail());
	 			result.add(ud);
			}
		}
		return result;
	}
}
