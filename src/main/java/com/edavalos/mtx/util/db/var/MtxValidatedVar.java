package com.edavalos.mtx.util.db.var;

/**
 * A wrapper for any object that may be intentionally inaccessible. The value inside can only be read when allowed to
 * be. That is, when valid. Invalidating this MtxValidatedVar prevents any reads of its inner value until it is
 * re-validated (if ever). Writes can happen on an invalid MtxValidatedVar, however.
 * <p> </p>
 * This is useful when you need to catch an error in a method but still return something. Returning a regular null,
 * false, or -1 might be confused with a success if that value might be returned anyway. Making the return value invalid
 * prevents any ambiguity that an error occurred and that value should not be used.
 * @param <T> Object type to store
 */
public class MtxValidatedVar<T> {
    protected static final String ERROR_MSG = "Cannot access value of invalid MtxValidatedVar";

    private T value;
    private boolean isValid;

    // ----------------------- Constructors ------------------------

    /**
     * Creates a new MtxValidatedVar of type T that is valid by default
     * @param value starting value
     */
    public MtxValidatedVar(T value) {
        this.value = value;
        this.isValid = true;
    }

    /**
     * Creates a new MtxValidatedVar of type T that is valid by default and has a null value
     */
    public MtxValidatedVar() {
        this.value = null;
        this.isValid = true;
    }

    /**
     * Creates a new MtxValidatedVar of type T
     * @param value starting value
     * @param isValid whether this MtxValidatedVar is valid or not
     */
    public MtxValidatedVar(T value, boolean isValid) {
        this.value = value;
        this.isValid = isValid;
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Gets the value in this MtxValidatedVar, unless it is invalid in which case it throws an exception
     * @return The value in this MtxValidatedVar
     * @throws IllegalStateException if this MtxValidatedVar is currently invalid
     */
    public T getValue() throws IllegalStateException {
        if (this.isValid()) {
            return this.value;
        } else {
            throw new IllegalStateException(ERROR_MSG);
        }
    }

    /**
     * Sets the value in this MtxValidatedVar
     * @param newValue new value to be stored
     */
    public void setValue(T newValue) {
        this.value = newValue;
    }

    /**
     * Checks if this MtxValidatedVar is valid or not. (i.e. if it's value can be read)
     * @return this MtxValidatedVar's valid status
     */
    public boolean isValid() {
        return this.isValid;
    }

    /**
     * Invalidates this MtxValidatedVar so no reads can happen. Does nothing if already invalid
     */
    public void invalidate() {
        this.isValid = false;
    }

    /**
     * Validates this MtxValidatedVar so reads can happen again. Does nothing if already valid
     */
    public void validate() {
        this.isValid = true;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj another object to compare this to
     * @return true if both are MtxValidatedVars, and either both have the same value, both are null, or both are
     * invalid
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MtxValidatedVar<?> other)) {
            return false;
        }

        // If both are invalid, they are considered equal since accessing their values would cause an equal result
        if (!other.isValid() && !this.isValid()) {
            return true;
        }

        if (other.getValue() == null) {
            return this.getValue() == null;
        }

        if (this.getValue() == null) {
            return other.getValue() == null;
        }

        return other.getValue().equals(this.getValue());
    }
}
