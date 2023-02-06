package com.edavalos.mtx.util.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

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
     */
    boolean containsAll(Collection<T> elements);
    boolean containsAll(T[] elements);

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
    MtxList<T> copy();

    // ------------------ Modification Operations ------------------

    /**
     * Adds an element to this list
     */
    void add(T element);

    /**
     * Adds all elements to this list
     */
    void addAll(Collection<T> elements);
    void addAll(T elements);

    /**
     * Removes an element from this list
     * @return true if element was found (and removed), false if element was not found
     */
    boolean remove(T element);

    /**
     * Removes all elements from this list
     * @return true if elements were all found (and removed), false if at least one element was not found
     */
    boolean removeAll(Collection<T> elements);

    /**
     * Removes all elements except this one from this list
     */
    void retainOnly(T element);

    /**
     * Removes all elements except these from this list
     */
    void retainOnly(Collection<T> elements);

    /**
     * Sorts this list
     */
    void sort(Comparator<? super T> comparator);

    /**
     * Removes everything from this list
     */
    void clear();

    /**
     * @return true if all elements in this list satisfy .equals() with given object, false otherwise
     */
    @Override
    boolean equals(Object o);

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
}
