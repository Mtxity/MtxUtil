package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxXmlParserTest {
    private static final String CURRENT_FILE_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\edavalos\\mtx\\util\\io\\";
    private static final String SAMPLE_XML_FILENAME = "sampleXmlFile.xml";

    private MtxXmlParser mtxXmlParser;

    @Test
    public void testParseOpeningTag() {
        try {
            HashMap<String, String> kv = MtxXmlParser.parseOpeningTag("<test k1=\"v1\" />");
            assertArrayEquals(kv.keySet().toArray(), MtxXmlParser.parseOpeningTag("<test k1=\"v1\" >").keySet().toArray());
            assertEquals(2, kv.size());
            assertEquals("test", kv.get(MtxXmlParser.TAG_NAME_MARKER));
            assertEquals("v1", kv.get("k1"));
        } catch (ParseException e) {
            System.out.println(e.getErrorOffset());
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testParseOpeningTag_hasError() {
        // Stray periods
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<.test k1=\"v1\" >"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test .k1=\"v1\" >"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test k1=.\"v1\" >"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test k1=\"v1\" .>"));

        // No closing bracket
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test k1=\"v1\""));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test k1=\"v1\" /"));

        // No opening bracket
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("test k1=\"v1\">"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("f<test k1=\"v1\">"));

        // Stray brackets
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<t<est k1=\"v1\">"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test <k1=\"v1\">"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test> k1=\"v1\">"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test k1=\"v1\">>"));

        // Stray forward slash
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test /k1=\"v1\">"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<test /k1=\"v1\"/>"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<te\"st k1=\"v1\"/>"));

        // Blank tag
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("< >"));

        // Blank name
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("< k1=\"v1\">"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseOpeningTag("<  k1=\"v1\">"));
    }

    @Test
    public void testParseClosingTag() {
        assertTrue(MtxXmlParser.parseClosingTag("</test>"));
        assertTrue(MtxXmlParser.parseClosingTag("</ test>"));

        assertFalse(MtxXmlParser.parseClosingTag("</>"));
        assertFalse(MtxXmlParser.parseClosingTag("</te st>"));
        assertFalse(MtxXmlParser.parseClosingTag("</\"test\">"));
    }

    @Test
    public void testParseCommentTag() {
        assertTrue(MtxXmlParser.parseCommentTag("<!-- test -->"));
        assertTrue(MtxXmlParser.parseCommentTag("<!--test-->"));
        assertTrue(MtxXmlParser.parseCommentTag("<!----test-->"));
        assertTrue(MtxXmlParser.parseCommentTag("<!---->"));

        assertFalse(MtxXmlParser.parseCommentTag("<!-- test ->"));
        assertFalse(MtxXmlParser.parseCommentTag("<!- test -->"));
        assertFalse(MtxXmlParser.parseCommentTag("<!-- test --"));
        assertFalse(MtxXmlParser.parseCommentTag("<-- test -->"));
    }

    @Test
    public void testParseTag() {
        try {
            String openingTagStr = "<testO k1=\"v1\" k2=\"v2\">";
            String inlineTagStr  = "<testI k1=\"v1\" />";
            String closingTagStr = "</testC>";
            String commentTagStr = "<!-- testCm -->";

            MtxXmlParser.MtxXmlTag openingTag = MtxXmlParser.parseTag(openingTagStr);
            MtxXmlParser.MtxXmlTag inlineTag  = MtxXmlParser.parseTag(inlineTagStr);
            MtxXmlParser.MtxXmlTag closingTag = MtxXmlParser.parseTag(closingTagStr);
            MtxXmlParser.MtxXmlTag commentTag = MtxXmlParser.parseTag(commentTagStr);

            assertEquals("testO", openingTag.name());
            assertEquals("testI", inlineTag.name());
            assertEquals("testC", closingTag.name());
            assertNull(commentTag.name());

            assertEquals(3, openingTag.fields().size());
            assertEquals(2, inlineTag.fields().size());
            assertEquals(1, closingTag.fields().size());
            assertEquals(1, commentTag.fields().size());

            assertEquals(MtxXmlParser.MtxXmlTagType.OPENING, openingTag.tagType());
            assertEquals(MtxXmlParser.MtxXmlTagType.INLINE,  inlineTag.tagType());
            assertEquals(MtxXmlParser.MtxXmlTagType.CLOSING, closingTag.tagType());
            assertEquals(MtxXmlParser.MtxXmlTagType.COMMENT, commentTag.tagType());
        } catch (ParseException e) {
            System.out.println(e.getErrorOffset());
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testParseTag_hasError() {
        // No closing bracket
        assertThrows(ParseException.class, () -> MtxXmlParser.parseTag("<test k1=\"v1\" "));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseTag("<test k1=\"v1\""));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseTag("</test k1=\"v1\""));

        // Incorrect closing bracket
        assertThrows(ParseException.class, () -> MtxXmlParser.parseTag("<!-- test ->"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseTag("<!-- test >"));
        assertThrows(ParseException.class, () -> MtxXmlParser.parseTag("<!-- test"));
    }

    @Nested
    class SeparatePartsTests {

        @Test
        public void testSeparateParts() {
            String sampleXml = "<n1><n2> <n3 t=\"2\"></n3><o1></o1>  </n2></n1>";
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1>",
                        "<n2>",
                            "<n3 t=\"2\">",
                            "</n3>",
                            "<o1>",
                            "</o1>",
                        "</n2>",
                    "</n1>",
            };

            assertArrayEquals(sampleXmlDeconstructed, MtxXmlParser.separateParts(sampleXml));
        }

        @Test
        public void testSeparateParts_withTextInBetween() {
            String sampleXml = "<n1 k=\"a\"><n2><n3>inner spot a</n3><o1>inner spot b</o1></n2></n1>";
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1 k=\"a\">",
                        "<n2>",
                            "<n3>inner spot a</n3>",
                            "<o1>inner spot b</o1>",
                        "</n2>",
                    "</n1>",
            };

            assertArrayEquals(sampleXmlDeconstructed, MtxXmlParser.separateParts(sampleXml));
        }

        @Test
        public void testSeparateParts_withSpacesInBetween() {
            String sampleXml =
                    """
                    <n1>
                        <n2>
                            <n3 t="2">
                            </n3>
                            <o1></o1>
                        </n2>
                    </n1>
                    """;
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1>",
                        "<n2>",
                            "<n3 t=\"2\">",
                            "</n3>",
                            "<o1>",
                            "</o1>",
                        "</n2>",
                    "</n1>",
            };

            assertArrayEquals(sampleXmlDeconstructed, MtxXmlParser.separateParts(sampleXml));
        }

        @Test
        public void testSeparateParts_withTextInBetween_withCommentsInBetween_center() {
            String sampleXml = "<n1 k=\"a\"><n2><n3>inner <!-- test --> spot a</n3><o1>inner spot b</o1></n2></n1>";
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1 k=\"a\">",
                        "<n2>",
                            "<n3>inner <!-- test --> spot a</n3>",
                            "<o1>inner spot b</o1>",
                        "</n2>",
                    "</n1>",
            };

            assertArrayEquals(sampleXmlDeconstructed, MtxXmlParser.separateParts(sampleXml));
        }

        @Test
        public void testSeparateParts_withTextInBetween_withCommentsInBetween_right() {
            String sampleXml = "<n1 k=\"a\"><n2><n3>inner spot a <!-- test --></n3><o1>inner spot b</o1></n2></n1>";
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1 k=\"a\">",
                        "<n2>",
                            "<n3>inner spot a <!-- test --></n3>",
                            "<o1>inner spot b</o1>",
                        "</n2>",
                    "</n1>",
            };

            assertArrayEquals(sampleXmlDeconstructed, MtxXmlParser.separateParts(sampleXml));
        }

        @Test
        public void testSeparateParts_withTextInBetween_withCommentsInBetween_left() {
            String sampleXml = "<n1 k=\"a\"><n2><n3><!-- test --> inner spot a</n3><o1>inner spot b</o1></n2></n1>";
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1 k=\"a\">",
                        "<n2>",
                            "<n3><!-- test --> inner spot a</n3>",
                            "<o1>inner spot b</o1>",
                        "</n2>",
                    "</n1>",
            };

            assertArrayEquals(sampleXmlDeconstructed, MtxXmlParser.separateParts(sampleXml));
        }
    }

    @Nested
    class IsolateTextTests {

        @Test
        public void testIsolateText_noTextInBetween() {
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1 k=\"a\">",
                    "<n2>",
                    "<n3>",
                    "</n3>",
                    "<o1>",
                    "</o1>",
                    "</n2>",
                    "</n1>",
            };
            assertArrayEquals(sampleXmlDeconstructed, MtxXmlParser.isolateText(sampleXmlDeconstructed));
        }

        @Test
        public void testIsolateText_withTextInBetween() {
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1 k=\"a\">",
                    "<n2>",
                    "<n3>inner spot a</n3>",
                    "<o1>inner spot b</o1>",
                    "</n2>",
                    "</n1>",
            };
            String[] sampleXmlDeconstructedWithSplitStrings = new String[]{
                    "<n1 k=\"a\">",
                    "<n2>",
                    "<n3>",
                    "inner spot a",
                    "</n3>",
                    "<o1>",
                    "inner spot b",
                    "</o1>",
                    "</n2>",
                    "</n1>",
            };
            assertArrayEquals(sampleXmlDeconstructedWithSplitStrings, MtxXmlParser.isolateText(sampleXmlDeconstructed));
        }

        @Test
        public void testIsolateText_withTextInBetween_withCommentsInBetween() {
            String[] sampleXmlDeconstructed = new String[]{
                    "<n1 k=\"a\">",
                    "<n2>",
                    "<n3>inne<!-- test -->r spot a</n3>",
                    "<o1>inner <!-- test --> spot b</o1>",
                    "</n2>",
                    "</n1>",
            };
            String[] sampleXmlDeconstructedWithSplitStrings = new String[]{
                    "<n1 k=\"a\">",
                    "<n2>",
                    "<n3>",
                    "inne<!-- test -->r spot a",
                    "</n3>",
                    "<o1>",
                    "inner <!-- test --> spot b",
                    "</o1>",
                    "</n2>",
                    "</n1>",
            };
            assertArrayEquals(sampleXmlDeconstructedWithSplitStrings, MtxXmlParser.isolateText(sampleXmlDeconstructed));
        }
    }

    @Test
    public void testConstructor_fileNotFound() {
        String fakeFilePath = "something/else.txt";
        String expectedErrorMsg = "The file '" + fakeFilePath + "' could not be found.";

        assertThrows(IOException.class, () -> mtxXmlParser = new MtxXmlParser(fakeFilePath));

        try {
            mtxXmlParser = new MtxXmlParser(fakeFilePath);
            fail();
        } catch (IOException e) {
            assertEquals(expectedErrorMsg, e.getMessage());
        }
    }

    @Test
    public void testToString() {
        StringBuilder expectedText = new StringBuilder();
        try {
            Scanner fileScanner = new Scanner(new File(CURRENT_FILE_PATH + SAMPLE_XML_FILENAME));
            while (fileScanner.hasNextLine()) {
                expectedText.append(fileScanner.nextLine());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            mtxXmlParser = new MtxXmlParser(CURRENT_FILE_PATH + SAMPLE_XML_FILENAME);
        } catch (IOException e) {
            fail();
        }

        assertEquals(expectedText.toString(), mtxXmlParser.toString());
    }
}
