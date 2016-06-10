package com.hui.service;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component("UserService")
public class UserService {
	private UserDAO userDAO;  
	public void add(User user) {
		userDAO.save(user);
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	@Resource(name="u")
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
