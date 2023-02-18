package com.edavalos.mtx.util.set;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

/**
 * Ordered implementation of MtxSet backed by a linked list.
 */
public final class MtxLinkedSet<T> implements MtxSet<T> {
    private class MtxNode {
        T content;
        MtxNode next;

        MtxNode(T content) {
            this.content = content;
            this.next = null;
        }
    }

    private final Class<T> type;
    private MtxNode head;
    private int size;

    public MtxLinkedSet(Class<T> type) {
        this.type = type;
        this.head = null;
        this.size = 0;
    }

    @SafeVarargs
    public MtxLinkedSet(Class<T> type, T... initialContents) {
        this(type);
        Set<T> addedElements = new HashSet<>(initialContents.length);
        for (T element : initialContents) {
            MtxNode newNode = new MtxNode(element);
            if (this.head == null) {
                this.head = newNode;
                this.size = 1;
                addedElements.add(element);
                continue;
            }

            if (addedElements.contains(element)) {
                continue;
            }

            MtxNode last = this.head;
            while (last.next != null) {
                last = last.next;
            }

            last.next = newNode;
            this.size ++;
            addedElements.add(element);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public boolean contains(T element) {
        MtxNode next = this.head;
        while (next != null) {
            if (next.content.equals(element)) {
                return true;
            }
            next = next.next;
        }
        return false;
    }

    @Override
    public T[] toArray() {
        T[] newArray = (T[]) Array.newInstance(this.type, this.size());
        int idx = 0;
        MtxNode next = this.head;
        while (next != null) {
            newArray[idx++] = next.content;
            next = next.next;
        }
        return newArray;
    }

    @Override
    public boolean add(T element) {
        // @TODO
    }

    /**
     * Adds all given elements to this set. If an element already exists in the set, it is skipped.
     * @return true if all elements were added to the set, false if at least one was already in it.
     */
    @SafeVarargs
    public final boolean add(T... elements) {
        boolean addedAll = true;
        for (T element : elements) {
            addedAll = addedAll & this.add(element);
        }
        return addedAll;
    }

    @Override
    public boolean remove(T element) {
        // @TODO
    }

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;

        MtxNode next = this.head;
        while (next != null) {
            int elementHashCode = next.content == null ? 0 : next.content.hashCode();
            hashCode += elementHashCode;
            next = next.next;
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        return this.equalsTo(o);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("(");

        MtxNode next = this.head;
        while (next != null) {
            string.append(next.content.toString()).append(", ");
            next = next.next;
        }

        string.append(")");
        return string.toString().replace(", )", ")");
    }
}
