package com.edavalos.mtx.util.list.line;

public final class MtxDeque<T> extends MtxQueue<T> {

    public void enqueueFront(T element) {
        super.enqueue(element);
    }

    public void enqueueBack(T element) {
        MtxNode<T> newNode = new MtxNode<>(element);

        if (this.size == 0) {
            this.head = newNode;
            this.tail = newNode;
            this.size = 1;
            return;
        }

        newNode.setPrevious(this.head);
        this.head.setNext(newNode);
        this.head = newNode;
        this.size++;
    }

    public T dequeueFront() {
        return super.dequeue();
    }

    public T dequeueBack() {
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
            element = this.tail.getContent();
            this.tail = this.tail.getNext();
            this.tail.setPrevious(null);
            this.size--;
        }

        return element;
    }
}
