package com.pdomingo.data_structures.implementations.priority_queue;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.priority_queue.abstracts.AbstractPriorityQueue;
import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.List;
import com.pdomingo.data_structures.interfaces.PriorityQueue;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.Comparator;

/**
 *
 * @param <K>
 * @param <V>
 */
public class BinaryHeap<K,V> extends AbstractPriorityQueue<K,V> {

	private List<Entry<K,V>> heap;
	private Order order;

	/*********************************************/

	public BinaryHeap(Comparator<K> comparator) {
		super(comparator);
		this.heap = new ArrayList<>();
		this.order = Order.MAX;
	}

	public BinaryHeap(Order order) {
		super();
		this.heap = new ArrayList<>();
		this.order = order;
	}

	public BinaryHeap() {
		this(Order.MAX);
	}

	public BinaryHeap(Iterable<Entry<K,V>> entries) {

		// TODO
		super();
		this.heap = new ArrayList<>();
		this.order = Order.MAX;
	}

	public enum Order {
		MAX, MIN
	}

	/*********************************************/

	@Override
	public int size() {
		return heap.size();
	}

	@Override
	public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {

		Entry<K,V> entry = PQEntry.of(key,value);

		// Add to deepest left most position
		heap.addLast(entry);

		// Restore heap-order property
		upHeap(entry, size() - 1);

		return entry;
	}

	@Override
	public Entry<K,V> min() {
		return heap.first().getElement();
	}

	@Override
	public Entry<K,V> removeMin() {
		Entry<K,V> min = min();

		if(size() > 2) {
			Entry<K,V> last = heap.removeLast().getElement();
			heap.put(last, 0);

			downHeap(last, 0);
		}

		return min;
	}

	private void downHeap(Entry<K,V> entry, int index) {

		int leftIndex = left(index);

		// Actual entry in index may not exist
		if(isValid(leftIndex)) {

			Entry<K, V> left = heap.get(leftIndex).getElement();

			int cmp = compare(entry, left);

			if (cmp < 0) {
				swap(entry, left);
				// entry has scale down and it's on his left child position
				downHeap(left, leftIndex);
			}
		}

		////////////////////////////////////////////////////

		int rightIndex = right(index);

		if(isValid(rightIndex)) {

			Entry<K,V> right = heap.get(rightIndex).getElement();

			int cmp = compare(entry, right);

			if(cmp < 0) {
				swap(entry, right);
				// entry has scale down and it's on his right child position
				downHeap(right, rightIndex);
			}
		}
	}

	private void upHeap(Entry<K,V> entry, int index) {

		// Heap-order property is implicitly respected
		if(size() < 2)
			return;

		int parentIndex = parent(index);
		Entry<K,V> parent = heap.get(parentIndex).getElement();

		int cmp = compare(entry, parent);

		if(cmp > 0) {
			swap(entry, parent);
			// entry has scale up and it's on his parent's position
			upHeap(parent, parentIndex);
		}
	}

	@Override
	protected int compare(Entry<K, V> a, Entry<K, V> b) {
		// Reverse cmp if heap is min
		return order == Order.MAX ? super.compare(a, b) : -1 * super.compare(a, b);
	}

	@Override
	public String toString() {
		String str = "";
		for (int idx = 0; idx < heap.size(); idx++) {
			str += "[" + idx + "]" + heap.get(idx).getElement() + "\n";
		}
		return str;
	}

	private void swap(Entry<K, V> a, Entry<K, V> b) {

		K key = a.getKey();
		V value = a.getValue();

		a.setKey(b.getKey());
		a.setValue(b.getValue());

		b.setKey(key);
		b.setValue(value);
	}

	private int parent(int index) {
		return (int) Math.ceil((index - 1) / 2);
	}

	private int left(int index) {
		return index * 2 + 1;
	}

	private int right(int index) {
		return index * 2 + 2;
	}

	private boolean isValid(int index) {
		return index <= size() - 1;
	}

	public static void main(String[] args) {

		PriorityQueue<Integer, String> pq = new BinaryHeap<>(Order.MIN);
		pq.insert(1,"A");
		pq.insert(2,"C");
		pq.insert(5,"D");
		pq.insert(10,"E");
		pq.insert(4,"F");
		pq.insert(7,"G");
		pq.insert(2,"H");
		pq.insert(5,"I");
		pq.insert(9,"J");
		pq.insert(11,"K");
		System.out.println(pq.toString());

		pq.removeMin();
		System.out.println(pq.toString());
		pq.removeMin();
		System.out.println(pq.toString());
		pq.removeMin();
		System.out.println(pq.toString());
		pq.removeMin();
		System.out.println(pq.toString());
		pq.removeMin();
		System.out.println(pq.toString());
		pq.removeMin();
		System.out.println(pq.toString());
		pq.removeMin();
		System.out.println(pq.toString());
	}
}
