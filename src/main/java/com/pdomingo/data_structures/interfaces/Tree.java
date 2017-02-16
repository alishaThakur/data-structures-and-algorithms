package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 26/1/17.
 */
public interface Tree<T> extends Iterable<T> {

	/**
	 * Number of elements stored in the tree
	 * @return size of the tree
	 */
	int size();

	/**
	 * Checks if the tree has elements
	 * @return true if the tree is empty
	 */
	boolean isEmpty();

	/**
	 * Empty the tree
	 */
	void clear();

	Position<T> root();

	Position<T> parent(Position<T> position);

	Iterable<Position<T>> children(Position<T> position);

	int numChildren(Position<T> position);

	boolean isRoot(Position<T> position);

	boolean isLeaf(Position<T> position);

	int depth(Position<T> position);

	int height(Position<T> position);

	Iterable<Position<T>> positions();

	Iterable<Position<T>> traverse(TraversalStrategy<T> strategy);
}
