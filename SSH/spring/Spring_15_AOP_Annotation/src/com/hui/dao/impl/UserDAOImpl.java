package com.hui.dao.impl;

import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component("u")
public class UserDAOImpl implements UserDAO {

	public void save(User user) {
		//Hibernate
		//JDBC
		//XML
		//NetWork
		System.out.println("user saved!");
		//throw new RuntimeException("exception");
	}

	@Override
	public void delete() {
		System.out.println("user delete!!!");
		
	}

}
