package com.bjsxt.spring30.service.impl;

import com.bjsxt.spring30.dao.UserDAO;
import com.bjsxt.spring30.model.User;
import com.bjsxt.spring30.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDAO userDAO;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void add(User u) {
		this.userDAO.save(u);
	}

}
