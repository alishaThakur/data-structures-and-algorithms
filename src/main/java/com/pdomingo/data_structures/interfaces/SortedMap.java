package com.pdomingo.data_structures.interfaces;

/**
 *
 */
public interface SortedMap<K,V> extends Map<K,V> {

	/**
	 * Return the entry with lowest key
	 * @return entry with smalled key or null if the map is empty
	 */
	Entry<K,V> lowest();

	/**
	 * Return the entry with highest key
	 * @return entry with highest key or null if the map is empty
	 */
	Entry<K,V> highest();

	/**
	 * Return the entry with least key greater than or equal to k
	 * @return entry with highest key or null if no such entry exists
	 */
	Entry<K,V> ceiling(K key);

	/**
	 * Return the entry with highest key less than or equal to k
	 * @return entry with highest key or null if no such entry exists
	 */
	Entry<K,V> floor(K key);

	/**
	 * Return the entry with highest key stricly less than k
	 * @return entry with highest key or null if no such entry exists
	 */
	Entry<K,V> lowerThan(K key);

	/**
	 * Return the entry with highest key stricly greater than k
	 * @return entry with highest key or null if no such entry exists
	 */
	Entry<K,V> higherThan(K key);

	/**
	 * Returns an iterable of entries whose keys are greater than
	 * or equal to k1, but strictly less than k2
	 * @param k1 key1
	 * @param k2 key2
	 * @return iterable of entries whose keys are between k1 and k2
	 */
	Iterable<Entry<K,V>> subMap(K k1, K k2);
}
