package com.edavalos.mtx.util.list;

import java.util.regex.Pattern;

/**
 * Disclaimer: This implementation of MtxList is purposefully stupid
 * */
public final class MtxStringList<T> implements Iterable<T> {
    public interface MtxStringDecoder<E> {
        E fromString(String stringRepresentation);
    }

    private static final String DELIMITER = ", ";
    private static final String EMPTY_STRING = "";

    private final MtxStringDecoder<T> mtxStringDecoder;
    private String content;
    private int size;

    public MtxStringList(MtxStringDecoder<T> stringDecoder) {
        this.mtxStringDecoder = stringDecoder;
        this.content = EMPTY_STRING;
        this.size = 0;
    }

    public MtxStringList(MtxStringDecoder<T> stringDecoder, T[] initialContents) {
        this.mtxStringDecoder = stringDecoder;
        this.content = EMPTY_STRING;
        this.size = 0;
        this.addAll(initialContents);
    }

    // @TODO: Add a check to prevent unserializable elements from being added & throw an exception if one is added
    public void add(T element) {
        if (element.toString().contains(DELIMITER)) {
            throw new IllegalArgumentException(
                    "A comma followed by a space (', ') is MtxStringList's delimiter and therefore is not allowed " +
                    "to be in an element of this MtxList"
            );
        }

        if (this.size != 0) {
            this.content += DELIMITER;
        }

        this.content += element.toString();
        this.size ++;
    }

    public void addAll(T[] elements) {
        for (T element : elements) {
            if (element.toString().contains(DELIMITER)) {
                throw new IllegalArgumentException(
                        "A comma followed by a space (', ') is MtxStringList's delimiter and therefore is not allowed " +
                                "to be in an element of this MtxList"
                );
            }
        }

        if (this.size != 0) {
            this.content += DELIMITER;
        }

        StringBuilder newElements = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                newElements.append(DELIMITER);
            }
            newElements.append(elements[i].toString());
        }

        this.content += newElements.toString();
        this.size += elements.length;
    }

    @Override
    public String toString() {
        return "[" + this.content + "]";
    }
}
