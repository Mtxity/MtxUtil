package com.edavalos.mtx.util.db;

public final class MtxDeferredConstant<T> {
    private T value;
    private boolean isValueSet;
    private boolean throwException;

    public MtxDeferredConstant(boolean throwException) {
        this.value = null;
        this.isValueSet = false;
        this.throwException = throwException;
    }

    public MtxDeferredConstant() {
        this(true);
    }

    public boolean setValue(T value) {
        if (this.isValueSet) {
            if (this.throwException) {
                throw new UnsupportedOperationException("Value has already been set and therefore cannot be changed.");
            } else {
                return false;
            }
        }

        this.value = value;
        this.isValueSet = true;
        return true;
    }

    public T getValue() {
        return this.value;
    }

    public boolean isValueSet() {
        return this.isValueSet;
    }

    public boolean throwsException() {
        return this.throwException;
    }
}
