package com.edavalos.mtx.util.list.line;

public class MtxNode<E> {
    private final E content;
    private MtxNode<E> next;
    private MtxNode<E> previous;

    protected MtxNode(E content) {
        this.content = content;
        this.next = null;
        this.previous = null;
    }

    public E getContent() {
        return this.content;
    }

    public void setNext(MtxNode<E> next) {
        this.next = next;
    }

    public MtxNode<E> getPrevious() {
        return this.previous;
    }

    public void setPrevious(MtxNode<E> prev) {
        this.previous = prev;
    }
}
