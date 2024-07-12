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

    public double getMedian(int lastNumbersToUse) {
        if (lastNumbersToUse > this.integerList.size()) {
            lastNumbersToUse = this.integerList.size();
        }

        List<Integer> runningTotal = new ArrayList<>();
        for (int i = this.integerList.size(); i >= this.integerList.size() - lastNumbersToUse; i--) {
            runningTotal.add(this.integerList.get(i - 1));
        }

        int middleIdx = runningTotal.size() / 2;
        if (lastNumbersToUse % 2 == 0) {
            // If val is even, average the two middle values
            return (runningTotal.get(middleIdx - 1) + runningTotal.get(middleIdx)) / 2.0;
        } else {
            // val is odd, return the middle value
            return runningTotal.get(middleIdx);
        }
    }

    public double getMax(int lastNumbersToUse) {
        if (lastNumbersToUse > this.integerList.size()) {
            lastNumbersToUse = this.integerList.size();
        }

        int max = 0;
        for (int i = this.integerList.size(); i >= this.integerList.size() - lastNumbersToUse; i--) {
            if (max < this.integerList.get(i - 1)) {
                max = this.integerList.get(i - 1);
            }
        }
        return max;
    }

    public double getMin(int lastNumbersToUse) {
        if (lastNumbersToUse > this.integerList.size()) {
            lastNumbersToUse = this.integerList.size();
        }

        int min = Integer.MAX_VALUE;
        for (int i = this.integerList.size(); i >= this.integerList.size() - lastNumbersToUse; i--) {
            if (min > this.integerList.get(i - 1)) {
                min = this.integerList.get(i - 1);
            }
        }
        return min;
    }
}
