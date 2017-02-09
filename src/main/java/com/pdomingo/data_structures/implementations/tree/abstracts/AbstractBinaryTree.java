package com.pdomingo.data_structures.implementations.tree.abstracts;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.interfaces.*;

/**
 * Created by Pablo on 28/1/17.
 */
public abstract class AbstractBinaryTree<T> extends AbstractTree<T> implements BinaryTree<T> {

	@Override
	public Position<T> sibling(Position<T> position) {

		Position<T> parent = parent(position);
		if(parent == null)
			return null;

		// If position is left then return right and viceversa
		if(left(parent) == position)
			return right(parent);
		else
			return left(parent);
	}

	@Override
	public Iterable<Position<T>> children(Position<T> position) {

		List<Position<T>> children = new ArrayList<>(2);

		Position<T> left = left(position);
		Position<T> right = right(position);

		if(left != null)
			children.add(left);
		if(right != null)
			children.add(right);

		return children;
	}

	@Override
	public int numChildren(Position<T> position) {

		int numChildren = 0;

		if(left(position) != null)
			numChildren++;
		if(right(position) != null)
			numChildren++;

		return numChildren;
	}
}
