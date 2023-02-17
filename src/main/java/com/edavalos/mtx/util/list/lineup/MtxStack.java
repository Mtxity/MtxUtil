package com.edavalos.mtx.util.list.lineup;

public final class MtxStack<T> {
    private MtxNode<T> head;
    private int size;

    public MtxStack() {
        this.head = null;
        this.size = 0;
    }

    public void push(T element) {
        MtxNode<T> newNode = new MtxNode<>(element);

        if (this.size == 0) {
            this.head = newNode;
            this.size = 1;
            return;
        }

        newNode.setPrevious(this.head);
        this.head.setNext(newNode);
        this.head = newNode;
        this.size ++;
    }

    public T pop() {
        if (this.size == 0) {
            return null;
        }

        T element = this.head.getContent();
        if (this.size == 1) {
            this.head = null;
            this.size = 0;
        } else {
            this.head = this.head.getPrevious();
            this.head.setNext(null);
            this.size --;
        }

        return element;
    }

    public T peek() {
        if (this.size == 0) {
            return null;
        }

        return this.head.getContent();
    }

    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("]");

        MtxNode<T> next = this.head;
        while (next != null) {
            string.append(" ,").append(next.getContent().toString());
            next = next.getPrevious();
        }

        string.append('[');
        return string.reverse().toString().replace(", ]", "]");
    }
}
