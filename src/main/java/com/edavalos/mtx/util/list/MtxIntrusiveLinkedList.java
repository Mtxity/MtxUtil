package com.edavalos.mtx.util.list;

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
}
