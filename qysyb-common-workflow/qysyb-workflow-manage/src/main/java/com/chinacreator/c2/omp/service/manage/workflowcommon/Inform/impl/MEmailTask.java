package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;









import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.service.message.MessageService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.inf.EmailTask;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.service.InformService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.service.UserJobService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.service.WorkFlowService;

public class MEmailTask extends EmailTask {
	
	public final static String MAILCHANEL = "mail";
	MessageService messageService;
	
	private boolean isGroup;
	private String content;
	private String toId;
	private String commentUserName;
	private UserDTO user;
	private Map<String, String> contentMap;
	private String category;
	private String fromUserId;
	private List<MEmailTask> subTasks = new ArrayList<MEmailTask>();
//	private Template template;
	public MEmailTask(boolean isGroup,String toId, String fromUserId,Map<String,String> contentMap){
		this.isGroup = isGroup;
		this.toId = toId;
		this.fromUserId = fromUserId;
		this.contentMap = contentMap;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if(isGroup){
			UserJobService userJobService = ApplicationContextManager.getContext().getBean(UserJobService.class);
			List<UserDTO> us = userJobService.getAllUserJob(toId);		
			for(UserDTO user:us){
				MEmailTask task = new MEmailTask(false,user.getUserId(),null,contentMap);
				task.init();
				subTasks.add(task);
			}
		}else{
			this.setUserInfo();
			this.setOtherContent();
			this.setMailTextContent();
			
		}
		System.out.println("email sended init!");
	}

	@Override
	public void sendEmail(){
		// TODO Auto-generated method stub
		if(subTasks.size()>0){
			for(MEmailTask t:subTasks){
				t.run();
			}
		}else{
			messageService = ApplicationContextManager.getContext().getBean(MessageService.class);
			boolean valid = this.checkBeforSend();
			if(valid){
				Map map = new HashMap();
				map.put("category", category);
				map.put("title", "工单【"+contentMap.get(WorkFlowService.WORKTITLEKEY)+"】有新动向了");
				messageService.sendMessage(MEmailTask.MAILCHANEL, user.getUserName(), content,map);
				System.out.println("email sended to"+user.getUserRealname()+"\ncontent:\n"+content);
			}		
		}
		
	}
	
	private boolean checkBeforSend(){
		// TODO Auto-generated method stub
		if(category==null){
			System.out.println("category 为空");
			return false;
		}			
		return true;
	}

	public void setUserInfo(){
		if(!isGroup){
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class); 
			user = userService.queryByPK(toId);	
		}
	}
	
	public void setOtherContent(){
		if(contentMap.get("category")!=null){
			category = contentMap.get(InformService.CATEGORY_KEY);
		}
		if(contentMap.get(InformService.COMMENT_USERID_KEY)!=null){
			String userId = contentMap.get(InformService.COMMENT_USERID_KEY);
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class); 
			UserDTO user = userService.queryByPK(userId);	
			commentUserName = user.getUserRealname();
		}
	}
	
	public void setMailTextContent(){
		if(contentMap.get(InformService.STR_TEMPLATE_KEY)!=null){
			String template = contentMap.get(InformService.STR_TEMPLATE_KEY);
			if(template.equals(InformService.TASK_INFORM_EMAIL_TEMPLATE)){
				content= String.format(template, user.getUserRealname(),contentMap.get(InformService.WORKSTATGEKEY),null,null);
			}else if(template.equals(InformService.COMMENT_INFORM_EMAIL_TEMPLATE)){
				content= String.format(template, user.getUserRealname(),contentMap.get(InformService.WORKSTATGEKEY),contentMap.get(InformService.COMMENT_KEY),commentUserName,null,null);
			}
			
		}
		
/*		template.
		return VelocityEngineUtils.mergeTemplateIntoString(this.getVelocityEngine(), this.getTemplateName(), "UTF-8",model);*/
	}
	@Override
	public boolean equals(Object other){       //重写equals方法，后面最好重写hashCode方法
		if(this == other)                                      //先检查是否其自反性，后比较other是否为空。这样效率高
			return true;
		if(other == null)         
			return false;
		if(!(other instanceof MEmailTask))
			return false;
		MEmailTask other1 = (MEmailTask)other;
		if(!isGroup&&!this.user.getUserId().equals(other1.user.getUserId()))
			return false;
		if(!this.content.equals(other1.content))
			return false;
		return true;
	 	}
	
	@Override
	public int hashCode(){                 //hashCode主要是用来提高hash系统的查询效率。当hashCode中不进行任何操作时，可以直接让其返回 一常数，或者不进行重写。
		int result = getName().hashCode();
		result = 29 * result +this.content.hashCode();
		return result;
	}
	
	public List<MEmailTask> getSubTasks(){
		return subTasks;
	}
}
