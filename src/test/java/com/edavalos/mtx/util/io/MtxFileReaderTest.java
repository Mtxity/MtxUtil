package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxFileReaderTest {
    private static final String CURRENT_FILE_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\edavalos\\mtx\\util\\io\\";

    private MtxFileReader mtxFileReader;

    @Test
    public void testConstructor_fileNotFound() {
        String fakeFilePath = "something/else.txt";
        String expectedErrorMsg = "The file '" + fakeFilePath + "' could not be found.";

        assertThrows(IOException.class, () -> mtxFileReader = new MtxFileReader(fakeFilePath));

        try {
            mtxFileReader = new MtxFileReader(fakeFilePath);
            fail();
        } catch (IOException e) {
            assertEquals(expectedErrorMsg, e.getMessage());
        }
    }

    @Test
    public void testGetContents() {
        String[] expectedString = new String[] {"Sample string"};

        try {
            mtxFileReader = new MtxFileReader(CURRENT_FILE_PATH + "sampleTxtFile.txt");
        } catch (IOException e) {
            fail();
        }

        assertArrayEquals(expectedString, mtxFileReader.getContents());
    }
}
