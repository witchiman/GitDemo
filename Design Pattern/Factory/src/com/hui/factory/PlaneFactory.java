package com.hui.factory;

public class PlaneFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Plane();
	}

}
