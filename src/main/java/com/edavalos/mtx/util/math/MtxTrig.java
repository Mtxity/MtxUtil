package com.edavalos.mtx.util.math;

public final class MtxTrig {
    private static final double tiny = 1.0e-300;
    private static final double HUGE = 1.0e+300;
    private static final double pi_o_2 = 0x1.921fb54442d18p0;
    private static final double pi_o_4 = 0x1.921fb54442d18p-1;
    private static final double pi_lo = 0x1.1a62633145c07p-53;
    private static final int EXP_BITS = 0x7ff0_0000;
    private static final int EXP_SIGNIF_BITS = 0x7fff_ffff;

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
            return StrictMath.atan(y);
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

    private static double atan(double x) {
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

    public static double sin(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        }
        if (x == 0.0) {
            return x; // preserve the sign of zero
        }

        // Reduce to [-pi, pi]. Split pi/2 into high and low parts so the
        // subtraction retains useful precision for ordinary sized arguments.
        double ax = MtxMath.abs(x);
        if (ax > 0x1.0p20) {
            // A bounded remainder avoids overflow and keeps the polynomial's
            // input small. For very large inputs the available double-precision
            // value of 2*pi limits the accuracy of this reduction.
            x %= 2.0 * MtxMath.PI;
        }

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
            case 0 -> s;
            case 1 -> c;
            case 2 -> -s;
            default -> -c;
        };
    }

    public static double cos(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        }

        double ax = MtxMath.abs(x);
        if (ax > 0x1.0p20) {
            x %= 2.0 * MtxMath.PI;
        }

        double n = MtxMath.floor(MtxMath.abs(x) * 0x1.45f306dc9c883p-1 + 0.5);
        if (x < 0.0) {
            n = -n;
        }
        double r = x - n * 0x1.921fb54400000p0;
        r -= n * 0x1.0b4611a626331p-34;
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
            case 0 -> c;
            case 1 -> -s;
            case 2 -> -c;
            default -> s;
        };
    }
}
