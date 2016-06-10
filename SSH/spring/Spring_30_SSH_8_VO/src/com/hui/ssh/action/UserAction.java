package com.hui.ssh.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hui.ssh.model.User;
import com.hui.ssh.service.UserManager;
import com.hui.ssh.vo.UserRegisterInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component("user")
public class UserAction extends ActionSupport implements ModelDriven {
	private UserManager um ;
	private UserRegisterInfo info = new UserRegisterInfo();
	
	@Override
	public String execute() throws Exception {
		 User u = new User(); 
		 u.setUsername(info.getUsername());
		 u.setPassword(info.getPassword());
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
	
}
