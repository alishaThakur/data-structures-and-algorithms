package com.pdomingo.implementations.list;


import com.pdomingo.exceptions.IndexOutOfBoundsException;
import com.pdomingo.exceptions.ItemNotFoundException;
import com.pdomingo.interfaces.List;

import java.util.Iterator;

/**
 * Created by Pablo on 20/12/16.
 */
public class LinkedList<T> extends AbstractList<T> {

	private Node head, tail;
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
			return "Node{" + item + "}";
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
		checkRange(index);

		Node node = getNodeAt(index);
		return node.item;
	}

	@Override
	public List<T> put(T item, int index) throws IndexOutOfBoundsException {

		checkRange(index);

		Node node = getNodeAt(index);
		node.item = item;

		return this;
	}

	@Override
	public T remove(int index) throws IndexOutOfBoundsException {

		checkRange(index);

		Node node = getNodeAt(index);
		T item = node.item;

		if(node == head && node == tail) {
			head = null;
			tail = null;
		} else if (node == head) { // Remove head
			Node newHead = node.next;

			node.next = null;
			newHead.prev = null;

			head = newHead;
		} else if (node == tail) { // Remove tail
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

	private Node getNodeAt(int index) {

		assert (index >= 0 && index < size);

		if (index == 0)
			return head;
		else if (index == size - 1)
			return tail;
		else {
			Node node = head;
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
	public void clear() {

		for(Node node = head; node != null; node = node.next) {
			node.prev = null;
			node.item = null;
		}

		head = tail = null;
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

	private List<T> addFirst(T item) {

		if (isEmpty())
			addLast(item);
		else {
			Node newHead = new Node(item, null, null);
			head.prev = newHead; // NH <- H
			newHead.next = head; // NH <-> H
			head = newHead;
			size++;
		}

		return this;
	}

	private List<T> addLast(T item) {

		Node newTail = new Node(item, null, null);

		if (isEmpty()) {
			head = newTail;
			tail = newTail;
		} else {
			tail.next = newTail; // T -> NT
			newTail.prev = tail; // T <-> NT
			tail = newTail;
		}

		size++;

		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LinkedList<?> otherList = (LinkedList<?>) o;
		if(size != otherList.size()) return false;
		Iterator<?> iterThis = iterator();
		Iterator<?> iterOther = otherList.iterator();

		while(iterThis.hasNext() && iterOther.hasNext()) {
			if( ! iterThis.next().equals(iterOther.next()))
				return false;
		}
		return true;
	}
}
