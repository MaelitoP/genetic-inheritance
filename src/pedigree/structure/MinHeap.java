package pedigree.structure;

import java.util.Arrays;

/**
 * Generic class to implement the min heap priority queue.
 *
 * @param <T> Parametric type. Will be either Sim, PA, or Event class
 *
 * @author Maël LE PETIT
 */
public class MinHeap<T extends Comparable<? super T>> {
    private int size;
    private int maxSize;
    private T[] array;
    private int childNbr;

    /**
     * Constructors
     */
    public MinHeap(int maxSize, int childNbr) {
        this.maxSize = maxSize;
        this.array = (T[]) new Comparable[this.maxSize];
        this.size = 0;
        this.childNbr = childNbr;
    }

    /**
     * Getters
     */
    public int size() {
        return size;
    }

    public T[] getArray() {
        return array;
    }

    public T getElement(int i) {
        return array[i];
    }

    /**
     * Checks if the array is empty
     *
     * @return true if empty, else false
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    public T peek() {
        return array[0];
    }

    /**
     * Expands or reduces the array by a multiple
     *
     * @param multiple expansion/contraction factor
     */
    private void reSize(double multiple) {
        T[] newArray = (T[]) new Comparable[(int) (array.length * multiple)];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
    }

    /**
     * Changes the position of 2 items in the array
     *
     * @param child index of child
     * @param parent index of parent
     */
    public void swap(int child, int parent) {
        T temp = array[child];

        array[child] = array[parent];
        array[parent] = temp;
    }

    /**
     * Sets the appropriate result when comparing 2 values.
     *
     * If max Heap, a successful test finds a new max. If min Heap, a successful
     * test finds a new min.
     *
     * @param first index of first value
     * @param second index of second value
     */
    public boolean higher(int first, int second) {
        return array[first].compareTo(array[second]) > 0;
    }

    /**
     * Returns the smallest child
     *
     * @param parent index of the first child
     * @return index of smallest child
     */
    public int smallestChild(int parent) {

        if (parent * childNbr + 1 >= size) {
            return 0;
        }

        // Search through all the children for the target (min or max) value
        int firstChild = parent * childNbr + 1;
        int targetChild = firstChild;

        for (int j = 1; j < childNbr; j++) {
            if (firstChild + j >= size) {
                break;
            }
            // If found a new min/max
            if (higher(targetChild, firstChild + j)) {
                targetChild = firstChild + j;
            }
        }

        return targetChild;
    }

    /**
     * Percolates up the tree to put items in order
     *
     * @param i starting index (usually at bottom)
     */
    public void swim(int i) {
        int parent;

        while (i > 0) {
            parent = (i - 1) / childNbr;
            // If values are not in order
            if (higher(parent,i)) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    /**
     * Percolates down the tree to put items in order
     *
     * @param i starting index (usually at the top)
     */
    public void sink(int i) {
        int child;

        while (i < size) {
            child = smallestChild(i);

            // If a child exists and values are not in order
            if (child != 0 && (higher(i, child))) {
                swap(child, i);
                i = child;
            } else {
                break;
            }
        }
    }

    /**
     * Adds a new item in the structure
     *
     * @param item the item to add
     */
    public void insert(T item) {
        if (isEmpty()) {
            array[0] = item;
            size++;
            return;
        }
        // Check to see if the array can hold another element
        // If not, expand the array
        if (size == array.length) {
            reSize(2);
        }
        array[size] = item;
        swim(size++);
    }

    /**
     * Deletes the root item
     *
     * @return the deleted item
     */
    public T delete() {
        if (size == 0)
            throw new java.lang.IllegalStateException("Empty Heap");

        T root = array[0];

        swap(0, size - 1);
        array[size - 1] = null;
        --size;
        sink(0);

        // Check if need to reduce array
        if (size < array.length / 3) this.reSize(0.5);
        return root;
    }

    /**
     * Creates a heap from an array
     *
     * @param array array storing data
     */
    public void heapify(T[] array) {
        this.array = array;
        this.size = array.length;
        for (int i = this.size/2; i >=0; i--) {
            this.sink(i);
        }
    }

    // For testing
    @Override
    public String toString() {
        return Arrays.toString(this.array);
    }
}
