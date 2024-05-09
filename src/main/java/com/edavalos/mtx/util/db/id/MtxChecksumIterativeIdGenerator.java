package com.edavalos.mtx.util.db.id;

import java.util.Arrays;

public class MtxChecksumIterativeIdGenerator extends MtxChecksumIdGenerator {
    private int nextId;

    public MtxChecksumIterativeIdGenerator(int length) {
        super(length);
    }

    @Override
    protected int[] getInitialDigits(int length) {
        if (String.valueOf(this.nextId).length() > length) {
            this.nextId = 0;
        }
        char[] digitChars = String.format("%0" + length + "d", this.nextId).toCharArray();
        int[] digitInts = new int[length];
        Arrays.setAll(digitInts, i -> Character.getNumericValue(digitChars[i]));
        this.nextId ++;
        return digitInts;
    }
}
