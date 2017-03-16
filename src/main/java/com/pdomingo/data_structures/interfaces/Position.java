package com.pdomingo.data_structures.interfaces;

/**
 * Simple interface to allow accesing the data stored in a data structure without
 * leaking the internal structure/interfaces use by the element that stores the
 * data
 * @param <T> type
 */
public interface Position<T> {

	/**
	 * Gets the element stored in the position
	 * @return the element
	 */
	T getElement();
}
