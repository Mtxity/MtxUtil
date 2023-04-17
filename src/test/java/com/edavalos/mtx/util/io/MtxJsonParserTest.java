package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxJsonParserTest {
    private static final String CURRENT_FILE_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\edavalos\\mtx\\util\\io\\";
    private static final String SAMPLE_JSON_FILENAME = "sampleJsonFile.json";
    private static final String SAMPLE_JSON_STRING = "{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":" +
            "\"S\",\"GlossList\":[{\"GlossEntry1\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":" +
            "\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":" +
            "{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":" +
            "[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}},{\"GlossEntry2\":{\"ID\":\"SQLM\",\"SortAs\":\"SQLM\",\"GlossTerm\":" +
            "\"Standard Query Language Markup\",\"Acronym\":\"SQLM\",\"Abbrev\":\"ISO 8109:4482\",\"GlossDef\":{\"para\":" +
            "\"A query language used for databases such as MySql and PostgreSQL\",\"GlossSeeAlso\":[\"SQL\",\"noSQL\"]}," +
            "\"GlossSee\":\"query\"}}]}}}";
    private static final List<MtxJsonParser.MtxJsonToken> SAMPLE_JSON_TOKENS = new ArrayList<>() {{
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "glossary"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "title"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "example glossary"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossDiv"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "title"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "S"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossList"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossEntry1"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "ID"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SGML"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SortAs"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SGML"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossTerm"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "Standard Generalized Markup Language"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "Acronym"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SGML"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "Abbrev"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "ISO 8879:1986"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossDef"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "para"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "A meta-markup language, used to create markup languages such as DocBook."));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossSeeAlso"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GML"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "XML"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossSee"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "markup"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossEntry2"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "ID"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SQLM"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SortAs"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SQLM"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossTerm"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "Standard Query Language Markup"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "Acronym"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SQLM"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "Abbrev"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "ISO 8109:4482"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossDef"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "para"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "A query language used for databases such as MySql and PostgreSQL"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossSeeAlso"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "SQL"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "noSQL"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "GlossSee"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "query"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
    }};
    private static final LinkedHashMap<String, Object> SAMPLE_PARSED_JSON = new LinkedHashMap<>() {{
        put("glossary", new LinkedHashMap<String, Object>() {{
            put("title", "example glossary");
            put("GlossDiv", new LinkedHashMap<String, Object>() {{
                put("title", "S");
                put("GlossList", new LinkedList<Object>() {{
                    add(new LinkedHashMap<String, Object>() {{
                        put("GlossEntry1", new LinkedHashMap<String, Object>() {{
                            put("ID", "SGML");
                            put("SortAs", "SGML");
                            put("GlossTerm", "Standard Generalized Markup Language");
                            put("Acronym", "SGML");
                            put("Abbrev", "ISO 8879:1986");
                            put("GlossDef", new LinkedHashMap<String, Object>() {{
                                put("para", "A meta-markup language, used to create markup languages such as DocBook.");
                                put("GlossSeeAlso", new LinkedList<Object>() {{
                                    add("GML");
                                    add("XML");
                                }});
                            }});
                            put("GlossSee", "markup");
                        }});
                        put("GlossEntry2", new LinkedHashMap<String, Object>() {{
                            put("ID", "SQLM");
                            put("SortAs", "SQLM");
                            put("GlossTerm", "Standard Query Language Markup");
                            put("Acronym", "SQLM");
                            put("Abbrev", "ISO 8109:4482");
                            put("GlossDef", new LinkedHashMap<String, Object>() {{
                                put("para", "A query language used for databases such as MySql and PostgreSQL");
                                put("GlossSeeAlso", new LinkedList<Object>() {{
                                    add("SQL");
                                    add("noSQL");
                                }});
                            }});
                            put("GlossSee", "query");
                        }});
                    }});
                }});
            }});
        }});
    }};

    private MtxJsonParser mtxJsonParser;

    @Test
    public void testConstructor_fileNotFound() {
        String fakeFilePath = "something/else.txt";
        String expectedErrorMsg = "The file '" + fakeFilePath + "' could not be found.";

        assertThrows(IOException.class, () -> mtxJsonParser = new MtxJsonParser(fakeFilePath));

        try {
            mtxJsonParser = new MtxJsonParser(fakeFilePath);
            fail();
        } catch (IOException e) {
            assertEquals(expectedErrorMsg, e.getMessage());
        }
    }

    @Test
    public void testGetRawStream() {
        try {
            mtxJsonParser = new MtxJsonParser(CURRENT_FILE_PATH + SAMPLE_JSON_FILENAME);
        } catch (IOException e) {
            fail();
        }
        assertEquals(SAMPLE_JSON_STRING, mtxJsonParser.getRawStream());
    }

    @Test
    public void testTokenizeRawJson() {
        try {
            assertArrayEquals(SAMPLE_JSON_TOKENS.toArray(), MtxJsonParser.tokenizeRawJson(SAMPLE_JSON_STRING).toArray());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testTokenizeRawJson_noClosingQuotes() {
        assertThrows(ParseException.class, () -> MtxJsonParser.tokenizeRawJson("\"test"));
    }
}
