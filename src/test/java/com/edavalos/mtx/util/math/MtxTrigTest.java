package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MtxTrigTest {

    private static final double TOLERANCE = 1e-15;

    @Nested
    class AtanTests {

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
    }

    private static void assertAngle(double y, double x) {
        assertEquals(
                Math.atan2(y, x),
                MtxTrig.atan(y, x),
                TOLERANCE,
                "atan(" + y + ", " + x + ")"
        );
    }

    private static void assertSameBits(double expected, double actual) {
        assertEquals(
                Double.doubleToRawLongBits(expected),
                Double.doubleToRawLongBits(actual)
        );
    }
}
