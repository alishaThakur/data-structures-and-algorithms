package com.pdomingo.data_structures.implementations.queue;

import com.pdomingo.data_structures.implementations.list.LinkedList;
import com.pdomingo.data_structures.implementations.queue.abstracts.AbstractQueue;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Queue;

import java.util.Iterator;

/**
 * Created by Pablo on 24/1/17.
 */
public class LinkedQueue<T> extends AbstractQueue<T> {

	public LinkedQueue() {
		this.queue = new LinkedList<>();
	}

	public LinkedQueue(Iterable<T> items) {
		this.queue = new LinkedList<>(items);
	}
}
