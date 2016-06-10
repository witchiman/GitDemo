package com.bjsxt.spring30.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bjsxt.spring30.model.User;
import com.bjsxt.spring30.service.UserService;

@ContextConfiguration("classpath:beans.xml")
public class UserServiceTest extends AbstractJUnit4SpringContextTests{
	
	@Resource(name="userService")
	private UserService userService;
	
	@Test
	public void testAdd() {
		this.userService.add(new User());
	}

}
