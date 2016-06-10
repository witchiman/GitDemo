package com.bjsxt.service;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.bjsxt.dao.UserDAO;
import com.bjsxt.dao.impl.UserDAOImpl;
import com.bjsxt.model.User;
import com.bjsxt.spring.BeanFactory;
import com.bjsxt.spring.ClassPathXmlApplicationContext;
import com.hui.apo.LogIntercepter;


public class UserServiceTest {
	
	@Test
	public void testProxy() {
		UserDAO userDAO = new UserDAOImpl();
		LogIntercepter li = new LogIntercepter(userDAO);
		//第二个参数也可以为 new Class[]{UserDAO.class}		 
		UserDAO userDAOProxy =  (UserDAO) Proxy.newProxyInstance(userDAO.getClass().getClassLoader(), userDAO.getClass().getInterfaces(), li);
		userDAOProxy.delete();
		userDAOProxy.save(new User());
	}
}
    
	