package com.edavalos.mtx.util.generator.data;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
