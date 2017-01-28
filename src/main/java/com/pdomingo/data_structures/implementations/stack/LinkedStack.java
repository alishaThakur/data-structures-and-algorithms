package com.pdomingo.data_structures.implementations.stack;

import com.pdomingo.data_structures.implementations.list.LinkedList;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Stack;

import java.util.Iterator;

/**
 * Created by Pablo on 24/1/17.
 */
public class LinkedStack<T> implements Stack<T> {

	private List<T> stack;

	public LinkedStack() {
		this.stack = new LinkedList<>();
	}

	public LinkedStack(Iterable<T> items) {
		this.stack = new LinkedList<>(items);
	}

	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public void clear() {
		stack.clear();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public void push(T item) {
		stack.addLast(item);
	}

	@Override
	public T pop() {
		return stack.removeLast();
	}

	@Override
	public T peek() {
		return stack.last();
	}

	@Override
	public Iterator<T> iterator() {
		return stack.iterator();
	}
}
