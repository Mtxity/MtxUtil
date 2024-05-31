package com.edavalos.mtx.util.set;

import java.util.Comparator;

/**
 * Ordered and auto-sorted implementation of MtxArraySet backed by a tree set.
 */
public class MtxSortedArraySet<T> extends MtxArraySet<T> {
    private final Comparator<T> comparator;

    public MtxSortedArraySet(Comparator<T> comparator) {
        super();
        this.comparator = comparator;
    }

    public MtxSortedArraySet(Comparator<T> comparator, int startingSize) {
        super(startingSize);
        this.comparator = comparator;
    }

    public MtxSortedArraySet(Comparator<T> comparator, T... contents) {
        super(contents);
        this.comparator = comparator;
        this.sort();
    }

    // @TODO: Make this more efficient by upshifting array instead of adding new element at the end and then sorting
    @Override
    public boolean add(T element) {
        if (this.contains(element)) {
            return false;
        }

        super.add(element);
        this.sort();
        return true;
    }

    // @TODO: Make this more efficient by downshifting array instead of swapping with last element
    @Override
    public boolean remove(T element) {
        boolean removed = super.remove(element);
        if (removed) {
            this.sort();
            return true;
        } else {
            return false;
        }
    }

    private void sort() {
        // @TODO: Implement this
    }
}
