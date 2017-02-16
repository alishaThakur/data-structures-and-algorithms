package com.pdomingo.data_structures.interfaces;

/**
 *
 * @param <T>
 */
public interface Deque<T> {

	/**
	 * Number of elements stored in the deque
	 * @return size of the deque
	 */
	int size();

	/**
	 * Checks if the deque has elements
	 * @return true if the deque is empty
	 */
	boolean isEmpty();

	/**
	 * Empty the deque
	 */
	void clear();

	/**
	 * Appends the item to the start of the deque
	 * @param item to be inserted
	 * @return the deque
	 */
	Deque<T> addFirst(T item);

	/**
	 * Appends the item to the end of the deque
	 * @param item to be inserted
	 * @return the deque
	 */
	Deque<T> addLast(T item);

	/**
	 * Removes the first element of the deque
	 * @return the removed element
	 */
	T removeFirst();

	/**
	 * Removes the last element of the deque
	 * @return the removed element
	 */
	T removeLast();

	/**
	 * Gets the first element of the deque
	 * @return the removed element
	 */
	T first();

	/**
	 * Gets the first element of the deque
	 * @return the removed element
	 */
	T last();
}
