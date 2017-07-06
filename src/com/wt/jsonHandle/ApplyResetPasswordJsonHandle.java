package com.wt.jsonHandle;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.CommonsLogLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wt.entity.User;
import com.wt.service.UserService;
import com.wt.utilMail.MailSenderInfo;
import com.wt.utilMail.SimpleMailSender;

public class ApplyResetPasswordJsonHandle extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> dataMap;

	private UserService userService;

	private Map<String, Object> session;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String jsonApplyResetPassword() {

		dataMap = new HashMap<String, Object>();

		session = ActionContext.getContext().getSession();

		ActionContext context = ActionContext.getContext();

		HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);

		// ��ȡ�û����������
		String email = request.getParameter("email");
		String captcha = request.getParameter("captcha");

		HttpSession httpSession = request.getSession();

		String validateCode = (String) httpSession.getAttribute("validateCode");

		// �����ִ�Сд�Ĳ���������ȡ�� captcha ����תΪ��д��ĸ
		captcha = captcha.toUpperCase();

		// ����Ϊ��֤�����Ƿ����
		User user = userService.emailCheck(email);

		String new_username = user.getUser_name();

		// System.out.println("11111111");

		// ��ʱΪ���䲻���ڵ���������Խ����ʼ��ķ���
		if (user == null) {
			dataMap.put("code", 1);
			dataMap.put("data", "����δ��ע�ᣬ���������룡");
		}
		// ��ʱΪ������ڵ���������Խ����ʼ��ķ���
		else {

			if (!captcha.equals(validateCode)) {
				dataMap.put("data", "��֤�����");
				dataMap.put("code", 1);
			} else {
				// System.out.println("22222222");

				// ����Ϊ�����ʼ��Ĺ���
				// �ϳ�֮ǰʹ�ô�ͳ�ķ����ʼ��ķ�ʽ
				// VelocityEngine ve = new VelocityEngine();
				// System.out.println("333333");
				// ve.setProperty(RuntimeConstants.RESOURCE_LOADER,
				// "classpath");
				// System.out.println("44444");
				// ve.setProperty("classpath.resource.loader.class",
				// ClasspathResourceLoader.class.getName());
				//
				// ve.init();
				// System.out.println("555555");

				// ����Ϊʹ���µ�ħ����еĸ��ģ����Է����ʼ��ɹ�
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

				// send Velocity log to SLF4J (via Commons Logging)
				ve.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, CommonsLogLogChute.class.getName());

				// this allows unit tests to detect missing context vars:
				ve.setProperty("runtime.references.strict", true);
				ve.init();

				Template template = ve.getTemplate("template/emailTemplate.vm", "gbk");
				VelocityContext ctx = new VelocityContext();

				// ������λ����ַ���
				String chars = "0123456789";
				String randomNum = "";

				char[] rands = new char[6];

				for (int i = 0; i < 6; i++) {
					int rand = (int) (Math.random() * 10);
					rands[i] = chars.charAt(rand);
					randomNum += rands[i];
				}

				System.out.println(randomNum);

				ctx.put("title", "�ǻ���������ϵͳ");
				ctx.put("username", new_username);
				ctx.put("randomNum", randomNum);

				StringWriter sw = new StringWriter();

				template.merge(ctx, sw);

				// System.out.println(sw.toString());

				// �������Ҫ�������ʼ�
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailServerHost("smtp.163.com");
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true);
				mailInfo.setUserName("wo9niu9@163.com");

				// ��ͨsmtp�ȷ���ʱ���������������Ȩ��
				// ��ʱ�����Ϊ�������Ȩ�룬�����������������
				mailInfo.setPassword("woniu99");
				mailInfo.setFromAddress("wo9niu9@163.com");
				mailInfo.setToAddress(email);
				mailInfo.setSubject("�ǻ���������ϵͳ ��¼��Ϣ�һ�~");
				mailInfo.setContent(sw.toString());

				// �������Ҫ�������ʼ�
				SimpleMailSender sms = new SimpleMailSender();

				sms.sendHtmlMail(mailInfo);// ����html��ʽ

				httpSession.setAttribute("email", email);
				httpSession.setAttribute("new_username", new_username);

				// �����ɵ���֤����д�� session ������
				httpSession.setAttribute("randomNum", randomNum);

				dataMap.put("data", "�ʼ����ͳɹ�,��ǰ������������գ�");
				dataMap.put("code", 0);
			}

		}

		return SUCCESS;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

}
