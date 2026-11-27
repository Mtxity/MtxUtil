package com.edavalos.mtx.util.math;

public final class MtxTrig {
    /** The sine and cosine of an angle, in radians. */
    public record SinCos(double sin, double cos) { }

    private static final double tiny = 1.0e-300;
    private static final double HUGE = 1.0e+300;
    private static final double pi_o_2 = 0x1.921fb54442d18p0;
    private static final double pi_o_4 = 0x1.921fb54442d18p-1;
    private static final double pi_lo = 0x1.1a62633145c07p-53;
    private static final int EXP_BITS = 0x7ff0_0000;
    private static final int EXP_SIGNIF_BITS = 0x7fff_ffff;
    private static final double DEGREES_TO_RADIANS = 0.017453292519943295;
    private static final double RADIANS_TO_DEGREES = 57.29577951308232;

    private static final double[] atanHi = {
            0x1.dac670561bb4fp-2,  // atan(0.5)hi 4.63647609000806093515e-01
            0x1.921fb54442d18p-1,  // atan(1.0)hi 7.85398163397448278999e-01
            0x1.f730bd281f69bp-1,  // atan(1.5)hi 9.82793723247329054082e-01
            0x1.921fb54442d18p0,   // atan(inf)hi 1.57079632679489655800e+00
    };

    private static final double[] atanLo = {
            0x1.a2b7f222f65e2p-56, // atan(0.5)lo 2.26987774529616870924e-17
            0x1.1a62633145c07p-55, // atan(1.0)lo 3.06161699786838301793e-17
            0x1.007887af0cbbdp-56, // atan(1.5)lo 1.39033110312309984516e-17
            0x1.1a62633145c07p-54, // atan(inf)lo 6.12323399573676603587e-17
    };

    private static final double[] aT = {
            0x1.555555555550dp-2,  //  3.33333333333329318027e-01
            -0x1.999999998ebc4p-3, // -1.99999999998764832476e-01
            0x1.24924920083ffp-3,  //  1.42857142725034663711e-01
            -0x1.c71c6fe231671p-4, // -1.11111104054623557880e-01
            0x1.745cdc54c206ep-4,  //  9.09088713343650656196e-02
            -0x1.3b0f2af749a6dp-4, // -7.69187620504482999495e-02
            0x1.10d66a0d03d51p-4,  //  6.66107313738753120669e-02
            -0x1.dde2d52defd9ap-5, // -5.83357013379057348645e-02
            0x1.97b4b24760debp-5,  //  4.97687799461593236017e-02
            -0x1.2b4442c6a6c2fp-5, // -3.65315727442169155270e-02
            0x1.0ad3ae322da11p-6,  //  1.62858201153657823623e-02
    };

    // Source: https://docs.oracle.com/javase/8/docs/api/java/lang/StrictMath.html#atan2-double-double-
    public static double atan(double y, double x) {
        double z;
        int k, m, hx, hy, ix, iy;
        /*unsigned*/
        int lx, ly;

        hx = (int) (Double.doubleToRawLongBits(x) >> 32);
        ix = hx & 0x7fff_ffff;
        lx = (int) Double.doubleToRawLongBits(x);
        hy = (int) (Double.doubleToRawLongBits(y) >> 32);
        iy = hy & 0x7fff_ffff;
        ly = (int) Double.doubleToRawLongBits(y);
        if (Double.isNaN(x) || Double.isNaN(y)) {
            return x + y;
        }
        if (((hx - 0x3ff0_0000) | lx) == 0) { // x = 1.0
            return atan(y);
        }
        m = ((hy >> 31) & 1) | ((hx >> 30) & 2); // 2*sign(x) + sign(y)

        // when y = 0
        if ((iy | ly) == 0) {
            switch (m) {
                case 0:
                case 1:
                    return y;                  // atan(+/-0, +anything)  = +/-0
                case 2:
                    return MtxMath.PI + tiny;  // atan(+0,   -anything)  =  pi
                case 3:
                    return -MtxMath.PI - tiny; // atan(-0,   -anything)  = -pi
            }
        }
        // when x = 0
        if ((ix | lx) == 0) {
            return (hy < 0) ? -pi_o_2 - tiny : pi_o_2 + tiny;
        }

        // when x is INF
        if (ix == EXP_BITS) {
            if (iy == EXP_BITS) {
                switch (m) {
                    case 0:
                        return pi_o_4 + tiny;        // atan(+INF, +INF)
                    case 1:
                        return -pi_o_4 - tiny;       // atan(-INF, +INF)
                    case 2:
                        return 3.0 * pi_o_4 + tiny;  // atan(+INF, -INF)
                    case 3:
                        return -3.0 * pi_o_4 - tiny; // atan(-INF, -INF)
                }
            } else {
                switch (m) {
                    case 0:
                        return 0.0;                  // atan(+..., +INF)
                    case 1:
                        return -0.0;                 // atan(-..., +INF)
                    case 2:
                        return MtxMath.PI + tiny;    // atan(+..., -INF)
                    case 3:
                        return -MtxMath.PI - tiny;   // atan(-..., -INF)
                }
            }
        }
        // when y is INF
        if (iy == EXP_BITS) {
            return (hy < 0) ? -pi_o_2 - tiny : pi_o_2 + tiny;
        }

        // compute y/x
        k = (iy - ix) >> 20;
        if (k > 60) {   // |y/x| >  2**60
            z = pi_o_2 + 0.5 * pi_lo;
        } else if (hx < 0 && k < -60) { // |y|/x < -2**60
            z = 0.0;
        } else { // safe to do y/x
            z = atan(MtxMath.abs(y / x));
        }
        return switch (m) {
            case 0 -> z;                         // atan(+, +)
            case 1 -> -z;                        // atan(-, +)
            case 2 -> MtxMath.PI - (z - pi_lo);  // atan(+, -)
            default -> (z - pi_lo) - MtxMath.PI; // atan(-, -), case 3
        };
    }

