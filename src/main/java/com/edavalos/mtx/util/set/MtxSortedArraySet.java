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

    private void sort() {
        // @TODO: Implement this
    }

    private static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    private static void merge(int[] a, int[] l, int[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
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
