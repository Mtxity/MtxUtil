package com.edavalos.mtx.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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
//
//    protected class MtxJsonContent {
//        private final String name;
//
//        public MtxJsonContent(String name) {
//            this.name = name;
//        }
//
//        public String getName() {
//            return this.name;
//        }
//    }
//
//    protected class MtxJsonStringContent extends MtxJsonContent {
//        private final String value;
//
//        public MtxJsonStringContent(String name, String value) {
//            super(name);
//            this.value = value;
//        }
//
//        public String getValue() {
//            return this.value;
//        }
//    }
//
//    protected class MtxJsonObjectContent extends MtxJsonContent {
//        private final
//    }
    protected record MtxJsonObject(String tag, Object contents) { }

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

    protected static List<MtxJsonToken> stripExternalBrackets(List<MtxJsonToken> tokenList) {
        assert tokenList.get(0).tokenType == MtxJsonTokenType.OPENING_BRACKET;
        assert tokenList.get(tokenList.size() - 1).tokenType == MtxJsonTokenType.CLOSING_BRACKET;
        return tokenList.subList(1, tokenList.size() - 1);
    }

//    protected static List<List<MtxJsonToken>> nestLists(List<MtxJsonToken> tokenList) {
//
//    }
//    protected static void doWork(List<MtxJsonToken> tokenList) {
//            doWork(tokenList, false);
//    }
//
//    protected static void doWork(List<MtxJsonToken> tokenList, boolean inList) {
//        boolean writingTag = true;
//        String currentKey;
//        Object currentVal;
//        LinkedList<Object> currentList = null;
//        for (MtxJsonToken token : tokenList) {
//            if (writingTag) {
//                assert token.tokenType == MtxJsonTokenType.STRING;
//                currentKey = token.value;
//                writingTag = false;
//                continue;
//            }
//
//            switch (token.tokenType) {
//                case COLON -> {
//                    assert !writingTag && !inList;
//                    continue;
//                }
//                case STRING -> {
//                    assert !writingTag;
//                    if (!inList) {
//                        currentVal = token.value;
//                        writingTag = true;
//                    } else {
//                        assert currentList != null;
//                        currentList.add(token.value);
//                    }
//                }
//                case OPENING_BRACE -> {
//                    assert !writingTag;
//                    if (!inList) {
//                        inList = true;
//                        currentList = new LinkedList<Object>();
//                    } else {
//                        // @todo recursively insert list contents into currentList
//                        // get closing bracket, form a token list from here to there, and call this method again
//                    }
//                }
//                case OPENING_BRACKET -> {
//                    assert !writingTag;
//
//                }
//            }
//        }
//    }

//    protected static MtxJsonObject doWork(List<MtxJsonToken> tokenList) {
//        for (int i = 0; i < tokenList.size(); i++) {
//
//        }
//    }

    protected static LinkedList<Object> processList(List<MtxJsonToken> tokenList) {
        LinkedList<Object> innerList = new LinkedList<>();
        for (int i = 0; i < tokenList.size(); i++) {
            MtxJsonToken token = tokenList.get(i);
            switch (token.tokenType) {
                case STRING -> innerList.add(token.value);
                case OPENING_BRACE -> {
                    List<MtxJsonToken> unprocessedInnerInnerList = getInnerList(tokenList, i);
                    i += unprocessedInnerInnerList.size();
                    innerList.add(processList(unprocessedInnerInnerList));
                }
            }
        }
        return innerList;
    }

    protected static List<MtxJsonToken> getInnerList(List<MtxJsonToken> tokenList, int openingBracePosition) {
        return getInner(tokenList, openingBracePosition, true);
    }

    protected static List<MtxJsonToken> getInnerObject(List<MtxJsonToken> tokenList, int openingBracketPosition) {
        return getInner(tokenList, openingBracketPosition, false);
    }

    private static List<MtxJsonToken> getInner(List<MtxJsonToken> tokenList, int openingPosition, boolean isBrace) {
        if (isBrace) {
            assert tokenList.get(openingPosition).tokenType == MtxJsonTokenType.OPENING_BRACE;
        }
        else {
            assert tokenList.get(openingPosition).tokenType == MtxJsonTokenType.OPENING_BRACKET;
        }

        List<MtxJsonToken> subList = new ArrayList<>();
        int openersFound = 0;
        for (MtxJsonToken token : tokenList.subList(openingPosition + 1, tokenList.size())) {
            switch (token.tokenType) {
                case OPENING_BRACE -> {
                    if (isBrace) {
                        openersFound ++;
                    }
                    subList.add(token);
                }
                case OPENING_BRACKET -> {
                    if (!isBrace) {
                        openersFound ++;
                    }
                    subList.add(token);
                }
                case CLOSING_BRACE -> {
                    if (isBrace) {
                        if (openersFound == 0) {
                            return subList;
                        } else {
                            openersFound --;
                            subList.add(token);
                        }
                    } else {
                        subList.add(token);
                    }
                }
                case CLOSING_BRACKET -> {
                    if (!isBrace) {
                        if (openersFound == 0) {
                            return subList;
                        } else {
                            openersFound --;
                            subList.add(token);
                        }
                    } else {
                        subList.add(token);
                    }
                }
                default -> subList.add(token);
            }
        }
        return null;
    }

    public String getRawStream() {
        return this.rawStream;
    }
}
