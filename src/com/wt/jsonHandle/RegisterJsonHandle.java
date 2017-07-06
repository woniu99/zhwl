package com.wt.jsonHandle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.wt.entity.User;
import com.wt.service.UserService;

public class RegisterJsonHandle extends ActionSupport implements 
	ModelDriven<User>, Preparable{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> dataMap;
	
	private UserService userService;
	
	private User model;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	
	// ע�ᴴ���û�
	public String save(){
		
		dataMap = new HashMap<String, Object>();
		
		ActionContext context = ActionContext.getContext();
		
		HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		
		String password = request.getParameter("password");
		
		String email = request.getParameter("email");
		
		String Str_userphone = request.getParameter("userphone");
		
		String captcha = request.getParameter("captcha");
		
		// �����ִ�Сд�Ĳ���������ȡ�� captcha ����תΪ��д��ĸ 
		captcha = captcha.toUpperCase();
		
		String validateCode = (String) session.getAttribute("validateCode");
		
		long userphone = Long.parseLong(Str_userphone);
		
		System.out.println(userphone);
		
		String useraddress = request.getParameter("useraddress");
		
		if(!captcha.equals(validateCode)){
			dataMap.put("data", "��֤�����");
			dataMap.put("code", 1);
//			System.out.println("error------��֤�����");
		}
		else{
			// �����ݿ��в���һ���û�����
			if(username != null && password != null && email != null && Str_userphone != null && useraddress != null){
				System.out.println("username --- " + username + " password --- " + password + " email--- " + email + " userphone --- " + userphone + "useraddress ---" + useraddress);
				
				User user = new User();
				
				user.setUser_name(username);
				user.setUser_password(password);
				user.setUser_email(email);
				user.setUser_phone(userphone);
				user.setUser_address(useraddress);
				
				// ����û�������ʱ��
				Date createTime = new Date();
				user.setCreateTime(createTime);
				
				// ����û�״̬����ʼʱ�û���״̬Ϊ 1 ����
				user.setUser_state(1);
				
				userService.saveOrUpdate(user);
				
				dataMap.put("user", model);
				dataMap.put("code", 0);
			}
		}

		return SUCCESS;
	}

	@Override
	public void prepare() throws Exception {}

	@Override
	public User getModel() {
		return model;
	}
	
}
