package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MtxComplexTest {

    private static final double TOLERANCE = 1e-9;

    @Test
    void computesAbsoluteValue() {
        assertEquals(5.0, new MtxComplex(3.0, 4.0).abs(), TOLERANCE);
    }

    @Test
    void addsComplexNumbers() {
        MtxComplex result = new MtxComplex(3.0, 4.0).plus(new MtxComplex(1.0, -2.0));

        assertEquals(Math.sqrt(20.0), result.abs(), TOLERANCE);
    }

    @Test
    void subtractsComplexNumbers() {
        MtxComplex result = new MtxComplex(3.0, 4.0).minus(new MtxComplex(1.0, -2.0));

        assertEquals(Math.sqrt(40.0), result.abs(), TOLERANCE);
    }

    @Test
    void multipliesComplexNumbers() {
        MtxComplex result = new MtxComplex(3.0, 4.0).times(new MtxComplex(1.0, -2.0));

        assertEquals(Math.sqrt(125.0), result.abs(), TOLERANCE);
    }

    @Test
    void conjugatesComplexNumbers() {
        MtxComplex value = new MtxComplex(3.0, 4.0);

        assertEquals(6.0, value.plus(value.conjugate()).abs(), TOLERANCE);
        assertEquals(8.0, value.minus(value.conjugate()).abs(), TOLERANCE);
    }

    @Test
    void dividesComplexNumbers() {
        MtxComplex dividend = new MtxComplex(3.0, 4.0);
        MtxComplex divisor = new MtxComplex(1.0, -2.0);

        MtxComplex quotient = dividend.divide(divisor);

        assertEquals(Math.sqrt(5.0), quotient.abs(), TOLERANCE);
        assertEquals(dividend.abs(), quotient.times(divisor).abs(), TOLERANCE);
    }

    @Test
    void rejectsDivisionByZero() {
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> new MtxComplex(3.0, 4.0).divide(new MtxComplex(0.0, 0.0))
        );

        assertEquals("Division by zero", exception.getMessage());
    }

    @Nested
    class ToStringTests {

        @Test
        void testToString_realNumber() {
            MtxComplex complex = new MtxComplex(3.0, 0.0);
            assertEquals("3.000", complex.toString());
        }

        @Test
        void testToString_imaginaryNumber() {
            MtxComplex complex = new MtxComplex(3.0, 1.5);
            assertEquals("3.000 + 1.500i", complex.toString());
        }

        @Test
        void testToString_realNaN() {
            MtxComplex complex1 = new MtxComplex(Double.NaN, 0.0);
            assertEquals("NaN", complex1.toString());
            MtxComplex complex2 = new MtxComplex(Double.NaN, 1.0);
            assertEquals("NaN + 1.000i", complex2.toString());
        }

        @Test
        void testToString_imaginaryNaN() {
            MtxComplex complex = new MtxComplex(3.0, Double.NaN);
            assertEquals("3.000 + NaNi", complex.toString());
        }

        @Test
        void testToString_bothNaN() {
            MtxComplex complex = new MtxComplex(Double.NaN, Double.NaN);
            assertEquals("NaN + NaNi", complex.toString());
        }
    }
}
