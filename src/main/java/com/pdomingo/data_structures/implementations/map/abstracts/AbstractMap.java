package com.pdomingo.data_structures.implementations.map.abstracts;

import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.Map;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @param <K>
 * @param <V>
 */
public abstract class AbstractMap<K,V> implements Map<K,V> {

	public static class MapEntry<K, V> implements Entry<K, V> {

		private K key;
		private V value;

		public static <K, V> MapEntry<K, V> of(K key, V value) {
			return new MapEntry<>(key, value);
		}

		protected MapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public void setKey(K key) {
			this.key = key;
		}

		@Override
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "MapEntry{" + key + " => " + value + "}";
		}
	}

	protected static <K,V> Comparator<Entry<K, V>> entryComparator() {
		return new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return ((Comparable<K>) o1.getKey()).compareTo(o2.getKey());
			}
		};
	}

	protected static class DefaultComparator<K> implements Comparator<K> {
		@Override
		public int compare(K a, K b) throws ClassCastException {
			return ((Comparable<K>) a).compareTo(b);
		}
	}

	/**
	 * Checks if the list has elements
	 *
	 * @return true if the list is empty
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Collection of keys stored in the map
	 *
	 * @return the keys of the map
	 */
	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}

	/**
	 * Collection of values stored in the map
	 *
	 * @return the values of the map
	 */
	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return entrySet().iterator();
	}


	/********************************************************************/

	private class KeyIterable implements Iterable<K> {

		@Override
		public Iterator<K> iterator() {
			return new Iterator<K>() {

				private Iterator<Entry<K,V>> entries = entrySet().iterator();

				@Override
				public boolean hasNext() {
					return entries.hasNext();
				}

				@Override
				public K next() {
					return entries.next().getKey();
				}
			};
		}
	}

	private class ValueIterable implements Iterable<V> {

		@Override
		public Iterator<V> iterator() {
			return new Iterator<V>() {

				private Iterator<Entry<K,V>> entries = entrySet().iterator();

				@Override
				public boolean hasNext() {
					return entries.hasNext();
				}

				@Override
				public V next() {
					return entries.next().getValue();
				}
			};
		}
	}
}
