package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        public void testRound() {
            assertEquals("1.12", String.valueOf(this.round(1.1234567, 2)));
            assertEquals("1.1235", String.valueOf(this.round(1.1234567, 4)));
            assertEquals("1.12346", String.valueOf(this.round(1.1234567, 5)));
        }

        @Test
        public void testPi() {
            assertEquals(Math.PI, this.round(MtxMath.PI, 15));
        }

        @Test
        public void testTau() {
            assertEquals(Math.PI * 2, this.round(MtxMath.TAU, 15));
        }

        @Test
        public void testE() {
            assertEquals(Math.E, this.round(MtxMath.E, 15));
        }

        @Test
        public void testY() {
            double expectedY = 0.577215664901533;
            assertEquals(expectedY, this.round(MtxMath.Y, 15));
        }

        @Test
        public void testPhi() {
            double expectedPhi = (1 + Math.sqrt(5))/2;
            assertEquals(expectedPhi, this.round(MtxMath.PHI, 15));
        }

        @Test
        public void testLn2() {
            double expectedLn2 = 0.693147180559945;
            assertEquals(expectedLn2, this.round(MtxMath.LN2, 15));
        }

        @Test
        public void testLn3() {
            double expectedLn3 = 1.09861228866811;
            assertEquals(expectedLn3, this.round(MtxMath.LN3, 15));
        }

        private double round(double input, int digits) {
            double flattener = Long.parseLong("1" + "0".repeat(digits));
            return Math.round(input * flattener) / flattener;
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
        private static final double DELTA = 0.001;
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
            assertEquals(4.2980628711600355, mtxStats.getStandardDeviation(), DELTA);
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

    @Nested
    class MinMaxTests {
        private static final int[] SAMPLE_INT_ARRAY = new int[]{50, 100, 150, 10, 120};
        private static final double[] SAMPLE_DOUBLE_ARRAY = new double[]{50.05, 100.1, 10.01, 120.12, -350.35, -20.02};

        @Test
        public void testMin_givenValues() {
            assertEquals(10, MtxMath.min(SAMPLE_INT_ARRAY));
            assertEquals(-350.35, MtxMath.min(SAMPLE_DOUBLE_ARRAY));
        }

        @Test
        public void testMin_givenValues_asList() {
            ArrayList<Integer> ints = new ArrayList<>();
            for (int i : SAMPLE_INT_ARRAY) {
                ints.add(i);
            }
            assertEquals(10, MtxMath.min(ints));

            ArrayList<Double> doubles = new ArrayList<>();
            for (double i : SAMPLE_DOUBLE_ARRAY) {
                doubles.add(i);
            }
            assertEquals(-350.35, MtxMath.minFromList(doubles));
        }

        @Test
        public void testMin_zeroValues() {
            assertEquals(0, MtxMath.min());
        }

        @Test
        public void testMax_givenValues() {
            assertEquals(150, MtxMath.max(SAMPLE_INT_ARRAY));
            assertEquals(120.12, MtxMath.max(SAMPLE_DOUBLE_ARRAY));
        }

        @Test
        public void testMax_givenValues_asList() {
            ArrayList<Integer> ints = new ArrayList<>();
            for (int i : SAMPLE_INT_ARRAY) {
                ints.add(i);
            }
            assertEquals(150, MtxMath.max(ints));

            ArrayList<Double> doubles = new ArrayList<>();
            for (double i : SAMPLE_DOUBLE_ARRAY) {
                doubles.add(i);
            }
            assertEquals(120.12, MtxMath.maxFromList(doubles));
        }

        @Test
        public void testMax_zeroValues() {
            assertEquals(0, MtxMath.max());
        }
    }

    @Nested
    class HasRemainderTest {

        @Test
        public void testHasRemainder_true() {
            assertTrue(MtxMath.hasRemainder(7, 3));
        }

        @Test
        public void testHasRemainder_false() {
            assertFalse(MtxMath.hasRemainder(6, 3));
        }
    }

    @Nested
    class DividesEvenlyEitherWayTest {

        @Test
        public void testDividesEvenlyEitherWay_true() {
            assertTrue(MtxMath.dividesEvenlyEitherWay(4, 12));
            assertTrue(MtxMath.dividesEvenlyEitherWay(12, 4));
        }

        @Test
        public void testDividesEvenlyEitherWay_false() {
            assertFalse(MtxMath.dividesEvenlyEitherWay(5, 12));
            assertFalse(MtxMath.dividesEvenlyEitherWay(12, 5));
        }

        @Test
        public void testDividesEvenlyEitherWay_zero() {
            assertFalse(MtxMath.dividesEvenlyEitherWay(0, 12));
            assertFalse(MtxMath.dividesEvenlyEitherWay(12, 0));
        }
    }

    @Nested
    class GcdTests {

        @Test
        public void testGcd_positive() {
            assertEquals(5, MtxMath.gcd(10, 5));
            assertEquals(5, MtxMath.gcd(5, 10));
            assertEquals(1, MtxMath.gcd(7, 3));
            assertEquals(6, MtxMath.gcd(12, 18));
        }

        @Test
        public void testGcd_zero() {
            assertEquals(12, MtxMath.gcd(0, 12));
            assertEquals(12, MtxMath.gcd(12, 0));
        }

        @Test
        public void testGcd_negative() {
            // Note: This reflects the current implementation's behavior with negative inputs
            assertEquals(5, MtxMath.gcd(-10, 5));
            assertEquals(-5, MtxMath.gcd(10, -5));
        }
    }

    @Nested
    class LcmTests {

        @Test
        void testLcm_sameNumbers() {
            assertEquals(5, MtxMath.lcm(5, 5));
        }

        @Test
        void testLcm_commonMultiples() {
            assertEquals(24, MtxMath.lcm(6, 8));
        }

        @Test
        void testLcm_coPrimeNumbers() {
            assertEquals(35, MtxMath.lcm(5, 7));
        }

        @Test
        void testLcm_oneIsMultipleOfOther() {
            assertEquals(12, MtxMath.lcm(3, 12));
        }

        @Test
        void testLcm_withOne() {
            assertEquals(7, MtxMath.lcm(1, 7));
        }

        @Test
        void testLcm_negativeNumbers() {
            assertEquals(-12, MtxMath.lcm(-3, 12));
            assertEquals(12, MtxMath.lcm(3, -12));
            assertEquals(-12, MtxMath.lcm(-3, -12));
        }

        @Test
        void testZeroAndNumber() {
            assertEquals(0, MtxMath.lcm(0, 5));
        }

        @Test
        void testBothZero() {
            assertThrows(
                    ArithmeticException.class,
                    () -> MtxMath.lcm(0, 0)
            );
        }
    }

    @Nested
    class CoprimeTests {

        @Test
        void testCoprime_TrueForCoPrimeNumbers() {
            assertTrue(MtxMath.coprime(8, 15)); // gcd = 1
        }

        @Test
        void testCoprime_FalseForNonCoPrimeNumbers() {
            assertFalse(MtxMath.coprime(8, 12)); // gcd = 4
        }

        @Test
        void testCoprime_ConsecutiveNumbersAreCoPrime() {
            assertTrue(MtxMath.coprime(14, 15));
        }

        @Test
        void testCoprime_SameNumberGreaterThanOne() {
            assertFalse(MtxMath.coprime(7, 7));
        }

        @Test
        void testCoprime_WithOne() {
            assertTrue(MtxMath.coprime(1, 100));
        }
    }

    @Nested
    class AbsTests {

        @Nested
        class IntAbsTests {

            @Test
            void testAbs_PositiveNumber() {
                assertEquals(7, MtxMath.abs(7));
            }

            @Test
            void testAbs_NegativeNumber() {
                assertEquals(7, MtxMath.abs(-7));
            }

            @Test
            void testAbs_Zero() {
                assertEquals(0, MtxMath.abs(0));
            }
        }

        @Nested
        class DoubleAbsTests {

            @Test
            void testAbs_double_positiveNumber() {
                assertEquals(7.5, MtxMath.abs(7.5));
            }

            @Test
            void testAbs_double_negativeNumber() {
                assertEquals(7.5, MtxMath.abs(-7.5));
            }

            @Test
            void testAbs_double_zero() {
                assertEquals(0.0, MtxMath.abs(0.0));
            }
        }
    }

    @Nested
    class PowerTests {

        @Nested
        class IntPowerTests {

            @Test
            void testPower_PositiveExponent() {
                assertEquals(8, MtxMath.power(2, 3));
            }

            @Test
            void testPower_ExponentZero() {
                assertEquals(1, MtxMath.power(5, 0));
            }

            @Test
            void testPower_ExponentOne() {
                assertEquals(7, MtxMath.power(7, 1));
            }

            @Test
            void testPower_NegativeBaseOddExponent() {
                assertEquals(-8, MtxMath.power(-2, 3));
            }

            @Test
            void testPower_NegativeBaseEvenExponent() {
                assertEquals(16, MtxMath.power(-2, 4));
            }

            @Test
            void testPower_ZeroBasePositiveExponent() {
                assertEquals(0, MtxMath.power(0, 5));
            }

            @Test
            void testPower_NegativeExponentThrows() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxMath.power(2, -1)
                );
            }
        }

        @Nested
        class DoublePowerTests {
            private static final double DELTA = 1e-9;

            @Test
            void testPower_double_positiveExponent() {
                assertEquals(8.0, MtxMath.power(2.0, 3), DELTA);
            }

            @Test
            void testPower_double_exponentZero() {
                assertEquals(1.0, MtxMath.power(5.0, 0), DELTA);
            }

            @Test
            void testPower_double_baseZeroPositiveExponent() {
                assertEquals(0.0, MtxMath.power(0.0, 5), DELTA);
            }

            @Test
            void testPower_double_baseOne() {
                assertEquals(1.0, MtxMath.power(1.0, 100), DELTA);
            }

            @Test
            void testPower_double_negativeExponent() {
                assertEquals(0.125, MtxMath.power(2.0, -3), DELTA);
            }

            @Test
            void testPower_double_negativeBaseOddExponent() {
                assertEquals(-8.0, MtxMath.power(-2.0, 3), DELTA);
            }

            @Test
            void testPower_double_negativeBaseEvenExponent() {
                assertEquals(16.0, MtxMath.power(-2.0, 4), DELTA);
            }

            @Test
            void testPower_double_negativeBaseNegativeExponent() {
                assertEquals(-0.125, MtxMath.power(-2.0, -3), DELTA);
            }

            @Test
            void testPower_double_fractionalBase() {
                assertEquals(0.25, MtxMath.power(0.5, 2), DELTA);
            }

            @Test
            void testPower_double_fractionalBaseNegativeExponent() {
                assertEquals(4.0, MtxMath.power(0.5, -2), DELTA);
            }

            @Test
            void testPower_double_zeroBaseZeroExponent() {
                assertEquals(1.0, MtxMath.power(0.0, 0), DELTA);
            }

            @Test
            void testPower_double_largeExponent() {
                assertEquals(1024.0, MtxMath.power(2.0, 10), DELTA);
            }

            @Test
            void testPower_double_negativeOneEvenExponent() {
                assertEquals(1.0, MtxMath.power(-1.0, 100), DELTA);
            }

            @Test
            void testPower_double_negativeOneOddExponent() {
                assertEquals(-1.0, MtxMath.power(-1.0, 101), DELTA);
            }
        }

        @Nested
        class PowerOfTwoTests {

            @Test
            void testPowerOfTwo_exponentTooSmall() {
                IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxMath.powerOfTwo(-1023)
                );

                assertEquals(
                        "Result must fit in a double",
                        exception.getMessage()
                );
            }

            @Test
            void testPowerOfTwo_exponentTooBig() {
                IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxMath.powerOfTwo(1024)
                );

                assertEquals(
                        "Result must fit in a double",
                        exception.getMessage()
                );
            }

            @Test
            void testPowerOfTwo_exponentZero() {
                assertEquals(1.0, MtxMath.powerOfTwo(0));
            }

            @Test
            void testPowerOfTwo_exponentSmall() {
                assertEquals(2.0,  MtxMath.powerOfTwo(1));
                assertEquals(4.0,  MtxMath.powerOfTwo(2));
                assertEquals(8.0,  MtxMath.powerOfTwo(3));
                assertEquals(16.0, MtxMath.powerOfTwo(4));
                assertEquals(32.0, MtxMath.powerOfTwo(5));
            }

            @Test
            void testPowerOfTwo_exponentLarge() {
                int expected = 32;
                for (int i = 5; i < 20; i++) {
                    assertEquals(expected, MtxMath.powerOfTwo(i));
                    expected *= 2;
                }
            }
        }
    }

    @Nested
    class IsEvenTests {

        @Test
        void testIsEven_True() {
            assertTrue(MtxMath.isEven(2));
        }

        @Test
        void testIsEven_False() {
            assertFalse(MtxMath.isEven(1));
        }

        @Test
        void testIsEven_Zero() {
            assertTrue(MtxMath.isEven(0));
        }
    }

    @Nested
    class IsOddTests {

        @Test
        void testIsOdd_True() {
            assertTrue(MtxMath.isOdd(1));
        }

        @Test
        void testIsOdd_False() {
            assertFalse(MtxMath.isOdd(2));
        }

        @Test
        void testIsOdd_Zero() {
            assertFalse(MtxMath.isOdd(0));
        }
    }

    @Nested
    class ReverseNumberTests {

        @Test
        void testReverseNumber_Positive() {
            assertEquals(4321, MtxMath.reverseNumber(1234));
        }

        @Test
        void testReverseNumber_Negative() {
            assertEquals(-4321, MtxMath.reverseNumber(-1234));
        }

        @Test
        void testReverseNumber_EndsWithZero() {
            assertEquals(1, MtxMath.reverseNumber(100));
        }

        @Test
        void testReverseNumber_Zero() {
            assertEquals(0, MtxMath.reverseNumber(0));
        }
    }

    @Nested
    class CountDigitsTests {

        @Test
        void testCountDigits_Positive() {
            assertEquals(4, MtxMath.countDigits(1234));
        }

        @Test
        void testCountDigits_Negative() {
            assertEquals(4, MtxMath.countDigits(-1234));
        }

        @Test
        void testCountDigits_Zero() {
            assertEquals(1, MtxMath.countDigits(0));
        }

        @Test
        void testCountDigits_WithTrailingZeros() {
            assertEquals(3, MtxMath.countDigits(100));
        }
    }

    @Nested
    class SumOfDigitsTests {

        @Test
        void testSumOfDigits_Positive_One() {
            assertEquals(4, MtxMath.sumOfDigits(4));
        }

        @Test
        void testSumOfDigits_Positive_Many() {
            assertEquals(10, MtxMath.sumOfDigits(1234));
        }

        @Test
        void testSumOfDigits_Negative() {
            assertEquals(10, MtxMath.sumOfDigits(-1234));
        }

        @Test
        void testSumOfDigits_Zero() {
            assertEquals(0, MtxMath.sumOfDigits(0));
        }

        @Test
        void testSumOfDigits_WithTrailingZeros() {
            assertEquals(1, MtxMath.sumOfDigits(100));
        }

        @Test
        void testSumOfDigits_WithLeadingZeros() {
            assertEquals(2, MtxMath.sumOfDigits(100001));
        }
    }

    @Nested
    class NextPrimeTests {

        @Test
        void testNextPrime_InputLessThanTwo_ReturnsTwo() {
            assertEquals(2, MtxMath.nextPrime(-10));
            assertEquals(2, MtxMath.nextPrime(0));
            assertEquals(2, MtxMath.nextPrime(1));
        }

        @Test
        void testNextPrime_InputTwo_ReturnsTwo() {
            assertEquals(2, MtxMath.nextPrime(2));
        }

        @Test
        void testNextPrime_InputAlreadyPrime_ReturnsSameNumber() {
            assertEquals(3,  MtxMath.nextPrime(3));
            assertEquals(17, MtxMath.nextPrime(17));
            assertEquals(97, MtxMath.nextPrime(97));
        }

        @Test
        void testNextPrime_InputComposite_ReturnsNextPrime() {
            assertEquals(5,   MtxMath.nextPrime(4));
            assertEquals(11,  MtxMath.nextPrime(8));
            assertEquals(17,  MtxMath.nextPrime(15));
            assertEquals(101, MtxMath.nextPrime(100));
        }
    }

    @Nested
    class PrimeFactorsTests {

        @Test
        void testPrimeFactors_NumberLessThanTwo_ReturnsEmptyList() {
            assertEquals(List.of(), MtxMath.primeFactors(0));
            assertEquals(List.of(), MtxMath.primeFactors(1));
        }

        @Test
        void testPrimeFactors_PrimeNumber_ReturnsNumberItself() {
            assertEquals(List.of(2),  MtxMath.primeFactors(2));
            assertEquals(List.of(13), MtxMath.primeFactors(13));
            assertEquals(List.of(97), MtxMath.primeFactors(97));
        }

        @Test
        void testPrimeFactors_CompositeNumber_ReturnsPrimeFactors() {
            assertEquals(List.of(2, 2),    MtxMath.primeFactors(4));
            assertEquals(List.of(2, 3),    MtxMath.primeFactors(6));
            assertEquals(List.of(2, 2, 3), MtxMath.primeFactors(12));
            assertEquals(List.of(2, 3, 5), MtxMath.primeFactors(30));
        }

        @Test
        void testPrimeFactors_RepeatedPrimeFactors_ReturnsAllOccurrences() {
            assertEquals(List.of(2, 2, 2), MtxMath.primeFactors(8));
            assertEquals(List.of(3, 3),    MtxMath.primeFactors(9));
            assertEquals(List.of(5, 5, 5), MtxMath.primeFactors(125));
        }

        @Test
        void testPrimeFactors_NegativeCompositeNumber_UsesAbsoluteValue() {
            assertEquals(List.of(2, 2, 3), MtxMath.primeFactors(-12));
            assertEquals(List.of(3, 3, 5), MtxMath.primeFactors(-45));
        }
    }

    @Nested
    class IsCompositeTests {

        @Test
        void testIsComposite_CompositeNumbers_ReturnsTrue() {
            assertTrue(MtxMath.isComposite(4));
            assertTrue(MtxMath.isComposite(6));
            assertTrue(MtxMath.isComposite(9));
            assertTrue(MtxMath.isComposite(12));
            assertTrue(MtxMath.isComposite(100));
        }

        @Test
        void testIsComposite_PrimeNumbers_ReturnsFalse() {
            assertFalse(MtxMath.isComposite(2));
            assertFalse(MtxMath.isComposite(3));
            assertFalse(MtxMath.isComposite(5));
            assertFalse(MtxMath.isComposite(17));
            assertFalse(MtxMath.isComposite(97));
        }

        @Test
        void testIsComposite_ZeroAndOne_ReturnsFalse() {
            assertFalse(MtxMath.isComposite(0));
            assertFalse(MtxMath.isComposite(1));
        }

        @Test
        void testIsComposite_NegativeNumbers_ReturnsFalse() {
            assertFalse(MtxMath.isComposite(-1));
            assertFalse(MtxMath.isComposite(-4));
            assertFalse(MtxMath.isComposite(-100));
        }
    }

    @Nested
    class IsDivisibleByTests {

        @Test
        void testIsDivisibleBy_DivisibleNumbers_ReturnsTrue() {
            assertTrue(MtxMath.isDivisibleBy(10, 2));
            assertTrue(MtxMath.isDivisibleBy(12, 3));
            assertTrue(MtxMath.isDivisibleBy(100, 10));
            assertTrue(MtxMath.isDivisibleBy(0, 5));
        }

        @Test
        void testIsDivisibleBy_NonDivisibleNumbers_ReturnsFalse() {
            assertFalse(MtxMath.isDivisibleBy(10, 3));
            assertFalse(MtxMath.isDivisibleBy(14, 5));
            assertFalse(MtxMath.isDivisibleBy(100, 6));
        }

        @Test
        void testIsDivisibleBy_ZeroDivisor_ReturnsFalse() {
            assertFalse(MtxMath.isDivisibleBy(10, 0));
            assertFalse(MtxMath.isDivisibleBy(0, 0));
        }

        @Test
        void testIsDivisibleBy_NegativeNumbers_ReturnsExpectedResult() {
            assertTrue(MtxMath.isDivisibleBy(-10, 2));
            assertTrue(MtxMath.isDivisibleBy(10, -2));
            assertTrue(MtxMath.isDivisibleBy(-10, -2));
            assertFalse(MtxMath.isDivisibleBy(-10, 3));
        }

        @Test
        void testIsDivisibleBy_DivisorOne_ReturnsTrue() {
            assertTrue(MtxMath.isDivisibleBy(10, 1));
            assertTrue(MtxMath.isDivisibleBy(-10, 1));
            assertTrue(MtxMath.isDivisibleBy(0, 1));
        }
    }

    @Nested
    class IsPalindromeNumberTests {

        @Test
        void testIsPalindromeNumber_SingleDigitNumbers_ReturnTrue() {
            assertTrue(MtxMath.isPalindromeNumber(0));
            assertTrue(MtxMath.isPalindromeNumber(1));
            assertTrue(MtxMath.isPalindromeNumber(7));
        }

        @Test
        void testIsPalindromeNumber_EvenLengthPalindrome_ReturnsTrue() {
            assertTrue(MtxMath.isPalindromeNumber(11));
            assertTrue(MtxMath.isPalindromeNumber(1221));
            assertTrue(MtxMath.isPalindromeNumber(9009));
        }

        @Test
        void testIsPalindromeNumber_OddLengthPalindrome_ReturnsTrue() {
            assertTrue(MtxMath.isPalindromeNumber(121));
            assertTrue(MtxMath.isPalindromeNumber(12321));
            assertTrue(MtxMath.isPalindromeNumber(90709));
        }

        @Test
        void testIsPalindromeNumber_NonPalindrome_ReturnsFalse() {
            assertFalse(MtxMath.isPalindromeNumber(10));
            assertFalse(MtxMath.isPalindromeNumber(123));
            assertFalse(MtxMath.isPalindromeNumber(1234));
            assertFalse(MtxMath.isPalindromeNumber(100));
        }

        @Test
        void testIsPalindromeNumber_NegativeNumber_ReturnsFalse() {
            assertFalse(MtxMath.isPalindromeNumber(-1));
            assertFalse(MtxMath.isPalindromeNumber(-121));
            assertFalse(MtxMath.isPalindromeNumber(-12321));
        }
    }

    @Nested
    class IsArmstrongNumberTests {

        @Test
        void testIsArmstrongNumber_SingleDigitNumbers_ReturnTrue() {
            assertTrue(MtxMath.isArmstrongNumber(0));
            assertTrue(MtxMath.isArmstrongNumber(1));
            assertTrue(MtxMath.isArmstrongNumber(7));
            assertTrue(MtxMath.isArmstrongNumber(9));
        }

        @Test
        void testIsArmstrongNumber_KnownArmstrongNumbers_ReturnTrue() {
            assertTrue(MtxMath.isArmstrongNumber(153));
            assertTrue(MtxMath.isArmstrongNumber(370));
            assertTrue(MtxMath.isArmstrongNumber(371));
            assertTrue(MtxMath.isArmstrongNumber(407));
            assertTrue(MtxMath.isArmstrongNumber(9474));
        }

        @Test
        void testIsArmstrongNumber_NonArmstrongNumbers_ReturnFalse() {
            assertFalse(MtxMath.isArmstrongNumber(10));
            assertFalse(MtxMath.isArmstrongNumber(100));
            assertFalse(MtxMath.isArmstrongNumber(152));
            assertFalse(MtxMath.isArmstrongNumber(200));
            assertFalse(MtxMath.isArmstrongNumber(9475));
        }

        @Test
        void testIsArmstrongNumber_NegativeNumbers_ReturnFalse() {
            assertFalse(MtxMath.isArmstrongNumber(-1));
            assertFalse(MtxMath.isArmstrongNumber(-153));
            assertFalse(MtxMath.isArmstrongNumber(-9474));
        }
    }

    @Nested
    class DigitalRootTests {

        @Test
        void testDigitalRoot_SingleDigitNumber_ReturnsSameNumber() {
            assertEquals(0, MtxMath.digitalRoot(0));
            assertEquals(1, MtxMath.digitalRoot(1));
            assertEquals(7, MtxMath.digitalRoot(7));
            assertEquals(9, MtxMath.digitalRoot(9));
        }

        @Test
        void testDigitalRoot_MultipleDigitsOneIteration_ReturnsDigitSum() {
            assertEquals(3, MtxMath.digitalRoot(12));
            assertEquals(6, MtxMath.digitalRoot(123));
            assertEquals(9, MtxMath.digitalRoot(45));
        }

        @Test
        void testDigitalRoot_MultipleDigitsSeveralIterations_ReturnsSingleDigitRoot() {
            assertEquals(1, MtxMath.digitalRoot(10));
            assertEquals(1, MtxMath.digitalRoot(100));
            assertEquals(2, MtxMath.digitalRoot(9992));
            assertEquals(3, MtxMath.digitalRoot(9876));
        }

        @Test
        void testDigitalRoot_NegativeNumber_UsesAbsoluteValue() {
            assertEquals(6, MtxMath.digitalRoot(-123));
            assertEquals(9, MtxMath.digitalRoot(-45));
            assertEquals(2, MtxMath.digitalRoot(-9992));
        }

        @Test
        void testDigitalRoot_NumberWithZeros_ReturnsExpectedRoot() {
            assertEquals(1, MtxMath.digitalRoot(1000));
            assertEquals(5, MtxMath.digitalRoot(5000));
            assertEquals(9, MtxMath.digitalRoot(9000));
        }
    }

    @Nested
    class ClampTests {

        @Nested
        class IntClampTests {

            @Test
            void testClamp_int_minGreaterThanMax() {
                String message = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxMath.clamp(3, 2, 1)
                ).getMessage();
                assertEquals("Minimum cannot be greater than maximum.", message);
            }

            @Test
            void testClamp_int_valueLessThanMin() {
                assertEquals(1, MtxMath.clamp(0, 1, 2));
            }

            @Test
            void testClamp_int_valueGreaterThanMax() {
                assertEquals(1, MtxMath.clamp(2, 0, 1));
            }

            @Test
            void testClamp_int_valueBetweenMinAndMax() {
                assertEquals(2, MtxMath.clamp(2, 1, 3));
            }
        }

        @Nested
        class DoubleClampTests {

            @Test
            void testClamp_int_minGreaterThanMax() {
                String message = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxMath.clamp(3.0, 2.0, 1.0)
                ).getMessage();
                assertEquals("Minimum cannot be greater than maximum.", message);
            }

            @Test
            void testClamp_int_valueLessThanMin() {
                assertEquals(1.5, MtxMath.clamp(0.5, 1.5, 2.5));
            }

            @Test
            void testClamp_int_valueGreaterThanMax() {
                assertEquals(1.1, MtxMath.clamp(2.2, 0.0, 1.1));
            }

            @Test
            void testClamp_int_valueBetweenMinAndMax() {
                assertEquals(1.3, MtxMath.clamp(1.3, 1.2, 2.4));
            }
        }
    }

    @Nested
    class IsBetweenTests {

        @Test
        void testIsBetween_valueLessThanMin() {
            assertFalse(MtxMath.isBetween(0, 1, 2));
        }

        @Test
        void testIsBetween_valueGreaterThanOrEqualToMin_valueGreaterThanMax() {
            assertFalse(MtxMath.isBetween(2, 1, 1));
            assertFalse(MtxMath.isBetween(2, 2, 1));
        }

        @Test
        void testIsBetween_valueGreaterThanOrEqualToMin_valueLessThanOrEqualToMax() {
            assertTrue(MtxMath.isBetween(2, 1, 3));
            assertTrue(MtxMath.isBetween(2, 1, 2));
        }
    }

    @Nested
    class IsPerfectSquareTests {

        @Test
        void testIsPerfectSquare_negativeInteger() {
            assertFalse(MtxMath.isPerfectSquare(-1));
        }

        @Test
        void testIsPerfectSquare_zero() {
            assertTrue(MtxMath.isPerfectSquare(0));
        }

        @Test
        void testIsPerfectSquare_positiveInteger_true() {
            assertTrue(MtxMath.isPerfectSquare(9));
            assertTrue(MtxMath.isPerfectSquare(16));
            assertTrue(MtxMath.isPerfectSquare(25));
        }

        @Test
        void testIsPerfectSquare_positiveInteger_false() {
            assertFalse(MtxMath.isPerfectSquare(20));
            assertFalse(MtxMath.isPerfectSquare(24));
            assertFalse(MtxMath.isPerfectSquare(30));
        }
    }

    @Nested
    class CountSetBitTests {

        @Test
        void testCountSetBit_zero() {
            assertEquals(0, MtxMath.countSetBits(0));
        }

        @Test
        void testCountSetBit_positive() {
            assertEquals(2, MtxMath.countSetBits(5));
            assertEquals(4, MtxMath.countSetBits(15));
            assertEquals(1, MtxMath.countSetBits(16));
        }

        @Test
        void testCountSetBit_negative() {
            assertEquals(31, MtxMath.countSetBits(-5));
            assertEquals(29, MtxMath.countSetBits(-15));
        }
    }

    @Nested
    class LerpTests {

        @Test
        void testLerp_startWhenTIsZero() {
            assertEquals(10.0, MtxMath.lerp(10.0, 20.0, 0.0));
        }

        @Test
        void testLerp_endWhenTIsOne() {
            assertEquals(20.0, MtxMath.lerp(10.0, 20.0, 1.0));
        }

        @Test
        void testLerp_midpoint() {
            assertEquals(15.0, MtxMath.lerp(10.0, 20.0, 0.5));
        }

        @Test
        void testLerp_interpolatesCorrectly() {
            assertEquals(12.5, MtxMath.lerp(10.0, 20.0, 0.25));
            assertEquals(17.5, MtxMath.lerp(10.0, 20.0, 0.75));
        }

        @Test
        void testLerp_clampsNegativeTToZero() {
            assertEquals(10.0, MtxMath.lerp(10.0, 20.0, -1.0));
        }

        @Test
        void testLerp_clampsTGreaterThanOneToOne() {
            assertEquals(20.0, MtxMath.lerp(10.0, 20.0, 2.0));
        }

        @Test
        void testLerp_worksWithNegativeNumbers() {
            assertEquals(-5.0, MtxMath.lerp(-10.0, 10.0, 0.25));
            assertEquals(0.0,  MtxMath.lerp(-10.0, 10.0, 0.5));
            assertEquals(10.0, MtxMath.lerp(-10.0, 10.0, 1.5)); // clamped
        }

        @Test
        void testLerp_sameValueWhenEndpointsAreEqual() {
            assertEquals(42.0, MtxMath.lerp(42.0, 42.0, 0.0));
            assertEquals(42.0, MtxMath.lerp(42.0, 42.0, 0.5));
            assertEquals(42.0, MtxMath.lerp(42.0, 42.0, 1.0));
            assertEquals(42.0, MtxMath.lerp(42.0, 42.0, 2.0));
        }
    }

    @Nested
    class SieveOfEratosthenesTests {

        @Test
        void testSieveOfEratosthenes_ReturnsEmptyListForNegativeLimit() {
            assertArrayEquals(new int[0], MtxMath.sieveOfEratosthenes(-1));
        }

        @Test
        void testSieveOfEratosthenes_ReturnsEmptyListForZero() {
            assertArrayEquals(new int[0], MtxMath.sieveOfEratosthenes(0));
        }

        @Test
        void testSieveOfEratosthenes_ReturnsEmptyListForOne() {
            assertArrayEquals(new int[0], MtxMath.sieveOfEratosthenes(1));
        }

        @Test
        void testSieveOfEratosthenes_ReturnsTwoForLimitTwo() {
            assertArrayEquals(new int[] {2}, MtxMath.sieveOfEratosthenes(2));
        }

        @Test
        void testSieveOfEratosthenes_ReturnsPrimesUpToTen() {
            assertArrayEquals(
                    new int[] {2, 3, 5, 7},
                    MtxMath.sieveOfEratosthenes(10)
            );
        }

        @Test
        void testSieveOfEratosthenes_ReturnsPrimesUpToTwenty() {
            assertArrayEquals(
                    new int[] {2, 3, 5, 7, 11, 13, 17, 19},
                    MtxMath.sieveOfEratosthenes(20)
            );
        }

        @Test
        void testSieveOfEratosthenes_ReturnsPrimesUpToThirty() {
            assertArrayEquals(
                    new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29},
                    MtxMath.sieveOfEratosthenes(30)
            );
        }

        @Test
        void testSieveOfEratosthenes_ReturnsOnlyPrimeWhenLimitIsPrime() {
            assertArrayEquals(
                    new int[] {2, 3, 5, 7, 11, 13},
                    MtxMath.sieveOfEratosthenes(13)
            );
        }

        @Test
        void testSieveOfEratosthenes_DoesNotIncludeCompositeUpperBound() {
            assertArrayEquals(
                    new int[] {2, 3, 5, 7, 11, 13},
                    MtxMath.sieveOfEratosthenes(15)
            );
        }
    }

    @Nested
    class RoundToDecimalsTests {

        @Test
        void testRoundToDecimals_negativeDecimals() {
            String message = assertThrows(
                    IllegalArgumentException.class,
                    () -> MtxMath.roundToDecimals(5.0, -1)
            ).getMessage();
            assertEquals("Decimal places must be non-negative.", message);
        }

        @Test
        void testRoundToDecimals_positiveDecimals() {
            assertEquals(3.14,  MtxMath.roundToDecimals(3.14159, 2));
            assertEquals(3.15,  MtxMath.roundToDecimals(3.145,   2));
            assertEquals(2.718, MtxMath.roundToDecimals(2.71828, 3));
            assertEquals(123.0, MtxMath.roundToDecimals(123.0,   0));
            assertEquals(-1.24, MtxMath.roundToDecimals(-1.235,  2));
        }
    }

    @Nested
    class PythagoreanTheoremTests {
        private static final double DELTA = 0.001;

        @Test
        void testPythagoreanTheorem_wholeNumbers() {
            assertEquals(5,  MtxMath.pythagoreanTheorem(3, 4));
            assertEquals(13, MtxMath.pythagoreanTheorem(5, 12));
            assertEquals(25, MtxMath.pythagoreanTheorem(7, 24));
            assertEquals(17, MtxMath.pythagoreanTheorem(8, 15), DELTA);
            assertEquals(41, MtxMath.pythagoreanTheorem(9, 40));
        }

        @Test
        void testPythagoreanTheorem_decimals() {
            assertEquals(4.085339643163099, MtxMath.pythagoreanTheorem(1.5, 3.8), DELTA);
            assertEquals(5.547071299343465, MtxMath.pythagoreanTheorem(2.6, 4.9), DELTA);
            assertEquals(7.58946638440411,  MtxMath.pythagoreanTheorem(7.2, 2.4), DELTA);
            assertEquals(8.532877591996735, MtxMath.pythagoreanTheorem(8.4, 1.5), DELTA);
            assertEquals(9.974467404327912, MtxMath.pythagoreanTheorem(9.0, 4.3), DELTA);
        }
    }

    @Nested
    class HypotenuseTests {
        private static final double DELTA = 0.001;

        @Test
        void testHypotenuse_wholeNumbers() {
            assertEquals(5,  MtxMath.hypotenuse(3, 4));
            assertEquals(13, MtxMath.hypotenuse(5, 12));
            assertEquals(25, MtxMath.hypotenuse(7, 24));
            assertEquals(17, MtxMath.hypotenuse(8, 15), DELTA);
            assertEquals(41, MtxMath.hypotenuse(9, 40));
        }

        @Test
        void testHypotenuse_decimals() {
            assertEquals(4.085339643163099, MtxMath.hypotenuse(1.5, 3.8), DELTA);
            assertEquals(5.547071299343465, MtxMath.hypotenuse(2.6, 4.9), DELTA);
            assertEquals(7.58946638440411,  MtxMath.hypotenuse(7.2, 2.4), DELTA);
            assertEquals(8.532877591996735, MtxMath.hypotenuse(8.4, 1.5), DELTA);
            assertEquals(9.974467404327912, MtxMath.hypotenuse(9.0, 4.3), DELTA);
        }
    }

    @Nested
    class SqrtTests {

        @Nested
        class IntSqrtTests {

            @Test
            void testSqrt_int_zero() {
                assertEquals(0, MtxMath.sqrt(0));
            }

            @Test
            void testSqrt_int_one() {
                assertEquals(1, MtxMath.sqrt(1));
            }

            @Test
            void testSqrt_int_perfectSquares() {
                assertEquals(2,  MtxMath.sqrt(4));
                assertEquals(3,  MtxMath.sqrt(9));
                assertEquals(4,  MtxMath.sqrt(16));
                assertEquals(5,  MtxMath.sqrt(25));
                assertEquals(10, MtxMath.sqrt(100));
            }

            @Test
            void testSqrt_int_nonPerfectSquares() {
                assertEquals(1, MtxMath.sqrt(2));
                assertEquals(1, MtxMath.sqrt(3));
                assertEquals(2, MtxMath.sqrt(5));
                assertEquals(2, MtxMath.sqrt(8));
                assertEquals(3, MtxMath.sqrt(15));
                assertEquals(4, MtxMath.sqrt(24));
                assertEquals(5, MtxMath.sqrt(35));
            }

            @Test
            void testSqrt_int_largeNumbers() {
                assertEquals(31622, MtxMath.sqrt(1_000_000_000));
                assertEquals(46340, MtxMath.sqrt(Integer.MAX_VALUE));
            }

            @Test
            void testSqrt_int_negative() {
                IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxMath.sqrt(-1)
                );

                assertEquals(
                        "Negative numbers do not have real square roots.",
                        exception.getMessage()
                );
            }

            @Test
            void testSqrt_int_boundaryValues() {
                assertEquals(46339, MtxMath.sqrt(2_147_395_599));
                assertEquals(46340, MtxMath.sqrt(2_147_395_600)); // 46340²
                assertEquals(46340, MtxMath.sqrt(Integer.MAX_VALUE));
            }
        }

        @Nested
        class DoubleSqrtTests {
            private static final double DELTA = 1e-9;

            @Test
            void testDoubleSqrt_double_zero() {
                assertEquals(0.0, MtxMath.sqrt(0.0), DELTA);
            }

            @Test
            void testDoubleSqrt_double_one() {
                assertEquals(1.0, MtxMath.sqrt(1.0), DELTA);
            }

            @Test
            void testDoubleSqrt_double_perfectSquares() {
                assertEquals(2.0,  MtxMath.sqrt(4.0), DELTA);
                assertEquals(3.0,  MtxMath.sqrt(9.0), DELTA);
                assertEquals(4.0,  MtxMath.sqrt(16.0), DELTA);
                assertEquals(5.0,  MtxMath.sqrt(25.0), DELTA);
                assertEquals(10.0, MtxMath.sqrt(100.0), DELTA);
            }

            @Test
            void testDoubleSqrt_double_nonPerfectSquares() {
                assertEquals(Math.sqrt(2),  MtxMath.sqrt(2.0), DELTA);
                assertEquals(Math.sqrt(3),  MtxMath.sqrt(3.0), DELTA);
                assertEquals(Math.sqrt(5),  MtxMath.sqrt(5.0), DELTA);
                assertEquals(Math.sqrt(10), MtxMath.sqrt(10.0), DELTA);
                assertEquals(Math.sqrt(99), MtxMath.sqrt(99.0), DELTA);
            }

            @Test
            void testDoubleSqrt_double_decimalInputs() {
                assertEquals(Math.sqrt(2.25),    MtxMath.sqrt(2.25), DELTA);
                assertEquals(Math.sqrt(0.25),    MtxMath.sqrt(0.25), DELTA);
                assertEquals(Math.sqrt(123.456), MtxMath.sqrt(123.456), DELTA);
            }

            // Takes too long to run
//            @Test
//            void testDoubleSqrt_double_largeNumbers() {
//                assertEquals(Math.sqrt(1_000_000_000),    MtxMath.sqrt(1_000_000_000.0), DELTA);
//                assertEquals(Math.sqrt(Double.MAX_VALUE), MtxMath.sqrt(Double.MAX_VALUE), 1e145);
//            }

            @Test
            void testDoubleSqrt_double_verySmallNumbers() {
                assertEquals(Math.sqrt(1e-6),  MtxMath.sqrt(1e-6), DELTA);
                assertEquals(Math.sqrt(1e-12), MtxMath.sqrt(1e-12), DELTA);
            }

            @Test
            void testDoubleSqrt_double_negative() {
                IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxMath.sqrt(-4.0)
                );

                assertEquals(
                        "Negative numbers do not have real square roots.",
                        exception.getMessage()
                );
            }

            @Test
            void testDoubleSqrt_double_resultSquaredEqualsOriginal() {
                double value = 12345.6789;
                double result = MtxMath.sqrt(value);

                assertEquals(value, result * result, 1e-6);
            }
        }
    }

    @Nested
    class FloorTests {

        @Test
        void testFloor_positiveInteger() {
            assertEquals(3.0, MtxMath.floor(3.0));
        }

        @Test
        void testFloor_positiveFraction() {
            assertEquals(3.0, MtxMath.floor(3.7));
        }

        @Test
        void testFloor_zero() {
            assertEquals(0.0, MtxMath.floor(0.0));
        }

        @Test
        void testFloor_negativeInteger() {
            assertEquals(-3.0, MtxMath.floor(-3.0));
        }

        @Test
        void testFloor_negativeFraction() {
            assertEquals(-4.0, MtxMath.floor(-3.2));
        }

        @Test
        void testFloor_negativeFractionCloseToZero() {
            assertEquals(-1.0, MtxMath.floor(-0.2));
        }

        @Test
        void testFloor_positiveFractionCloseToZero() {
            assertEquals(0.0, MtxMath.floor(0.2));
        }

        @Test
        void testFloor_largePositive() {
            assertEquals(123456789.0, MtxMath.floor(123456789.987));
        }

        @Test
        void testFloor_largeNegative() {
            assertEquals(-123456790.0, MtxMath.floor(-123456789.123));
        }
    }

    @Nested
    class CeilTests {

        @Test
        void testCeil_positiveInteger() {
            assertEquals(3.0, MtxMath.ceil(3.0));
        }

        @Test
        void testCeil_positiveFraction() {
            assertEquals(4.0, MtxMath.ceil(3.2));
        }

        @Test
        void testCeil_zero() {
            assertEquals(0.0, MtxMath.ceil(0.0));
        }

        @Test
        void testCeil_negativeInteger() {
            assertEquals(-3.0, MtxMath.ceil(-3.0));
        }

        @Test
        void testCeil_negativeFraction() {
            assertEquals(-3.0, MtxMath.ceil(-3.2));
        }

        @Test
        void testCeil_negativeFractionCloseToZero() {
            assertEquals(0.0, MtxMath.ceil(-0.2));
        }

        @Test
        void testCeil_positiveFractionCloseToZero() {
            assertEquals(1.0, MtxMath.ceil(0.2));
        }

        @Test
        void testCeil_largePositive() {
            assertEquals(123456790.0, MtxMath.ceil(123456789.123));
        }

        @Test
        void testCeil_largeNegative() {
            assertEquals(-123456789.0, MtxMath.ceil(-123456789.987));
        }
    }

    @Nested
    class RoundTests {

        @ParameterizedTest
        @CsvSource({
                "0.0, 0",
                "0.2, 0",
                "0.5, 1",
                "0.7, 1",
                "3.2, 3",
                "3.5, 4",
                "3.7, 4"
        })
        void testRound_positiveValues(double input, long expected) {
            assertEquals(expected, MtxMath.round(input));
        }

        @ParameterizedTest
        @CsvSource({
                "-0.2, 0",
                "-0.5, 0",
                "-0.7, -1",
                "-3.2, -3",
                "-3.5, -3",
                "-3.7, -4"
        })
        void testRound_negativeValues(double input, long expected) {
            assertEquals(expected, MtxMath.round(input));
        }

        @ParameterizedTest
        @CsvSource({
                "123456789.4, 123456789",
                "123456789.5, 123456790",
                "-123456789.4, -123456789",
                "-123456789.5, -123456789"
        })
        void testRound_largerValues(double input, long expected) {
            assertEquals(expected, MtxMath.round(input));
        }
    }

    @Nested
    class DistanceBetweenPointsTests {
        private static final double EPSILON = 1e-9;

        @Test
        void testDistanceBetweenPoints_distanceBetweenSamePointIsZero() {
            assertEquals(0.0, MtxMath.distanceBetweenPoints(2.5, -4.0, 2.5, -4.0), EPSILON);
        }

        @Test
        void testDistanceBetweenPoints_distanceBetweenHorizontalPoints() {
            assertEquals(5.0, MtxMath.distanceBetweenPoints(1.0, 3.0, 6.0, 3.0), EPSILON);
        }

        @Test
        void testDistanceBetweenPoints_distanceBetweenVerticalPoints() {
            assertEquals(7.0, MtxMath.distanceBetweenPoints(2.0, 1.0, 2.0, 8.0), EPSILON);
        }

        @Test
        void testDistanceBetweenPoints_distanceBetweenDiagonalPoints() {
            assertEquals(5.0, MtxMath.distanceBetweenPoints(0.0, 0.0, 3.0, 4.0), EPSILON);
        }

        @Test
        void testDistanceBetweenPoints_distanceIsSymmetric() {
            double d1 = MtxMath.distanceBetweenPoints(-2.0, 5.0, 4.0, -3.0);
            double d2 = MtxMath.distanceBetweenPoints(4.0, -3.0, -2.0, 5.0);

            assertEquals(d1, d2, EPSILON);
        }

        @Test
        void testDistanceBetweenPoints_distanceWithNegativeCoordinates() {
            assertEquals(5.0, MtxMath.distanceBetweenPoints(-1.0, -1.0, -4.0, -5.0), EPSILON);
        }

        @Test
        void testDistanceBetweenPoints_distanceWithLargeCoordinates() {
            assertEquals(
                    5.0,
                    MtxMath.distanceBetweenPoints(1_000_000.0, 1_000_000.0, 1_000_003.0, 1_000_004.0),
                    EPSILON
            );
        }
    }

    @Nested
    class OrderBitTests {

        @Test
        void testOrderBit_zero() {
            assertEquals(0x00000000, MtxMath.highOrderBit(0.0));
            assertEquals(0x00000000, MtxMath.lowOrderBit(0.0));
        }

        @Test
        void testOrderBit_negativeZero() {
            assertEquals(0x80000000, MtxMath.highOrderBit(-0.0));
            assertEquals(0x00000000, MtxMath.lowOrderBit(-0.0));
        }

        @Test
        void testOrderBit_one() {
            assertEquals(0x3FF00000, MtxMath.highOrderBit(1.0));
            assertEquals(0x00000000, MtxMath.lowOrderBit(1.0));
        }

        @Test
        void testOrderBit_negativeOne() {
            assertEquals(0xBFF00000, MtxMath.highOrderBit(-1.0));
            assertEquals(0x00000000, MtxMath.lowOrderBit(-1.0));
        }

        @Test
        void testOrderBit_pi() {
            assertEquals(0x400921FB, MtxMath.highOrderBit(Math.PI));
            assertEquals(0x54442D18, MtxMath.lowOrderBit(Math.PI));
        }

        @Test
        void testOrderBit_positiveInfinity() {
            assertEquals(0x7FF00000, MtxMath.highOrderBit(Double.POSITIVE_INFINITY));
            assertEquals(0x00000000, MtxMath.lowOrderBit(Double.POSITIVE_INFINITY));
        }

        @Test
        void testOrderBit_negativeInfinity() {
            assertEquals(0xFFF00000, MtxMath.highOrderBit(Double.NEGATIVE_INFINITY));
            assertEquals(0x00000000, MtxMath.lowOrderBit(Double.NEGATIVE_INFINITY));
        }

        @Test
        void testOrderBit_canonicalNaN() {
            assertEquals(0x7FF80000, MtxMath.highOrderBit(Double.NaN));
            assertEquals(0x00000000, MtxMath.lowOrderBit(Double.NaN));
        }

        @Test
        void testOrderBit_roundTripFromRawBits() {
            long raw = 0x123456789ABCDEFL;
            double value = Double.longBitsToDouble(raw);

            int high = MtxMath.highOrderBit(value);
            int low = MtxMath.lowOrderBit(value);

            long reconstructed = ((long) high << 32) | (low & 0xFFFFFFFFL);

            assertEquals(raw, reconstructed);
        }

        @Test
        void testOrderBit_roundTripWithArbitraryValue() {
            double value = 12345.6789;

            int high = MtxMath.highOrderBit(value);
            int low = MtxMath.lowOrderBit(value);

            long reconstructed = ((long) high << 32) | (low & 0xFFFFFFFFL);

            assertEquals(Double.doubleToRawLongBits(value), reconstructed);
        }
    }
}
