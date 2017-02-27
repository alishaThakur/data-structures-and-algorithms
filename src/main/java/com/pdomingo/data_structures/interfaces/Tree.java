package com.pdomingo.data_structures.interfaces;

/**
 *
 * @param <T>
 */
public interface Tree<T> extends Collection<T> {

	/**
	 * Get the root position of the tree
	 * @return the root position
	 */
	Position<T> root();

	/**
	 * Get the parent position for the given position
	 * @param position child of the parent
	 * @return the parent of position
	 */
	Position<T> parent(Position<T> position);

	/**
	 * Get the list of children positions for the given position
	 * @param position parent of the children
	 * @return children positions of position
	 */
	Iterable<Position<T>> children(Position<T> position);

	/**
	 * Number of childs for the given position
	 * @param position
	 * @return
	 */
	int numChildren(Position<T> position);

	/**
	 * Check if the given position if the root of the tree
	 * @param position
	 * @return if position is the root
	 */
	boolean isRoot(Position<T> position);

	/**
	 * Check if the given position if a leaf of the tree
	 * @param position
	 * @return if position is a leaf of the tree
	 */
	boolean isLeaf(Position<T> position);

	/**
	 * The depth starting from 0 at the root node for
	 * the given position
	 * @param position
	 * @return depth of the position
	 */
	int depth(Position<T> position);

	/**
	 * Height of the given position
	 * @param position
	 * @return the height of position
	 */
	int height(Position<T> position);

	/**
	 * List of position of the tree in breadth first order traversal
	 * @return list of positions in order
	 */
	Iterable<Position<T>> positions();

	/**
	 * Traverse this tree using the given strategy
	 * @param strategy to se for traversing
	 * @return list of positions in order
	 */
	Iterable<Position<T>> traverse(TraversalStrategy<T> strategy);
}
