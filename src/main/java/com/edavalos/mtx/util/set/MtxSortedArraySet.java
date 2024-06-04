package com.edavalos.mtx.util.set;

import java.util.Comparator;

/**
 * Ordered and auto-sorted implementation of MtxArraySet backed by an array.
 */
public class MtxSortedArraySet<T> extends MtxArraySet<T> {
    private final Comparator<T> comparator;

    public MtxSortedArraySet(Comparator<T> comparator) {
        super();
        this.comparator = comparator;
    }

    public MtxSortedArraySet(Comparator<T> comparator, int startingSize) {
        super(startingSize);
        this.comparator = comparator;
    }

    @SafeVarargs
    public MtxSortedArraySet(Comparator<T> comparator, T... contents) {
        super(contents);
        this.comparator = comparator;
        this.sort();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(T element) {
        if (element == null) {
            return false;
        }

        if (this.contains(element)) {
            return false;
        }

        if (this.nextIndex >= this.setContents.length) {
            Object[] setContentsCopy = new Object[this.setContents.length + STARTING_SIZE];
            System.arraycopy(this.setContents, 0, setContentsCopy, 0, this.setContents.length);
            this.setContents = setContentsCopy;
        }

        for (int i = 0; i < this.nextIndex; i++) {
            if (this.comparator.compare(element, ((T) this.setContents[i])) > 0) {
                System.arraycopy(this.setContents, i, this.setContents, i+1, this.setContents.length - i);
                this.setContents[i] = element;
                this.nextIndex ++;
                return true;
            }
        }
        this.setContents[this.nextIndex] = element;
        this.nextIndex ++;
        return true;
    }

    @Override
    public boolean remove(T element) {
        if (element == null) {
            return false;
        }

        boolean found = false;
        for (int i = 0; i < this.nextIndex; i++) {
            if (element == null && this.setContents[i] == null) {
                found = true;
                break;
            }

            if (this.setContents[i].equals(element)) {
                found = true;
                System.arraycopy(this.setContents, i+1, this.setContents, i, this.setContents.length - i);
                this.nextIndex --;
                break;
            }
        }
        return found;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T removeAndReturn(T element) {
        if (element == null) {
            return null;
        }

        T found = null;
        for (int i = 0; i < this.nextIndex; i++) {
            if (element == null && this.setContents[i] == null) {
                break;
            }

            if (this.setContents[i].equals(element)) {
                found = (T) this.setContents[i];
                System.arraycopy(this.setContents, i+1, this.setContents, i, this.setContents.length - i);
                this.nextIndex --;
                break;
            }
        }
        return found;
    }

    @SuppressWarnings("unchecked")
    protected void sort() {
        if (this.isEmpty()) {
            return;
        }

        mergeSort(((T[]) this.setContents), this.nextIndex, this.comparator);
    }

    @SuppressWarnings("unchecked")
    private static <C> void mergeSort(C[] array, int length, Comparator<C> comparator) {
        if (length < 2) {
            return;
        }
        int mid = length / 2;
        C[] l = (C[]) new Object[mid];
        C[] r = (C[]) new Object[length - mid];

        System.arraycopy(array, 0, l, 0, mid);
        if (length - mid >= 0) {
            System.arraycopy(array, mid, r, 0, length - mid);
        }

        mergeSort(l, mid, comparator);
        mergeSort(r, length - mid, comparator);

        merge(array, l, r, mid, length - mid, comparator);
    }

    private static <C> void merge(C[] a, C[] l, C[] r, int left, int right, Comparator<C> comparator) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
//            if (l[i] <= r[j]) {
            if (comparator.compare(l[i], r[j]) <= 0) {
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
}
