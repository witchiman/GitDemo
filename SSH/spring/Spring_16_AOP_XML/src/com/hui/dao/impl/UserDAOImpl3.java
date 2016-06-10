package com.hui.dao.impl;
 
import com.hui.model.User;

//ผฬณะ
public class UserDAOImpl3 extends UserDAOImpl {
    
	@Override
	public void save(User user) {
		System.out.println("save user...");
		super.save(user);
		 
	}

}
