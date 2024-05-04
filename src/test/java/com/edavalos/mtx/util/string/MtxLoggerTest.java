package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxLoggerTest {

    @Test
    public void testGetTimestamp() {
        String actualTs = MtxLogger.getTimestamp();
        String expectedTs = "[2024-05-03 03:04 AM]";
        assertEquals(actualTs, expectedTs);
    }
}
