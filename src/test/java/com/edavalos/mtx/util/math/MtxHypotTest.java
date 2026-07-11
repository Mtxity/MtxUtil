package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxHypotTest {

    private static final double STRICT_TOL = 1e-15;
    private static final double NUMERIC_TOL = 1e-12;

    @Test
    void computesSimple3_4_5() {
        assertEquals(5.0, MtxHypot.compute(3.0, 4.0), STRICT_TOL);
    }

    @Test
    void computesRegardlessOfArgumentOrder() {
        assertEquals(
                MtxHypot.compute(3.0, 4.0),
                MtxHypot.compute(4.0, 3.0),
                0.0);
    }

    @Test
    void ignoresSigns() {
        double expected = Math.hypot(3.0, 4.0);

        assertEquals(expected, MtxHypot.compute(-3.0, 4.0), STRICT_TOL);
        assertEquals(expected, MtxHypot.compute(3.0, -4.0), STRICT_TOL);
        assertEquals(expected, MtxHypot.compute(-3.0, -4.0), STRICT_TOL);
    }

    @Test
    void returnsZeroForZeroInputs() {
        assertEquals(0.0, MtxHypot.compute(0.0, 0.0));
    }

    @Test
    void returnsAbsoluteValueWhenOneArgumentIsZero() {
        assertEquals(5.0, MtxHypot.compute(5.0, 0.0));
        assertEquals(5.0, MtxHypot.compute(0.0, -5.0));
    }

    @Test
    void handlesPositiveInfinity() {
        assertEquals(Double.POSITIVE_INFINITY,
                MtxHypot.compute(Double.POSITIVE_INFINITY, 1.0));

        assertEquals(Double.POSITIVE_INFINITY,
                MtxHypot.compute(1.0, Double.POSITIVE_INFINITY));
    }

    @Test
    void infinityDominatesNaN() {
        assertEquals(Double.POSITIVE_INFINITY,
                MtxHypot.compute(Double.POSITIVE_INFINITY, Double.NaN));
    }

    @Test
    void propagatesNaN() {
        assertTrue(Double.isNaN(MtxHypot.compute(Double.NaN, 1.0)));
        assertTrue(Double.isNaN(MtxHypot.compute(1.0, Double.NaN)));
        assertTrue(Double.isNaN(MtxHypot.compute(Double.NaN, Double.NaN)));
    }

    @Test
    void handlesVeryLargeNumbersWithoutOverflow() {
        double x = 1e308;
        double y = 1e308;

        assertEquals(Math.hypot(x, y),
                MtxHypot.compute(x, y),
                NUMERIC_TOL);
    }

    @Test
    void handlesNormalTinyNumbers() {
        double x = 1e-300;
        double y = 2e-300;

        assertEquals(Math.hypot(x, y),
                MtxHypot.compute(x, y),
                NUMERIC_TOL);
    }

    @Test
    void handlesLargeRatioShortcut() {
        double x = Math.scalb(1.0, 100);
        double y = Math.scalb(1.0, 20);

        assertEquals(x + y, MtxHypot.compute(x, y));
    }

    @Test
    void executesWideGapBranch() {
        // a = 10, b = 2 -> w = 8 > b
        assertEquals(Math.hypot(10.0, 2.0),
                MtxHypot.compute(10.0, 2.0),
                NUMERIC_TOL);
    }

    @Test
    void executesEqualityBoundaryBranch() {
        // a = 2, b = 1 -> w == b
        assertEquals(Math.hypot(2.0, 1.0),
                MtxHypot.compute(2.0, 1.0),
                NUMERIC_TOL);
    }

    @Test
    void scalesUpNormalTinyNumbers() {
        double x = Math.scalb(1.0, -700);
        double y = Math.scalb(1.0, -700);

        assertEquals(Math.hypot(x, y),
                MtxHypot.compute(x, y),
                NUMERIC_TOL);
    }

    @Test
    void handlesEqualArguments() {
        assertEquals(Math.hypot(8.0, 8.0),
                MtxHypot.compute(8.0, 8.0),
                NUMERIC_TOL);
    }

    @Test
    void handlesMaxFiniteValues() {
        double x = Double.MAX_VALUE;
        double y = Double.MAX_VALUE;

        assertEquals(Math.hypot(x, y),
                MtxHypot.compute(x, y),
                NUMERIC_TOL);
    }

    @Test
    void handlesMinNormalValues() {
        double x = Double.MIN_NORMAL;
        double y = Double.MIN_NORMAL;

        assertEquals(Math.hypot(x, y),
                MtxHypot.compute(x, y),
                NUMERIC_TOL);
    }

    @Test
    void handlesMixedLargeAndSmallValues() {
        double x = 1e300;
        double y = 1.0;

        assertEquals(Math.hypot(x, y),
                MtxHypot.compute(x, y),
                NUMERIC_TOL);
    }
}
