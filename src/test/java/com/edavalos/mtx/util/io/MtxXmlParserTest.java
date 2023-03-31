package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxXmlParserTest {

    @Test
    public void testParseTag() {
        try {
            HashMap<String, String> kv = MtxXmlParser.parseOpeningTag("<test k1=\"v1\" />");
            assertEquals(2, kv.size());
            assertEquals("test", kv.get(".NAME"));
            assertEquals("v1", kv.get("k1"));
        } catch (ParseException e) {
            System.out.println(e.getErrorOffset());
            throw new RuntimeException(e);
        }
    }
}
