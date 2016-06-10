package com.hui.spring.factory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

 

public class Test {

	public static void main(String[] args) throws Exception  {
		/* Properties props = new Properties();
		 props.load(Test.class.getClassLoader()
				 .getResourceAsStream("com/hui/spring/factory/spring.properties"));
		 String vehicleType = props.getProperty("VehicleType");
		 System.out.println(vehicleType);
		 Object o = Class.forName(vehicleType).newInstance();
		 Moveable m = (Moveable)o;
		 m.run();
		 */
		
		 /*File类路径必须是src/..这样的格式 */
		BeanFactory factory = new ClassPathXmlApplicationContext("src/com/hui/spring/factory/applicationContext.xml");
		Object o = factory.getBean("v");
		Moveable m = (Moveable)o;
		m.run(); 
		 
		 
	}
}
