package com.chinacreator.c2.qyb.workflow.group.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.message.receiver.GroupDefinition;
import com.chinacreator.c2.qyb.workflow.usergroup.entity.ActIdUser;
@Service
public class ActGroupForMessage implements GroupDefinition {
	@Autowired
	UserJobService userJobService;
	@Override
	public String getPrefix() {
		return "$actgroup";
	}

	@Override
	public String getReceiverGroupDisplayName(String groupId) {
		return userJobService.getGroupById(groupId).getName();
	}

	@Override
	public String[] parse(String groupId) {
		List<ActIdUser> userList = userJobService.getAllUserFromGroup(groupId);
		List<String> userNameList = new ArrayList<String>();
		for (ActIdUser actIdUser : userList) {
			//first username
			userNameList.add(actIdUser.getFirst());
		}
		return ((String[]) userNameList.toArray(new String[userNameList.size()]));
	}

}
