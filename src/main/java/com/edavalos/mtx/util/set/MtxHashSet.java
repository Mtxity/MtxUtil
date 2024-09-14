package com.edavalos.mtx.util.set;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Unordered implementation of MtxSet backed by a hashmap.
 */
// Based on https://javaconceptoftheday.com/how-hashset-works-internally-in-java/
public final class MtxHashSet<T> implements MtxSet<T> {
    private static final Object PRESENT = new Object();

    private final Class<T> type;
    private HashMap<T, Object> content;

    public MtxHashSet(Class<T> type) {
        this.content = new HashMap<>();
        this.type = type;
    }

    @SafeVarargs
    public MtxHashSet(Class<T> type, T... initialContents) {
        this(type);
        for (T element : initialContents) {
            if (content == null) {
                continue;
            }

            this.content.put(element, PRESENT);
        }
    }

    @Override
    public int size() {
        return this.content.keySet().size();
    }

    @Override
    public boolean isEmpty() {
        return this.content.keySet().isEmpty();
    }

    @Override
    public boolean contains(T element) {
        return this.content.containsKey(element);
    }

    @Override
    public T[] toArray() {
        T[] newArray = (T[]) Array.newInstance(this.type, this.size());
        int idx = 0;
        for (T key : this.content.keySet()) {
            newArray[idx++] = key;
        }
        return newArray;
    }

    /**
     * @return a set containing the elements in this set
     */
    @Override
    public Set<T> toSet() {
        return new HashSet<>(Arrays.asList(this.toArray()));
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }

        return this.content.put(element, PRESENT) == null;
    }

    /**
     * Adds all given elements to this set. If an element already exists in the set, it is skipped.
     * @return true if all elements were added to the set, false if at least one was already in it.
     */
    @SafeVarargs
    public final boolean add(T... elements) {
        boolean addedAll = true;
        for (T element : elements) {
            addedAll = addedAll & this.add(element);
        }
        return addedAll;
    }

    @Override
    public boolean remove(T element) {
        return this.content.remove(element) == PRESENT;
    }

    @Override
    public void clear() {
        this.content = new HashMap<>();
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (T element : this.content.keySet()) {
            int elementHashCode = element == null ? 0 : element.hashCode();
            hashCode += elementHashCode;
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        return this.equalsTo(o);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("(");
        for (T element : this.content.keySet()) {
            string.append(element.toString()).append(", ");
        }

        string.append(")");
        return string.toString().replace(", )", ")");
    }
}
