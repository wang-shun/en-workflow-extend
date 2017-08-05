package com.chinacreator.c2.qyb.workflow.read.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.common.bean.WorkFlowActivity;
import com.chinacreator.c2.qyb.workflow.read.entity.ProcInsReadRecord;

/**
 * 流程传阅
 * 
 * @author qiao
 * 
 */
@Service
public class ProcInstReadService {

	public final static String SENDUSER_READ_KEY = "wfsendUserRead";
	public final static String SENDORG_READ_KEY = "wfsendOrgRead";

	/**
	 * 
	 * @param pirr
	 * @return
	 */
	public int saveRecord(ProcInsReadRecord pirr) {
		Dao<ProcInsReadRecord> dao = DaoFactory.create(ProcInsReadRecord.class);
		if (pirr.getId() != null) {
			return dao.update(pirr);
		} else {
			return dao.insert(pirr);
		}
	}
	/**
	 * 保存的话 一个实例 一个人 只能有一条记录
	 * @param pirrs
	 * @return
	 */
	public int saveRecords(List<ProcInsReadRecord> pirrs) {
		List<ProcInsReadRecord> adds = new ArrayList<ProcInsReadRecord>();
		List<ProcInsReadRecord> updates = new ArrayList<ProcInsReadRecord>();
		List<ProcInsReadRecord> dels = new ArrayList<ProcInsReadRecord>();
		Dao<ProcInsReadRecord> dao = DaoFactory.create(ProcInsReadRecord.class);
		for (ProcInsReadRecord procInsReadRecord : pirrs) {
			if (procInsReadRecord.getProcInstId() == null) {
				continue;
			}
			if (procInsReadRecord.getReceiveUserId() != null) {
				if (hasUserRecord(procInsReadRecord)) {
					ProcInsReadRecord exist = getUserRecord(procInsReadRecord.getProcInstId(),
							procInsReadRecord.getReceiveUserId());
					procInsReadRecord.setId(exist.getId());
					updates.add(procInsReadRecord);
				} else {
					procInsReadRecord.setSendTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					adds.add(procInsReadRecord);
				}
			}

		}
		dao.updateBatch(updates);
		return dao.insertBatch(adds);
	}

	public ProcInsReadRecord getUserRecord(String procInstId, String receiveUserId) {
		Dao<ProcInsReadRecord> dao = DaoFactory.create(ProcInsReadRecord.class);
		ProcInsReadRecord con = new ProcInsReadRecord();
		con.setProcInstId(procInstId);
		con.setProcInstId(receiveUserId);
		List<ProcInsReadRecord> list = dao.select(con);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	private boolean hasUserRecord(ProcInsReadRecord procInsReadRecord) {
		Dao<ProcInsReadRecord> dao = DaoFactory.create(ProcInsReadRecord.class);
		ProcInsReadRecord con = new ProcInsReadRecord();
		con.setProcInstId(procInsReadRecord.getProcInstId());
		con.setProcInstId(procInsReadRecord.getReceiveUserId());
		List<ProcInsReadRecord> list = dao.select(con);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param sendUserId
	 * @param businessKey
	 * @param procInsId
	 * @param moduleId
	 * @param activity
	 * @param map
	 * @return
	 */
	public int saveUserRecord(String sendUserId, String businessKey, String procInsId, String moduleId,
			WorkFlowActivity activity, Map<String, Object> map) {
		String userIdsstr = (String) map.get(SENDUSER_READ_KEY);
		String[] userIds = userIdsstr.split(",");
		String moduleName = (String) map.get(WorkFlowService.PRODUCTNAMEKEY);
		return addUserReadRecords(sendUserId, userIds, moduleId, businessKey, procInsId, moduleName, activity.getId(),
				activity.getName());
	}

	@Autowired
	UserService userService;

	/**
	 * 
	 * @param userId
	 * @param userIds
	 * @param moduleId
	 * @param businessKey
	 * @param moduleName
	 * @param activityId
	 * @param activityName
	 * @return
	 */
	public int addUserReadRecords(String userId, String[] userIds, String moduleId, String businessKey,
			String procInsId, String moduleName, String activityId, String activityName) {
		List<UserDTO> users = new ArrayList<UserDTO>();
		for (String id : userIds) {
			UserDTO dto = userService.queryByPK(id);
			users.add(dto);
		}
		return addUserReadRecords(userId, users, moduleId, businessKey, procInsId, moduleName, activityId, activityName);
	}
	
	/**
	 * 
	 * @param userId 发送userId
	 * @param userDTOs 接收的用户们
	 * @param moduleId 事项ID
	 * @param businessKey 
	 * @param procInsId
	 * @param moduleName 事项名
	 * @param activityId 
	 * @param activityName
	 * @return
	 */
	public int addUserReadRecords(String userId, List<UserDTO> userDTOs, String moduleId, String businessKey,
			String procInsId, String moduleName, String activityId, String activityName) {
		List<ProcInsReadRecord> pirrs = new ArrayList<ProcInsReadRecord>();
		for (UserDTO userDTO : userDTOs) {
			ProcInsReadRecord r = new ProcInsReadRecord();
			r.setSendUserId(userId);
			r.setReceiveUserId(userDTO.getUserId());
			r.setReceiveUserName(userDTO.getUserRealname());
			r.setProcInstId(procInsId);
			r.setSendActivityId(activityId);
			r.setSendActivityName(activityName);
			r.setSendModuleId(moduleId);
			r.setSendModuleName(moduleName);
			//状态初始化
			r.setReadStatus(0);
			r.setSignStatus(0);
			r.setReceiptStatus(0);
			pirrs.add(r);
		}
		return saveRecords(pirrs);
	}
	
	/**
	 * 设置传阅已读状态
	 * @param id
	 * @param status
	 */
	public void setReadStatus(String id, int status){
		Dao<ProcInsReadRecord> dao = DaoFactory.create(ProcInsReadRecord.class);
		ProcInsReadRecord con = new ProcInsReadRecord();
		con.setId(id);
		con.setReadStatus(status);
		if(status == 1){
			con.setReadTime(Calendar.getInstance().getTimeInMillis());
		}
		dao.update(con);
	}
	
	/**
	 * 设置传阅已读状态
	 * @param id
	 * @param status
	 */
	public void setReaded(String id){
		setReadStatus(id, 1);
	}	
}
