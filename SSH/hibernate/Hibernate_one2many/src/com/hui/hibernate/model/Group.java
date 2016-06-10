package com.hui.hibernate.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_group")
public class Group {
	private int id;
	private String name;
	private Set<User> set = new HashSet<User>();//避免重复，只能使用Set
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@OneToMany
	@JoinColumn(name="groupId") //避免生成中间表，并且多对一和一对多的数据库模型是一样的，只有对象模型是不一 样的
	public Set<User> getSet() {
		return set;
	}
	public void setSet(Set<User> set) {
		this.set = set;
	}
	
}
