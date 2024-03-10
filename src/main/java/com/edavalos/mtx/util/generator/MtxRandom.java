package com.edavalos.mtx.util.generator;

import java.util.Random;

public abstract class MtxRandom {
    private final Random random;

    public MtxRandom(long seed) {
        this.random = new Random(seed);
    }
}
