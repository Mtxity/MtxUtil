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

    public MtxComplex times(MtxComplex b) {
        double real = this.re * b.re - this.im * b.im;
        double imag = this.re * b.im + this.im * b.re;
        return new MtxComplex(real, imag);
    }

    public double abs() {
        return MtxMath.hypotenuse(re, im); // Avoids underflow/overflow
    }
}
