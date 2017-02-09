package com.pdomingo.data_structures.implementations.queue;

import com.pdomingo.data_structures.implementations.list.LinkedList;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Queue;

import java.util.Iterator;

/**
 * Created by Pablo on 24/1/17.
 */
public class LinkedQueue<T> implements Queue<T> {

	private List<T> queue;

	public LinkedQueue() {
		this.queue = new LinkedList<>();
	}

	public LinkedQueue(Iterable<T> items) {
		this.queue = new LinkedList<>(items);
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public void clear() {
		queue.clear();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public void enqueue(T item) {
		queue.addLast(item);
	}

	@Override
	public void enqueueAll(Iterable<T> items) {
		for(T item : items)
			enqueue(item);
	}

	@Override
	public T dequeue() {
		return queue.removeFirst();
	}

	@Override
	public T peek() {
		return queue.last();
	}

	@Override
	public Iterator<T> iterator() {
		return queue.iterator();
	}
}
