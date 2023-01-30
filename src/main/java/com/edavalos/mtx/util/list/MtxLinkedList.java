package com.edavalos.mtx.util.list;

public final class MtxLinkedList<T> {
    private class MtxNode {
        T content;
        MtxNode next;

        MtxNode(T content) {
            this.content = content;
            this.next = null;
        }
    }

    private MtxNode head;
    private int size;

    public MtxLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(T element) {
        MtxNode newNode = new MtxNode(element);

        if (this.head == null) {
            this.head = newNode;
            return;
        }

        MtxNode last = this.head;
        while (last.next != null) {
            last = last.next;
        }

        last.next = newNode;
    }
}
