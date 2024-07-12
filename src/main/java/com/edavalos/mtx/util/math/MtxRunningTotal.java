package com.edavalos.mtx.util.math;

import java.util.ArrayList;
import java.util.List;

public class MtxRunningTotal {
    private static final int DEFAULT_LIMIT = 100;

    private final List<Integer> integerList;
    private final int limit;

    public MtxRunningTotal() {
        this.integerList = new ArrayList<>();
        this.limit = DEFAULT_LIMIT;
    }

    public void add(int value) {
        if (integerList.size() >= limit) {
            integerList.remove(0);
        }

        integerList.add(value);
    }

    public boolean remove(int value) {
        return integerList.remove(Integer.valueOf(value));
    }

    public void removeLast() {
        integerList.remove(integerList.size() - 1);
    }
}
