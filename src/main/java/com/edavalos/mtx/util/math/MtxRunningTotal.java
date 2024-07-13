package com.edavalos.mtx.util.math;

import java.util.ArrayList;
import java.util.List;

public class MtxRunningTotal {
    private enum Property {
        AVERAGE, MEDIAN, MIN, MAX, SUM;
    }

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
        return this.calculate(Property.AVERAGE, lastNumbersToUse);
    }

    public double getMedian(int lastNumbersToUse) {
        return this.calculate(Property.MEDIAN, lastNumbersToUse);
    }

    public double getMax(int lastNumbersToUse) {
        return this.calculate(Property.MAX, lastNumbersToUse);
    }

    public double getMin(int lastNumbersToUse) {
        return this.calculate(Property.MIN, lastNumbersToUse);
    }

    private double calculate(Property property, int sampleSize) {
        if (sampleSize > this.integerList.size()) {
            sampleSize = this.integerList.size();
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
        };
    }
}
