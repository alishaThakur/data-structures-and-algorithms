package com.pdomingo.data_structures.implementations.common;

import com.pdomingo.data_structures.implementations.list.LinkedList;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class Collections {

    private static final Iterable EMPTY_ITERABLE = new EmptyIterable<>();
	private static final List EMPTY_LIST = new LinkedList<>(); // TODO hacer inmutable

	public static <T> List<T> emptyList() {
		return (List<T>) EMPTY_LIST;
	}

	public static <T> Iterable<T> emptyIterable() {
		return (Iterable<T>) EMPTY_ITERABLE;
	}

	public static final class EmptyIterable<T> implements Iterable<T> {

		/**
		 * Returns an iterator over elements of type {@code T}.
		 *
		 * @return an Iterator.
		 */
		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {
				/**
				 * Returns {@code true} if the iteration has more elements.
				 * (In other words, returns {@code true} if {@link #next} would
				 * return an element rather than throwing an exception.)
				 *
				 * @return {@code true} if the iteration has more elements
				 */
				@Override
				public boolean hasNext() {
					return false;
				}

				/**
				 * Returns the next element in the iteration.
				 *
				 * @return the next element in the iteration
				 * @throws NoSuchElementException if the iteration has no more elements
				 */
				@Override
				public T next() {
					throw new NoSuchElementException();
				}
			};
		}
	}
}
