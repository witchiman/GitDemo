package com.bjsxt.spring30.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bjsxt.spring30.model.User;

@ContextConfiguration("classpath:beans.xml")
public class UserDAOTest extends AbstractJUnit4SpringContextTests{
	
	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Test
	public void testSave() {
		this.userDAO.save(new User());
	}

}
