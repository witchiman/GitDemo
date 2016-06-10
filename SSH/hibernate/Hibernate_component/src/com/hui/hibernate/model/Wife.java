package com.hui.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;

 
public class Wife {
	 
	private String wifeName;
	private int age;	 
 
	public String getWifeName() {
		return wifeName;
	}
	public void setWifeName(String name) {
		this.wifeName =	 name;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	 
}
