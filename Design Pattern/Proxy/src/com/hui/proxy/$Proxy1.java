package com.hui.proxy;
import java.lang.reflect.Method;
public class $Proxy1 implements com.hui.proxy.Moveable{
	com.hui.proxy.InvocationHandler h;
	@Override
	public void move() {
		try {
			Method md = com.hui.proxy.Moveable.class.getMethod("move");
			h.invoke(this,md);
		}catch (Exception e) {
			e.printStackTrace();
}
	}
	public $Proxy1(InvocationHandler h) {
		this.h = h;
	}
}