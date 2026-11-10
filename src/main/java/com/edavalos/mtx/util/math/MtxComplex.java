package com.edavalos.mtx.util.math;

public class MtxComplex {
    public static final MtxComplex ZERO = new MtxComplex(0.0, 0.0);
    public static final MtxComplex ONE = new MtxComplex(1.0, 0.0);
    public static final MtxComplex I = new MtxComplex(0.0, 1.0);

    private final double re; // Real part
    private final double im; // Imaginary part

    public MtxComplex(double real, double imag) {
        this.re = real;
        this.im = imag;
    }

    public MtxComplex plus(MtxComplex b) {
        return new MtxComplex(this.re + b.re, this.im + b.im);
    }

    public MtxComplex minus(MtxComplex b) {
        return new MtxComplex(this.re - b.re, this.im - b.im);
    }

    public MtxComplex times(MtxComplex b) {
        double real = this.re * b.re - this.im * b.im;
        double imag = this.re * b.im + this.im * b.re;
        return new MtxComplex(real, imag);
    }

    public double abs() {
        return MtxMath.hypotenuse(re, im); // Avoids underflow/overflow
    }

    public double absSquared() {
        return this.re * this.re + this.im * this.im;
    }

    public double argument() {
        return MtxTrig.atan(this.im, this.re);
    }

    public MtxComplex pow(int exponent) {
        MtxComplex base = exponent < 0 ? this.reciprocal() : this;
        long remaining = MtxMath.abs((long) exponent);
        MtxComplex result = new MtxComplex(1.0, 0.0);
        while (remaining > 0) {
            if ((remaining & 1) == 1) {
                result = result.times(base);
            }
            base = base.times(base);
            remaining >>= 1;
        }
        return result;
    }

    public MtxComplex pow(MtxComplex exponent) {
        return exponent.times(this.log()).exp();
    }

    public MtxComplex sqrt() {
        if (this.re == 0.0 && this.im == 0.0) {
            return new MtxComplex(0.0, 0.0);
        }

        double magnitude = this.abs();
        double real, imaginary;
        if (this.re >= 0.0) {
            real = MtxMath.sqrt((magnitude + this.re) / 2.0);
            imaginary = this.im / (2.0 * real);
        } else {
            imaginary = MtxMath.sqrt((magnitude - this.re) / 2.0);
            real = MtxMath.abs(this.im) / (2.0 * imaginary);
        }

        return new MtxComplex(real, MtxMath.copySign(imaginary, this.im));
    }

    public MtxComplex sin() {
        return new MtxComplex(
                MtxTrig.sin(this.re) * MtxTrig.cosh(this.im),
                MtxTrig.cos(this.re) * MtxTrig.sinh(this.im)
        );
    }

    public MtxComplex cos() {
        return new MtxComplex(
                MtxTrig.cos(this.re) * MtxTrig.cosh(this.im),
                -MtxTrig.sin(this.re) * MtxTrig.sinh(this.im)
        );
    }

    public MtxComplex tan() {
        return this.sin().divide(this.cos());
    }

    public MtxComplex exp() {
        double scale = MtxMath.power(MtxMath.E, this.re);
        return new MtxComplex(
                scale * MtxTrig.cos(this.im),
                scale * MtxTrig.sin(this.im)
        );
    }

    public MtxComplex log() {
        return new MtxComplex(
                MtxMath.ln(this.abs()),
                this.argument()
        );
    }

    public MtxComplex negate() {
        return new MtxComplex(-this.re, -this.im);
    }

    public MtxComplex reciprocal() {
        double denominator = this.absSquared();
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return new MtxComplex(this.re / denominator, -this.im / denominator);
    }

    public MtxComplex conjugate() {
        return new MtxComplex(this.re, -this.im);
    }

    public MtxComplex divide(MtxComplex b) {
        double denominator = b.absSquared();
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero");
        }

        double real = (this.re * b.re + this.im * b.im) / denominator;
        double imag = (this.im * b.re - this.re * b.im) / denominator;

        return new MtxComplex(real, imag);
    }

    public double real() {
        return this.re;
    }

    public double imaginary() {
        return this.im;
    }

    @Override
    public String toString() {
        String num = formatDouble("%.3f", this.re);
        if (this.im == 0.0 || Double.isNaN(this.im)) {
            return num;
        } else if (this.im < 0.0) {
            return num + " - " + formatDouble("%.3f", -this.im) + 'i';
        } else {
            return num + " + " + formatDouble("%.3f", this.im) + 'i';
        }
    }

    private static String formatDouble(String format, double value) {
        if (!Double.isFinite(value)) {
            return String.valueOf(value);
        }

        String result = String.format(format, value);
        int end = result.length();
        int dot = result.indexOf('.');

        while (end > dot + 2 && result.charAt(end - 1) == '0') {
            end--;
        }
        return result.substring(0, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MtxComplex other)) {
            return false;
        }

        return Double.compare(this.re, other.re) == 0
            && Double.compare(this.im, other.im) == 0;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(this.re);
        result = 31 * result + Double.hashCode(this.im);
        return result;
    }

    public static MtxComplex fromPolar(double magnitude, double argument) {
        return new MtxComplex(
                magnitude * MtxTrig.cos(argument),
                magnitude * MtxTrig.sin(argument)
        );
    }
}
