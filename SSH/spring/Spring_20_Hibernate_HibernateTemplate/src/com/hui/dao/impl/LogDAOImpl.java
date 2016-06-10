package com.hui.dao.impl;


import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hui.dao.LogDAO;
import com.hui.dao.UserDAO;
import com.hui.model.Log;
import com.hui.model.User;

@Component("logDAO")
public class LogDAOImpl implements LogDAO {
	private SessionFactory sessionFactory; 
	
	public void save(Log log) {
		
		 Session s = sessionFactory.getCurrentSession();
		 s.save(log);
		 System.out.println("user saved!");
		 //throw new RuntimeException();   //抛出RuntimeException时事务会回滚。
		 
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
 

}
