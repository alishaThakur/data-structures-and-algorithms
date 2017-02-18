package com.pdomingo.data_structures.implementations.priority_queue.abstracts;

import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.PriorityQueue;

import java.util.Comparator;

/**
 * Created by Pablo on 9/2/17.
 */
public abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V> {

	protected Comparator<K> comparator;
	protected Order order;

	public static final double log2 = Math.log(2);

	protected AbstractPriorityQueue(Comparator<K> comparator, Order order) {
		this.comparator = comparator;
		this.order = order;
	}

	protected AbstractPriorityQueue(Comparator<K> comparator) {
		this(comparator, Order.MAX);
	}

	protected AbstractPriorityQueue(Order order) {
		this(new DefaultComparator<>(), order);
	}

	protected AbstractPriorityQueue() {
		this(new DefaultComparator<>(), Order.MAX);
	}

	/**
	 *
	 * @param <K>
	 * @param <V>
	 */
	protected static class PQEntry<K,V> implements Entry<K,V> {

		private K key;
		private V value;

		public static <K,V> PQEntry<K,V> of(K key, V value) {
			return new PQEntry<>(key, value);
		}

		protected PQEntry(K key, V value) {
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
			return "PQEntry{" + key + ", "+ value + "}";
		}
	}

	protected enum Order {
		MAX(1), MIN(-1);

		private final int value;

		Order(int value) {
			this.value = value;
		}
	}

	protected int compare(Entry<K,V> a, Entry<K,V> b) {
		// Reverse cmp if heap is first
		return order.value * comparator.compare(a.getKey(), b.getKey());
	}

	protected static class DefaultComparator<K> implements Comparator<K> {
		@Override
		public int compare(K a, K b) throws ClassCastException {
			return ((Comparable<K>) a).compareTo(b);
		}
	}

	protected boolean checkKey(K key) throws IllegalArgumentException {
		try {
			return comparator.compare(key, key) == 0;
		} catch(ClassCastException ex) {
			throw new IllegalArgumentException("Illegal key");
		}
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
}
