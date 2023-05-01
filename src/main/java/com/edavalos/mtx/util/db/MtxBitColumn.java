package com.edavalos.mtx.util.db;

public final class MtxBitColumn {
    private int value;

    public MtxBitColumn() {
        this(0);
    }

    public MtxBitColumn(int intValue) {
        this.value = intValue;
    }

    public MtxBitColumn(boolean[] bitArray) {
        //
    }
}
