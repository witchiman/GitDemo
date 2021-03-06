package com.bjsxt.spring30.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bjsxt.spring30.dao.UserDAO;
import com.bjsxt.spring30.dao.impl.UserDAOImpl;
import com.bjsxt.spring30.service.UserService;
import com.bjsxt.spring30.service.impl.UserServiceImpl;

@Configuration
public class AppConfig {
	@Bean
	public UserDAO userDAO() {
		return new UserDAOImpl();
	}
	
	@Bean
	public UserService userService() {
		UserServiceImpl service = new UserServiceImpl();
		service.setUserDAO(userDAO());
		return service;
	}
}
