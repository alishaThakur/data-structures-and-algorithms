package com.pdomingo.data_structures.interfaces;

/**
 * FIFO (First In First Out) positional group of elements
 * @param <T> type
 */
public interface Queue<T> extends Collection<T> {

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
