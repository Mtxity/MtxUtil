package com.edavalos.mtx.util.db;

import java.util.ArrayList;
import java.util.List;

public final class MtxIdGenerator {
    public enum MtxCharType {
        CHAR,
        UINT,
        ANY
    }

    private final MtxCharType[] format;

    public MtxIdGenerator(MtxCharType... charTypes) {
        this.validateFormatArray(charTypes);
        this.format = charTypes;
    }

    public MtxIdGenerator(String format) {
        List<MtxCharType> charTypes = new ArrayList<>();

        byte idx = 0;
        for (char c : format.toCharArray()) {
            if (idx >= 32) {
                throw new IllegalArgumentException("Length of ID format too long. Cannot exceed 32.");
            }

            switch (c) {
                case 'L' -> charTypes.add(MtxCharType.CHAR);
                case 'N' -> charTypes.add(MtxCharType.UINT);
                case 'A' -> charTypes.add(MtxCharType.ANY);
                default -> throw new IllegalArgumentException("Unknown character at position: " + idx + ". " +
                                                              "Must be either 'L', 'N', or 'A'.");
            }

            idx ++;
        }

        this.format = charTypes.toArray(new MtxCharType[idx]);
        this.validateFormatArray(this.format);
    }

    private void validateFormatArray(MtxCharType[] charTypes) {
        if (charTypes.length > 32) {
            throw new IllegalArgumentException("Length of ID format too long. Cannot exceed 32.");
        }
        if (charTypes.length == 0) {
            throw new IllegalArgumentException("Length of ID format too small. Must be at least 1.");
        }
    }
}
