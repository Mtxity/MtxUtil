package com.edavalos.mtx.util.string;

import java.util.ArrayList;

public class MtxStringBuilder {
    private final ArrayList<String> parts;


    // ----------------------- Constructors ------------------------

    public MtxStringBuilder() {
        this.parts = new ArrayList<>();
    }

    public MtxStringBuilder(String firstString) {
        this.parts = new ArrayList<>();
        this.parts.add(firstString);
    }

    public MtxStringBuilder(Object firstValue) {
        this.parts = new ArrayList<>();
        this.parts.add(firstValue.toString());
    }


    // ---------------------- Public Methods -----------------------

    public MtxStringBuilder append(String nextString) {
        this.parts.add(nextString);
        return this;
    }

    public MtxStringBuilder append(char nextChar) {
        this.parts.add(String.valueOf(nextChar));
        return this;
    }

    public MtxStringBuilder append(int nextInt) {
        this.parts.add(String.valueOf(nextInt));
        return this;
    }

    public MtxStringBuilder append(long nextLong) {
        this.parts.add(String.valueOf(nextLong));
        return this;
    }

    public MtxStringBuilder append(short nextShort) {
        this.parts.add(String.valueOf(nextShort));
        return this;
    }

    public MtxStringBuilder append(byte nextByte) {
        this.parts.add(String.valueOf(nextByte));
        return this;
    }

    public MtxStringBuilder append(double nextDouble) {
        this.parts.add(String.valueOf(nextDouble));
        return this;
    }

    public MtxStringBuilder append(float nextFloat) {
        this.parts.add(String.valueOf(nextFloat));
        return this;
    }

    public MtxStringBuilder append(boolean nextBool) {
        this.parts.add(String.valueOf(nextBool));
        return this;
    }


    // ------------------ Private Helper Methods -------------------

    private String temp() {
        return "";
    }
}
