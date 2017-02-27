package com.pdomingo.data_structures.implementations.queue;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.queue.abstracts.AbstractQueue;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Queue;

import java.util.Iterator;

/**
 * Created by Pablo on 21/12/16.
 */
public class ArrayQueue<T> extends AbstractQueue<T> {

	public ArrayQueue() {
		this.queue = new ArrayList<>();
	}

	public ArrayQueue(Iterable<T> items) {
		this.queue = new ArrayList<>(items);
	}

	public ArrayQueue(int capacity) {
		this.queue = new ArrayList<>(capacity);
	}
}
