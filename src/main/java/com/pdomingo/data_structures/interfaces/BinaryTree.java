package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 28/1/17.
 */
public interface BinaryTree<T> extends Tree<T> {

	Position<T> left(Position<T> position);

	Position<T> right(Position<T> position);

	Position<T> sibling(Position<T> position);
}
