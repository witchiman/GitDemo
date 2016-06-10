package com.bjsxt.spring30.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bjsxt.spring30.dao.UserDAO;
import com.bjsxt.spring30.dao.impl.UserDAOImpl;

@Configuration
public class AppConfig {
	@Bean
	public UserDAO userDAO() {
		return new UserDAOImpl();
	}
	
	//xml
	//<bean id = "userDAO" class="userDAO()"
}
