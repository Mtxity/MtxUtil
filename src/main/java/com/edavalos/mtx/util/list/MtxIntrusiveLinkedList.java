package com.edavalos.mtx.util.list;

import java.util.Iterator;

public final class MtxIntrusiveLinkedList<T> implements MtxList<T>, Iterable<T> {
    private class MtxItrNode {
        MtxItrNode next;
        MtxItrNode prev;

        MtxItrNode() {
            this.next = null;
            this.prev = null;
        }
    }

    private class MtxItrData<D> extends MtxItrNode {
        D data;

        MtxItrData(D data) {
            this.data = data;
        }
    }

    private MtxItrNode head;
    private int size;

    public MtxIntrusiveLinkedList() {
        this.head = new MtxItrNode();
        this.head.next = this.head;
        this.head.prev = this.head;
        this.size = 0;
    }

    @Override
    public void add(T element) {
        MtxItrData<T> newNode = new MtxItrData<>(element);
        newNode.next = this.head.next;
        newNode.prev = this.head;
        this.head.next.prev = newNode;
        this.head.next = newNode;
        this.size ++;
    }

    @Override
    public boolean remove(T element) {
        if (this.head == null) {
            return false;
        }

        MtxItrNode node = this.findNode(element);
        if (node == null) {
            return false;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        this.size --;
        return true;
    }

    private MtxItrData<T> findNode(T contents) {
        MtxItrNode current = this.head.next;
        while (current != this.head) {
            if (current instanceof MtxItrData<T> dataNode) {
                if (dataNode.data.equals(contents)) {
                    return dataNode;
                }
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public String toString() {
        String[] contentsStrArr = new String[this.size()];

        MtxItrNode current = this.head.next;
        int i = 0;
        while (current != this.head) {
            if (current instanceof MtxItrData<T> dataNode) {
                contentsStrArr[i++] = dataNode.data.toString();
            }
            current = current.next;
        }

        return "[" + String.join(", ", contentsStrArr) + "]";
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head.next == this.head.prev;
    }

    @Override
    public boolean contains(T element) {
        return this.findNode(element) != null;
    }

    @Override
    public int countOccurrences(T element) {
        int count = 0;
        MtxItrNode current = this.head.next;
        while (current != this.head) {
            if (current instanceof MtxItrData<T> dataNode) {
                if (dataNode.data.equals(element)) {
                    count ++;
                }
            }
            current = current.next;
        }
        return count;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(index);
        }

        MtxItrNode node = this.getNodeAt(index);
        if (node instanceof MtxItrData<T> dataNode) {
            return dataNode.data;
        } else {
            return null;
        }
    }

    private MtxItrNode getNodeAt(int index) {
        MtxItrNode current = this.head.next;
        while (current != this.head) {
            if (current instanceof MtxItrData<T> dataNode) {
                if (index == 0) {
                    return dataNode;
                }
                index --;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return this.equalsTo(o);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        MtxItrNode current = this.head.next;
        while (current != this.head) {
            if (current instanceof MtxItrData<T> dataNode) {
                int elementHashCode = dataNode.data == null ? 0 : dataNode.data.hashCode();
                hashCode = (31 * hashCode) + elementHashCode;
            }
            current = current.next;
        }
        return hashCode;
    }

    @Override
    public int indexOf(T element) {
        int idx = 0;
        MtxItrNode current = this.head.next;
        while (current != this.head) {
            if (current instanceof MtxItrData<T> dataNode) {
                if (dataNode.data.equals(element)) {
                    return idx;
                }
                idx ++;
            }
            current = current.next;
        }
        return idx;
    }

    @Override
    public void clear() {
        this.head = new MtxItrNode();
        this.head.next = this.head;
        this.head.prev = this.head;
        this.size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private MtxItrNode current = head.next;

            @Override
            public boolean hasNext() {
                return current != head;
            }

            @Override
            public T next() {
                T element = ((MtxItrData<T>) this.current).data;
                this.current = this.current.next;
                return element;
            }
        };
    }
}
