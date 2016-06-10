package com.hui.dom4j;

import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DOM4J2 {

	public static void main(String[] args) throws Exception {
		createDocument();		
	}
	
	 public static Document createDocument() throws Exception {
	       Document document = DocumentHelper.createDocument();
	       Element root = document.addElement("hibernate-mapping");
	       Element classElement = root.addElement("class")
	    		   .addAttribute("name", "com.hui.xml")
	    		   .addAttribute("table", "t_user");
	       classElement.addElement("property")
	       				.addAttribute("name", "username");
	      /* FileWriter out = new FileWriter("User.xml");
	       document.write(out);
	       out.flush();
	       out.close();*/
	       
	       //格式化后的输出	   
	       OutputFormat format = OutputFormat.createPrettyPrint();
	       XMLWriter  writer = new XMLWriter( new FileWriter("User2.xml"), format );
	       writer.write( document);
	       writer.close();
	   
	       					           
	       return document;
	    }

}
