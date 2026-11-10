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
    void createsComplexNumberFromPolarCoordinates() {
        MtxComplex result = MtxComplex.fromPolar(2.0, Math.PI / 3.0);

        assertEquals(1.0, result.real(), TOLERANCE);
        assertEquals(Math.sqrt(3.0), result.imaginary(), TOLERANCE);
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
    void raisesComplexNumberToAnIntegerPower() {
        MtxComplex result = new MtxComplex(1.0, 2.0).pow(3);

        assertEquals(-11.0, result.real(), TOLERANCE);
        assertEquals(-2.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void raisesComplexNumberToAnEvenIntegerPower() {
        MtxComplex result = new MtxComplex(1.0, 2.0).pow(2);

        assertEquals(-3.0, result.real(), TOLERANCE);
        assertEquals(4.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void returnsOneForZeroPower() {
        MtxComplex result = new MtxComplex(3.0, 4.0).pow(0);

        assertEquals(1.0, result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void supportsNegativePowers() {
        MtxComplex result = new MtxComplex(3.0, 4.0).pow(-1);

        assertEquals(3.0 / 25.0, result.real(), TOLERANCE);
        assertEquals(-4.0 / 25.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void raisesComplexNumberToAComplexPower() {
        MtxComplex result = new MtxComplex(1.0, 1.0).pow(new MtxComplex(2.0, 0.0));

        assertEquals(0.0, result.real(), TOLERANCE);
        assertEquals(2.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void raisesNegativeRealNumberToFractionalComplexPowerUsingPrincipalLogarithm() {
        MtxComplex result = new MtxComplex(-1.0, 0.0).pow(new MtxComplex(0.5, 0.0));

        assertEquals(0.0, result.real(), TOLERANCE);
        assertEquals(1.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesPrincipalSquareRoot() {
        MtxComplex result = new MtxComplex(3.0, 4.0).sqrt();

        assertEquals(2.0, result.real(), TOLERANCE);
        assertEquals(1.0, result.imaginary(), TOLERANCE);
        assertEquals(new MtxComplex(3.0, 4.0).toString(), result.times(result).toString());
    }

    @Test
    void computesSquareRootOfZero() {
        MtxComplex result = new MtxComplex(0.0, 0.0).sqrt();

        assertEquals(0.0, result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesSquareRootOfPureImaginaryNumber() {
        MtxComplex value = new MtxComplex(0.0, 1.0);
        MtxComplex result = value.sqrt();
        MtxComplex squared = result.times(result);

        assertEquals(value.real(), squared.real(), TOLERANCE);
        assertEquals(value.imaginary(), squared.imaginary(), TOLERANCE);
    }

    @Test
    void computesSquareRootWithNegativeImaginaryPartAndNonnegativeRealPart() {
        MtxComplex result = new MtxComplex(3.0, -4.0).sqrt();

        assertEquals(2.0, result.real(), TOLERANCE);
        assertEquals(-1.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesSquareRootOfNegativeRealNumber() {
        MtxComplex result = new MtxComplex(-9.0, 0.0).sqrt();

        assertEquals(0.0, result.real(), TOLERANCE);
        assertEquals(3.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesSquareRootWithNegativeRealAndImaginaryParts() {
        MtxComplex result = new MtxComplex(-3.0, -4.0).sqrt();

        assertEquals(1.0, result.real(), TOLERANCE);
        assertEquals(-2.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesComplexExponential() {
        MtxComplex result = new MtxComplex(0.0, Math.PI).exp();

        assertEquals(-1.0, result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesComplexSine() {
        MtxComplex result = new MtxComplex(Math.PI / 2.0, 1.0).sin();

        assertEquals(Math.cosh(1.0), result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesComplexCosine() {
        MtxComplex result = new MtxComplex(Math.PI / 2.0, 1.0).cos();

        assertEquals(0.0, result.real(), TOLERANCE);
        assertEquals(-Math.sinh(1.0), result.imaginary(), TOLERANCE);
    }

    @Test
    void computesComplexTangent() {
        MtxComplex result = new MtxComplex(Math.PI / 4.0, 0.0).tan();

        assertEquals(1.0, result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesComplexArcsine() {
        MtxComplex result = new MtxComplex(1.0, 0.0).asin();

        assertEquals(Math.PI / 2.0, result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesComplexArccosine() {
        MtxComplex result = new MtxComplex(1.0, 0.0).acos();

        assertEquals(0.0, result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesComplexArctangent() {
        MtxComplex result = new MtxComplex(0.0, 0.0).atan();

        assertEquals(0.0, result.real(), TOLERANCE);
        assertEquals(0.0, result.imaginary(), TOLERANCE);
    }

    @Test
    void verifiesConstants() {
        assertEquals(0.0, MtxComplex.ZERO.real());
        assertEquals(0.0, MtxComplex.ZERO.imaginary());
        assertEquals(1.0, MtxComplex.ONE.real());
        assertEquals(0.0, MtxComplex.ONE.imaginary());
        assertEquals(0.0, MtxComplex.I.real());
        assertEquals(1.0, MtxComplex.I.imaginary());
    }

    @Test
    void computesComplexExponentialDecimal() {
        MtxComplex result = new MtxComplex(3.5, 3.5).exp();

        assertEquals(-31.01118643937451, result.real(), TOLERANCE);
        assertEquals(-11.61634512447063, result.imaginary(), TOLERANCE);
    }

    @Test
    void computesPrincipalComplexLogarithm() {
        MtxComplex result = new MtxComplex(3.0, 4.0).log();

        assertEquals(Math.log(5.0), result.real(), TOLERANCE);
        assertEquals(Math.atan2(4.0, 3.0), result.imaginary(), TOLERANCE);
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
        void testToString_realNumber_negative() {
            MtxComplex complex = new MtxComplex(-3.0, 0.0);
            assertEquals("-3.0", complex.toString());
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
        void testToString_imaginaryNumber_negativeImaginaryPart_positiveRealPart() {
            MtxComplex complex = new MtxComplex(3.0, -1.5);
            assertEquals("3.0 - 1.5i", complex.toString());
        }

        @Test
        void testToString_imaginaryNumber_negativeImaginaryPart_negativeRealPart() {
            MtxComplex complex = new MtxComplex(-3.0, -1.5);
            assertEquals("-3.0 - 1.5i", complex.toString());
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
