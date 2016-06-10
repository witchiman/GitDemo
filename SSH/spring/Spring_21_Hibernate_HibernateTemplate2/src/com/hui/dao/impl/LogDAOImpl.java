package com.hui.dao.impl;

import org.springframework.stereotype.Component;

import com.hui.dao.LogDAO;
import com.hui.model.Log;

 @Component("logDAO")
public class LogDAOImpl extends SuperDAO implements LogDAO {
	 
	public void save(Log log) {
		this.getHiberanteTemplate().save(log);  
		 
	}
 

}
