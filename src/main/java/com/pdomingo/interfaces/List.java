package com.pdomingo.interfaces;


public interface List<T> extends Iterable<T> {

	int size();

	boolean isEmpty();

	List<T> add(T item);

	List<T> addAll(Iterable<T> items);

	T get(int index) throws IndexOutOfBoundsException;

	List<T> put(T item, int index) throws IndexOutOfBoundsException;

	T remove(int index) throws IndexOutOfBoundsException;

	boolean remove(T item);

	Iterable<T> removeAll(Iterable<T> items);

	void clear();

	boolean contains(T item);
}
