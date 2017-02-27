package com.pdomingo.data_structures.implementations.stack;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.stack.abstracts.AbstractStack;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Stack;

import java.util.Iterator;

/**
 * Created by Pablo on 21/12/16.
 */
public class ArrayStack<T> extends AbstractStack<T> {

	public ArrayStack() {
		this.stack = new ArrayList<>();
	}

	public ArrayStack(Iterable<T> items) {
		this.stack = new ArrayList<>(items);
	}

	public ArrayStack(int capacity) {
		this.stack = new ArrayList<>(capacity);
	}
}
