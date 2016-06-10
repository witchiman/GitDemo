package com.hui.service;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;


@Component("userService")
public class UserService {
	private UserDAO userDAO;  
	
	 
	public void add(User user) { 
		userDAO.save(user);
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	@Resource                                               
	public void setUserDAO(UserDAO userDAO) {   
		this.userDAO = userDAO;
	}
	 
}
