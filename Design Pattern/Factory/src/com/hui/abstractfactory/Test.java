package com.hui.abstractfactory;

public class Test {

	public static void main(String[] args) {
		AbstractFactory factory = new DefaultFactory();
		Vehicle vehicle = factory.createVehicle();
		Weapon weapon = factory.createWeapon();
		Food food = factory.createFood();
		vehicle.run();
		weapon.shoot();
		food.printName();
	}

}
			