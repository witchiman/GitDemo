package com.bjsxt.spring30.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bjsxt.spring30.spel.MyBean;

@ContextConfiguration("classpath:beans.xml")
public class SpELTest extends AbstractJUnit4SpringContextTests{
	
	@Resource
	private MyBean my;
	
	@Test
	public void test01() {
		System.out.println(my.getRandomNumber());
		System.out.println(my.getUserRegion());
	}

}
