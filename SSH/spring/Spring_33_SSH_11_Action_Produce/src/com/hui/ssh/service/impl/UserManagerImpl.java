package com.hui.ssh.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hui.ssh.dao.UserDao;
import com.hui.ssh.dao.impl.UserDaoImpl;
import com.hui.ssh.model.User;
import com.hui.ssh.service.UserManager;

@Component("userManager")
public class UserManagerImpl implements UserManager {
	private UserDao userDao;
	 	
	 
	@Override
	public boolean exists(User u ) throws Exception {	 
		 return userDao.checkUserExistsWithName(u.getUsername());
	}
	 
	@Override
	public void add(User u ) throws Exception {
		userDao.save(u);
	}

	public UserDao getUserDao() {
		return userDao;
	}
    @Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> getUsers() {
		return this.userDao.getUsers();
	}

	@Override
	public User loadUserById(int id) {
		return this.userDao.loadUserById(id);
	}
}
