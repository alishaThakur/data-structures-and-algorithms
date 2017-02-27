package com.pdomingo.data_structures.implementations.queue.abstracts;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Queue;

import java.util.Iterator;

/**
 *
 */
public class AbstractQueue<T> implements Queue<T> {

	protected List<T> queue;

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
		queue.addFirst(item);
	}

	@Override
	public void enqueueAll(Iterable<T> items) {
		for(T item : items)
			enqueue(item);
	}

	@Override
	public T dequeue() {
		return queue.removeLast().getElement();
	}

	@Override
	public T peek() {
		return queue.last().getElement();
	}

	@Override
	public Iterator<T> iterator() {
		return queue.iterator();
	}
}