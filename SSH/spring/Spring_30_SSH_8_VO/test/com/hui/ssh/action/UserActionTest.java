package com.hui.ssh.action;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hui.ssh.vo.UserRegisterInfo;

public class UserActionTest {

	@Test
	public void testExecute() throws Exception { 
		UserAction ua = new UserAction();
		UserRegisterInfo info = new UserRegisterInfo();
		info.setUsername("l");
		info.setUsername("l");
		info.setPassword("l");
		ua.setInfo(info);
		String ret = ua.execute();
		assertEquals("success", ret);
	}

}
