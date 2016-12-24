package com.pdomingo.implementations;


import com.pdomingo.exceptions.IndexOutOfBoundsException;
import com.pdomingo.interfaces.List;

import java.util.Iterator;

/**
 * Created by Pablo on 20/12/16.
 */
public class LinkedList<T> extends AbstractList<T> {

	private Node head, tail;
	private int size;
	private int capacity;

	private final int DEFAULT_CAPACITY = 16;

	private class Node {
		public T item;
		public Node prev, next;

		private Node(T item, Node prev, Node next) {
			this.item = item;
			this.prev = prev;
			this.next = next;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<T> add(T item) {

		tail = new Node(item, tail, null);

		if(isEmpty()) {
			head = tail;
		}
		else {
			tail.prev.next = tail; // A <- B ==> A <-> B
		}

		size++;

		return this;
	}

	@Override
	public T get(int index) throws IndexOutOfBoundsException {
		checkRange(index);

		Iterable<T> iter = index < Math.floorDiv(index, 2) ?
				this : new BackwardIterable();
		for(T item : iter)
			if(index-- == 0)
				return item;

		return null;
	}

	@Override
	public List<T> put(T item, int index) throws IndexOutOfBoundsException {
		checkRange(index);

		Node node = head;
		while(index-- > 0)
			node = node.next;

		node.item = item;

		return this;
	}

	@Override
	public T remove(int index) throws IndexOutOfBoundsException {

		checkRange(index);

		Node node = head;
		while(index-- > 0)
			node = node.next;

		node.prev.next = node.next; // A -> B (node) -> C ==> A -> C
		node.next.prev = node.prev; // A <- B (node) <- C ==> A <- C

		node.next = null;
		node.prev = null;
		T item = node.item;
		node.item = null;

		return item;
	}

	@Override
	public boolean remove(T item) {
		return false;
	}

	@Override
	public void clear() {

		do {
			head.item = null;
			head = head.next;
		}
		while(head.next != null);

		head = tail = null;
		size = 0;
	}

	@Override
	public boolean contains(T item) {
		return false;
	}

	private class BackwardIterable implements Iterable<T> {

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {

				private Node currentNode = tail;

				@Override
				public boolean hasNext() {
					return currentNode == null;
				}

				@Override
				public T next() {
					return null;
				}
			};
		}
	}
}
