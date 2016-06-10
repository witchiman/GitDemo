package com.hui.hibernate.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="_Teacher")
@javax.persistence.TableGenerator(
	    name="Teacher_GEN",
	    table="GENERATOR_TABLE",
	    pkColumnName = "pk_key",                    //不能为key，否则与mysql里的关键词冲突。
	    valueColumnName = "pk_value",
	    pkColumnValue="teacher",
	    allocationSize=2
	)                                                             //使用TableGenerator，数据也能跨数据库平台，工作时很少遇到
@SequenceGenerator(name="TeacherSEQ",sequenceName="TeacherSEQ_DB")
public class Teacher {
	private int id;
	private String name;
	private String title;
	private String secret;
	private Date date;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="Teacher_GEN")
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Transient             //不会插入数据库
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Temporal(TemporalType.DATE)  //设置成只显示日期，默认为DATETIME。
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
 
}
