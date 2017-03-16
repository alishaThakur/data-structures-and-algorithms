package com.pdomingo.data_structures.implementations.misc;

import com.pdomingo.data_structures.implementations.list.LinkedList;
import com.pdomingo.data_structures.interfaces.Position;
import java.util.Random;

/**
 *
 * @param <T> type of the elements stored
 */
public class SkipList<T extends Comparable<T>> {

	private LinkedList<Tower<T>> skipList;
	private final Random rand = new Random(System.currentTimeMillis());

	/*--------------------- Constructors ---------------------*/

	public SkipList() {
		skipList = new LinkedList<>();
	}

	/*--------------------- Nested Classes ---------------------*/

	private static class Tower<T> {

		private T item;
		private NodeLevel<T>[] levelReferences;

		public Tower(int level, T item) {
			this.item = item;
			this.levelReferences = (NodeLevel<T>[]) new NodeLevel[level+1];
		}

		private static class NodeLevel<T> {
			private Tower<T> previous;
			private Tower<T> next;
		}

		public int height() {
			return levelReferences.length - 1;
		}

		private void setupReferences(Tower<T> prev, Tower<T> pos) {

			// A number of references equal to the height of the tower must be set
			int height = height();

			// Pre




		}
	}



    /*--------------------- Public Methods ---------------------*/

    public void add(T item) {

	    Position<Tower<T>> previousTower = findItem(item);
	    Position<Tower<T>> nextTower = skipList.next(previousTower);

	    Tower<T> tower = new Tower<>(rand.nextInt(), item);
	    tower.setupReferences(previousTower.getElement(), nextTower.getElement());

	    skipList.addAfter(previousTower, tower);
    }

	private Position<Tower<T>> findItem(T item) {

		Position<Tower<T>> pos = skipList.first();
		int currentHeight = pos.getElement().height();
		T itemToCompare = pos.getElement().item;

		while(currentHeight > 0) {

			currentHeight -= 1;

			while(item.compareTo(itemToCompare) >= 0) {
				pos = skipList.next(pos);
				itemToCompare = pos.getElement().item;
			}
		}

		return pos;
	}

	private T delete(T item) {
		return null;
	}

    /*--------------------- Private Methods ---------------------*/

	public static void main(String[] args) {

		SkipList<Integer> skipList = new SkipList<>();
	}
}
