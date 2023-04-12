package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxJsonParserTest {

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
}
