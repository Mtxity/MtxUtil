package com.edavalos.mtx.util.set;

import java.util.Comparator;

/**
 * Ordered and auto-sorted implementation of MtxArraySet backed by a tree set.
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

    public MtxSortedArraySet(Comparator<T> comparator, T... contents) {
        super(contents);
        this.comparator = comparator;
        this.sort();
    }

    @Override
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

    // @TODO: Make this more efficient by downshifting array instead of swapping with last element
    @Override
    public boolean remove(T element) {
        boolean removed = super.remove(element);
        if (removed) {
            this.sort();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T removeAndReturn(T element) {
        // @TODO: Implement this natively
        if (this.remove(element)) {
            return element;
        } else {
            return null;
        }
    }

    private void sort() {
        // @TODO: Implement this
    }
}
