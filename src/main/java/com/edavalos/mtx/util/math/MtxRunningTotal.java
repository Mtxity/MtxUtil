package com.edavalos.mtx.util.math;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MtxRunningTotal {
    private enum Property {
        AVERAGE, MEDIAN, MIN, MAX, SUM, RANGE;
    }

    private static final int DEFAULT_LIMIT = 100;
    private static final int MAX_LIMIT = 100000;
    private static final int DEFAULT_LAST = 10;

    private final List<Integer> integerList;
    private final int limit;

    public MtxRunningTotal() {
        this.integerList = new ArrayList<>();
        this.limit = DEFAULT_LIMIT;
    }

    public MtxRunningTotal(int limit) {
        this.integerList = new ArrayList<>();
        this.limit = Math.min(limit, MAX_LIMIT);
    }

    public MtxRunningTotal(Iterable<Integer> values) {
        List<Integer> list = new LinkedList<>();
        for (Integer value : values) {
            list.add(value);
        }
        int size = Math.min(list.size(), MAX_LIMIT);
        this.integerList = new ArrayList<>();
        this.limit = size;

        for (int i = list.size() - size; i < list.size(); i++) {
            this.integerList.add(list.get(i));
        }
    }

    public void add(int value) {
        if (this.integerList.size() >= limit) {
            this.integerList.remove(0);
        }

        this.integerList.add(value);
    }

    public void add(Iterable<Integer> values) {
        for (Integer value : values) {
            this.add(value);
        }
    }

    public boolean remove(int value) {
        return integerList.remove(Integer.valueOf(value));
    }

    public void removeLast() {
        if (integerList.isEmpty()) {
            return;
        }
        integerList.remove(integerList.size() - 1);
    }

    public void removeLast(int count) {
        if (count >= integerList.size()) {
            integerList.clear();
        } else {
            for (int i = 0; i < count; i++) {
                integerList.remove(integerList.size() - 1);
            }
        }
    }

    public int length() {
        return this.integerList.size();
    }

    public int limit() {
        return this.limit;
    }

    public double getAvg(int lastNumbersToUse) {
        return this.calculate(Property.AVERAGE, lastNumbersToUse);
    }

    public double getAvg() {
        return this.getAvg(DEFAULT_LAST);
    }

    public double getMedian(int lastNumbersToUse) {
        return this.calculate(Property.MEDIAN, lastNumbersToUse);
    }

    public double getMedian() {
        return this.getMedian(DEFAULT_LAST);
    }

    public double getMax(int lastNumbersToUse) {
        return this.calculate(Property.MAX, lastNumbersToUse);
    }

    public double getMax() {
        return this.getMax(DEFAULT_LAST);
    }

    public double getMin(int lastNumbersToUse) {
        return this.calculate(Property.MIN, lastNumbersToUse);
    }

    public double getMin() {
        return this.getMin(DEFAULT_LAST);
    }

    public int getSum(int lastNumbersToUse) {
        return (int) this.calculate(Property.SUM, lastNumbersToUse);
    }

    public int getSum() {
        return this.getSum(DEFAULT_LAST);
    }

    public int getRange(int lastNumbersToUse) {
        return (int) this.calculate(Property.RANGE, lastNumbersToUse);
    }

    public int getRange() {
        return this.getRange(DEFAULT_LAST);
    }

    private double calculate(Property property, int sampleSize) {
        if (sampleSize > this.integerList.size()) {
            sampleSize = this.integerList.size();
        }

        if (sampleSize == 0) {
            return 0.0;
        }

        return switch (property) {
            case AVERAGE -> {
                int runningTotal = 0;
                for (int i = this.integerList.size(); i > this.integerList.size() - sampleSize; i--) {
                    runningTotal += this.integerList.get(i - 1);
                }
                yield runningTotal / (double) sampleSize;
            }

            case MEDIAN -> {
                List<Integer> runningTotal = new ArrayList<>();
                for (int i = this.integerList.size(); i > this.integerList.size() - sampleSize; i--) {
                    runningTotal.add(this.integerList.get(i - 1));
                }

                int middleIdx = runningTotal.size() / 2;
                if (sampleSize % 2 == 0) {
                    // If val is even, average the two middle values
                    yield (runningTotal.get(middleIdx - 1) + runningTotal.get(middleIdx)) / 2.0;
                } else {
                    // If val is odd, return the middle value
                    yield runningTotal.get(middleIdx);
                }
            }

            case MIN -> {
                int min = Integer.MAX_VALUE;
                for (int i = this.integerList.size(); i > this.integerList.size() - sampleSize; i--) {
                    if (min > this.integerList.get(i - 1)) {
                        min = this.integerList.get(i - 1);
                    }
                }
                yield min;
            }

            case MAX -> {
                int max = 0;
                for (int i = this.integerList.size(); i > this.integerList.size() - sampleSize; i--) {
                    if (max < this.integerList.get(i - 1)) {
                        max = this.integerList.get(i - 1);
                    }
                }
                yield max;
            }

            case SUM -> {
                int sum = 0;
                for (int i = this.integerList.size(); i > this.integerList.size() - sampleSize; i--) {
                    sum += this.integerList.get(i - 1);
                }
                yield sum;
            }

            case RANGE -> {
                double min = this.calculate(Property.MIN, sampleSize);
                double max = this.calculate(Property.MAX, sampleSize);
                yield max - min;
            }
        };
    }
}
