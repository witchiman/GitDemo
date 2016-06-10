package com.hui.apo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogIntercepter implements InvocationHandler{
	private Object target;
	
	public void beforeMethod(Method m) {
		System.out.println(m.getName() + " stasrt...");
	}
    
	
	public LogIntercepter(Object target) {		 
		this.target = target;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		beforeMethod(method);
		method.invoke(target, args);
		return null;
	}
}
