package com.hui.dao.impl;


import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component("userDAO")
public class UserDAOImpl extends SuperDAO implements UserDAO {
	 
	@Override
	public void save(User user) {
		   
		this.getHibernateTemplate().save(user);
		 	 
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
 
 
}
