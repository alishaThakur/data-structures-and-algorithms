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
	public Deque<T> addFirst(T item) {
		deque.addFirst(item);
		return this;
	}

	@Override
	public Deque<T> addLast(T item) {
		deque.addLast(item);
		return this;
	}

	@Override
	public T removeFirst() {
		return deque.removeFirst().getElement();
	}

	@Override
	public T removeLast() {
		return deque.removeLast().getElement();
	}

	@Override
	public T first() {
		return deque.first().getElement();
	}

	@Override
	public T last() {
		return deque.last().getElement();
	}
}
