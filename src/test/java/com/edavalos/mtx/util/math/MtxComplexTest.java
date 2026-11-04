package com.edavalos.mtx.util.math;

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
}
