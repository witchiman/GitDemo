/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hui.model;

/**
 * 
 */
public class Admin implements Person{

	private int ID;
	private String name;
	private String password;
	private String sex;

	public Admin() {
		 
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

	
}
