package com.edavalos.mtx.util.math;

import java.util.Iterator;

public final class MtxMath {
    public static final int POSITIVE_INFINITY = (int) Double.POSITIVE_INFINITY;
    public static final int NEGATIVE_INFINITY = (int) Double.NEGATIVE_INFINITY;

    public static class MtxFactorial implements Iterable<Integer> {
        private static final int DEFAULT_SIZE = 8;
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
}
