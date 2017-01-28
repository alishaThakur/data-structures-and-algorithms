package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 22/1/17.
 */
public interface Deque<T> {

	int size();

	void clear();

	boolean isEmpty();

	void addFirst(T item);

	void addLast(T item);

	T removeFirst();

	T removeLast();

	T first();

	T last();
}
