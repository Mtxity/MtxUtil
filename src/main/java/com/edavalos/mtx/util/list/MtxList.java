package com.edavalos.mtx.util.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public interface MtxList<T> {
    // ---------------------- Query Operations ---------------------

    /**
     * @return size/length of this list
     */
    int size(); // DONE

    /**
     * @return true if this list is empty, false otherwise
     */
    boolean isEmpty(); // DONE

    /**
     * @return true if this list contains the specified element, false otherwise
     */
    boolean contains(T element); // DONE

    /**
     * @return true if this list contains all the specified elements, false otherwise
     */
    // TODO: Add unit tests
    boolean containsAll(Collection<T> elements); // DONE
    boolean containsAll(T[] elements); // DONE

    /**
     * @return an iterator of this list
     */
    Iterator<T> iterator(); // DONE

    /**
     * @return an array of this list
     */
    T[] toArray(); // DONE

    /**
     * @return an identical copy of this list
     */
    MtxList<T> copy(); // DONE

    // ------------------ Modification Operations ------------------

    /**
     * Adds an element to this list
     */
    void add(T element); // DONE

    /**
     * Adds all elements to this list
     */
    // TODO: Add unit tests
    void addAll(Collection<T> elements); // DONE
    void addAll(T[] elements); // DONE

    /**
     * Removes an element from this list
     * @return true if element was found (and removed), false if element was not found
     * @apiNote if the same element is in this list more than once, this only removes the first occurrence
     */
    boolean remove(T element); // DONE

    /**
     * Removes all elements from this list
     * @return true if elements were all found (and removed), false if at least one element was not found.
     * Note that if an element is not found (and the method terminates and returns false), elements that
     * came before it in the given list will still have been removed.
     * @apiNote if an element from the given list is in this list more than once, this only removes the first occurrence
     */
    // TODO: Add unit tests
    boolean removeAll(Collection<T> elements); // DONE
    boolean removeAll(T[] elements); // DONE

    /**
     * Sorts this list
     */
    void sort(Comparator<T> comparator); // DONE

    /**
     * Removes everything from this list
     */
    void clear(); // DONE

    /**
     * @return true if all elements in this list satisfy .equals() with given object, false otherwise
     */
    @Override
    boolean equals(Object o); // DONE

    /**
     * @return the hashcode of this list
     */
    @Override
    int hashCode(); // DONE

    /**
     * @return a string representation of this list
     */
    @Override
    String toString(); // DONE

    // --------------- Positional Access Operations ----------------

    /**
     * @return the element at the given index
     * @throws IndexOutOfBoundsException when the index is larger than the size of this list minus one (or is negative)
     */
    T get(int index) throws IndexOutOfBoundsException; // DONE

    /**
     * Sets the element at the given index to the given element
     * @return the old element before replacing it with the new one
     * @throws IndexOutOfBoundsException when the index is larger than the size of this list minus one (or is negative)
     */
    T set(int index, T element) throws IndexOutOfBoundsException; // DONE

    /**
     * Removes the element at the given index and shifts everything that comes after down by one
     * @return the element being removed
     * @throws IndexOutOfBoundsException when the index is larger than the size of this list minus one (or is negative)
     */
    T removeAt(int index) throws IndexOutOfBoundsException; // DONE

    /**
     * @return the index of the given element, or -1 if the element could not be found
     * @apiNote if the same element is in this list more than once, this only gets the index of the first occurrence
     */
    int indexOf(T element); // DONE

    /**
     * @return the number of occurrences of the given element in this list
     */
    int countOccurrences(T element); // DONE

    /**
     * @return a new list starting at the given fromIndex and ending at the given toIndex
     * @throws IndexOutOfBoundsException when the fromIndex or toIndex is larger than the size of this list minus one (or is negative)
     */
    MtxList<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException; // DONE
}
