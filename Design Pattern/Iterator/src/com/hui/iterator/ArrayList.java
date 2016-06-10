package com.hui.iterator;

import com.hui.iterator.Collection;
import com.hui.iterator.Iterator;

public class ArrayList implements Collection{
	private int index = 0;
	Object[] objects = new Object[10];
	
	public void add(Object o) {
		if(index == objects.length) {
			Object[] newObjects = new Object[objects.length*2];
			System.arraycopy(objects, 0, newObjects, 0, objects.length);
			objects = newObjects;
		}
		
		objects[index] = o;
		index++;
	}
	
	public int size() {
		return index;
	}

	@Override
	public Iterator iterator() {		
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator {
		int currentIndex = 0;
		
		@Override
		public boolean hasNext() {
			if(currentIndex >= index) return false;
			else return true;
		}

		@Override
		public Object next() {			 
			Object o = objects[currentIndex];
			currentIndex++;
			return o;
		}
		
	}
}
