package com.hui.ssh.action;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserActionTest {

	@Test
	public void testExecute() throws Exception { 
		UserAction ua = new UserAction();
		ua.setUsername("j");
		ua.setPassword("j");
		String ret = ua.execute();
		assertEquals("success", ret);
	}

}
