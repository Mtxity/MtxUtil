package com.edavalos.mtx.util.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public interface MtxList<T> {
    // Query Operations
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    boolean containsAll(Collection<T> c);
    Iterator<T> iterator();
    T[] toArray();
    MtxList<T> copy();

    // Modification Operations
    boolean add(T e);
    boolean addAll(Collection<T> c);
    boolean remove(T e);
    boolean removeAll(Collection<T> c);
    boolean retainOnly(T e);
    boolean retainOnly(Collection<T> c);
    void sort(Comparator<? super T> c);
    void clear();
    @Override
    boolean equals(Object o);
    @Override
    int hashCode();

    // Positional Access Operations
    T get(int index);
    T set(int index, T element);
    T removeAt(int index);
    int indexOf(T element);
    int countOccurrences(T element);
    MtxList<T> subList(int fromIndex, int toIndex);
}
