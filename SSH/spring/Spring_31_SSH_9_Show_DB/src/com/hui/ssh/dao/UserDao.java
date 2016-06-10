package com.hui.ssh.dao;

import java.util.List;

import com.hui.ssh.model.User;

public interface UserDao {
	public void save(User u);
	public boolean checkUserExistsWithName(String username);
	public List<User> getUsers();
}
