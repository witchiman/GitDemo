package com.hui.iterator;
import com.hui.iterator.ArrayList;
import com.hui.iterator.LinkedList;
import com.hui.iterator.Collection;
import com.hui.iterator.Iterator;

public class Test {

	public static void main(String[] args) {
		// ArrayList list = new ArrayList();
		Collection list = new LinkedList(); 
		for(int i=0;i<15; i++) {
			 list.add(new Cat(i));
		 }
		System.out.println(list.size());
		Iterator iterator = list.iterator();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			System.out.print(o);
		}
		 
	}

}
