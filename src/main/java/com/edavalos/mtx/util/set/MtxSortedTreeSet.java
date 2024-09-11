package com.edavalos.mtx.util.set;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Ordered and auto-sorted implementation of MtxSet backed by a treeset.
 * Nulls are not allowed in this one.
 */
public class MtxSortedTreeSet<T> implements MtxSet<T> {
    protected final TreeSet<T> setContents;

    public MtxSortedTreeSet(Comparator<T> comparator) {
        this.setContents = new TreeSet<>(comparator);
    }

    public MtxSortedTreeSet(Comparator<T> comparator, T... contents) {
        this.setContents = new TreeSet<>(comparator);
        for (T content : contents) {
            this.add(content);
        }
    }

    /**
     * @return size/length of this set
     */
    @Override
    public int size() {
        return this.setContents.size();
    }

    /**
     * @return true if this set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.setContents.isEmpty();
    }

    /**
     * @param element
     * @return true if this set contains the specified element, false otherwise
     */
    @Override
    public boolean contains(T element) {
        return this.setContents.contains(element);
    }

    /**
     * @param elements
     * @return true if this set contains all the specified elements, false otherwise
     */
    @Override
    public boolean containsAll(List<T> elements) {
        for (int i = 0; i < elements.size(); i++) {
            if (!this.contains(elements.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param elements
     * @return true if this set contains all the specified elements, false otherwise
     */
    @Override
    public boolean containsAll(T[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (!this.contains(elements[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return an array of this set
     */
    @Override
    public T[] toArray() {
        return (T[]) this.setContents.toArray();
    }

    /**
     * Adds an element to this set. If it already exists in the set, nothing happens.
     *
     * @param element
     * @return true if element was added to the set, false if it was already in it.
     */
    @Override
    public boolean add(T element) {
        if (element == null || this.contains(element)) {
            return false;
        } else {
            this.setContents.add(element);
            return true;
        }
    }

    /**
     * Removes an element from this set
     *
     * @param element
     * @return true if element was found (and removed), false if element was not found.
     */
    @Override
    public boolean remove(T element) {
        if (element == null) {
            return false;
        }

        return this.setContents.remove(element);
    }

    /**
     * Removes an element from this set and returns it. If the given argument is null then this will have no effect.
     *
     * @return {@code T} if element was found (and removed), null if element was not found.
     */
    public T removeAndReturn(T element) {
        if (element == null) {
            return null;
        }

        for (T elementInList : this.setContents) {
            if (element.equals(elementInList)) {
                this.setContents.remove(elementInList);
                return elementInList;
            }
        }
        return null;
    }

    /**
     * Removes everything from this set
     */
    @Override
    public void clear() {
        this.setContents.clear();
    }

    /**
     * @return a string representation of this set
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (Object item : this.setContents) {
            sb.append(item).append(", ");
        }
        sb.append(")");
        return sb.toString().replace(", )", ")");
    }
}
