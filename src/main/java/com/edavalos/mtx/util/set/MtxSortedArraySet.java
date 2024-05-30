package com.edavalos.mtx.util.set;

/**
 * Ordered and auto-sorted implementation of MtxArraySet backed by a tree set.
 */
public class MtxSortedArraySet<T> extends MtxArraySet<T> {
    protected int next;

    public MtxSortedArraySet() {
    }

    public MtxSortedArraySet(T... contents) {
    }

    @Override
    public boolean remove(T element) {
        boolean removed = super.remove(element);
    }
}
