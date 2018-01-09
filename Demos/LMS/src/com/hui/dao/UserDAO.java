/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hui.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hui.db.ConnDB;
import com.hui.model.Person;
import com.hui.model.User;


 
public class UserDAO implements PersonDAO{
	
	@Override
	public  boolean checkPerson(String username, String password) {
		boolean result = false;
		ConnDB db = new ConnDB();
		String sql = "select password from User where name ='" + username + "'";
		String pwdb = null;
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		try {
			if (db.getResultSet().next()) {				 
				pwdb = db.getResultSet().getString(1);
			} else {
				return false; 
			}
		} catch (SQLException ex) {
			Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
        
		if(password.equals(pwdb)){
			result = true;
		}

		return result;
	}

	@Override
	public Person showPerson(String username) {
		User u = new User();
		String sql = "select * from User where name='" + username + "'";
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		try {
			if (db.getResultSet().next()) {
				//System.out.println("userµÄID£º"+db.getResultSet().getInt(1));
				u.setID(db.getResultSet().getInt(1));				
				u.setName(db.getResultSet().getString(2));
				u.setPassword(db.getResultSet().getString(3));
				u.setSex(db.getResultSet().getString(4));
				u.setDate(db.getResultSet().getString(5));
				u.setNumber(db.getResultSet().getInt(6));
			}
		} catch (Exception ex) {
			Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return u;
	}

	 
	public boolean updateUser(User u) {
		boolean result = false;
		String sql = "update User set password='" + u.getPassword() + 
				"',sex='" + u.getSex() + "',date='" + u.getDate() + "',number=" + u.getNumber() + 
				" where name='" + u.getName() + "'";
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execUpdate();
		if (db.getExecUpdateNum() != 0) {
			result = true;
		}
		db.close();
		return result;
	}

	 
	public boolean delUser(String username) {
		boolean result = false;
		String sql = "delete from User where name='" + username + "'";
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execUpdate();
		if (db.getExecUpdateNum() != 0) {
			result = true;
		}
		db.close();
		return result;

	}

	 
	public boolean addUser(User u) {
		boolean result = false;
		ConnDB db = new ConnDB();
		String sql = "select * from User where name='" + u.getName() + "'";
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();

		try {
			 
			if (db.getResultSet().next()) {
				return false;
			}
			 
			Date currentdate = new Date(System.currentTimeMillis());
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(currentdate);
			String sql2 = "insert into User (name,password,sex,date,number) values ('"  + u.getName() + "','" + 
						u.getPassword() + "','" + u.getSex() + "','" +  date + "','" +u.getNumber()+ "')";
			db.setSqlStatement(sql2);
			db.execUpdate();			 
			if (db.getExecUpdateNum() != 0) {
				result = true;
			}

		} catch (SQLException ex) {
			Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return result;
	}
	
	public int getUserNumber(String username) {
		int number = 0;
		ConnDB db = new ConnDB();
		String sql = "select number from User where name='" + username + "'";
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try {
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return number;
	}
	
	public boolean updateUserNumber(String username, String action) {
		boolean result = false;
		ConnDB db = new ConnDB();
		db.connect();
		int num = getUserNumber(username);
		String sql;
		
		if (action.equals("return")) {
			sql = "update User set number=" + (num + 1) + " where name='" + username + "'";
		} else if (action.equals("borrow")) {
			if (num <=0) {
				return false;
			}
			sql = "update User set number=" + (num - 1) + " where name='" + username + "'";
		} else {
			return result;
		}
		 
		db.setSqlStatement(sql);
		db.execUpdate();
		if (db.getExecUpdateNum() == 1) {
			result = true;
		}

		return result;
	}
	 
	public ArrayList<User> searchUser(String s, int pageNow, int pageSize) {
		ArrayList<User> list = new ArrayList<User>();
		String sql = "select * from User where concat(ID,name) like '%" + s + "%' and concat(ID,name) is not null limit " + 
				(pageNow - 1) * pageSize + "," + pageSize;
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try {
			while (rs.next()) {
				User u = new User();
				u.setID(rs.getInt(1));
				u.setName(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setSex(rs.getString(4));
				u.setDate(rs.getString(5));
				u.setNumber(rs.getInt(6));
				list.add(u);
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return list;
	}

	 
	public int getSearchCount(String s) {
		String sql = "select count(*) from User where concat(ID,name) like '%" + s + "%' and concat(ID,name) is not null";
		ConnDB db = new ConnDB();
		db.connect();
		System.out.println(sql);
		db.setSqlStatement(sql);
		db.execQuery();
		try {
			if (db.getResultSet().next()) {
				return db.getResultSet().getInt(1);

			}
		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return 0;
	}
}
