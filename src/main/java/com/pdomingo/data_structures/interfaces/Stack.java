package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 21/12/16.
 */
public interface Stack<T> extends Iterable<T> {

	/**
	 * Number of elements stored in the stack
	 * @return size of the stack
	 */
	int size();

	/**
	 * Checks if the stack has elements
	 * @return true if the stack is empty
	 */
	boolean isEmpty();

	/**
	 * Empty the stack
	 */
	void clear();

	void push(T item);

	T pop();

	T peek();
}
