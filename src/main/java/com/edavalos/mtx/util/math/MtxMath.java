package com.edavalos.mtx.util.math;

import com.edavalos.mtx.util.list.MtxArrayList;
import com.edavalos.mtx.util.list.MtxList;

import java.math.BigInteger;
import java.util.*;

public final class MtxMath {
    private static final int DEFAULT_SIZE = 8;
    public static final int POSITIVE_INFINITY = (int) Double.POSITIVE_INFINITY;
    public static final int NEGATIVE_INFINITY = (int) Double.NEGATIVE_INFINITY;

    /**
     * Pi (π) to the 32nd decimal place
     */
    public static final double PI  = 3.14159265358979323846264338327950;
    /**
     * Pi * 2, or Tau (τ) to the 32nd decimal place
     */
    public static final double TAU = 6.28318530717958647692528676655901;
    /**
     * Euler's number (e) to the 32nd decimal place
     */
    public static final double E   = 2.71828182845904523536028747135266;
    /**
     * Euler's constant, or the Euler-Mascheroni constant (γ) to the 32nd decimal place
     */
    public static final double Y   = 0.57721566490153286060651209008240;
    /**
     * The golden ratio, or Phi (𝜑) to the 32nd decimal place
     */
    public static final double PHI = 1.61803398874989484820458683436564;
    /**
     * The natural logarithm of 2
     */
    public static final double LN2 = 0.69314718055994530941723212145818;
    /**
     * The natural logarithm of 3
     */
    public static final double LN3 = 1.09861228866810969139524523692205;
    /**
     * The natural logarithm of 10
     */
    public static final double LN10 = 2.30258509299404568401799145468436;

    public static class MtxFactorial implements Iterable<Integer> {
        private static final String NEGATIVE_SIZE_ERR = "Factorial is not defined for negative numbers.";

        private final int iteratorLength;

        public MtxFactorial(int size) {
            if (size < 0) {
                throw new IllegalArgumentException(NEGATIVE_SIZE_ERR);
            }

            this.iteratorLength = size;
        }

        public MtxFactorial() {
            this(DEFAULT_SIZE);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                private int iterations = 0;

                @Override
                public boolean hasNext() {
                    return this.iterations <= iteratorLength;
                }

                @Override
                public Integer next() {
                    long currentVal = factorial(this.iterations);
                    this.iterations++;
                    return ((int) currentVal);
                }
            };
        }

