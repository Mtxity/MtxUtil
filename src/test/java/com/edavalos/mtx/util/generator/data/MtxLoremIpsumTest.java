package com.edavalos.mtx.util.generator.data;

import com.edavalos.mtx.util.string.MtxStringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class MtxLoremIpsumTest {
    private MtxLoremIpsum mtxLoremIpsum;

    @BeforeEach
    public void setUp() {
        MtxLoremIpsum.LOREM_IPSUM = null;
        MtxLoremIpsum.LOREM_IPSUM_SINGLE_LINE = null;
    }

    @Test
    public void testGetFullLoremIpsum() {
        int length = 0;
        try {
            for (String[] row : MtxLoremIpsum.getFullLoremIpsum()) {
//                // Spam console with lorem ipsum
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
        public void testGetWords_3() {
            String expected = "Lorem ipsum dolor";

            try {
                assertEquals(expected, mtxLoremIpsum.getWords(3));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }

        @Test
        public void testGetWords_5() {
            String expected = "Lorem ipsum dolor sit amet";

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
        public void testGetWords_110() {
            String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Et odio pellentesque diam volutpat commodo sed egestas. Venenatis tellus in metus vulputate eu scelerisque felis. Vitae congue mauris rhoncus aenean vel. Nibh venenatis cras sed felis eget velit aliquet sagittis id. In dictum non consectetur a erat nam. Curabitur vitae nunc sed velit dignissim sodales ut eu. In pellentesque massa placerat duis ultricies lacus. Amet est placerat in egestas. Libero volutpat sed cras ornare. Adipiscing bibendum est ultricies integer quis auctor elit sed. Tempus imperdiet nulla malesuada pellentesque elit. Massa eget egestas purus viverra accumsan. Tincidunt id aliquet risus feugiat";

            try {
                assertEquals(expected, mtxLoremIpsum.getWords(110));
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
