package com.hui.dao.impl;


import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component("uerDAO")
public class UserDAOImpl implements UserDAO {
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(User user) {
		   
		hibernateTemplate.save(user);
		 	 
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
 
 
}
