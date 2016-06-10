package com.hui.factory;

public class CarFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Car();
	}

}
