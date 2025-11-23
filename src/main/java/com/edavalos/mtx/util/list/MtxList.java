package com.edavalos.mtx.util.list;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public interface MtxList<T> {
    // ---------------------- Query Operations ---------------------

    /**
     * @return size/length of this list
     */
    int size();

    /**
     * @return true if this list is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * @return true if this list contains the specified element, false otherwise
     */
    boolean contains(T element);

    /**
     * @return true if this list contains all the specified elements, false otherwise
     * @apiNote this ignores duplicates.
     */
    default boolean containsAll(List<T> elements) {
        for (T element : elements) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }
    default boolean containsAll(T[] elements) {
        return this.containsAll(Arrays.asList(elements));
    }

    /**
     * @return an iterator of this list
     */
    Iterator<T> iterator();

    /**
     * @return an array of this list
     */
    T[] toArray();

    /**
     * @return an identical copy of this list
     */
    default MtxList<T> copy() {
        return this.subList(0, this.size());
    }

    /**
     * @return true if all elements in this list satisfy .equals() with objects in given list/array, false otherwise.
     * Also returns false if given object is not a List, MtxList, or array.
     */
    @Override
    boolean equals(Object o);
    default boolean equalsTo(Object o) {
        if (o instanceof MtxList<?> otherList) {
            if (otherList.size() != this.size()) {
                return false;
            }

            for (int i = 0; i < this.size(); i++) {
                if (!otherList.get(i).equals(this.get(i))) {
                    return false;
                }
            }

            return true;
        }

        if (o instanceof List<?> otherList) {
            if (otherList.size() != this.size()) {
                return false;
            }

            for (int i = 0; i < this.size(); i++) {
                if (!otherList.get(i).equals(this.get(i))) {
                    return false;
                }
            }

            return true;
        }

        if (o instanceof Object[] array) {
            if (array.length != this.size()) {
                return false;
            }

            for (int i = 0; i < this.size(); i++) {
                if (!array[i].equals(this.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return o.equals(this);
    }

    /**
     * @return the hashcode of this list
     */
    @Override
    int hashCode();

    /**
     * @return a string representation of this list
     */
    @Override
    String toString();

    // ------------------ Modification Operations ------------------

    /**
     * Adds an element to this list
     */
    void add(T element);

    /**
     * Adds all elements to this list
     */
    default void addAll(Collection<T> elements) {
        for (T element : elements) {
            this.add(element);
        }
    }
    default void addAll(T[] elements) {
        this.addAll(Arrays.asList(elements));
    }

    /**
     * Removes an element from this list
     * @return true if element was found (and removed), false if element was not found
     * @apiNote if the same element is in this list more than once, this only removes the first occurrence
     */
    boolean remove(T element);

    /**
     * Removes all elements from this list
     * @return true if elements were all found (and removed), false if at least one element was not found.
     * Note that if an element is not found (and the method terminates and returns false), elements that
     * came before it in the given list will still have been removed.
     * @apiNote if an element from the given list is in this list more than once, this only removes the first occurrence
     */
    default boolean removeAll(Collection<T> elements) {
        boolean found = false;
        for (T element : elements) {
            if (this.remove(element)) {
                found = true;
            }
        }
        return found;
    }
    default boolean removeAll(T[] elements) {
        return this.removeAll(Arrays.asList(elements));
    }

    /**
     * Removes all duplicate elements from this list
     * @return true if at least one element was found two or more times in this list and all but one of it were removed.
     */
    boolean removeDuplicates();

    /**
     * Sorts this list
     */
    void sort(Comparator<T> comparator);

    /**
     * Removes everything from this list
     */
    void clear();

    // --------------- Positional Access Operations ----------------

    /**
     * @return the element at the given index
     * @throws IndexOutOfBoundsException when the index is larger than the size of this list minus one (or is negative)
     */
    T get(int index) throws IndexOutOfBoundsException;

    /**
     * Sets the element at the given index to the given element
     * @return the old element before replacing it with the new one
     * @throws IndexOutOfBoundsException when the index is larger than the size of this list minus one (or is negative)
     */
    T set(int index, T element) throws IndexOutOfBoundsException;

    /**
     * Removes the element at the given index and shifts everything that comes after down by one
     * @return the element being removed
     * @throws IndexOutOfBoundsException when the index is larger than the size of this list minus one (or is negative)
     */
    T removeAt(int index) throws IndexOutOfBoundsException;

    /**
     * @return the index of the given element, or -1 if the element could not be found
     * @apiNote if the same element is in this list more than once, this only gets the index of the first occurrence
     */
    int indexOf(T element);

    /**
     * @return the number of occurrences of the given element in this list
     */
    int countOccurrences(T element);

    /**
     * @return a new list starting at the given fromIndex and ending at the given toIndex
     * @throws IndexOutOfBoundsException when the fromIndex or toIndex is larger than the size of this list minus one (or is negative)
     */
    MtxList<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException;

    /**
     * Shifts this list left by times times
     * @param times number of times to shift left
     */
    void rotateLeft(int times);

    /**
     * Shifts this list right by times times
     * @param times number of times to shift right
     */
    void rotateRight(int times);

    void reverse();
}
