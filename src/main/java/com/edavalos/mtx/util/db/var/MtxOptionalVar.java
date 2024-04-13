package com.edavalos.mtx.util.db.var;

import java.util.NoSuchElementException;

public class MtxOptionalVar<T> {
    private static final String NO_VAL = "No value present";
    private static final MtxOptionalVar<?> EMPTY = new MtxOptionalVar<>(false);

    private final T value;
    private final boolean hasValue;

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

    public boolean isEmpty() {
        return !(this.hasValue || this.value == null);
    }

    public T getValue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException(NO_VAL);
        }
        return this.value;
    }
}
