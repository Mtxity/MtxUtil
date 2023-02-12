package com.edavalos.mtx.util.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class MtxArrayList<T> implements Iterable<T> {
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

    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.nextIdx) {
            throw new IndexOutOfBoundsException(index);
        }
        return ((T) this.content[index]);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < this.size(); i++) {
            int elementHashCode = this.content[i] == null ? 0 : this.content[i].hashCode();
            hashCode = (31 * hashCode) + elementHashCode;
        }
        return hashCode;
    }

    public int indexOf(T element) {
        return Arrays.asList(this.content).indexOf(element);
    }

    public void clear() {
        this.nextIdx = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.content = new Object[this.capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return this.idx < size();
            }

            @Override
            public T next() {
                return ((T) content[this.idx++]);
            }
        };
    }

    // TODO: Make this return MtxList<T> once interface is done being implemented
    public MtxArrayList<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException(fromIndex);
        }
        if (toIndex > this.size()) {
            throw new IndexOutOfBoundsException(toIndex);
        }

        MtxArrayList<T> newList = new MtxArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            newList.add(((T) this.content[i]));
        }
        return newList;
    }

    public T[] toArray() {
        Object[] array = new Object[this.size()];

        for (int i = 0; i < this.size(); i++) {
            array[i] = this.content[i];
        }

        return (T[]) array;
    }

    public T removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        T element = ((T) this.content[index]);
        for (int i = index; i < this.size(); i++) {
            this.content[i] = this.content[i + 1];
        }

        this.nextIdx --;
        return element;
    }

    public T set(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        T oldElement = ((T) this.content[index]);
        this.content[index] = element;
        return oldElement;
    }

    // Based on https://www.baeldung.com/java-merge-sort
    public void sort(Comparator<T> comparator) {
        this.mergeSort(this.content, this.size(), comparator);
    }

    private void mergeSort(Object[] a, int n, Comparator<T> comparator) {
        if (n < 2) {
            return;
        }

        int mid = n / 2;
        Object[] l = new Object[mid];
        Object[] r = new Object[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int j = mid; j < n; j++) {
            r[j - mid] = a[j];
        }
        this.mergeSort(l, mid, comparator);
        this.mergeSort(r, n - mid, comparator);

        this.merge(a, l, r, mid, n - mid, comparator);
    }

    private void merge(Object[] a, Object[] l, Object[] r, int left, int right, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left && j < right) {
            if (comparator.compare(((T) l[i]), ((T) r[j])) <= 0) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }

        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    public boolean removeDuplicates() {
        List<Object> newContents = Arrays.asList(new Object[this.size()]);
        boolean foundDuplicate = false;

        for (int i = 0; i < this.size(); i++) {
            if (newContents.contains(this.content[i])) {
                foundDuplicate = true;
                this.nextIdx --;
            } else {
                newContents.add(this.content[i]);
            }
        }

        if (foundDuplicate) {
            this.content = newContents.toArray();
            return true;
        }
        return false;
    }
}
