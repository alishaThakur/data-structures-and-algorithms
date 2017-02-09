package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 21/12/16.
 */
public interface Queue<T> extends Iterable<T> {

	int size();

	void clear();

	boolean isEmpty();

	void enqueue(T item);

	void enqueueAll(Iterable<T> items);

	T dequeue();

	T peek();
}
