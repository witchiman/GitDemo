/*
 * BO means Bean Operate
 * This class using Admin
 */
package com.hui.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hui.db.ConnDB;
import com.hui.model.Admin;
import com.hui.model.Person;



public class AdminDAO implements PersonDAO{

	 @Override
	public boolean checkPerson(String u, String p) {
		boolean result = false;
		ConnDB db = new ConnDB();
		String sql = "select password from Admin where name='" + u + "'";
		String pwdb = null;

		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		try {
			if (db.getResultSet().next()) {
				pwdb = db.getResultSet().getString(1);
			} else {
				return result; 
			}
		} catch (SQLException ex) {
			Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}

		if(p.equals(pwdb)){
			result = true;
		}

		return result;
	}

	public boolean createAdmin(String ID,String adminName, String password,String sex) {
		boolean reslut = false;
		ConnDB db = new ConnDB();
		String sql = "select * from admin where ID='" +ID + "'";

		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();

		try {
			if (db.getResultSet().next()) {
				return false;
			}			 
			String sql2 = "insert into admin(ID, adminName,password,sex) values('" +ID +"','"+ 
			adminName + "','" + password +  "','"+ sex+"')";
			db.setSqlStatement(sql2);
			db.execUpdate();
			if (db.getExecUpdateNum() != 0) {
				reslut = true;
			}

		} catch (SQLException ex) {
			Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}

		return reslut;

	}
    
	@Override
	public Person showPerson(String adminName) {
		Admin a = null;
		ConnDB db = new ConnDB();
		String sql = "select * from admin where name='" + adminName + "'";

		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();

		try {
			if (rs.next()) {
				a = new Admin();
				a.setID(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPassword(rs.getString(3));
				a.setSex(rs.getString(4));
			}
		} catch (SQLException ex) {
			Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		//System.out.println("管理员的ID："+ a.getID());
		return a;
	}
}
