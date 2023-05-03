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
    private final char[] nextId;
    private final int max;

    public MtxIdGenerator(MtxCharType... charTypes) {
        this.validateFormatArray(charTypes);
        this.format = charTypes;

        this.nextId = new char[charTypes.length];
        this.populateInitialNextId();

        this.max = this.calculateMax(this.format);
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

        this.nextId = new char[idx];
        this.populateInitialNextId();

        this.max = this.calculateMax(this.format);
    }

    private void validateFormatArray(MtxCharType[] charTypes) {
        if (charTypes.length > 32) {
            throw new IllegalArgumentException("Length of ID format too long. Cannot exceed 32.");
        }
        if (charTypes.length == 0) {
            throw new IllegalArgumentException("Length of ID format too small. Must be at least 1.");
        }
    }

    private void populateInitialNextId() {
        for (int i = 0; i < this.nextId.length; i++) {
            switch (this.format[i]) {
                case CHAR      -> this.nextId[i] = 'A';
                case UINT, ANY -> this.nextId[i] = '0';
            }
        }
    }

    private int calculateMax(MtxCharType[] format) {
        int i = 1;
        for (MtxCharType type : format) {
            switch (type) {
                case CHAR -> i *= 26;
                case UINT -> i *= 10;
                case ANY ->  i *= 36;
            }
        }
        return i;
    }

    public String getNextId() {
        for (int i = this.nextId.length - 1; i >= 0; i--) {
            char c = this.nextId[i];
            switch (this.format[i]) {
                case CHAR -> {
                    char next = ((char) (c + 1));
                    if (next == '[') {
                        this.nextId[i] = 'A';
                        continue;
                    } else {
                        this.nextId[i] = next;
                        return String.valueOf(this.nextId);
                    }
                }
                case UINT -> {
                    char next = ((char) (c + 1));
                    if (next == ':') {
                        this.nextId[i] = '0';
                        continue;
                    } else {
                        this.nextId[i] = next;
                        return String.valueOf(this.nextId);
                    }
                }
                case ANY -> {
                    char next = ((char) (c + 1));
                    if (next == ':') {
                        this.nextId[i] = 'A';
                        return String.valueOf(this.nextId);
                    } else if (next == '[') {
                        this.nextId[i] = '0';
                        continue;
                    } else {
                        this.nextId[i] = next;
                        return String.valueOf(this.nextId);
                    }
                }
            }
        }

        return String.valueOf(this.nextId);
    }

    public int getMaxIds() {
        return this.max;
    }
}
