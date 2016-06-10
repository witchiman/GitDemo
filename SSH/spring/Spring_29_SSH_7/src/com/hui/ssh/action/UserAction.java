package com.hui.ssh.action;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.hui.ssh.model.User;
import com.hui.ssh.service.UserManager;
import com.opensymphony.xwork2.ActionSupport;

@Component("user")
public class UserAction extends ActionSupport{
	private UserManager um ;
	private String username;
	private String password;
	private String password2;
    
	@Override
	public String execute() throws Exception {
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		if(um.exists(u)) {
			return ERROR;
		}
		um.add(u);
		return SUCCESS;
	}
    
 
	public UserManager getUm() {
		return um;
	}
    
	@Resource(name="userManager")
	public void setUm(UserManager um) {
		this.um = um;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
}
