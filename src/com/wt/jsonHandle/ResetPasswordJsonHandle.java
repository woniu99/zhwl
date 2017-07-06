package com.wt.jsonHandle;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wt.entity.User;
import com.wt.service.UserService;

public class ResetPasswordJsonHandle extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> dataMap;
	
	private UserService userService;
	
	private Map<String, Object> session;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String jsonResetPassword() {
		
		dataMap = new HashMap<String, Object>();

		session = ActionContext.getContext().getSession();
		
		ActionContext context = ActionContext.getContext();
		
		HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		
		HttpSession httpSession = request.getSession();
		
		// ��ȡ�û���������������Ϣ
		String new_password = request.getParameter("new_password");
		String captcha = request.getParameter("captcha");
		String reset_captcha = request.getParameter("reset_captcha");
		
		System.out.println("new_password --- " + new_password);
		System.out.println("captcha --- " + captcha);
		System.out.println("reset_captcha --- " + reset_captcha);
		
		
		// ��ȡ�û�
		String email = (String) httpSession.getAttribute("email");
		User user = userService.emailCheck(email);
		
		String validateCode = (String) httpSession.getAttribute("validateCode");
		
		// �����ִ�Сд�Ĳ���������ȡ�� captcha ����תΪ��д��ĸ 
		captcha = captcha.toUpperCase();
		
		String randomNum = (String) httpSession.getAttribute("randomNum");
//		System.out.println("randomNum ----" + randomNum);
		
		if(!reset_captcha.equals(randomNum)){
//			System.out.println("!reset_captcha.equals(randomNum) ----");
			dataMap.put("data", "������Կ����, �����¼��ϵͳ�����ʼ���");
			dataMap.put("code", 1);
		}
		else{
			if(!captcha.equals(validateCode)){
//				System.out.println("!captcha.equals(validateCode) ----");
				dataMap.put("data", "��֤�����");
				dataMap.put("code", 1);
			}
			else{
//				System.out.println("captcha.equals(validateCode) ----");
				
				// ���±����û���Ϣ
				user.setUser_password(new_password);
				userService.saveOrUpdate(user);
				
				dataMap.put("data", "��������ɹ��������µ�¼��");
				dataMap.put("code", 0);
			}
		}
		
		return SUCCESS;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	
}
