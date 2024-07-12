package com.edavalos.mtx.util.math;

import java.util.ArrayList;
import java.util.List;

public class MtxRunningTotal {
    private final List<Integer> integerList;

    public MtxRunningTotal() {
        this.integerList = new ArrayList<>();
    }

    public void add(int value) {
        integerList.add(value);
    }

    public boolean remove(int value) {
        return integerList.remove(Integer.valueOf(value));
    }

    public void removeLast() {
        integerList.remove(integerList.size() - 1);
    }
}
