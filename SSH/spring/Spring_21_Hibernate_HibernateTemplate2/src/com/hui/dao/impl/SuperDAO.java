package com.hui.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component("supperDAO")
public class SuperDAO {
	private HibernateTemplate hiberanteTemplate;

	public HibernateTemplate getHiberanteTemplate() {
		return hiberanteTemplate;
	}
	
	@Resource
	public void setHiberanteTemplate(HibernateTemplate hiberanteTemplate) {
		this.hiberanteTemplate = hiberanteTemplate;
	}
}
