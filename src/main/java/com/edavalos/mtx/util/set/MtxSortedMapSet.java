package com.edavalos.mtx.util.set;

import java.util.HashMap;

/**
 * Ordered and auto-sorted implementation of MtxMapSet backed by a tree set.
 */
public class MtxSortedMapSet<T> extends MtxMapSet<T> {
    protected int next;

    public MtxSortedMapSet() {
        super.setContents = new HashMap<>();
        this.next = 0;
    }

    public MtxSortedMapSet(T... contents) {
        super.setContents = new HashMap<>();
        this.next = contents.length;
        for (T content : contents) {
            this.add(content);
            this.next ++;
        }
    }

    @Override
    public boolean remove(T element) {
        return super.remove(element);
    }
}
