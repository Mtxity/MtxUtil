package com.edavalos.mtx.util.generator.data;

public class MtxCsv {
    private final String[][] contents;

    public MtxCsv(int size) {
        this.contents = new String[size][];
        for (int i = 0; i < size; i++) {
            this.contents[i] = new String[size];
        }
    }
}