    public static double atan2(double y, double x) {
        return atan(y, x);
    }

    public static double atan(double x) {
        double w, s1, s2, z;
        int ix, hx, id;

        hx = (int) (Double.doubleToRawLongBits(x) >> 32);
        ix = hx & EXP_SIGNIF_BITS;
        if (ix >= 0x4410_0000) { // if |x| >= 2^66
            if (ix > EXP_BITS || (ix == EXP_BITS && ((int) Double.doubleToRawLongBits(x) != 0))) {
                return x+x; // NaN
            }
            if (hx > 0) {
                return atanHi[3] + atanLo[3];
            } else {
                return -atanHi[3] - atanLo[3];
            }
        } if (ix < 0x3fdc_0000) {     // |x| < 0.4375
            if (ix < 0x3e20_0000) {   // |x| < 2^-29
                if (HUGE + x > 1.0) { // raise inexact
                    return x;
                }
            }
            id = -1;
        } else {
            x = MtxMath.abs(x);
            if (ix < 0x3ff3_0000) {         // |x| < 1.1875
                if (ix < 0x3fe60000) {      // 7/16 <= |x| < 11/16
                    id = 0;
                    x = (2.0*x - 1.0)/(2.0 + x);
                } else {                    // 11/16 <= |x| < 19/16
                    id = 1;
                    x  = (x - 1.0)/(x + 1.0);
                }
            } else {
                if (ix < 0x4003_8000) {     // |x| < 2.4375
                    id = 2;
                    x  = (x - 1.5)/(1.0 + 1.5*x);
                } else {                    // 2.4375 <= |x| < 2^66
                    id = 3;
                    x  = -1.0/x;
                }
            }
        }
        // end of argument reduction
        z = x*x;
        w = z*z;
        // break sum from i=0 to 10 aT[i]z**(i+1) into odd and even poly
        s1 = z*(aT[0] + w*(aT[2] + w*(aT[4] + w*(aT[6] + w*(aT[8] + w*aT[10])))));
        s2 = w*(aT[1] + w*(aT[3] + w*(aT[5] + w*(aT[7] + w*aT[9]))));
        if (id < 0) {
            return x - x*(s1 + s2);
        } else {
            z = atanHi[id] - ((x*(s1+s2) - atanLo[id]) - x);
            return (hx < 0) ? -z: z;
        }
    }

    public static double asin(double x) {
        if (Double.isNaN(x) || x < -1.0 || x > 1.0) {
            return Double.NaN;
        }
        if (x == 0.0) {
            return x;
        }
        if (x == 1.0) {
            return pi_o_2;
        }
        if (x == -1.0) {
            return -pi_o_2;
        }

        double absX = MtxMath.abs(x);
        double radicand = (1.0 - absX) * (1.0 + absX);
        double root = refinedSqrt(radicand);
        return atan(x / root);
    }

    public static double acos(double x) {
        if (Double.isNaN(x) || x < -1.0 || x > 1.0) {
            return Double.NaN;
        }
        if (x == 1.0) {
            return 0.0;
        }
        if (x == -1.0) {
            return MtxMath.PI;
        }

        double absX = MtxMath.abs(x);
        double radicand = (1.0 - absX) * (1.0 + absX);
        double root = refinedSqrt(radicand);
        return atan(root, x);
    }

    /**
     * MtxMath.sqrt stops at an absolute 1e-10 error, which is too coarse for asin/acos near +/-1 where the result is
     * sensitive to sqrt(x). A few Newton steps refine its estimate to double precision.
     *
     * @param x the non-negative value whose square root is being refined
     * @return the square root of {@code x}, refined with Newton's method
     */
    private static double refinedSqrt(double x) {
        double root = MtxMath.sqrt(x);
        for (int i = 0; i < 64; i++) {
            double next = 0.5 * (root + x / root);
            if (next == root) {
                return root;
            }
            root = next;
        }
        return root;
    }

    public static double sin(double x) {
        return sinCos(x).sin();
    }

