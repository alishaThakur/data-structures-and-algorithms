package com.pdomingo.data_structures.interfaces;

/**
 *
 */
public interface AdaptablePriorityQueue<K,V> extends PriorityQueue<K,V> {

	void replaceKey(Entry<K,V> entry, K key);

	void replaceValue(Entry<K,V> entry, V value);

	Entry<K,V> remove(Entry<K,V> entry);
}
