package com.hui.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;

@Entity
@IdClass(WifePK.class)
public class Wife {
	private int id;
	private String name;
	private int age;
	 
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name =	 name;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	 
}
