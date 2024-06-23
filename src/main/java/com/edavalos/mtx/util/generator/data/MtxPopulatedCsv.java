package com.edavalos.mtx.util.generator.data;

import com.edavalos.mtx.util.string.MtxStringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MtxPopulatedCsv {
    private static final String SAMPLE_CSV_FILEPATH = "./randomData.csv";
    private static final String ERROR_SAMPLE_CSV_COULD_NOT_BE_LOADED = "Sample csv could not be loaded!";
    private static final String ERROR_MODIFICATIONS_DISALLOWED = "This MtxPopulatedCsv cannot be modified! No direct access allowed!";

    private final String xChar = "x";
    private final String yChar = "y";

    protected final String[][] contents;
    protected boolean editable;

    public MtxPopulatedCsv(int size, boolean editable) {
        this.contents = new String[size][];
        for (int i = 0; i < size; i++) {
            this.contents[i] = new String[size];
        }
        this.fillContentsWithGenericSamples();
        this.editable = editable;
    }

    public MtxPopulatedCsv(String[][] contents, boolean editable) {
        this.contents = contents;
        this.editable = editable;
    }

    public MtxPopulatedCsv(boolean editable) {
        this.contents = loadSampleCsv().stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        this.editable = editable;
    }

    private void fillContentsWithGenericSamples() {
        for (int x = 0; x < this.contents.length; x++) {
            for (int y = 0; y < this.contents[x].length; y++) {
                this.contents[x][y] = this.xChar.repeat(x) + this.yChar.repeat(y);
            }
        }
    }

    private static List<List<String>> loadSampleCsv() {
        Scanner fileScanner;
        try {
            File sampleCsv = new File(MtxPopulatedCsv.class.getClassLoader().getResource(SAMPLE_CSV_FILEPATH).getFile());
            fileScanner = new Scanner(sampleCsv);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(ERROR_SAMPLE_CSV_COULD_NOT_BE_LOADED);
        }

        List<List<String>> data = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            List<String> values = new ArrayList<>();
            try (Scanner rowScanner = new Scanner(fileScanner.nextLine())) {
                rowScanner.useDelimiter(MtxStringUtil.COMMA);
                while (rowScanner.hasNext()) {
                    values.add(rowScanner.next());
                }
            }
            data.add(values);
        }
        return data;
    }

    public void setAt(int x, int y, String newValue) throws IllegalAccessException {
        if (!this.editable) {
            throw new IllegalAccessException(ERROR_MODIFICATIONS_DISALLOWED);
        }

        this.contents[x][y] = newValue;
    }

    public String getAt(int x, int y) {
        return this.contents[x][y];
    }

    public String[][] getCopy() {
        return Arrays.stream(this.contents).map(String[]::clone).toArray(String[][]::new);
    }

    public String[][] getOriginal() throws IllegalAccessException {
        if (!this.editable) {
            throw new IllegalAccessException(ERROR_MODIFICATIONS_DISALLOWED);
        }
        return this.contents;
    }
}
