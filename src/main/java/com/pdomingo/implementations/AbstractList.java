package com.pdomingo.implementations;

import com.pdomingo.exceptions.IndexOutOfBoundsException;
import com.pdomingo.interfaces.List;

import java.util.Iterator;

/**
 * Created by Pablo on 21/12/16.
 */
public abstract class AbstractList<T> implements List<T> {

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public List<T> addAll(Iterable<T> items) {
		for(T item : items)
			add(item);

		return this;
	}

	@Override
	public Iterable<T> removeAll(Iterable<T> items) {
		for(T item : items)
			remove(item);

		return this;
	}

	/**
	 *
	 * @return
	 */
	public Iterator<T> iterator() {
		return new AbstractListIterator();
	}

	/**
	 *
	 */
	protected class AbstractListIterator implements Iterator<T> {

		private int index = 0;

		public boolean hasNext() {
			return index < size();
		}

		public T next() {
			index++;
			return get(index);
		}

		public void remove() {
			AbstractList.this.remove(index);
		}
	}

	protected void checkRange(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException("Invalid index");
	}
}
