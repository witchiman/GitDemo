package com.hui.dao;
import com.hui.model.User;


public interface UserDAO {
	public void save(User user);
	public void delete();
}
