package com.pdomingo.algorithms.interfaces;

import com.pdomingo.data_structures.interfaces.List;

/**
 *
 */
public interface SortStrategy<T> {
	List<T> sort(List<T> list);
}
