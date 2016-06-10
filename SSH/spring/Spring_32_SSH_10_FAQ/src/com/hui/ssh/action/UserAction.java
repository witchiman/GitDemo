package com.hui.ssh.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hui.ssh.model.User;
import com.hui.ssh.service.UserManager;
import com.hui.ssh.vo.UserRegisterInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

//@Component("u")             //struts2自动生成
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven {
	private UserManager userManager ;
	private UserRegisterInfo info = new UserRegisterInfo();
	private List<User> users = new ArrayList<User>();
	private User user;
	@Override
	public String execute() throws Exception {
		 User u = new User(); 
		 u.setUsername(info.getUsername());
		 u.setPassword(info.getPassword());
		if(userManager.exists(u)) {
			return ERROR;
		}
		userManager.add(u);
		return SUCCESS;
	}
    

	@Override
	public Object getModel() {
		return info;
	}


	public UserRegisterInfo getInfo() {
		return info;
	}


	public void setInfo(UserRegisterInfo info) {
		this.info = info;
	}
	
	public String list() {
		this.users = this.userManager.getUsers();
		return "list";
	}

	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	public String load() {
		this.user = this.userManager.loadUserById(info.getId());
		return "load";		
	}
  
     
	public UserManager getUserManager() {
		return userManager;
	} 
	
	//@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
