package com.edavalos.mtx.util.generator.data;

import com.edavalos.mtx.util.string.MtxStringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// Loosely inspired by:
// https://sourceforge.net/p/loremipsum/code/HEAD/tree/trunk/src/main/java/de/svenjacobs/loremipsum/LoremIpsum.java
public class MtxLoremIpsum implements Iterable<String> {
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

    // @TODO: Add javadocs for these methods
    public String getWords(int amount, int startIndex) throws IOException {
        if (amount + startIndex > MAX_LOREMS_WORDS) {
            throw new IndexOutOfBoundsException("Max number of lorem ipsum words is " + MAX_LOREMS_WORDS);
        }
        if (amount < 1) {
            throw new IndexOutOfBoundsException("Minimum number of lorem ipsum words is 1");
        }

        StringBuilder words = new StringBuilder();
        for (int i = startIndex; i < amount; i++) {
            words.append(getFullLoremIpsumInOneLine()[i]);
            if (i != amount - 1) {
                words.append(" ");
            }
        }

        String wordsAsString = words.toString();
        if (wordsAsString.endsWith(",")) {
            wordsAsString = MtxStringUtil.replaceLast(wordsAsString, ",", "");
        }
        return wordsAsString;
    }

    public String getWords(int amount) throws IOException {
        return getWords(amount, 0);
    }

    public String getParagraphs(int amount) throws IOException {
        if (amount > MAX_LOREMS) {
            throw new IndexOutOfBoundsException("Max number of lorem ipsum paragraphs is " + MAX_LOREMS);
        }
        if (amount < 1) {
            throw new IndexOutOfBoundsException("Minimum number of lorem ipsum paragraphs is 1");
        }

        StringBuilder paragraphs = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            String[] words = getFullLoremIpsum()[i];
            paragraphs.append(String.join(" ", words)).append(MtxStringUtil.NEWLINE);
        }

        String paragraphsAsString = paragraphs.toString();
        if (paragraphsAsString.endsWith(MtxStringUtil.NEWLINE)) {
            paragraphsAsString = MtxStringUtil.replaceLast(paragraphsAsString, MtxStringUtil.NEWLINE, "");
        }
        return paragraphsAsString;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return this.idx <= MAX_LOREMS_WORDS;
            }

            @Override
            public String next() {
                try {
                    return getFullLoremIpsumInOneLine()[this.idx++];
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
