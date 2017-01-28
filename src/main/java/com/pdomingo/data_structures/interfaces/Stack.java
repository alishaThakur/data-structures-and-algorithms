package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 21/12/16.
 */
public interface Stack<T> extends Iterable<T> {

	int size();

	void clear();

	boolean isEmpty();

	void push(T item);

	T pop();

	T peek();
}
