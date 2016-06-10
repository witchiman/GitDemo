package com.hui.iterator;
import com.hui.iterator.Iterator;

public interface Collection {
	void add(Object o);
	int size();
	Iterator iterator();
}
