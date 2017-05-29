package com.chinacreator.c2.qyb.workflow.group.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.flow.api.GroupType;
import com.chinacreator.c2.flow.detail.ChooseGroup;
import com.chinacreator.c2.flow.detail.ChooseUser;
import com.chinacreator.c2.flow.util.CommonUtil;
import com.chinacreator.c2.flow.util.WfConfig;
import com.chinacreator.c2.flow.util.WfUtils;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
import com.chinacreator.c2.web.ds.impl.DefaultTreeNode;
import com.chinacreator.c2.workflow.service.JobGroupType;
import com.chinacreator.c2.workflow.service.OrgGroupType;

public class OrgJobGroupsProvider implements TreeContentProvider {

	@Override
	public TreeNode[] getElements(TreeContext context) {

		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			
			boolean needUser = true;
			String needUserStr = (String) map.get("needUser");
			if(needUserStr != null && needUserStr.equals("false")){
				needUser = false;
			}
			
			String id=(String) map.get("id");
			String gid = WfUtils.parseToGroupId(id);
			String gType = WfUtils.parseToGroupTypePrex(id);
			String selectedUserIds = (String) map.get("selectedUserIds");
			String selectedGroupIds = (String) map.get("selectedGroupIds");
			List<String> selectedUserIdList = new ArrayList<String>();
			List<String> selectedGroupIdList = new ArrayList<String>();
			if(selectedUserIds != null){
				String[] userIds = selectedUserIds.split(",");
				for(String userId : userIds){
					selectedUserIdList.add(userId);
				}
			}
			if(selectedGroupIds != null){
				String[] groupIds = selectedGroupIds.split(",");
				for(String groupId : groupIds){
					selectedGroupIdList.add(groupId);
				}
			}
			
			WfConfig groupTypeConfig  = (WfConfig)ApplicationContextManager.getContext().getBean("wfConfig");
			
			//为空，查询所有组类型
			if(CommonUtil.stringNullOrEmpty(gType)&&CommonUtil.stringNullOrEmpty(gid)){
				try {
					
/*					TreeNode tnRoot = new DefaultTreeNode(null, "groups", "用户组",true);
					((DefaultTreeNode)tnRoot).setChkDisabled(true);
					nodes.add(tnRoot);*/
					
/*					for(GroupType groupType:groupTypeConfig.getGroupTypes()){
						TreeNode tnGroupType = new DefaultTreeNode("orgTree",groupType.getPrefix(),groupType.getGroupTypeDisplayName(),true);
						((DefaultTreeNode)tnGroupType).setChkDisabled(true);
						nodes.add(tnGroupType);
					}*/
					TreeNode jobGroupType = new DefaultTreeNode("jobgroup","$job","岗位组",true);
					nodes.add(jobGroupType);
					TreeNode orgGroupType = new DefaultTreeNode("orggroup","$org","机构组",true);
					nodes.add(orgGroupType);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				if("$job".equals(gType)){
					GroupType groupType = new JobGroupType();
					//查询当前组下的子组
					List<ChooseGroup>  groupList=groupType.getChildGroups(gid);
					for (ChooseGroup candidateGroup : groupList) {
						List<ChooseGroup>  child = groupType.getChildGroups(candidateGroup.getId());
						TreeNode tn = new DefaultTreeNode(null,groupType.getPrefix()+":"+candidateGroup.getId(),candidateGroup.getDisplayName(),child.size()>0);
						if(selectedGroupIdList.contains(groupType.getPrefix()+":"+candidateGroup.getId())) ((DefaultTreeNode)tn).setChecked(true);
						nodes.add(tn);
					}
					if(needUser){
						//查询当前组下用户
						List<ChooseUser>  userList=groupType.parseUsers(gid);
						for (ChooseUser candidateUser : userList) {
							TreeNode tnUser = new DefaultTreeNode(null,candidateUser.getId(),candidateUser.getDisplayName(),false);
							if(selectedUserIdList.contains(candidateUser.getId())) ((DefaultTreeNode)tnUser).setChecked(true);
							nodes.add(tnUser);
						}						
					}
				}else if("$org".equals(gType)){
					GroupType groupType = new OrgGroupType();
					//查询当前组下的子组
					List<ChooseGroup>  groupList=groupType.getChildGroups(gid);
					for (ChooseGroup candidateGroup : groupList) {
						List<ChooseGroup>  child = groupType.getChildGroups(candidateGroup.getId());
						TreeNode tn = new DefaultTreeNode(null,groupType.getPrefix()+":"+candidateGroup.getId(),candidateGroup.getDisplayName(),child.size()>0);
						if(selectedGroupIdList.contains(groupType.getPrefix()+":"+candidateGroup.getId())) ((DefaultTreeNode)tn).setChecked(true);
						nodes.add(tn);
					}				
					if(needUser){
						//查询当前组下用户
						List<ChooseUser>  userList=groupType.parseUsers(gid);
						for (ChooseUser candidateUser : userList) {
							TreeNode tnUser = new DefaultTreeNode(null,candidateUser.getId(),candidateUser.getDisplayName(),false);
							if(selectedUserIdList.contains(candidateUser.getId())) ((DefaultTreeNode)tnUser).setChecked(true);
							nodes.add(tnUser);
						}					
					}
				}
				

			}
			
		}
		return nodes.toArray(new TreeNode[nodes.size()]);
	}

}
