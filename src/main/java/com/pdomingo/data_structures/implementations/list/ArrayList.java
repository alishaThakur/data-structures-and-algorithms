package com.pdomingo.data_structures.implementations.list;

import com.pdomingo.data_structures.implementations.common.Collections;
import com.pdomingo.data_structures.interfaces.Position;
import com.pdomingo.data_structures.implementations.list.abstracts.AbstractList;
import com.pdomingo.data_structures.interfaces.List;
import java.util.Iterator;


/**
 * List implementation baked by an array
 *
 * @param <T> type
 *
 * <h4>Complexity summary</h4>
 * <table>
 *      <thead>
 *          <td>Method</td><td>Complexity</td>
 *      </thead>
 *      <tr><td>{@link ArrayList#size()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#isEmpty()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#add(Object)}</td><td>O(1) * amortizado</td></tr>
 *      <tr><td>{@link ArrayList#addFirst(Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#addLast(Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#addAfter(Position, Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#addBefore(Position, Object)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#addAll(Iterable)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link ArrayList#get(int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#put(Object, int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#removeByIndex(int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#removeByItem(Object)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link ArrayList#last()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#removeFirst()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#removeLast()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#removeNext(Position)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#removePrevious(Position)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#removeAll(Iterable)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link ArrayList#swap(int, int)}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#clear()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link ArrayList#contains(Object)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link ArrayList#contains(Object)}</td><td>O(n)</td></tr>
 *      <tr><td>{@link ArrayList#positions()}</td><td>O(1)</td></tr>
 * </table>
 */
public class ArrayList<T> extends AbstractList<T> {

	/* Baking array */
	private Node<T>[] data;

	/* Available size of the array */
	private int capacity;

	/* Used size of the array */
	private int size;

	private static final int DEFAULT_CAPACITY = 16;
	private static final int INVALID = -1;

	/*--------------------- Nested classes ---------------------*/

	/**
	 * Internal object stored in the array
	 * @param <E>
	 */
	private static class Node<E> implements Position<E> {

		private int index;
		private E item;

		private Node(int index, E item) {
			this.index = index;
			this.item = item;
		}

		public static <E> Node<E> of(int index, E item) {
			return new Node<>(index, item);
		}

		@Override
		public E getElement() {
			return item;
		}

		public void delete() {
			index = INVALID;
			item = null;
		}

		@Override
		public String toString() {
			return String.format("Node[%d] - %s", index, item);
		}
	}

	/*--------------------- Constructors ---------------------*/

	/**
	 * Default constructor
	 */
	public ArrayList() {
		reset(DEFAULT_CAPACITY);
	}

	/**
	 * Builds and ArrayList of the given capacity
	 * @param capacity requested capacity of the list
	 * @throws IllegalArgumentException if capacity <= 0
	 */
	public ArrayList(int capacity) throws IllegalArgumentException{
		if(capacity <= 0)
			throw new IllegalArgumentException("Invalid capacity " + capacity);

		reset(capacity);
	}

	/**
	 *
	 */
	public ArrayList(Iterable<T> items) {
		this();
		this.addAll(items);
	}

	/*--------------------- Public Methods ---------------------*/

	/**
	 * Number of elements stored in the list
	 * @return size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Appends the item to the end of the list (shortcut for addLast)
	 * @param item to be inserted
	 * @return the list
	 */
	public List<T> add(T item) {

		if(size + 1 > capacity)
			increaseCapacity();

		data[size] = Node.of(size,item);
		size++;

		return this;
	}

	/**
	 * Appends the item to the start of the list
	 * @param item to be inserted
	 * @return the list
	 */
	@Override
	public List<T> addFirst(T item) {

		shiftRight(0);

		data[0] = Node.of(0,item);
		size++;

		return this;
	}

	/**
	 * Appends the item to the end of the list
	 * @param item to be inserted
	 * @return the list
	 */
	@Override
	public List<T> addLast(T item) {
		add(item);
		return this;
	}

	/**
	 * Add the item after the specified position
	 * that must be stored in the list
	 * @param position after the item will be inserted
	 * @param item to be inserted
	 * @return the list
	 */
	@Override
	public List<T> addAfter(Position<T> position, T item) {

		Node<T> node = node(position);
		int index = node.index;

		checkRange(index);

		shiftRight(index + 1);
		data[index + 1] = Node.of(index + 1,item);
		size++;

		return this;
	}

