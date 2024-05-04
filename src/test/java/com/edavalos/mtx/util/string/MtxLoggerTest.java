package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxLoggerTest {

    @Test
    public void testGetTimestamp() {
        Pattern pattern = Pattern.compile("\\[[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} [AP]M]");
        String ts = MtxLogger.getTimestamp();
        assertTrue(ts.matches(pattern.pattern()));
    }
}
