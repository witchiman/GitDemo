package com.hui.hibernate;

import org.hibernate.LockOptions;
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
	public void testSchema() {
		new SchemaExport(new Configuration().configure()).create(true,true);   
	 }
	
    @Test
	public void testSave() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		Account a = new Account();
		a.setBalance(100);
		session.save(a);
			
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void testOperation1() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		Account a = (Account)session.load(Account.class, 1);
		int balance = a.getBalance();
		//do some caculations
		balance = balance - 10;
		a.setBalance(balance);
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void testPessimisticLock() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		Account a = (Account)session.load(Account.class, 1, LockOptions.UPGRADE); //原视频代码为LockMode（已经废弃）
		int balance = a.getBalance();
		//do some caculation
		balance = balance - 10;
		a.setBalance(balance);
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void testOptimisticLock() {
		Session session = sf.openSession();

		Session session2 = sf.openSession();

		
		
		
		session.beginTransaction();
		Account a1 = (Account) session.load(Account.class, 1);
		

		session2.beginTransaction();
		Account a2 = (Account) session2.load(Account.class, 1);
		
		a1.setBalance(900);
		a2.setBalance(1100);

		session.getTransaction().commit();
		System.out.println(a1.getVersion());

		session2.getTransaction().commit();
		System.out.println(a2.getVersion());

		session.close();
		session2.close();
	}

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
