package com.edavalos.mtx.util.generator.data;

import com.edavalos.mtx.util.string.MtxStringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MtxLoremIpsum {
    protected static final String LOREM_IPSUM_FILEPATH = "/loremIpsum.txt";
    protected static final String ERROR_LOREM_IPSUM_COULD_NOT_BE_LOADED = "Lorem Ipsum could not be loaded!";

    protected static String[][] LOREM_IPSUM = null;

    private static void loadLoremIpsum() throws IOException {
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new File(String.valueOf(MtxLoremIpsum.class.getResource(LOREM_IPSUM_FILEPATH))));
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
}
