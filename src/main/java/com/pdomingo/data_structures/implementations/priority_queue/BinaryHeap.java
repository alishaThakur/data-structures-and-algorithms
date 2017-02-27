package com.pdomingo.data_structures.implementations.priority_queue;

import com.pdomingo.data_structures.implementations.list.ArrayList;
import com.pdomingo.data_structures.implementations.priority_queue.abstracts.AbstractPriorityQueue;
import com.pdomingo.data_structures.interfaces.Entry;
import com.pdomingo.data_structures.interfaces.List;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @param <K>
 * @param <V>
 *
 * <h4>Complexity summary</h4>
 * <table>
 *      <thead>
 *          <td>Method</td><td>Worst Case Complexity</td>
 *      </thead>
 *      <tr><td>{@link BinaryHeap#size()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link BinaryHeap#isEmpty()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link BinaryHeap#clear()}</td><td>O(n)</td></tr>
 *      <tr><td>{@link BinaryHeap#insert(Object, Object)}</td><td>O(log<sub>2</sub>n)</td></tr>
 *      <tr><td>{@link BinaryHeap#first()}</td><td>O(1)</td></tr>
 *      <tr><td>{@link BinaryHeap#removeFirst()}</td><td>O(log<sub>2</sub>n)</td></tr>
 * </table>
 */
public class BinaryHeap<K,V> extends AbstractPriorityQueue<K,V> {

	protected List<Entry<K,V>> heap;

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
		this.heap = new ArrayList<>(entries);

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

	protected void downHeap(Entry<K,V> entry, int index) {

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
				//swap(entry, left);
				swap(index, leftIndex);

				// entry has scale down and it's on his left child position
				downHeap(entry, leftIndex);
			}
		}

		////////////////////////////////////////////////////

		int rightIndex = right(index);

		if(isValid(rightIndex)) {

			Entry<K,V> right = heap.get(rightIndex).getElement();

			int cmp = compare(entry, right);

			if(cmp < 0) {
				//swap(entry, right);
				swap(index, rightIndex);

				// entry has scale down and it's on his right child position
				downHeap(entry, rightIndex);
			}
		}
	}

	protected void upHeap(Entry<K,V> entry, int index) {

		// Heap-order property is implicitly respected
		if(size() < 2)
			return;

		int parentIndex = parent(index);
		Entry<K,V> parent = heap.get(parentIndex).getElement();

		int cmp = compare(entry, parent);

		if(cmp > 0) {
			//swap(entry, parent);
			swap(index, parentIndex);

			// entry has scale up and it's on his parent's position
			upHeap(entry, parentIndex);
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

	protected void swap(int indexA, int indexB) {
		if(isValid(indexA) && isValid(indexB))
			swap(indexA, indexB);
	}

	protected static int parent(int index) {
		return (int) Math.ceil((index - 1) / 2);
	}

	protected static int left(int index) {
		return index * 2 + 1;
	}

	protected static int right(int index) {
		return index * 2 + 2;
	}

	protected boolean isValid(int index) {
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
	protected static int depth(int index) {
		return (int) Math.abs(Math.log(index + 1) / log2);
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return heap.iterator();
	}
}
