package com.hui.ssh.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.hui.ssh.dao.UserDao;
import com.hui.ssh.model.User;
import com.hui.ssh.util.HibernateUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public void save(User u) {
		 SessionFactory sf = HibernateUtil.getSessionFactory();		  
		 Session s = sf.getCurrentSession();
		 s.beginTransaction();
		 s.save(u);
		 s.getTransaction().commit(); 
		
	}

	@Override
	public boolean checkUserExistsWithName(String username) {
		  
		 SessionFactory sf = HibernateUtil.getSessionFactory();
		 Session s = sf.getCurrentSession();
		 s.beginTransaction();
		 Query query =  s.createQuery("select count(*) from User u where u.username=:username");
		 query.setParameter("username",username);
		 long count = (Long) query.uniqueResult();
		 s.getTransaction().commit();
		 if(count > 0) return true;
		 return false;
	}

}
