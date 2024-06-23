package com.edavalos.mtx.util.generator.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MtxPopulatedCsv {
    private static final String SAMPLE_CSV_FILEPATH = "./randomData.csv";
    private static final String ERROR_SAMPLE_CSV_COULD_NOT_BE_LOADED = "Sample csv could not be loaded!";

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

    public MtxPopulatedCsv() {
        this.contents = loadSampleCsv().stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        this.editable = true;
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
                rowScanner.useDelimiter(",");
                while (rowScanner.hasNext()) {
                    values.add(rowScanner.next());
                }
            }
            data.add(values);
        }
        return data;
    }

    public String get(int x, int y) {
        return this.contents[x][y];
    }
}
