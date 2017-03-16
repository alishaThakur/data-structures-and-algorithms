package com.pdomingo.data_structures.implementations.set.abstracts;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.Set;

/**
 *
 */
public abstract class AbstractSet<T> implements Set<T> {

    /**
	 * Perform the mathematical union of both sets
	 * That is:
	 * A ∪ B = { x : x ∈ A or x ∈ B }
	 *
	 * @param set
	 * @return
	 */
	@Override
	public Set<T> union(Set<T> set) {

		for(T item : set)
			this.add(item);

		return this;
	}

	/**
	 * Perform the mathematical intersection of both sets
	 * That is:
	 * A ∩ B = { x : x ∈ A and x ∈ B }
	 *
	 * @param set
	 * @return
	 */
	@Override
	public Set<T> intersection(Set<T> set) {

		List<T> list = new ArrayList<>();
		for(T item : set)
			if(this.contains(item))
				list.add(item);

		this.clear();
		this.addAll(list);

		return this;
	}

	/**
	 * Perform the mathematical intersection of both sets
	 * That is:
	 * A - B = { x : x ∈ A and x not ∈ B }
	 *
	 * @param set
	 * @return
	 */
	@Override
	public Set<T> difference(Set<T> set) {
		for(T item : set)
			this.remove(item);

		return this;
	}

	/**
	 * Checks if the collection has elements
	 *
	 * @return true if the collection is empty
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Add all the element of the iterable into the set
	 * @param items
	 */
	@Override
	public void addAll(Iterable<T> items) {
		for (T item : items)
			add(item);
	}
}
