package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 26/1/17.
 */
public interface Tree<T> extends Iterable<T> {

	boolean isEmpty();

	Position<T> root();

	Position<T> parent(Position<T> position);

	Iterable<Position<T>> children(Position<T> position);

	int numChildren(Position<T> position);

	boolean isRoot(Position<T> position);

	boolean isLeaf(Position<T> position);

	int size();

	int depth(Position<T> position);

	int height(Position<T> position);

	Iterable<Position<T>> positions();

}