	/**
	 * Add the item before the specified position
	 * that must be stored in the list
	 * @param position before the item will be inserted
	 * @param item to be inserted
	 * @return the list
	 */
	@Override
	public List<T> addBefore(Position<T> position, T item) {

		Node<T> node = node(position);
		int index = node.index;

		checkRange(index);

		shiftRight(index);
		data[index] = Node.of(index,item);
		size++;

		return this;
	}

	/**
	 * Gets the position stored at the given index
	 * @param index of the list to search the position for
	 * @return the position stored at the given index
	 * @throws IndexOutOfBoundsException if index is not accessible
	 */
	public Position<T> get(int index) throws IndexOutOfBoundsException {
		checkRange(index);
		return data[index];
	}

	/**
	 * Gets the first element of the list
	 * @return the first element of the list or null if the list is empty
	 */
	@Override
	public Position<T> first() {
		return isEmpty() ? null : data[0];
	}

	/**
	 * Gets the last element of the list
	 * @return the last element of the list or null if the list is empty
	 */
	@Override
	public Position<T> last() {
		return isEmpty() ? null : data[size - 1];
	}

	/**
	 * Set the value of the position stored at the given index with value
	 * @param item to be stored
	 * @param index of the list where to store the given index
	 * @return the list
	 * @throws IndexOutOfBoundsException if index is not accessible
	 */
	public List<T> put(T item, int index) throws IndexOutOfBoundsException {
		checkRange(index);

		data[index].item = item;
		return this;
	}

	/**
	 * Remove the value of the position stored at the given index with value
	 * @param index of the list where to removeByIndex the given index
	 * @return the list
	 * @throws IndexOutOfBoundsException if index is not accessible
	 */
	public Position<T> removeByIndex(int index) throws IndexOutOfBoundsException {
		checkRange(index);

		Node<T> removedItem = data[index];
		removedItem.index = INVALID;
		shiftLeft(index);
		size--;

		if(size < Math.floorDiv(capacity, 2))
			decreaseCapacity();

		return removedItem;
	}

	/**
	 * Return the index of the given item in the list or -1
	 * if the item is not in the list
	 * @param item
	 * @return index of item or -1 if there's no such item
	 */
	private int findItem(T item) {

		int position = -1;

		for(int idx = 0; idx < size; idx++) {
			if (data[idx].getElement().equals(item)) {
				position = idx;
				break;
			}
		}

		return position;
	}

	/**
	 * Removes the first item equal to the given item from the list
	 * @param item to removeByIndex from the list
	 * @return if the item was found and removed from the list
	 */
	public boolean removeByItem(T item) {
		int position = findItem(item);
		if(position == -1)
			return false;

		removeByIndex(position);
		return true;
	}

	/**
	 * Removes the first position of the list
	 * @return the removed position
	 */
	@Override
	public Position<T> removeFirst() {
		return isEmpty() ? null : removeByIndex(0);
	}

	/**
	 * Removes the last position of the list
	 * @return the removed position
	 */
	@Override
	public Position<T> removeLast() {
		return isEmpty() ? null : removeByIndex(size - 1);
	}

	/**
	 * Removes the next position to the given parameter
	 * @param position previous to the element that will be removed
	 * @return the removed position
	 */
	@Override
	public Position<T> removeNext(Position<T> position) {
		Node<T> node = node(position);
		return isTail(node) ? null : removeByIndex(node.index + 1);
	}

	/**
	 * Removes the previous position to the given parameter
	 * @param position next to the element that will be removed
	 * @return the removed position
	 */
	@Override
	public Position<T> removePrevious(Position<T> position) {
		Node<T> node = node(position);
		return isHead(node) ? null : removeByIndex(node.index - 1);
	}

	@Override
	public void swap(int indexA, int indexB) {

		checkRange(indexA);
		checkRange(indexB);

		Node<T> nodeA = node(get(indexA));
		Node<T> nodeB = node(get(indexB));

		T itemA = nodeA.item;

		nodeA.item = nodeB.item;
		nodeB.item = itemA;
	}

