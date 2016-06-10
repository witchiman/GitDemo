package com.hui.ssh.service;

import java.util.List;

import com.hui.ssh.model.User;

public interface UserManager {

	public abstract boolean exists(User u) throws Exception;

	public abstract void add(User u) throws Exception;
	public List<User> getUsers();

}