package com.hui.ssh.service;

import org.junit.Assert;
import org.junit.Test;

import com.hui.ssh.model.User;

public class UserManagerTest {

	@Test
	public void testExists() throws Exception {
		UserManager um = new UserManager();
		User u = new User();
		u.setUsername("e");
		boolean exists = um.exists(u);
		 
		Assert.assertEquals(true, exists);
	}

	@Test
	public void testAdd() throws Exception { 
		UserManager um = new UserManager();
		User u = new User();
		u.setUsername("f");
		u.setPassword("f");
		boolean exists = um.exists(u);
		if(!exists) {
			um.add(u);
			u.setUsername("f");
			Assert.assertEquals(true, um.exists(u));
		}else {
			Assert.fail("not added!");
		}
		
	}

}
