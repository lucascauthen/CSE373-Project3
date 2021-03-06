package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */

// An 0-indexing array min heap where each node has up to four child nodes.

public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
	private static final int NUM_CHILDREN = 4;
	private static final int DEFAULT_SIZE = 5;
	private T[] heap;
	private int heapSize;

	public ArrayHeap() {
		heap = makeArrayOfT(DEFAULT_SIZE);
		heapSize = 0;
	}

	/**
	 * This method will return a new, empty array of the given size that can contain
	 * elements of type T.
	 *
	 * Note that each element in the array will initially be null.
	 */
	@SuppressWarnings("unchecked")
	private T[] makeArrayOfT(int size) {
		// This helper method is basically the same one we gave you
		// in ArrayDictionary and ChainedHashDictionary.
		//
		// As before, you do not need to understand how this method
		// works, and should not modify it in any way.
		return (T[]) (new Comparable[size]);
	}

	@Override
	// remove and return the smallest element in the heap. If the whole
	// if heap is empty, throw EmptyContainerException.
	public T removeMin() {
		if (this.heapSize < 1) {
			throw new EmptyContainerException();
		}
		T result = this.peekMin();
		heap[0] = heap[this.heapSize - 1];
		heap[this.heapSize - 1] = null;
		removeMinHelper(0);
		this.heapSize--;
		return result;
	}

	// Pass an integer position as an parameter.
	// Help RemoveMin() to sort the heap after removing smallest element.
	// It will compare the element in given position to its child elements
	// and swap position if the child is smaller than the given element and
	// is the smallest element among all children.
	private void removeMinHelper(int position) {
		T min = null;
		int order = 0;
		for (int i = 1; i <= NUM_CHILDREN; i++) {
			// If child is valid and less than parent
			if (position * NUM_CHILDREN + i < heap.length && heap[position * NUM_CHILDREN + i] != null
					&& heap[position * NUM_CHILDREN + i].compareTo(heap[position]) < 0) {
				// If we have found a child smaller than the parent or if we found an even
				// smaller child
				if (min == null || min != null 
						&& heap[position * NUM_CHILDREN + i].compareTo(min) < 0) {
					min = heap[position * NUM_CHILDREN + i];
					order = i;
				}
			}
		}
		if (min != null) {
			heap[position * NUM_CHILDREN + order] = heap[position];
			heap[position] = min;
			removeMinHelper(position * NUM_CHILDREN + order);
		}
	}

	// return the smallest elements in the heap
	// if heap is empty, throw EmptyContainerException.
	@Override
	public T peekMin() {
		if (this.heapSize < 1) {
			throw new EmptyContainerException();
		}
		return heap[0];
	}

	// Pass an object T as a parameter. Throw IllegalArgumentException if
	// T is null. It will insert given item to heap based on sorting order.
	@Override
	public void insert(T item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if (heap.length == heapSize) {
			T[] newHeap = makeArrayOfT(this.heapSize * 2);
			for (int i = 0; i < heapSize; i++) {
				newHeap[i] = heap[i];
			}
			heap = newHeap;
		}
		heap[heapSize] = item;
		insertionHelper(heapSize);
		heapSize++;
	}

	// Pass an integer position as an parameter. Help insert() to determine
	// the correct order of element with given position. It compare the given
	// given element with its parent element. If the its parent element is larger
	// than the element, swap their positions.
	private void insertionHelper(int position) {
		// If parent is larger than child
		if (heap[(position - 1) / NUM_CHILDREN].compareTo(heap[position]) > 0) { 
			T item = heap[(position - 1) / NUM_CHILDREN];
			heap[(position - 1) / NUM_CHILDREN] = heap[position];
			heap[position] = item;
			insertionHelper((position - 1) / NUM_CHILDREN);
		}
	}

	// return the size of array Heap
	@Override
	public int size() {
		return this.heapSize;
	}
}
