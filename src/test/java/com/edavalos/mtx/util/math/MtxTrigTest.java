package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MtxTrigTest {

    private static final double TOLERANCE = 1e-15;

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
            assertSin(x, 1e-9);
            assertSin(-x, 1e-9);
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
            assertCos(x, 1e-9);
            assertCos(-x, 1e-9);
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

        private static void assertCosh(double x) {
            double expected = StrictMath.cosh(x);
            assertEquals(
                    expected,
                    MtxTrig.cosh(x),
                    scaledTolerance(expected),
                    "cosh(" + x + ")"
            );
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
            assertEquals(expected, MtxTrig.acosh(x), scaledTolerance(expected), "acosh(" + x + ")");
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
     * @return the allowed absolute error for comparing the result
     */
    private static double scaledTolerance(double expected) {
        double magnitude = MtxMath.abs(expected);
        return TOLERANCE * (magnitude > 1.0 ? magnitude : 1.0);
    }

    private static void assertSameBits(double expected, double actual) {
        assertEquals(
                Double.doubleToRawLongBits(expected),
                Double.doubleToRawLongBits(actual)
        );
    }
}
