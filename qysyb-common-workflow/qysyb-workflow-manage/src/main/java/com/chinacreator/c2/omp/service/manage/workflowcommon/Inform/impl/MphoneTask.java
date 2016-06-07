package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.impl;

import java.util.HashMap;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.service.message.MessageService;
import com.chinacreator.c2.omp.service.manage.unitymessage.PhoneMessage;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.inf.InformTask;
import com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.service.InformService;

public class MphoneTask extends InformTask {

	public final static String CHANEL = "PHONE_MSG";
	MessageService messageService;
	private String content;
	private String emailTo;
	private String commentUserName;
	private String category;
	private String mobileTo;
//	private List<MEmailTask> subTasks = new ArrayList<MEmailTask>();
	public MphoneTask(String toId, String fromUserId,Map<String,String> contentMap){
		super(toId,fromUserId,contentMap);
	}
	public MphoneTask(UserDTO toId, UserDTO fromUserId,Map<String,String> contentMap){
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
	public void doInform() throws Exception {
		/**
			messageService = ApplicationContextManager.getContext().getBean(
				MessageService.class);
			Map map = new HashMap();
			map.put("category", category);
			map.put("title",
					"【" + contentMap.get(WorkFlowService.WORKTITLEKEY)
							+ "】有一条待办信息");
			messageService.sendMessage(MphoneTask.CHANEL,
					userTo.getUserName(), content, map);
			**/
		
			if(checkBeforMobile()){
				Dao<PhoneMessage> dao=DaoFactory.create(PhoneMessage.class);
			
				//String ss = dao.getSession().selectOne("selectMessageSeq","1");
				PhoneMessage phonemessage=new PhoneMessage();
				phonemessage.setMobileNum(mobileTo);
				phonemessage.setMessageContent(content);
				phonemessage.setSendState(0);
				///phonemessage.setOid(112223);
				dao.insert(phonemessage);
				System.out.println("phone sended to" + userTo.getUserRealname()
						+ "\ncontent:\n" + content);
			}else {
				throw new Exception(userTo.getUserName()+" ：手机号码为空!");
			}
	}
	
	private boolean checkBeforMobile(){
		// TODO Auto-generated method stub
		if(mobileTo == null){
			return false;
		}			
		return true;
	}
	
	public void setOtherContent(){
//		if(contentMap.get("category")!=null){
//			category = (String) contentMap.get(InformService.CATEGORY_KEY);
//		}
		if(contentMap.get(InformService.COMMENT_USERID_KEY)!=null){
			String userId = (String) contentMap.get(InformService.COMMENT_USERID_KEY);
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class); 
			UserDTO user = userService.queryByPK(userId);	
			commentUserName = user.getUserRealname();
		}
	}
	
	public void setWebTextContent(){
		if(contentMap.get(InformService.STR_TEMPLATE_KEY)!=null){
			content = "您有一条标题为 《"+contentMap.get(InformService.COMMENT_TITLE_KEY)+"》的"+contentMap.get(InformService.COMMENT_MODULE_KEY)+"待办事项需要处理!";
			if(userTo.getUserMobiletel1() ==null & userTo.getUserMobiletel2()!=null){
				mobileTo = userTo.getUserMobiletel2();
			}else  {
				mobileTo = userTo.getUserMobiletel1();
			}
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
		if (!(other instanceof MphoneTask))
			return false;
		MphoneTask other1 = (MphoneTask) other;
		this.ensureUserDTOToInitialized();
		other1.ensureUserDTOToInitialized();
		if (!this.userTo.getUserId().equals(other1.userTo.getUserId())&&this.emailTo!=null&&this.emailTo!=other1.emailTo)
			return false;
		if (!isContentEqualToThis(other1))
			return false;
		return true;
	}	

	public boolean isContentEqualToThis(Object other) {
		if (!(other instanceof MphoneTask))
			return false;
		MphoneTask other1 = (MphoneTask) other;
		if (!this.content.equals(other1.content)){
			return false;
		}
		return true;
	}
	
}
