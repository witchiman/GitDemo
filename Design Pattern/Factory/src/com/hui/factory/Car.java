package com.hui.factory;

public class Car implements Moveable {
	private static Car car = new Car();		//单例
	//List<Car> cars = new ArrayList<Car>(); //多例
	
	//private Car() {}
	
	public static Car getInstance() {
		return car;
	}
	
	public void run() {
		System.out.println("冒着烟儿的跑。。。");
	}
}
