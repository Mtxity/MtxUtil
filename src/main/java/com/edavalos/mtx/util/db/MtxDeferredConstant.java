package com.edavalos.mtx.util.db;

/**
 * This data structure is a wrapper for any object deemed immutable that does not have a value ready at instantiation.
 * You can create a new one of these to hold an object which is currently null but will have a value in the future. Once
 * that value is set, however, no further assignments are allowed.
 * <p> </p>
 * This is useful in times when an object with immutable
 * values is created ahead of time, and does not have the values it will use to populate it with yet.
 * @param <T> Object type to wrap
 */
public final class MtxDeferredConstant<T> {
    private T value;
    private boolean isValueSet;
    private boolean throwException;

    // ----------------------- Constructors ------------------------

    /**
     * Creates a new MtxDeferredConstant of type T
     * @param throwException whether to throw an exception when this object's value is overwritten
     */
    public MtxDeferredConstant(boolean throwException) {
        this.value = null;
        this.isValueSet = false;
        this.throwException = throwException;
    }

    /**
     * Creates a new MtxDeferredConstant of type T that throws an exception when value is overwritten
     */
    public MtxDeferredConstant() {
        this(true);
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Sets the value of T
     * @param value new value for T
     * @return true if value is successfully set, false if value already is set
     * @throws UnsupportedOperationException thrown if value already exists and throwException is set to true in
     * constructor
     */
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

    /**
     * Gets the value of T
     * @return value of T. May be null if either value of T was set to null or T has no value set yet.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Checks whether T has a value yet
     * @return true if a value has been assigned to T, false otherwise
     */
    public boolean isValueSet() {
        return this.isValueSet;
    }

    /**
     * Checks whether an exception will be thrown if value is overwritten
     * @return true if throwException was set to true in constructor, false otherwise
     */
    public boolean throwsException() {
        return this.throwException;
    }
}
