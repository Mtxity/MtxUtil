package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MtxComplexTest {

    private static final double TOLERANCE = 1e-9;

    @Test
    void computesAbsoluteValue() {
        assertEquals(5.0, new MtxComplex(3.0, 4.0).abs(), TOLERANCE);
    }

    @Test
    void negatesComplexNumbers() {
        MtxComplex result = new MtxComplex(3.0, 4.0).negate();

        assertEquals(-3.0, result.real());
        assertEquals(-4.0, result.imaginary());
    }

    @Test
    void negatesComplexNumbersNegative() {
        MtxComplex result = new MtxComplex(-3.0, -4.0).negate();

        assertEquals(3.0, result.real());
        assertEquals(4.0, result.imaginary());
    }

    @Test
    void computesSquaredAbsoluteValue() {
        assertEquals(
                25.0,
                new MtxComplex(3.0, 4.0).absSquared(),
                TOLERANCE
        );
    }

    @Test
    void computesArgument() {
        assertEquals(
                Math.atan2(4.0, 3.0),
                new MtxComplex(3.0, 4.0).argument(),
                TOLERANCE
        );
    }

    @Test
    void computesReciprocal() {
        MtxComplex result = new MtxComplex(3.0, 4.0).reciprocal();

        assertEquals(3.0 / 25.0,  result.real(),      TOLERANCE);
        assertEquals(-4.0 / 25.0, result.imaginary(), TOLERANCE);
        assertEquals(
                1.0,
                new MtxComplex(3.0, 4.0).times(result).abs(),
                TOLERANCE
        );
    }

    @Test
    void rejectsReciprocalOfZero() {
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> new MtxComplex(0.0, 0.0).reciprocal()
        );

        assertEquals("Division by zero", exception.getMessage());
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
            assertEquals("3.0", complex.toString());
        }

        @Test
        void testToString_realNumber_withDecimals() {
            MtxComplex complex = new MtxComplex(3.34567, 0.0);
            assertEquals("3.346", complex.toString());
        }

        @Test
        void testToString_imaginaryNumber() {
            MtxComplex complex = new MtxComplex(3.0, 1.5);
            assertEquals("3.0 + 1.5i", complex.toString());
        }

        @Test
        void testToString_imaginaryNumber_withDecimals() {
            MtxComplex complex = new MtxComplex(3.0, 1.56789);
            assertEquals("3.0 + 1.568i", complex.toString());
        }

        @Test
        void testToString_realNaN() {
            MtxComplex complex1 = new MtxComplex(Double.NaN, 0.0);
            assertEquals("NaN", complex1.toString());
            MtxComplex complex2 = new MtxComplex(Double.NaN, 1.0);
            assertEquals("NaN + 1.0i", complex2.toString());
        }

        @Test
        void testToString_imaginaryNaN() {
            MtxComplex complex = new MtxComplex(3.0, Double.NaN);
            assertEquals("3.0", complex.toString());
        }

        @Test
        void testToString_bothNaN() {
            MtxComplex complex = new MtxComplex(Double.NaN, Double.NaN);
            assertEquals("NaN", complex.toString());
        }
    }

    @Nested
    class EqualsAndHashCodeTests {

        @Test
        void equalComplexNumbersAreEqual() {
            MtxComplex first = new MtxComplex(3.0, 4.0);
            MtxComplex second = new MtxComplex(3.0, 4.0);

            assertEquals(first, second);
        }

        @Test
        void equalComplexNumbersAreCopies() {
            MtxComplex first = new MtxComplex(3.0, 4.0);

            assertEquals(first, first);
        }

        @Test
        void equalComplexNumbersHaveSameHashCode() {
            MtxComplex first = new MtxComplex(3.0, 4.0);
            MtxComplex second = new MtxComplex(3.0, 4.0);

            assertEquals(first.hashCode(), second.hashCode());
        }

        @Test
        void differentRealPartsAreNotEqual() {
            assertNotEquals(
                    new MtxComplex(3.0, 4.0),
                    new MtxComplex(5.0, 4.0)
            );
        }

        @Test
        void differentImaginaryPartsAreNotEqual() {
            assertNotEquals(
                    new MtxComplex(3.0, 4.0),
                    new MtxComplex(3.0, 5.0)
            );
        }

        @Test
        void complexNumberIsNotEqualToNull() {
            assertNotEquals(new MtxComplex(3.0, 4.0), null);
        }

        @Test
        void complexNumberIsNotEqualToAnotherType() {
            assertNotEquals(new MtxComplex(3.0, 4.0), "3.0 + 4.0i");
        }

        @Test
        void nanRealComponentsCompareAsEqual() {
            MtxComplex first = new MtxComplex(Double.NaN, 4.0);
            MtxComplex second = new MtxComplex(Double.NaN, 4.0);

            assertEquals(first, second);
            assertEquals(first.hashCode(), second.hashCode());
        }

        @Test
        void nanImgComponentsCompareAsEqual() {
            MtxComplex first = new MtxComplex(4.0, Double.NaN);
            MtxComplex second = new MtxComplex(4.0, Double.NaN);

            assertEquals(first, second);
            assertEquals(first.hashCode(), second.hashCode());
        }

        @Test
        void positiveAndNegativeZeroAreDifferent() {
            assertNotEquals(
                    new MtxComplex(0.0, 0.0),
                    new MtxComplex(-0.0, 0.0)
            );
            assertNotEquals(
                    new MtxComplex(0.0, 0.0),
                    new MtxComplex(0.0, -0.0)
            );
        }
    }
}
