package com.wt.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.Color;     
import java.awt.Font;     
import java.awt.Graphics2D;     
import java.awt.image.BufferedImage;     
import java.util.Random;     
     


import javax.imageio.ImageIO;     
import javax.servlet.ServletException;     
import javax.servlet.ServletOutputStream;    
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VerifyCodeAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	/** 
    * ��֤��ͼƬ�Ŀ�ȡ�  
    */  
    private int width = 100;     
       
    /** 
    *  ��֤��ͼƬ�ĸ߶ȡ� 
    */  
    private int height = 30;     
     
    /** 
    * ��֤���ַ�����  
    */  
    private int codeCount = 4;     
     
    /** 
    * ����߶�    
    */  
    private int fontHeight;     
      
    /** 
    * ��һ���ַ���x��ֵ����Ϊ������ַ��������ε������������ǵ�x��ֵ��codeX�ı��� 
    */  
    private int codeX;     
      
    /** 
    * codeY ,��֤�ַ���y��ֵ����Ϊ��������ֵһ�� 
    */  
    private int codeY;     
     
    /** 
    * codeSequence ��ʾ�ַ�������ֵ�����ֵ 
    */  
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',     
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',     
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };     

    // ʵ�ִ�����֤��Ĺ���
	public void captcha(){
		
		ActionContext context = ActionContext.getContext();
		
		HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		
		HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
		
		
		//width-4 ��ȥ���Ҷ����λ�ã�ʹ��֤����Ӽ�����ʾ������Խ��Խ���С�  
        //codeCount+1     //�ȱȷ�����ʾ�Ŀ�ȣ������������ߵĿո�  
        codeX = (width-4) / (codeCount+1);  
        //height - 10 ������ʾ��֤��  
        fontHeight = height - 10;    
        codeY = height - 7;  
        
        
        // ����ͼ��buffer     
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     
        Graphics2D gd = buffImg.createGraphics();     
        // ����һ���������������     
        Random random = new Random();     
        // ��ͼ�����Ϊ��ɫ     
        //gd.setColor(Color.LIGHT_GRAY);     
        gd.fillRect(0, 0, width, height);     
        // �������壬����Ĵ�СӦ�ø���ͼƬ�ĸ߶�������     
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);     
        // �������塣     
        gd.setFont(font);     
        // ���߿�     
        //gd.setColor(Color.BLACK);     
        gd.drawRect(0, 0, width - 1, height - 1);     
        // �������160�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽��     
        gd.setColor(Color.gray);     
        for (int i = 0; i < 16; i++) {     
            int x = random.nextInt(width);     
            int y = random.nextInt(height);     
            int xl = random.nextInt(12);     
            int yl = random.nextInt(12);     
            gd.drawLine(x, y, x + xl, y + yl);     
        }     
        // randomCode���ڱ��������������֤�룬�Ա��û���¼�������֤��     
        StringBuffer randomCode = new StringBuffer();     
        int red = 0, green = 0, blue = 0;     
        // �������codeCount���ֵ���֤�롣     
        for (int i = 0; i < codeCount; i++) {     
            // �õ������������֤�����֡�     
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);     
            // �����������ɫ������������ɫֵ�����������ÿλ���ֵ���ɫֵ������ͬ��     
            red = random.nextInt(255);     
            green = random.nextInt(255);     
            blue = random.nextInt(255);     
            // �������������ɫ����֤����Ƶ�ͼ���С�     
            gd.setColor(new Color(red,green,blue));     
            gd.drawString(strRand, (i + 1) * codeX, codeY);     
            // ���������ĸ�����������һ��     
            randomCode.append(strRand);     
        }     
        // ����λ���ֵ���֤�뱣�浽Session�С�     
        HttpSession session = request.getSession();     
        session.setAttribute("validateCode", randomCode.toString());  
        // ��ֹͼ�񻺴档     
        response.setHeader("Pragma", "no-cache");     
        response.setHeader("Cache-Control", "no-cache");     
        response.setDateHeader("Expires", 0);     
     
        response.setContentType("image/jpeg");     
        // ��ͼ�������Servlet������С�     
        //ServletOutputStream sos;
        OutputStream sos;
        
		try {
			sos = response.getOutputStream();
	        ImageIO.write(buffImg, "jpeg", sos);  
	        sos.flush();
	        sos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error");
		}          
		
	}
	
}
