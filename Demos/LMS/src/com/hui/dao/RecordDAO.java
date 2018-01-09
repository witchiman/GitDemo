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
import com.hui.model.Record;

/* 
 */
public class RecordDAO {

	private int rowCount;
	private ArrayList<Record> list;
	String recordSQL;
	String countSQL;
	
	public int getRowCount() {
		return rowCount;
	}	
	 

	/**返回借阅记录*/
	public ArrayList<Record> getRecordList(String name, String nameType, String action, int pageNow, int pageSize) {
		
		list = new ArrayList<Record>();
		
		if (nameType.equals("username") || nameType.equals("adminname")) {
			 
			if (action.equals("return")) {
				//System.out.println("This is return");
				recordSQL = "select * from Record where returnDate is not null and " + nameType + "='" + name + "' limit " + (pageNow-1)*pageSize + "," + pageSize;
				countSQL = "select count(*) from record where returnDate is not NULL and " + nameType + "='" + name + "'";

			} else if (action.equals("borrow")) {
				//System.out.println("this is borrow");
				recordSQL = "select * from Record where returnDate is null and " + nameType + "='" + name + "' limit " + (pageNow-1)*pageSize + "," + pageSize;
				countSQL = "select count(*) from Record where returnDate is NULL and " + nameType + "='" + name + "'";
			} else {
				recordSQL = "select * from Record where " + nameType + "='" + name + "' limit " + (pageNow-1)*pageSize + "," + pageSize;
				countSQL = "select count(*) from Record where " + nameType + "='" + name + "'";
			}
		}  
		
		System.out.println(recordSQL);
		System.out.println(countSQL);
		showRecord();
		calcRowCount();
		return list;

	}

	/**查询记录总数
	 */
	private void calcRowCount() {
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(countSQL);
		db.execQuery();
		try {
			if (db.getResultSet().next()) {
				rowCount = db.getResultSet().getInt(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
	}
	
	/**添加借阅记录到list*/
	private void showRecord() {
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(recordSQL);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try {
			while (rs.next()) {
				Record r = new Record();
				r.setSN(rs.getString(1));
				r.setAdminName(rs.getString(2));
				r.setUserName(rs.getString(3));
				r.setISBN(rs.getString(4));
				r.setBorrowDate(rs.getDate(5));
				r.setReturnDate(rs.getDate(6));	
				list.add(r);
			}
		} catch (SQLException ex) {
			Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();

		}
	}
	
	/**
	 * 生成一个借阅记录*/
	public boolean borrowBook(String ISBN, String userName, String adminName) {
		//先检察馆藏，再检查用户可借书数量
		BookDAO bd = new BookDAO();
		if (!bd.updateBookNum(ISBN, "borrow")) {
			return false;
		}
		UserDAO ud = new UserDAO();
		if(!ud.updateUserNumber(userName, "borrow")){
			bd.updateBookNum(ISBN, "return");
			return false;
		};
		ConnDB db = new ConnDB();
		db.connect();
		Date currentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(currentDate);
		//System.out.println(date);
		String sql = "insert into Record (ISBN,adminName,userName,borrowDate) values ('" + ISBN + "','" +adminName + "','" + userName + "','" + date + "')";
		db.setSqlStatement(sql);
		db.execUpdate();
		db.close();
		return true;

	}
	
	public Date getBorrowdate(String SN) {
		Date borrowDate = null;
		ConnDB db = new ConnDB();
		db.connect();
		String sql = "select borrowDate from Record where SN=" + SN;
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try{
			if(rs.next()) {
				borrowDate = rs.getDate(1);
			}else {
				return null;
			}
		}catch (SQLException ex) {
			Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
		}finally {
			db.close();
		}
		return borrowDate;
	}
	 
	public float returnBook(String ISBN, String userName) {
		float fine = 0;
		ConnDB db = new ConnDB();
		db.connect();
		String sql = "select SN from Record where ISBN='" + ISBN + "' and userName='" + userName + "' and returnDate is null";
       
		db.setSqlStatement(sql);
		db.execQuery();
		String SN;;
		ResultSet rs = db.getResultSet();
		Date currentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String returnDate = df.format(currentDate);
		try {
			if (rs.next()) {
				SN = rs.getString(1);
			} else {
				return -1;
			}
			Date borrowDate = getBorrowdate(SN);
			long diff = currentDate.getTime()- borrowDate.getTime();
			long days = (long)diff/(1000*24*60*60);   //得到借阅日期与还书的当天日期的天数之差
			fine = (float) ((float)(days-30)*0.1);
			if(fine>0) {
				String tempSql ="insert into Fine (userName, ISBN,date,fine) values('" + userName +"',' " +ISBN +
						"','" +  returnDate + "'," +fine+")";
System.out.println("add fine sql:" + tempSql);
				FineDAO fd = new FineDAO();
				fd.addFine(tempSql);
			}else {
				fine = 0;
			}
			sql = "update Record set returnDate='" + returnDate + "' where SN=" + SN;
			db.setSqlStatement(sql);
			db.execUpdate();
			if (db.getExecUpdateNum() == 1) {
				BookDAO bd = new BookDAO();
				bd.updateBookNum(ISBN, "return");
				UserDAO ud = new UserDAO();
				ud.updateUserNumber(userName, "return");
			}

		} catch (SQLException ex) {
			Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return fine;

	}
}
