package com.pdomingo.data_structures.implementations.list;

import com.pdomingo.data_structures.interfaces.Position;
import com.pdomingo.data_structures.implementations.list.abstracts.AbstractList;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Iterator;


/**
 * List implementation baked by an array
 *
 * @param <T> type
 */
public class ArrayList<T> extends AbstractList<T> {

	private final int DEFAULT_CAPACITY = 16;

	/* Baking array */
	private Node<T>[] data;

	/* Available size of the array */
	private int capacity;

	/* Used size of the array */
	private int size;

	/********************* Nested classes *********************/

	/**
	 * Internal object stored in the array
	 * @param <E>
	 */
	private static class Node<E> implements Position<E> {

		int index;
		E item;

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

		@Override
		public String toString() {
			return String.format("Node[%d] - %s", index, item);
		}
	}

	/********************* Constructors *********************/

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

	/********************* Public methods *********************/

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

		if(index == size - 1) // Node is tail
			addLast(item);
		else {
			shiftRight(index + 1);
			data[index + 1] = Node.of(index + 1,item);
		}

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

		if(index == 0) // Node is head
			addFirst(item);
		else {
			shiftRight(index - 1);
			data[index - 1] = Node.of(index - 1,item);
		}

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
	 * @param index of the list where to remove the given index
	 * @return the list
	 * @throws IndexOutOfBoundsException if index is not accessible
	 */
	public Position<T> remove(int index) throws IndexOutOfBoundsException {
		checkRange(index);

		Node<T> removedItem = data[index];
		removedItem.index = -1;
		shiftLeft(index);

		if(size < Math.floorDiv(capacity, 2))
			decreaseCapacity();

		return removedItem;
	}

	/**
	 * TODO
	 * @param item
	 * @return
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
	 * @param item to remove from the list
	 * @return if the item was found and removed from the list
	 */
	public boolean remove(T item) {
		int position = findItem(item);
		if(position == -1)
			return false;

		remove(position);
		return true;
	}

	/**
	 * Removes the first position of the list
	 * @return the removed position
	 */
	@Override
	public Position<T> removeFirst() {
		Position<T> item = isEmpty() ? null : first();
		shiftRight(0);
		return item;
	}

	/**
	 * Removes the last position of the list
	 * @return the removed position
	 */
	@Override
	public Position<T> removeLast() {
		Position<T> item = isEmpty() ? null : last();
		data[size - 1] = null;
		size--;
		return item;
	}

	/**
	 * Removes the next position to the given parameter
	 * @param position previous to the element that will be removed
	 * @return the removed position
	 */
	@Override
	public Position<T> removeNext(Position<T> position) {

		Node<T> node = node(position);

		if(node.index == size - 1) // Node is tail
			return null;
		else
			return remove(node.index + 1);
	}

	/**
	 * Removes the previous position to the given parameter
	 * @param position next to the element that will be removed
	 * @return the removed position
	 */
	@Override
	public Position<T> removePrevious(Position<T> position) {

		Node<T> node = node(position);

		if(node.index == 0) // Node is head
			return null;
		else
			return remove(node.index - 1);
	}

	@Override
	public void swap(int indexA, int indexB) {

		checkRange(indexA);
		checkRange(indexB);

		Node<T> nodeA = node(get(indexA));
		Node<T> nodeB = node(get(indexB));

		T itemA = nodeA.item;

		nodeA.item = nodeB.item;
		nodeA.index = nodeB.index;

		nodeB.item = itemA;
		nodeB.index = indexA;
	}

	/**
	 * Empty the list
	 */
	public void clear() {

		// help GC and invalidate nodes
		for (int idx = 0; idx < size; idx++) {
			Node<T> node = node(data[idx]);

			node.index = -1;
			node.item = null;
			data[idx] = null;
		}

		reset(DEFAULT_CAPACITY);
	}

	/**
	 * Check if the given item is stored in the list
	 * @param item to be searched for
	 * @return if the item is stored in the list
	 */
	public boolean contains(T item) {
		for (int idx = 0; idx < size; idx++) {
			if(data[idx].getElement().equals(item))
				return true;
		}
		return false;
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

	/********************* Private methods *********************/

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
		data[size - 1] = null; // void last index
		size--;
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
		size++;
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

		if(node.index < 0)
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
	public int hashCode() {
		// TODO
		return -1;
	}
}
