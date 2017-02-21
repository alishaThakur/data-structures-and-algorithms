package com.pdomingo.data_structures.interfaces;

/**
 * FIFO (First In First Out) positional group of elements
 * @param <T> type
 */
public interface Queue<T> extends Iterable<T> {

	/**
	 * Number of elements stored in the queue
	 * @return size of the queue
	 */
	int size();

	/**
	 * Checks if the queue has elements
	 * @return true if the queue is empty
	 */
	boolean isEmpty();

	/**
	 * Empty the queue
	 */
	void clear();

	/**
	 * Inserst a new item at the last position of the queue
	 * @param item
	 */
	void enqueue(T item);

	/**
	 * Inserts one by one each object of the iterable at the last
	 * position of the queue
	 * @param items
	 */
	void enqueueAll(Iterable<T> items);

	/**
	 * Extracts the first (most time being stored) element of the queue
	 * @return the element
	 */
	T dequeue();

	/**
	 * Gets the first (most time being stored) element of the queue
	 * @return the element
	 */
	T peek();
}
