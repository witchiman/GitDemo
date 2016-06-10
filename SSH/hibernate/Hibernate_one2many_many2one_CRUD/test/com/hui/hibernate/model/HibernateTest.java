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
	public void test() {
		  
		
		 
		 Session session = sf.getCurrentSession();
		 session.beginTransaction();
		 session.save(null);
		 session.getTransaction().commit();
		 
	}
	
	@Test
	public void testSchemaExport() {		 
		new SchemaExport(new Configuration().configure()).create(true,true);
	}
	
	@Test
	public void testSaveUser() {
		User u = new User();
		u.setName("u2");
		Group g = new Group();
		g.setName("g1");
		u.setGroup(g);
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		s.save(u);
		//s.save(g);
		s.getTransaction().commit();
	}
	
	@Test
	public void testSaveGroup() {
		User u1 = new User();
		u1.setName("u1");		
		User u2 = new User();
		u2.setName("u2");
		
		Group g = new Group();
		g.setName("g1");
		g.getUsers().add(u1);
		g.getUsers().add(u2);
		u1.setGroup(g);     //group中没有设定mappedBy,User为主导，必须在此处设定关联
		u2.setGroup(g);
		
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		//s.save(u);
		s.save(g);
		s.getTransaction().commit();
	}
	
	@Test
	public void testGetUser() {
		testSaveGroup();
				
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		User u = (User)s.get(User.class,1);   //User和group的信息都会被取出来
		s.getTransaction().commit();
	}
	
	@Test
	public void testGetGroup() {
		testSaveGroup();
				
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		Group g = (Group)s.get(Group.class,1);   //只会取出group的信息，除非在Group里设定OneToMany(fetch=FetchType.EAGER)
		for(User u : g.getUsers()) {
			System.out.println(u.getName());
		}
		s.getTransaction().commit();
	}
	
	@Test
	public void testUpdateUser1() {
		testSaveGroup();
				
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		User u = (User)s.get(User.class, 1);           
		u.setName("jim&&tom");
		u.getGroup().setName("gggg");
		s.getTransaction().commit();
	}
	
	@Test
	public void tesUpdatetUser2() {
		testSaveGroup();
				
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		User u = (User)s.get(User.class, 1); 	 
		s.getTransaction().commit();
		
		u.setName("ua");
		u.getGroup().setName("gggaaa");   //CascadeType为All时Group才更新，或者CascadeType为merge时调用merge方法而不是update方法。
		Session s2 =sf.getCurrentSession();
		s2.beginTransaction();
		s2.update(u);
		s2.getTransaction().commit();
		
		
	}
	
	@Test
	public void tesPersisttUser() {
		testSaveGroup();	 
		
		User u  = new User();
		u.setName("persistUser");
		Group g = new Group();
		g.setName("persistGroup");
		u.setGroup(g);
		
		Session s2 = sf.getCurrentSession();
		s2.beginTransaction();
		s2.persist(u);               //CascadeType应该改为PERSIST
		s2.getTransaction().commit();		
		
	}
	
	@Test
	public void tesDeletetUser() {
		testSaveGroup();	 
		
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		/*User u = (User) s.load(User.class, 1);
		s.delete(u);*/                          //所有级联的都会删除
		s.createQuery("delete from User u where u.id=1").executeUpdate(); //使用HQL，只删除一个user
		
		s.getTransaction().commit();		
		
	}
	
	@Test
	public void tesDeleteGroup() {
		testSaveGroup();	 
		
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		Group g = (Group)s.load(Group.class, 1);
		s.delete(g);          //全部删除
		//s.createQuery("delete from User u where u.id=1").executeUpdate(); //使用HQL，只删除一个user
		
		s.getTransaction().commit();		
		
	}

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
