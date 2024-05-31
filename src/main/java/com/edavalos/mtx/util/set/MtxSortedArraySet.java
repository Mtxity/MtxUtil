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

    @Override
    public boolean remove(T element) {
        boolean removed = super.remove(element);
    }
}
