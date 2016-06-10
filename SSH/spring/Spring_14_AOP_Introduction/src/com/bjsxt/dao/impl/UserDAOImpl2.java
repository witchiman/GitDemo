package com.bjsxt.dao.impl;
 
import com.bjsxt.dao.UserDAO;
import com.bjsxt.model.User;

//×éºÏ
public class UserDAOImpl2 implements UserDAO {
    private UserDAO userDAO = new UserDAOImpl();
	
	@Override
	public void save(User user) {
		System.out.println("save user...");
		userDAO.save(user);
	}

	@Override
	public void delete() {
		
		
	}

}
