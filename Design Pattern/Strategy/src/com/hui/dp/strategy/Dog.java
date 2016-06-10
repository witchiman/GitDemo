package com.hui.dp.strategy;


public class Dog implements Comparable<Dog> {
	public Dog(int food) {
		this.food = food;
	}

	private int food;

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}
	
	
	
	@Override
	public String toString() {
		return food +" ";
	}

	@Override
	public int compareTo(Dog d) {
		 	 
			 if(this.food > d.food) return 1;
			 else if(this.food < d.food) return -1;
			 else return 0;
	}
}
