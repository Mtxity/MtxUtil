package com.edavalos.mtx.util.set;

import java.util.Comparator;

/**
 * Ordered and auto-sorted implementation of MtxSet backed by a linked list.
 */
public final class MtxSortedLinkedSet<T> extends MtxLinkedSet<T> {
    private final Comparator<T> comparator;

    public MtxSortedLinkedSet(Class<T> type, Comparator<T> comparator) {
        super(type);
        this.comparator = comparator;
    }

    @SafeVarargs
    public MtxSortedLinkedSet(Class<T> type, Comparator<T> comparator, T... initialContents) {
        super(type, initialContents);
        this.comparator = comparator;
    }

    @Override
    public boolean add(T element) {
        boolean added = super.add(element);
        this.sort();
        return added;
    }

    private void sort() {
        MtxNode current = this.head;
        MtxNode index;

        while (current != null) {
            index = current.next;
            while (index != null) {
                if (this.comparator.compare(current.content, index.content) > 0) {
                    T temp = current.content;
                    current.content = index.content;
                    index.content = temp;
                }
                index = index.next;
            }
            current = current.next;
        }
    }
}
