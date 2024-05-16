package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.BeforeEach;
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
    class ConstantsTests {

        // Unit test for ensuring accuracy of utility method used in this testing suite
        @Test
        public void testTruncate() {
            assertEquals("1.12", String.valueOf(truncate(1.1234567, 2)));
            assertEquals("1.1234", String.valueOf(truncate(1.1234567, 4)));
            assertEquals("1.12345", String.valueOf(truncate(1.1234567, 5)));
        }

        private double truncate(double input, int digits) {
            int flattener = Integer.parseInt("1" + "0".repeat(digits));
            return Math.floor(input * flattener) / flattener;
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

        @Nested
        class FactorialConstructorTests {

            @Test
            public void testConstructor_default() {
                final int expectedSize = 8;
                int actualSize = -1;
                for (int i : new MtxMath.MtxFactorial()) {
                    actualSize ++;
                }
                assertEquals(expectedSize, actualSize);
            }

            @Test
            public void testConstructor_positiveSize() {
                final int expectedSize = 12;
                int actualSize = -1;
                for (int i : new MtxMath.MtxFactorial(expectedSize)) {
                    actualSize ++;
                }
                assertEquals(expectedSize, actualSize);
            }

            @Test
            public void testConstructor_negativeSize() {
                String errorMsg = "Factorial is not defined for negative numbers.";
                int sampleNegativeInteger = -4;

                Throwable error = assertThrows(IllegalArgumentException.class, () -> new MtxMath.MtxFactorial(sampleNegativeInteger));
                assertEquals(errorMsg, error.getMessage());
            }
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

        @Nested
        class FibonacciConstructorTests {

            @Test
            public void testConstructor_default() {
                final int expectedSize = 8;
                int actualSize = 0;
                for (int i : new MtxMath.MtxFibonacci()) {
                    actualSize ++;
                }
                assertEquals(expectedSize, actualSize);
            }

            @Test
            public void testConstructor_positiveSize() {
                final int expectedSize = 12;
                int actualSize = 0;
                for (int i : new MtxMath.MtxFibonacci(expectedSize)) {
                    actualSize ++;
                }
                assertEquals(expectedSize, actualSize);
            }

            @Test
            public void testConstructor_negativeSize() {
                String errorMsg = "Invalid input. Fibonacci sequence starts from index 1.";
                int sampleNegativeInteger = -4;

                Throwable error = assertThrows(IllegalArgumentException.class, () -> new MtxMath.MtxFibonacci(sampleNegativeInteger));
                assertEquals(errorMsg, error.getMessage());
            }
        }
    }

    @Nested
    class CatalanTests {
        private static final List<Integer> SAMPLE_CATALANS = new ArrayList<>() {
            {
                add(1);
                add(2);
                add(5);
                add(14);
                add(42);
                add(132);
                add(429);
                add(1430);
            }
        };

        @Test
        public void testIterator() {
            List<Integer> actualElements = new ArrayList<>();
            for (int i : new MtxMath.MtxCatalan()) {
                actualElements.add(i);
            }

            assertEquals(SAMPLE_CATALANS, actualElements);
        }

        @Test
        public void testCatalan() {
            for (int i = 1; i < SAMPLE_CATALANS.size(); i++) {
                assertEquals(Long.valueOf(SAMPLE_CATALANS.get(i - 1)),  MtxMath.MtxCatalan.catalan(i));
            }
        }

        @Test
        public void testCatalan_negative() {
            String errorMsg = "No Catalan number exists for negative numbers.";
            int sampleNegativeInteger = -46;

            Throwable error = assertThrows(IllegalArgumentException.class, () -> MtxMath.MtxCatalan.catalan(sampleNegativeInteger));
            assertEquals(errorMsg, error.getMessage());
        }

        @Nested
        class CatalanConstructorTests {

            @Test
            public void testConstructor_default() {
                final int expectedSize = 8;
                int actualSize = 0;
                for (int i : new MtxMath.MtxCatalan()) {
                    actualSize ++;
                }
                assertEquals(expectedSize, actualSize);
            }

            @Test
            public void testConstructor_positiveSize() {
                final int expectedSize = 12;
                int actualSize = 0;
                for (int i : new MtxMath.MtxCatalan(expectedSize)) {
                    actualSize ++;
                }
                assertEquals(expectedSize, actualSize);
            }

            @Test
            public void testConstructor_negativeSize() {
                String errorMsg = "No Catalan number exists for negative numbers.";
                int sampleNegativeInteger = -4;

                Throwable error = assertThrows(IllegalArgumentException.class, () -> new MtxMath.MtxCatalan(sampleNegativeInteger));
                assertEquals(errorMsg, error.getMessage());
            }
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

        @Test
        public void testIsPrime_negativeNumber() {
            assertFalse(MtxMath.isPrime(-1));
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

    @Nested
    class StatsTests {
        private static final double[] SAMPLE_DATA_SET = {
                2.5, 3.0, 6.8, 7.2, 9.8, 10.1,
                12.0, 12.0, 12.1, 13.4, 13.7, 15,
                15.12, 15.16, 16.0, 16.1, 16.2, 16.4
        };
        private MtxMath.MtxStats mtxStats;

        @Nested
        class StatsConstructorTests {
            private static final List<Double> SAMPLE_DOUBLE_LIST = new ArrayList<>() {{
                add(1.0);
                add(2.0);
                add(3.0);
            }};

            @Test
            public void testConstructor_default() {
                mtxStats = new MtxMath.MtxStats();
                assertTrue(mtxStats.getContents().isEmpty());
            }

            @Test
            public void testConstructor_doubleArray() {
                double[] sampleDoubles = new double[SAMPLE_DOUBLE_LIST.size()];
                for (int i = 0; i < SAMPLE_DOUBLE_LIST.size(); i++) {
                    sampleDoubles[i] = SAMPLE_DOUBLE_LIST.get(i);
                }

                mtxStats = new MtxMath.MtxStats(sampleDoubles);
                assertEquals(SAMPLE_DOUBLE_LIST, mtxStats.getContents());
            }

            @Test
            public void testConstructor_intArray() {
                int[] sampleDoubles = new int[SAMPLE_DOUBLE_LIST.size()];
                for (int i = 0; i < SAMPLE_DOUBLE_LIST.size(); i++) {
                    int x = SAMPLE_DOUBLE_LIST.get(i).intValue();
                    sampleDoubles[i] = x;
                }

                mtxStats = new MtxMath.MtxStats(sampleDoubles);
                assertEquals(SAMPLE_DOUBLE_LIST, mtxStats.getContents());
            }
        }

        @Test
        public void testClearContents() {
            mtxStats = new MtxMath.MtxStats(new int[]{5, 6, 7});
            assertTrue(mtxStats.clearContents());
            assertFalse(mtxStats.clearContents());
            assertTrue(mtxStats.getContents().isEmpty());
        }

        @Nested
        class AddTests {
            @BeforeEach
            public void setUp() {
                mtxStats = new MtxMath.MtxStats(new int[]{5, 6, 7});
            }

            @Test
            public void testAdd_double() {
                double newVal = 2.3;
                mtxStats.add(newVal);
                assertTrue(mtxStats.getContents().contains(newVal));
                assertEquals(4, mtxStats.getContents().size());
            }

            @Test
            public void testAdd_int() {
                int newVal = 5;
                mtxStats.add(newVal);
                assertTrue(mtxStats.getContents().contains((double) newVal));
                assertEquals(4, mtxStats.getContents().size());
            }
        }

        @BeforeEach
        public void setUp() {
            mtxStats = new MtxMath.MtxStats(SAMPLE_DATA_SET);
        }

        @Test
        public void testGetStandardDeviation() {
            assertEquals(4.2980628711600355, mtxStats.getStandardDeviation());
        }

        @Test
        public void testGetMean() {
            // Actual result should be 11.81
            assertEquals(	11.809999999999999, mtxStats.getMean());
        }

        @Test
        public void testGetMedian_even() {
            assertEquals(12.75, mtxStats.getMedian());
        }

        @Test
        public void testGetMedian_odd() {
            mtxStats.add(17);
            assertEquals(13.4, mtxStats.getMedian());
        }

        @Test
        public void testGetMode() {
            assertEquals(12.0, mtxStats.getMode());
        }

        @Test
        public void testGetVariance() {
            assertEquals(18.473344444444447, mtxStats.getVariance());
        }
    }

    @Test
    public void testTwoDimensionArrayCopy() {
        int[][] source = new int[][]{
                new int[]{1, 2, 3},
                new int[]{4, 5, 6},
                new int[]{7, 8, 9}
        };
        int[][] dest = new int[3][3];

        MtxMath.twoDimensionArrayCopy(source, dest);

        for (int i = 0; i < source.length; i++) {
            assertArrayEquals(source[i], dest[i]);
        }
    }
}
