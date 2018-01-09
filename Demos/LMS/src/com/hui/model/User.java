/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hui.model;

 
public class User implements Person{

	private int ID;
	private String name;
	private String password;
	private String sex;
	private String date;
	private int number;

	public User() {
	}

	

	public User(int ID, String name, String password, String sex, String date) {
		super();
		this.ID = ID;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.date = date;
	}



	public int getID() {
		return ID;
	}



	public void setID(int ID) {
		this.ID = ID;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public int getNumber() {
		return number;
	}



	public void setNumber(int number) {
		this.number = number;
	}


	 
 
}
