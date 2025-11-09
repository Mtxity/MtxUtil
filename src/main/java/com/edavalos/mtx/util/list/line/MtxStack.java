package com.edavalos.mtx.util.list.line;

import java.util.HashMap;

public final class MtxStack<T> {
    private static final HashMap<Character, Character> PARENTHESIS_PAIRS = new HashMap<>() {{
        put(')', '(');
        put('}', '{');
        put(']', '[');
        put('>', '<');
    }};

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

    public boolean contains(T element) {
        MtxNode<T> next = this.head;
        while (next != null) {
            if (next.getContent().equals(element)) {
                return true;
            }
            next = next.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(index);
        }

        // Index 0 = top (head). Traverse towards older elements via 'previous'.
        MtxNode<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getPrevious();
        }
        return current.getContent();
    }

    public static boolean isValidParenthesis(String s) {
        MtxStack<Character> parStack = new MtxStack<>();
        for (char c : s.toCharArray()) {
            if (PARENTHESIS_PAIRS.containsKey(c)) {
                if (parStack.isEmpty()) {
                    return false;
                }
                if (parStack.pop() != PARENTHESIS_PAIRS.get(c)) {
                    return false;
                }
            } else if (parStack.PARENTHESIS_PAIRS.containsValue(c)) {
                parStack.push(c);
            }
        }
        return parStack.isEmpty();
    }
}
