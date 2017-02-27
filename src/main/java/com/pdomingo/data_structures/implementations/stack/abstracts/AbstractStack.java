package com.pdomingo.data_structures.implementations.stack.abstracts;

import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Stack;

import java.util.Iterator;

/**
 *
 */
public class AbstractStack<T> implements Stack<T> {

	protected List<T> stack;

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
		return stack.removeLast().getElement();
	}

	@Override
	public T peek() {
		return stack.last().getElement();
	}

	@Override
	public Iterator<T> iterator() {
		return stack.iterator();
	}
}
