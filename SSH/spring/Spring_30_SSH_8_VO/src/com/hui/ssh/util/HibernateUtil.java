package com.hui.ssh.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
		private static SessionFactory sf;		
		static {
			Configuration cfg = new Configuration();
			cfg.configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
												.applySettings(cfg.getProperties()).build();
		    sf = cfg.buildSessionFactory(serviceRegistry);
		}
		
		public static SessionFactory getSessionFactory() {
			return sf;
		}
}
