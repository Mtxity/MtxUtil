package com.edavalos.mtx.util.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class MtxMath {
    private static final int DEFAULT_SIZE = 8;
    public static final int POSITIVE_INFINITY = (int) Double.POSITIVE_INFINITY;
    public static final int NEGATIVE_INFINITY = (int) Double.NEGATIVE_INFINITY;

    public static class MtxFactorial implements Iterable<Integer> {
        private final int iteratorLength;

        public MtxFactorial(int size) {
            this.iteratorLength = size;
        }

        public MtxFactorial() {
            this(DEFAULT_SIZE);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                private int iterations = 0;

                @Override
                public boolean hasNext() {
                    return this.iterations <= iteratorLength;
                }

                @Override
                public Integer next() {
                    long currentVal = factorial(this.iterations);
                    this.iterations++;
                    return ((int) currentVal);
                }
            };
        }

        public static long factorial(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
            }

            long factorial = 1;
            for (int i = 2; i <= n; i++) {
                factorial *= i;
            }

            return factorial;
        }
    }

    public static class MtxFibonacci implements Iterable<Integer> {
        private final int iteratorLength;

        public MtxFibonacci(int size) {
            this.iteratorLength = size;
        }

        public MtxFibonacci() {
            this(DEFAULT_SIZE);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                private int iterations = 1;

                @Override
                public boolean hasNext() {
                    return this.iterations <= iteratorLength;
                }

                @Override
                public Integer next() {
                    int currentVal = fibonacci(this.iterations);
                    this.iterations++;
                    return currentVal;
                }
            };
        }

        public static int fibonacci(int n) {
            if (n <= 0) {
                throw new IllegalArgumentException("Invalid input. Fibonacci sequence starts from index 1.");
            }

            if (n == 1 || n == 2) {
                return 1;
            }

            int fib1 = 1;
            int fib2 = 1;
            int fibonacci = 0;

            for (int i = 3; i <= n; i++) {
                fibonacci = fib1 + fib2;
                fib1 = fib2;
                fib2 = fibonacci;
            }

            return fibonacci;
        }
    }

    public static class MtxStats {
        private final ArrayList<Double> contents;

        public MtxStats() {
            this.contents = new ArrayList<>();
        }

        public MtxStats(double[] values) {
            this();
            for (double value : values) {
                this.contents.add(value);
            }
        }

        public MtxStats(int[] values) {
            this();
            for (int value : values) {
                this.contents.add((double) value);
            }
        }

        public boolean clearContents() {
            boolean isEmpty = this.contents.isEmpty();
            this.contents.clear();
            return !isEmpty;
        }

        public void add(double value) {
            this.contents.add(value);
        }

        public void add(int value) {
            this.contents.add((double) value);
        }

        // Source: https://www.baeldung.com/java-calculate-standard-deviation#calculate-the-standard-deviation
        public double getStandardDeviation() {
            double mean = this.getMean();
            double stdDev = 0.0;
            for (double j : this.contents) {
                stdDev += Math.pow(j - mean, 2);
            }

            return Math.sqrt(stdDev / this.contents.size());
        }

        public double getMean() {
            return this.getTotal() / this.contents.size();
        }

        public double getMode() {
            Map<Double, Integer> frequencyMap = new HashMap<>();
            for (double i : this.contents) {
                frequencyMap.put(i, frequencyMap.getOrDefault(i, 0) + 1);
            }

            double mode = 0.0;
            int maxFrequency = 0;

            for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
                double number = entry.getKey();
                int frequency = entry.getValue();

                if (frequency > maxFrequency) {
                    mode = number;
                    maxFrequency = frequency;
                }
            }

            return mode;
        }

        private double getTotal() {
            double sum = 0.0;
            for (double i : this.contents) {
                sum += i;
            }
            return sum;
        }
    }

    public static boolean isPrime(int i) {
        if (i <= 1) {
            return false;
        }

        for (int j = 2; j < i; j++) {
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }

    public static double[] quadraticFormulaSolver(int a, int b, int c) {
        double[] x = new double[2];

        double rootOfDeterminant = Math.sqrt(Math.pow(b, 2) - (4 * a * c));
        double positiveDeterminant = (-1 * b) + rootOfDeterminant;
        double negativeDeterminant = (-1 * b) - rootOfDeterminant;

        x[0] = positiveDeterminant / (2 * a);
        x[1] = negativeDeterminant / (2 * a);
        return x;
    }
}
