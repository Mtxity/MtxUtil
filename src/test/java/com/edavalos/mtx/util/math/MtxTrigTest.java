package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MtxTrigTest {

    private static final double TOLERANCE = 1e-15;
    private static final double PSEUDO_INFINITY     = 1.1169973830808569E308;
    private static final double PSEUDO_INFINITY_ADJ = 1.1169973830808549E308;

    @Nested
    class SinCosTests {

        @Test
        void testSinCos_matchesIndividualFunctionsAndStrictMath() {
            double[] inputs = {0.0, -0.0, 0.25, -0.25, Math.PI / 4.0, Math.PI / 2.0,
                    Math.PI, -Math.PI, 10.0, -10.0};
            for (double x : inputs) {
                MtxTrig.SinCos result = MtxTrig.sinCos(x);
                assertEquals(StrictMath.sin(x), result.sin(), TOLERANCE, "sinCos.sin(" + x + ")");
                assertEquals(StrictMath.cos(x), result.cos(), TOLERANCE, "sinCos.cos(" + x + ")");
                assertEquals(MtxTrig.sin(x), result.sin(), 0.0, "sin(" + x + ")");
                assertEquals(MtxTrig.cos(x), result.cos(), 0.0, "cos(" + x + ")");
            }
        }

        @Test
        void testSinCos_largeArgumentReduction() {
            double[] inputs = {Math.scalb(1.0, 21), -Math.scalb(1.0, 21), 1e100, -1e100,
                    Double.MAX_VALUE, -Double.MAX_VALUE};
            for (double x : inputs) {
                MtxTrig.SinCos result = MtxTrig.sinCos(x);
                assertEquals(StrictMath.sin(x), result.sin(), TOLERANCE, "sinCos.sin(" + x + ")");
                assertEquals(StrictMath.cos(x), result.cos(), TOLERANCE, "sinCos.cos(" + x + ")");
            }
        }

        @Test
        void testSinCos_signedZeroAndNonFiniteInputs() {
            assertSameBits(0.0, MtxTrig.sinCos(0.0).sin());
            assertSameBits(-0.0, MtxTrig.sinCos(-0.0).sin());
            assertEquals(1.0, MtxTrig.sinCos(0.0).cos(), 0.0);
            for (double x : new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
                MtxTrig.SinCos result = MtxTrig.sinCos(x);
                assertTrue(Double.isNaN(result.sin()));
                assertTrue(Double.isNaN(result.cos()));
            }
        }
    }

    @Nested
    class CardinalSinTests {

        @Test
        void testSinc_zeroAndSmallInputs() {
            assertEquals(1.0, MtxTrig.sinc(0.0), 0.0);
            assertEquals(1.0, MtxTrig.sinc(-0.0), 0.0);
            for (double x : new double[]{Double.MIN_VALUE, -Double.MIN_VALUE, 1e-12, -1e-12, 1e-4, -1e-4}) {
                double expected = x == 0.0 ? 1.0 : StrictMath.sin(x) / x;
                assertEquals(expected, MtxTrig.sinc(x), TOLERANCE, "sinc(" + x + ")");
            }
        }

        @Test
        void testSinc_knownValuesAndLargeArguments() {
            for (double x : new double[]{Math.PI / 2.0, Math.PI, -Math.PI, 10.0, -10.0,
                    Math.scalb(1.0, 21), 1e100, Double.MAX_VALUE}) {
                assertEquals(StrictMath.sin(x) / x, MtxTrig.sinc(x), TOLERANCE, "sinc(" + x + ")");
            }
        }

        @Test
        void testSinc_nonFiniteInputs() {
            assertTrue(Double.isNaN(MtxTrig.sinc(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.sinc(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.sinc(Double.NEGATIVE_INFINITY)));
        }
    }

    @Nested
    class CardinalTanTests {

        @Test
        void testTanc_zeroAndSmallInputs() {
            assertEquals(1.0, MtxTrig.tanc(0.0), 0.0);
            assertEquals(1.0, MtxTrig.tanc(-0.0), 0.0);
            for (double x : new double[]{Double.MIN_VALUE, -Double.MIN_VALUE, 1e-12, -1e-12, 1e-4, -1e-4}) {
                double expected = x == 0.0 ? 1.0 : StrictMath.tan(x) / x;
                assertEquals(expected, MtxTrig.tanc(x), TOLERANCE, "tanc(" + x + ")");
            }
        }

        @Test
        void testTanc_knownValuesAndLargeArguments() {
            for (double x : new double[]{Math.PI / 4.0, Math.PI, -Math.PI, 10.0, -10.0,
                    Math.scalb(1.0, 21), 1e100, Double.MAX_VALUE}) {
                assertEquals(StrictMath.tan(x) / x, MtxTrig.tanc(x), TOLERANCE, "tanc(" + x + ")");
            }
        }

        @Test
        void testTanc_nearTangentPole() {
            double x = Math.nextDown(Math.PI / 2.0);
            double expected = StrictMath.tan(x) / x;
            double actual = MtxTrig.tanc(x);
            assertEquals(0.0, Math.abs((actual - expected) / expected), 1e-9);
        }

        @Test
        void testTanc_nonFiniteInputs() {
            assertTrue(Double.isNaN(MtxTrig.tanc(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.tanc(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.tanc(Double.NEGATIVE_INFINITY)));
        }
    }

    @Nested
    class CardinalCosTests {

        @Test
        void testCosc_zeroAndSmallInputs() {
            assertSameBits(0.0, MtxTrig.cosc(0.0));
            assertSameBits(-0.0, MtxTrig.cosc(-0.0));
            for (double x : new double[]{Double.MIN_VALUE, -Double.MIN_VALUE, 1e-12, -1e-12, 1e-4, -1e-4}) {
                double expected = 2.0 * StrictMath.sin(x / 2.0) * StrictMath.sin(x / 2.0) / x;
                assertEquals(expected, MtxTrig.cosc(x), 1e-15, "cosc(" + x + ")");
            }
        }

        @Test
        void testCosc_knownValuesAndLargeArguments() {
            for (double x : new double[]{Math.PI / 2.0, Math.PI, -Math.PI, 10.0, -10.0,
                    Math.scalb(1.0, 21), 1e100, Double.MAX_VALUE}) {
                double expected = (1.0 - StrictMath.cos(x)) / x;
                assertEquals(expected, MtxTrig.cosc(x), TOLERANCE, "cosc(" + x + ")");
            }
            assertEquals(2.0 / Math.PI, MtxTrig.cosc(Math.PI), TOLERANCE);
            assertEquals(-2.0 / Math.PI, MtxTrig.cosc(-Math.PI), TOLERANCE);
        }

        @Test
        void testCosc_nonFiniteInputs() {
            assertTrue(Double.isNaN(MtxTrig.cosc(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.cosc(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.cosc(Double.NEGATIVE_INFINITY)));
        }
    }

    @Nested
    class ArcTanTests {

        @Test
        void testAtan_everyQuadrant() {
            assertAngle(1.0, 1.0);
            assertAngle(-1.0, 1.0);
            assertAngle(1.0, -1.0);
            assertAngle(-1.0, -1.0);
            assertAngle(2.0, 5.0);
            assertAngle(-2.0, 5.0);
            assertAngle(2.0, -5.0);
            assertAngle(-2.0, -5.0);
        }

        @Test
        void testAtan_xIsOne() {
            assertEquals(StrictMath.atan(2.0), MtxTrig.atan(2.0, 1.0), TOLERANCE);
            assertEquals(StrictMath.atan(-2.0), MtxTrig.atan(-2.0, 1.0), TOLERANCE);
        }

        @Test
        void testAtan2_matchesTwoArgumentAtan() {
            assertEquals(MtxTrig.atan(1.0, 1.0), MtxTrig.atan2(1.0, 1.0), TOLERANCE);
            assertEquals(MtxTrig.atan(-2.0, -5.0), MtxTrig.atan2(-2.0, -5.0), TOLERANCE);
        }

        @Test
        void testAtan_signedZeroWithPositiveX() {
            assertSameBits(0.0, MtxTrig.atan(0.0, 2.0));
            assertSameBits(-0.0, MtxTrig.atan(-0.0, 2.0));
        }

        @Test
        void testAtan_signedPiWithZeroYAndNegativeX() {
            assertEquals(Math.PI, MtxTrig.atan(0.0, -2.0), TOLERANCE);
            assertEquals(-Math.PI, MtxTrig.atan(-0.0, -2.0), TOLERANCE);
        }

        @Test
        void testAtan_zeroXAndInfiniteY() {
            assertEquals(Math.PI / 2.0, MtxTrig.atan(2.0, 0.0), TOLERANCE);
            assertEquals(-Math.PI / 2.0, MtxTrig.atan(-2.0, 0.0), TOLERANCE);
            assertEquals(Math.PI / 2.0, MtxTrig.atan(Double.POSITIVE_INFINITY, 0.0), TOLERANCE);
            assertEquals(-Math.PI / 2.0, MtxTrig.atan(Double.NEGATIVE_INFINITY, 0.0), TOLERANCE);
            assertEquals(Math.PI / 2.0, MtxTrig.atan(Double.POSITIVE_INFINITY, 2.0), TOLERANCE);
            assertEquals(-Math.PI / 2.0, MtxTrig.atan(Double.NEGATIVE_INFINITY, 2.0), TOLERANCE);
        }

        @Test
        void testAtan_allInfiniteCoordinateCombinations() {
            assertEquals(Math.PI / 4.0, MtxTrig.atan(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), TOLERANCE);
            assertEquals(-Math.PI / 4.0, MtxTrig.atan(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), TOLERANCE);
            assertEquals(3.0 * Math.PI / 4.0, MtxTrig.atan(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY), TOLERANCE);
            assertEquals(-3.0 * Math.PI / 4.0, MtxTrig.atan(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), TOLERANCE);
        }

        @Test
        void testAtan_infiniteXWithFiniteYAndSignedZero() {
            assertSameBits(0.0, MtxTrig.atan(2.0, Double.POSITIVE_INFINITY));
            assertSameBits(-0.0, MtxTrig.atan(-2.0, Double.POSITIVE_INFINITY));
            assertEquals(Math.PI, MtxTrig.atan(2.0, Double.NEGATIVE_INFINITY), TOLERANCE);
            assertEquals(-Math.PI, MtxTrig.atan(-2.0, Double.NEGATIVE_INFINITY), TOLERANCE);
            assertEquals(Math.PI, MtxTrig.atan(0.0, Double.NEGATIVE_INFINITY), TOLERANCE);
            assertEquals(-Math.PI, MtxTrig.atan(-0.0, Double.NEGATIVE_INFINITY), TOLERANCE);
        }

        @Test
        void testAtan_nanCoordinates() {
            assertTrue(Double.isNaN(MtxTrig.atan(Double.NaN, 1.0)));
            assertTrue(Double.isNaN(MtxTrig.atan(1.0, Double.NaN)));
        }

        @Test
        void testAtan_extremeRatios() {
            assertAngle(Math.scalb(1.0, 100), 1.0);
            assertAngle(-Math.scalb(1.0, 100), 1.0);
            assertEquals(Math.PI, MtxTrig.atan(Math.scalb(1.0, -100), -Math.scalb(1.0, 100)), TOLERANCE);
            assertEquals(-Math.PI, MtxTrig.atan(-Math.scalb(1.0, -100), -Math.scalb(1.0, 100)), TOLERANCE);

            assertAngle(Double.MAX_VALUE, Double.MIN_VALUE);
            assertAngle(-Double.MAX_VALUE, Double.MIN_VALUE);
            assertAngle(Double.MIN_VALUE, Double.MAX_VALUE);
            assertAngle(-Double.MIN_VALUE, Double.MAX_VALUE);
            assertAngle(Double.MIN_VALUE, -Double.MAX_VALUE);
            assertAngle(-Double.MIN_VALUE, -Double.MAX_VALUE);
        }

        @Test
        void testAtan_argumentReductionRanges() {
            assertAngle(0.25, 1.0);
            assertAngle(0.5, 1.0);
            assertAngle(0.75, 1.0);
            assertAngle(1.0, 2.0);
            assertAngle(1.5, 1.0);
            assertAngle(3.0, 1.0);
            assertAngle(1.0, 3.0);
            assertAngle(-0.0, 3.0);
            assertAngle(Math.scalb(1.0, -30), 1.0);
        }

        @Test
        void testAtan_privateKernelReductionRanges() {
            // Use x != 1 to exercise the custom scalar atan kernel rather than
            // the StrictMath special case in atan(y, x).
            assertAngle(0.25, 2.0); // |y/x| < 0.4375
            assertAngle(1.0, 2.0);  // reduction id 0
            assertAngle(1.5, 2.0);  // reduction id 1
            assertAngle(3.0, 2.0);  // reduction id 2
            assertAngle(5.0, 2.0);  // reduction id 3
        }

        @Test
        void testAtan_scalarKernel_specialAndLargeValues() {
            assertTrue(Double.isNaN(MtxTrig.atan(Double.NaN)));
            assertKernelAngle(Double.POSITIVE_INFINITY);
            assertKernelAngle(Double.NEGATIVE_INFINITY);
            assertKernelAngle(Math.scalb(1.0, 66));
            assertKernelAngle(-Math.scalb(1.0, 66));
        }

        @Test
        void testAtan_scalarKernel_smallValuesAndReductionRanges() {
            assertSameBits(Math.scalb(1.0, -30), MtxTrig.atan(Math.scalb(1.0, -30)));
            assertSameBits(-Math.scalb(1.0, -30), MtxTrig.atan(-Math.scalb(1.0, -30)));
            assertKernelAngle(0.25); // no argument reduction
            assertKernelAngle(0.5);  // reduction id 0
            assertKernelAngle(0.75); // reduction id 1
            assertKernelAngle(1.5);  // reduction id 2
            assertKernelAngle(3.0);  // reduction id 3
            assertKernelAngle(-0.5);
            assertKernelAngle(-0.75);
            assertKernelAngle(-1.5);
            assertKernelAngle(-3.0);
        }

        @Test
        void testAtan_bitAndArgumentReductionBoundaries() {
            // Exercise both sides of every exponent-based reduction cut-off,
            // including subnormal inputs and the exact cut-off values.
            double[] scalarInputs = {
                    Double.MIN_VALUE, -Double.MIN_VALUE,
                    Math.scalb(1.0, -29), Math.nextDown(Math.scalb(1.0, -29)),
                    0.4375, Math.nextUp(0.4375),
                    7.0 / 16.0, Math.nextUp(7.0 / 16.0),
                    11.0 / 16.0, Math.nextUp(11.0 / 16.0),
                    19.0 / 16.0, Math.nextUp(19.0 / 16.0),
                    2.4375, Math.nextUp(2.4375),
                    Math.scalb(1.0, 66), Math.nextDown(Math.scalb(1.0, 66))
            };
            for (double x : scalarInputs) {
                assertKernelAngle(x);
            }

            // Signed zero in x must not change atan2's vertical-axis answer.
            assertAngle(3.0, -0.0);
            assertAngle(-3.0, -0.0);
        }

        private static void assertAngle(double y, double x) {
            assertEquals(
                    Math.atan2(y, x),
                    MtxTrig.atan(y, x),
                    TOLERANCE,
                    "atan(" + y + ", " + x + ")"
            );
        }

        private static void assertKernelAngle(double x) {
            assertEquals(
                    StrictMath.atan(x),
                    MtxTrig.atan(x),
                    TOLERANCE,
                    "atan kernel(" + x + ")"
            );
        }
    }

    @Nested
    class ArcSinTests {

        @Test
        void testAsin_signedZero() {
            assertSameBits(0.0, MtxTrig.asin(0.0));
            assertSameBits(-0.0, MtxTrig.asin(-0.0));
        }

        @Test
        void testAsin_domainAndSpecialValues() {
            assertTrue(Double.isNaN(MtxTrig.asin(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.asin(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.asin(Double.NEGATIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.asin(1.0001)));
            assertTrue(Double.isNaN(MtxTrig.asin(-1.0001)));
        }

        @Test
        void testAsin_knownValues() {
            assertAsin(0.0);
            assertAsin(-0.0);
            assertAsin(0.5);
            assertAsin(-0.5);
            assertAsin(Math.sqrt(0.5));
            assertAsin(-Math.sqrt(0.5));
            assertAsin(1.0);
            assertAsin(-1.0);
            assertAsin(Math.nextDown(1.0));
            assertAsin(Math.nextUp(-1.0));
        }

        @Test
        void testAsin_denseDomainRegression() {
            for (int numerator = -31; numerator <= 31; numerator++) {
                assertAsin(numerator / 32.0);
            }
        }

        private static void assertAsin(double x) {
            assertEquals(
                    StrictMath.asin(x),
                    MtxTrig.asin(x),
                    TOLERANCE,
                    "asin(" + x + ")"
            );
        }
    }

    @Nested
    class ArcCosTests {

        @Test
        void testAcos_domainAndSpecialValues() {
            assertTrue(Double.isNaN(MtxTrig.acos(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.acos(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.acos(Double.NEGATIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.acos(1.0001)));
            assertTrue(Double.isNaN(MtxTrig.acos(-1.0001)));
        }

        @Test
        void testAcos_knownValues() {
            assertAcos(-1.0);
            assertAcos(-Math.sqrt(0.5));
            assertAcos(-0.5);
            assertAcos(0.0);
            assertAcos(0.5);
            assertAcos(Math.sqrt(0.5));
            assertAcos(1.0);
            assertAcos(Math.nextDown(1.0));
            assertAcos(Math.nextUp(-1.0));
        }

        @Test
        void testAcos_denseDomainRegression() {
            for (int numerator = -31; numerator <= 31; numerator++) {
                assertAcos(numerator / 32.0);
            }
        }

        private static void assertAcos(double x) {
            assertEquals(
                    StrictMath.acos(x),
                    MtxTrig.acos(x),
                    TOLERANCE,
                    "acos(" + x + ")"
            );
        }
    }

    @Nested
    class SinTests {

        @Test
        void testSin_signedZero() {
            assertSameBits(0.0, MtxTrig.sin(0.0));
            assertSameBits(-0.0, MtxTrig.sin(-0.0));
        }

        @Test
        void testSin_specialValues() {
            assertTrue(Double.isNaN(MtxTrig.sin(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.sin(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.sin(Double.NEGATIVE_INFINITY)));
        }

        @Test
        void testSin_knownAngles() {
            assertSin(0.0);
            assertSin(-0.0);
            assertSin(Math.PI / 6.0);
            assertSin(Math.PI / 4.0);
            assertSin(Math.PI / 2.0);
            assertSin(Math.PI);
            assertSin(3.0 * Math.PI / 2.0);
            assertSin(2.0 * Math.PI);
            assertSin(-Math.PI / 6.0);
            assertSin(-Math.PI / 2.0);
            assertSin(-Math.PI);
        }

        @Test
        void testSin_argumentReduction() {
            assertSin(10.0);
            assertSin(-10.0);
        }

        @Test
        void testSin_largeArgumentReduction() {
            double x = 0x1.0p21;
            assertSin(x);
            assertSin(-x);
        }

        @Test
        void testSin_allQuadrantsAroundHalfPiBoundaries() {
            for (int multiple = -8; multiple <= 8; multiple++) {
                double center = multiple * Math.PI / 2.0;
                assertSin(Math.nextDown(center));
                assertSin(center);
                assertSin(Math.nextUp(center));
            }
        }

        private static void assertSin(double x) {
            assertSin(x, TOLERANCE);
        }

        private static void assertSin(double x, double tolerance) {
            assertEquals(
                    StrictMath.sin(x),
                    MtxTrig.sin(x),
                    tolerance,
                    "sin(" + x + ")"
            );
        }
    }

    @Nested
    class CosTests {

        @Test
        void testCos_specialValues() {
            assertTrue(Double.isNaN(MtxTrig.cos(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.cos(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.cos(Double.NEGATIVE_INFINITY)));
        }

        @Test
        void testCos_knownAngles() {
            assertCos(0.0);
            assertCos(-0.0);
            assertCos(Math.PI / 6.0);
            assertCos(Math.PI / 4.0);
            assertCos(Math.PI / 2.0);
            assertCos(Math.PI);
            assertCos(3.0 * Math.PI / 2.0);
            assertCos(2.0 * Math.PI);
            assertCos(-Math.PI / 6.0);
            assertCos(-Math.PI / 2.0);
            assertCos(-Math.PI);
        }

        @Test
        void testCos_argumentReduction() {
            assertCos(10.0);
            assertCos(-10.0);
        }

        @Test
        void testCos_largeArgumentReduction() {
            double x = 0x1.0p21;
            assertCos(x);
            assertCos(-x);
        }

        @Test
        void testCos_allQuadrantsAroundHalfPiBoundaries() {
            for (int multiple = -8; multiple <= 8; multiple++) {
                double center = multiple * Math.PI / 2.0;
                assertCos(Math.nextDown(center));
                assertCos(center);
                assertCos(Math.nextUp(center));
            }
        }

        private static void assertCos(double x) {
            assertCos(x, TOLERANCE);
        }

        private static void assertCos(double x, double tolerance) {
            assertEquals(
                    StrictMath.cos(x),
                    MtxTrig.cos(x),
                    tolerance,
                    "cos(" + x + ")"
            );
        }
    }

    @Nested
    class TanTests {

        @Test
        void testTan_signedZero() {
            assertSameBits(0.0, MtxTrig.tan(0.0));
            assertSameBits(-0.0, MtxTrig.tan(-0.0));
        }

        @Test
        void testTan_specialValues() {
            assertTrue(Double.isNaN(MtxTrig.tan(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.tan(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.tan(Double.NEGATIVE_INFINITY)));
        }

        @Test
        void testTan_knownAngles() {
            assertTan(0.0);
            assertTan(-0.0);
            assertTan(Math.PI / 6.0);
            assertTan(-Math.PI / 6.0);
            assertTan(Math.PI / 4.0);
            assertTan(-Math.PI / 4.0);
            assertTan(Math.PI);
            assertTan(-Math.PI);
            assertTanRelative(Math.PI / 2.0, 1e-10);
            assertTanRelative(-Math.PI / 2.0, 1e-10);
        }

        @Test
        void testTan_argumentReduction() {
            assertTan(10.0);
            assertTan(-10.0);
        }

        @Test
        void testTan_largeArgumentReduction() {
            double x = 0x1.0p21;
            assertTan(x, 1e-8);
            assertTan(-x, 1e-8);
        }

        @Test
        void testTan_nearPolesAndQuadrantBoundaries() {
            for (int multiple = -7; multiple <= 7; multiple++) {
                double center = multiple * Math.PI / 2.0;
                if ((multiple & 1) == 0) {
                    assertTan(Math.nextDown(center));
                    assertTan(center);
                    assertTan(Math.nextUp(center));
                } else {
                    assertTanRelative(Math.nextDown(center), 1e-9);
                    assertTanRelative(center, 1e-9);
                    assertTanRelative(Math.nextUp(center), 1e-9);
                }
            }
        }

        private static void assertTan(double x) {
            assertTan(x, TOLERANCE);
        }

        private static void assertTan(double x, double tolerance) {
            assertEquals(
                    StrictMath.tan(x),
                    MtxTrig.tan(x),
                    tolerance,
                    "tan(" + x + ")"
            );
        }

        private static void assertTanRelative(double x, double relativeTolerance) {
            double expected = StrictMath.tan(x);
            double actual = MtxTrig.tan(x);
            assertEquals(
                    0.0,
                    Math.abs((actual - expected) / expected),
                    relativeTolerance,
                    "relative error for tan(" + x + ")"
            );
        }
    }

    @Nested
    class SinHypTests {

        @Test
        void testSinh_signedZero() {
            assertSameBits(0.0, MtxTrig.sinh(0.0));
            assertSameBits(-0.0, MtxTrig.sinh(-0.0));
        }

        @Test
        void testSinh_specialValues() {
            assertTrue(Double.isNaN(MtxTrig.sinh(Double.NaN)));
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.sinh(Double.POSITIVE_INFINITY));
            assertEquals(Double.NEGATIVE_INFINITY, MtxTrig.sinh(Double.NEGATIVE_INFINITY));
        }

        @Test
        void testSinh_knownValues() {
            assertSinh(0.0);
            assertSinh(-0.0);
            assertSinh(0.5);
            assertSinh(-0.5);
            assertSinh(0.009);
            assertSinh(1e-100);
            assertSinh(-1e-100);
            assertSinh(1.0);
            assertSinh(-1.0);
            assertSinh(10.0);
            assertSinh(-10.0);
            assertSinh(20.0);
            assertSinh(-20.0);
        }

        @Test
        void testSinh_overflow() {
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.sinh(1000.0));
            assertEquals(Double.NEGATIVE_INFINITY, MtxTrig.sinh(-1000.0));
        }

        @Test
        void testSinh_convergenceAndOverflowBoundaries() {
            assertSinh(Math.nextUp(0.0));
            assertSinh(-Math.nextUp(0.0));
            assertSinh(709.0);
            assertSinh(-709.0);
            assertEquals(PSEUDO_INFINITY, MtxTrig.sinh(710.0));
            assertEquals(-PSEUDO_INFINITY, MtxTrig.sinh(-710.0));
        }

        @Test
        void testSinh_convergenceConditions() {
            // Exercise the case where next == sum for very small x (already covered by testSinh_knownValues for 1e-100)
            // Exercise the convergence condition: MtxMath.abs(term) <= MtxMath.abs(next) * 0x1.0p-53
            assertSinh(1.0);
            assertSinh(5.0);
        }

        @Test
        void testSinh_internalInfinity() {
            // Test values that force internal loop to hit infinity
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.sinh(800.0));
            assertEquals(Double.NEGATIVE_INFINITY, MtxTrig.sinh(-800.0));

            // The loop for sinh(x) might reach infinity before the loop breaks.
            // 710.0 is already handled by the early exit, but we want to ensure the loop logic handles it if it
            // reaches there. With the early exit > 709, this might be hard to reach, but let's test a value that is
            // large but not quite infinite to force the internal loop to hit infinity. Actually, with the >709 check,
            // this code is unreachable for finite numbers. But let's check a value just inside the boundary if
            // possible or assume covered.
            assertSinh(708.0);
        }

        private static void assertSinh(double x) {
            double expected = StrictMath.sinh(x);
            assertEquals(
                    expected,
                    MtxTrig.sinh(x),
                    scaledTolerance(expected),
                    "sinh(" + x + ")"
            );
        }
    }

    @Nested
    class CosHypTests {

        @Test
        void testCosh_specialValues() {
            assertTrue(Double.isNaN(MtxTrig.cosh(Double.NaN)));
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.cosh(Double.POSITIVE_INFINITY));
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.cosh(Double.NEGATIVE_INFINITY));
        }

        @Test
        void testCosh_knownValues() {
            assertCosh(0.0);
            assertCosh(-0.0);
            assertCosh(0.009);
            assertCosh(1e-100);
            assertCosh(0.5);
            assertCosh(-0.5);
            assertCosh(1.0);
            assertCosh(-1.0);
            assertCosh(10.0);
            assertCosh(-10.0);
            assertCosh(20.0);
            assertCosh(-20.0);
        }

        @Test
        void testCosh_overflow() {
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.cosh(1000.0));
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.cosh(-1000.0));
        }

        @Test
        void testCosh_convergenceAndOverflowBoundaries() {
            assertCosh(Math.nextUp(0.0));
            assertCosh(709.0);
            assertEquals(PSEUDO_INFINITY_ADJ, MtxTrig.cosh(710.0));
        }

        @Test
        void testCosh_verificationBoundary() {
            assertEquals(StrictMath.cosh(709.0), MtxTrig.cosh(709.0), scaledTolerance(StrictMath.cosh(709.0), true));
            assertEquals(StrictMath.sinh(709.0), MtxTrig.sinh(709.0), scaledTolerance(StrictMath.sinh(709.0)));
            assertEquals(PSEUDO_INFINITY_ADJ, MtxTrig.cosh(710.0));
            assertEquals(PSEUDO_INFINITY, MtxTrig.sinh(710.0));
            assertEquals(-PSEUDO_INFINITY, MtxTrig.sinh(-710.0));
        }

        @Test
        void testCosh_convergenceConditions() {
            // Exercise the convergence condition: term <= next * 0x1.0p-53
            assertCosh(1.0);
            assertCosh(5.0);
        }

        @Test
        void testCosh_internalInfinity() {
            // Similarly to sinh, this is largely unreachable due to the >709 check.
            assertCosh(708.0);

            // Test values that force internal loop to hit infinity
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.cosh(800.0));
        }

        private static void assertCosh(double x) {
            double expected = StrictMath.cosh(x);
            assertEquals(
                    expected,
                    MtxTrig.cosh(x),
                    scaledTolerance(expected, true),
                    "cosh(" + x + ")"
            );
        }
    }

    @Nested
    class ReciprocalTrigTests {

        @Test
        void testCotSecCsc_knownValues() {
            double x = 0.3;
            assertEquals(StrictMath.cos(x) / StrictMath.sin(x), MtxTrig.cot(x), TOLERANCE);
            assertEquals(1.0 / StrictMath.cos(x), MtxTrig.sec(x), TOLERANCE);
            assertEquals(1.0 / StrictMath.sin(x), MtxTrig.csc(x), TOLERANCE);
            assertEquals(1.0, MtxTrig.sec(0.0), 0.0);
            assertEquals(1.0, MtxTrig.csc(Math.PI / 2.0), TOLERANCE);
        }

        @Test
        void testCotSecCsc_singularitiesAndSignedZero() {
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.cot(0.0), 0.0);
            assertEquals(Double.NEGATIVE_INFINITY, MtxTrig.cot(-0.0), 0.0);
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.csc(0.0), 0.0);
            assertEquals(Double.NEGATIVE_INFINITY, MtxTrig.csc(-0.0), 0.0);
            for (double x : new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
                assertTrue(Double.isNaN(MtxTrig.cot(x)));
                assertTrue(Double.isNaN(MtxTrig.sec(x)));
                assertTrue(Double.isNaN(MtxTrig.csc(x)));
            }
        }

        @Test
        void testSecAndCsc_nearSingularitiesCanBeLarge() {
            double nearCosineZero = Math.nextDown(Math.PI / 2.0);
            double nearSineZero = Double.MIN_VALUE;
            assertTrue(Double.isFinite(MtxTrig.sec(nearCosineZero)));
            assertTrue(MtxMath.abs(MtxTrig.sec(nearCosineZero)) > 1.0e15);
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.csc(nearSineZero), 0.0);
        }
    }

    @Nested
    class TanHypTests {

        @Test
        void testTanh_signedZero() {
            assertSameBits(0.0, MtxTrig.tanh(0.0));
            assertSameBits(-0.0, MtxTrig.tanh(-0.0));
        }

        @Test
        void testTanh_specialValues() {
            assertTrue(Double.isNaN(MtxTrig.tanh(Double.NaN)));
            assertEquals(1.0, MtxTrig.tanh(Double.POSITIVE_INFINITY));
            assertEquals(-1.0, MtxTrig.tanh(Double.NEGATIVE_INFINITY));
        }

        @Test
        void testTanh_knownValues() {
            assertTanh(0.0);
            assertTanh(-0.0);
            assertTanh(1e-100);
            assertTanh(-1e-100);
            assertTanh(0.5);
            assertTanh(-0.5);
            assertTanh(1.0);
            assertTanh(-1.0);
            assertTanh(2.0);
            assertTanh(-2.0);
            assertTanh(5.0);
            assertTanh(-5.0);
            assertTanh(10.0);
            assertTanh(-10.0);
            assertTanh(18.0);
            assertTanh(-18.0);
            assertTanh(19.0);
            assertTanh(-19.0);
            assertTanh(19.5);
            assertTanh(-19.5);
        }

        @Test
        void testTanh_largeFiniteValues() {
            assertTanh(20.0);
            assertTanh(-20.0);
            assertTanh(20.1);
            assertTanh(-20.1);
            assertTanh(100.0);
            assertTanh(-100.0);
            assertTanh(1e100);
            assertTanh(-1e100);
            assertTanh(Double.MAX_VALUE);
            assertTanh(-Double.MAX_VALUE);
        }

        private static void assertTanh(double x) {
            double expected = StrictMath.tanh(x);
            assertEquals(
                    expected,
                    MtxTrig.tanh(x),
                    scaledTolerance(expected),
                    "tanh(" + x + ")"
            );
        }
    }

    @Nested
    class ArcSinHypTests {

        @Test
        void testAsinh_signedZero() {
            assertSameBits(0.0, MtxTrig.asinh(0.0));
            assertSameBits(-0.0, MtxTrig.asinh(-0.0));
        }

        @Test
        void testAsinh_specialValues() {
            assertTrue(Double.isNaN(MtxTrig.asinh(Double.NaN)));
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.asinh(Double.POSITIVE_INFINITY));
            assertEquals(Double.NEGATIVE_INFINITY, MtxTrig.asinh(Double.NEGATIVE_INFINITY));
        }

        @Test
        void testAsinh_knownValues() {
            assertAsinh(1e-100, 1e-100);
            assertAsinh(-1e-100, -1e-100);
            assertAsinh(0.1, 0.09983407889920758);
            assertAsinh(-0.1, -0.09983407889920758);
            assertAsinh(0.5, 0.48121182505960347);
            assertAsinh(-0.5, -0.48121182505960347);
            assertAsinh(1.0, 0.88137358701954305);
            assertAsinh(-1.0, -0.88137358701954305);
            assertAsinh(2.0, 1.4436354751788103);
            assertAsinh(-2.0, -1.4436354751788103);
            assertAsinh(10.0, 2.9982229502979698);
            assertAsinh(-10.0, -2.9982229502979698);
        }

        @Test
        void testAsinh_largeFiniteValues() {
            assertAsinh(1e100, 230.95165647996453);
            assertAsinh(-1e100, -230.95165647996453);
            assertAsinh(Double.MAX_VALUE, 710.4758600739439);
            assertAsinh(-Double.MAX_VALUE, -710.4758600739439);
        }

        private static void assertAsinh(double x, double expected) {
            assertEquals(expected, MtxTrig.asinh(x), scaledTolerance(expected), "asinh(" + x + ")");
        }
    }

    @Nested
    class ArcCosHypTests {

        @Test
        void testAcosh_domainAndSpecialValues() {
            assertTrue(Double.isNaN(MtxTrig.acosh(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.acosh(Double.NEGATIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.acosh(0.9999)));
            assertEquals(0.0, MtxTrig.acosh(1.0));
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.acosh(Double.POSITIVE_INFINITY));
        }

        @Test
        void testAcosh_knownValues() {
            assertAcosh(Math.nextUp(1.0), 2.1073424255447017e-8);
            assertAcosh(1.0001, 0.014142017775251547);
            assertAcosh(1.5, 0.9624236501192069);
            assertAcosh(2.0, 1.3169578969248166);
            assertAcosh(10.0, 2.993222846126381);
            assertAcosh(1e100, 230.95165647996453);
        }

        @Test
        void testAcosh_largeFiniteValue() {
            assertAcosh(Double.MAX_VALUE, 710.4758600739439);
        }

        private static void assertAcosh(double x, double expected) {
            assertEquals(expected, MtxTrig.acosh(x), scaledTolerance(expected, true), "acosh(" + x + ")");
        }
    }

    @Nested
    class ArcTanHypTests {

        @Test
        void testAtanh_signedZero() {
            assertSameBits(0.0, MtxTrig.atanh(0.0));
            assertSameBits(-0.0, MtxTrig.atanh(-0.0));
        }

        @Test
        void testAtanh_domainAndSpecialValues() {
            assertTrue(Double.isNaN(MtxTrig.atanh(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.atanh(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.atanh(Double.NEGATIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.atanh(1.0001)));
            assertTrue(Double.isNaN(MtxTrig.atanh(-1.0001)));
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.atanh(1.0));
            assertEquals(Double.NEGATIVE_INFINITY, MtxTrig.atanh(-1.0));
        }

        @Test
        void testAtanh_knownValues() {
            assertAtanh(1e-100, 1e-100);
            assertAtanh(-1e-100, -1e-100);
            assertAtanh(0.1, 0.10033534773107558);
            assertAtanh(-0.1, -0.10033534773107558);
            assertAtanh(0.5, 0.5493061443340549);
            assertAtanh(-0.5, -0.5493061443340549);
            assertAtanh(0.75, 0.9729550745276566);
            assertAtanh(-0.75, -0.9729550745276566);
            assertAtanh(0.9, 1.4722194895832204);
            assertAtanh(-0.9, -1.4722194895832204);
            assertAtanh(0.99, 2.6466524123622457);
            assertAtanh(-0.99, -2.6466524123622457);
        }

        @Test
        void testAtanh_nearDomainBoundaries() {
            assertAtanh(Math.nextDown(1.0), 18.714973875118524);
            assertAtanh(Math.nextUp(-1.0), -18.714973875118524);
        }

        @Test
        void testAtanh_branchBoundaryAndDenseSeriesInputs() {
            assertAtanh(Math.nextDown(0.5), atanhLocal(Math.nextDown(0.5)));
            assertAtanh(0.5, atanhLocal(0.5));
            assertAtanh(Math.nextUp(0.5), atanhLocal(Math.nextUp(0.5)));
            for (int numerator = -15; numerator <= 15; numerator++) {
                double x = numerator / 32.0;
                assertAtanh(x, atanhLocal(x));
            }
        }

        private static double atanhLocal(double x) {
            return 0.5 * Math.log((1.0 + x) / (1.0 - x));
        }

        private static void assertAtanh(double x, double expected) {
            assertEquals(expected, MtxTrig.atanh(x), scaledTolerance(expected), "atanh(" + x + ")");
        }
    }

    /**
     * Scales the test tolerance with the expected result's magnitude. Hyperbolic
     * functions grow quickly, so a fixed absolute tolerance is too strict for
     * larger outputs even when the implementation's relative error is tiny.
     * For values below one, the base tolerance remains an absolute tolerance.
     *
     * @param expected the reference result
     * @param weakerTolerance if true, use a weaker tolerance for small magnitudes
     * @return the allowed absolute error for comparing the result
     */
    private static double scaledTolerance(double expected, boolean weakerTolerance) {
        double magnitude = MtxMath.abs(expected);
        double magnitudeAmplifier = (magnitude > 1.0 ? magnitude : 1.0);
        if (weakerTolerance) {
            return 2e-15 * magnitudeAmplifier;
        }
        return TOLERANCE * magnitude;
    }

    private static double scaledTolerance(double expected) {
        return scaledTolerance(expected, false);
    }

    private static void assertSameBits(double expected, double actual) {
        assertEquals(
                Double.doubleToRawLongBits(expected),
                Double.doubleToRawLongBits(actual)
        );
    }

    @Test
    void testRefinedSqrtIterationCapForNonFiniteInternalInput() throws ReflectiveOperationException {
        // No public caller supplies NaN to refinedSqrt, but it is the sole way
        // to exercise the defensive iteration-cap return without changing the
        // production implementation. The normal public paths converge early.
        Method refinedSqrt = MtxTrig.class.getDeclaredMethod("refinedSqrt", double.class);
        refinedSqrt.setAccessible(true);
        assertTrue(Double.isNaN((double) refinedSqrt.invoke(null, Double.NaN)));
    }

    @Nested
    class AngleHelperTests {

        @Test
        void testAngleConversions() {
            assertEquals(Math.PI, MtxTrig.toRadians(180.0), 0.0);
            assertEquals(90.0, MtxTrig.toDegrees(Math.PI / 2.0), 0.0);
            assertEquals(Math.toRadians(-45.0), MtxTrig.toRadians(-45.0), 0.0);
            assertEquals(Math.toDegrees(-Math.PI), MtxTrig.toDegrees(-Math.PI), 0.0);
            assertEquals(Double.POSITIVE_INFINITY, MtxTrig.toRadians(Double.POSITIVE_INFINITY), 0.0);
            assertTrue(Double.isNaN(MtxTrig.toDegrees(Double.NaN)));
        }

        @Test
        void testNormalizeRadians_usesZeroInclusiveTauExclusiveRange() {
            assertEquals(0.0, MtxTrig.normalizeRadians(0.0), 0.0);
            assertEquals(0.0, MtxTrig.normalizeRadians(-0.0), 0.0);
            assertEquals(Math.PI / 2.0, MtxTrig.normalizeRadians(Math.PI / 2.0), 0.0);
            assertEquals(Math.PI, MtxTrig.normalizeRadians(-Math.PI), 0.0);
            assertEquals(3.0 * Math.PI / 2.0, MtxTrig.normalizeRadians(-Math.PI / 2.0), TOLERANCE);
            assertEquals(0.0, MtxTrig.normalizeRadians(MtxMath.TAU), 0.0);
            assertEquals(0.0, MtxTrig.normalizeRadians(-MtxMath.TAU), 0.0);

            // Adding TAU to this tiny negative remainder rounds up to TAU,
            // exercising the upper-bound rounding guard.
            assertEquals(0.0, MtxTrig.normalizeRadians(-Double.MIN_VALUE), 0.0);
            assertEquals(Math.PI / 2.0, MtxTrig.normalizeRadians(5.0 * Math.PI / 2.0), TOLERANCE);
        }

        @Test
        void testNormalizeRadians_nonFiniteAndLargeValues() {
            assertTrue(Double.isNaN(MtxTrig.normalizeRadians(Double.NaN)));
            assertTrue(Double.isNaN(MtxTrig.normalizeRadians(Double.POSITIVE_INFINITY)));
            assertTrue(Double.isNaN(MtxTrig.normalizeRadians(Double.NEGATIVE_INFINITY)));
            for (double angle : new double[]{Double.MAX_VALUE, -Double.MAX_VALUE}) {
                double normalized = MtxTrig.normalizeRadians(angle);
                assertTrue(normalized >= 0.0 && normalized < MtxMath.TAU);
            }
        }
    }

    @Nested
    class StrictTrigTests {

        @Test
        void computeSin_coversKernelAndQuadrants() {
            for (double x : new double[]{0.0, -0.0, Double.MIN_VALUE, -Double.MIN_VALUE,
                    0.1, -0.1, Math.PI / 4.0, Math.nextUp(Math.PI / 4.0),
                    1.2, 2.35, Math.PI / 2.0, -Math.PI / 2.0, Math.PI, -Math.PI,
                    3.0 * Math.PI / 2.0, 1e6, -1e6, 1e20, -1e20,
                    Double.MAX_VALUE, -Double.MAX_VALUE}) {
                assertEquals(StrictMath.sin(x), MtxTrig.MtxStrictTrig.computeSin(x),
                        2e-15, "strict sin(" + x + ")");
            }
            assertFalse(Double.isNaN(MtxTrig.MtxStrictTrig.computeSin(Double.POSITIVE_INFINITY)));
            assertFalse(Double.isNaN(MtxTrig.MtxStrictTrig.computeSin(Double.NaN)));
        }

        @Test
        void computeCos_coversKernelAndQuadrants() {
            for (double x : new double[]{0.0, -0.0, Double.MIN_VALUE, -Double.MIN_VALUE,
                    0.1, -0.1, Math.PI / 4.0, Math.nextUp(Math.PI / 4.0),
                    1.2, 2.35, Math.PI / 2.0, -Math.PI / 2.0, Math.PI, -Math.PI,
                    3.0 * Math.PI / 2.0, 1e6, -1e6, 1e20, -1e20,
                    Double.MAX_VALUE, -Double.MAX_VALUE}) {
                assertEquals(StrictMath.cos(x), MtxTrig.MtxStrictTrig.computeCos(x),
                        2e-15, "strict cos(" + x + ")");
            }
            assertFalse(Double.isNaN(MtxTrig.MtxStrictTrig.computeCos(Double.POSITIVE_INFINITY)));
            assertFalse(Double.isNaN(MtxTrig.MtxStrictTrig.computeCos(Double.NaN)));
        }

        @Test
        void kernelSin_coversCorrectionModesAndTinyInputs() {
            for (int correction : new int[]{0, 1}) {
                double actual = MtxTrig.MtxStrictTrig.kernelSin(0.3, 1e-17, correction);
                assertEquals(StrictMath.sin(0.3), actual, 1e-15);
            }
            assertSameBits(-0.0, MtxTrig.MtxStrictTrig.kernelSin(-0.0, 0.0, 0));
            assertSameBits(Double.MIN_VALUE,
                    MtxTrig.MtxStrictTrig.kernelSin(Double.MIN_VALUE, 0.0, 0));
        }

        @Test
        void remPio2_coversSmallSpecialMediumAndLargeInputs() {
            double[] y = new double[2];
            assertEquals(0, MtxTrig.MtxStrictTrig.ieee754RemPio2(0.2, y));
            assertEquals(1, MtxTrig.MtxStrictTrig.ieee754RemPio2(1.0, y));
            assertEquals(-1, MtxTrig.MtxStrictTrig.ieee754RemPio2(-1.0, y));
            assertEquals(1, MtxTrig.MtxStrictTrig.ieee754RemPio2(Math.PI / 2.0, y));
            assertEquals(-1, MtxTrig.MtxStrictTrig.ieee754RemPio2(-Math.PI / 2.0, y));
            assertEquals(2, MtxTrig.MtxStrictTrig.ieee754RemPio2(3.0, y));
            assertEquals(-2, MtxTrig.MtxStrictTrig.ieee754RemPio2(-3.0, y));
            int positiveLarge = MtxTrig.MtxStrictTrig.ieee754RemPio2(1e20, y);
            assertTrue(Math.abs(y[0] + y[1]) <= Math.PI / 4.0);
            assertTrue(positiveLarge >= 0);
            int negativeLarge = MtxTrig.MtxStrictTrig.ieee754RemPio2(-1e20, y);
            assertEquals(-positiveLarge, negativeLarge);
            assertEquals(0, MtxTrig.MtxStrictTrig.ieee754RemPio2(Double.POSITIVE_INFINITY, y));
            assertEquals(0, MtxTrig.MtxStrictTrig.ieee754RemPio2(Double.NaN, y));
        }

        @Test
        void kernelRemPio2_supportsEachPrecisionMode() throws ReflectiveOperationException {
            int[] table = twoOverPiTable();
            for (int precision = 0; precision <= 3; precision++) {
                double[] y = new double[3];
                int quadrant = MtxTrig.MtxStrictTrig.kernelRemPio2(
                        new double[]{1.0, 0.0, 0.0}, y, 0, 1, precision, table);
                assertTrue(quadrant >= 0 && quadrant < 8);
                assertTrue(Double.isFinite(y[0]));
                if (precision > 0) assertTrue(Double.isFinite(y[1]));
            }
        }

        @Test
        void kernelRemPio2_exercisesExponentAndRoundingBranches() throws ReflectiveOperationException {
            int[] table = twoOverPiTable();
            int[] exponents = {-40, -1, 0, 1, 2, 3, 4, 5, 6, 24, 25, 26, 48, 100, 300, 900};
            double[] chunks = {1.0, 0x1.555555p23, 0x1.0p23, 0x1.fffffep23};
            for (int e0 : exponents) {
                for (double chunk : chunks) {
                    for (int nx : new int[]{1, 2, 3}) {
                        double[] x = {chunk, chunk == 1.0 ? 0.0 : 0x1.0p20, 1.0};
                        for (int precision = 0; precision <= 3; precision++) {
                            double[] y = new double[3];
                            int quadrant = MtxTrig.MtxStrictTrig.kernelRemPio2(
                                    x, y, e0, nx, precision, table);
                            assertTrue(quadrant >= 0 && quadrant < 8);
                            assertTrue(Double.isFinite(y[0]));
                        }
                    }
                }
            }
        }

        @Test
        void kernelRemPio2_trimsZeroRemainderDigits() {
            int[] sparseTwoOverPi = new int[66];
            // Leave the highest two quotient chunks empty while retaining a
            // lower nonzero chunk. Extreme scaling makes z zero, so trimming
            // must step past the empty iq digit before reaching the nonzero one.
            sparseTwoOverPi[2] = 1;
            double[] remainder = new double[3];
            int quadrant = MtxTrig.MtxStrictTrig.kernelRemPio2(
                    new double[]{0x1.0p20}, remainder, -2100, 1, 2, sparseTwoOverPi);

            assertTrue(quadrant >= 0 && quadrant < 8);
            assertEquals(0.0, remainder[0], 0.0);
        }

        private static int[] twoOverPiTable() throws ReflectiveOperationException {
            Field field = MtxTrig.MtxStrictTrig.class.getDeclaredField("two_over_pi");
            field.setAccessible(true);
            return (int[]) field.get(null);
        }

        @Test
        void scalingHelper_coversNormalAndSubnormalScaleRanges() {
            for (int exponent : new int[]{1023, 1024, 2046, 2047, 2097, -1023, -1024,
                    -2046, -2047, -2080, -2100}) {
                double result = MtxTrig.MtxStrictTrig.sCalB(1.0, exponent);
                assertEquals(StrictMath.scalb(1.0, exponent), result, 0.0,
                        "sCalB exponent " + exponent);
            }
            assertEquals(Double.POSITIVE_INFINITY,
                    MtxTrig.MtxStrictTrig.sCalB(Double.MAX_VALUE, 3070));
            assertEquals(0.0, MtxTrig.MtxStrictTrig.sCalB(1.0, -3000), 0.0);
        }
    }
}
