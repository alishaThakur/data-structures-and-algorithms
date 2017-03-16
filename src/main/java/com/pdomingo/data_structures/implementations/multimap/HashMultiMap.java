package com.pdomingo.data_structures.implementations.multimap;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.map.HashMap;
import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Map;
import com.pdomingo.data_structures.interfaces.MultiMap;
import java.util.Iterator;

/**
 *
 */
public class HashMultiMap<K,V> implements MultiMap<K,V> {

	/*---------------------- Attributes ----------------------*/

	private Map<K, List<V>> multimap;
	private int size;

	/*--------------------- Constructors ---------------------*/

	public HashMultiMap() {
		multimap = new HashMap<>();
		size = 0;
	}

	/*--------------------- Public Methods ---------------------*/

	/**
	 * Adds the value using the given key.
	 * If the key already exists, the value is add to the list of value
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void put(K key, V value) {
		if(multimap.containsKey(key))
			multimap.get(key).add(value);
		else
			multimap.put(key, new ArrayList<>(value));

		size++;
	}

	/**
	 * Adds the values using the given key.
	 * If the key already exists, the values are added to the list of value
	 *
	 * @param key
	 * @param values
	 */
	@Override
	public void putAll(K key, Iterable<V> values) {
		if(multimap.containsKey(key)) {
			size -= multimap.get(key).size(); // in order to update correctly the size
			multimap.get(key).addAll(values);
		}
		else
			multimap.put(key, new ArrayList<>(values));

		size += multimap.get(key).size();
	}

	/**
	 * Get the values for the given key in the map
	 *
	 * @param key
	 * @return the values for key or null if key is not in the map
	 */
	@Override
	public List<V> get(K key) {
		return multimap.get(key);
	}

	/**
	 * Checks if the given value exists in the map
	 *
	 * @param value
	 * @return if the value exists
	 */
	@Override
	public boolean containsValue(V value) {
		for(List<V> values : multimap.values())
			if(values.contains(value))
				return true;

		return false;
	}

	/**
	 * Delete the values (and key) for the given key
	 *
	 * @param key
	 * @return the values for key in the map or null if there's no value
	 */
	@Override
	public List<V> remove(K key) {
		size -= multimap.containsKey(key) ? multimap.get(key).size() : 0;
		return multimap.remove(key);
	}

	/**
	 * Collection of keys stored in the map
	 *
	 * @return the keys of the map
	 */
	@Override
	public Iterable<K> keySet() {
		return multimap.keySet();
	}

	/**
	 * Collection of values stored in the map
	 *
	 * @return the values of the map
	 */
	@Override
	public Iterable<List<V>> values() {
		return multimap.values();
	}

	/**
	 * Collection of pair key-value stored in the map
	 *
	 * @return the entries of the map
	 */
	@Override
	public Iterable<Entry<K, List<V>>> entrySet() {
		return multimap.entrySet();
	}

	/**
	 * Number of elements stored in the collection
	 *
	 * @return size of the collection
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Checks if the collection has elements
	 *
	 * @return true if the collection is empty
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Empty the collection
	 */
	@Override
	public void clear() {
		multimap.clear();
		size = 0;
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
