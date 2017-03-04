package com.pdomingo.data_structures.interfaces;

/**
 *
 */
public interface Map<K,V> extends Collection<Entry<K,V>> {

	/**
	 * Adds the value using the given key.
	 * If the key already exists, the value is overriden
	 * @param key
	 * @param value
	 */
	void put(K key, V value);

	/**
	 * Get the value for the given key in the map
	 * @param key
	 * @return the value for key or null if key is not in the map
	 */
	V get(K key);

	/**
	 * Checks if the given key exists in the map
	 * @param key
	 * @return if the key exists
	 */
	boolean containsKey(K key);

	/**
	 * Checks if the given value exists in the map
	 * @param value
	 * @return if the value exists
	 */
	boolean containsValue(V value);

	/**
	 * Delete the value (and key) for the given key
	 * @param key
	 * @return the value for key in the map or null if there's no value
	 */
	V remove(K key);

	/**
	 * Collection of keys stored in the map
	 * @return the keys of the map
	 */
	Iterable<K> keySet();

	/**
	 * Collection of values stored in the map
	 * @return the values of the map
	 */
	Iterable<V> values();

	/**
	 * Collection of pair key-value stored in the map
	 * @return the entries of the map
	 */
	Iterable<Entry<K,V>> entrySet();
}
