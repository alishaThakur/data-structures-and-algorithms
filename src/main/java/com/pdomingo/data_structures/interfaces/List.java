package com.pdomingo.data_structures.interfaces;

/**
 *
 * @param <T>
 */
public interface List<T> extends Iterable<T>, Cloneable {

	int size();

	boolean isEmpty();

	List<T> add(T item);

	List<T> addFirst(T item);

	List<T> addLast(T item);

	List<T> addAll(Iterable<T> items);

	T get(int index) throws IndexOutOfBoundsException;

	T first();

	T last();

	List<T> put(T item, int index) throws IndexOutOfBoundsException;

	T remove(int index) throws IndexOutOfBoundsException;

	boolean remove(T item);

	T removeFirst();

	T removeLast();

	Iterable<T> removeAll(Iterable<T> items);

	void clear();

	boolean contains(T item);

	boolean containsAll(Iterable<T> items);
}
