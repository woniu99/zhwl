package com.wt.fileHandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wt.entity.User;
import com.wt.service.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FileHandle extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
	
	private UserService userService;

    private Configuration configuration = new Configuration();
    
    public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
 // �����ĵ�ģ��
 	public void CreateDoc(Map<String, Object> dataMap, String fileName) throws FileNotFoundException{
         configuration.setDefaultEncoding("utf-8"); 
         
         /* ��ȡ request ���� */   
 		HttpServletRequest request = ServletActionContext.getRequest(); 
 		
 		/* �õ���ǰ�����ľ���·�� */
 		String contextPath = request.getSession().getServletContext().getRealPath("\\");   
 		
 		System.out.println("contextPath --- " +  contextPath);
         
         try {
 			configuration.setDirectoryForTemplateLoading(new File(contextPath + "template"));
 		} catch (IOException e1) {
 			e1.printStackTrace();
 		}
         
         Template template = null;
         
         try {
             template = configuration.getTemplate("template.ftl");
         } catch (Exception e) {
             System.out.println("template : " + e);
         }
         
         File outFile = new File(fileName);
         Writer out = null;
         FileOutputStream fos=null;
         
         try {
             fos = new FileOutputStream(outFile);
             OutputStreamWriter oWriter = new OutputStreamWriter(fos, "UTF-8");
             out = new BufferedWriter(oWriter); 
         } catch (Exception e) {
             System.out.println("fos : " + e);
         }
         
         try {  
             template.process(dataMap, out);  
             out.close();  
             fos.close();  
         } catch (TemplateException e) {  
             e.printStackTrace();  
         } catch (IOException e) {  
             e.printStackTrace();  
         }  
         
     }
 	
 	// װ������
 	public Map<String, Object> handle_dataMap(User user){
         Map<String, Object> dataMap = new HashMap<String, Object>();
         
         System.out.println("query name : " + user.getUser_name());
         
         dataMap.put("title", user.getUser_name());
         dataMap.put("user_id", user.getUser_id());
         dataMap.put("user_name", user.getUser_name());
         dataMap.put("user_email", user.getUser_email());
         dataMap.put("user_phone", user.getUser_phone());
         dataMap.put("user_address", user.getUser_address());
         dataMap.put("createTime", user.getCreateTime());
         dataMap.put("user_state", user.getUser_state());
         
         return dataMap;
     }
 	
 	public void listfile(File file,Map<String,String> map){
         if(!file.isFile()){
             File files[] = file.listFiles();
             
             for(File f : files){
                 listfile(f,map);
             }
         }else{
             String realName = file.getName().substring(file.getName().indexOf("_")+1);
             map.put(file.getName(), realName);
         }
     }
 	

	public String fileList() {
		
		session = ActionContext.getContext().getSession();

		List<User> userLists = userService.getAll();
		
		// ����Ϊ����ʱʹ��
		for (User user : userLists) {
			
			System.out.println("User_name --- " + user.getUser_name() + 
					"   user_state --- " + user.getUser_state());
			
		}

		session.put("userLists", userLists);
		
		
		// --------------------------------------------------------
		// ����Ϊ�ļ����ֵ���ز���
		
		
		/* ��ȡ request ���� */   
		HttpServletRequest request = ServletActionContext.getRequest(); 
		
		/* �õ��ļ�����ľ���·�� */
		String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/uploads");   
		
		System.out.println("savePath --- " +  savePath);
		
		File file = new File(savePath);
		
		if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
            file.mkdir();
        }
		
		// ����Ϊ�����û�����ϸ��Ϣ��
		for (User user : userLists) {

            Map<String, Object> dataMap = handle_dataMap(user);
            
            String filename = user.getUser_name();
            
            String uploadFilePath = savePath + "\\" + filename + "�û��������.doc";
            
            try {
				CreateDoc(dataMap, uploadFilePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		Map<String,String> fileNameMap = new HashMap<String,String>();
		listfile(new File(savePath), fileNameMap);

		session.put("fileNameMap", fileNameMap);
		
		return "fileList";
	}
	
	// ����Ϊ�ļ������ط���
	public void downLoadFile() throws IOException {
		
		/* ��ȡ request ���� */   
		HttpServletRequest request = ServletActionContext.getRequest(); 
		
		ActionContext ctx = ActionContext.getContext(); 
		
		/* ��ȡ response ���� */ 
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 
		
		// ��ȡ�ļ���
		String fileName = request.getParameter("fileName");
		
		// ȥ���ַ�������β�ո�
		fileName.trim();
		
		// תΪ���ı��룬������ĵ���������
		String utfFileName = new String(fileName.getBytes("iso-8859-1"),"UTF-8");
		
		// ����ʱʹ��
//		System.out.println("error --- fileName " + fileName);
//		System.out.println("utfFileName --- utfFileName " + utfFileName);
		
		/* �õ��ļ�����ľ���·�� */
		String fileSaveRootPath = request.getSession().getServletContext().getRealPath("/WEB-INF/uploads");   
		
		System.out.println("fileSaveRootPath --- " +  fileSaveRootPath);
		
		File file = new File(fileSaveRootPath+ "\\" + utfFileName);
		
		if(!file.exists()){
            request.setAttribute("message", "��Ҫ���ص���Դ�ѱ�ɾ������");
        }
		
		String realname = utfFileName.substring(utfFileName.indexOf("_")+1);
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + utfFileName);
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[1024];
        int len = 0;
        while((len=in.read(buffer))>0){
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
		
	}
	
}
