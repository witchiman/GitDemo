package com.hui.factory;

public class Test {

	public static void main(String[] args) {
		 VehicleFactory factory = new BroomFactory();
		 Moveable m = factory.create();
		 m.run();
	}

}
