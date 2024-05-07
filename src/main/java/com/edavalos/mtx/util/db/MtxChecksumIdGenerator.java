package com.edavalos.mtx.util.db;

import java.util.Arrays;
import java.util.Random;

public class MtxChecksumIdGenerator implements MtxInfiniteIdBuilder {
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
        return Arrays.toString(digits);
    }

    protected int[] getInitialDigits(int length) {
        int[] digits = new int[length];
        for (int i = 0; i < length; i++) {
            int digit = NUMBER_GENERATOR.nextInt(10) % 10;
            digits[i] = digit;
        }
        return digits;
    }
}
