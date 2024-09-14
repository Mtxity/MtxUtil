package com.edavalos.mtx.util.set;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @SafeVarargs
    public MtxSortedTreeSet(Comparator<T> comparator, T... contents) {
        this.setContents = new TreeSet<>(comparator);
        for (T content : contents) {
            this.add(content);
        }
    }

    /**
     * Gets the number of elements in this MtxSortedTreeSet
     * @return size/length of this set
     */
    @Override
    public int size() {
        return this.setContents.size();
    }

    /**
     * Checks if this MtxSortedTreeSet has any elements or not
     * @return true if this set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.setContents.isEmpty();
    }

    /**
     * Checks if this MtxSortedTreeSet contains the given element
     * @param element element to check against
     * @return true if this set contains the specified element, false otherwise
     */
    @Override
    public boolean contains(T element) {
        return this.setContents.contains(element);
    }

    /**
     * Checks if this MtxSortedTreeSet contains all the given elements
     * @param elements list of elements to check against
     * @return true if this set contains all the specified elements, false otherwise
     */
    @Override
    public boolean containsAll(List<T> elements) {
        for (T element : elements) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if this MtxSortedTreeSet contains all the given elements
     * @param elements array of elements to check against
     * @return true if this set contains all the specified elements, false otherwise
     */
    @Override
    public boolean containsAll(T[] elements) {
        for (T element : elements) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts all elements in this MtxSortedTreeSet into an array
     * @return an array containing the elements in this set
     */
    @Override
    public T[] toArray() {
        return (T[]) this.setContents.toArray();
    }

    // @TODO: Add javadocs & unit tests for this
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (T element : this.setContents) {
            list.add(element);
        }
        return list;
    }

    // @TODO: Add javadocs & unit tests for this
    public Set<T> toSet() {
        return new HashSet<>(this.setContents);
    }

    /**
     * Adds an element to this MtxSortedTreeSet. If it already exists in the set, nothing happens.
     *
     * @param element element to attempt to add to this set
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
     * Removes an element from this MtxSortedTreeSet.
     *
     * @param element element to attempt to remove from this set
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
     * Removes an element from this MtxSortedTreeSet and returns it.
     * If the given argument is null then this will have no effect.
     *
     * @param element element to attempt to remove and return from this set
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
     * Removes everything from this MtxSortedTreeSet
     */
    @Override
    public void clear() {
        this.setContents.clear();
    }

    /**
     * Creates a String with all elements in this MtxSortedTreeSet bounded by parenthesis
     * and separate by commas.
     * @return a string representation of this MtxSortedTreeSet
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

    /**
     * Compare equality of this MtxSortedTreeSet with another object
     * @param obj Other object to compare
     * @return true if obj is instanceof MtxSortedTreeSet and all its elements are equal. False otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MtxSortedTreeSet<?> otherTreeSet)) {
            return false;
        }

        return this.setContents.equals(otherTreeSet.setContents);
    }
}
