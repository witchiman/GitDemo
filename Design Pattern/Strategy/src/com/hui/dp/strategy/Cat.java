package com.hui.dp.strategy;

public class Cat implements Comparable<Cat> {
	private Comparator<Cat> comparator = new CatHeightComparator();
	
	public Cat(int weight, int height) {		 
		this.weight = weight;
		this.height = height;
	}

	private int weight,height;

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	} 

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return height + "|" + weight + " ";
	}

	@Override
	public int compareTo(Cat o) {
		return comparator.compare(this, o);
	}

	public Comparator<Cat> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<Cat> comparator) {
		this.comparator = comparator;
	}
	
}
