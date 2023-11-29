package com.edavalos.mtx.util.db;

/**
 * An integer that can only have a value inside the given range
 */
public class MtxRangedInteger extends Number implements Comparable<MtxUnsignedInteger>  {
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
