package com.edavalos.mtx.util.list.lineup;

public final class MtxQueue<T> {
    private MtxNode<T> head;
    private MtxNode<T> tail;
    private int size;

    public MtxQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(T element) {
        MtxNode<T> newNode = new MtxNode<>(element);

        if (this.size == 0) {
            this.head = newNode;
            this.tail = newNode;
            this.size = 1;
            return;
        }

        newNode.setNext(this.tail);
        this.tail.setPrevious(newNode);
        this.tail = newNode;
        this.size ++;
    }

    public T dequeue() {
        T element;

        if (this.size == 0) {
            return null;
        }

        if (this.size == 1) {
            element = this.tail.getContent();
            this.head = null;
            this.tail = null;
            this.size = 0;
        } else {
            element = this.head.getContent();
            this.head = this.head.getPrevious();
            this.head.setNext(null);
            this.size --;
        }

        return element;
    }

    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");

        MtxNode<T> next = this.head;
        while (next != null) {
            string.append(next.getContent().toString()).append(", ");
            next = next.getPrevious();
        }

        string.append(']');
        return string.toString().replace(", ]", "]");
    }
}
