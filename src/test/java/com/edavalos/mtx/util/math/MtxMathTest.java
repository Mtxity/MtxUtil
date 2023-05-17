package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
