package com.wt.dao;

import java.util.List;

import org.hibernate.Query;

import com.wt.entity.Admin;
import com.wt.entity.User;

public class AdminDao extends BaseDao{

	// ��ѯ���еĹ���Ա
	public List<Admin> getAll(){
	
		String hql = "FROM Admin";
		
		return getSession().createQuery(hql).list();
		
	}
	
	// �û��ĵ�¼��֤
	public Admin adminCheck(String admin_name, String admin_password) {
		
		Admin admin = null;
		
		String hql = "FROM Admin a WHERE a.admin_name = ? and a.admin_password = ?";
		
		Query query = getSession().createQuery(hql);
		
		query.setString(0, admin_name);
		query.setString(1, admin_password);
		
		admin = (Admin) query.uniqueResult();
		
		return admin;
	}
	
}
