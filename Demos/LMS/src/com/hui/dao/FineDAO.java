package com.hui.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hui.db.ConnDB;
import com.hui.model.Fine;

public class FineDAO {
	private String countSql;
	private int rowCount;
	
	public boolean addFine(String sql) {
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execUpdate();
		db.close();		
		return true;
	}
	
	public ArrayList<Fine> showFine(String name, String nameType ,int pageNow,int pageSize) {
		ArrayList<Fine> list = new ArrayList<Fine>();
		String sql;
		if(nameType.equals("userName")) {   //用户只能看到自己的罚款记录
			sql = "select * from Fine where " + nameType + "='" + name + "' limit " + (pageNow-1)*pageSize + ","+ pageSize;
			countSql  = "select count(*) from Fine where " + nameType + "='" + name + "'";
		}else {//管理员可以看到所有用户的罚款记录
			sql = "select * from Fine where SN is not null limit " + (pageNow-1)*pageSize + ","+ pageSize;
			countSql  = "select count(*) from Fine where SN is not null";
		}
System.out.println(sql);
System.out.println(countSql);
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try {
			while(rs.next()) {
				Fine f = new Fine();
				f.setSN(rs.getString(1));
				f.setUserName(rs.getString(2));
				f.setISBN(rs.getString(3));
				f.setTime(rs.getDate(4));
				f.setFine(rs.getFloat(5));
				list.add(f);
			}
			
		}catch (SQLException ex){
			Logger.getLogger(FineDAO.class.getName()).log(Level.SEVERE, null, ex);
		}finally {
			db.close();
		}

		calrowCount();
		return list;
	}
	
	public void calrowCount() {
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(countSql);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try {
			if(rs.next()) {
				rowCount = rs.getInt(1);
			}
		} catch(SQLException ex) {
			Logger.getLogger(FineDAO.class.getName()).log(Level.SEVERE, null, ex);
		}finally {
			db.close();
		}
	}
	
	public int getRowCount() {
		return rowCount;
	}
}
