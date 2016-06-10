package com.hui.hibernate.model;

import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TeacherTest {
	private static SessionFactory sf = null;
	
    @BeforeClass
    public static void beforeClass() {
    	Configuration cfg = new Configuration();
		 cfg.configure();
		 ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		 sf = cfg.buildSessionFactory(sr);
    }
	
	@Test	
	public void test() {
		Teacher t = new Teacher();
		 t.setId(12);
		 t.setName("Niker");
		 t.setTitle("ÖÐ¼¶");
		 
		 
		 Session session = sf.openSession();
		 session.beginTransaction();
		 session.save(t);
		 session.getTransaction().commit();
		 session.close();
	}
	
	@AfterClass
	public static void afterClass() {		 
		 sf.close();
	}
}
