package com.hui.dom4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class DOM4JTest {

	public static void main(String[] args) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("XMLTest.xml"));
		Element rootElement = document.getRootElement(); 	 //获取根结点
		System.out.println(rootElement.getName());
		
		/*for(Iterator  i=rootElement.elementIterator();i.hasNext();) {
			Element element = (Element)i.next();  	//获取子结点
			System.out.println(element.getName());
			
			for(Iterator j=element.attributeIterator();j.hasNext();) {
				Attribute attribute = (Attribute)j.next();  	//获取属性，可用element.attributeValue(str)直接获取元素某指定属性的值
				System.out.println(attribute.getName() + " " + attribute.getValue());
			}
		}		
		*/
		
		
		//XPATH
		List<Node> list = document.selectNodes("//hibernate-mapping/class/property");     //使用XPath需要引入jaxen的jar包。
		/*for(Node n : list) {
			System.out.println(n.getName());
			System.out.println(n.valueOf("@name"));
		}		
		Node node = document.selectSingleNode("//hibernate-mapping/class/property"); //只取第一个结点
		String name = node.valueOf(@name); 
		*/
		
		
		Element element = (Element) rootElement.selectSingleNode("disk");  //获取根结点下第一个为“disk”的element	
		//Element element = rootElement.element("disk"); //获取根结点下的disk节点
		//Element  element = document.elementById("disk");  //直接通过ID获取disk节点，xml中的ID要大写
 		List<Element> elements = element.elements();               //获取disk下所有的子节点。
		for(Element e : elements) {
			System.out.println(e.getName()+":" + e.getText());
		}
		 
		
	}
	}
	
}
 