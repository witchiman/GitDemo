package com.hui.dao.impl;

import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component        //此处可以指定@Component的value,同时@Resource也要设置相关属性
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
