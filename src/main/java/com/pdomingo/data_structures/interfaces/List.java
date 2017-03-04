package com.pdomingo.data_structures.interfaces;

import java.util.Comparator;

/**
 * Positional ordered group of elements
 * @param <T> type
 */
public interface List<T> extends Collection<T> {

	/**
	 * Appends the item to the end of the list (shortcut for addLast)
	 * @param item to be inserted
	 * @return the list
	 */
	List<T> add(T item);

	/**
	 * Appends the item to the start of the list
	 * @param item to be inserted
	 * @return the list
	 */
	List<T> addFirst(T item);

	/**
	 * Appends the item to the end of the list
	 * @param item to be inserted
	 * @return the list
	 */
	List<T> addLast(T item);

	/**
	 * Add the item after the specified position
	 * that must be stored in the list
	 * @param position after the item will be inserted
	 * @param item to be inserted
	 * @return the list
	 */
	List<T> addAfter(Position<T> position, T item);

	/**
	 * Add the item before the specified position
	 * that must be stored in the list
	 * @param position before the item will be inserted
	 * @param item to be inserted
	 * @return the list
	 */
	List<T> addBefore(Position<T> position, T item);

	/**
	 * Adds all the items to the list
	 * @param items to be inserted in the list
	 * @return the list
	 */
	List<T> addAll(Iterable<T> items);

	/**
	 * Gets the position stored at the given index
	 * @param index of the list to search the position for
	 * @return the position stored at the given index
	 * @throws IndexOutOfBoundsException if index is not accessible
	 */
	Position<T> get(int index) throws IndexOutOfBoundsException;

	/**
	 * Gets the first element of the list
	 * @return the first element of the list or null if the list is empty
	 */
	Position<T> first();

	/**
	 * Gets the last element of the list
	 * @return the last element of the list or null if the list is empty
	 */
	Position<T> last();

	/**
	 * Set the value of the position stored at the given index with value
	 * @param item to be stored
	 * @param index of the list where to store the given index
	 * @return the list
	 * @throws IndexOutOfBoundsException if index is not accessible
	 */
	List<T> put(T item, int index) throws IndexOutOfBoundsException;

	/**
	 * Remove the value of the position stored at the given index with value
	 * @param index of the list where to removeByIndex the given index
	 * @return the list
	 * @throws IndexOutOfBoundsException if index is not accessible
	 */
	Position<T> removeByIndex(int index) throws IndexOutOfBoundsException;

	/**
	 * Removes the first item equal to the given item from the list
	 * @param item to removeByIndex from the list
	 * @return if the item was found and removed from the list
	 */
	boolean removeByItem(T item);

	/**
	 * Removes the first position of the list
	 * @return the removed position
	 */
	Position<T> removeFirst();

	/**
	 * Removes the last position of the list
	 * @return the removed position
	 */
	Position<T> removeLast();

	/**
	 * Removes the next position to the given parameter
	 * @param position previous to the element that will be removed
	 * @return the removed position
	 */
	Position<T> removeNext(Position<T> position);

	/**
	 * Removes the previous position to the given parameter
	 * @param position next to the element that will be removed
	 * @return the removed position
	 */
	Position<T> removePrevious(Position<T> position);

	/**
	 * Removed all the items that match any of the items
	 * @param items group of items to be matched
	 * @return the removed items
	 */
	Iterable<T> removeAll(Iterable<T> items);

	/**
	 * Swap the values of positions locates at indices A and B
	 * @param indexA index of one element to swap
	 * @param indexB index of other element to swap
	 */
	void swap(int indexA, int indexB);

	/**
	 * Check if the given item is stored in the list
	 * @param item to be searched for
	 * @return if the item is stored in the list
	 */
	boolean contains(T item);

	/**
	 * Check if the given items are stored in the list
	 * @param items to be searched for
	 * @return if all the items are stored in the list
	 */
	boolean containsAll(Iterable<T> items);

	/**
	 * Positions of the list in order
	 * @return the positions stored in the list in order of insertion
	 */
	Iterable<Position<T>> positions();

	/**
	 *
	 * @param itemToFind
	 * @param comparator
	 * @return
	 */
	T findItem(T itemToFind, Comparator<T> comparator);

	int findIndex(T itemToFind, Comparator<T> comparator);
}
