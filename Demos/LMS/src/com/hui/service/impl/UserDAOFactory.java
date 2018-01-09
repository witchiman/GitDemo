package com.hui.service.impl;


import com.hui.dao.PersonDAO;
import com.hui.dao.UserDAO;
import com.hui.service.IFactory;

public class UserDAOFactory implements IFactory{

	@Override
	public PersonDAO createPersonDAO() {
		// TODO Auto-generated method stub
		return new UserDAO();
	}
	
}
