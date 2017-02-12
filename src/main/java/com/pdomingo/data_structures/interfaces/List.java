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

	List<T> addAfter(Position<T> position, T item);

	List<T> addBefore(Position<T> position, T item);

	List<T> addAll(Iterable<T> items);

	Position<T> get(int index) throws IndexOutOfBoundsException;

	Position<T> first();

	Position<T> last();

	List<T> put(T item, int index) throws IndexOutOfBoundsException;

	Position<T> remove(int index) throws IndexOutOfBoundsException;

	boolean remove(T item);

	Position<T> removeFirst();

	Position<T> removeLast();

	Position<T> removeNext(Position<T> position);

	Position<T> removePrevious(Position<T> position);

	Iterable<T> removeAll(Iterable<T> items);

	void clear();

	boolean contains(T item);

	boolean containsAll(Iterable<T> items);

	Iterable<Position<T>> positions();
}
