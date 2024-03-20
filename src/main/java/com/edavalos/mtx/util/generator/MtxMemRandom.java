package com.edavalos.mtx.util.generator;

public class MtxMemRandom extends MtxRandom {
    public MtxMemRandom() {
        super(getCpuMemoryAsLong());
    }

    private static long getCpuMemoryAsLong() {
        return Runtime.getRuntime().freeMemory();
    }
}
