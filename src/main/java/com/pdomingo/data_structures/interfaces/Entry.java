package com.pdomingo.data_structures.interfaces;

/**
 * Wrapper interface to hold a key and a value
 * @param <K> key
 * @param <V> value
 */
public interface Entry<K,V> {

	/**
	 * Get the key for this Entry
	 * @return the key
	 */
	K getKey();

	/**
	 * Get the value for this Entry
	 * @return the value
	 */
	V getValue();

	/**
	 * Get the key for this Entry
	 * @param key
	 */
	void setKey(K key);

	/**
	 * Set the value for this Entry
	 * @param value
	 */
	void setValue(V value);
}
