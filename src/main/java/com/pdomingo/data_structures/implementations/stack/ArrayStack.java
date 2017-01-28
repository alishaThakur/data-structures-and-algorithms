package com.pdomingo.data_structures.implementations.stack;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Stack;

import java.util.Iterator;

/**
 * Created by Pablo on 21/12/16.
 */
public class ArrayStack<T> implements Stack<T> {

	private List<T> stack;

	public ArrayStack() {
		this.stack = new ArrayList<>();
	}

	public ArrayStack(Iterable<T> items) {
		this.stack = new ArrayList<>(items);
	}

	public ArrayStack(int capacity) {
		this.stack = new ArrayList<>(capacity);
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
