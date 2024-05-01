package com.edavalos.mtx.util.string;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class MtxStringBuilder {
    private final LinkedList<Character> parts;


    // ----------------------- Constructors ------------------------

    public MtxStringBuilder() {
        this.parts = new LinkedList<>();
    }

    public MtxStringBuilder(String firstString) {
        this.parts = new LinkedList<>();
        this.appendString(firstString);
    }

    public MtxStringBuilder(Object firstValue) {
        this.parts = new LinkedList<>();
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

    public MtxStringBuilder appendSpace() {
        return this.append(' ');
    }

    public MtxStringBuilder appendSpaces(int count) {
        if (count < 0) {
            throw new StringIndexOutOfBoundsException("Cannot append string of negative size: " + count);
        }
        for (int i = 0; i < count; i++) {
            this.parts.add(' ');
        }
        return this;
    }

    public MtxStringBuilder reverse() {
        if (this.length() <= 1) {
            return this;
        }
        MtxStringBuilder newMtxStringBuilder = new MtxStringBuilder();
        for (int i = this.parts.size() - 1; i >= 0; i--) {
            newMtxStringBuilder.append(((char) this.parts.get(i)));
        }
        return newMtxStringBuilder;
    }

    public MtxStringBuilder replaceAll(char target, char replacement) {
        this.parts.replaceAll(character -> {
            if (character == target) {
                return replacement;
            } else {
                return character;
            }
        });
        return this;
    }

    public int length() {
        return this.parts.size();
    }

    public char charAt(int index) {
        if (index < 0 || index >= this.length()) {
            throw new IndexOutOfBoundsException("Index outside bounds of MtxStringBuilder: " + index);
        }
        return this.parts.get(index);
    }


    // ------------------ Private Helper Methods -------------------

    private void appendString(String nextString) {
        Character[] charArray = nextString.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        this.parts.addAll(Arrays.asList(charArray));
    }
}
