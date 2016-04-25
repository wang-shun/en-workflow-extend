package com.chinacreator.c2.omp.service.manage.notificationmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.exception.ServiceException;
import com.chinacreator.c2.omp.service.manage.notificationmanage.Noticeannouncement;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

/**
 * 通知公告服务类
 * 
 * @author bing.huang
 */
public class NoticeService implements ArrayContentProvider {

	/**
	 * 获取kcomp_notice_announcement所有信息
	 * 
	 * @param notifyXh
	 * @return
	 */
	public Noticeannouncement getInfo(String notifyXh) {

		Dao<Noticeannouncement> dao = DaoFactory
				.create(Noticeannouncement.class);

		Noticeannouncement notify = dao.selectByID(notifyXh);

		// System.out.println("通知标题"+notify.getNotifyName());

		return notify;
	}

	/**
	 * 通过条件 获取通知公告列表
	 * 
	 * @param scopeId
	 *            发布范围id 即机构id
	 * @param notifyTypeId
	 *            通知类型id 分为紧急通知和一般通知
	 * @param index
	 * @param size
	 * @return
	 * @throws ServiceException
	 */
	public Page<Noticeannouncement> getNotifyListInfo(String scopeId,
			String notifyTypeId, int index, int size) throws ServiceException {
		Dao<Noticeannouncement> dao = DaoFactory
				.create(Noticeannouncement.class);
		SqlSession session = dao.getSession();
		List<Noticeannouncement> noticeannouncementList = new ArrayList<Noticeannouncement>();
		Page<Noticeannouncement> NoticeannouncementPage = null;
		try {

			// 调用自定义sql获得查询结果
			Noticeannouncement noticeannouncement = new Noticeannouncement();
			noticeannouncement.setNotifyTypeId(notifyTypeId);
		
			//
			OrgService orgservice = ApplicationContextManager.getContext().getBean(OrgService.class);
			List<OrgDTO> listorg = orgservice.queryFatherOrgs(scopeId, true);
			for(OrgDTO org:listorg){
				noticeannouncement.setScopeId(org.getOrgId());
				noticeannouncementList.addAll(dao.select(noticeannouncement));
			}
/*			noticeannouncementList = session
					.selectList(
							"com.chinacreator.c2.omp.service.manage.notificationmanage.NoticeannouncementMapper.selectNotifyList",
							noticeannouncement);
*/
			NoticeannouncementPage = new Page<Noticeannouncement>(index, size,
					noticeannouncementList.size(), noticeannouncementList);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return NoticeannouncementPage;
	}

	/**
	 * 通过条件 获取通知公告列表
	 * 
	 * @param scopeId
	 *            发布范围id 即机构id
	 * @param notifyTypeId
	 *            通知类型id 分为紧急通知和一般通知
	 * @param index
	 * @param size
	 * @return
	 * @throws ServiceException
	 */

	public List<Noticeannouncement> getNotifyList(String scopeId,
			String notifyTypeId, int index, int size) {
		Dao<Noticeannouncement> dao = DaoFactory
				.create(Noticeannouncement.class);
		SqlSession session = dao.getSession();
		List<Noticeannouncement> noticeannouncementList = new ArrayList<Noticeannouncement>();
		try {

			// 调用自定义sql获得查询结果
			Noticeannouncement noticeannouncement = new Noticeannouncement();
			noticeannouncement.setNotifyTypeId(notifyTypeId);
			noticeannouncement.setScopeId(scopeId);
			// 资产信息查询
			noticeannouncementList = session
					.selectList(
							"com.chinacreator.c2.omp.service.manage.notificationmanage.NoticeannouncementMapper.selectNotifyList",
							noticeannouncement);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return getNotifyListInfo(scopeId, notifyTypeId, index, size)
				.getContents();

	}

	@Override
	public Page<Noticeannouncement> getElements(ArrayContext arrayContext) {
		Dao<Noticeannouncement> dao = DaoFactory.create(Noticeannouncement.class);
		SqlSession session = dao.getSession();
		List<Noticeannouncement> noticeannouncementList = new ArrayList<Noticeannouncement>();
		
		Map<String,String> params=new HashMap<String,String>();
		Map<String,Object> selectMap = arrayContext.getCondition();
		UserService us = ApplicationContextManager.getContext().getBean(UserService.class);
		
		if(null!=selectMap.get("orgId")){
			params.put("orgId", selectMap.get("orgId").toString());
		}
		String orgtest=(String) selectMap.get("orgId");
		String userId =  (String) selectMap.get("userId");
		Noticeannouncement noticeannouncement = new Noticeannouncement();
		if(userId!=null&&userId.equals("1")){
			noticeannouncement.setScopeId(null);
			noticeannouncementList = dao.select(noticeannouncement);
		}else if(userId!=null&&!userId.equals("1")){
			OrgDTO org = us.queryMainOrg(userId);
			OrgService orgservice = ApplicationContextManager.getContext().getBean(OrgService.class);
			List<OrgDTO> listorg = orgservice.queryFatherOrgs(org.getOrgId(), true);
			for(OrgDTO org1:listorg){
				noticeannouncement.setScopeId(org1.getOrgId());
				noticeannouncementList.addAll(dao.select(noticeannouncement));
			}
		}
//		noticeannouncement.setScopeId(orgtest);
/*		noticeannouncementList = session
				.selectList(
						"com.chinacreator.c2.omp.service.manage.notificationmanage.NoticeannouncementMapper.selectNotifyList",
						noticeannouncement);*/
		Page NoticeannouncementPage = new Page<Noticeannouncement>(arrayContext
				.getPageable().getPageIndex(), arrayContext.getPageable()
				.getPageSize(), noticeannouncementList.size(),
				noticeannouncementList);

		return NoticeannouncementPage;
	}

}
