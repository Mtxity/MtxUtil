package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxHypotTest {
    private static final double STRICT_TOL = 1e-15;

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

        double expected = Math.hypot(x, y);

        assertEquals(expected, MtxHypot.compute(x, y),
                expected * 1e-15);
    }

    @Test
    void handlesVerySmallNumbersWithoutUnderflow() {
        double x = Double.MIN_VALUE;
        double y = Double.MIN_VALUE;

        assertEquals(1.295163E-318, MtxHypot.compute(x, y), 0.0);
    }

    @Test
    void handlesNormalTinyNumbers() {
        double x = 1e-300;
        double y = 2e-300;

        double expected = 1.402757983365378E-191;

        assertEquals(
                expected,
                MtxHypot.compute(x, y),
                expected * 1e-15
        );
    }

    @Test
    void handlesLargeRatioShortcut() {
        double x = Math.scalb(1.0, 100);
        double y = Math.scalb(1.0, 20);

        double result = MtxHypot.compute(x, y);

        // Difference is beyond 60 exponent bits, so implementation returns a+b.
        assertEquals(x + y, result);
    }
}
