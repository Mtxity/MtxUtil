package com.edavalos.mtx.util.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public final class MtxHashList<T> {
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

    public void add(T element) {
        this.content.put(this.nextSpot, element);
        this.nextSpot ++;
    }

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

    public int size() {
        return this.content.size() - this.holes.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }
}
