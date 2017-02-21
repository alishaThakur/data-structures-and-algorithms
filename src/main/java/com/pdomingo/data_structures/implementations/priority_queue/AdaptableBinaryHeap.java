package com.pdomingo.data_structures.implementations.priority_queue;

import com.pdomingo.data_structures.interfaces.AdaptablePriorityQueue;
import com.pdomingo.data_structures.interfaces.Entry;

/**
 * Priority Queue designed to...
 *
 * @param <K> key
 * @param <V> value
 *
 * <h4>Complexity summary</h4>
 * <table>
 *      <thead>
 *          <td>Method</td><td>Worst Case Complexity</td>
 *      </thead>
 *      <tr><td>{@link AdaptableBinaryHeap#size()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#isEmpty()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#clear()}</td><td>O(n)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#insert(Object, Object)}</td><td>O(log<sub>2</sub>n)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#first()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#removeFirst()}</td><td>O(log<sub>2</sub>n)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#remove(Entry)}</td><td>O(log<sub>2</sub>n)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#replaceKey(Entry, Object)}</td><td>O(log<sub>2</sub>n)</td></tr>
 *      <tr><td>{@link AdaptableBinaryHeap#replaceValue(Entry, Object)}</td><td>O(1)</td></tr>
 * </table>
 */
public class AdaptableBinaryHeap<K,V>
		extends BinaryHeap<K,V>
		implements AdaptablePriorityQueue<K,V> {

	/**
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	protected static class LocationAwarePQEntry<K,V> extends PQEntry<K,V> {

		private int index;

		private LocationAwarePQEntry(K key, V value, int index) {
			super(key, value);
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		@Override
		public String toString() {
			return "LAEntry{" + this.getKey() + ", "+ this.getValue() + ", " + index +" }";
		}
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {

		Entry<K,V> entry = new LocationAwarePQEntry<>(key, value, heap.size());

		// Add to deepest left most position
		heap.addLast(entry);

		// Restore heap-order property
		upHeap(entry, size() - 1);

		return entry;
	}

	@Override
	public void replaceKey(Entry<K, V> entry, K key) {

		checkKey(key);

		// In order to respect PriorityQueue Order attribute
		// we use compare method instead of comparator
		int cmp = compare(PQEntry.of(key, null), entry);

		// Check if entry belongs to the heap
		LocationAwarePQEntry<K,V> locationAwareEntry = LAentry(entry);
		locationAwareEntry.setKey(key);

		if(cmp > 0)
			super.upHeap(locationAwareEntry, locationAwareEntry.getIndex());
		else
			super.downHeap(locationAwareEntry, locationAwareEntry.getIndex());
	}

	@Override
	public void replaceValue(Entry<K, V> entry, V value) {
		// Check if entry belongs to the heap
		LocationAwarePQEntry<K,V> locationAwareEntry = LAentry(entry);
		locationAwareEntry.setValue(value);
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> entry) {

		LocationAwarePQEntry<K,V> locationAwareEntry = LAentry(entry);
		int entryIndex = locationAwareEntry.index;

		// Last entry has easy removal. No need to restore ordering
		if(entryIndex == heap.size() - 1)
			return heap.removeLast().getElement();

		swap(entryIndex, heap.size() - 1);
		// Now the element to be removed is the last and we
		// avoid an array shift relocation
		Entry<K,V> entryToRemove = heap.removeLast().getElement();

		super.downHeap(heap.get(entryIndex).getElement(), entryIndex);

		return entryToRemove;
	}


	@Override
	protected void swap(int indexA, int indexB) {

		LocationAwarePQEntry<K,V> aEntry = LAentry(heap.get(indexA).getElement());
		LocationAwarePQEntry<K,V> bEntry = LAentry(heap.get(indexB).getElement());

		// swap indices
		bEntry.setIndex(indexA);
		aEntry.setIndex(indexB);

		// swar PQEntry values
		super.swap(aEntry.index, bEntry.index);
	}


	private LocationAwarePQEntry<K,V> LAentry(Entry<K,V> entry) {

		if( ! (entry instanceof LocationAwarePQEntry))
			throw new IllegalArgumentException("Invalid entry");

		// Safe cast
		LocationAwarePQEntry<K,V> locationAwarePQEntry = (LocationAwarePQEntry<K,V>) entry;

		if(locationAwarePQEntry.index < 0)
			throw new IllegalArgumentException("Entry is no longer in the priority queue");

		return locationAwarePQEntry;
	}
}
