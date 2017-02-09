package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 9/2/17.
 */
public interface PriorityQueue<K,V> {

	int size();

	boolean isEmpty();

	Entry<K,V> insert(K key, V value) throws IllegalArgumentException;

	Entry<K,V> min();

	Entry<K,V> removeMin();
}
