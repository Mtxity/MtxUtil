package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxLoggerTest {

    @Test
    public void testGetTimestamp() {
        Pattern pattern = Pattern.compile("\\[[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} [AP]M]");
        String ts = MtxLogger.getTimestamp();
        assertTrue(ts.matches(pattern.pattern()));
    }

    @Test
    public void testFillInBrackets() {
        String template = "Test {} {} {} ok";
        String expected = "Test a b c ok";
        String actual = MtxLogger.fillInBrackets(template, "a", "b", "c");
        assertEquals(expected, actual);
    }
    @Test
    public void testFillInBrackets_incorrectAmountOfArgs() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> MtxLogger.fillInBrackets("{} {}", "1", "2", "3")
        );
        assertEquals("Number of arguments do not match log template! Expected 2 but found 3", e.getMessage());
    }
}
