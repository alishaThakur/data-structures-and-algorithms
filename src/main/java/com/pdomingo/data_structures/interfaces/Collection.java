package com.pdomingo.data_structures.interfaces;

/**
 *
 */
public interface Collection<T> extends Iterable<T>, Cloneable {

	/**
	 * Number of elements stored in the list
	 * @return size of the list
	 */
	int size();

	/**
	 * Checks if the list has elements
	 * @return true if the list is empty
	 */
	boolean isEmpty();

	/**
	 * Empty the list
	 */
	void clear();
}
