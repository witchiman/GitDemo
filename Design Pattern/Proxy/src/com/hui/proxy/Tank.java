package com.hui.proxy;
import java.util.Random;


public class Tank implements Moveable{ 
	@Override
	public void move() {
		System.out.println("Tank moving...");
 
		try {			
			Thread.sleep(new Random().nextInt(2000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
}
