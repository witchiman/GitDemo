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
		Org o = new Org();
		o.setName("P_Com");
		Org o1 = new Org();
		o1.setName("C_Com1");
		Org o2 = new Org();
		o2.setName("C_Com2");
		Org o11 = new Org();
		o11.setName("C_Com1_Dep1");
		Org o12 = new Org();
		o12.setName("C_Com1_Dep2");
		
		o.getChildren().add(o1);
		o.getChildren().add(o2);
		o1.getChildren().add(o11);
		o1.getChildren().add(o12);
		o11.setParent(o1);
		o12.setParent(o1);
		o1.setParent(o);
		o2.setParent(o);
		
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		s.save(o);
		s.getTransaction().commit();
		
	}
	
	@Test
	public void testLoad() {
		testSave();
		
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		
		Org o = (Org)s.load(Org.class, 1);
		print(o,0);                     //µ÷ÓÃµÝ¹é		
		s.getTransaction().commit();
	}
	
	 private void print(Org o, int level) {
		String preStr="";
		 for(int i=0; i<level; i++) {
			 preStr+="----";
		 }
		 
		 System.out.println(preStr+o.getName());
		for(Org child : o.getChildren()) {
			print(child, level+1);
		}
		
	}
	public static void main(String[] args) {
		 beforeClass();
	 }
	

	@AfterClass
	public static void afterClass() {		 
		 
	}
}
