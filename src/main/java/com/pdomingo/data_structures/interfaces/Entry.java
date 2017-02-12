package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 9/2/17.
 */
public interface Entry<K,V> {

	K getKey();

	V getValue();

	void setKey(K key);

	void setValue(V value);
}
