package com.edavalos.mtx.util.string.search;

public interface MtxSearch {

    int search(String pattern);

    default boolean contains(String pattern) {
        return this.search(pattern) != -1;
    }

    String getText();
}
