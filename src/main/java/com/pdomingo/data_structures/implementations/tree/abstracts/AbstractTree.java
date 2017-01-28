package com.pdomingo.data_structures.implementations.tree.abstracts;

import com.pdomingo.data_structures.interfaces.Position;
import com.pdomingo.data_structures.interfaces.Tree;

/**
 * Created by Pablo on 26/1/17.
 */
public abstract class AbstractTree<T> implements Tree<T> {

	@Override
	public boolean isRoot(Position<T> position) {
		return position == root();
	}

	@Override
	public boolean isLeaf(Position<T> position) {
		return numChildren(position) == 0;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int height(Position<T> position) {

		int height = 0;
		for(Position<T> node : children(position))
			height = Math.max(height, 1 + height(node));

		return height;
	}
}
