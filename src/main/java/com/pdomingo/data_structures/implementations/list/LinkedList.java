package com.pdomingo.data_structures.implementations.list;

import com.pdomingo.data_structures.implementations.common.Collections;
import com.pdomingo.data_structures.interfaces.Position;
import java.lang.IndexOutOfBoundsException;
import com.pdomingo.data_structures.implementations.list.abstracts.AbstractList;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Iterator;

/**
 *
 * @param <T>
 *
 * <h4>Complexity summary</h4>
 * <table>
 *      <thead>
 *          <td>Method</td><td>Complexity</td>
 *      </thead>
 *      <tr><td>{@link LinkedList#size()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#isEmpty()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#add(Object)}</td><td>O(1) * amortizado</td></tr>
 *      <tr><td>{@link LinkedList#addFirst(Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#addLast(Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#addAfter(Position, Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#addBefore(Position, Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#addAll(Iterable)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link LinkedList#get(int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#put(Object, int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#removeByIndex(int)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link LinkedList#last()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#removeFirst()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#removeLast()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#removeNext(Position)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#removePrevious(Position)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#removeAll(Iterable)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link LinkedList#swap(int, int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#clear()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link LinkedList#contains(Object)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link LinkedList#contains(Object)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link LinkedList#positions()}</td><td>O(1)</td></tr>
 * </table>
 *
 */
public class LinkedList<T> extends AbstractList<T> {

	private Node<T> head, tail;
	private int size;

	/********************* Nested classes *********************/

	/**
	 *
	 * @param <E>
	 */
	private static class Node<E> implements Position<E> {

		private E item;
		private Node<E> prev, next;

		private Node(E item,Node<E> prev, Node<E> next) {
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

	/********************* Constructors *********************/

	public LinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	public LinkedList(Iterable<T> items) {
		this();
		addAll(items);
	}

	/********************* Public methods *********************/

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<T> add(T item) {
		addLast(item);
		return this;
	}

	public List<T> addFirst(T item) {

		if (isEmpty())
			addLast(item);
		else {
			Node<T> newHead = new Node<>(item, null, head);

			head.prev = newHead; // NH <- H
			head = newHead;
			size++;
		}

		return this;
	}

	public List<T> addLast(T item) {

		Node<T> newTail = new Node<>(item, tail, null);

		if (isEmpty())
			head = newTail;
		else
			tail.next = newTail; // T -> NT

		tail = newTail;
		size++;

		return this;
	}

	@Override
	public List<T> addAfter(Position<T> position, T item) {

		Node<T> node = node(position);

		if(node == tail)
			addLast(item);
		else {
			Node<T> newNode = new Node<>(item, node, node.next);
			node.next = newNode;
			newNode.next.prev = newNode;
		}

		return this;
	}

	@Override
	public List<T> addBefore(Position<T> position, T item) {

		Node<T> node = node(position);

		if(node == head)
			addFirst(item);
		else {
			Node<T> newNode = new Node<>(item, node.prev, node);
			node.prev = newNode;
			newNode.prev.next = newNode;
		}

		return this;
	}


	@Override
	public Position<T> get(int index) throws IndexOutOfBoundsException {
		checkRange(index);
		return getNodeAt(index);
	}

	@Override
	public Position<T> first() {
		return isEmpty() ? null : get(0);
	}

	@Override
	public Position<T> last() {
		return isEmpty() ? null : get(size - 1);
	}

	@Override
	public List<T> put(T item, int index) throws IndexOutOfBoundsException {

		checkRange(index);

		Node<T> node = getNodeAt(index);
		node.item = item;

		return this;
	}

	@Override
	public Position<T> removeByIndex(int index) throws IndexOutOfBoundsException {

		checkRange(index);

		Node<T> node = getNodeAt(index);

		if (node == head)
			return removeFirst();
		if (node == tail)
			return removeLast();

		// Node between head and tail
		Node<T> prevNode = node.prev;
		Node<T> nextNode = node.next;

		prevNode.next = nextNode; // A -> B -> C ==> A -> C
		nextNode.prev = prevNode; // A <- B <- C ==> A <- C

		node.next = null;
		node.prev = null;

		size--;

		return node;
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

		if(isEmpty())
			return null;
		else {
			Node<T> currentHead = head;
			Node<T> newHead = head.next;

			head.next = null;
			if(newHead != null)
				newHead.prev = null;
			else
				tail = null;

			head = newHead;
			size--;

			return currentHead;
		}
	}

	@Override
	public Position<T> removeLast() {

		if(isEmpty())
			return null;
		else {
			Node<T> currentTail = tail;
			Node<T> newTail = tail.prev;

			tail.prev = null;
			if(newTail != null)
				newTail.next = null;
			else
				head = null;

			tail = newTail;
			size--;

			return currentTail;
		}
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
		checkRange(indexA);
		checkRange(indexB);

		Node<T> nodeA = getNodeAt(indexA);
		Node<T> nodeB = getNodeAt(indexB);

		T itemA = nodeA.item;

		nodeA.item = nodeB.item;
		nodeB.item = itemA;
	}

	@Override
	public void clear() {

		if(isEmpty())
			return;

		Node<T> next = head.next;

		for(Node<T> node = head; next != null;) {

			node.prev = null;
			node.next = null;
			node.item = null;

			node = next;
			next = node.next;
		}

		head = tail = null;
		size = 0;
	}

	@Override
	public boolean contains(T item) {
		return findItem(item) != -1;
	}

	public List<T> sublist(int from, int to) {

		int size = to - from;
		if(size <= 0)
			return Collections.emptyList();
		else {
			List<T> sublist = new LinkedList<>();

			Node<T> node = getNodeAt(from);
			while(size > 0) {
				sublist.add(node.getElement());
				node = node.next;
			}

			return sublist;
		}
	}

	@Override
	public Iterable<Position<T>> positions() {
		return new PositionIterable();
	}

	private class PositionIterable implements Iterable<Position<T>> {

		@Override
		public Iterator<Position<T>> iterator() {
			return new Iterator<Position<T>>() {

				private Node<T> node = head;

				@Override
				public boolean hasNext() {
					return node == null;
				}

				@Override
				public Position<T> next() {
					Position<T> position = node;
					node = node.next;
					return position;
				}
			};
		}
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

	/********************* Private methods *********************/

	/**
	 *
	 * @param position
	 * @return
	 * @throws IllegalArgumentException
	 */
	private Node<T> node(Position<T> position) throws IllegalArgumentException {

		if( ! (position instanceof Node))
			throw new IllegalArgumentException("Invalid position");

		// Safe cast
		Node<T> node = (Node<T>) position;

		if(node.next == null && node.prev == null)
			throw new IllegalArgumentException("Position is no longer in the list");

		return node;
	}

	/**
	 * @param item
	 * @return
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

	/**
	 *
	 * @param index
	 * @return
	 */
	private Node<T> getNodeAt(int index) {

		assert (index >= 0 && index < size);

		if (index == 0)
			return head;
		else if (index == size - 1)
			return tail;
		else {
			Node<T> node = head;
			while (index-- > 0)
				node = node.next;
			return node;
		}
	}
}
