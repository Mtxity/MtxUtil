package com.edavalos.mtx.util.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxCsvParserTest {
    private static final String CURRENT_FILE_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\edavalos\\mtx\\util\\io\\";

    private MtxCsvParser mtxCsvParser;

    @Test
    public void testConstructor_fileNotFound() {
        String fakeFilePath = "something/else.csv";
        String expectedErrorMsg = "The file '" + fakeFilePath + "' could not be found.";

        assertThrows(IOException.class, () -> mtxCsvParser = new MtxCsvParser(fakeFilePath));

        try {
            mtxCsvParser = new MtxCsvParser(fakeFilePath);
            fail();
        } catch (IOException e) {
            assertEquals(expectedErrorMsg, e.getMessage());
        }
    }

    @Test
    public void testConstructor_fileNotCsv() {
        String txtFilePath = "sampleTxtFile.txt";
        String expectedErrorMsg = "The file '" + CURRENT_FILE_PATH + txtFilePath + "' is not a csv file.";

        assertThrows(IOException.class, () -> mtxCsvParser = new MtxCsvParser(CURRENT_FILE_PATH + txtFilePath));

        try {
            mtxCsvParser = new MtxCsvParser(CURRENT_FILE_PATH + txtFilePath);
            fail();
        } catch (IOException e) {
            assertEquals(expectedErrorMsg, e.getMessage());
        }
    }

    @Test
    public void testGetCsvContents() {
        String[][] expectedContents = new String[][] {
                new String[] {"a1", "a2", "a3", "a4", "a5"},
                new String[] {"b1", "b2", "b3", "b4", "b5"},
                new String[] {"c1", "c2", "c3", "c4", "c5"},
                new String[] {"d1", "d2", "d3", "d4", "d5"},
                new String[] {"e1", "e2", "e3", "e4", "e5"},
        };

        try {
            mtxCsvParser = new MtxCsvParser(CURRENT_FILE_PATH + "sampleCsvFile.csv");
        } catch (IOException e) {
            fail();
        }

        assertArrayEquals(expectedContents, mtxCsvParser.getCsvContents());
    }

    @Test
    public void testToString() {
        String expectedStringRep = "+---+---+---+---+---+\n" +
                                   "| a1| a2| a3| a4| a5|\n" +
                                   "+---+---+---+---+---+\n" +
                                   "| b1| b2| b3| b4| b5|\n" +
                                   "+---+---+---+---+---+\n" +
                                   "| c1| c2| c3| c4| c5|\n" +
                                   "+---+---+---+---+---+\n" +
                                   "| d1| d2| d3| d4| d5|\n" +
                                   "+---+---+---+---+---+\n" +
                                   "| e1| e2| e3| e4| e5|\n" +
                                   "+---+---+---+---+---+";

        try {
            mtxCsvParser = new MtxCsvParser(CURRENT_FILE_PATH + "sampleCsvFile.csv");
        } catch (IOException e) {
            fail();
        }

        assertEquals(expectedStringRep, mtxCsvParser.toString());
    }
}
