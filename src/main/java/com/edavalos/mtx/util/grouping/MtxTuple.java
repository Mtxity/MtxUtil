package com.edavalos.mtx.util.grouping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

public class MtxTuple<T> implements Iterable<T> {
    private static final String TUPLE_DELIMITER = " : ";
    private final ArrayList<T> contents;
    private final int size;

    @SafeVarargs
    public MtxTuple(T... contents) {
        this.size = contents.length;
        this.contents = new ArrayList<>(contents.length);
        this.contents.addAll(Arrays.asList(contents));
    }

    public MtxTuple(int size) {
        this.size = size;
        this.contents = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.contents.add(null);
        }
    }

    public int size() {
        return this.size;
    }

    public void setAt(T element, int index) {
        this.contents.set(index, element);
    }

    public T getAt(int index) {
        return this.contents.get(index);
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
                return contents.get(this.idx++);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof MtxTuple<?> otherTuple)) {
            return false;
        }

        if (otherTuple.contents.size() != this.contents.size()) {
            return false;
        }

        boolean equals = true;
        for (int i = 0; i < otherTuple.size(); i++) {
            equals = equals & otherTuple.contents.get(i).equals(this.contents.get(i));
        }
        return equals;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T element : this.contents) {
            s.append(TUPLE_DELIMITER).append(element.toString());
        }
        return "<" + s.toString().replaceFirst(Pattern.quote(TUPLE_DELIMITER), "") + ">";
    }
}
