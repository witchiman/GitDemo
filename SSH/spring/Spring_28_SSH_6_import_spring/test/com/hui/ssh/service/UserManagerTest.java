package com.hui.ssh.service;

import org.junit.Assert;
import org.junit.Test;

import com.hui.ssh.model.User;
import com.hui.ssh.service.impl.UserManagerImpl;

public class UserManagerTest {

	@Test
	public void testExists() throws Exception {
		UserManager um = new UserManagerImpl();
		User u = new User();
		u.setUsername("f");
		boolean exists = um.exists(u);
		 
		Assert.assertEquals(true, exists);
	}

	@Test
	public void testAdd() throws Exception { 
		UserManager um = new UserManagerImpl();
		User u = new User();
		u.setUsername("g");
		u.setPassword("g");
		boolean exists = um.exists(u);
		if(!exists) {
			um.add(u);
			u.setUsername("g");
			Assert.assertEquals(true, um.exists(u));
		}else {
			Assert.fail("not added!");
		}
		
	}

}
