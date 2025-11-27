package com.edavalos.mtx.util.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public final class MtxHashList<T> implements MtxList<T>, Iterable<T> {
    private HashMap<Integer, T> content;
    private List<Integer> holes; // Keys to elements that were removed
    private int nextSpot;

    public MtxHashList() {
        this.content = new HashMap<>();
        this.holes = new ArrayList<>();
        this.nextSpot = 0;
    }

    public MtxHashList(T[] initialContents) {
        this.content = new HashMap<>();
        this.holes = new ArrayList<>();

        int idx = 0;
        for (T element : initialContents) {
            this.content.put(idx, element);
            idx ++;
        }
        this.nextSpot = idx;
    }

    @Override
    public void add(T element) {
        this.content.put(this.nextSpot, element);
        this.nextSpot ++;
    }

    @Override
    public boolean remove(T element) {
        if (!this.content.containsValue(element)) {
            return false;
        }

        for (int key : new TreeSet<>(this.content.keySet())) {
            if (this.holes.contains(key)) {
                continue;
            }

            if (this.content.get(key).equals(element)) {
                this.holes.add(key);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");

        for (int key : new TreeSet<>(this.content.keySet())) {
            if (this.holes.contains(key)) {
                continue;
            }

            T element = this.content.get(key);
            string.append(element.toString()).append(", ");
        }

        string.append("]");
        return string.toString().replace(", ]", "]");
    }

    @Override
    public int size() {
        return this.content.size() - this.holes.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(T element) {
        return this.countOccurrences(element) > 0;
    }

    @Override
    public int countOccurrences(T element) {
        int timesFound = 0;
        for (T thing : this.content.values()) {
            if (thing.equals(element)) {
                timesFound ++;
            }
        }

        for (int key : this.holes) {
            if (this.content.get(key).equals(element)) {
                timesFound --;
            }
        }

        return Math.max(timesFound, 0);
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        for (int key : this.holes) {
            if (key <= index) {
                index ++;
            }
        }

        return this.content.get(index);
    }

    @Override
    public boolean equals(Object o) {
        return this.equalsTo(o);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int key : new TreeSet<>(this.content.keySet())) {
            if (this.holes.contains(key)) {
                continue;
            }

            T element = this.content.get(key);
            int elementHashCode = element == null ? 0 : element.hashCode();
            hashCode = (31 * hashCode) + elementHashCode;
        }
        return hashCode;
    }

    @Override
    public int indexOf(T element) {
        int idx = 0;
        for (int key : new TreeSet<>(this.content.keySet())) {
            if (this.holes.contains(key)) {
                continue;
            }

            if (element.equals(this.content.get(key))) {
                return idx;
            }
            idx ++;
        }
        return -1;
    }

    @Override
    public void clear() {
        this.content = new HashMap<>();
        this.holes = new ArrayList<>();
        this.nextSpot = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final List<T> elements = prepareIterator();
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return this.idx < size();
            }

            @Override
            public T next() {
                return elements.get(this.idx ++);
            }

            private List<T> prepareIterator() {
                List<T> elements = new ArrayList<>();

                for (int key : new TreeSet<>(content.keySet())) {
                    if (holes.contains(key)) {
                        continue;
                    }

                    elements.add(content.get(key));
                }

                return elements;
            }
        };
    }

    @Override
    public MtxList<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException(fromIndex);
        }
        if (toIndex > this.size()) {
            throw new IndexOutOfBoundsException(toIndex);
        }

        MtxList<T> newList = new MtxHashList<>();
        int idx = 0;
        for (T element : this) {
            if (idx < fromIndex) {
                idx ++;
                continue;
            }
            if (idx >= toIndex) {
                break;
            }

            newList.add(element);
            idx ++;
        }
        return newList;
    }

    @Override
    public T[] toArray() {
        List<T> listToUse = new ArrayList<>();

        for (int key : new TreeSet<>(this.content.keySet())) {
            if (this.holes.contains(key)) {
                continue;
            }

            T element = this.content.get(key);
            listToUse.add(element);
        }

        return listToUse.toArray(((T[]) new Object[0]));
    }

    @Override
    public T removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }
        T[] tempArray = this.toArray();
        this.clear();
        int idx = 0;
        for (T element : tempArray) {
            this.content.put(idx, element);
            idx ++;
        }
        this.nextSpot = idx;

        T element = this.content.get(index);
        this.holes.add(index);
        return element;
    }

    @Override
    public T set(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        for (int key : this.holes) {
            if (key <= index) {
                index ++;
            }
        }

        // HashMap.put() returns the previous value
        return this.content.put(index, element);
    }

    @Override
    public void sort(Comparator<T> comparator) {
        HashMap<Integer, T> sortedMap = new HashMap<>();
        int idx = 0;
        for (T value : this.content.values().stream().sorted(comparator).toList()) {
            sortedMap.put(idx, value);
            idx ++;
        }

        this.holes = new ArrayList<>();
        this.content = sortedMap;
        this.nextSpot = idx;
    }

    @Override
    public boolean removeDuplicates() {
        List<Object> newContents = new ArrayList<>();
        boolean foundDuplicate = false;

        for (int key : new TreeSet<>(this.content.keySet())) {
            if (this.holes.contains(key)) {
                continue;
            }

            if (newContents.contains(this.content.get(key))) {
                foundDuplicate = true;
                this.holes.add(key);
            } else {
                newContents.add(this.content.get(key));
            }
        }
        return foundDuplicate;
    }

    @Override
    public void rotateLeft(int times) {
        int n = this.size();
        if (n <= 1) {
            return;
        }

        int k = times % n;
        if (k < 0) {
            k += n;
        }
        if (k == 0) {
            return;
        }

        List<T> elements = new ArrayList<>(n);
        for (T e : this) {
            elements.add(e);
        }

        this.clear();
        for (int i = 0; i < n; i++) {
            this.add(elements.get((i + k) % n));
        }
    }

    @Override
    public void rotateRight(int times) {
        int n = this.size();
        if (n <= 1) {
            return;
        }

        int k = times % n;
        if (k < 0) {
            k += n;
        }
        if (k == 0) {
            return;
        }

        int left = n - k;

        List<T> elements = new ArrayList<>(n);
        for (T e : this) {
            elements.add(e);
        }

        this.clear();
        for (int i = 0; i < n; i++) {
            this.add(elements.get((i + left) % n));
        }
    }

    @Override
    public void reverse() {
        int n = this.size();
        if (n <= 1) {
            return;
        }

        List<T> elements = new ArrayList<>(n);
        for (T e : this) {
            elements.add(e);
        }

        this.clear();
        for (int i = n - 1; i >= 0; i--) {
            this.add(elements.get(i));
        }
    }

    public boolean isPalindrome() {
        int n = this.size();
        if (n <= 1) {
            return true;
        }

        List<T> elements = new ArrayList<>(n);
        for (int key : new TreeSet<>(this.content.keySet())) {
            if (this.holes.contains(key)) {
                continue;
            }
            elements.add(this.content.get(key));
        }

        int i = 0;
        int j = n - 1;
        while (i < j) {
            T left = elements.get(i);
            T right = elements.get(j);
            if (!Objects.equals(left, right)) {
                return false;
            }

            i ++;
            j --;
        }
        return true;
    }
}
