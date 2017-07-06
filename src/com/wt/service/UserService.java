package com.wt.service;

import java.util.List;

import com.wt.dao.UserDao;
import com.wt.entity.User;

public class UserService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	// �����û��б�
	public List<User> getAll(){
		
		List<User> users = userDao.getAll();
		
		return users;
		
	}
	
	// �û���¼����֤
	public User userCheck(String username, String password) {
		return userDao.userCheck(username, password);
	}
	
	// �û�ע�ᣬ�����ݿ��в���һ���û�����
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}
	
	// �����Ƿ�ע��ļ���
	public User emailCheck(String email){
		return userDao.emailCheck(email);
	}

	// �û����Ƿ�ע��ļ���
	public User usernameCheck(String username){
		return userDao.usernameCheck(username);
	}
	
	// ɾ���û�
	public void delete(Integer id){
		userDao.delete(id);
	}
	
	// �༭�û�
	public void update(Integer id, Integer userLevel) {
		userDao.updateUser(id, userLevel);
	}
	
}
