package com.pdomingo.data_structures.implementations.priority_queue;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.priority_queue.abstracts.AbstractPriorityQueue;
import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Comparator;

/**
 *
 * @param <K>
 * @param <V>
 */
public class BinaryHeap<K,V> extends AbstractPriorityQueue<K,V> {

	private List<Entry<K,V>> heap;

	/*********************************************/

	public BinaryHeap(Comparator<K> comparator, Order order) {
		super(comparator, order);
		this.heap = new ArrayList<>();
	}

	public BinaryHeap(Order order) {
		super(order);
		this.heap = new ArrayList<>();
	}

	public BinaryHeap() {
		this(Order.MAX);
	}

	public BinaryHeap(List<Entry<K,V>> entries) {

		this();

		for (int idx = heap.size() - 1; idx > 0 ; idx--) {
			int parent = parent(idx);
			downHeap(heap.get(parent).getElement(), parent);
		}
	}

	/*********************************************/

	@Override
	public int size() {
		return heap.size();
	}

	/**
	 * Empty the priority queue
	 */
	@Override
	public void clear() {
		for (Entry<K,V> entry : heap) {
			entry.setKey(null);
			entry.setValue(null);
		}

		heap.clear();
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
	public Entry<K,V> first() {
		return heap.first().getElement();
	}

	@Override
	public Entry<K,V> removeFirst() {
		Entry<K,V> first = first();

		if(size() > 2) {
			Entry<K,V> last = heap.removeLast().getElement();
			heap.put(last, 0);

			downHeap(last, 0);
		}

		return first;
	}

	private void downHeap(Entry<K,V> entry, int index) {

		/* Notas:
		 * - left y right sirven para calcular la posición que ocuparía
		 * el node en un arbol completo pero eso no implica que exista,
		 * de ahí el metodo isValid
		 *
		 * - Al realizar swap con el mismo parametro de entrada, permite
		 * mantener el orden completo dentro del arbol ya que primero se
		 * hara swap y down-heap recursivamente sobre el hijo izquierdo y
		 * despues con el derecho por lo que si al hacer swap en una ocasion
		 * el nodo que sube no cumple con la regla de orden del heap, esta
		 * regla se restaurará en la siguiente pasada por el lado izquierdo
		 */


		int leftIndex = left(index);

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
	public String toString() {

		String str = "";
		for (int idx = 0; idx < heap.size(); idx++) {
			str += new String(new char[depth(idx)]).replace('\0', '\t');
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

	private static int parent(int index) {
		return (int) Math.ceil((index - 1) / 2);
	}

	private static int left(int index) {
		return index * 2 + 1;
	}

	private static int right(int index) {
		return index * 2 + 2;
	}

	private boolean isValid(int index) {
		return index <= size() - 1;
	}

	/**
	 * Depth of the node expected to be at the given index
	 * As a heap is a complete binary tree, the the of an index
	 * can be calculated as if that index would be the size - 1
	 * of the tree
	 * @param index
	 * @return
	 */
	private static int depth(int index) {
		return (int) Math.abs(Math.log(index + 1) / log2);
	}
}
