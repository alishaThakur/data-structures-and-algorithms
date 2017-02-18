package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 21/12/16.
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

	void enqueue(T item);

	void enqueueAll(Iterable<T> items);

	T dequeue();

	T peek();
}
