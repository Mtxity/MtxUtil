package com.edavalos.mtx.util.generator.data;

import com.edavalos.mtx.util.string.MtxStringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MtxLoremIpsum {
    protected static final String LOREM_IPSUM_FILEPATH = "./loremIpsum.txt";
    protected static final String ERROR_LOREM_IPSUM_COULD_NOT_BE_LOADED = "Lorem Ipsum could not be loaded!";
    protected static final int MAX_LOREMS = 75;
    protected static final int MAX_LOREMS_WORDS = 8382;

    protected static String[][] LOREM_IPSUM = null;
    protected static String[] LOREM_IPSUM_SINGLE_LINE = null;

    private static void loadLoremIpsum() throws IOException {
        Scanner fileScanner;
        try {
            File loremFile = new File(MtxLoremIpsum.class.getClassLoader().getResource(LOREM_IPSUM_FILEPATH).getFile());
            fileScanner = new Scanner(loremFile);
        } catch (FileNotFoundException e) {
            throw new IOException(ERROR_LOREM_IPSUM_COULD_NOT_BE_LOADED);
        }

        List<String[]> rows = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            String loremLine = fileScanner.nextLine();
            if (MtxStringUtil.isEmpty(loremLine)) {
                continue;
            }

            rows.add(loremLine.split(MtxStringUtil.SPACE_QUOTED_PATTERN));
        }
        fileScanner.close();

        LOREM_IPSUM = rows.toArray(new String[0][]);
    }

    protected static String[][] getFullLoremIpsum() throws IOException {
        if (LOREM_IPSUM == null) {
            loadLoremIpsum();
        }

        return LOREM_IPSUM;
    }

    private static void loadFullLoremIpsumInOneLine() throws IOException {
        if (LOREM_IPSUM == null) {
            loadLoremIpsum();
        }

        List<String> words = new ArrayList<>();
        for (String[] row : LOREM_IPSUM) {
            words.addAll(Arrays.asList(row));
        }
        LOREM_IPSUM_SINGLE_LINE = words.toArray(new String[0]);
    }

    protected static String[] getFullLoremIpsumInOneLine() throws IOException {
        if (LOREM_IPSUM_SINGLE_LINE == null) {
            loadFullLoremIpsumInOneLine();
        }

        return LOREM_IPSUM_SINGLE_LINE;
    }


    // ---------------------- Public Methods -----------------------

    public String getWords(int amount) throws IOException {
        if (amount > MAX_LOREMS_WORDS) {
            throw new IndexOutOfBoundsException("Max number of lorem ipsum words is " + MAX_LOREMS_WORDS);
        }
        if (amount < 1) {
            throw new IndexOutOfBoundsException("Minimum number of lorem ipsum words is 1");
        }

        StringBuilder words = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            words.append(getFullLoremIpsumInOneLine()[i]);
            if (i != amount - 1) {
                words.append(" ");
            }
        }
        return words.toString();
    }
}
