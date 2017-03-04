package com.pdomingo.data_structures.implementations.map.abstracts;

import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.Map;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @param <K>
 * @param <V>
 */
public abstract class AbstractMap<K,V> implements Map<K,V> {

	protected long prime = 179425867;
	protected long scale;
	protected long shift;

	protected final Comparator<Entry<K, V>> keyComparator;

	protected AbstractMap() {
		Random random = new Random();
		scale = random.nextInt((int) prime - 1) + 1;
		shift = random.nextInt((int) prime);

		keyComparator = keyComparator();
	}

	protected static <K,V> Comparator<Entry<K, V>> keyComparator() {
		return new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return o1.getKey().equals(o2.getKey()) ? 0 : -1;
			}
		};
	}

	protected static class MapEntry<K, V> implements Entry<K, V> {

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
			return "MapEntry{" + key + ", " + value + "}";
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

	/**
	 * Compress the given hashCode value to the range [0, N-1]
	 * using Multiply-Add-Divide method
	 * @param hashCode
	 * @param size
	 * @return
	 */
	protected int compress(int hashCode, int size) {
		return (int) ((scale * hashCode + shift) % prime) % size;
	}

	/**
	 * Find the next prime number after the given number
	 *
	 * http://stackoverflow.com/a/30052481
	 *
	 * @param number
	 * @return next prime
	 */
	protected static int nextPrime(int number) {

		if(number <= 0)
			return 2; // first prime

		int nextPrime = number;

		outerloop:
		while(true) {

			nextPrime++;

			if (nextPrime == 2 || nextPrime == 3)
				break;
			if (nextPrime % 2 == 0 || nextPrime % 3 == 0)
				continue;

			int divisor = 6;
			while (divisor * divisor - 2 * divisor + 1 <= nextPrime) {

				if (nextPrime % (divisor - 1) == 0)
					continue outerloop;
				if (nextPrime % (divisor + 1) == 0)
					continue outerloop;

				divisor += 6;
			}
			break;
		}

		return nextPrime;
	}
}
