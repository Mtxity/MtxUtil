package com.edavalos.mtx.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    protected record MtxJsonToken(MtxJsonTokenType tokenType, String value) { }

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

    protected static List<MtxJsonToken> tokenizeRawJson(String jsonStream) throws ParseException {
        ArrayList<MtxJsonToken> tokens = new ArrayList<>();

        boolean openQuotes = false;
        StringBuilder currentString = new StringBuilder();
        for (char c : jsonStream.toCharArray()) {
            // if not currently building string, add chars as tokens
            if (!openQuotes) {
                switch (c) {
                    case '{' -> tokens.add(new MtxJsonToken(MtxJsonTokenType.OPENING_BRACKET, "{"));
                    case '}' -> tokens.add(new MtxJsonToken(MtxJsonTokenType.CLOSING_BRACKET, "}"));
                    case '[' -> tokens.add(new MtxJsonToken(MtxJsonTokenType.OPENING_BRACE, "["));
                    case ']' -> tokens.add(new MtxJsonToken(MtxJsonTokenType.CLOSING_BRACE, "]"));
                    case ':' -> tokens.add(new MtxJsonToken(MtxJsonTokenType.COLON, ":"));
                    case ',' -> tokens.add(new MtxJsonToken(MtxJsonTokenType.COMMA, ","));
                    case '"' -> openQuotes = true;
                }
            // if currently building string,
            } else {
                // if char is quotes, close string and add as token
                if (c == '"') {
                    tokens.add(new MtxJsonToken(MtxJsonTokenType.STRING, currentString.toString()));
                    currentString = new StringBuilder();
                    openQuotes = false;
                // if char is not quotes, add char to string being built
                } else {
                    currentString.append(c);
                }
            }
        }
        // if stream ended before string being built was closed, throw error
        if (!currentString.isEmpty()) {
            throw new ParseException(jsonStream, jsonStream.length() - 1);
        }

        return tokens;
    }

    protected static boolean isMtxJsonTokenListValid(List<MtxJsonToken> tokenList) {
        if (tokenList.get(0).tokenType != MtxJsonTokenType.OPENING_BRACKET) {
            return false;
        }

        int idx = -1;
        int depth = 0;
        boolean valid = false;
        for (MtxJsonToken token : tokenList) {
            idx ++;
            if (idx + 1 == tokenList.size()) {
                valid = token.tokenType == MtxJsonTokenType.CLOSING_BRACKET && depth == 1;
                break;
            }

            if (token.tokenType != null && tokenList.get(idx + 1).tokenType != null) {
                MtxJsonTokenType nextToken = tokenList.get(idx + 1).tokenType;
                switch (token.tokenType) {
                    case OPENING_BRACKET -> {
                        switch (nextToken) {
                            // Valid tokens after opening bracket: {
                            case STRING -> {
                                depth ++;
                                continue;
                            }
                            default -> {
                                return false;
                            }
                        }
                    }
                    case CLOSING_BRACKET -> {
                        switch (nextToken) {
                            // Valid tokens after closing bracket: }
                            case COMMA, CLOSING_BRACKET, CLOSING_BRACE -> {
                                depth --;
                                continue;
                            }
                            default -> {
                                return false;
                            }
                        }
                    }
                    case OPENING_BRACE -> {
                        switch (nextToken) {
                            // Valid tokens after opening brace: [
                            case STRING, OPENING_BRACKET -> {
                                continue;
                            }
                            default -> {
                                return false;
                            }
                        }
                    }
                    case CLOSING_BRACE -> {
                        switch (nextToken) {
                            // Valid tokens after closing brace: ]
                            case COMMA, CLOSING_BRACKET -> {
                                continue;
                            }
                            default -> {
                                return false;
                            }
                        }
                    }
                    case STRING -> {
                        switch (nextToken) {
                            // Valid tokens after string: "
                            case COLON, COMMA, CLOSING_BRACKET, CLOSING_BRACE -> {
                                continue;
                            }
                            default -> {
                                return false;
                            }
                        }
                    }
                    case COLON -> {
                        switch (nextToken) {
                            // Valid tokens after colon: :
                            case STRING, OPENING_BRACKET, OPENING_BRACE -> {
                                continue;
                            }
                            default -> {
                                return false;
                            }
                        }
                    }
                    case COMMA -> {
                        switch (nextToken) {
                            // Valid tokens after comma: ,
                            case STRING, OPENING_BRACKET -> {
                                continue;
                            }
                            default -> {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }

    public String getRawStream() {
        return this.rawStream;
    }
}
