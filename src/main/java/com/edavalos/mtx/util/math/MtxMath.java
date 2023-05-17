package com.edavalos.mtx.util.math;

import java.util.Iterator;

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
