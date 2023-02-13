package com.edavalos.mtx.util.list;

/**
 * Disclaimer: This implementation of MtxList is purposefully stupid
 * */
public final class MtxStringList<T> {
    private static final String DELIMITER = "~";
    private static final String EMPTY = "";
    private String content;
    private int size;

    public MtxStringList() {
        this.content = EMPTY;
        this.size = 0;
    }

    public MtxStringList(T[] initialContents) {
        this.content = EMPTY;
        this.size = 0;
        for (T element: initialContents) {
            this.add(element);
        }

        if (!this.content.equals(EMPTY)) {
            this.content = this.content.substring(0, this.content.length() - DELIMITER.length());
        }
    }

    // @TODO: Make a "more efficient" version of this
    // @TODO: Add a check to prevent unserializable elements from being added & throw an exception if one is added
    public void add(T element) {
        if (element.toString().equals(DELIMITER)) {
            throw new IllegalArgumentException(
                    "The string '" + DELIMITER + "' is this MtxStringList's delimiter and therefore is not allowed" +
                    "to be an element in this MtxList"
            );
        }

        this.content += element.toString() + DELIMITER;
        this.size ++;
    }

    @Override
    public String toString() {
        return "[" +
            this.content.replaceAll(DELIMITER, ", ").substring(0, (this.size * 3) - 2)
        + "]";
    }
}
