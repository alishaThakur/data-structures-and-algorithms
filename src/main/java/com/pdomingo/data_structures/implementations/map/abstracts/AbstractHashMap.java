package com.pdomingo.data_structures.implementations.map.abstracts;

import com.pdomingo.data_structures.interfaces.Entry;

import java.util.Comparator;
import java.util.Random;

/**
 *
 */
public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {

	private final long prime = 179425867; // prime number larger than capacity
	private final long scale;
	private final long shift;

	protected final Comparator<Entry<K, V>> keyComparator;

	protected AbstractHashMap() {
		Random random = new Random();
		scale = random.nextInt((int) prime - 1) + 1;
		shift = random.nextInt((int) prime);

		keyComparator = entryComparator();
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
