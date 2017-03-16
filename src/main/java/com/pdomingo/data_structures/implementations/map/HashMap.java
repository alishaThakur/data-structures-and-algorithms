package com.pdomingo.data_structures.implementations.map;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.map.abstracts.AbstractHashMap;
import com.pdomingo.data_structures.implementations.set.HashSet;
import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Set;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Unsorted Map with bucket chaining collision handling implementation
 * By default the load factor is 0.75 and the initial capacity is 131
 * items so there won't be a full rehash until 99 items have been
 * inserted (99/131 = 0,755 > loadFactor)
 *
 * @param <K> key
 * @param <V> value
 */
public class HashMap<K, V> extends AbstractHashMap<K, V> {

	private Bucket<K,V>[] map;
	private Set<K> keySet;

	private final double maxLoadFactor;
	private int size;
	private int capacity;

	private static final double DEFAULT_LOAD_FACTOR = 0.75;

	/*--------------------- Constructors ---------------------*/

	@SuppressWarnings("unchecked")
	public HashMap(double maxLoadFactor, int capacity) {
		this.capacity = capacity;
		this.maxLoadFactor = maxLoadFactor;

		map = (Bucket<K, V>[]) new Bucket[capacity];
		keySet = new HashSet<>();
	}

	public HashMap() {
		this(DEFAULT_LOAD_FACTOR, 131);
	}

	/*--------------------- Nested Classes ---------------------*/

	private static class Bucket<K,V> {

		private List<Entry<K, V>> entries;
		private final Comparator<Entry<K, V>> keyComparator;

		private Bucket(Entry<K,V> entry, Comparator<Entry<K, V>> comparator) {
			entries = new ArrayList<>(4);
			keyComparator = comparator;

			entries.add(entry);
		}

		private Entry<K,V> get(K key) {
			int keyIndex = findByKey(MapEntry.of(key,null));
			return keyIndex != -1 ? entries.get(keyIndex).getElement() : null;
		}

		private V put(Entry<K, V> entry) {

			int keyIndex = findByKey(entry);
			V oldValue = null;

			if(keyIndex == -1)
				entries.add(entry);
			else {
				oldValue = entries.get(keyIndex).getElement().getValue();
				entries.get(keyIndex).getElement().setValue(entry.getValue());
			}

			return oldValue;
		}

		private int findByKey(Entry<K, V> entry) {
			return entries.findIndex(entry, keyComparator);
		}

		private Entry<K,V> remove(K key) {
			int keyIndex = findByKey(MapEntry.of(key,null));
			return entries.removeByIndex(keyIndex).getElement();
		}

		private void clear() {
			entries.clear();
		}
	}

	/*--------------------- Public Methods ---------------------*/

	/**
	 * Number of elements stored in the list
	 *
	 * @return size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Empty the list
	 */
	@Override
	public void clear() {
		for (int lvl1 = 0; lvl1 < map.length; lvl1++) {
			if (map[lvl1] != null) {
				map[lvl1].clear();
				map[lvl1] = null;
			}
		}

		keySet.clear();
		size = 0;
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

		int hashCode = key.hashCode();
		int hashIndex = compress(hashCode, capacity);

		Entry<K,V> entry = MapEntry.of(key, value);

		if(map[hashIndex] != null)
			map[hashIndex].put(entry);
		else
			map[hashIndex] = new Bucket<>(entry, keyComparator);

		keySet.add(key);
		size += 1;

		checkLoadFactor();
	}

	/**
	 * Get the value for the given key in the map
	 *
	 * @param key
	 * @return the value for key or null if key is not in the map
	 */
	@Override
	public V get(K key) {

		/* To locate the position in the map for the key, two operations
		 * must be done beforehand:
		 *
		 * - Calculate hash value for the given key
		 * - Compress the hash to the range [0, size - 1]
		 *
		 * We rely on the native implementation of K hashCode() */

		int hashCode = key.hashCode();
		int hashIndex = compress(hashCode, capacity);

		Bucket<K,V> bucket = map[hashIndex];
		return bucket == null ? null : bucket.get(key).getValue();
	}

	/**
	 * Checks if the given key exists in the map
	 *
	 * @param key
	 * @return if the key exists
	 */
	@Override
	public boolean containsKey(K key) {
		return keySet.contains(key);
	}

	/**
	 * Checks if the given value exists in the map
	 *
	 * @param value
	 * @return if the value exists
	 */
	@Override
	public boolean containsValue(V value) {
		for(K key : keySet)
			if(get(key).equals(value))
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

		int hashCode = key.hashCode();
		int hashIndex = compress(hashCode, capacity);
		V value = null;

		if(map[hashIndex] != null)
			value = map[hashIndex].remove(key).getValue();

		keySet.remove(key);
		size -= 1;

		return value;
	}

	/**
	 * Collection of pair key-value stored in the map
	 *
	 * @return the entries of the map
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		List<Entry<K, V>> entries = new ArrayList<>(size);
		for(Bucket<K,V> bucket : map)
			if(bucket != null)
				entries.addAll(bucket.entries);

		return entries;
	}

	/*--------------------- Private Methods ---------------------*/

	private void checkLoadFactor() {
		if((float) size / capacity > maxLoadFactor) {
			List<Entry<K, V>> entries = growMap();

			for (Entry<K,V> entry : entries)
				put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Increase the size of the map due to a loadFactor violation
	 * New size will be the next prime number double the current capacity
	 * @return entries from the map
	 */
	private List<Entry<K,V>> growMap() {

		List<Entry<K,V>> entries = new ArrayList<>(size);

		for (int lvl1 = 0; lvl1 < map.length; lvl1++) {

			Bucket<K,V> bucket = map[lvl1];
			if(bucket != null) {
				entries.addAll(bucket.entries);
				map[lvl1] = null;
			}
		}

		size = 0;
		capacity = nextPrime(capacity * 2);
		map = (Bucket<K, V>[]) new Bucket[capacity];

		return entries;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for(Entry<K,V> entry : entrySet())
			sb.append(entry.getKey()).append(" => ").append(entry.getValue()).append("\n");

		return sb.toString();
	}
}
