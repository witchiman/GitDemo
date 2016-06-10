package com.hui.proxy;

public class Tank2 extends Tank {
	//ผฬณะ
	@Override
	public void move() {
		long start = System.currentTimeMillis();
		super.move();
		long end =System.currentTimeMillis();
		System.out.println("method run time :" +(end - start));
	}

}
