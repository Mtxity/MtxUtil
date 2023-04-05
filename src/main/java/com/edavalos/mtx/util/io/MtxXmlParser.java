package com.edavalos.mtx.util.io;

import com.edavalos.mtx.util.string.MtxStringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MtxXmlParser {
    protected enum MtxXmlTagType {
        OPENING,
        CLOSING,
        INLINE,
        COMMENT
    }

    protected record MtxXmlTag(String name, HashMap<String, String> fields, MtxXmlTagType tagType) { }

    protected static final String TAG_NAME_MARKER = ".NAME_FOR_INTERNAL_USE";

    private final String contents;

    public MtxXmlParser(String filePath) throws IOException {
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new IOException("The file '" + filePath + "' could not be found.");
        }

        StringBuilder contentsBuilder = new StringBuilder();
        while (fileScanner.hasNextLine()) {
            contentsBuilder.append(fileScanner.nextLine());
        }
        this.contents = contentsBuilder.toString();
    }

    /**
     * Separates input into array of tags. Splits text between these: "><" (ignoring whitespaces and newlines)
     * @return An array of tags, with possible valid strings in between them
     */
    protected static String[] separateParts(String stream) {
        String spliterator = ">" + MtxStringUtil.SEPARATOR_CHAR + "<";
        return stream
               .replaceAll("\n", "")
               .replaceAll(">[ ]*<", spliterator)
               .split(String.valueOf(MtxStringUtil.SEPARATOR_CHAR));
    }

    protected static MtxXmlTag parseTag(String tag) throws ParseException {
        HashMap<String, String> fields;
        MtxXmlTagType tagType;
        if (tag.startsWith("</")) {
            if (!parseClosingTag(tag)) {
                throw new ParseException(tag, 0);
            }
            tagType = MtxXmlTagType.CLOSING;
            fields = new HashMap<>();
            fields.put(TAG_NAME_MARKER, tag.replace("</", "").replace(">", ""));

        } else if (tag.startsWith("<!")) {
            if (!parseCommentTag(tag)) {
                throw new ParseException(tag, 0);
            }
            tagType = MtxXmlTagType.COMMENT;
            fields = new HashMap<>();
            fields.put(TAG_NAME_MARKER, null);

        } else {
            tagType = tag.contains("/>") ? MtxXmlTagType.INLINE : MtxXmlTagType.OPENING;
            fields = parseOpeningTag(tag);
        }
        return new MtxXmlTag(fields.get(TAG_NAME_MARKER), fields, tagType);
    }

    protected static HashMap<String, String> parseOpeningTag(String tag) throws ParseException {
        tag = tag.strip();
        if (tag.startsWith("< ")) {
            throw new ParseException(tag, 1);
        } else if (!tag.startsWith("<")) {
            throw new ParseException(tag, 0);
        } else if (!tag.endsWith(">")) {
            throw new ParseException(tag, tag.length());
        }

        tag = tag.replaceFirst(Pattern.quote("<"), "").replaceAll(">$", "");
        if (tag.contains("<")) {
            throw new ParseException(tag, tag.indexOf('<'));
        } else if (tag.contains(">")) {
            throw new ParseException(tag, tag.indexOf('>'));
        } else if (tag.endsWith("/") && MtxStringUtil.countOccurrencesOf(tag, "/") > 1) {
            throw new ParseException(tag, tag.indexOf('/'));
        } else if (!tag.endsWith("/") && MtxStringUtil.countOccurrencesOf(tag, "/") > 0) {
            throw new ParseException(tag, tag.indexOf('/'));
        }

        String[] tagComponents = tag.replaceAll("/$", "").split(Pattern.quote(" "));
        HashMap<String, String> tagContentsMap = new HashMap<>();
        int idx = 0;
        for (int i = 0; i < tagComponents.length; i++) {
            if (i == 0) {
                if (tagComponents[0].contains("\"")) {
                    throw new ParseException(tag, tag.indexOf('"'));
                } else {
                    tagContentsMap.put(TAG_NAME_MARKER, tagComponents[0]);
                }

            } else {
                String[] kv = tagComponents[i].split(Pattern.quote("="));
                if (kv.length != 2) {
                    throw new ParseException(tag, idx);
                } else {
                    if (!kv[1].startsWith("\"") ||
                        !kv[1].endsWith("\"") ||
                        MtxStringUtil.countOccurrencesOf(kv[1], "\"") > 2) {
                        throw new ParseException(tag, idx);
                    }
                }
                tagContentsMap.put(kv[0], kv[1].replaceAll("\"", ""));
            }

            idx += tagComponents[i].length() + 1;
        }

        for (String keyName : tagContentsMap.keySet()) {
            if (TAG_NAME_MARKER.equals(keyName)) {
                if (tagContentsMap.get(TAG_NAME_MARKER).startsWith(".")) {
                    throw new ParseException(tag, tag.indexOf('.'));
                } else {
                    continue;
                }
            }
            if (keyName.startsWith(".")) {
                throw new ParseException(tag, tag.indexOf('.'));
            }
        }

        return tagContentsMap;
    }

    protected static boolean parseClosingTag(String tag) {
        return Pattern.matches("</[ ]*[^<> \"]+>", tag);
    }

    protected static boolean parseCommentTag(String tag) {
        return Pattern.matches("<!--[^\"]*-->", tag);
    }
}
