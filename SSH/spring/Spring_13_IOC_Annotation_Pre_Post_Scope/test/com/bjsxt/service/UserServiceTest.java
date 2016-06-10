package com.bjsxt.service;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hui.dao.impl.UserDAOImpl;
import com.hui.model.User;
import com.hui.service.UserService;
import com.sun.org.apache.bcel.internal.util.ClassPath;

 
public class UserServiceTest {

	@Test
	public void testAdd() throws Exception {
	    ClassPathXmlApplicationContext cp = new ClassPathXmlApplicationContext("beans.xml");				
		UserService service = (UserService)cp.getBean("userService");		
		 
		service.add(new User()); 
		
		cp.destroy();
	}    
	 
}