	/**
	 * Empty the list
	 */
	public void clear() {

		// help GC and invalidate nodes
		for (int idx = 0; idx < size; idx++) {
			node(data[idx]).delete();
			data[idx] = null;
		}

		reset(DEFAULT_CAPACITY);
	}

	/**
	 * Check if the given item is stored in the list
	 * @param itemToSearch to be searched for
	 * @return if the item is stored in the list
	 */
	public boolean contains(T itemToSearch) {
		for (T item : this) {
			if(itemToSearch.equals(item))
				return true;
		}
		return false;
	}

	public List<T> sublist(int from, int to) {

		int size = to - from;
		if(size <= 0)
			return Collections.emptyList(); // Empty sublist. TODO improve
		else {
			List<T> sublist = new ArrayList<>(size);
			while(from < to)
				sublist.add(get(from++).getElement());

			return sublist;
		}
	}

	/**
	 * Positions of the list in order
	 * @return the positions stored in the list in order of insertion
	 */
	@Override
	public Iterable<Position<T>> positions() {
		return new PositionIterable();
	}

	/**
	 * Positon iterator
	 */
	private class PositionIterable implements Iterable<Position<T>> {

		@Override
		public Iterator<Position<T>> iterator() {
			return new Iterator<Position<T>>() {

				private int index = 0;

				@Override
				public boolean hasNext() {
					return index < size();
				}

				@Override
				public Position<T> next() {
					Position<T> position = get(index);
					index++;
					return position;
				}
			};
		}
	}

	/*--------------------- Private Methods ---------------------*/

	/**
	 * Duplicates de capacity of the baking array and copies existing data
	 */
	private void increaseCapacity() {
		cloneData(capacity * 2);
	}

	/**
	 * Halves de capacity of the baking array and copies existing data
	 */
	private void decreaseCapacity() {
		cloneData(Math.floorDiv(capacity, 2));
	}

	/**
	 *
	 * @param startIndex
	 */
	private void shiftLeft(int startIndex) {

		int length = size - startIndex - 1;
		System.arraycopy(data, startIndex + 1, data, startIndex, length);

		data[size - 1] = null; // void last item

		// Update nodes internal index
		for (int idx = startIndex; idx < size - 1; idx++)
			data[idx].index--;
	}

	/**
	 *
	 * @param startIndex
	 */
	private void shiftRight(int startIndex) {

		if(size + 1 > capacity)
			increaseCapacity();

		int length = size - startIndex;
		System.arraycopy(data, startIndex, data, startIndex + 1, length);

		data[startIndex] = null;

		// Update nodes internal index
		for (int idx = startIndex + 1; idx <= size; idx++)
			data[idx].index++;
	}

	/**
	 *
	 * @param newCapacity
	 */
	@SuppressWarnings("unchecked")
	private void cloneData(int newCapacity) {
		Node<T>[] newArray = (Node<T>[]) new Node[newCapacity];
		System.arraycopy(data, 0, newArray, 0, size);

		data = newArray;
		capacity = newCapacity;
	}

	@SuppressWarnings("unchecked")
	private void reset(int capacity) {
		data = (Node<T>[]) new Node[capacity];
		this.capacity = capacity;
		size = 0;
	}

	private Node<T> node(Position<T> position) throws IllegalArgumentException {

		if( ! (position instanceof Node))
			throw new IllegalArgumentException("Invalid position");

		// Safe cast
		Node<T> node = (Node<T>) position;

		if(node.index == INVALID)
			throw new IllegalArgumentException("Position is no longer in the list");

		return node;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ArrayList<?> otherList = (ArrayList<?>) o;
		if(size != otherList.size()) return false;
		Iterator<?> iterThis = iterator();
		Iterator<?> iterOther = otherList.iterator();

		while(iterThis.hasNext() && iterOther.hasNext()) {
			if( ! iterThis.next().equals(iterOther.next()))
				return false;
		}
		return true;
	}

	@Override
	protected Object clone() {
		ArrayList<T> newList = new ArrayList<>(size);
		newList.addAll(this);
		return newList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Position<T> pos : this.positions()) {
			sb.append(node(pos)).append("\n");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		// TODO
		return -1;
	}

	private boolean isHead(Node<T> node) {
		return node.index == 0 && size > 0;
	}

	private boolean isTail(Node<T> node) {
		return node.index == size - 1 && size > 0;
	}
}
