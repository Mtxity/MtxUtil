package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MtxMathTest {
    @Nested
    class InfinityTests {
        @Test
        public void testPositiveInfinity() {
            assertEquals(((int) Double.POSITIVE_INFINITY), MtxMath.POSITIVE_INFINITY);
        }

        @Test
        public void testNegativeInfinity() {
            assertEquals(((int) Double.NEGATIVE_INFINITY), MtxMath.NEGATIVE_INFINITY);
        }
    }

    @Nested
    class FactorialTests {
        private static final List<Integer> SAMPLE_FACTORIALS = new ArrayList<>() {
            {
                add(1);
                add(1);
                add(2);
                add(6);
                add(24);
                add(120);
                add(720);
                add(5040);
                add(40320);
            }
        };

        @Test
        public void testIterator() {
            List<Integer> actualElements = new ArrayList<>();
            for (int i : new MtxMath.MtxFactorial()) {
                actualElements.add(i);
            }

            assertEquals(SAMPLE_FACTORIALS, actualElements);
        }

        @Test
        public void testFactorial() {
            for (int i = 0; i < SAMPLE_FACTORIALS.size(); i++) {
                assertEquals(Long.valueOf(SAMPLE_FACTORIALS.get(i)),  MtxMath.MtxFactorial.factorial(i));
            }
        }

        @Test
        public void testFactorial_negative() {
            String errorMsg = "Factorial is not defined for negative numbers.";
            int sampleNegativeInteger = -46;

            Throwable error = assertThrows(IllegalArgumentException.class, () -> MtxMath.MtxFactorial.factorial(sampleNegativeInteger));
            assertEquals(errorMsg, error.getMessage());
        }
    }

    @Nested
    class FibonacciTests {
        private static final List<Integer> SAMPLE_FIBONACCIS = new ArrayList<>() {
            {
                add(1);
                add(1);
                add(2);
                add(3);
                add(5);
                add(8);
                add(13);
                add(21);
            }
        };

        @Test
        public void testIterator() {
            List<Integer> actualElements = new ArrayList<>();
            for (int i : new MtxMath.MtxFibonacci()) {
                actualElements.add(i);
            }

            assertEquals(SAMPLE_FIBONACCIS, actualElements);
        }

        @Test
        public void testFibonacci() {
            for (int i = 1; i < SAMPLE_FIBONACCIS.size(); i++) {
                assertEquals(Long.valueOf(SAMPLE_FIBONACCIS.get(i - 1)),  MtxMath.MtxFibonacci.fibonacci(i));
            }
        }

        @Test
        public void testFibonacci_negative() {
            String errorMsg = "Invalid input. Fibonacci sequence starts from index 1.";
            int sampleNegativeInteger = -46;

            Throwable error = assertThrows(IllegalArgumentException.class, () -> MtxMath.MtxFibonacci.fibonacci(sampleNegativeInteger));
            assertEquals(errorMsg, error.getMessage());
        }
    }

    @Nested
    public class IsPrimeTests {
        private static final int[] PRIME_NUMBERS = {
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
                31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                73, 79, 83, 89, 97
        };
        private static final int[] COMPOSITE_NUMBERS = {
                4, 6, 8, 9, 10, 12, 14, 15, 16, 18,
                20, 21, 22, 24, 25, 26, 27, 28, 30, 32,
                33, 34, 35, 36, 38, 39,
        };

        @Test
        public void testIsPrime_primeNumbers() {
            for (int i : PRIME_NUMBERS) {
                assertTrue(MtxMath.isPrime(i));
            }
        }

        @Test
        public void testIsPrime_nonPrimeNumbers() {
            for (int i : COMPOSITE_NUMBERS) {
                assertFalse(MtxMath.isPrime(i));
            }
        }
    }

    @Test
    public void testQuadraticFormulaSolver() {
        int a = 6;
        int b = 9;
        int c = 3;
        double[] result = MtxMath.quadraticFormulaSolver(a, b, c);

        assertEquals(2, result.length);
        assertEquals(-0.5, result[0]);
        assertEquals(-1, result[1]);
    }
}
