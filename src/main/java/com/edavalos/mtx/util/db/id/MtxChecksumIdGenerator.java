package com.edavalos.mtx.util.db.id;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class MtxChecksumIdGenerator implements MtxAutoIdBuilder {
    private static final Random NUMBER_GENERATOR = new Random();

    private final int length;

    public MtxChecksumIdGenerator(int length) {
        if (length > 64) {
            throw new IllegalArgumentException("Length of ID too long. Cannot exceed 64.");
        }
        if (length < 3) {
            throw new IllegalArgumentException("Length of ID too short. Must be at least 3.");
        }
        this.length = length;
    }

    @Override
    public String getNextId() {
        int[] digits = this.getInitialDigits(this.length - 1);
        digits[this.length - 1] = getChecksumDigit(digits);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.length; i++) {
            sb.append(digits[i]);
        }
        return sb.toString();
    }

    protected int[] getInitialDigits(int length) {
        int[] digits = new int[length + 1];
        for (int i = 0; i < length; i++) {
            int digit = NUMBER_GENERATOR.nextInt(10) % 10;
            digits[i] = digit;
        }
        return digits;
    }

    protected static int getChecksumDigit(int[] initialDigits) {
        // Based on the Luhn Algorithm
        // https://en.m.wikipedia.org/wiki/Luhn_algorithm
        boolean x2 = true;
        int sum = 0;
        for (int i = 0; i < initialDigits.length; i++) {
            String row = String.valueOf(x2 ? initialDigits[i] * 2 : initialDigits[i]);
            for (int j = 0; j < row.length(); j++) {
                sum += row.charAt(j);
            }
            x2 = !x2;
        }
        return 9 - ((sum + 9) % 10);
    }

    @Override
    public int getIdLength() {
        return this.length;
    }

    @Override
    public BigInteger getTotalUniqueIds() {
        return BigInteger.valueOf(10).pow(this.length);
    }
}
