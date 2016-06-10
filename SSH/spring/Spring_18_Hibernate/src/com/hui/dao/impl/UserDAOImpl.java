package com.hui.dao.impl;


import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component("u")
public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory; 
	
	public void save(User user) {
		
		 Session s = sessionFactory.openSession();
		 s.beginTransaction();
		 s.save(user);
		 s.getTransaction().commit();
		 s.close();
	 
		 System.out.println("user saved!"); 
	}

	@Override
	public void delete() {
		System.out.println("user delete!!!");
		
	}
   
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	 @Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
 

}
