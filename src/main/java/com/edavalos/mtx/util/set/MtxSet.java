package com.edavalos.mtx.util.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface MtxSet<T> extends Iterable<T> {
    // ---------------------- Query Operations ---------------------

    /**
     * @return size/length of this set
     */
    int size();

    /**
     * @return true if this set is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * @return true if this set contains the specified element, false otherwise
     */
    boolean contains(T element);

    /**
     * @return true if this set contains all the specified elements, false otherwise
     */
    default boolean containsAll(List<T> elements) {
        for (T element : elements) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true if this array contains all the specified elements, false otherwise
     */
    default boolean containsAll(T[] elements) {
        return this.containsAll(Arrays.asList(elements));
    }

    /**
     * @return an iterator of this set
     */
    @Override
    default Iterator<T> iterator() {
        return new Iterator<>() {
            private T[] array = toArray();
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return this.idx < size();
            }

            @Override
            public T next() {
                return array[this.idx++];
            }
        };
    }

    /**
     * @return an array of this set
     */
    T[] toArray();

    /**
     * @return a List of this set
     */
    default List<T> toList() {
        return Arrays.asList(this.toArray());
    }

    /**
     * @return a generic Set copy of this set
     */
    default Set<T> toSet() {
        return new HashSet<>(Arrays.asList(this.toArray()));
    }

    /**
     * @return the hashcode of this set
     */
    @Override
    int hashCode();

    /**
     * @return a string representation of this set
     */
    @Override
    String toString();

    /**
     * @return true if this set contains the same elements and only those elements as the ones in another given set.
     * If another type is passed, returns o.equals(this)
     */
    @Override
    boolean equals(Object o);

    default boolean equalsTo(Object o) {
        if (o instanceof MtxSet<?> otherSet) {
            if (otherSet.size() != this.size()) {
                return false;
            }

            for (Object otherElement : otherSet) {
                if (!this.contains((T) otherElement)) {
                    return false;
                }
            }

            return true;
        } else {
            return o.equals(this);
        }
    }

    // ------------------ Modification Operations ------------------

    /**
     * Adds an element to this set. If it already exists in the set, nothing happens.
     * @return true if element was added to the set, false if it was already in it.
     */
    boolean add(T element);

    /**
     * Adds all elements to this set. If any already exists, nothing happens. If all
     * are new then all are added.
     * @return true if all elements are added to the set, false if one element already exists in this set.
     */
    default boolean addAll(T[] elements) {
        for (T element : elements) {
            if (this.contains(element)) {
                return false;
            }
        }

        for (T element : elements) {
            this.add(element);
        }
        return true;
    }

    /**
     * Removes an element from this set
     * @return true if element was found (and removed), false if element was not found.
     */
    boolean remove(T element);

    /**
     * Removes everything from this set
     */
    void clear();
}
