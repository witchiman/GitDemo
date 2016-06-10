package com.hui.ssh.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.hui.ssh.dao.UserDao;
import com.hui.ssh.model.User;

@Component("userDao")
public class UserDaoImpl implements UserDao {
    
	private HibernateTemplate hibernateTemplate;
	@Override
	public void save(User u) {
		 /*SessionFactory sf = HibernateUtil.getSessionFactory();		  
		 Session s = sf.getCurrentSession();
		 s.beginTransaction();
		 s.save(u);
		 s.getTransaction().commit(); */
		hibernateTemplate.save(u);
		
	}

	@Override
	public boolean checkUserExistsWithName(String username) {		 
		/* SessionFactory sf = HibernateUtil.getSessionFactory();
		 Session s = sf.getCurrentSession();
		 s.beginTransaction();
		 Query query =  s.createQuery("select count(*) from User u where u.username=:username");
		 query.setParameter("username",username);
		 long count = (Long) query.uniqueResult();
		 s.getTransaction().commit();
		 if(count > 0) return true;
		 return false;*/
		 List<User> users = (List<User>) hibernateTemplate.find("from User u where u.username=?" , username);
		 if(users!=null && users.size()>0) {
			 return true;
		 }
		 return false;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<User> getUsers() {
		 return (List<User>) this.hibernateTemplate.find("from User");
	}

}
