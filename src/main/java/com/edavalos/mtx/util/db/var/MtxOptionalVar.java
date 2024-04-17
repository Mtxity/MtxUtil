package com.edavalos.mtx.util.db.var;

import java.util.NoSuchElementException;

public class MtxOptionalVar<T> {
    private static final String NO_VAL = "No value present";
    private static final MtxOptionalVar<?> EMPTY = new MtxOptionalVar<>();

    private final T value;
    private final boolean hasValue;

    public MtxOptionalVar(T value) {
        this.value = value;
        this.hasValue = true;
    }

    private MtxOptionalVar() {
        this.value = null;
        this.hasValue = false;
    }

    public static <T> MtxOptionalVar<T> empty() {
        @SuppressWarnings("unchecked")
        MtxOptionalVar<T> emptyOptional = (MtxOptionalVar<T>) EMPTY;
        return emptyOptional;
    }

    public static <T> MtxOptionalVar<T> of(T value) {
        return new MtxOptionalVar<>(value);
    }

    public boolean isEmpty() {
        return !this.hasValue || this.value == null;
    }

    public T getValue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException(NO_VAL);
        }
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MtxOptionalVar<?> other)) {
            return false;
        }

        if (other.isEmpty() && this.isEmpty()) {
            return true;
        }

        if (!other.isEmpty() && !this.isEmpty()) {
            assert other.value != null && this.value != null;
            Class<?> otherType = other.value.getClass();
            Class<?> thisType = this.value.getClass();
            if (!thisType.equals(otherType)) {
                return false;
            } else {
                return other.value.equals(this.value);
            }
        }

        return false;
    }
}
