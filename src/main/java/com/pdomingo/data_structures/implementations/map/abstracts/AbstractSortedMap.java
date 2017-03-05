package com.pdomingo.data_structures.implementations.map.abstracts;

import com.pdomingo.data_structures.interfaces.SortedMap;

import java.util.Comparator;

/**
 *
 */
public abstract class AbstractSortedMap<K,V>
		extends AbstractMap<K,V>
		implements SortedMap<K,V> {

	protected final Comparator<K> keyComparator;

	public AbstractSortedMap(Comparator<K> keyComparator) {
		this.keyComparator = keyComparator;
	}

	public AbstractSortedMap() {
		this(new DefaultComparator<>());
	}
}
