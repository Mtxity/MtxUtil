package com.edavalos.mtx.util.db.var;

import java.util.NoSuchElementException;

/**
 * A wrapper for an object that optionally has a value assigned. Accessing this should be preceded by checking if it
 * has a value first. This object is immutable, and once it is created either empty or with a value, it will stay like
 * that.
 * <p> </p>
 * This is useful when you need to exit early from a method that requires returning something. Returning a regular type,
 * false, or -1 might be confused with a success if that value might be returned at the end of the method too. Making
 * the return value optional indicates that a value might not have been returned for that method call.
 * @param <T> Object type to store if needed
 */
public class MtxOptionalVar<T> {
    private static final String NO_VAL = "No value present";
    private static final MtxOptionalVar<?> EMPTY = new MtxOptionalVar<>();

    private final T value;
    private final boolean hasValue;

    // ----------------------- Constructors ------------------------

    /**
     * Creates a new MtxOptionalVar of type T that has a value. Setting the value to null is the same as making it empty
     * @param value starting value
     */
    public MtxOptionalVar(T value) {
        this.value = value;
        this.hasValue = true;
    }

    // For use in EMPTY const
    private MtxOptionalVar() {
        this.value = null;
        this.hasValue = false;
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Returns an empty MtxOptionalVar
     * @return An empty MtxOptionalVar with no value
     * @param <T> this MtxOptionalVar's type, if specified. However, it is not needed
     */
    public static <T> MtxOptionalVar<T> empty() {
        @SuppressWarnings("unchecked")
        MtxOptionalVar<T> emptyOptional = (MtxOptionalVar<T>) EMPTY;
        return emptyOptional;
    }

    /**
     * Returns a newly created MtxOptionalVar with the given value. Infers the type based on value provided. Equivalent
     * to doing {@code new MtxOptionalVar<T>(value)}
     * @param value The value to store in this MtxOptionalVar.
     * @return A non-empty MtxOptionalVar of type {@code <T>} that holds {@code value}
     * @param <T> The type of {@code value}
     */
    public static <T> MtxOptionalVar<T> of(T value) {
        return new MtxOptionalVar<>(value);
    }

    /**
     * Checks if this MtxOptionalVar is empty or not
     * @return true if this mtxOptionalVar is empty, false if it has a value
     */
    public boolean isEmpty() {
        return !this.hasValue || this.value == null;
    }

    /**
     * Gets the value in this MtxOptionalVar, unless it is empty in which case it throws an exception
     * @return The value in this MtxOptionalVar
     * @throws NoSuchElementException if this MtxOptionalVar is empty
     */
    public T getValue() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException(NO_VAL);
        }
        return this.value;
    }

    /**
     * Indicates whether another MtxOptionalVar's value is equal to this one. In order for them to be equal they both
     * need to be of the same type and have the same value. Two MtxOptionalVars that are both empty are considered equal
     * @param obj another object to compare this to
     * @return true if both are MtxOptionalVars, and either both have the same type and value, or both are empty
     */
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
