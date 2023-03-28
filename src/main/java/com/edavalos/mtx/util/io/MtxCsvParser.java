package com.edavalos.mtx.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class MtxCsvParser {
    private String[][] csvContents;

    public MtxCsvParser(String filePath) throws IOException {
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new IOException("The file '" + filePath + "' could not be found.");
        }

        String[] fileName = filePath.split(Pattern.quote("."));
        if (!fileName[fileName.length - 1].endsWith(".csv")) {
            throw new IOException("The file '" + filePath + "' is not a csv file.");
        }
    }
}
