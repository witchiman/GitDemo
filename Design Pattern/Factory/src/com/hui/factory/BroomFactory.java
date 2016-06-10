package com.hui.factory;

public class BroomFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Broom();
	}

}
