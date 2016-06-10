package com.bjsxt.spring30.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjsxt.spring30.model.User;

public class UserDAOTest2 {
	@Test
	public void testSave() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		UserDAO userDAO = context.getBean("userDAO", UserDAO.class);
		
		//UserDAO userDAO = (UserDAO)context.getBean("userDAO");
		userDAO.save(new User());
	}
}
