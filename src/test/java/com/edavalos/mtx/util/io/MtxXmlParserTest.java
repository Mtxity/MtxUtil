package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxXmlParserTest {

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
}
