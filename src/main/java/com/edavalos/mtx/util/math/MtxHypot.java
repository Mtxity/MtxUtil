package com.edavalos.mtx.util.math;

public final class MtxHypot {
    public static final double TWO_MINUS_600 = 0x1.0p-600;
    public static final double TWO_PLUS_600  = 0x1.0p+600;

    private MtxHypot() { }

    public static double compute(double x, double y) {
        double a = MtxMath.abs(x);
        double b = MtxMath.abs(y);

        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            if (a == Double.POSITIVE_INFINITY || b == Double.POSITIVE_INFINITY) {
                return Double.POSITIVE_INFINITY;
            } else {
                return a + b;
            }
        }

        if (b > a) {
            double tmp = a;
            a = b;
            b = tmp;
        }
        assert a >= b;

        int ha = MtxMath.highOrderBit(a);
        int hb = MtxMath.highOrderBit(b);

        if ((ha - hb) > 0x3c00000) {
            return a + b; // x / y > 2**60
        }

        int k = 0;
        if (a > 0x1.00000_ffff_ffffp500) { // a > ~2**500
            // scale a and b by 2**-600
            ha -= 0x25800000;
            hb -= 0x25800000;
            a = a * TWO_MINUS_600;
            b = b * TWO_MINUS_600;
            k += 600;
        }

        double t1, t2;
        if (b < 0x1.0p-500) { // b < 2**-500
            if (b < Double.MIN_NORMAL) { // subnormal b or 0 */
                if (b == 0.0)
                    return a;
                t1 = 0x1.0p1022; // t1 = 2^1022
                b *= t1;
                a *= t1;
                k -= 1022;
            } else { // scale a and b by 2^600
                ha += 0x25800000; // a *= 2^600
                hb += 0x25800000; // b *= 2^600
                a = a * TWO_PLUS_600;
                b = b * TWO_PLUS_600;
                k -= 600;
            }
        }

        // mediumly size a and b
        double w = a - b;
        if (w > b) {
            t1 = 0;
            t1 = MtxMath.highOrderBit(t1, ha);
            t2 = a - t1;
            w  = MtxMath.sqrt(t1*t1 - (b*(-b) - t2 * (a + t1)));
        } else {
            double y1, y2;
            a  = a + a;
            y1 = 0;
            y1 = MtxMath.highOrderBit(y1, hb);
            y2 = b - y1;
            t1 = 0;
            t1 = MtxMath.highOrderBit(t1, ha + 0x00100000);
            t2 = a - t1;
            w  = MtxMath.sqrt(t1*y1 - (w*(-w) - (t1*y2 + t2*b)));
        }

        return k != 0 ? MtxMath.powerOfTwo(k) * w : w;
    }
}
