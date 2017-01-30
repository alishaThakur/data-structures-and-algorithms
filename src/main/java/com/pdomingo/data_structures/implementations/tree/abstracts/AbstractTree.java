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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return treeStructure(sb, root()).toString();
	}

	private StringBuilder treeStructure(StringBuilder sb, Position<T> node) {

		if(node == null)
			return sb;

		int depth = depth(node);
		if(depth == 0)
			sb.append(node.getElement()).append("\n");
		else {

			String symbol = isLeaf(node) ? "└── " : "├── ";

			for (int idx = 0; idx < depth; idx++)
				sb.append("|\t");
			sb.append("\n");
			for (int idx = 0; idx < depth - 1; idx++)
				sb.append("|\t");

			sb.append(symbol).append(node.getElement()).append("\n");
		}

		for(Position<T> child : children(node))
			treeStructure(sb, child);

		return sb;
	}
}
