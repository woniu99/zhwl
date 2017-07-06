package com.wt.utilMail;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


public class Test {
	
	public static void main(String[] args) {
		
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 
		ve.init();
		 
		Template template = ve.getTemplate("template/emailTemplate.vm", "gbk");
		VelocityContext ctx = new VelocityContext();
		
		// ������λ����ַ���
		String chars = "0123456789";  
	    String randomNum ="";
	    
		char[] rands = new char[6];  
		
	    for (int i = 0; i < 6; i++) { 
	    	int rand = (int) (Math.random() * 10);  
	        rands[i] = chars.charAt(rand);  
			randomNum += rands[i];
	    }  
	    
	    System.out.println(randomNum);
		 
		ctx.put("title", "�ǻ���������ϵͳ");
		ctx.put("username", "woniu99");
		ctx.put("randomNum", randomNum);
		 
		StringWriter sw = new StringWriter();
		 
		template.merge(ctx, sw);
		 
		System.out.println(sw.toString());
	 
		
		//�������Ҫ�������ʼ�   
		MailSenderInfo mailInfo = new MailSenderInfo();    
		mailInfo.setMailServerHost("smtp.163.com");    
		mailInfo.setMailServerPort("25");    
		mailInfo.setValidate(true);    
		mailInfo.setUserName("wo9niu9@163.com");
		
		// ��ͨsmtp�ȷ���ʱ���������������Ȩ��
		// ��ʱ�����Ϊ�������Ȩ�룬�����������������
		mailInfo.setPassword("woniu99"); 
      	mailInfo.setFromAddress("wo9niu9@163.com");    
      	mailInfo.setToAddress("994313551@qq.com");    
      	mailInfo.setSubject("�ǻ���������ϵͳ ��¼��Ϣ�һ�");    
      	mailInfo.setContent(sw.toString());    
      	//�������Ҫ�������ʼ�   
      	SimpleMailSender sms = new SimpleMailSender();   
      	//sms.sendTextMail(mailInfo);//���������ʽ    
      	
      	
		sms.sendHtmlMail(mailInfo);//����html��ʽ   
	}

}
