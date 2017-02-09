package com.pdomingo.data_structures.interfaces;

/**
 * Created by Pablo on 1/2/17.
 */
public interface TraversalStrategy<T> {
	Iterable<Position<T>> traverse(Tree<T> tree);
}
