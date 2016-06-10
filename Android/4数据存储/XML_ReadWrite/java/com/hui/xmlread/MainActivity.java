package com.hui.xmlread;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textview);

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            /*解析XML文件*/
          /*  Document document = builder.parse(getAssets().open("languages.xml"));
            Element element = document.getDocumentElement();//获取根元素
            NodeList list = element.getElementsByTagName("lan");

            for(int i=0; i<list.getLength();i++) {
                Element e = (Element) list.item(i);
                tv.append(e.getAttribute("id") + "\n");
                tv.append(e.getElementsByTagName("name").item(0).getTextContent()+" ");
                tv.append(e.getElementsByTagName("ide").item(0).getTextContent()+"\n");
            }*/

            /*生成XML文件*/
            Document newXml = builder.newDocument();
            Element languages = newXml.createElement("languages");
            languages.setAttribute("category", "IT");

            Element lan1 = newXml.createElement("lan");
            lan1.setAttribute("id", "1");
            Element name1 = newXml.createElement("name");
            name1.setTextContent("Java");
            Element ide1 = newXml.createElement("ide");
            ide1.setTextContent("Eclipse");
            lan1.appendChild(name1); //添加子元素
            lan1.appendChild(ide1);
            languages.appendChild(lan1);

            Element lan2 = newXml.createElement("lan");
            lan2.setAttribute("id", "2");
            Element name2 = newXml.createElement("name");
            name2.setTextContent("C#");
            Element ide2 = newXml.createElement("ide");
            ide2.setTextContent("Visual Studio");
            lan2.appendChild(name2); //添加子元素
            lan2.appendChild(ide2);
            languages.appendChild(lan2);

            newXml.appendChild(languages);//添加根元素

            TransformerFactory transformerFactory = TransformerFactory.newInstance(); //输出XMl文件
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            StringWriter sw = new StringWriter(); //输入到界面
            transformer.transform(new DOMSource(newXml),new StreamResult(sw));
            tv.setText(sw.toString());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
       /* } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();*/
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


}
