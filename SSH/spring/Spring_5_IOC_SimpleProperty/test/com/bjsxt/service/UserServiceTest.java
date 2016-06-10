package com.bjsxt.service;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hui.dao.impl.UserDAOImpl;
import com.hui.model.User;
import com.hui.service.UserService;


public class UserServiceTest {

	@Test
	public void testAdd() throws Exception {
		BeanFactory applicationContext = new ClassPathXmlApplicationContext("beans.xml");				
		UserService service = (UserService)applicationContext.getBean("userService");		
		
		User u = new User();
		u.setUsername("zhangsan");
		u.setPassword("zhangsan");
		service.add(u);
		UserDAOImpl impl = (UserDAOImpl)service.getUserDAO();
		
		System.out.println(impl.getDaoId()+ "||"+impl.getDaoStatus() );
	}    
	 
}
