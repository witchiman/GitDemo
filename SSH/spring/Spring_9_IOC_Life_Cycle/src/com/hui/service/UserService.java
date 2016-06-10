package com.hui.service;
import com.hui.dao.UserDAO;
import com.hui.model.User;



public class UserService {
	private UserDAO userDAO;  
	
	public void init() {
		System.out.println("init");
	}
	
	public void add(User user) { 
		userDAO.save(user);
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void destory() {
		System.out.println("destory");
	}
}
