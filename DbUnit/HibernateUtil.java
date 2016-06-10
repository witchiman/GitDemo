package com.hui.maven.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private final static SessionFactory FACTORY = buildSessionFactory();
	private static  SessionFactory buildSessionFactory() {
		Configuration cfg = new Configuration().configure();
		ServiceRegistry service = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		SessionFactory sessionFactory = cfg.buildSessionFactory(service);
		return sessionFactory;
	}
	
	public static SessionFactory getFactory() {
		return FACTORY;
	}
	
	public static Session openSession() {
		return FACTORY.getCurrentSession();
	}
}
