package com.edavalos.mtx.util.db.var;

public class MtxOptionalVar<T> {
    private static final MtxOptionalVar<?> EMPTY = new MtxOptionalVar<>(false);

    private T value;
    private boolean hasValue;

    public MtxOptionalVar(T value) {
        this.value = value;
        this.hasValue = true;
    }

    private MtxOptionalVar(boolean hasValue) {
        this.value = null;
        this.hasValue = false;
    }

    public static <T> MtxOptionalVar<T> empty() {
        return (MtxOptionalVar<T>) EMPTY;
    }

    public static <T> MtxOptionalVar<T> of(T value) {
        return new MtxOptionalVar<>(value);
    }
}
