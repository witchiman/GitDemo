package com.hui.model;

import java.sql.Date;

/** 
 */
public class Record {

	private String SN;	
	private String adminName;
	private String userName;	
	private String ISBN;
	private Date borrowDate;
	private Date returnDate;
	
	public Record() { 
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String SN) {
		this.SN = SN;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}


	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	
	

}
