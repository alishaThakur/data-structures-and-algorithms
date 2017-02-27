package com.pdomingo.data_structures.implementations.queue;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.queue.abstracts.AbstractQueue;
import com.pdomingo.data_structures.interfaces.Deque;
import com.pdomingo.data_structures.interfaces.List;

/**
 * Created by Pablo on 22/1/17.
 */
public class ArrayDeque<T> extends AbstractQueue<T> implements Deque<T> {

	public ArrayDeque() {
		this.queue = new ArrayList<>();
	}

	public ArrayDeque(Iterable<T> items) {
		this.queue = new ArrayList<>(items);
	}

	public ArrayDeque(int capacity) {
		this.queue = new ArrayList<>(capacity);
	}


	@Override
	public Deque<T> addFirst(T item) {
		queue.addFirst(item);
		return this;
	}

	@Override
	public Deque<T> addLast(T item) {
		queue.addLast(item);
		return this;
	}

	@Override
	public T removeFirst() {
		return queue.removeFirst().getElement();
	}

	@Override
	public T removeLast() {
		return queue.removeLast().getElement();
	}

	@Override
	public T first() {
		return queue.first().getElement();
	}

	@Override
	public T last() {
		return queue.last().getElement();
	}
}
