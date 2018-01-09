/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hui.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hui.db.ConnDB;
import com.hui.model.Book;

 
public class BookDAO {
	
	private int searchCount;
	private String searchContent = null;
	 
	public String getBookNameByISBN(String ISBN) {
		String bookName = null;
		ConnDB db = new ConnDB();
		String sql = "select name from Book where ISBN='" + ISBN + "'";
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();

		try {
			if (rs.next()) {
				bookName = rs.getString(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return bookName;
	}
 
	public int getBookNumByISBN(String ISBN) {
		int bookNum = 0;
		ConnDB db = new ConnDB();
		String sql = "select number from Book where ISBN='" + ISBN + "'";
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try {
			if (rs.next()) {
				bookNum = rs.getInt(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return bookNum;
	}

	 
	public boolean updateBookNum(String ISBN, String Mark) {
		boolean result = false;
		ConnDB db = new ConnDB();
		db.connect();
		int num = getBookNumByISBN(ISBN);
		String sql;
		
		if (Mark.equals("return")) {
			sql = "update Book set number=" + (num + 1) + " where ISBN='" + ISBN + "'";

		} else if (Mark.equals("borrow")) {
			if (num < 1) {
				return false;
			}
			sql = "update Book set number=" + (num - 1) + " where ISBN='" + ISBN + "'";
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

	 
	public Book searchBookByISBN(String isbn) {
		Book book = new Book();
		ConnDB db = new ConnDB();
		String sql = "select * from Book where ISBN='" + isbn + "'";
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();

		try {
			if (rs.next()) {
				book.setISBN(rs.getString(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setCategory(rs.getString(4));
				book.setPrice(rs.getDouble(5));
				book.setNumber(rs.getInt(6));
			}
		} catch (SQLException ex) {
			Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}

		return book;
	}

 
	

	 
	public boolean addBook(Book book) {
		boolean result = false;
		ConnDB db = new ConnDB();
		String data = "'" + book.getISBN() + "','" + book.getName() + "','" + book.getAuthor() +  "','" + 
					  book.getCategory() + "','" + book.getPrice() + "','" + book.getNumber() + "'";
		String sql = "insert into Book (ISBN,name,author,category,price, number) values('"+ book.getISBN() + 
				"','" + book.getName() + "','" + book.getAuthor()+"','"+book.getCategory()+"',"+book.getPrice() +","+book.getNumber() +")";
		System.out.println("addBookSql"+sql);
		db.connect();
		db.setSqlStatement(sql);
		db.execUpdate();
		if (db.getExecUpdateNum() != 0) {
			result = true;
		}
		db.close();
		return result;
	}

	 
	public boolean delBookByISBN(String isbn) {
		boolean result = false;
		String sql = "delete from Book where ISBN='" + isbn + "'";
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
	
	public boolean updateBook(Book book) {
		boolean result = false;
		String sql = "update Book set name='" + book.getName() + "',author='" + book.getAuthor() +  
					"',category='" + book.getCategory() + "',price='" + book.getPrice() +  
					"',number='" + book.getNumber() + "' where ISBN='" + book.getISBN() + "'";
		System.out.println("updatesql=" + sql);
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
	
	/**
	 * 根据搜索内容进行模糊查找，返回一页的结果
	 * <p/>
	 * @param pageNow  当前页
	 * @param pageSize 每页结果数量
	 * <p/>
	 * @return ArrayList 
	 */
	public ArrayList searchBook(int pageNow, int pageSize) {
		ArrayList<Book> list = new ArrayList<Book>();
		String sql = "select * from Book where concat(ISBN,author,name) like '%" + searchContent + "%' and concat(ISBN,author,name) is not null limit " + (pageNow - 1) * pageSize + "," + pageSize;
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		ResultSet rs = db.getResultSet();
		try {
			while (rs.next()) {
				Book b = new Book();

				b.setISBN(rs.getString(1));
				b.setName(rs.getString(2));
				b.setAuthor(rs.getString(3));
				b.setCategory(rs.getString(4));
				b.setPrice(rs.getDouble(5));
				b.setNumber(rs.getInt(6));

				list.add(b);
			}
		} catch (SQLException ex) {
			Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
		return list;
	}

	
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	/**
	 * 得到模糊查找数量
	 * <p/>
	 * @return int
	 */
	public int getSearchCount() {
		calcSearchCount();
		return searchCount;
	}

	/**
	 * 执行模糊查找数量统计
	 */
	private void calcSearchCount() {
		String sql = "select count(*) from Book where concat(author, name) like '%" + searchContent + "%' and  concat(author,name) is not null";
		ConnDB db = new ConnDB();
		db.connect();
		db.setSqlStatement(sql);
		db.execQuery();
		try {
			if (db.getResultSet().next()) {
				searchCount = db.getResultSet().getInt(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			db.close();
		}
	}
	 
	
}