    /**
     * Returns the unnormalized sinc function, {@code sin(x) / x}, with
     * {@code sinc(0) = 1}. A Taylor expansion avoids loss of precision near zero.
     */
    public static double sinc(double x) {
        if (x == 0.0) {
            return 1.0;
        }

        if (MtxMath.abs(x) < 0.01) {
            double xSquared = x * x;
            return 1.0 + xSquared * (-1.0 / 6.0 + xSquared * (1.0 / 120.0
                    + xSquared * (-1.0 / 5040.0 + xSquared * (1.0 / 362880.0
                    - xSquared / 39916800.0))));
        }

        return sin(x) / x;
    }

    /**
     * Returns the unnormalized cosc function, {@code (1 - cos(x)) / x},
     * with {@code cosc(0) = 0}. A Taylor expansion avoids cancellation near zero.
     */
    public static double cosc(double x) {
        if (x == 0.0) {
            return x;
        }

        if (MtxMath.abs(x) < 0.1) {
            double xSquared = x * x;
            return x * (0.5 + xSquared * (-1.0 / 24.0 + xSquared * (1.0 / 720.0
                    + xSquared * (-1.0 / 40320.0 + xSquared * (1.0 / 3628800.0
                    + xSquared * (-1.0 / 479001600.0 + xSquared / 87178291200.0))))));
        }

        return (1.0 - cos(x)) / x;
    }

    public static double cos(double x) {
        return sinCos(x).cos();
    }

    /**
     * Calculates the sine and cosine of {@code x} together, sharing argument
     * reduction and polynomial evaluation. The angle is in radians.
     */
    public static SinCos sinCos(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return new SinCos(Double.NaN, Double.NaN);
        }
        if (x == 0.0) {
            return new SinCos(x, 1.0); // preserve the sign of sine zero
        }

        // The polynomial reduction below loses precision when the quadrant
        // count is too large. StrictMath uses a multiword reduction for these
        // inputs, preserving useful precision across the full double range.
        double ax = MtxMath.abs(x);
        if (ax > 0x1.0p20) {
            return new SinCos(MtxStrictTrig.computeSin(x), MtxStrictTrig.computeCos(x));
        }

        // Split pi/2 into high and low parts so the subtraction retains
        // useful precision for ordinary-sized arguments.
        double n = MtxMath.floor(MtxMath.abs(x) * 0x1.45f306dc9c883p-1 + 0.5);
        if (x < 0.0) {
            n = -n;
        }
        double r = x - n * 0x1.921fb54400000p0;
        r -= n * 0x1.0b4611a626331p-34;

        // Rounding to the nearest multiple of pi/2 already bounds r to
        // [-pi/4, pi/4], so the quadrant comes directly from n.
        int quadrant = ((int) n) & 3;

        double z = r * r;
        double s = r * (1.0 + z * (-1.0 / 6.0 + z * (1.0 / 120.0
                 + z * (-1.0 / 5040.0 + z * (1.0 / 362880.0
                 + z * (-1.0 / 39916800.0 + z * (1.0 / 6227020800.0
                 + z * (-1.0 / 1307674368000.0 + z * (1.0 / 355687428096000.0
                 - z / 121645100408832000.0)))))))));
        double c = 1.0 + z * (-1.0 / 2.0 + z * (1.0 / 24.0
                 + z * (-1.0 / 720.0 + z * (1.0 / 40320.0
                 + z * (-1.0 / 3628800.0 + z * (1.0 / 479001600.0
                 + z * (-1.0 / 87178291200.0 + z / 20922789888000.0)))))));

