package com.hui.hibernate.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@javax.persistence.TableGenerator(
	    name="T_GEN",
	    table="GENERATOR_TABLE",
	    pkColumnName = "t_pk",                    
	    valueColumnName = "t_value",
	    pkColumnValue="person_pk",
	    allocationSize=1,
	    initialValue=1
	)            
public class Person {
	private int id;
	private String name;
	
	@Id
	@GeneratedValue(generator="T_GEN",strategy=GenerationType.TABLE)
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
}
