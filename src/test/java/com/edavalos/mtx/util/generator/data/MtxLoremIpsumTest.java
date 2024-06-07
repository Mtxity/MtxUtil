package com.edavalos.mtx.util.generator.data;

import com.edavalos.mtx.util.string.MtxStringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class MtxLoremIpsumTest {
    private MtxLoremIpsum mtxLoremIpsum;

    @Test
    public void testGetFullLoremIpsum() {
        int length = 0;
        try {
            for (String[] row : MtxLoremIpsum.getFullLoremIpsum()) {
//                // Will spam console with lorem ipsum
//                System.out.println(Arrays.toString(row));

                if (row.length >= 1 && !MtxStringUtil.isEmpty(row[0])) {
                    length ++;
                }
            }
            assertEquals(MtxLoremIpsum.MAX_LOREMS, length);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetFullLoremIpsumInOneLine() {
        try {
            assertEquals(8382, MtxLoremIpsum.getFullLoremIpsumInOneLine().length);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Nested
    class GetWordsTests {

        @BeforeEach
        public void setUp() {
            mtxLoremIpsum = new MtxLoremIpsum();
        }

        @Test
        public void testGetWords_5() {
            String expected = "Lorem ipsum dolor sit amet,";

            try {
                assertEquals(expected, mtxLoremIpsum.getWords(5));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }

        @Test
        public void testGetWords_30() {
            String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Et odio pellentesque diam volutpat commodo sed egestas. Venenatis tellus in";

            try {
                assertEquals(expected, mtxLoremIpsum.getWords(30));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }

        @Test
        public void testGetWords_tooSmall() {
            assertEquals(
                    "Minimum number of lorem ipsum words is 1",
                    assertThrows(
                            IndexOutOfBoundsException.class,
                            () -> mtxLoremIpsum.getWords(-1)
                    ).getMessage()
            );
        }

        @Test
        public void testGetWords_tooBig() {
            assertEquals(
                    "Max number of lorem ipsum words is " + MtxLoremIpsum.MAX_LOREMS_WORDS,
                    assertThrows(
                            IndexOutOfBoundsException.class,
                            () -> mtxLoremIpsum.getWords(8400)
                    ).getMessage()
            );
        }
    }
}
