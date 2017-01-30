package com.pdomingo.data_structures.implementations.list;


import com.pdomingo.exceptions.IndexOutOfBoundsException;
import com.pdomingo.exceptions.ItemNotFoundException;
import com.pdomingo.data_structures.implementations.list.abstracts.AbstractList;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Iterator;

/**
 * Created by Pablo on 20/12/16.
 */
public class CircularList<T> extends AbstractList<T> {

	private Node current;
	private Node tail;

	private int size;

	private class Node {
		T item;
		Node prev, next;

		private Node(T item, Node prev, Node next) {
			this.item = item;
			this.prev = prev;
			this.next = next;
		}

		@Override
		public String toString() {
			return prev + "<- {" + item + "} ->" + next;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<T> add(T item) {
		addLast(item);
		return this;
	}

	@Override
	public T get(int index) throws IndexOutOfBoundsException {

		index = index % size; //Circular nature
		checkRange(index);

		boolean leftHalf = index < Math.floorDiv(index, 2);

		Iterable<T> iter = leftHalf ? this : new BackwardIterable();
		int idx = leftHalf ? index : size - index;

		for (T item : iter)
			if (--idx == 0)
				return item;

		return null;
	}

	@Override
	public T first() {
		return null;
	}

	@Override
	public T last() {
		return null;
	}

	@Override
	public List<T> put(T item, int index) throws IndexOutOfBoundsException {

		index = index % size; //Circular nature
		checkRange(index);

		Node node = tail;
		while (index-- > 0)
			node = node.next;

		node.item = item;

		return this;
	}

	@Override
	public T remove(int index) throws IndexOutOfBoundsException {

		index = index % size; //Circular nature
		checkRange(index);

		Node node = getNode(index);
		T item = node.item;

		if (node == tail) { // Remove tail
			Node newTail = node.prev;

			node.prev = null;
			newTail.next = null;

			tail = newTail;
		} else { // Remove else
			Node prevNode = node.prev;
			Node nextNode = node.next;

			prevNode.next = nextNode; // A -> B -> C ==> A -> C
			nextNode.prev = prevNode; // A <- B <- C ==> A <- C

			node.next = null;
			node.prev = null;
		}

		size--;

		return item;
	}

	private Node getNode(int index) {

		assert (index >= 0 && index < size);

		if (index == size - 1)
			return tail;
		else {
			Node node = tail;
			while (index-- > 0)
				node = node.next;
			return node;
		}
	}

	/**
	 * @param item
	 * @return
	 * @throws ItemNotFoundException
	 */
	private int findItem(T item) {

		int position = -1;
		int idx = 0;

		for (T itemToCompare : this) {
			if (itemToCompare.equals(item)) {
				position = idx;
				break;
			}
			idx++;
		}

		return position;
	}

	@Override
	public boolean remove(T item) {
		int position = findItem(item);
		if (position == -1)
			return false;

		remove(position);
		return true;
	}

	@Override
	public T removeFirst() {
		return null;
	}

	@Override
	public T removeLast() {
		return null;
	}

	@Override
	public void clear() {

		for(Node node = tail; node != null; node = node.next) {
			node.prev = null;
			node.item = null;
		}

		tail = null;
		size = 0;
	}

	@Override
	public boolean contains(T item) {
		return findItem(item) != -1;
	}

	private class BackwardIterable implements Iterable<T> {

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {

				private Node currentNode = tail;

				@Override
				public boolean hasNext() {
					return currentNode != null;
				}

				@Override
				public T next() {
					T item = currentNode.item;
					currentNode = currentNode.prev;
					return item;
				}
			};
		}
	}

	public List<T> addFirst(T item) {
		addLast(item);
		return this;
	}

	public List<T> addLast(T item) {

		Node newTail = new Node(item, null, null);

		if (isEmpty()) {
			tail = newTail;
			newTail.prev = newTail;
			newTail.next = newTail;
		} else {
			newTail.next = tail.next;
			newTail.prev = tail; // T <-> NT

			tail.next.prev = newTail;
			tail.next = newTail; // T -> NT

			tail = newTail;
		}

		size++;

		return this;
	}

	public void rotate() {
		if(size > 1)
			tail = tail.next;
	}

	@Override
	public String toString() {

		String nodes = "";
		Iterator<T> iter = iterator();
		while (iter.hasNext()) {

			T item = iter.next();
			nodes += item;

			if (iter.hasNext()) {
				nodes += "- ";
			}
		}

		return "CircularList{ " + nodes + " }";
	}

	public static void main(String[] args) {
		CircularList<String> cl = new CircularList<>();

		cl.add("item1");
		System.out.printf(cl.toString());
		cl.add("item2");
		System.out.printf(cl.toString());
		cl.rotate();
		System.out.printf(cl.toString());
		cl.remove("item1");
	}
}
