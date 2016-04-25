package com.chinacreator.c2.omp.common.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlServiceImpl;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.UserInfo;
import com.chinacreator.c2.omp.common.exception.ServiceException;
/**
 * 请注意密码的保护 UserInfo UserDTO均含有密码信息
 * @author 
 * @version 1.0
 * @date 
 */
@Service
public class UserInfoService {
	private OrgService orgService;

	public UserDTO getUserInfo()
	{
		UserDTO user = new UserDTO();
		AccessControlService acc=new AccessControlServiceImpl();
		String userId = acc.getUserID();
		if (null != userId && !userId.trim().equals(""))
		{
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);
			user= userService.queryByPK(userId);
		}	
		return user;
	}
	
	/**
	 * 
	 *<p>查询用户信息</p>
	 *@param userIds
	 *@return
	 *@throws ServiceException
	 * @author tao.wang1
	 * 2016年1月20日
	 */
	public List<UserInfo> queryUserInfoByIds(String userIds)  throws ServiceException{
		Dao<UserInfo> messageDao = DaoFactory.create(UserInfo.class);
		SqlSession session = messageDao.getSession();
		Map<String,String> paramMap=new HashMap<String, String>();
		if(null!=userIds&&!userIds.isEmpty()){
			paramMap.put("userIds", userIds);
		}
		List<UserInfo> resultUserInfo = session.selectList("com.chinacreator.c2.omp.common.UserInfoMapper.selectUserByIds", paramMap);
		return resultUserInfo;		
	}	
	/**
	 * 
	 * RT
	 * @param userIds
	 * @return
	 */
	public Map<String,UserDTO> queryUserInfoById(String[] userIds){
		Map<String,UserDTO> map = new HashMap<String,UserDTO>();
		if(userIds==null)
			return map;
		UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);
		for(String userId:userIds){
			UserDTO userDTO = userService.queryByPK(userId);
			map.put(userId, userDTO);
		}
		return map;
	}
	/**
	 * map形式返回所有的用户信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Map> queryAllUserInfo(){
		Map<String,Map> map = new HashMap<String,Map>();
		List<OrgDTO> orgs = getOrgService().queryChildOrgs("0", true);
		for (OrgDTO orgDTO : orgs) {
			List<UserDTO> userDTOs = getOrgService().queryUsers(orgDTO.getOrgId());		
			for (UserDTO user : userDTOs) {
				Map<String,String> userMap = new HashMap<String,String>();
				userMap.put("userRealname", user.getUserRealname());
				map.put(user.getUserId(), userMap);
			}
		}	
		return map;
	}
	
	private OrgService getOrgService() {
		if (null == orgService) {
			orgService = ApplicationContextManager.getContext().getBean(OrgService.class);
		}
		return orgService;
	}
}
