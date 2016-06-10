package com.hui.abstractfactory;

public class MagicFactory extends AbstractFactory {

	@Override
	Vehicle createVehicle() {
		return new MagicBroom();
	}

	@Override
	Weapon createWeapon() {
		return new MagicStick();
	}

	@Override
	Food createFood() {
		return new MagicBean();
	}

}
