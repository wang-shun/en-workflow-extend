package com.chinacreator.c2.omp.common.service.impl;

import java.util.Map;

import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.service.inf.MessageServiceRunnable;
import com.chinacreator.c2.omp.common.service.message.MessageService;
/**
 * 
 *<p>多线程发送消息 </p>
 * @author tao.wang1
 * 2016年1月20日
 */
public class MessageServiceRunnableImpl extends MessageServiceRunnable{

	private  String channel;//渠道：如邮件、短信等
	private String recipient;//接收人多个使用逗号分隔
	private String content;//消息内容
	private Map<String,Object>  paramExtMap;//{smtpHost:邮件服务器主机名(smtp服务器地址),username:登录邮箱的用户名,
											//password:密码,from:发件人,fileNameArr:附件路径集合,title:邮件主题}
	MessageService messageService;
	public MessageServiceRunnableImpl(String channel,String recipient,String content,Map<String,Object>  paramExtMap){
		this.channel=channel;
		this.recipient=recipient;
		this.content=content;
		this.paramExtMap=paramExtMap;
	}
	/**
	 * 
	 *<p>发送消息</p>
	 * @author tao.wang1
	 * 2016年1月20日
	 */
	@Override
	public void sendMessageToThread() {
		messageService=ApplicationContextManager.getContext().getBean(MessageService.class);
		messageService.sendMessage(channel, recipient, content, paramExtMap);
	}

}
