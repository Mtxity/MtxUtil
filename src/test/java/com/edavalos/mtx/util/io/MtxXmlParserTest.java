package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxXmlParserTest {

    @Test
    public void testParseOpeningTag() {
        try {
            HashMap<String, String> kv = MtxXmlParser.parseOpeningTag("<test k1=\"v1\" />");
            assertArrayEquals(kv.keySet().toArray(), MtxXmlParser.parseOpeningTag("<test k1=\"v1\" >").keySet().toArray());
            assertEquals(2, kv.size());
            assertEquals("test", kv.get(".NAME"));
            assertEquals("v1", kv.get("k1"));
        } catch (ParseException e) {
            System.out.println(e.getErrorOffset());
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testParseClosingTag() {
        assertTrue(MtxXmlParser.parseClosingTag("</test>"));
        assertTrue(MtxXmlParser.parseClosingTag("</ test>"));

        assertFalse(MtxXmlParser.parseClosingTag("</>"));
        assertFalse(MtxXmlParser.parseClosingTag("</te st>"));
        assertFalse(MtxXmlParser.parseClosingTag("</\"test\">"));
    }
}
