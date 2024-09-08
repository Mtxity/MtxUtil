package com.edavalos.mtx.util.set;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Consumer;

public class MtxSortedTreeSet<T> implements MtxSet<T> {
    private final TreeSet<T> treeSet;
    private final Comparator<T> comparator;

    public MtxSortedTreeSet(Comparator<T> comparator) {
        this.treeSet = new TreeSet<>(comparator);
        this.comparator = comparator;
    }

    /**
     * @return size/length of this set
     */
    @Override
    public int size() {
        return this.treeSet.size();
    }

    /**
     * @return true if this set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.treeSet.isEmpty();
    }

    /**
     * @param element
     * @return true if this set contains the specified element, false otherwise
     */
    @Override
    public boolean contains(T element) {
        return this.treeSet.contains(element);
    }

    /**
     * @param elements
     * @return true if this set contains all the specified elements, false otherwise
     */
    @Override
    public boolean containsAll(List<T> elements) {
        for (int i = 0; i < this.treeSet.size(); i++) {
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
        for (int i = 0; i < this.treeSet.size(); i++) {
            if (!this.contains(elements[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return an iterator of this set
     */
    @Override
    public Iterator<T> iterator() {
        return MtxSet.super.iterator();
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Actions are performed in the order of iteration, if that
     * order is specified.  Exceptions thrown by the action are relayed to the
     * caller.
     * <p>
     * The behavior of this method is unspecified if the action performs
     * side-effects that modify the underlying source of elements, unless an
     * overriding class has specified a concurrent modification policy.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        MtxSet.super.forEach(action);
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="../util/Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator<T> spliterator() {
        return MtxSet.super.spliterator();
    }

    /**
     * @return an array of this set
     */
    @Override
    public T[] toArray() {
        return (T[]) this.treeSet.toArray();
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equalsTo(Object o) {
        return MtxSet.super.equalsTo(o);
    }

    /**
     * Adds an element to this set. If it already exists in the set, nothing happens.
     *
     * @param element
     * @return true if element was added to the set, false if it was already in it.
     */
    @Override
    public boolean add(T element) {
        if (this.contains(element)) {
            return false;
        } else {
            this.treeSet.add(element);
            return true;
        }
    }

    /**
     * Adds all elements to this set. If any already exists, nothing happens. If all
     * are new then all are added.
     *
     * @param elements
     * @return true if all elements are added to the set, false if one element already exists in this set.
     */
    @Override
    public boolean addAll(T[] elements) {
        return MtxSet.super.addAll(elements);
    }

    /**
     * Removes an element from this set
     *
     * @param element
     * @return true if element was found (and removed), false if element was not found.
     */
    @Override
    public boolean remove(T element) {
        return this.treeSet.remove(element);
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

        if (!this.treeSet.contains(element)) {
            return null;
        }

        for (T elementInList : this.treeSet) {
            if (element.equals(elementInList)) {
                this.treeSet.remove(elementInList);
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
        this.treeSet.clear();
    }
}
