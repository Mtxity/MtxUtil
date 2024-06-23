package com.edavalos.mtx.util.generator.data;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class MtxPopulatedCsvTest {
    private MtxPopulatedCsv mtxPopulatedCsv;

    @Nested
    class ConstructorTests {

        @Test
        public void testConstructor_sizeArg() {
            int sampleSize = 5;
            mtxPopulatedCsv = new MtxPopulatedCsv(sampleSize);

            assertEquals(sampleSize, mtxPopulatedCsv.contents.length);
            assertEquals(sampleSize, mtxPopulatedCsv.contents[0].length);
        }

        @Test
        public void testConstructor_contentsArg() {
            String[][] sampleContents = {{"x", "y", "z"}, {"l", "m", "n"}};
            mtxPopulatedCsv = new MtxPopulatedCsv(sampleContents);

            assertEquals(sampleContents, mtxPopulatedCsv.contents);
        }

        @Test
        public void testConstructor_default() {
            mtxPopulatedCsv = new MtxPopulatedCsv();

            assertEquals("blueberry", mtxPopulatedCsv.contents[0][0]);
            assertEquals("blackberry", mtxPopulatedCsv.contents[1][1]);
            assertEquals("pickle", mtxPopulatedCsv.contents[2][2]);
        }
    }

    @Test
    public void testGetAt() {
        mtxPopulatedCsv = new MtxPopulatedCsv();

        assertEquals("strawberry", mtxPopulatedCsv.getAt(0, 1));
        assertEquals("kumquat", mtxPopulatedCsv.getAt(1, 2));
        assertEquals("dill", mtxPopulatedCsv.getAt(2, 3));
    }

    @Test
    public void testGetCopy() {
        String[][] sampleContents = {{"a", "b"}, {"c", "d"}, {"e", "f"}};
        mtxPopulatedCsv = new MtxPopulatedCsv(sampleContents);
        assertArrayEquals(sampleContents, mtxPopulatedCsv.getCopy());

        mtxPopulatedCsv.contents[0][1] = "g";
        assertArrayEquals(sampleContents, mtxPopulatedCsv.getCopy());
    }

    @Test
    public void testGetOriginal_editable() {
        String[][] sampleContents = {{"a", "b"}, {"c", "d"}, {"e", "f"}};
        mtxPopulatedCsv = new MtxPopulatedCsv(sampleContents);
        try {
            assertArrayEquals(sampleContents, mtxPopulatedCsv.getOriginal());
        } catch (IllegalAccessException e) {
            fail();
        }

        mtxPopulatedCsv.contents[0][1] = "g";
        sampleContents[0][1] = "g";
        try {
            assertArrayEquals(sampleContents, mtxPopulatedCsv.getOriginal());
        } catch (IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testGetOriginal_nonEditable() {
        String[][] sampleContents = {{"a", "b"}, {"c", "d"}, {"e", "f"}};
        mtxPopulatedCsv = new MtxPopulatedCsv(sampleContents);
        mtxPopulatedCsv.editable = false;

        String expectedErrMsg = "This MtxPopulatedCsv cannot be modified! No direct access allowed!";
        String actualErrMsg = assertThrows(
                IllegalAccessException.class,
                () -> mtxPopulatedCsv.getOriginal()
        ).getMessage();
        assertEquals(expectedErrMsg, actualErrMsg);
    }
}
