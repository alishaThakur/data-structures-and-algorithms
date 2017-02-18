package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 9/2/17.
 */
public interface PriorityQueue<K,V> {

	/**
	 * Number of elements stored in the priority queue
	 * @return size of the priority queue
	 */
	int size();

	/**
	 * Checks if the priority queue has elements
	 * @return true if the priority queue is empty
	 */
	boolean isEmpty();

	/**
	 * Empty the priority queue
	 */
	void clear();

	Entry<K,V> insert(K key, V value) throws IllegalArgumentException;

	Entry<K,V> first();

	Entry<K,V> removeFirst();
}
