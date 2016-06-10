package com.hui.dp.strategy;

public class DateSorter {
	
	public static void sort(Comparable[] a) {
		for(int i=a.length; i>0; i--) {
			for(int j=0; j<i-1; j++) {
				
				if(a[j].compareTo(a[j+1]) == 1) {
					swap(a, j , j+1);
				}
			}
		}
	}
	
	private static void swap(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		
	}

	public static void p(Object[] a) {
		for(int i=0;i<a.length; i++) {
			System.out.print(a[i] + "");
		}
		System.out.println();
	}
}
