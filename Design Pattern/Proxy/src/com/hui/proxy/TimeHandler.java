package com.hui.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {
	
	private Object target;
	
	public TimeHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public void invoke(Object o, Method m) {
		System.out.println("dealing...");
		System.out.println(o.getClass().getName());
		long start = System.currentTimeMillis();
		try {
			m.invoke(target);
		} catch (Exception e) {
			e.printStackTrace();
		}		 
	    long end =System.currentTimeMillis(); 
		System.out.println("method run time :" +(end - start)); 

	}

 
}
