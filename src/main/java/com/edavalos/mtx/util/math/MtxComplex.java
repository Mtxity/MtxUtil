package com.edavalos.mtx.util.math;

public class MtxComplex {
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

    public MtxComplex conjugate() {
        return new MtxComplex(this.re, -this.im);
    }

    public MtxComplex divide(MtxComplex b) {
        double denominator = b.re * b.re + b.im * b.im;
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
}
