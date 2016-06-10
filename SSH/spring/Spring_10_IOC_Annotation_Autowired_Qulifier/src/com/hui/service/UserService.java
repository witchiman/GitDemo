package com.hui.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hui.dao.UserDAO;
import com.hui.model.User;



public class UserService {
	private UserDAO userDAO;  
	
	 
	public void add(User user) { 
		userDAO.save(user);
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	@Autowired                      //自动装配，默认是byType,如果有多个bean，则可使用@Qualifier指定
	public void setUserDAO(@Qualifier("u")UserDAO userDAO) {   
		this.userDAO = userDAO;
	}
	 
}
