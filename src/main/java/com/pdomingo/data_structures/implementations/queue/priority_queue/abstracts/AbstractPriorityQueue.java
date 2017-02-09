package com.pdomingo.data_structures.implementations.queue.priority_queue.abstracts;

import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.PriorityQueue;

import java.util.Comparator;

/**
 * Created by Pablo on 9/2/17.
 */
public abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V> {

	protected static class PQEntry<K,V> implements Entry<K,V> {

		private K key;
		private V value;

		public static <K,V> PQEntry<K,V> of(K key, V value) {
			return new PQEntry<>(key, value);
		}

		private PQEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return null;
		}

		@Override
		public V getValue() {
			return null;
		}
	}

	private class DefaultComparator<T> implements Comparator<T> {
		@Override
		public int compare(T a, T b) throws ClassCastException {
			return ((Comparable<T>) a).compareTo(b);
		}
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
}
