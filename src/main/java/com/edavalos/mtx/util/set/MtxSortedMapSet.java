package com.edavalos.mtx.util.set;

import java.util.HashMap;

/**
 * Ordered and auto-sorted implementation of MtxMapSet backed by a tree set.
 */
public class MtxSortedMapSet<T> extends MtxMapSet<T> {
    protected final HashMap<Integer, T> setOrder;
    protected int next;

    public MtxSortedMapSet() {
        super();
        this.setOrder = new HashMap<>();
        this.next = 0;
    }

    public MtxSortedMapSet(T... contents) {
        this.setOrder = new HashMap<>();
        this.next = 0;
        for (T content : contents) {
            this.add(content);
            this.setOrder.put(this.next, content);
            this.next ++;
        }
    }
}
