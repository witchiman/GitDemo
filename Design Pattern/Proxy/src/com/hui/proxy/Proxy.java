package com.hui.proxy;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Proxy {
	public static Object newProxyInstance(Class infce, InvocationHandler h) throws Exception{
		String rt = "\r\n";
		String methodStr = "";
		Method[] methods = infce.getMethods();
		/*for(int i=0; i<methods.length; i++) {
			Method m = methods[i];
			methodStr += 
					"	public void " + m.getName() +"() {" + rt +
					"		long start = System.currentTimeMillis();" + rt +
					"		t." + m.getName() +"();" +rt +
					"		long end =System.currentTimeMillis();" + rt +
					"		System.out.println(\"method run time :\" +(end - start));" + rt +			
					"	}" + rt;
		}*/
		
		for(Method m : methods) {			
			methodStr +=
						"	public void " + m.getName() +"() {" + rt +						
						"		try {"	+ rt +
						"			Method md = " + infce.getName() + ".class.getMethod(\"" +m.getName() + "\");" + rt +
						"			h.invoke(this,md);" + rt +
						"		}catch (Exception e) {" + rt +
						"			e.printStackTrace();" + rt +
								"}" + rt +							
						"	}" + rt;
		}
		
		String src=
			"package com.hui.proxy;" +	rt +
			"import java.lang.reflect.Method;" +rt+
			"public class $Proxy1 implements " + infce.getName() +"{" + rt +    //动态传入接口
			"	com.hui.proxy.InvocationHandler h;" + rt +
			"	@Override" + rt +
			methodStr +
			"	public $Proxy1(InvocationHandler h) {" + rt +			
			"		this.h = h;" + rt +
			"	}" + rt +
			"}";
		
			//usrer.dir 是指当前项目路径
			String filename =  System.getProperty("user.dir") + "/src/com/hui/proxy/$Proxy1.java";
			//System.out.println(filename); 
			FileWriter fw = new FileWriter(new File(filename));
			fw.write(src);
			fw.flush();
			fw.close();
			//compile
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
			Iterable units = fileMgr.getJavaFileObjects(filename);
			CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
			t.call();
			fileMgr.close();
			
			//load into memory and create an instance
			URL[] urls = new URL[] {new URL("file://"  +System.getProperty("user.dir") + "/src/")};
			URLClassLoader ul = new URLClassLoader(urls);
			Class c = ul.loadClass("com.hui.proxy.$Proxy1");
			//System.out.println(c);
			
			Constructor ctr = c.getConstructor(InvocationHandler.class);
			Object m = ctr.newInstance(h);			 
			//Moveable mm = (Moveable)m;			 
			//mm.move();
			 
			 
		return m;
	}
}
