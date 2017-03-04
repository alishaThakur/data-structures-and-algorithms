package com.pdomingo.data_structures.interfaces;

/**
 *
 */
public interface Collection<T> extends Iterable<T>, Cloneable {

	/**
	 * Number of elements stored in the collection
	 * @return size of the collection
	 */
	int size();

	/**
	 * Checks if the collection has elements
	 * @return true if the collection is empty
	 */
	boolean isEmpty();

	/**
	 * Empty the collection
	 */
	void clear();
}
