package com.hpe.Utils;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class utils {
	public static void sendEmail(String telephone, String inputemail,String activeCode) {
		// 创建服务器,配置服务器的信息
		Properties props = new Properties();
		// 配置邮箱服务器
		props.setProperty("mail.smtp.host", "smtp.sina.com");
		// 配置是否验证登录
		props.setProperty("mail.smtp.auth", "true");
		// 创建session对象
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("zou1003941268@sina.com", "cb51bb2678a7070f");
			}
		});
		// 创建一封邮件,配置信息
		MimeMessage message = new MimeMessage(session);
		try {
			// 发件人必须与登录账号一致
			message.setFrom(new InternetAddress("zou1003941268@sina.com"));
			// 收件人
			message.setRecipient(RecipientType.TO, new InternetAddress(inputemail));
			// 设置邮件标题
			message.setSubject("激活邮件");
			
			// 设置邮件正文
			
			message.setContent(
					"请在48个小时以内激活<a href='http://localhost:9082/bos_fore/activeCode?telephone="+telephone+"&activeCode="+activeCode+"'>激活链接</a>",
					"text/html;charset=utf-8");
			// 发送邮件
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
