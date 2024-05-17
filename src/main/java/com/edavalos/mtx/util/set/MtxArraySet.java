package com.edavalos.mtx.util.set;

import java.util.Iterator;

public class MtxArraySet<T> implements MtxSet<T> {
    protected static final int STARTING_SIZE = 8;

    protected Object[] setContents;
    protected int nextIndex;

    public MtxArraySet(int startingSize) {
        if (startingSize < 0) {
            throw new NegativeArraySizeException("MtxArraySet size cannot be negative");
        }

        this.setContents = new Object[startingSize];
        this.nextIndex = 0;
    }

    public MtxArraySet(T... contents) {
        this.setContents = new Object[contents.length + STARTING_SIZE];
        this.nextIndex = contents.length + 1;
        System.arraycopy(contents, 0, this.setContents, 0, contents.length);
    }

    public MtxArraySet() {
        this(STARTING_SIZE);
    }

    /**
     * @return size/length of this set
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * @return true if this set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * @return true if this set contains the specified element, false otherwise
     */
    @Override
    public boolean contains(T element) {
        return false;
    }

    /**
     * @return an iterator of this set
     */
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    /**
     * @return an array of this set
     */
    @Override
    public T[] toArray() {
        return null;
    }

    /**
     * @return the hashcode of this set
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * @return a string representation of this set
     */
    @Override
    public String toString() {
        return "";
    }

    /**
     * Adds an element to this set. If it already exists in the set, nothing happens.
     *
     * @return true if element was added to the set, false if it was already in it.
     */
    @Override
    public boolean add(T element) {
        // @TODO: Check if element already exists
        this.setContents[this.getNextIndex()] = element;
        return true;
    }

    /**
     * Removes an element from this set
     *
     * @return true if element was found (and removed), false if element was not found.
     */
    @Override
    public boolean remove(T element) {
        return false;
    }

    /**
     * Removes everything from this set
     */
    @Override
    public void clear() {

    }

    protected int getNextIndex() {
        int currentSize = this.setContents.length;
        for (int i = 0; i < currentSize; i++) {
            if (this.setContents[i] == null) {
                return i;
            }
        }
        Object[] newSet = new Object[currentSize + STARTING_SIZE];
        System.arraycopy(this.setContents, 0, newSet, currentSize, currentSize);
        this.setContents = newSet;
        return currentSize;
    }
}
