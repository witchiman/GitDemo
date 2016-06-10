package com.hui.iterator;
import com.hui.iterator.Collection;
import com.hui.iterator.Iterator;

public class LinkedList implements Collection {
	private int size = 0;
	private Node head = null;
	private Node tail =null;
	
	public void add(Object o) {
		Node n = new Node(o, null);
		if(head == null) {
			head = n;
			tail = n;
		}
		tail.setNext(n);
		tail = n;
		size++;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public Iterator iterator() {
		return new LinkedListIterator();
	}
	
	private class LinkedListIterator implements Iterator {
        private Node p = head;
		@Override
		public boolean hasNext() {
			if(p == null ) return false;
			else return true;
		}

		@Override
		public Object next() {
			Node o = p;
			p = p.getNext();
			return o.getData();
		}
		
	}
}
