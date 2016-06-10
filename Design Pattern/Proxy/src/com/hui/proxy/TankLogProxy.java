package com.hui.proxy;

public class TankLogProxy implements Moveable{
	//¾ÛºÏ
	Moveable t;
	@Override
	public void move() {
		System.out.println("Tank Start...");
		t.move(); 
		System.out.println("Tank End...");
		
	}
	public TankLogProxy(Moveable t) {
		super();
		this.t = t;
	}
	 
}
