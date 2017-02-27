package com.pdomingo.data_structures.implementations.list;


import com.pdomingo.data_structures.interfaces.Position;
import java.lang.IndexOutOfBoundsException;
import com.pdomingo.exceptions.ItemNotFoundException;
import com.pdomingo.data_structures.implementations.list.abstracts.AbstractList;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Iterator;

/**
 *
 *
 * @param T type
 *
 * <h4>Complexity summary</h4>
 * <table>
 *      <thead>
 *          <td>Method</td><td>Complexity</td>
 *      </thead>
 *      <tr><td>{@link CircularList#size()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#isEmpty()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#add(Object)}</td><td>O(1) * amortizado</td></tr>
 *      <tr><td>{@link CircularList#addFirst(Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#addLast(Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#addAfter(Position, Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#addBefore(Position, Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#addAll(Iterable)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link CircularList#get(int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#put(Object, int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#removeByIndex(int)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link CircularList#last()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#removeFirst()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#removeLast()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#removeNext(Position)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#removePrevious(Position)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#removeAll(Iterable)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link CircularList#swap(int, int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#clear()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link CircularList#contains(Object)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link CircularList#contains(Object)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link CircularList#positions()}</td><td>O(1)</td></tr>
 * </table>
 */
public class CircularList<T> extends AbstractList<T> {

	private Node<T> current;
	private Node<T> tail;

	private int size;

	private class Node<E> implements Position<E> {

		private E item;
		private Node<E> prev, next;

		private Node(E item, Node<E> prev, Node<E> next) {
			this.item = item;
			this.prev = prev;
			this.next = next;
		}

		@Override
		public E getElement() {
			return item;
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
	public Position<T> get(int index) throws IndexOutOfBoundsException {

		index = index % size; //Circular nature
		checkRange(index);

		boolean leftHalf = index < Math.floorDiv(index, 2);

		for (Position<T> item : positions())
			if (--index == 0)
				return item;

		return null;
	}

	@Override
	public Position<T> first() {
		return null;
	}

	@Override
	public Position<T> last() {
		return null;
	}

	@Override
	public List<T> put(T item, int index) throws IndexOutOfBoundsException {

		index = index % size; //Circular nature
		checkRange(index);

		Node<T> node = tail;
		while (index-- > 0)
			node = node.next;

		node.item = item;

		return this;
	}

	@Override
	public Position<T> removeByIndex(int index) throws IndexOutOfBoundsException {

		index = index % size; //Circular nature
		checkRange(index);

		Node<T> node = getNode(index);

		if (node == tail) { // Remove tail
			Node<T> newTail = node.prev;

			node.prev = null;
			newTail.next = null;

			tail = newTail;
		} else { // Remove else
			Node<T> prevNode = node.prev;
			Node<T> nextNode = node.next;

			prevNode.next = nextNode; // A -> B -> C ==> A -> C
			nextNode.prev = prevNode; // A <- B <- C ==> A <- C

			node.next = null;
			node.prev = null;
		}

		size--;

		return node;
	}

	private Node<T> getNode(int index) {

		assert (index >= 0 && index < size);

		if (index == size - 1)
			return tail;
		else {
			Node<T> node = tail;
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
	public boolean removeByItem(T item) {
		int position = findItem(item);
		if (position == -1)
			return false;

		removeByIndex(position);
		return true;
	}

	@Override
	public Position<T> removeFirst() {
		return null;
	}

	@Override
	public Position<T> removeLast() {
		return null;
	}

	@Override
	public Position<T> removeNext(Position<T> position) {
		return null;
	}

	@Override
	public Position<T> removePrevious(Position<T> position) {
		return null;
	}

	@Override
	public void swap(int indexA, int indexB) {

		/*
		checkRange(indexA);
		checkRange(indexB);


		Node<E> nodeA = node(get(indexA));
		Node<E> nodeB = node(get(indexB));

		E itemA = nodeA.item;

		nodeA.item = nodeB.item;
		nodeB.item = itemA;
		*/
	}

	@Override
	public void clear() {

		for(Node<T> node = tail; node != null; node = node.next) {
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

	@Override
	public Iterable<Position<T>> positions() {
		return null;
	}

	private class BackwardIterable implements Iterable<T> {

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {

				private Node<T> currentNode = tail;

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

		Node<T> newTail = new Node<T>(item, null, null);

		if (isEmpty()) {
			tail = newTail;
			newTail.prev = newTail;
			newTail.next = newTail;
		} else {
			newTail.next = tail.next;
			newTail.prev = tail; // E <-> NT

			tail.next.prev = newTail;
			tail.next = newTail; // E -> NT

			tail = newTail;
		}

		size++;

		return this;
	}

	@Override
	public List<T> addAfter(Position<T> position, T item) {
		return null;
	}

	@Override
	public List<T> addBefore(Position<T> position, T item) {
		return null;
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
		cl.removeByItem("item1");
	}
}
