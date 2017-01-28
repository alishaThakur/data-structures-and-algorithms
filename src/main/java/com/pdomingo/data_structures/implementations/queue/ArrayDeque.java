package com.pdomingo.data_structures.implementations.queue;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.interfaces.Deque;
import com.pdomingo.data_structures.interfaces.List;

/**
 * Created by Pablo on 22/1/17.
 */
public class ArrayDeque<T> implements Deque<T> {

	private List<T> deque;

	public ArrayDeque() {
		this.deque = new ArrayList<>();
	}

	public ArrayDeque(Iterable<T> items) {
		this.deque = new ArrayList<>(items);
	}

	public ArrayDeque(int capacity) {
		this.deque = new ArrayList<>(capacity);
	}

	@Override
	public int size() {
		return deque.size();
	}

	@Override
	public void clear() {
		deque.clear();
	}

	@Override
	public boolean isEmpty() {
		return deque.isEmpty();
	}

	@Override
	public void addFirst(T item) {
		deque.addFirst(item);
	}

	@Override
	public void addLast(T item) {
		deque.addLast(item);
	}

	@Override
	public T removeFirst() {
		return deque.removeFirst();
	}

	@Override
	public T removeLast() {
		return deque.removeLast();
	}

	@Override
	public T first() {
		return deque.first();
	}

	@Override
	public T last() {
		return deque.last();
	}
}
