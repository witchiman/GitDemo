package com.hui.ssh.dao;

import com.hui.ssh.model.User;

public interface UserDao {
	public void save(User u);
	public boolean checkUserExistsWithName(String username);
}
