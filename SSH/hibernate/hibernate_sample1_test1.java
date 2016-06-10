import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.hui.hibernate.model.Student;


public class StudentTest {

	public static void main(String[] args) {
		Student s = new Student();
		s.setId(28);
		s.setName("Ji");
		s.setAge(23);
		
		/*使用注解时 hibernate.cfg.xml里 
		加入<mapping class="com.hui.hibernate.model.sampleTest"/> */
		Configuration cfg = new Configuration();		
		cfg.configure();
		ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		SessionFactory sf = cfg.buildSessionFactory(sr);
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(s);
		session.getTransaction().commit();
		session.close();
		sf.close();
	}

}
