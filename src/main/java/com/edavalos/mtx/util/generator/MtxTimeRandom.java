package com.edavalos.mtx.util.generator;

import java.time.Clock;

public class MtxTimeRandom extends MtxRandom {
    public MtxTimeRandom() {
        super(getTimeAsLong());
    }

    private static long getTimeAsLong() {
        long time = System.currentTimeMillis();
        long nano = Clock.systemDefaultZone().instant().getNano();
        return time * nano;
    }
}
