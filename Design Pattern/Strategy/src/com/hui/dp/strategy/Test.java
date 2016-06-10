package com.hui.dp.strategy;

public class Test {
	public static void main(String[] args) {
		//int[] a = {9, 5, 3, 7, 1};
		Cat[] a = {new Cat(5, 5), new Cat(3, 3), new Cat(1, 1)};
		//Dog[] a = {new Dog(5),new Dog(3), new Dog(1)}; 
		DateSorter.p(a);
		DateSorter.sort(a);  //java.util.Arrays.sort”Î¥À¿‡À∆
		DateSorter.p(a);
	}
}
