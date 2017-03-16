package com.pdomingo.data_structures.interfaces;

/**
 * Collection of elements where duplicates are not allowed
 * @param <T> type
 */
public interface Set<T> extends Collection<T> {

	/**
	 * Add the item to the set
	 * If the item is already present in the set, no action is performed
	 * @param item
	 */
	void add(T item);

	/**
	 * Remove the item from the set
	 * If the item is not present in the set, no action is performed
	 * @param item
	 */
	void remove(T item);

	/**
	 * Check if the given item is present on the set
	 * @param item
	 * @return
	 */
	boolean contains(T item);

	/**
	 * Perform the mathematical union of both sets
	 * That is:
	 *      A ∪ B = { x : x ∈ A or x ∈ B }
	 * @param set
	 * @return
	 */
	Set<T> union(Set<T> set);

	/**
	 * Perform the mathematical intersection of both sets
	 * That is:
	 *      A ∩ B = { x : x ∈ A and x ∈ B }
	 * @param set
	 * @return
	 */
	Set<T> intersection(Set<T> set);

	/**
	 * Perform the mathematical intersection of both sets
	 * That is:
	 *      A - B = { x : x ∈ A and x not ∈ B }
	 * @param set
	 * @return
	 */
	Set<T> difference(Set<T> set);

	/**
	 * Add all the element of the iterable into the set
	 * @param items
	 */
	void addAll(Iterable<T> items);
}
