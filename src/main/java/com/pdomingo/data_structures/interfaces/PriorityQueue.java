package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 9/2/17.
 */
public interface PriorityQueue<K,V> extends Collection<Entry<K,V>> {

	Entry<K,V> insert(K key, V value) throws IllegalArgumentException;

	Entry<K,V> first();

	Entry<K,V> removeFirst();
}
