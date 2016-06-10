package com.hui.ssh.action;

import org.junit.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		Assert.assertEquals("success", ret);
	}
	
	@Test
	public void testList() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserAction ua = (UserAction) ctx.getBean("user");
		ua.list();
		System.out.println(ua.getUsers().size());
		Assert.assertTrue(ua.getUsers().size()>0);
	}

}
