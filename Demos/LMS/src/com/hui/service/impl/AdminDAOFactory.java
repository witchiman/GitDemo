package com.hui.service.impl;

import com.hui.dao.AdminDAO;
import com.hui.dao.PersonDAO;
import com.hui.service.IFactory;

public class AdminDAOFactory implements IFactory{

	@Override
	public PersonDAO createPersonDAO() {
		// TODO Auto-generated method stub
		return new AdminDAO();
	}
	
}
