package com.pdomingo.data_structures.interfaces;

/**
 *
 */
public interface Map<K,V> extends Collection<Entry<K,V>> {

	void put(K key, V value);

	Entry<K,V> get(K key);

	boolean containsKey(K key);

	boolean containsValue(V value);
}
