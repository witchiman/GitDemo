package com.hui.hibernate.model;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateIDTest {
private static SessionFactory sf = null;
	
    @BeforeClass
    public static void beforeClass() {
    	 try{
    		 Configuration cfg = new Configuration();
			 cfg.configure();
			 ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
			 sf = cfg.buildSessionFactory(sr);
    	 }catch(Exception e) {
    		 e.printStackTrace();
    	 }
    }
	
	@Test	
	public void test() {
		 Husband h = new Husband();
		
		 
		 Session session = sf.getCurrentSession();
		 session.beginTransaction();
		 session.save(h);
		 session.getTransaction().commit();
		 
	}
	
	@Test
	public void testSchemaExport() {		 
		new SchemaExport(new Configuration().configure()).create(true,true);
	}

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
