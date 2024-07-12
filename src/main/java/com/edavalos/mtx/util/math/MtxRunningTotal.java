package com.edavalos.mtx.util.math;

import java.util.ArrayList;
import java.util.List;

public class MtxRunningTotal {
    private static final int DEFAULT_LIMIT = 100;
    private static final int MAX_LIMIT = 100000;

    private final List<Integer> integerList;
    private final int limit;

    public MtxRunningTotal() {
        this.integerList = new ArrayList<>();
        this.limit = DEFAULT_LIMIT;
    }

    public MtxRunningTotal(int limit) {
        this.integerList = new ArrayList<>();
        if (limit > MAX_LIMIT) {
            this.limit = MAX_LIMIT;
        } else {
            this.limit = limit;
        }
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

    public double getAvg(int lastNumbersToUse) {
        if (lastNumbersToUse > this.integerList.size()) {
            lastNumbersToUse = this.integerList.size();
        }

        int runningTotal = 0;
        for (int i = this.integerList.size(); i >= this.integerList.size() - lastNumbersToUse; i--) {
            runningTotal += this.integerList.get(i - 1);
        }
        return runningTotal / (double) lastNumbersToUse;
    }
}
