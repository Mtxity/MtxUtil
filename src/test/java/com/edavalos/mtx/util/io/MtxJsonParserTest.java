package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
                    }});
                    add(new LinkedHashMap<String, Object>() {{
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

    @Nested
    class IsMtxJsonTokenListValidTests {
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_AFTER_OPENING_BRACKET = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_AFTER_CLOSING_BRACKET = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_AFTER_OPENING_BRACE = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_AFTER_CLOSING_BRACE = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_AFTER_STRING = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_AFTER_COLON = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_AFTER_COMMA = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_LENGTH = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_CLOSING = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_INVALID_NULL = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(null, null));
            add(new MtxJsonParser.MtxJsonToken(null, null));
        }};

        @Test
        public void testIsMtxJsonTokenListValid_validSampleJsonTokenList() {
            assertTrue(MtxJsonParser.isMtxJsonTokenListValid(SAMPLE_JSON_TOKENS));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidAfterOpeningBracket() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_AFTER_OPENING_BRACKET));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidAfterClosingBracket() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_AFTER_CLOSING_BRACKET));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidAfterOpeningBrace() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_AFTER_OPENING_BRACE));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidAfterClosingBrace() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_AFTER_CLOSING_BRACE));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidAfterString() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_AFTER_STRING));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidAfterColon() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_AFTER_COLON));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidAfterComma() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_AFTER_COMMA));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidLength() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_LENGTH));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_invalidClosing() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_CLOSING));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_null() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(TOKENS_INVALID_NULL));
        }

        @Test
        public void testIsMtxJsonTokenListValid_invalidSampleJsonTokenList_missingStartingBracket() {
            assertFalse(MtxJsonParser.isMtxJsonTokenListValid(SAMPLE_JSON_TOKENS.subList(1, SAMPLE_JSON_TOKENS.size())));
        }
    }

    @Nested
    class StripExternalBracketsTests {
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_SAMPLE = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_EXPECTED = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample"));
        }};

        @Test
        public void testStripExternalBrackets() {
            assertEquals(TOKENS_EXPECTED, MtxJsonParser.stripExternalBrackets(TOKENS_SAMPLE));
        }
    }

    @Nested
    class GetInnerTests {
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_NO_CLOSING_BRACE = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        }};
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_NO_CLOSING_BRACKET = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
        }};

        @Nested
        class GetInnerListTests {
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample1"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            }};
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE_INNER = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
            }};
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE_MULTIPLE = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample1"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            }};
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE_MULTIPLE_INNER = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
            }};

            @Test
            public void testInnerList_one() {
                assertEquals(TOKENS_OPEN_CLOSE_INNER, MtxJsonParser.getInnerList(TOKENS_OPEN_CLOSE, 1));
            }

            @Test
            public void testInnerList_multiple() {
                assertEquals(TOKENS_OPEN_CLOSE_MULTIPLE_INNER, MtxJsonParser.getInnerList(TOKENS_OPEN_CLOSE_MULTIPLE, 1));
            }
        }

        @Nested
        class GetInnerObjectTests {
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample1"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            }};
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE_INNER = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
            }};
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE_MULTIPLE = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample1"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            }};
            private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OPEN_CLOSE_MULTIPLE_INNER = new ArrayList<>() {{
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample2"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ":"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "sample3"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
                add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
            }};

            @Test
            public void testInnerList_one() {
                assertEquals(TOKENS_OPEN_CLOSE_INNER, MtxJsonParser.getInnerObject(TOKENS_OPEN_CLOSE, 1));
            }

            @Test
            public void testInnerList_multiple() {
                assertEquals(TOKENS_OPEN_CLOSE_MULTIPLE_INNER, MtxJsonParser.getInnerObject(TOKENS_OPEN_CLOSE_MULTIPLE, 1));
            }
        }

        @Test
        public void testInner_NoClosing() {
            assertNull(MtxJsonParser.getInnerObject(TOKENS_NO_CLOSING_BRACKET, 0));
            assertNull(MtxJsonParser.getInnerList(TOKENS_NO_CLOSING_BRACE, 0));
        }
    }

    @Nested
    class ProcessListTests {
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_LIST = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "a"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "b"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "c"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "d"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "e"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
        }};

        private static final LinkedList<Object> PARSED_JSON_LIST = new LinkedList<>() {{
            add("a");
            add("b");
            add(new LinkedList<Object>() {{
                add("c");
                add(new LinkedList<Object>() {{
                    add("d");
                }});
                add("e");
            }});
        }};

        @Test
        public void testProcessList_NoObjects() {
            assertEquals(PARSED_JSON_LIST, MtxJsonParser.processList(TOKENS_LIST));
        }
    }

    @Nested
    class ProcessObjectTests {
        private static final List<MtxJsonParser.MtxJsonToken> TOKENS_OBJECT = new ArrayList<>() {{
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "a"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "a"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "b"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "b"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "c"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "d"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACE, "["));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "d"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.OPENING_BRACKET, "{"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "1"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "1"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COMMA, ","));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "2"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "2"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACE, "]"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.CLOSING_BRACKET, "}"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "e"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.COLON, ":"));
            add(new MtxJsonParser.MtxJsonToken(MtxJsonParser.MtxJsonTokenType.STRING, "e"));
        }};

        private static final LinkedHashMap<String, Object> PARSED_JSON_OBJECT = new LinkedHashMap<>() {{
            put("a", "a");
            put("b", "b");
            put("c", new LinkedHashMap<String, Object>() {{
                put("d", new LinkedList<Object>() {{
                    add("d");
                    add(new LinkedHashMap<String, Object>() {{
                        put("1", "1");
                        put("2", "2");
                    }});
                }});
            }});
            put("e", "e");
        }};

        @Test
        public void testProcessObject() {
            assertEquals(PARSED_JSON_OBJECT, MtxJsonParser.processObject(TOKENS_OBJECT));
        }
    }

    @Test
    public void testParseTokens() {
        List<MtxJsonParser.MtxJsonToken> strippedTokenList = MtxJsonParser.stripExternalBrackets(SAMPLE_JSON_TOKENS);
        LinkedHashMap<String, Object> parsedJson = MtxJsonParser.processObject(strippedTokenList);

        assertEquals(SAMPLE_PARSED_JSON, parsedJson);
    }
}
