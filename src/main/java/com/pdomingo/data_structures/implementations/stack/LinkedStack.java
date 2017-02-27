package com.pdomingo.data_structures.implementations.stack;

import com.pdomingo.data_structures.implementations.list.LinkedList;
import com.pdomingo.data_structures.implementations.stack.abstracts.AbstractStack;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Stack;

import java.util.Iterator;

/**
 * Created by Pablo on 24/1/17.
 */
public class LinkedStack<T> extends AbstractStack<T> {

	public LinkedStack() {
		this.stack = new LinkedList<>();
	}

	public LinkedStack(Iterable<T> items) {
		this.stack = new LinkedList<>(items);
	}


}
