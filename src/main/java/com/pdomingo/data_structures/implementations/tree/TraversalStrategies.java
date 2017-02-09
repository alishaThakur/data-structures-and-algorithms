package com.pdomingo.data_structures.implementations.tree;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.queue.LinkedQueue;
import com.pdomingo.data_structures.interfaces.*;

/**
 * Created by Pablo on 9/2/17.
 */
public class TraversalStrategies {

	public static <T> TraversalStrategy<T> postOrder() {
		return new PostOrderTraversal<>();
	}

	public static <T> TraversalStrategy<T> preOrder() {
		return new PreOrderTraversal<>();
	}

	public static <T> TraversalStrategy<T> breadthFirst() {
		return new BreadthFirstTraversal<>();
	}

	public static <T> TraversalStrategy<T> eulerTour() {
		return new EulerTourTraversal<>();
	}

	public static <T> TraversalStrategy<T> inOrder() {
		return new InOrderTraversal<>();
	}

	/**
	 *
	 * @param <T>
	 */
	private static class PostOrderTraversal<T> implements TraversalStrategy<T> {

		private void postorder(List<Position<T>> list, Position<T> position, Tree<T> tree) {

			for (Position<T> child : tree.children(position))
				postorder(list, child, tree);

			list.add(position);
		}

		@Override
		public Iterable<Position<T>> traverse(Tree<T> tree) {

			List<Position<T>> positions = new ArrayList<>(tree.size());

			if (!tree.isEmpty())
				postorder(positions, tree.root(), tree);

			return positions;
		}
	}

	/**
	 *
	 * @param <T>
	 */
	private static class PreOrderTraversal<T> implements TraversalStrategy<T> {

		private void preorder(List<Position<T>> list, Position<T> position, Tree<T> tree) {

			list.add(position);

			for (Position<T> child : tree.children(position))
				preorder(list, child, tree);
		}

		@Override
		public Iterable<Position<T>> traverse(Tree<T> tree) {

			List<Position<T>> positions = new ArrayList<>(tree.size());

			if (!tree.isEmpty())
				preorder(positions, tree.root(), tree);

			return positions;
		}
	}

	/**
	 *
	 * @param <T>
	 */
	private static class BreadthFirstTraversal<T> implements TraversalStrategy<T> {
		@Override
		public Iterable<Position<T>> traverse(Tree<T> tree) {

			List<Position<T>> positions = new ArrayList<>();
			if(tree.isEmpty())
				return positions;

			Queue<Position<T>> toVisit = new LinkedQueue<>();
			toVisit.enqueue(tree.root());

			while(!toVisit.isEmpty()) {

				Position<T> node = toVisit.dequeue(); // No need to check for null

				positions.add(node);
				toVisit.enqueueAll(tree.children(node));
			}

			return positions;
		}
	}

	/**
	 *
	 * @param <T>
	 */
	private static class EulerTourTraversal<T> implements TraversalStrategy<T> {

		private void eulerTour(List<Position<T>> list, Position<T> position, Tree<T> tree) {

			list.add(position);

			for (Position<T> child : tree.children(position)) {
				eulerTour(list, child, tree);
				list.add(position);
			}
		}

		@Override
		public Iterable<Position<T>> traverse(Tree<T> tree) {

			List<Position<T>> positions = new ArrayList<>(tree.size() * 2);

			if (!tree.isEmpty())
				eulerTour(positions, tree.root(), tree);

			return positions;
		}
	}

	/**
	 *
	 * @param <T>
	 */
	private static class InOrderTraversal<T> implements TraversalStrategy<T> {

		private void inorder(List<Position<T>> list, Position<T> position, BinaryTree<T> btree) {

			if(btree.left(position) != null)
				inorder(list, btree.left(position), btree);

			list.add(position);

			if(btree.right(position) != null)
				inorder(list, btree.right(position), btree);
		}

		@Override
		public Iterable<Position<T>> traverse(Tree<T> tree) {

			if(!(tree instanceof BinaryTree))
				throw new IllegalArgumentException("Tree must be a BinaryTree");

			List<Position<T>> positions = new ArrayList<>(tree.size());

			if (!tree.isEmpty())
				inorder(positions, tree.root(), (BinaryTree<T>) tree);

			return positions;
		}
	};

}
