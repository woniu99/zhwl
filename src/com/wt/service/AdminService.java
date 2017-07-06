package com.wt.service;

import java.util.List;

import org.hibernate.Query;

import com.wt.dao.AdminDao;
import com.wt.entity.Admin;

public class AdminService {

	private AdminDao adminDao;
	
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	// ��ѯ���еĹ���Ա
	public List<Admin> getAll(){
	
		return adminDao.getAll();
		
	}
	
	// ����Ա�ĵ�¼��֤
	public Admin adminCheck(String admin_name, String admin_password) {
		
		return adminDao.adminCheck(admin_name, admin_password);
	}
	
}
