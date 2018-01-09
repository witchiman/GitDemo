package com.hui.model;

import java.sql.Date;

public class Fine {
	private String SN;
	private String userName;
	private String ISBN;
	private Date time;
	float fine;
	
	public Fine() {
		
	}

	
	public String getSN() {
		return SN;
	}

	public void setSN(String SN) {
		this.SN = SN;
	}
 

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public float getFine() {
		return fine;
	}

	public void setFine(float fine) {
		this.fine = fine;
	}


	public String getISBN() {
		return ISBN;
	}


	public void setISBN(String iSBN) {
		this.ISBN = iSBN;
	}
	
	
	
}
