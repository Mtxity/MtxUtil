package com.edavalos.mtx.util.db;

public final class MtxDeferredConstant<T> {
    private T value;
    private boolean isValueSet;

    public MtxDeferredConstant() {
        this.value = null;
        this.isValueSet = false;
    }

    public void setValue(T value) {
        if (this.isValueSet) {
            throw new UnsupportedOperationException("Value has already been set and therefore cannot be changed.");
        }

        this.value = value;
        this.isValueSet = true;
    }

    public T getValue() {
        return this.value;
    }
}
