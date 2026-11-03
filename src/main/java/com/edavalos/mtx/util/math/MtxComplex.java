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
}
