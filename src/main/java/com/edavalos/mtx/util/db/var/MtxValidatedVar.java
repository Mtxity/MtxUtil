package com.edavalos.mtx.util.db.var;

public class MtxValidatedVar<T> {
    protected static final String ERROR_MSG = "Cannot access value of invalid MtxValidatedVar";

    private T value;
    private boolean isValid;

    // ----------------------- Constructors ------------------------

    public MtxValidatedVar(T value) {
        this.value = value;
        this.isValid = true;
    }

    public MtxValidatedVar() {
        this.value = null;
        this.isValid = true;
    }

    public MtxValidatedVar(T value, boolean isValid) {
        this.value = value;
        this.isValid = isValid;
    }

    // ---------------------- Public Methods -----------------------

    public T getValue() {
        if (this.isValid) {
            return this.value;
        } else {
            throw new IllegalStateException(ERROR_MSG);
        }
    }

    public void setValue(T newValue) {
        this.value = newValue;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void invalidate() {
        this.isValid = false;
    }

    public void validate() {
        this.isValid = true;
    }
}
