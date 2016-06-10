package com.hui.spring.factory;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ClassPathXmlApplicationContext implements BeanFactory {
	Map<String, Object> container = new HashMap<String, Object>();
	
	public ClassPathXmlApplicationContext(String filename) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filename));
		List list = document.selectNodes("/beans/bean");
		System.out.println("list.size():" + list.size());
		 
		for(int i=0; i< list.size(); i++) {
			Element e = (Element)list.get(i);
			System.out.println(e.getName());
			
			Attribute attrId = e.attribute("id");
			String id = attrId.getValue();
			Attribute attrName = e.attribute("class");
			String clazz = attrName.getValue();
			
			Object o = Class.forName(clazz).newInstance();			
			
			container.put(id, o);
		}
	}
	
	@Override
	public Object getBean(String id) {
		return container.get(id);
	}

}
