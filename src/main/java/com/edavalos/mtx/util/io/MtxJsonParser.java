package com.edavalos.mtx.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MtxJsonParser {
    protected enum MtxJsonTokenType {
        OPENING_BRACKET, // opens a section
        CLOSING_BRACKET, // closes a section
        OPENING_BRACE,   // opens a list
        CLOSING_BRACE,   // closes a list
        STRING,          // key or value
        COLON,           // separates a key and value
        COMMA;           // separates values in a list
        // Keys must be strings. Values can be a string, list or section.
        // Lists can contain any type of value.
    }

    private final String rawStream;

    public MtxJsonParser(String filePath) throws IOException {
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new IOException("The file '" + filePath + "' could not be found.");
        }

        StringBuilder stream = new StringBuilder();
        while (fileScanner.hasNextLine()) {
            stream.append(removeSpaces(fileScanner.nextLine().replaceAll("\n", "")));
        }
        fileScanner.close();

        this.rawStream = stream.toString();
    }

    protected static String removeSpaces(String s) {
        StringBuilder newString = new StringBuilder();
        boolean ignoreSpaces = true;
        for (char c : s.toCharArray()) {
            if (c == '"') {
                ignoreSpaces = !ignoreSpaces;
            }
            boolean ignore = ignoreSpaces && c == ' ';
            if (!ignore) {
                newString.append(c);
            }
        }
        return newString.toString();
    }

    protected static

    public String getRawStream() {
        return this.rawStream;
    }
}
