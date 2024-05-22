package com.edavalos.mtx.util.set;

import java.util.Arrays;
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

    @SafeVarargs
    public MtxArraySet(T... contents) {
        this.setContents = new Object[contents.length + STARTING_SIZE];
        this.nextIndex = contents.length;
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
        return this.nextIndex;
    }

    /**
     * @return true if this set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.nextIndex == 0;
    }

    /**
     * @return true if this set contains the specified element (or if this element is null), false otherwise
     */
    @Override
    public boolean contains(T element) {
        if (element == null) {
            return true;
        }

        for (Object setContent : this.setContents) {
            if (element.equals(setContent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return an iterator of this set
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return this.idx < size();
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                return (T) setContents[this.idx++];
            }
        };
    }

    /**
     * @return an array of this set
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        Object[] arr = new Object[this.nextIndex];
        System.arraycopy(this.setContents, 0, arr, 0, this.nextIndex);
        return (T[]) arr;
    }

    /**
     * @return the hashcode of this set
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Object item : this.setContents) {
            int elementHashCode = item == null ? 0 : item.hashCode();
            hashCode += elementHashCode;
        }
        return hashCode;
    }

    /**
     * @return a string representation of this set
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (Object item : this.setContents) {
            if (item == null) {
                continue;
            }

            sb.append(item).append(", ");
        }
        sb.append(")");
        return sb.toString().replace(", )", ")");
    }

    /**
     * Adds an element to this set. If it already exists in the set, nothing happens.
     *
     * @return true if element was added to the set, false if it was already in it.
     */
    @Override
    public boolean add(T element) {
        // @TODO: Check if element already exists
        if (this.nextIndex >= this.setContents.length) {
            Object[] setContentsCopy = new Object[this.setContents.length + STARTING_SIZE];
            System.arraycopy(this.setContents, 0, setContentsCopy, 0, this.setContents.length);
            this.setContents = setContentsCopy;
        }
        this.setContents[this.nextIndex] = element;
        this.nextIndex ++;
        return true;
    }

    /**
     * Removes an element from this set. If the given argument is null then this will have no effect, but will return
     * true if this MtxArraySet has any null values.
     *
     * @return true if element was found (and removed), false if element was not found.
     */
    @Override
    public boolean remove(T element) {
        boolean found = false;
        for (int i = 0; i < this.nextIndex; i++) {
            if (element == null && this.setContents[i] == null) {
                found = true;
                break;
            }

            if (this.setContents[i].equals(element)) {
                found = true;
                this.setContents[i] = this.setContents[this.nextIndex - 1];
                this.setContents[this.nextIndex - 1] = null;
                this.nextIndex --;
                break;
            }
        }
        return found;
    }

    /**
     * Removes an element from this set and returns it. If the given argument is null then this will have no effect.
     *
     * @return {@code T} if element was found (and removed), null if element was not found.
     */
    @SuppressWarnings("unchecked")
    public T removeAndReturn(T element) {
        T found = null;
        for (int i = 0; i < this.nextIndex; i++) {
            if (element == null) {
                break;
            }

            if (this.setContents[i].equals(element)) {
                found = (T) this.setContents[i];
                this.setContents[i] = this.setContents[this.nextIndex - 1];
                this.setContents[this.nextIndex - 1] = null;
                this.nextIndex --;
                break;
            }
        }
        return found;
    }

    /**
     * Removes everything from this set
     */
    @Override
    public void clear() {
        Arrays.fill(this.setContents, null);
        this.nextIndex = 0;
    }
}
