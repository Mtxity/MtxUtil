package com.edavalos.mtx.util.db;

/**
 * Bit columns are a way to store multiple booleans in a database without needing a separate column for each boolean.
 * Instead of 32 separate columns, only one integer column is needed, and every bit used to store this integer holds a
 * value representing some boolean. The integer's value itself is useless, as it is only a placeholder for bits.
 * <p> </p>
 * This data structure can be treated like a 32 bit boolean array, except the data is stored is an int, ready to be
 * imported and exported into a database.
 */
public final class MtxBitColumn {
    private int value;

    // ----------------------- Constructors ------------------------

    public MtxBitColumn() {
        this(0);
    }

    public MtxBitColumn(int intValue) {
        this.value = intValue;
    }

    public MtxBitColumn(boolean[] bitArray) {
        this.value = this.convertBooleanArrayToInt(bitArray);
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Sets the value of a bit at the specified column / index
     * @param column Index of bit to change (zero indexed)
     * @param newValue New value of bit
     * @return Old value of bit at this column
     */
    public boolean setBit(int column, boolean newValue) {
        boolean previousVal = this.getBit(column);
        if (newValue) {
            this.value = this.value + ((int) Math.pow(2, column));
        } else {
            this.value = this.value - ((int) Math.pow(2, column));
        }
        return previousVal;
    }

    /**
     * Gets the value of a bit at the specified column / index
     * @param column Index of bit to look up (zero indexed)
     * @return Value of bit at this column
     */
    public boolean getBit(int column) {
        // SQL equivalent: BITWISE_AND(this.value, CAST(POWER(2, column) AS INT)) > 0
        int deconstructed = (this.value & ((int) Math.pow(2, column)));
        return deconstructed > 0 && deconstructed != this.value;
    }

    /**
     * Gets the database-friendly version of this bit column
     * @return Integer used to store bits
     */
    public int getAllBits() {
        return this.value;
    }

    /**
     * Returns a boolean array representation of this bit column
     * @return Boolean array of length 32 with every bit's value
     */
    public boolean[] getAllBitsAsArray() {
        return this.convertIntToBooleanArray(this.value);
    }

    /**
     * Overwrites the integer representing this bit column. Typically used when reading from a database
     * @param value Integer used to store bits
     */
    public void setAllBits(int value) {
        this.value = value;
    }

    /**
     * Overwrites the integer representing this bit column with an array holding 32 booleans
     * @param bitArray boolean array of size 32
     */
    public void setAllBits(boolean[] bitArray) {
        this.value = this.convertBooleanArrayToInt(bitArray);
    }

    /**
     * Sets all bits in this bit column to false
     */
    public void clear() {
        this.value = 0;
    }

    // ---------------------- Private Methods ----------------------

    protected int convertBooleanArrayToInt(boolean[] booleans) {
        int i = 0;
        for (int j = 0; j < 31; j++) {
            if (booleans[j]) {
                i += ((int) Math.pow(2, j));
            }
        }
        return i;
    }

    protected boolean[] convertIntToBooleanArray(int i) {
        boolean[] bits = new boolean[32];
        for (int j = 0; j < 31; j++) {
            bits[j] = (i & ((int) Math.pow(2, j))) > 0;
        }
        return bits;
    }
}
