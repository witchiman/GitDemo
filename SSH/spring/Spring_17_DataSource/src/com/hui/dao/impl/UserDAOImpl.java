package com.hui.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.hui.dao.UserDAO;
import com.hui.model.User;

@Component("u")
public class UserDAOImpl implements UserDAO {
	private DataSource ds;
	
	public void save(User user) {
		try {
			Connection conn = ds.getConnection();
			conn.createStatement().execute("insert into users values (null,'zhangsan');");
			conn.close();
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
		System.out.println("user saved!"); 
	}

	@Override
	public void delete() {
		System.out.println("user delete!!!");
		
	}

	public DataSource getDs() {
		return ds;
	}
    
	@Resource
	public void setDs(DataSource ds) {
		this.ds = ds;
	}

}