        return switch (quadrant) {
            case 0 ->  new SinCos(s, c);
            case 1 ->  new SinCos(c, -s);
            case 2 ->  new SinCos(-s, -c);
            default -> new SinCos(-c, s);
        };
    }

    public static double tan(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        }
        if (x == 0.0) {
            return x; // preserve the sign of zero
        }
        return sin(x) / cos(x);
    }

    /**
     * Returns {@code cos(x) / sin(x)}. At sine zeros, the result follows
     * IEEE-754 division semantics; near those singularities it may be very
     * large or infinite due to floating-point precision. No pole threshold is applied.
     */
    public static double cot(double x) {
        SinCos values = sinCos(x);
        return values.cos() / values.sin();
    }

    /**
     * Returns {@code 1 / cos(x)}. At cosine zeros, the result follows
     * IEEE-754 division semantics; near those singularities it may be very
     * large or infinite due to floating-point precision. No pole threshold is applied.
     */
    public static double sec(double x) {
        return 1.0 / sinCos(x).cos();
    }

    /**
     * Returns {@code 1 / sin(x)}. At sine zeros, the result follows
     * IEEE-754 division semantics; near those singularities it may be very
     * large or infinite due to floating-point precision. No pole threshold is applied.
     */
    public static double csc(double x) {
        return 1.0 / sinCos(x).sin();
    }

    public static double sinh(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x) || x == 0.0) {
            return x;
        }

        // The Maclaurin series is well-conditioned for sinh: all terms have
        // the same sign, so it avoids cancellation for both small and large x.
        double xSquared = x * x;
        double term = x;
        double sum = x;
        for (int n = 1; ; n++) {
            term *= xSquared / ((2.0 * n) * (2.0 * n + 1.0));
            double next = sum + term;
            if (Double.isInfinite(next)) {
                return next;
            }
            if (next == sum || MtxMath.abs(term) <= MtxMath.abs(next) * 0x1.0p-53) {
                return next;
            }
            sum = next;
        }
    }

    public static double cosh(double x) {
        if (Double.isNaN(x)) {
            return x;
        }
        if (Double.isInfinite(x)) {
            return Double.POSITIVE_INFINITY;
        }

        // The Maclaurin series has only non-negative terms, avoiding
        // cancellation for both small and large inputs.
        double xSquared = x * x;
        double term = 1.0;
        double sum = 1.0;
        for (int n = 1; ; n++) {
            term *= xSquared / ((2.0 * n - 1.0) * (2.0 * n));
            double next = sum + term;
            if (Double.isInfinite(next)) {
                return next;
            }
            if (next == sum || term <= next * 0x1.0p-53) {
                return next;
            }
            sum = next;
        }
    }

    public static double tanh(double x) {
        if (Double.isNaN(x) || x == 0.0) {
            return x;
        }
        if (Double.isInfinite(x)) {
            return x < 0.0 ? -1.0 : 1.0;
        }

        double absX = MtxMath.abs(x);
        int doublings = 0;
        while (absX > 0.5) {
            absX *= 0.5;
            doublings++;
        }

        // Evaluate in the well-conditioned range, then repeatedly use
        // tanh(2x) = 2*tanh(x)/(1 + tanh(x)^2). This avoids sinh/cosh overflow.
        double result = sinh(absX) / cosh(absX);
        for (int i = 0; i < doublings; i++) {
            result = (2.0 * result) / (1.0 + result * result);
        }
        return x < 0.0 ? -result : result;
    }

    public static double atanh(double x) {
        if (Double.isNaN(x) || x < -1.0 || x > 1.0) {
            return Double.NaN;
        }
        if (x == 1.0) {
            return Double.POSITIVE_INFINITY;
        }
        if (x == -1.0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (x == 0.0) {
            return x;
        }

        double absX = MtxMath.abs(x);
        double result;
        if (absX <= 0.5) {
            // atanh(x) = x + x^3/3 + x^5/5 + ...; this avoids subtracting
            // nearly equal logarithms when x is close to zero.
            double xSquared = absX * absX;
            double term = absX;
            result = absX;
            for (int n = 1; ; n++) {
                term *= xSquared;
                double addend = term / (2.0 * n + 1.0);
                double next = result + addend;
                if (next == result || addend <= next * 0x1.0p-53) {
                    result = next;
                    break;
                }
                result = next;
            }
        } else {
            // The logarithm form converges quickly near |x| = 1, where the
            // direct power series would require many terms.
            result = 0.5 * MtxMath.ln((1.0 + absX) / (1.0 - absX));
        }
        return x < 0.0 ? -result : result;
    }

    public static double acosh(double x) {
        if (Double.isNaN(x) || x < 1.0) {
            return Double.NaN;
        }
        if (x == 1.0) {
            return 0.0;
        }
        if (Double.isInfinite(x)) {
            return Double.POSITIVE_INFINITY;
        }

        if (x < 2.0) {
            double radicand = (x - 1.0) * (x + 1.0);
            return MtxMath.ln(x + refinedSqrt(radicand));
        }

        // This equivalent form avoids overflowing x + sqrt(x*x - 1) for
        // large finite x: acosh(x) = ln(x) + ln(1 + sqrt(1 - 1/x^2)).
        double reciprocal = 1.0 / x;
        double root = refinedSqrt(1.0 - reciprocal * reciprocal);
        return MtxMath.ln(x) + MtxMath.ln(1.0 + root);
    }

    public static double asinh(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x) || x == 0.0) {
            return x;
        }

        double absX = MtxMath.abs(x);
        double result;
        if (absX <= 0.5) {
            // asinh(x) = x - x^3/6 + 3x^5/40 - ...; the series preserves
            // precision for inputs where the logarithm form would cancel.
            double xSquared = absX * absX;
            double term = absX;
            result = absX;
            for (int n = 0; ; n++) {
                term *= -xSquared * (2.0 * n + 1.0) * (2.0 * n + 1.0)
                        / (2.0 * (n + 1.0) * (2.0 * n + 3.0));
                double next = result + term;
                if (next == result || MtxMath.abs(term) <= MtxMath.abs(next) * 0x1.0p-53) {
                    result = next;
                    break;
                }
                result = next;
            }
        } else if (absX <= 2.0) {
            result = MtxMath.ln(absX + refinedSqrt(absX * absX + 1.0));
        } else {
            double reciprocal = 1.0 / absX;
            double root = refinedSqrt(1.0 + reciprocal * reciprocal);
            result = MtxMath.ln(absX) + MtxMath.ln(1.0 + root);
        }
        return x < 0.0 ? -result : result;
    }

    public static double toRadians(double degrees) {
        return degrees * DEGREES_TO_RADIANS;
    }

    public static double toDegrees(double radians) {
        return radians * RADIANS_TO_DEGREES;
    }

    /**
     * Normalizes an angle in radians to the range {@code [0, 2π)}.
     * Returns {@link Double#NaN} for NaN or infinite inputs.
     */
    public static double normalizeRadians(double radians) {
        if (!Double.isFinite(radians)) {
            return Double.NaN;
        }
        double normalized = radians % MtxMath.TAU;
        if (normalized < 0.0) {
            normalized += MtxMath.TAU;
        }
        // Rounding can produce TAU for a result just below the upper bound.
        return normalized >= MtxMath.TAU ? 0.0 : normalized;
    }

    public static class MtxStrictTrig {
        private static final double S1  = -0x1.5555555555549p-3;
        private static final double S2  =  0x1.111111110f8a6p-7;
        private static final double S3  = -0x1.a01a019c161d5p-13;
        private static final double S4  =  0x1.71de357b1fe7dp-19;
        private static final double S5  = -0x1.ae5e68a2b9cebp-26;
        private static final double S6  =  0x1.5d93a5acfd57cp-33;
        private static final double C1  =  0x1.555555555554cp-5;
        private static final double C2  = -0x1.6c16c16c15177p-10;
        private static final double C3  =  0x1.a01a019cb1590p-16;
        private static final double C4  = -0x1.27e4f809c52adp-22;
        private static final double C5  =  0x1.1ee9ebdb4b1c4p-29;
        private static final double C6  = -0x1.8fae9be8838d4p-37;

        private static final double
                invpio2 =  0x1.45f306dc9c883p-1,   // 6.36619772367581382433e-01
                pio2_1  =  0x1.921fb544p0,         // 1.57079632673412561417e+00
                pio2_1t =  0x1.0b4611a626331p-34,  // 6.07710050650619224932e-11
                pio2_2  =  0x1.0b4611a6p-34,       // 6.07710050630396597660e-11
                pio2_2t =  0x1.3198a2e037073p-69,  // 2.02226624879595063154e-21
                pio2_3  =  0x1.3198a2ep-69,        // 2.02226624871116645580e-21
                pio2_3t =  0x1.b839a252049c1p-104; // 8.47842766036889956997e-32

        private static final int[] npio2_hw = {
                0x3FF921FB, 0x400921FB, 0x4012D97C, 0x401921FB, 0x401F6A7A, 0x4022D97C,
                0x4025FDBB, 0x402921FB, 0x402C463A, 0x402F6A7A, 0x4031475C, 0x4032D97C,
                0x40346B9C, 0x4035FDBB, 0x40378FDB, 0x403921FB, 0x403AB41B, 0x403C463A,
                0x403DD85A, 0x403F6A7A, 0x40407E4C, 0x4041475C, 0x4042106C, 0x4042D97C,
                0x4043A28C, 0x40446B9C, 0x404534AC, 0x4045FDBB, 0x4046C6CB, 0x40478FDB,
                0x404858EB, 0x404921FB,
        };

        private static final int[] two_over_pi = {
                0xA2F983, 0x6E4E44, 0x1529FC, 0x2757D1, 0xF534DD, 0xC0DB62,
                0x95993C, 0x439041, 0xFE5163, 0xABDEBB, 0xC561B7, 0x246E3A,
                0x424DD2, 0xE00649, 0x2EEA09, 0xD1921C, 0xFE1DEB, 0x1CB129,
                0xA73EE8, 0x8235F5, 0x2EBB44, 0x84E99C, 0x7026B4, 0x5F7E41,
                0x3991D6, 0x398353, 0x39F49C, 0x845F8B, 0xBDF928, 0x3B1FF8,
                0x97FFDE, 0x05980F, 0xEF2F11, 0x8B5A0A, 0x6D1F6D, 0x367ECF,
                0x27CB09, 0xB74F46, 0x3F669E, 0x5FEA2D, 0x7527BA, 0xC7EBE5,
                0xF17B3D, 0x0739F7, 0x8A5292, 0xEA6BFB, 0x5FB11F, 0x8D5D08,
                0x560330, 0x46FC7B, 0x6BABF0, 0xCFBC20, 0x9AF436, 0x1DA9E3,
                0x91615E, 0xE61B08, 0x659985, 0x5F14A0, 0x68408D, 0xFFD880,
                0x4D7327, 0x310606, 0x1556CA, 0x73A8C9, 0x60E27B, 0xC08C6B,
        };

        private static final double TWO24 = 0x1.0p24; // 1.67772160000000000000e+07
        private static final int init_jk[] = {2, 3, 4, 6};
        private static final double twon24 = 0x1.0p-24;

        private static final double PIo2[] = {
                0x1.921fb4p0,    // 1.57079625129699707031e+00
                0x1.4442dp-24,   // 7.54978941586159635335e-08
                0x1.846988p-48,  // 5.39030252995776476554e-15
                0x1.8cc516p-72,  // 3.28200341580791294123e-22
                0x1.01b838p-96,  // 1.27065575308067607349e-29
                0x1.a25204p-120, // 1.22933308981111328932e-36
                0x1.382228p-145, // 2.73370053816464559624e-44
                0x1.9f31dp-169,  // 2.16741683877804819444e-51
        };

        private static final int SIGNIFICAND_WIDTH = 53;
        private static final int EXP_BIAS = (1 << (64 - SIGNIFICAND_WIDTH - 1)) - 1; // 1023
        private static final double F_UP = 0x1p1023; // normal, exact, 2^DoubleConsts.EXP_BIAS
        private static final double F_DOWN = 0x1p-1023; // subnormal, exact, 2^-DoubleConsts.EXP_BIAS
        private static final double MIN_VALUE = 0x0.0000000000001P-1022; // 4.9e-324

        public static double computeSin(double x) {
            double[] y = new double[2];
            double z = 0.0;
            int n, ix;

            // High word of x
            ix = MtxMath.highOrderBit(x);

            // |x| ~< pi/4
            ix &= EXP_SIGNIF_BITS;
            if (ix <= 0x3fe9_21fb) {
                return kernelSin(x, z, 0);
            } else if (ix >= EXP_BITS) {
                // sin(Inf or NaN) is NaN
                return 0.0;
            } else {
                // argument reduction needed
                n = ieee754RemPio2(x, y);
                return switch (n & 3) {
                    case 0 ->   kernelSin(y[0], y[1], 1);
                    case 1 ->   kernelCos(y[0], y[1]);
                    case 2 ->  -kernelSin(y[0], y[1], 1);
                    default -> -kernelCos(y[0], y[1]);
                };
            }
        }

        public static double computeCos(double x) {
            double[] y = new double[2];
            double z = 0.0;
            int n, ix;

            // High word of x
            ix = MtxMath.highOrderBit(x);

            // |x| ~< pi/4
            ix &= EXP_SIGNIF_BITS;
            if (ix <= 0x3fe9_21fb) {
                return kernelCos(x, z);
            } else if (ix >= EXP_BITS) {
                // Match computeSin's handling of infinities and NaN.
                return 0.0;
            } else {
                // argument reduction needed
                n = ieee754RemPio2(x, y);
                return switch (n & 3) {
                    case 0 -> kernelCos(y[0], y[1]);
                    case 1 -> -kernelSin(y[0], y[1], 1);
                    case 2 -> -kernelCos(y[0], y[1]);
                    default -> kernelSin(y[0], y[1], 1);
                };
            }
        }

        private static double kernelCos(double x, double y) {
            double z = x * x;
            double r = z * (C1 + z * (C2 + z * (C3 + z * (C4 + z * (C5 + z * C6)))));
            if (MtxMath.abs(x) < 0.3) {
                return 1.0 - (0.5 * z - (z * r - x * y));
            }
            double qx = MtxMath.abs(x) > 0.78125 ? 0.28125 : 0.5 * z;
            return (1.0 - qx) - ((0.5 * z - qx) - (z * r - x * y));
        }

        protected static double kernelSin(double x, double y, int iy) {
            double z, r, v;
            int ix;
            ix = MtxMath.highOrderBit(x) & EXP_SIGNIF_BITS; // high word of x
            if (ix < 0x3e40_0000) {                         // |x| < 2**-27
                if ((int) x == 0)                           // generate inexact
                    return x;
            }
            z = x * x;
            v = z * x;
            r = S2 + z * (S3 + z * (S4 + z * (S5 + z * S6)));
            if (iy == 0) {
                return x + v * (S1 + z * r);
            } else {
                return x - ((z * (0.5 * y - v * r) - y) - v * S1);
            }
        }

        protected static int ieee754RemPio2(double x, double[] y) {
            double z = 0.0, w, t, r, fn;
            double[] tx = new double[3];
            int e0, i, j, nx, n, ix, hx;

            hx = MtxMath.highOrderBit(x); // high word of x
            ix = hx & EXP_SIGNIF_BITS;
            if (ix <= 0x3fe9_21fb) { // |x| ~<= pi/4, no need for reduction
                y[0] = x;
                y[1] = 0;
                return 0;
            }

            if (ix < 0x4002_d97c) { // |x| < 3pi/4, special case with n=+-1
                if (hx > 0) {
                    z = x - pio2_1;
                    if (ix != 0x3ff9_21fb) { // 33+53 bit pi is good enough
                        y[0] = z - pio2_1t;
                        y[1] = (z - y[0]) - pio2_1t;
                    } else { // near pi/2, use 33+33+53 bit pi
                        z -= pio2_2;
                        y[0] = z - pio2_2t;
                        y[1] = (z-y[0])-pio2_2t;
                    }
                    return 1;
                } else { // negative x
                    z = x + pio2_1;
                    if (ix != 0x3ff_921fb) { // 33+53 bit pi is good enough
                        y[0] = z + pio2_1t;
                        y[1] = (z - y[0]) + pio2_1t;
                    } else { // near pi/2, use 33+33+53 bit pi
                        z += pio2_2;
                        y[0] = z + pio2_2t;
                        y[1] = (z - y[0]) + pio2_2t;
                    }
                    return -1;
                }
            }

            if (ix <= 0x4139_21fb) { // |x| ~<= 2^19*(pi/2), medium size
                t  = MtxMath.abs(x);
                n  = (int) (t * invpio2 + 0.5);
                fn = n;
                r  = t - fn * pio2_1;
                w  = fn * pio2_1t; // 1st round good to 85 bit
                if (n < 32 && ix != npio2_hw[n - 1]) {
                    y[0] = r - w; // quick check no cancellation
                } else {
                    j = ix >> 20;
                    y[0] = r - w;
                    i = j - (((MtxMath.highOrderBit(y[0])) >> 20) & 0x7ff);
                    if (i > 16) { // 2nd iteration needed, good to 118
                        t  = r;
                        w  = fn * pio2_2;
                        r  = t - w;
                        w  = fn * pio2_2t - ((t - r) - w);
                        y[0] = r - w;
                        i = j - (((MtxMath.highOrderBit(y[0])) >> 20) & 0x7ff);
                        if (i > 49)  { // 3rd iteration need, 151 bits acc
                            t  = r; // will cover all possible cases
                            w  = fn * pio2_3;
                            r  = t - w;
                            w  = fn * pio2_3t - ((t - r) - w);
                            y[0] = r - w;
                        }
                    }
                }
                y[1] = (r - y[0]) - w;
                if (hx < 0) {
                    y[0] = -y[0];
                    y[1] = -y[1];
                    return -n;
                } else {
                    return n;
                }
            }

            // all other (large) arguments
            if (ix >= EXP_BITS) { // x is inf or NaN
                y[0] = y[1] = 0.0;
                return 0;
            }

            // set z = scalbn(|x|, ilogb(x)-23)
            z = MtxMath.lowOrderBit(z, MtxMath.lowOrderBit(x));
            e0 = (ix >> 20) - 1046; // e0 = ilogb(z) - 23;
            z = MtxMath.highOrderBit(z, ix - (e0 << 20));
            for (i=0; i < 2; i++) {
                tx[i] = (int)(z);
                z = (z - tx[i])*TWO24;
            }
            tx[2] = z;
            nx = 3;
            while (tx[nx - 1] == 0.0) { // skip zero term
                nx--;
            }
            n = kernelRemPio2(tx, y, e0, nx, 2, two_over_pi);
            if (hx < 0) {
                y[0] = -y[0];
                y[1] = -y[1];
                return -n;
            }
            return n;
        }

        protected static int kernelRemPio2(double[] x, double[] y, int e0, int nx, int prec, final int[] ipio2) {
            int jz, jx, jv, jp, jk, carry, n, i, j, k, m, q0, ih;
            int[] iq = new int[20];
            double z,fw;
            double [] f = new double[20];
            double [] fq= new double[20];
            double [] q = new double[20];

            // initialize jk
            jk = init_jk[prec];
            jp = jk;

            // determine jx, jv, q0, note that 3 > q0
            jx = nx - 1;
            jv = (e0 - 3)/24;
            if (jv < 0) {
                jv = 0;
            }
            q0 =  e0 - 24*(jv + 1);

            // set up f[0] to f[jx+jk] where f[jx+jk] = ipio2[jv+jk]
            j = jv - jx;
            m = jx + jk;
            for (i = 0; i <= m; i++, j++) {
                f[i] = (j < 0) ? 0.0 : (double) ipio2[j];
            }

            // compute q[0],q[1],...q[jk]
            for (i=0; i <= jk; i++) {
                for (j = 0, fw = 0.0; j <= jx; j++) {
                    fw += x[j]*f[jx + i - j];
                }
                q[i] = fw;
            }

            jz = jk;
            while(true) {
                // distill q[] into iq[] reversingly
                for (i=0, j=jz, z=q[jz]; j > 0; i++, j--) {
                    fw    = (int)(twon24* z);
                    iq[i] = (int)(z - TWO24*fw);
                    z     = q[j - 1] + fw;
                }

                // compute n
                z  = sCalB(z, q0); // actual value of z
                z -= 8.0*Math.floor(z*0.125); // trim off integer >= 8
                n  = (int) z;
                z -= n;
                ih = 0;
                if (q0 > 0) { // need iq[jz - 1] to determine n
                    i  = (iq[jz - 1] >> (24 - q0));
                    n += i;
                    iq[jz - 1] -= i << (24 - q0);
                    ih = iq[jz - 1] >> (23 - q0);
                } else if (q0 == 0) {
                    ih = iq[jz-1]>>23;
                } else if (z >= 0.5) {
                    ih=2;
                }

                if (ih > 0) { // q > 0.5
                    n += 1;
                    carry = 0;
                    for (i=0; i < jz; i++) { // compute 1-q
                        j = iq[i];
                        if (carry == 0) {
                            if (j != 0) {
                                carry = 1;
                                iq[i] = 0x100_0000 - j;
                            }
                        } else {
                            iq[i] = 0xff_ffff - j;
                        }
                    }
                    if (q0 > 0) { // rare case: chance is 1 in 12
                        switch(q0) {
                            case 1:
                                iq[jz-1] &= 0x7f_ffff;
                                break;
                            case 2:
                                iq[jz-1] &= 0x3f_ffff;
                                break;
                        }
                    }
                    if (ih == 2) {
                        z = 1.0 - z;
                        if (carry != 0) {
                            z -= sCalB(1.0, q0);
                        }
                    }
                }

                // check if recomputation is needed
                if (z == 0.0) {
                    j = 0;
                    for (i = jz - 1; i >= jk; i--) {
                        j |= iq[i];
                    }
                    if (j == 0) { // need recomputation
                        for (k=1; iq[jk - k] == 0; k++); // k = no. of terms needed

                        for (i = jz + 1; i <= jz + k; i++) { // add q[jz+1] to q[jz+k]
                            f[jx + i] = ipio2[jv + i];
                            for (j=0, fw = 0.0; j <= jx; j++) {
                                fw += x[j]*f[jx + i - j];
                            }
                            q[i] = fw;
                        }
                        jz += k;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            // chop off zero terms
            if (z == 0.0) {
                jz -= 1;
                q0 -= 24;
                while (iq[jz] == 0) {
                    jz--;
                    q0-=24;
                }
            } else { // break z into 24-bit if necessary
                z = sCalB(z, -q0);
                if (z >= TWO24) {
                    fw = (int) (twon24*z);
                    iq[jz] = (int)(z - TWO24*fw);
                    jz += 1;
                    q0 += 24;
                    iq[jz] = (int) fw;
                } else {
                    iq[jz] = (int) z;
                }
            }

            // convert integer "bit" chunk to floating-point value
            fw = sCalB(1.0, q0);
            for (i = jz; i>=0; i--) {
                q[i] = fw*(double)iq[i];
                fw *= twon24;
            }

            // compute PIo2[0,...,jp]*q[jz,...,0]
            for (i = jz; i>=0; i--) {
                for (fw = 0.0, k = 0; k <= jp && k <= jz-i; k++) {
                    fw += PIo2[k]*q[i + k];
                }
                fq[jz - i] = fw;
            }

            // compress fq[] into y[]
            switch (prec) {
                case 0:
                    fw = 0.0;
                    for (i = jz; i >=0; i--) {
                        fw += fq[i];
                    }
                    y[0] = (ih == 0)? fw: -fw;
                    break;
                case 1:
                case 2:
                    fw = 0.0;
                    for (i = jz; i>=0; i--) {
                        fw += fq[i];
                    }
                    y[0] = (ih == 0) ? fw: -fw;
                    fw = fq[0] - fw;
                    for (i = 1; i <= jz; i++) {
                        fw += fq[i];
                    }
                    y[1] = (ih == 0)? fw: -fw;
                    break;
                case 3:
                    for (i = jz; i > 0; i--) {
                        fw      = fq[i - 1] + fq[i];
                        fq[i]  += fq[i - 1] - fw;
                        fq[i - 1] = fw;
                    }
                    for (i = jz; i>1; i--) {
                        fw      = fq[i - 1] + fq[i];
                        fq[i]  += fq[i - 1] - fw;
                        fq[i-1] = fw;
                    }
                    for (fw = 0.0, i = jz; i >= 2; i--) {
                        fw += fq[i];
                    }
                    if (ih == 0) {
                        y[0] =  fq[0];
                        y[1] =  fq[1];
                        y[2] =  fw;
                    } else {
                        y[0] = -fq[0];
                        y[1] = -fq[1];
                        y[2] = -fw;
                    }
            }

            return n & 7;
        }

        protected static double sCalB(double d, int scaleFactor) {
            if (scaleFactor > -EXP_BIAS) {
                if (scaleFactor <= EXP_BIAS) {
                    return d * primPowerOfTwoD(scaleFactor);
                }
                if (scaleFactor <= 2 * EXP_BIAS) {
                    return d * primPowerOfTwoD(scaleFactor - EXP_BIAS) * F_UP;
                }
                if (scaleFactor < 2 * EXP_BIAS + SIGNIFICAND_WIDTH - 1) {
                    return d * primPowerOfTwoD(scaleFactor - 2 * EXP_BIAS) * F_UP * F_UP;
                }
                return d * F_UP * F_UP * F_UP;
            }
            if (scaleFactor > -2 * EXP_BIAS) {
                return d * primPowerOfTwoD(scaleFactor + EXP_BIAS) * F_DOWN;
            }
            if (scaleFactor > -2 * EXP_BIAS - SIGNIFICAND_WIDTH) {
                return d * primPowerOfTwoD(scaleFactor + 2 * EXP_BIAS) * F_DOWN * F_DOWN;
            }
            return d * MIN_VALUE * MIN_VALUE;
        }

        private static double primPowerOfTwoD(int n) {
            // Native method
            return Double.longBitsToDouble((long) (n + EXP_BIAS) << SIGNIFICAND_WIDTH - 1);
        }
    }
}
