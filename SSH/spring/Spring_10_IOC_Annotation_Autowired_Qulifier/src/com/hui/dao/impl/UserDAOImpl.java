package com.hui.dao.impl;

import com.hui.dao.UserDAO;
import com.hui.model.User;


public class UserDAOImpl implements UserDAO {
    private int daoId; 
	public void save(User user) {
		//Hibernate
		//JDBC
		//XML
		//NetWork
		System.out.println("user saved!");
	}
	public int getDaoId() {
		return daoId;
	}
	public void setDaoId(int daoId) {
		this.daoId = daoId;
	}
	 
	@Override
	public String toString() {
		return "daoId:" + daoId;
	}
}
