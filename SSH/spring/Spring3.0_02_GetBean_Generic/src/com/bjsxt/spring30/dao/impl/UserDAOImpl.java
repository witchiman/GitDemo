package com.bjsxt.spring30.dao.impl;

import com.bjsxt.spring30.dao.UserDAO;
import com.bjsxt.spring30.model.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public void save(User u) {
		System.out.println("a user saved!");
	}

}
