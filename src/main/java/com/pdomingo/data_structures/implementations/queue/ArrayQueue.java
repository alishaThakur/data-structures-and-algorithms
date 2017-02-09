package com.pdomingo.data_structures.implementations.queue;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Queue;

import java.util.Iterator;

/**
 * Created by Pablo on 21/12/16.
 */
public class ArrayQueue<T> implements Queue<T> {

	public List<T> queue;

	public ArrayQueue() {
		this.queue = new ArrayList<>();
	}

	public ArrayQueue(Iterable<T> items) {
		this.queue = new ArrayList<>(items);
	}

	public ArrayQueue(int capacity) {
		this.queue = new ArrayList<>(capacity);
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
		queue.addFirst(item);
	}

	@Override
	public void enqueueAll(Iterable<T> items) {
		for(T item : items)
			enqueue(item);
	}

	@Override
	public T dequeue() {
		return queue.removeLast();
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
