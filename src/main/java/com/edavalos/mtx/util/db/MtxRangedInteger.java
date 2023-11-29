package com.edavalos.mtx.util.db;

/**
 * An integer that can only have a value inside the given range
 */
public class MtxRangedInteger extends Number implements Comparable<MtxRangedInteger>  {
    private final boolean throwException;
    private final int min;
    private final int max;
    private int value;
    private boolean hasValue;

    // ----------------------- Constructors ------------------------

    public MtxRangedInteger(int minValue, int maxValue, boolean throwException) {
        this.min = minValue;
        this.max = maxValue;
        this.throwException = throwException;
        this.hasValue = false;
    }

    public MtxRangedInteger(int minValue, int maxValue, boolean throwException, int startingValue) {
        this(minValue, maxValue, throwException);

        this.setValue(startingValue);
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Attempts to set the value for this MtxRangedInteger. If the
     * given value falls outside this MtxRangedInteger's range, then
     * either the closest value possible is set, or an exception is
     * thrown, depending on if throwException is true or not
     *
     * @param newValue the new value to try to set
     * @throws IllegalArgumentException if throwException is true and
     *         newValue falls outside previously set range
     */
    public void setValue(int newValue) {
        if (newValue < this.min || newValue > this.max) {
            if (this.throwException) {
                throw new IllegalArgumentException("Value outside range: " + newValue);
            } else {
                newValue = this.getValueClosestToLimit(value);
            }
        }

        this.value = newValue;
        this.hasValue = true;
    }

    /**
     * Returns the value of this MtxRangedInteger as a byte
     * after a narrowing primitive conversion.
     */
    @Override
    public byte byteValue() {
        return ((byte) this.value);
    }

    /**
     * Returns the value of this MtxRangedInteger as a short
     * after a narrowing primitive conversion.
     */
    @Override
    public short shortValue() {
        return ((short) this.value);
    }

    /**
     * Returns a String object representing this
     * MtxRangedInteger's value. The value is converted to signed
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
     * {@code null} and is an MtxRangedInteger object that
     * contains the same {@code int} value as this object.
     *
     * @param obj the object to compare with.
     * @return true if the objects are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MtxRangedInteger otherRangedInt) {
            return this.value == otherRangedInt.intValue();
        }
        return false;
    }

    /**
     * Returns a hash code for this MtxRangedInteger.
     *
     * @return a hash code value for this object, equal to the
     *         primitive {@code int} value represented by this
     *         MtxRangedInteger object.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    // ----------------- Public Inherited Methods ------------------

    /**
     * Compares two MtxRangedInteger objects numerically.
     *
     * @param anotherMtxRangedInteger the MtxRangedInteger to be
     *                                compared.
     * @return the value {@code 0} if this MtxRangedInteger is
     *         equal to the argument MtxRangedInteger, a value less
     *         than {@code 0} if this MtxRangedInteger is
     *         numerically less than the argument MtxRangedInteger,
     *         and a value greater than {@code 0} if this
     *         MtxRangedInteger is numerically greater than the
     *         argument MtxRangedInteger.
     */
    @Override
    public int compareTo(MtxRangedInteger anotherMtxRangedInteger) {
        int otherRangedInt = anotherMtxRangedInteger.intValue();
        return Integer.compare(this.value, otherRangedInt);
    }

    /**
     * Returns the value of this MtxRangedInteger as an int.
     */
    @Override
    public int intValue() {
        return this.value;
    }

    /**
     * Returns the value of this MtxRangedInteger as a long
     * after a widening primitive conversion.
     */
    @Override
    public long longValue() {
        return ((long) this.value);
    }

    /**
     * Returns the value of this MtxRangedInteger as a float
     * after a widening primitive conversion.
     */
    @Override
    public float floatValue() {
        return ((float) this.value);
    }

    /**
     * Returns the value of this MtxRangedInteger as a double
     * after a widening primitive conversion.
     */
    @Override
    public double doubleValue() {
        return ((double) this.value);
    }

    // ---------------------- Static Methods -----------------------

    /**
     * Parses the string argument as a ranged decimal integer.
     * @param s a String to be parsed.
     * @param max Maximum size this ranged int is allowed to be
     * @param min Minimum size this ranged int is allowed to be
     * @return an MtxRangedInteger represented by the argument
     * @throws NumberFormatException if argument String contains
     *         any characters other than digits or negative signs
     * @throws IllegalArgumentException if value represented by
     *         the argument falls outside given range
     */
    public static MtxRangedInteger parseRangedInt(String s, int min, int max) {
        int newRangedIntVal = Integer.parseInt(s);
        return new MtxRangedInteger(min, max, true, newRangedIntVal);
    }

    // ---------------------- Private Methods ----------------------

    protected int getValueClosestToLimit(int value) {
        if (value < this.min) {
            value = this.min;
        } else if (value > this.max) {
            value = this.max;
        }
        return value;
    }
}
