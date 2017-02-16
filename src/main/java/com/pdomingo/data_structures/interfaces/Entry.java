package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 9/2/17.
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
