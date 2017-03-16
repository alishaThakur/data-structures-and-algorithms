package com.pdomingo.data_structures.interfaces;

/**
 * Special type of map where a single key may have 1 or more values
 * asociated. In pure maps, a key has 0 or 1 values.
 * @param <K> key type
 * @param <V> value type
 */
public interface MultiMap<K,V> extends Collection<Entry<K, V>> {

	/**
	 * Adds the value using the given key.
	 * If the key already exists, the value is add to the list of value
	 * @param key
	 * @param value
	 */
	void put(K key, V value);

	/**
	 * Adds the values using the given key.
	 * If the key already exists, the values are added to the list of value
	 * @param key
	 * @param values
	 */
	void putAll(K key, Iterable<V> values);

	/**
	 * Get the values for the given key in the map
	 * @param key
	 * @return the values for key or null if key is not in the map
	 */
	List<V> get(K key);

	/**
	 * Checks if the given value exists in the map
	 * @param value
	 * @return if the value exists
	 */
	boolean containsValue(V value);

	/**
	 * Delete the values (and key) for the given key
	 * @param key
	 * @return the values for key in the map or null if there's no value
	 */
	List<V> remove(K key);

	/**
	 * Collection of keys stored in the map
	 * @return the keys of the map
	 */
	Iterable<K> keySet();

	/**
	 * Collection of values stored in the map
	 * @return the values of the map
	 */
	Iterable<List<V>> values();

	/**
	 * Collection of pair key-value stored in the map
	 * @return the entries of the map
	 */
	Iterable<Entry<K, List<V>>> entrySet();


}
