package com.chinacreator.c2.omp.common.service.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.ioc.ApplicationContextManager;
//import com.chinacreator.c2.message.MessageRequest;
//import com.chinacreator.c2.message.MessageRequestBuilder;
//import com.chinacreator.c2.message.MessageSender;
import com.chinacreator.c2.omp.common.MessageServiceInfo;
import com.chinacreator.c2.omp.common.UserInfo;
import com.chinacreator.c2.omp.common.exception.ServiceException;
import com.chinacreator.c2.omp.common.service.UserInfoService;
import com.chinacreator.c2.omp.common.service.impl.MessageServiceRunnableImpl;
import com.chinacreator.c2.omp.common.service.inf.MessageServiceRunnable;
import com.chinacreator.c2.omp.common.service.inf.UtilConstants;
@Service
public class MessageService implements UtilConstants{
	/**
	 * 
	 *@title
	 *@param channel  消息推送渠道
	 *@param recipient 接收人多个使用逗号分隔(邮箱或者userId)
	 *@param content  消息内容
	 *@param paramExtMap  扩展参数{smtpHost:邮件服务器主机名(smtp服务器地址),username:登录邮箱的用户名,
	 *password:密码,from:发件人,fileNameArr:附件路径集合,title:邮件主题}
	 *@return
	 *@return  boolean
	 *@date 2016年1月6日
	 *@author tao.wang1
	 */
	
	public boolean sendMessage(String channel,String recipient,String content,Map<String,Object>  paramExtMap)  throws ServiceException{
		boolean result=false;
		String[] recipientArr=recipient.split(",");//接收人组
		String recipientTempOne = "";//接收人零时组
		String recipientTempTwo = "";//接收人零时组
		if(channel.indexOf(SYSTEM_SERVICE_CODE_MAIL)>-1){//邮箱服务
			List<MessageServiceInfo>  serviceInfoList=this.queryServiceByCategoryName(channel);//查询服务管理信息
			for(String user:recipientArr){
				if(user.indexOf("@")>-1){
					recipientTempOne+=user+",";
				}else{
					recipientTempTwo+=user+",";
				}
			}
			UserInfoService userInfoService = ApplicationContextManager.getContext().getBean(UserInfoService.class);
			List<UserInfo> userInfoList=null;
			if(!recipientTempTwo.isEmpty()){
				userInfoList = userInfoService.queryUserInfoByIds(recipientTempTwo.substring(0, recipientTempTwo.length()-1));//查询用户信息
			}
			recipient=recipientTempOne;
			if(serviceInfoList.size()>0){
				for(UserInfo userInfo:userInfoList){
					recipient+=userInfo.getUserEmail()+",";
				}
				recipient=recipient.substring(0,recipient.length()-1);
				String url="";
				for(MessageServiceInfo serviceInfo:serviceInfoList){
					url=serviceInfo.getUrl();
					if(null!=url&&!url.isEmpty()){
						JerseyService jerseyService = ApplicationContextManager.getContext().getBean(JerseyService.class);
						if(null!=content&&!content.isEmpty()){
							paramExtMap.put("content", content);
						}	
						if(null!=recipient&&!recipient.isEmpty()){
							paramExtMap.put("to", recipient);
						}		
//						url=url+"/sendMail";
						result=jerseyService.execute(url, JSONObject.toJSONString(paramExtMap));
					}
				}				
			}else{//默认使用平台提供插件
				for(UserInfo userInfo:userInfoList){
					recipient+=userInfo.getUserName()+",";
				}
				recipient=recipient.substring(0,recipient.length()-1);
				String category="";
				if(null!=paramExtMap.get("category")){
					category=paramExtMap.get("category").toString();
				}else{
					category="邮件通知";
				}
//				MessageRequest messageRequest=new MessageRequestBuilder().channel("email").to(recipient).content(content).category(category).build();
//				MessageSender.getInstance().send(messageRequest);
				result=true;				
			}

			/*String url=this.queryServiceAddress(channel);
			if(url.isEmpty()){//默认使用平台提供插件
				String category="";
				if(null!=paramExtMap.get("category")){
					category=paramExtMap.get("category").toString();
				}
				MessageRequest messageRequest=new MessageRequestBuilder().channel("email").to(recipient).content(content).category(category).build();
				MessageSender.getInstance().send(messageRequest);
				result=true;
			}else{
				JerseyService jerseyService = ApplicationContextManager.getContext().getBean(JerseyService.class);
				if(null!=content&&!content.isEmpty()){
					paramExtMap.put("content", content);
				}	
				if(null!=recipient&&!recipient.isEmpty()){
					paramExtMap.put("to", recipient);
				}		
				url=url+"/sendMail";
				result=jerseyService.execute(url, JSONObject.toJSONString(paramExtMap));
			}*/
		}
		return result;
	}
	/**
	 * 
	 *<p>title</p>
	 *@param channel  消息推送渠道
	 *@param recipient 接收人多个使用逗号分隔(邮箱或者userId)
	 *@param content  消息内容
	 *@param paramExtMap  扩展参数{smtpHost:邮件服务器主机名(smtp服务器地址),username:登录邮箱的用户名,
	 *password:密码,from:发件人,fileNameArr:附件路径集合,title:邮件主题}
	 *@author tao.wang1
	 *2016年1月20日
	 */
	public void sendMessageToThread(String channel,String recipient,String content,Map<String,Object>  paramExtMap) throws ServiceException{
		MessageServiceRunnable messageSR=new MessageServiceRunnableImpl(channel, recipient, content, paramExtMap);
		Thread thread = new Thread(messageSR);  
		thread.start();	
	}
	/**
	 * 
	 *@title  查询服务器url地址
	 *@param serviceCode 消息服务编码
	 *@return
	 *@return  String
	 *@date 2016年1月8日
	 *@author tao.wang1
	 */
	public String queryServiceAddress(String serviceCode)  throws ServiceException{
		String url="";//服务地址
		Dao<MessageServiceInfo> messageDao = DaoFactory.create(MessageServiceInfo.class);
		MessageServiceInfo messageServiceInfo  = new MessageServiceInfo();
		messageServiceInfo.setServiceCode(serviceCode);
		messageServiceInfo=messageDao.selectOne(messageServiceInfo);
		if(null!=messageServiceInfo){
			url=messageServiceInfo.getUrl();
		}
		return url;
	}
	/**
	 * 根据服务分类名称获取服务信息
	 * @param categoryName 服务分类名称
	 * @return
	 */
	public List<MessageServiceInfo> queryServiceByCategoryName(String categoryName)  throws ServiceException{
		Dao<MessageServiceInfo> messageDao = DaoFactory.create(MessageServiceInfo.class);
		SqlSession session = messageDao.getSession();
		Map<String,String> paramMap=new HashMap<String, String>();
		if(null!=categoryName&&!categoryName.isEmpty()){
			paramMap.put("categoryName", categoryName);
		}
		List<MessageServiceInfo> resultServiceInfo = session.selectList("com.chinacreator.c2.omp.common.MessageServiceInfoMapper.queryServiceByCategoryName", paramMap);
		return resultServiceInfo;
	}

	
}
