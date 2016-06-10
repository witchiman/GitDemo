package com.bjsxt.service;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hui.dao.UserDAO;
import com.hui.dao.impl.UserDAOImpl;
import com.hui.model.User;
import com.hui.service.UserService;


public class UserServiceTest {

	@Test
	public void testAdd() throws Exception {
		BeanFactory bf = new ClassPathXmlApplicationContext("beans.xml");			 
		UserDAO  ud = (UserDAO) bf.getBean("u");
		System.out.println(ud);
	}    
	 
}
