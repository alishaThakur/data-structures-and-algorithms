package com.pdomingo.data_structures.interfaces;

/**
 * LIFO (Last In First Out) positional group of elements
 * @param <T> type
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

	/**
	 * Inserts a new item at the top of the stack
	 * @param item
	 */
	void push(T item);

	/**
	 * Extract the element at the top of the stack or null
	 * if the stack if empty
	 * @return element of null is the stack is empty
	 */
	T pop();

	/**
	 * Get the element at the top of the stack or null
	 * if the stack if empty
	 * @return element of null is the stack is empty
	 */
	T peek();
}
