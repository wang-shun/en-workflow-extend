package com.chinacreator.c2.omp.service.manage.workflowcommon.Inform.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * <p>
 * Title: 使用javamail发送邮件
 * </p>
 */
public class MailUtils {

	String to = "";// 收件人
	String from = "";// 发件人
	String host = "";// smtp主机
	String username = "";
	String password = "";
	String filename = "";// 附件文件名
	String subject = "";// 邮件主题
	String content = "";// 邮件正文
	Vector<String> file = new Vector<String>();// 附件文件集合

	public static MailUtils mail = null;

	public static MailUtils getInstance() {
		if (mail == null) {
			mail = new MailUtils();

			Properties prop = new Properties();
			try {
				InputStream is = MailUtils.class.getClassLoader()
						.getResourceAsStream("mail.properties");
				prop.load(is);

				if (prop.getProperty("host") != null)
					mail.setHost(prop.getProperty("host"));
				else
					mail.setHost("smtp.163.com");
				if (prop.getProperty("username") != null)
					mail.setUserName(prop.getProperty("username"));
				else
					mail.setUserName("wuxuehong214@163.com");
				if (prop.getProperty("password") != null)
					mail.setPassWord(prop.getProperty("password"));
				else
					mail.setPassWord("15802540365xue");
				if (prop.getProperty("from") != null)
					mail.setFrom(prop.getProperty("from"));
				else
					mail.setFrom("wuxuehong214@163.com");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mail;
	}

	/**
	 * <br>
	 * 方法说明：默认构造器 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	private MailUtils() {
	}

	/**
	 * <br>
	 * 方法说明：构造器，提供直接的参数传入 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	private MailUtils(String to, String from, String smtpServer,
			String username, String password, String subject, String content) {
		this.to = to;
		this.from = from;
		this.host = smtpServer;
		this.username = username;
		this.password = password;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * <br>
	 * 方法说明：设置邮件服务器地址 <br>
	 * 输入参数：String host 邮件服务器地址名称 <br>
	 * 返回类型：
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * <br>
	 * 方法说明：设置登录服务器校验密码 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void setPassWord(String pwd) {
		this.password = pwd;
	}

	/**
	 * <br>
	 * 方法说明：设置登录服务器校验用户 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void setUserName(String usn) {
		this.username = usn;
	}

	/**
	 * <br>
	 * 方法说明：设置邮件发送目的邮箱 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * <br>
	 * 方法说明：设置邮件发送源邮箱 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * <br>
	 * 方法说明：设置邮件主题 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * <br>
	 * 方法说明：设置邮件内容 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * <br>
	 * 方法说明：把主题转换为中文 <br>
	 * 输入参数：String strText <br>
	 * 返回类型：
	 */
	public String transferChinese(String strText) {
		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(),
					"utf-8"), "utf-8", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	/**
	 * <br>
	 * 方法说明：往附件组合中添加附件 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void attachfile(String fname) {
		file.addElement(fname);
	}

	/**
	 * <br>
	 * 方法说明：发送邮件 <br>
	 * 输入参数： <br>
	 * 返回类型：boolean 成功为true，反之为false
	 * 
	 * @throws Exception
	 */
	public boolean sendMail() throws Exception {

		// 构造mail session
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.localhost", "127.0.0.1");
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		// Session session = Session.getDefaultInstance(props);
		// Session session = Session.getDefaultInstance(props, null);

		try {
			// 构造MimeMessage 并设定基本的值
			MimeMessage msg = new MimeMessage(session);
			// MimeMessage msg = new MimeMessage();
			msg.setFrom(new InternetAddress(from));

			// msg.addRecipients(Message.RecipientType.TO, address);
			// //这个只能是给一个人发送email
			msg.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(to));
			subject = transferChinese(subject);
			msg.setSubject(subject);

			// 构造Multipart
			Multipart mp = new MimeMultipart();

			// 向Multipart添加正文
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setContent(content, "text/html;charset=utf-8");

			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbpContent);

			// 向Multipart添加附件
			Enumeration<String> efile = file.elements();
			while (efile.hasMoreElements()) {

				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				// <span style="color: #ff0000;">//这个方法可以解决附件乱码问题。</span>
				String filename = new String(fds.getName().getBytes(),
						"ISO-8859-1");

				mbpFile.setFileName(filename);
				// 向MimeMessage添加（Multipart代表附件）
				mp.addBodyPart(mbpFile);

			}

			file.removeAllElements();
			// 向Multipart添加MimeMessage
			msg.setContent(mp);
			msg.setSentDate(new Date());
			msg.saveChanges();
			// 发送邮件

			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception mex) {
			mex.printStackTrace();
			// Exception ex = null;
			// if ((ex = mex.getNextException()) != null) {
			// ex.printStackTrace();
			// }
			throw mex;
		}
		return true;
	}

	public Vector<String> getFile() {
		return file;
	}

	/**
	 * <br>
	 * 方法说明：主方法，用于测试 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		MailUtils sendmail = new MailUtils();

		sendmail.setHost("smtp.163.com");
		sendmail.setUserName("wuxuehong214@163.com");
		sendmail.setPassWord("");
		sendmail.setTo("214592800@qq.com");
		sendmail.setFrom("wuxuehong214@163.com");
		sendmail.setSubject("你好，hahahahahah这是测试！");
		sendmail.setContent("你好这是一!!!!!个带多附件的测试！");
		// Mail sendmail = new
		// Mail("dujiang@sricnet.com","du_jiang@sohu.com","smtp.sohu.com","du_jiang","31415926","你好","胃，你好吗？");
		// sendmail.attachfile("abc/110227.png");
		// sendmail.attachfile("abc/110232.png");

		System.out.println(sendmail.sendMail());

	}
}
