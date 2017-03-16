package com.pdomingo.data_structures.implementations.set;

import com.pdomingo.data_structures.implementations.map.HashMap;
import com.pdomingo.data_structures.implementations.set.abstracts.AbstractSet;
import com.pdomingo.data_structures.interfaces.Map;

import java.util.Iterator;

/**
 * Set implementation baked by a HashMap
 * @param <T> type of the elements
 */
public class HashSet<T> extends AbstractSet<T> {

	private final Map<T, Object> set;
	private static final Object PRESENT = new Object();

	/*--------------------- Constructors ---------------------*/

	public HashSet() {
		set = new HashMap<>();
	}

	public HashSet(Iterable<T> items) {
		set = new HashMap<>();
		addAll(items);
	}

	/*--------------------- Public Methods ---------------------*/

	/**
	 * Number of elements stored in the collection
	 *
	 * @return size of the collection
	 */
	@Override
	public int size() {
		return set.size();
	}

	/**
	 * Empty the collection
	 */
	@Override
	public void clear() {
		set.clear();
	}

	/**
	 * Add the item to the set
	 * If the item is already present in the set, no action is performed
	 *
	 * @param item
	 */
	@Override
	public void add(T item) {
		if( ! set.containsKey(item))
			set.put(item, PRESENT);
	}

	/**
	 * Remove the item from the set
	 * If the item is not present in the set, no action is performed
	 *
	 * @param item
	 */
	@Override
	public void remove(T item) {
		if(set.containsKey(item))
			set.remove(item);
	}

	/**
	 * Check if the given item is present on the set
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean contains(T item) {
		return set.get(item) != PRESENT;
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<T> iterator() {
		return set.keySet().iterator();
	}
}
