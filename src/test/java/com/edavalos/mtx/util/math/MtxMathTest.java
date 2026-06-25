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
    class PowerTests {

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
            assertEquals(-8,MtxMath.power(-2, 3));
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
}