        public static long factorial(int n) {
            if (n < 0) {
                throw new IllegalArgumentException(NEGATIVE_SIZE_ERR);
            }

            long factorial = 1;
            for (int i = 2; i <= n; i++) {
                factorial *= i;
            }

            return factorial;
        }
    }

    public static class MtxFibonacci implements Iterable<Integer> {
        private static final String NEGATIVE_SIZE_ERR = "Invalid input. Fibonacci sequence starts from index 1.";

        private final int iteratorLength;

        public MtxFibonacci(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException(NEGATIVE_SIZE_ERR);
            }

            this.iteratorLength = size;
        }

        public MtxFibonacci() {
            this(DEFAULT_SIZE);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                private int iterations = 1;

                @Override
                public boolean hasNext() {
                    return this.iterations <= iteratorLength;
                }

                @Override
                public Integer next() {
                    int currentVal = fibonacci(this.iterations);
                    this.iterations++;
                    return currentVal;
                }
            };
        }

        public static int fibonacci(int n) {
            if (n <= 0) {
                throw new IllegalArgumentException(NEGATIVE_SIZE_ERR);
            }

            if (n == 1 || n == 2) {
                return 1;
            }

            int fib1 = 1;
            int fib2 = 1;
            int fibonacci = 0;

            for (int i = 3; i <= n; i++) {
                fibonacci = fib1 + fib2;
                fib1 = fib2;
                fib2 = fibonacci;
            }

            return fibonacci;
        }
    }

    public static class MtxCatalan implements Iterable<Integer> {
        private static final String NEGATIVE_SIZE_ERR = "No Catalan number exists for negative numbers.";

        private final int iteratorLength;

        public MtxCatalan(int size) {
            if (size < 0) {
                throw new IllegalArgumentException(NEGATIVE_SIZE_ERR);
            }

            this.iteratorLength = size;
        }

        public MtxCatalan() {
            this(DEFAULT_SIZE);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                private int iterations = 1;

                @Override
                public boolean hasNext() {
                    return this.iterations <= iteratorLength;
                }

                @Override
                public Integer next() {
                    long currentVal = catalan(this.iterations);
                    this.iterations++;
                    return ((int) currentVal);
                }
            };
        }

        public static long catalan(int n) {
            if (n < 0) {
                throw new IllegalArgumentException(NEGATIVE_SIZE_ERR);
            }

            if (n == 0 || n == 1) {
                return 1;
            }

            long catalanNum = 0;
            for (int i = 0; i < n; i++) {
                catalanNum += catalan(i) * catalan(n - i - 1);
            }
            return catalanNum;
        }
    }

    public static class MtxStats {
        private final ArrayList<Double> contents;

        public MtxStats() {
            this.contents = new ArrayList<>();
        }

        public MtxStats(double[] values) {
            this();
            for (double value : values) {
                this.contents.add(value);
            }
        }

        public MtxStats(int[] values) {
            this();
            for (int value : values) {
                this.contents.add((double) value);
            }
        }

        public boolean clearContents() {
            boolean isEmpty = this.contents.isEmpty();
            this.contents.clear();
            return !isEmpty;
        }

        public void add(double value) {
            this.contents.add(value);
        }

        public void add(int value) {
            this.contents.add((double) value);
        }

        public List<Double> getContents() {
            return this.contents;
        }

        // Source: https://www.baeldung.com/java-calculate-standard-deviation#calculate-the-standard-deviation
        public double getStandardDeviation() {
            double mean = this.getMean();
            double stdDev = 0.0;
            for (double j : this.contents) {
                stdDev += power(j - mean, 2);
            }

            return sqrt(stdDev / this.contents.size());
        }

        public double getMean() {
            return this.getTotal() / this.contents.size();
        }

        public double getMedian() {
            List<Double> localCopy = new ArrayList<>(List.copyOf(this.contents));
            localCopy.sort(Comparator.naturalOrder());

            int length = localCopy.size();
            int middleIdx = length / 2;

            if (length % 2 == 0) {
                // If array length is even, average the two middle values
                return (localCopy.get(middleIdx - 1) + localCopy.get(middleIdx)) / 2.0;
            } else {
                // array length is odd, return the middle value
                return localCopy.get(middleIdx);
            }
        }

        public double getMode() {
            Map<Double, Integer> frequencyMap = new HashMap<>();
            for (double i : this.contents) {
                frequencyMap.put(i, frequencyMap.getOrDefault(i, 0) + 1);
            }

            double mode = 0.0;
            int maxFrequency = 0;

            for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
                double number = entry.getKey();
                int frequency = entry.getValue();

                if (frequency > maxFrequency) {
                    mode = number;
                    maxFrequency = frequency;
                }
            }

            return mode;
        }

        public double getVariance() {
            double mean = this.getMean();
            double sumOfSquaredDifferences = 0;

            for (double number : this.contents) {
                double difference = number - mean;
                sumOfSquaredDifferences += difference * difference;
            }

            return sumOfSquaredDifferences / this.contents.size();
        }

        private double getTotal() {
            double sum = 0.0;
            for (double i : this.contents) {
                sum += i;
            }
            return sum;
        }
    }

    public static int sqrt(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative numbers do not have real square roots.");
        }

        if (n == 0 || n == 1) {
            return n;
        }

        int left = 1, right = n;
        int ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) mid * mid == n) {
                return mid;
            } else if ((long) mid * mid < n) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    public static double sqrt(double n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative numbers do not have real square roots.");
        }

        if (n == 0) {
            return 0;
        }

        double x = n;
        double precision = 1e-10;
        while (abs(x - n / x) > precision) {
            x = (x + n / x) / 2;
        }

        return x;
    }

    public static double ln(double n) {
        if (Double.isNaN(n) || n < 0.0) {
            return Double.NaN;
        }
        if (n == 0.0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (n == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }

        // Preloaded values
        if (n == 2.0) {
            return LN2;
        }
        if (n == 3.0) {
            return LN3;
        }
        if (n == 10.0) {
            return LN10;
        }

        long bits = Double.doubleToRawLongBits(n);
        int encodedExponent = (int) ((bits >>> 52) & 0x7ffL);
        int exponentAdjustment = 0;

        // Scale subnormal values into the normal range before extracting the
        // exponent. The adjustment restores the original exponent below.
        if (encodedExponent == 0) {
            n *= 0x1.0p52;
            bits = Double.doubleToRawLongBits(n);
            encodedExponent = (int) ((bits >>> 52) & 0x7ffL);
            exponentAdjustment = -52;
        }

        int exponent = encodedExponent - 1023 + exponentAdjustment;
        double mantissa = Double.longBitsToDouble(
                (bits & 0x000f_ffff_ffff_ffffL) | 0x3ff0_0000_0000_0000L);
        double y = (mantissa - 1.0) / (mantissa + 1.0);
        double ySquared = y * y;
        double term = y;
        double result = 0.0;

        // ln(m) = 2 * (y + y^3/3 + y^5/5 + ...), y=(m-1)/(m+1).
        for (int denominator = 1; denominator <= 127; denominator += 2) {
            result += term / denominator;
            term *= ySquared;
        }

        return exponent * LN2 + 2.0 * result;
    }

    public static double log10(double n) {
        return ln(n) / LN10;
    }

    public static double logN(double base, double value) {
        if (Double.isNaN(base) || Double.isNaN(value) || base <= 0.0 || base == 1.0 || value < 0.0) {
            return Double.NaN;
        }

        return ln(value) / ln(base);
    }

    public static boolean isPrime(int i) {
        if (i <= 1) {
            return false;
        }

        for (int j = 2; j < i; j++) {
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }

    public static double[] quadraticFormulaSolver(int a, int b, int c) {
        double[] x = new double[2];

        double rootOfDeterminant = sqrt(power(b, 2) - (4 * a * c));
        double positiveDeterminant = (-1 * b) + rootOfDeterminant;
        double negativeDeterminant = (-1 * b) - rootOfDeterminant;

        x[0] = positiveDeterminant / (2 * a);
        x[1] = negativeDeterminant / (2 * a);
        return x;
    }

    public static void twoDimensionArrayCopy(int[][] source, int[][] destination) {
        for (int i = 0; i < destination.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, destination[i].length);
        }
    }

    public static int min(int... val) {
        int smallest = val.length == 0 ? 0 : val[0];
        for (int i : val) {
            if (i < smallest) {
                smallest = i;
            }
        }
        return smallest;
    }

    public static int min(List<Integer> val) {
        return min(val.stream().mapToInt(Integer::intValue).toArray());
    }

    public static double min(double... val) {
        double smallest = val.length == 0 ? 0 : val[0];
        for (double i : val) {
            if (i < smallest) {
                smallest = i;
            }
        }
        return smallest;
    }

    public static double minFromList(List<Double> val) {
        return min(val.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public static int max(int... val) {
        int biggest = val.length == 0 ? 0 : val[0];
        for (int i : val) {
            if (i > biggest) {
                biggest = i;
            }
        }
        return biggest;
    }

    public static int max(List<Integer> val) {
        return max(val.stream().mapToInt(Integer::intValue).toArray());
    }

    public static double max(double... val) {
        double biggest = val.length == 0 ? 0 : val[0];
        for (double i : val) {
            if (i > biggest) {
                biggest = i;
            }
        }
        return biggest;
    }

    public static double maxFromList(List<Double> val) {
        return max(val.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public static boolean hasRemainder(int xDiv, int yDev) {
        return xDiv % yDev != 0;
    }

    public static boolean dividesEvenlyEitherWay(int a, int b) {
        if (a == 0 || b == 0) {
            return false;
        }

        return a % b == 0 || b % a == 0;
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static int lcm(int a, int b) {
        return abs(a * b) / gcd(a, b);
    }

    public static boolean coprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    public static int power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative");
        }

        int result = 1;

        for (int i = 0; i < exponent; i++) {
            result *= base;
        }

        return result;
    }

    public static double power(double base, int exponent) {
        if (exponent == 0) {
            return 1.0;
        }

        long exp = exponent;
        if (exp < 0) {
            base = 1.0 / base;
            exp = -exp;
        }

        double result = 1.0;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result *= base;
            }
            base *= base;
            exp >>= 1;
        }
        return result;
    }

    public static double power(double base, double exponent) {
        if (base == 0) {
            if (exponent > 0) {
                return 0;
            }
            throw new ArithmeticException("Undefined result");
        }

        if (base < 0) {
            if (exponent != (long) exponent) {
                throw new ArithmeticException(
                        "Negative base with fractional exponent"
                );
            }

            return power(-base, exponent) * ((long) exponent % 2 == 0 ? 1 : -1);
        }

        return exp(exponent * ln(base));
    }

    public static double exp(double x) {
        // Taylor series: e^x = 1 + x + x²/2! + ...
        double sum = 1;
        double term = 1;

        for (int i = 1; i <= 200; i++) {
            term *= x / i;
            sum += term;

            if (abs(term) < 1e-15) {
                break;
            }
        }

        return sum;
    }

    public static double powerOfTwo(int n) {
        if (n < Double.MIN_EXPONENT || n > Double.MAX_EXPONENT) {
            throw new IllegalArgumentException("Result must fit in a double");
        }
        return Double.longBitsToDouble((long) (n + Double.MAX_EXPONENT) << 52);
    }

    public static int abs(int n) {
        if (n < 0) {
            return -n;
        }
        return n;
    }

    public static double abs(double n) {
        if (n < 0) {
            return -n;
        }
        return n;
    }

    public static long abs(long n) {
        if (n < 0) {
            return -n;
        }
        return n;
    }

    public static int sumOfDigits(int n) {
        int sum = 0;
        n = abs(n);

        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public static int reverseNumber(int n) {
        int reversed = 0;
        int num = abs(n);

        while (num > 0) {
            reversed = reversed * 10 + (num % 10);
            num /= 10;
        }

        return (n < 0 ? -1 : 1) * reversed;
    }

    public static int countDigits(int n) {
        if (n == 0) {
            return 1;
        }

        int count = 0;
        int num = abs(n);

        while (num > 0) {
            count++;
            num /= 10;
        }

        return count;
    }

    public static boolean isEven(int n) {
        if (n == 0) {
            return true;
        }

        return n % 2 == 0;
    }

    public static boolean isOdd(int n) {
        if (n == 0) {
            return false;
        }

        return n % 2 != 0;
    }

    public static int nextPrime(int n) {
        if (n <= 2) {
            return 2;
        }

        int candidate = n;
        while (!isPrime(candidate)) {
            candidate++;
        }

        return candidate;
    }

    public static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        int num = abs(n);

        if (num <= 1) {
            return factors;
        }

        for (int factor = 2; factor <= num / factor; factor++) {
            while (num % factor == 0) {
                factors.add(factor);
                num /= factor;
            }
        }

        if (num > 1) {
            factors.add(num);
        }

        return factors;
    }

    public static boolean isComposite(int n) {
        return n > 1 && !isPrime(n);
    }

    public static boolean isDivisibleBy(int n, int divisor) {
        if (divisor == 0) {
            return false;
        }

        return n % divisor == 0;
    }

    public static boolean isPalindromeNumber(int n) {
        if (n < 0) {
            return false;
        }

        return n == reverseNumber(n);
    }

    public static boolean isArmstrongNumber(int n) {
        if (n < 0) {
            return false;
        }

        int digits = countDigits(n);
        int num = n;
        int sum = 0;

        while (num > 0) {
            int digit = num % 10;
            sum += power(digit, digits);
            num /= 10;
        }

        return sum == n;
    }

    public static int digitalRoot(int n) {
        int root = abs(n);

        while (root >= 10) {
            root = sumOfDigits(root);
        }

        return root;
    }

    public static int clamp(int value, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum cannot be greater than maximum.");
        }

        if (value < min) {
            return min;
        }

        if (value > max) {
            return max;
        }

        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum cannot be greater than maximum.");
        }

        if (value < min) {
            return min;
        }

        if (value > max) {
            return max;
        }

        return value;
    }

    public static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isPerfectSquare(int n) {
        if (n < 0) {
            return false;
        }

        long root = sqrt(n);
        return root * root == n;
    }

    public static int countSetBits(int n) {
        int count = 0;

        while (n != 0) {
            count++;
            n &= (n - 1);
        }

        return count;
    }

    public static double lerp(double a, double b, double t) {
        t = max(0.0, min(1.0, t));
        return a + (b - a) * t;
    }

    public static int[] sieveOfEratosthenes(int limit) {
        if (limit < 2) {
            return new int[0];
        }

        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        MtxList<Integer> primes = new MtxArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        int[] result = new int[primes.size()];
        for (int i = 0; i < primes.size(); i++) {
            result[i] = primes.get(i);
        }
        return result;
    }

    public static double roundToDecimals(double value, int decimals) {
        if (decimals < 0) {
            throw new IllegalArgumentException("Decimal places must be non-negative.");
        }

        double factor = power(10, decimals);
        return round(value * factor) / factor;
    }

    public static double pythagoreanTheorem(double a, double b) {
        return sqrt(a * a + b * b);
    }

    public static double hypotenuse(double a, double b) {
        return MtxHypot.compute(a, b);
    }

    public static double floor(double x) {
        long i = (long) x;
        if (x >= 0 || x == i) {
            return i;
        }
        return i - 1;
    }

    public static double ceil(double x) {
        long i = (long) x;
        if (x <= 0 || x == i) {
            return i;
        }
        return i + 1;
    }

    public static long round(double x) {
        return (long) floor(x + 0.5);
    }

    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return hypotenuse(dx, dy);
    }

    public static int highOrderBit(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int) (transducer >> 32);
    }

    public static double highOrderBit(double x, int high) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble( (transX & 0x0000_0000_FFFF_FFFFL) | ((((long)high)) << 32) );
    }

    public static int lowOrderBit(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int) transducer;
    }

    public static double copySign(double magnitude, double sign) {
        return Double.longBitsToDouble(
                (Double.doubleToRawLongBits(sign) & (1L << (Double.SIZE - 1)))
              | (Double.doubleToRawLongBits(magnitude)
                & (((1L << (Double.SIZE - 53)) - 1) << (53 - 1) | ((1L << (53 - 1)) - 1))
                )
        );
    }

    public static int frobeniusNumber(int[] values) {
        // @TODO: Implement a sorting algorithm to use instead of this one
        Arrays.sort(values);
        int gcd = values[0];
        for (int coin : values) {
            if (coin <= 0) {
                throw new IllegalArgumentException("All values must be positive.");
            }
            gcd = gcd(gcd, coin);
        }

        if (gcd != 1) {
            throw new IllegalArgumentException("Frobenius number exists only when GCD is 1.");
        }

        int max = values[values.length - 1];
        boolean[] dp = new boolean[max * max + 1];
        dp[0] = true;

        int largestImpossible = -1;
        for (int amount = 1; amount < dp.length; amount++) {
            for (int coin : values) {
                if (amount >= coin && dp[amount - coin]) {
                    dp[amount] = true;
                    break;
                }
            }
            if (!dp[amount]) {
                largestImpossible = amount;
            }
        }
        return largestImpossible;
    }

    /**
     * Returns { gcd, x, y } Such that <code> a*x + b*y = gcd(a,b) </code>
     * @param a
     * @param b
     * @return <code> BigInteger[]{gcd, x, y} </code>
     */
    public static BigInteger[] extendedGCD(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[] {
                    a, BigInteger.ONE, BigInteger.ZERO
            };
        }

        BigInteger[] result = extendedGCD(b, a.mod(b));

        BigInteger gcd = result[0];
        BigInteger x = result[2];
        BigInteger y = result[1].subtract(
                a.divide(b).multiply(result[2])
        );

        return new BigInteger[] { gcd, x, y };
    }

    public static BigInteger chineseRemainderTheorem(BigInteger[] remainders, BigInteger[] moduli) {
        if (remainders.length == 0 || remainders.length != moduli.length) {
            throw new IllegalArgumentException("Invalid input.");
        }

        BigInteger x = remainders[0];
        BigInteger m = moduli[0];
        if (m.signum() <= 0) {
            throw new IllegalArgumentException("Moduli must be positive.");
        }

        x = x.mod(m);
        for (int i = 1; i < remainders.length; i++) {
            BigInteger a = remainders[i];
            BigInteger n = moduli[i];
            if (n.signum() <= 0) {
                throw new IllegalArgumentException("Moduli must be positive.");
            }

            BigInteger[] extGCD = extendedGCD(m, n);
            BigInteger gcd = extGCD[0];
            BigInteger inverse = extGCD[1];

            a = a.mod(n);
            BigInteger difference = a.subtract(x);
            if (!difference.remainder(gcd).equals(BigInteger.ZERO)) {
                throw new ArithmeticException("No solution exists.");
            }

            BigInteger mReduced = m.divide(gcd);
            BigInteger nReduced = n.divide(gcd);
            BigInteger k = difference.divide(gcd)
                    .multiply(inverse)
                    .mod(nReduced);

            x = x.add(m.multiply(k));
            m = m.multiply(nReduced);
            x = x.mod(m);
        }

        return x;
    }

    public static int sign(int n) {
        return Integer.compare(n, 0);
    }

    public static double sign(double n) {
        return Math.signum(n);
    }

    public static double reciprocal(double n) {
        return 1.0 / n;
    }

    public static int modulo(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero.");
        }

        return ((a % b) + abs(b)) % abs(b);
    }
}
