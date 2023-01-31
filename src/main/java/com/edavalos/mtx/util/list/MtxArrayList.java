package com.edavalos.mtx.util.list;

public final class MtxArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double SCALE_FACTOR = 1.8;

    private int nextIdx;
    private int capacity;
    private Object[] content;

    public MtxArrayList() {
        this.nextIdx = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.content = new Object[capacity];
    }

    public void add(T element) {
        if (this.nextIdx > this.capacity) {
            int newCapacity = (int) Math.floor(this.capacity * SCALE_FACTOR);
            Object[] copy = new Object[newCapacity];
            System.arraycopy(this.content, 0, copy, 0, this.capacity);
            this.content = copy;
            this.capacity = newCapacity;
        }

        this.content[this.nextIdx] = element;
        this.nextIdx ++;
    }

    boolean remove(T element) {
        boolean found = false;
        for (int i = 0; i < this.capacity; i++) {
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

        return found;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");

        for (Object element : this.content) {
            string.append(element.toString()).append(", ");
        }

        string.append("]");
        return string.toString().replace(", ]", "]");
    }

    public int size() {
        return this.nextIdx - 1;
    }
}
