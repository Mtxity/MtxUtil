package com.edavalos.mtx.util.set;

import java.util.TreeMap;

/**
 * Unordered implementation of MtxSet backed by a treemap.
 */
public class MtxMapSet<T> implements MtxSet<T> {
    protected final TreeMap<Integer, T> setContents;

    public MtxMapSet() {
        this.setContents = new TreeMap<>();
    }

    public MtxMapSet(T... contents) {
        this.setContents = new TreeMap<>();
        for (T content : contents) {
            this.add(content);
        }
    }

    /**
     * @return size/length of this set
     */
    @Override
    public int size() {
        return this.setContents.size();
    }

    /**
     * @return true if this set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.setContents.isEmpty();
    }

    /**
     * @return true if this set contains the specified element, false otherwise
     */
    @Override
    public boolean contains(T element) {
        int hashCode = this.getHashCode(element);
        return this.setContents.containsKey(hashCode);
    }

    /**
     * @return an array of this set
     */
    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray() {
        return (T[]) this.setContents.values().toArray();
    }

    /**
     * Adds an element to this set. If it already exists in the set, nothing happens.
     *
     * @param element
     * @return true if element was added to the set, false if it was already in it.
     */
    @Override
    public boolean add(T element) {
        int hashCode = this.getHashCode(element);
        if (this.setContents.containsKey(hashCode)) {
            return false;
        }

        this.setContents.put(hashCode, element);
        return true;
    }

    /**
     * Removes an element from this set
     *
     * @param element
     * @return true if element was found (and removed), false if element was not found.
     */
    @Override
    public boolean remove(T element) {
        int hashCode = this.getHashCode(element);
        if (this.setContents.containsKey(hashCode)) {
            this.setContents.remove(hashCode);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an element from this set and returns it. If the given argument is null then this will have no effect.
     *
     * @return {@code T} if element was found (and removed), null if element was not found.
     */
    @SuppressWarnings("unchecked")
    public T removeAndReturn(T element) {
        if (element == null) {
            return null;
        }

        if (!this.setContents.containsValue(element)) {
            return null;
        }

        return this.setContents.remove(this.getHashCode(element));
    }

    /**
     * Removes everything from this set
     */
    @Override
    public void clear() {
        this.setContents.clear();
    }

    protected int getHashCode(T element) {
        return element.hashCode();
    }
}
