package com.edavalos.mtx.util.generator.random;

import java.util.Random;

public abstract class MtxRandom {
    private final Random random;

    public MtxRandom(long seed) {
        this.random = new Random(seed);
    }

    public byte randomByte() {
        long val = this.randomLong();
        return Long.valueOf(val).byteValue();
    }

    public short randomShort() {
        long val = this.randomLong();
        return Long.valueOf(val).shortValue();
    }

    public int randomInt() {
        long val = this.randomLong();
        return Long.valueOf(val).intValue();
    }

    public int randomInt(int min, int max) {
        return this.random.nextInt(min, max);
    }

    public long randomLong() {
        return this.random.nextLong();
    }

    public float randomFloat() {
        long val = this.randomLong();
        return Long.valueOf(val).floatValue();
    }

    public double randomDouble() {
        long val = this.randomLong();
        return Long.valueOf(val).doubleValue();
    }
    public char randomChar() {
        long val = this.randomLong();
        return ((char) Long.valueOf(val).intValue());
    }
}
