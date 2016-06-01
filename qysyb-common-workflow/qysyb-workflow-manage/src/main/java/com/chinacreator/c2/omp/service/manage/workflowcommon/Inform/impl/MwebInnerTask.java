package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.impl;

import java.util.HashMap;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.service.message.MessageService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.inf.InformTask;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.service.InformService;
import com.chinacreator.c2.omp.service.manage.workflowcommon.service.WorkFlowService;

public class MwebInnerTask extends InformTask {

	public final static String CHANEL = "WEB_INNER";
	MessageService messageService;
	private String content;
	private String emailTo;
	private String commentUserName;
	private String category;
//	private List<MEmailTask> subTasks = new ArrayList<MEmailTask>();
	public MwebInnerTask(String toId, String fromUserId,Map<String,String> contentMap){
		super(toId,fromUserId,contentMap);
	}
	public MwebInnerTask(UserDTO toId, UserDTO fromUserId,Map<String,String> contentMap){
		super(toId,fromUserId,contentMap);
	}
	@Override
	public void init() {
		this.ensureUserDTOToInitialized();
		this.setOtherContent();
		this.setWebTextContent();
		System.out.println("web sended init!");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doInform() {
		// TODO Auto-generated method stub

		messageService = ApplicationContextManager.getContext().getBean(
				MessageService.class);
		boolean valid = this.checkBeforSend();
		if (valid) {
			Map map = new HashMap();
			map.put("category", category);
			map.put("title",
					"工单【" + contentMap.get(WorkFlowService.WORKTITLEKEY)
							+ "】有新动向了");
			messageService.sendMessage(MwebInnerTask.CHANEL,
					userTo.getUserName(), content, map);
			System.out.println("inner sended to" + userTo.getUserRealname()
					+ "\ncontent:\n" + content);
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
	
	public void setOtherContent(){
		if(contentMap.get("category")!=null){
			category = (String) contentMap.get(InformService.CATEGORY_KEY);
		}
		if(contentMap.get(InformService.COMMENT_USERID_KEY)!=null){
			String userId = (String) contentMap.get(InformService.COMMENT_USERID_KEY);
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class); 
			UserDTO user = userService.queryByPK(userId);	
			commentUserName = user.getUserRealname();
		}
	}
	
	public void setWebTextContent(){
		if(contentMap.get(InformService.STR_TEMPLATE_KEY)!=null){
			String template = (String) contentMap.get(InformService.STR_TEMPLATE_KEY);
			content = "测试测试!";
			/**
			if(template.equals(InformService.TASK_INFORM_EMAIL_TEMPLATE)){
				content= String.format(template, userTo.getUserRealname(),contentMap.get(InformService.WORKSTATGEKEY),null,null);
			}else if(template.equals(InformService.COMMENT_INFORM_EMAIL_TEMPLATE)){
				content= String.format(template, userTo.getUserRealname(),contentMap.get(InformService.WORKSTATGEKEY),contentMap.get(InformService.COMMENT_KEY),commentUserName,null,null);
			}
			**/
			
		}
		
/*		template.
		return VelocityEngineUtils.mergeTemplateIntoString(this.getVelocityEngine(), this.getTemplateName(), "UTF-8",model);*/
	}
	
	@Override
	public boolean equals(Object other) { // 重写equals方法，后面最好重写hashCode方法
		if (this == other) // 先检查是否其自反性，后比较other是否为空。这样效率高
			return true;
		if (other == null)
			return false;
		if (!(other instanceof MwebInnerTask))
			return false;
		MwebInnerTask other1 = (MwebInnerTask) other;
		this.ensureUserDTOToInitialized();
		other1.ensureUserDTOToInitialized();
		if (!this.userTo.getUserId().equals(other1.userTo.getUserId())&&this.emailTo!=null&&this.emailTo!=other1.emailTo)
			return false;
		if (!isContentEqualToThis(other1))
			return false;
		return true;
	}	

	public boolean isContentEqualToThis(Object other) {
		if (!(other instanceof MwebInnerTask))
			return false;
		MwebInnerTask other1 = (MwebInnerTask) other;
		if (!this.content.equals(other1.content)){
			return false;
		}
		return true;
	}
	
}
