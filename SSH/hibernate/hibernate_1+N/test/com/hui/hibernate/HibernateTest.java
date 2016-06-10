package com.hui.hibernate;

import java.util.Date;
import java.util.List;







import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateTest {
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
	public void testSave() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		for(int i=0; i<10; i++) {
			Category c = new Category();
			c.setName("c" + i);
			Topic t = new Topic();
			t.setCategory(c);
			t.setTitle("t" + i);
			t.setCreateDate(new Date());
			session.save(c);
			session.save(t);
		}
			
		session.getTransaction().commit();
		session.close();
	}
	
	//N+1
	@Test
	public void testQuery1() {
		Session session = sf.openSession();
		session.beginTransaction();
		//List<Topic> topics = (List<Topic>)session.createCriteria(Topic.class).list();
		List<Topic> topics = session.createQuery("from Topic").list();
					 
		
		for(Topic t : topics) {
			System.out.println(t.getId() + "-" + t.getTitle());
		}
		session.getTransaction().commit();
		session.close();
		
	}
	
	@Test
	public void testQuery2() {
		Session session = sf.openSession();
		session.beginTransaction();
		//List<Topic> topics = session.createCriteria(Topic.class).list();
		List<Topic> topics = session.createQuery("from Topic").list();
					 
		
		for(Topic t : topics) {
			System.out.println(t.getId() + "-" + t.getTitle());
			System.out.println(t.getCategory().getName());
		}
		session.getTransaction().commit();
		session.close();
		
	}
	
	//@BatchSize
	@Test
	public void testQuery3() {
		Session session = sf.openSession();
		session.beginTransaction();
		//List<Topic> topics = (List<Topic>)session.createCriteria(Topic.class).list();
		List<Topic> topics = session.createQuery("from Topic").list();
		
		for(Topic t : topics) {
			System.out.println(t.getId() + "-" + t.getTitle());
			System.out.println(t.getCategory().getName());
		}
		session.getTransaction().commit();
		session.close();
		
	}
	//join fetch
	@Test
	public void testQuery4() {
		Session session = sf.openSession();
		session.beginTransaction();
		//List<Topic> topics = (List<Topic>)session.createCriteria(Topic.class).list();
		List<Topic> topics = session.createQuery("from Topic t left join fetch t.category c").list(); //fetchType为EAGER时，初始会把Gategory每一条记录都取出。
		
		for(Topic t : topics) {
			System.out.println(t.getId() + "-" + t.getTitle());
			System.out.println(t.getCategory().getName());
		}
		session.getTransaction().commit();
		session.close();
		
	}
	
  @Test
	public void testSchema() {
		new SchemaExport(new Configuration().configure()).create(true,true);   
	 }
	

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
