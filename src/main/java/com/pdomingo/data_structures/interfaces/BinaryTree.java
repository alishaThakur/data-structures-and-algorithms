package com.pdomingo.data_structures.interfaces;

/**
 * Binary specialization for the general n-ary tree
 * Every node must have (0...2] nodes as childs.
 * @param <T> type
 */
public interface BinaryTree<T> extends Tree<T> {

	/**
	 * Get the left child of the element stored by position
	 * @param position the parent position
	 * @return the left child of position or null if there's no such child
	 */
	Position<T> left(Position<T> position);

	/**
	 * Get the right child of the element stored by position
	 * @param position the parent position
	 * @return the tight child of position or null if there's no such child
	 */
	Position<T> right(Position<T> position);

	/**
	 * Get the sibling of the element stored by position
	 * @param position sibling
	 * @return the sibling position or null if there's no such element
	 */
	Position<T> sibling(Position<T> position);
}
