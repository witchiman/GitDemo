package com.hui.hibernate.model;

import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateTest {
private static SessionFactory sf = null;
	
    @BeforeClass
    public static void beforeClass() {
    	new SchemaExport(new Configuration().configure()).create(true,true); //建表，或者设置hibernate.cfg.xml的hbm2ddl.auto属性.
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
	public void loadGroup() {
		  
		
		 
		 Session session = sf.getCurrentSession();
		 session.beginTransaction();
		 
		 Group g = (Group)session.get(Group.class, 1);
		 for(Entry<Integer,User> entry : g.getUsers().entrySet()) {
			 System.out.println(entry.getValue());
		 }
		  
		 session.getTransaction().commit();
		 
	} 
	 
	public static void main(String[] arsg) {
		beforeClass();
	}
	

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
