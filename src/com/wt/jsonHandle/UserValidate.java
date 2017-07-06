package com.wt.jsonHandle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wt.entity.User;
import com.wt.service.UserService;

public class UserValidate extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private UserService userService;
	
	ActionContext context = ActionContext.getContext();
	
	HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	// �û�����֤
	public String usernameCheck(){
		
		String username = request.getParameter("username");
		
		// ��֤�û����Ƿ�ע��ʹ��
		User usernameCheckUser = userService.usernameCheck(username);
				
		// ����û����ѱ�ע�ᣬ���ظ��û����ѱ�ע�ᣡ�Ĵ�����Ϣ
		if(usernameCheckUser != null){
			return "usernameError";
		}
		else{
			return "usernameSuccess";
		}
	}
	
	// ������֤
	public String emailCheck(){
		
		String email = request.getParameter("email");
		
		// ��֤�����Ƿ�ע��ʹ��
		User emailCheckUser = userService.emailCheck(email);
				
		// ����û����ѱ�ע�ᣬ���ظ��û����ѱ�ע�ᣡ�Ĵ�����Ϣ
		if(emailCheckUser != null){
			return "emailError";
		}
		else{
			return "emailSuccess";
		}
	}
	
	// �����ʼ��޸�����ʱ��������֤
	public String resetPasswordEmailCheck(){
		
		String email = request.getParameter("email");
		
		// ��֤�����Ƿ�ע��ʹ��
		User emailCheckUser = userService.emailCheck(email);
				
		// ����û����ѱ�ע�ᣬ���ظ��û����ѱ�ע�ᣡ�Ĵ�����Ϣ
		if(emailCheckUser != null){
			return "emailSuccess";
		}
		else{
			return "emailError";
		}
	}
	
}
