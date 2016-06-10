package com.hui.dao.impl;


import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component("uerDAO")
public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory; 
	
	@Override
	public void save(User user) {
		
		 Session s = sessionFactory.getCurrentSession();
		 s.save(user);	 
	}
 
    
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

 


	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
 
 
}
