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
        this.size ++;
    }

    boolean remove(T element) {
        if (this.head == null) {
            return false;
        }

        if (this.head.content.equals(element)) {
            this.head = this.head.next;
            this.size --;
            return true;
        }

        MtxNode currentNode = this.head;
        MtxNode previous = null;
        while (currentNode != null && !currentNode.content.equals(element)) {
            currentNode = currentNode.next;
            previous = currentNode;
        }

        if (currentNode != null) {
            previous.next = currentNode.next;
            this.size --;
            return true;
        }

        return false;
    }
}
