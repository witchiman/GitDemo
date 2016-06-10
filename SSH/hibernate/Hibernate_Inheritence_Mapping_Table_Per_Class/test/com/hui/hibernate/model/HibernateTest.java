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
    	new SchemaExport(new Configuration().configure()).create(true,true);
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
	public void testSave() {
		Student s = new Student();
		s.setName("s1");
		s.setScore(80);
		Teacher t = new Teacher();
		t.setName("t1");
		t.setTitle("senior");
		
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.save(s);
		session.save(t);
		session.getTransaction().commit();
	}
	
	@Test
	public void testLoad() {
		testSave();		 
		
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Student t = (Student)session.load(Student.class, 1);
		System.out.println(t.getScore());
		Person p = (Person)session.load(Person.class, 2); 
		System.out.println(p.getName());
		session.getTransaction().commit();
	}
	

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
