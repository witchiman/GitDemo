package com.hui.hibernate.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_group")
public class Group {
	private int id;
	private String name;
	//private Set<User> users = new HashSet<User>();//避免重复，只能使用Set
	//private List<User> users = new ArrayList<User>();
	private Map<Integer,User> users = new HashMap<Integer,User>();
	
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
	
	@OneToMany(mappedBy="group",cascade=CascadeType.ALL)
	@MapKey(name="id")
	public Map<Integer,User> getUsers() {
		return users;
	}
	public void setUsers(Map<Integer,User> users) {
		this.users = users;
	}
	
	
	/*@OneToMany(mappedBy="group",cascade=CascadeType.ALL	 
			,fetch=FetchType.LAZY  //此处默认为lazy
			)	
	@OrderBy("name ASC")                          //默认按主键升序排序
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}*/
	
	
	/*@OneToMany(mappedBy="group",cascade=CascadeType.ALL	 
			,fetch=FetchType.LAZY  //此处默认为lazy
			)	
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	*/
}
