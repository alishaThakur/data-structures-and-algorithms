package com.pdomingo.data_structures.implementations.map;

import com.pdomingo.data_structures.implementations.common.Collections;
import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.map.abstracts.AbstractSortedMap;
import com.pdomingo.data_structures.interfaces.*;

/**
 * Key ordered map
 * @param <K>
 * @param <V>
 */
public class SortedTableMap<K,V> extends AbstractSortedMap<K,V> {

	private List<Entry<K,V>> table;

	/*--------------------- Constructors ---------------------*/

	public SortedTableMap() {
		super();
		table = new ArrayList<>();
	}

	/*--------------------- Public Methods ---------------------*/

	/**
	 * Number of elements stored in the collection
	 *
	 * @return size of the collection
	 */
	@Override
	public int size() {
		return table.size();
	}

	/**
	 * Empty the collection
	 */
	@Override
	public void clear() {
		table.clear();
	}

	/**
	 * Return the entry with lowest key
	 *
	 * @return entry with smalled key or null if the map is empty
	 */
	@Override
	public Entry<K, V> lowest() {
		return entryAt(0);
	}

	/**
	 * Return the entry with highest key
	 *
	 * @return entry with highest key or null if the map is empty
	 */
	@Override
	public Entry<K, V> highest() {
		return entryAt(size() - 1);
	}

	/**
	 * Return the entry with least key greater than or equal to k
	 *
	 * @param key
	 * @return entry with highest key or null if no such entry exists
	 */
	@Override
	public Entry<K, V> ceiling(K key) {
		return entryAt(findIndex(key));
	}

	/**
	 * Return the entry with highest key less than or equal to k
	 *
	 * @param key
	 * @return entry with highest key or null if no such entry exists
	 */
	@Override
	public Entry<K, V> floor(K key) {

		int keyIndex = findIndex(key);
		Entry<K,V> entry = entryAt(keyIndex);

		if(exactMatch(key, entry) || keyIndex <= size())
			keyIndex -= 1;

		return entryAt(keyIndex);
	}

	/**
	 * Return the entry with highest key stricly less than k
	 *
	 * @param key
	 * @return entry with highest key or null if no such entry exists
	 */
	@Override
	public Entry<K, V> lowerThan(K key) {
		return entryAt(findIndex(key) - 1);
	}

	/**
	 * Return the entry with highest key stricly greater than k
	 *
	 * @param key
	 * @return entry with highest key or null if no such entry exists
	 */
	@Override
	public Entry<K, V> higherThan(K key) {
		return entryAt(findIndex(key) + 1);
	}

	/**
	 * Returns an iterable of entries whose keys are greater than
	 * or equal to k1, but strictly less than k2
	 *
	 * @param k1 key1
	 * @param k2 key2
	 * @return iterable of entries whose keys are between k1 and k2
	 */
	@Override
	public Iterable<Entry<K, V>> subMap(K k1, K k2) {

		// Locate indices
		int from = findIndex(k1);
		int to = findIndex(k2);

		// Ensure constraints
		if(from < 0 || from > size() - 1)
			from = 0;

		if(to < 0 || to > size() - 1)
			to = size();

		// Get sublist
		if(to - from <= 0)
			return Collections.emptyIterable();
		else
			return table.sublist(from, to);
	}

	/**
	 * Adds the value using the given key.
	 * If the key already exists, the value is overriden
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void put(K key, V value) {

		int keyIndex = findIndex(key);
		Position<Entry<K,V>> position = isEmpty() ? null : table.get(keyIndex > 0 ? keyIndex - 1 : 0);
		Entry<K,V> entry = position == null ? null : position.getElement();
		boolean exactMatch = exactMatch(key, entry);

		if(exactMatch)
			entry.setValue(value);
		else if (keyIndex == 0)
			table.addFirst(MapEntry.of(key,value));
		else
			table.addAfter(position,MapEntry.of(key,value));
	}

	/**
	 * Get the value for the given key in the map
	 *
	 * @param key
	 * @return the value for key or null if key is not in the map
	 */
	@Override
	public V get(K key) {
		Entry<K,V> entry = entryAt(findIndex(key));
		if(exactMatch(key, entry))
			return entry.getValue();
		else
			return null;
	}

	/**
	 * Checks if the given key exists in the map
	 *
	 * @param key
	 * @return if the key exists
	 */
	@Override
	public boolean containsKey(K key) {
		return exactMatch(key, entryAt(findIndex(key)));
	}

	/**
	 * Checks if the given value exists in the map
	 *
	 * @param value
	 * @return if the value exists
	 */
	@Override
	public boolean containsValue(V value) {
		for(Entry<K,V> entry : table)
			if(entry.getValue().equals(value))
				return true;

		return false;
	}

	/**
	 * Delete the value (and key) for the given key
	 *
	 * @param key
	 * @return the value for key in the map or null if there's no value
	 */
	@Override
	public V remove(K key) {

		int keyIndex = findIndex(key);
		Entry<K,V> entry = entryAt(findIndex(key));

		if(exactMatch(key, entry))
			return table.removeByIndex(keyIndex).getElement().getValue();
		else
			return null;
	}

	/**
	 * Collection of pair key-value stored in the map
	 *
	 * @return the entries of the map
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return table;
	}

	/*--------------------- Private Methods ---------------------*/

	/**
	 *
	 * @param key
	 * @param low
	 * @param high
	 * @return
	 */
	private int findIndex(K key, int low, int high) {

		if(high < low)
			return high + 1;

		int mid = (low + high) / 2;

		K midKey = table.get(mid).getElement().getKey();
		int cmp = keyComparator.compare(key, midKey);

		if(cmp == 0)
			return mid;
		else if(cmp < 0)
			return findIndex(key, low, mid-1);
		else
			return findIndex(key, mid+1, high);
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	private int findIndex(K key) {
		return findIndex(key, 0, size() - 1);
	}

	/**
	 *
	 * @param index
	 * @return
	 */
	private Entry<K,V> entryAt(int index) {
		return (index >= 0 && index < size()) ? table.get(index).getElement() : null;
	}

	/**
	 * Check if the match for the searched key is exact (entry key is equal to key)
	 * @param searchedKey
	 * @param foundEntry
	 * @return
	 */
	private boolean exactMatch(K searchedKey, Entry<K,V> foundEntry) {
		return foundEntry != null && keyComparator.compare(foundEntry.getKey(), searchedKey) == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<K,V> entry : table)
			sb.append(entry.toString()).append("\n");

		return sb.toString();
	}
}
