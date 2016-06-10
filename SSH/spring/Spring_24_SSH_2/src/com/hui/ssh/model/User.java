package com.hui.ssh.model;

import com.hui.ssh.service.UserManager;

//贫血模型，充血模型
public class User {
	private String username;
	private int id;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/*public boolean exists() throws Exception {   //充血模型
		return new UserManager().exists(this);
	}*/
}
