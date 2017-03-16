package com.pdomingo.data_structures.implementations.tree;

import com.pdomingo.data_structures.implementations.common.Collections;
import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.tree.abstracts.AbstractTree;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Position;
import com.pdomingo.data_structures.interfaces.TraversalStrategy;
import com.pdomingo.data_structures.interfaces.Tree;

/**
 *
 */
public class LinkedTree<T> extends AbstractTree<T> {

	/*---------------------- Attributes ----------------------*/

	private NAryNode<T> root;
	private int size;

	/*---------------------- Nested Classes ----------------------*/

	/**
	 *
	 * @param <T>
	 */
	private static class NAryNode<T> implements Position<T> {

		private NAryNode<T> parent;
		private List<NAryNode<T>> childs;

		private T element;

		private NAryNode(NAryNode<T> parent, List<NAryNode<T>> childs, T element) {
			this.parent = parent;
			this.childs = childs;
			this.element = element;
		}

		@Override
		public T getElement() {
			return element;
		}

		private boolean hasChilds() {
			return childs != null && childs.size() > 0;
		}

		private void addChild(NAryNode<T> node) {
			if(childs == null)
				childs = new ArrayList<>(node);
			else
				childs.add(node);
		}

		private void removeChild(NAryNode<T> node) {
			childs.removeByItem(node);
		}

		private T delete() {

			if(parent != null)
				parent.removeChild(this);

			parent = null;
			childs.clear();
			childs = null;
			T element = this.element;
			this.element = null;
			return element;
		}
	}

	/*--------------------- Constructors ---------------------*/

	public LinkedTree() {
		this.root = null;
		this.size = 0;
	}

	private LinkedTree<T> asSubTree(Position<T> root) {
		NAryNode<T> nroot = treeNode(root);
		return new LinkedTree<>(nroot);
	}

	private LinkedTree(NAryNode<T> root) {
		this.root = root;
		this.size = subtreeSize(root);
	}

	private int subtreeSize(NAryNode<T> root) {

		int size = 0;

		if(root.hasChilds())
			for(NAryNode<T> node : root.childs)
				size += subtreeSize(node);

		size += numChildren(root);

		return size;
	}

	/*--------------------- Public Methods ---------------------*/

	@Override
	public Position<T> root() {
		return root;
	}

	@Override
	public Position<T> parent(Position<T> position) {
		return treeNode(position).parent;
	}

	/**
	 * Get the list of children positions for the given position
	 *
	 * @param position parent of the children
	 * @return children positions of position
	 */
	@Override
	public Iterable<Position<T>> children(Position<T> position) {
		NAryNode<T> node = treeNode(position);

		if(node.hasChilds()) {
			List<Position<T>> childs = new ArrayList<>(node.childs.size());
			for(Position<T> pos : node.childs)
				childs.add(pos);

			return childs; // fucking type erasure and covariance!
		}

		return Collections.emptyIterable();
	}

	/**
	 * Number of childs for the given position
	 *
	 * @param position
	 * @return
	 */
	@Override
	public int numChildren(Position<T> position) {
		NAryNode<T> node = treeNode(position);
		return node.hasChilds() ? node.childs.size() : 0;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Empty the tree
	 */
	@Override
	public void clear() {
		for(Position<T> position : positions())
			treeNode(position).delete();

		size = 0;
	}

	@Override
	public int depth(Position<T> position) {

		NAryNode<T> node = treeNode(position);

		if(node == root)
			return 0;
		else
			return 1 + depth(node.parent);
	}

	public Position<T> addRoot(T element) {
		if(!isEmpty())
			throw new IllegalStateException("Tree is not empty");

		root = new NAryNode<>(null, null, element);
		size++;

		return root;
	}

	public T set(Position<T> position, T element) {

		NAryNode<T> node = treeNode(position);
		T prevElement = node.element;
		node.element = element;

		return prevElement;
	}

	public void attach(Position<T> position, Tree<T> tree) {

		if( ! isLeaf(position))
			throw new IllegalStateException("Position is not a leaf");

		NAryNode<T> parent = treeNode(position);

		if(!tree.isEmpty()) {
			NAryNode<T> treeRoot = treeNode(tree.root());

			parent.childs.add(treeRoot);
			treeRoot.parent = parent;
		}

		size += tree.size();
	}

	public void prune(Position<T> position) {

		/* Notes: After deleting recursively the left subtree,
		 * delete the 'root' node of the subree */

		if(position == null)
			return;

		NAryNode<T> node = treeNode(position);

		if(node.hasChilds()) {
			// Prune child sutree
			for (NAryNode<T> child : node.childs) {
				prune(node);
				child.delete();
				size--;
			}
		}
	}

	@Override
	public Iterable<Position<T>> traverse(TraversalStrategy<T> strategy) {
		return strategy.traverse(this);
	}

	/**
	 *
	 * @param position
	 * @return node that corresponds to given position
	 */
	private NAryNode<T> treeNode(Position<T> position) {

		if( ! (position instanceof NAryNode))
			throw new IllegalArgumentException("Invalid position");

		// Safe cast
		NAryNode<T> node = (NAryNode<T>) position;

		if(node.parent == node)
			throw new IllegalArgumentException("Position is no longer in the tree");

		return node;
	}
}
