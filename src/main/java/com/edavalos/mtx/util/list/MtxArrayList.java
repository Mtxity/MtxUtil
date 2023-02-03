package com.edavalos.mtx.util.list;

import java.util.Arrays;

public final class MtxArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double SCALE_FACTOR = 1.8;

    private int nextIdx;
    private int capacity;
    private Object[] content;

    public MtxArrayList() {
        this.nextIdx = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.content = new Object[this.capacity];
    }

    public MtxArrayList(int size) {
        this.nextIdx = 0;
        this.capacity = size;
        this.content = new Object[this.capacity];
    }

    public MtxArrayList(T[] initialContents) {
        this.nextIdx = initialContents.length;
        this.capacity = initialContents.length;
        this.content = new Object[this.capacity];
        System.arraycopy(initialContents, 0, this.content, 0, this.capacity);
    }

    public void add(T element) {
        if (this.nextIdx >= this.capacity) {
            int newCapacity = (int) Math.floor(this.capacity * SCALE_FACTOR);
            Object[] copy = new Object[newCapacity];
            System.arraycopy(this.content, 0, copy, 0, this.capacity);
            this.content = copy;
            this.capacity = newCapacity;
        }

        this.content[this.nextIdx] = element;
        this.nextIdx ++;
    }

    public boolean remove(T element) {
        boolean found = false;
        for (int i = 0; i < this.size(); i++) {
            Object nextVal = null;
            if (i + 1 < this.capacity) {
                nextVal = this.content[i + 1];
            }

            if (this.content[i].equals(element)) {
                found = true;
            }

            if (found) {
                this.content[i] = nextVal;
            }
        }

        if (found) {
            this.nextIdx --;
        }

        return found;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");

        for (int i = 0; i < this.size(); i++) {
            string.append(this.content[i].toString()).append(", ");
        }

        string.append("]");
        return string.toString().replace(", ]", "]");
    }

    public int size() {
        return this.nextIdx;
    }

    public int getSpaceLeftBeforeArrayIncrease() {
        return this.capacity - this.nextIdx;
    }

    public boolean isEmpty() {
        return this.nextIdx == 0;
    }

    public boolean contains(T element) {
        return Arrays.asList(this.content).contains(element);
    }

    public int countOccurrences(T element) {
        int count = 0;
        for (int i = 0; i < this.nextIdx; i++) {
            if (this.content[i].equals(element)) {
                count ++;
            }
        }
        return count;
    }
}
