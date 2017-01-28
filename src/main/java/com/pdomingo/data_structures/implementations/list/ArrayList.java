package com.pdomingo.data_structures.implementations.list;

import com.pdomingo.exceptions.IndexOutOfBoundsException;
import com.pdomingo.exceptions.ItemNotFoundException;
import com.pdomingo.data_structures.implementations.list.abstracts.AbstractList;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Iterator;


public class ArrayList<T> extends AbstractList<T> {

	private final int DEFAULT_CAPACITY = 16;

	private T[] data;
	private int capacity;
	private int size;

	/**
	 *
	 */
	public ArrayList() {
		reset(DEFAULT_CAPACITY);
	}

	/**
	 *
	 */
	public ArrayList(int capacity) {
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

	/**
	 *
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 *
	 * @param item
	 * @return
	 */
	public List<T> add(T item) {

		if(size + 1 > capacity)
			increaseCapacity();

		data[size] = item;
		size++;

		return this;
	}

	@Override
	public List<T> addFirst(T item) {
		shiftRight(0);
		data[0] = item;
		return this;
	}

	@Override
	public List<T> addLast(T item) {
		add(item);
		return this;
	}

	/**
	 *
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public T get(int index) throws IndexOutOfBoundsException {
		checkRange(index);
		return data[index];
	}

	@Override
	public T first() {
		return isEmpty() ? null : data[0];
	}

	@Override
	public T last() {
		return isEmpty() ? null : data[size - 1];
	}

	/**
	 *
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public List<T> put(T item, int index) throws IndexOutOfBoundsException {
		checkRange(index);

		shiftRight(index);
		data[index] = item;
		return this;
	}

	/**
	 *
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public T remove(int index) throws IndexOutOfBoundsException {
		checkRange(index);

		T removedItem = data[index];
		shiftLeft(index);

		if(size < Math.floorDiv(capacity, 2))
			decreaseCapacity();

		return removedItem;
	}

	/**
	 *
	 * @param item
	 * @return
	 * @throws ItemNotFoundException
	 */
	private int findItem(T item) {

		int position = -1;

		for(int idx = 0; idx < size; idx++) {
			if (data[idx].equals(item)) {
				position = idx;
				break;
			}
		}

		return position;
	}

	/**
	 *
	 * @param item
	 * @return
	 */
	public boolean remove(T item) {
		int position = findItem(item);
		if(position == -1)
			return false;

		remove(position);
		return true;
	}

	@Override
	public T removeFirst() {
		T item = isEmpty() ? null : first();
		shiftRight(0);
		return item;
	}

	@Override
	public T removeLast() {
		T item = isEmpty() ? null : last();
		data[size - 1] = null;
		size--;
		return item;
	}

	/**
	 *
	 */
	public void clear() {

		for (int idx = 0; idx < size; idx++)
			data[idx] = null; // help GC

		reset(DEFAULT_CAPACITY);
	}

	/**
	 *
	 * @param item
	 * @return
	 */
	public boolean contains(T item) {
		for (int idx = 0; idx < size; idx++) {
			if(data[idx].equals(item))
				return true;
		}
		return false;
	}

	/**
	 *
	 */
	private void increaseCapacity() {
		cloneData(capacity * 2);
	}

	/**
	 *
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
		T[] newArray = (T[]) new Object[newCapacity];
		System.arraycopy(data, 0, newArray, 0, newCapacity);
		data = newArray;
	}

	@SuppressWarnings("unchecked")
	private void reset(int capacity) {
		data = (T[]) new Object[capacity];
		this.capacity = capacity;
		size = 0;
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
		ArrayList<T> newList = new ArrayList<T>(size);
		newList.addAll(this);
		return newList;
	}

	@Override
	public int hashCode() {
		// TODO
		return -1;
	}
}
