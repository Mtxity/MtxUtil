package com.edavalos.mtx.util.generator.data;

public class MtxPopulatedCsv {
    private final String xChar = "x";
    private final String yChar = "y";

    private final String[][] contents;
    private boolean editable;

    public MtxPopulatedCsv(int size) {
        this.contents = new String[size][];
        for (int i = 0; i < size; i++) {
            this.contents[i] = new String[size];
        }
        this.fillContentsWithGenericSamples();
        this.editable = true;
    }

    public MtxPopulatedCsv(String[][] contents) {
        this.contents = contents;
        this.editable = true;
    }

    private void fillContentsWithGenericSamples() {
        for (int x = 0; x < this.contents.length; x++) {
            for (int y = 0; y < this.contents[x].length; y++) {
                this.contents[x][y] = this.xChar.repeat(x) + this.yChar.repeat(y);
            }
        }
    }

    public String get(int x, int y) {
        return this.contents[x][y];
    }
}
