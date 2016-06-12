package com.chinacreator.c2.qyb.workflow.inform.impl;

import java.util.HashMap;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.service.message.MessageService;
import com.chinacreator.c2.qyb.workflow.activiti.impl.WorkFlowService;
import com.chinacreator.c2.qyb.workflow.inform.inf.InformTask;

public class MEmailTask extends InformTask {

	public final static String MAILCHANEL = "mail";
	MessageService messageService;
	private String content;
	private String emailTo;
	private String commentUserName;
	private String category;
//	private List<MEmailTask> subTasks = new ArrayList<MEmailTask>();
	public MEmailTask(String toId, String fromUserId,Map<String,String> contentMap){
		super(toId,fromUserId,contentMap);
	}
	public MEmailTask(UserDTO toId, UserDTO fromUserId,Map<String,String> contentMap){
		super(toId,fromUserId,contentMap);
	}
	@Override
	public void init() {
		this.ensureUserDTOToInitialized();
		this.setOtherContent();
		this.setMailTextContent();
		System.out.println("email sended init!");
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
			messageService.sendMessage(MEmailTask.MAILCHANEL,
					userTo.getUserName(), content, map);
			System.out.println("email sended to" + userTo.getUserRealname()
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
	
	public void setMailTextContent(){
		if(contentMap.get(InformService.STR_TEMPLATE_KEY)!=null){
			String template = (String) contentMap.get(InformService.STR_TEMPLATE_KEY);
			if(template.equals(InformService.TASK_INFORM_EMAIL_TEMPLATE)){
				content= String.format(template, userTo.getUserRealname(),contentMap.get(InformService.WORKSTATGEKEY),null,null);
			}else if(template.equals(InformService.COMMENT_INFORM_EMAIL_TEMPLATE)){
				content= String.format(template, userTo.getUserRealname(),contentMap.get(InformService.WORKSTATGEKEY),contentMap.get(InformService.COMMENT_KEY),commentUserName,null,null);
			}
			
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
		if (!(other instanceof MEmailTask))
			return false;
		MEmailTask other1 = (MEmailTask) other;
		this.ensureUserDTOToInitialized();
		other1.ensureUserDTOToInitialized();
		if (!this.userTo.getUserId().equals(other1.userTo.getUserId())&&this.emailTo!=null&&this.emailTo!=other1.emailTo)
			return false;
		if (!isContentEqualToThis(other1))
			return false;
		return true;
	}	

	public boolean isContentEqualToThis(Object other) {
		if (!(other instanceof MEmailTask))
			return false;
		MEmailTask other1 = (MEmailTask) other;
		if (!this.content.equals(other1.content)){
			return false;
		}
		return true;
	}
	
}
