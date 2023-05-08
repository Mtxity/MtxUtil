package com.edavalos.mtx.util.db;

public final class MtxUnsignedInteger extends Number implements Comparable<MtxUnsignedInteger> {
    private final int value;

    // ----------------------- Constructors ------------------------

    public MtxUnsignedInteger(int value) {
        this.validateIntIsNotNegative(value);

        this.value = value;
    }

    public MtxUnsignedInteger(String s) {
        this(Integer.parseInt(s));
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Returns the value of this MtxUnsignedInteger as a byte
     * after a narrowing primitive conversion.
     */
    @Override
    public byte byteValue() {
        return ((byte) this.value);
    }

    /**
     * Returns the value of this MtxUnsignedInteger as a short
     * after a narrowing primitive conversion.
     */
    @Override
    public short shortValue() {
        return ((short) this.value);
    }

    /**
     * Returns a String object representing this
     * MtxUnsignedInteger's value. The value is converted to signed
     * decimal representation and returned as a string, exactly as if
     * the integer value were given as an argument to the {@link
     * java.lang.Integer#toString(int)} method.
     *
     * @return a string representation of the value of this object in
     *         base&nbsp;10.
     */
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    /**
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is an MtxUnsignedInteger object that
     * contains the same {@code int} value as this object.
     *
     * @param obj the object to compare with.
     * @return true if the objects are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MtxUnsignedInteger otherUInt) {
            return this.value == otherUInt.intValue();
        }
        return false;
    }

    /**
     * Returns a hash code for this MtxUnsignedInteger.
     *
     * @return a hash code value for this object, equal to the
     *         primitive {@code int} value represented by this
     *         MtxUnsignedInteger object.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    // ----------------- Public Inherited Methods ------------------

    /**
     * Compares two MtxUnsignedInteger objects numerically.
     *
     * @param  anotherMtxUnsignedInteger the MtxUnsignedInteger to be
     *                                   compared.
     * @return the value {@code 0} if this MtxUnsignedInteger is
     *         equal to the argument MtxUnsignedInteger, a value less
     *         than {@code 0} if this MtxUnsignedInteger is
     *         numerically less than the argument MtxUnsignedInteger,
     *         and a value greater than {@code 0} if this
     *         MtxUnsignedInteger is numerically greater than the
     *         argument MtxUnsignedInteger.
     */
    @Override
    public int compareTo(MtxUnsignedInteger anotherMtxUnsignedInteger) {
        int otherUInt = anotherMtxUnsignedInteger.intValue();
        return Integer.compare(this.value, otherUInt);
    }

    /**
     * Returns the value of this MtxUnsignedInteger as an int.
     */
    @Override
    public int intValue() {
        return this.value;
    }

    /**
     * Returns the value of this MtxUnsignedInteger as a long
     * after a widening primitive conversion.
     */
    @Override
    public long longValue() {
        return ((long) this.value);
    }

    /**
     * Returns the value of this MtxUnsignedInteger as a float
     * after a widening primitive conversion.
     */
    @Override
    public float floatValue() {
        return ((float) this.value);
    }

    /**
     * Returns the value of this MtxUnsignedInteger as a double
     * after a widening primitive conversion.
     */
    @Override
    public double doubleValue() {
        return ((double) this.value);
    }

    // ---------------------- Private Methods ----------------------

    private void validateIntIsNotNegative(int i) {
        if (i < 0) {
            throw new NumberFormatException(String.valueOf(i));
        }
    }
}
