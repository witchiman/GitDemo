package com.hui.ssh.service;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.hui.ssh.model.User;
import com.hui.ssh.util.HibernateUtil;

public class UserManager {
	 	
	public boolean exists(User u ) throws Exception {
		  
		 SessionFactory sf = HibernateUtil.getSessionFactory();
		 Session s = sf.getCurrentSession();
		 s.beginTransaction();
		 Query query =  s.createQuery("select count(*) from User u where u.username=:username");
		 query.setParameter("username", u.getUsername());
		 long count = (Long) query.uniqueResult();
		 s.getTransaction().commit();
		 if(count > 0) return true;
		 return false;
	}
	
	public void add(User u ) throws Exception {
		 SessionFactory sf = HibernateUtil.getSessionFactory();		  
		 Session s = sf.getCurrentSession();
		 s.beginTransaction();
		 s.save(u);
		 s.getTransaction().commit(); 
	}
}
