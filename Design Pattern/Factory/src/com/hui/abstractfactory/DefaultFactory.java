package com.hui.abstractfactory;

public class DefaultFactory extends AbstractFactory {

	@Override
	Vehicle createVehicle() {
		return new Car();
	}

	@Override
	Weapon createWeapon() {
		return new AK47();
	}

	@Override
	Food createFood() {
		return new Apple();
	}

}
