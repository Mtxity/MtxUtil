package com.edavalos.mtx.util.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MtxStringBuilder {
    private final ArrayList<Character> parts;


    // ----------------------- Constructors ------------------------

    public MtxStringBuilder() {
        this.parts = new ArrayList<>();
    }

    public MtxStringBuilder(String firstString) {
        this.parts = new ArrayList<>();
        this.appendString(firstString);
    }

    public MtxStringBuilder(Object firstValue) {
        this.parts = new ArrayList<>();
        this.appendString(firstValue.toString());
    }


    // ---------------------- Public Methods -----------------------

    public MtxStringBuilder append(String nextString) {
        if (nextString != null && !nextString.isEmpty()) {
            this.appendString(nextString);
        }
        return this;
    }

    public MtxStringBuilder append(char nextChar) {
        this.parts.add(nextChar);
        return this;
    }

    public MtxStringBuilder append(int nextInt) {
        this.appendString(String.valueOf(nextInt));
        return this;
    }

    public MtxStringBuilder append(long nextLong) {
        this.appendString(String.valueOf(nextLong));
        return this;
    }

    public MtxStringBuilder append(short nextShort) {
        this.appendString(String.valueOf(nextShort));
        return this;
    }

    public MtxStringBuilder append(byte nextByte) {
        this.appendString(String.valueOf(nextByte));
        return this;
    }

    public MtxStringBuilder append(double nextDouble) {
        this.appendString(String.valueOf(nextDouble));
        return this;
    }

    public MtxStringBuilder append(float nextFloat) {
        this.appendString(String.valueOf(nextFloat));
        return this;
    }

    public MtxStringBuilder append(boolean nextBool) {
        this.appendString(String.valueOf(nextBool));
        return this;
    }

    public MtxStringBuilder append(Object nextValue) {
        return this.append(nextValue.toString());
    }

    public MtxStringBuilder append(Object[] nextValue) {
        if (nextValue != null) {
            for (Object o : nextValue) {
                this.append(o.toString());
            }
        }
        return this;
    }

    public MtxStringBuilder append(List<?> nextValue) {
        if (nextValue == null) {
            return this;
        }
        return this.append(nextValue.toArray());
    }


    // ------------------ Private Helper Methods -------------------

    private void appendString(String nextString) {
        Character[] charArray = nextString.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        this.parts.addAll(Arrays.asList(charArray));
    }
}
