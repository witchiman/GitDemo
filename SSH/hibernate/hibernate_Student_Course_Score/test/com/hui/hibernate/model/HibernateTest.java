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
    // new SchemaExport(new Configuration().configure()).create(true,true);  //由于生成的score表有问题，要手动建表
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
		Student s1 = new Student();
		s1.setName("zhangsan");
		Course c1 = new Course();
		c1.setName("java");
		Score score =new Score();
		score.setStudent(s1);
		score.setCourse(c1);
		
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		s.save(s1);
		s.save(c1);
		s.save(score);
		s.getTransaction().commit();
		
	}
	
	@Test
	public void testLoad() {
		testSave();
		
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		
		 Student s1 = (Student)s.load(Student.class, 1);
		 for(Course c : s1.getCourses()) {
			 System.out.println(c.getName());
		 }
		s.getTransaction().commit();
	}
	
	 
	public static void main(String[] args) {
		 beforeClass();
	 }
	

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
