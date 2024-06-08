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

        mtxLoremIpsum = new MtxLoremIpsum();
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
            String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                    "incididunt ut labore et dolore magna aliqua. Et odio pellentesque diam volutpat commodo sed " +
                    "egestas. Venenatis tellus in";

            try {
                assertEquals(expected, mtxLoremIpsum.getWords(30));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }

        @Test
        public void testGetWords_7_startIndex_3() {
            String expected = "sit amet, consectetur adipiscing";

            try {
                assertEquals(expected, mtxLoremIpsum.getWords(7, 3));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }

        @Test
        public void testGetWords_110() {
            String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                    "incididunt ut labore et dolore magna aliqua. Et odio pellentesque diam volutpat commodo sed " +
                    "egestas. Venenatis tellus in metus vulputate eu scelerisque felis. Vitae congue mauris rhoncus " +
                    "aenean vel. Nibh venenatis cras sed felis eget velit aliquet sagittis id. In dictum non " +
                    "consectetur a erat nam. Curabitur vitae nunc sed velit dignissim sodales ut eu. In pellentesque " +
                    "massa placerat duis ultricies lacus. Amet est placerat in egestas. Libero volutpat sed cras " +
                    "ornare. Adipiscing bibendum est ultricies integer quis auctor elit sed. Tempus imperdiet nulla " +
                    "malesuada pellentesque elit. Massa eget egestas purus viverra accumsan. Tincidunt id aliquet risus feugiat";

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

    @Nested
    class GetParagraphTests {

        @Test
        public void testGetParagraphs_1() {
            String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                    "incididunt ut labore et dolore magna aliqua. Et odio pellentesque diam volutpat commodo sed " +
                    "egestas. Venenatis tellus in metus vulputate eu scelerisque felis. Vitae congue mauris rhoncus " +
                    "aenean vel. Nibh venenatis cras sed felis eget velit aliquet sagittis id. In dictum non " +
                    "consectetur a erat nam. Curabitur vitae nunc sed velit dignissim sodales ut eu. In pellentesque " +
                    "massa placerat duis ultricies lacus. Amet est placerat in egestas. Libero volutpat sed cras " +
                    "ornare. Adipiscing bibendum est ultricies integer quis auctor elit sed. Tempus imperdiet nulla " +
                    "malesuada pellentesque elit. Massa eget egestas purus viverra accumsan.";

            try {
                assertEquals(expected, mtxLoremIpsum.getParagraphs(1));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }

        @Test
        public void testGetParagraphs_2() {
            String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                    "incididunt ut labore et dolore magna aliqua. Et odio pellentesque diam volutpat commodo sed " +
                    "egestas. Venenatis tellus in metus vulputate eu scelerisque felis. Vitae congue mauris rhoncus " +
                    "aenean vel. Nibh venenatis cras sed felis eget velit aliquet sagittis id. In dictum non " +
                    "consectetur a erat nam. Curabitur vitae nunc sed velit dignissim sodales ut eu. In pellentesque " +
                    "massa placerat duis ultricies lacus. Amet est placerat in egestas. Libero volutpat sed cras " +
                    "ornare. Adipiscing bibendum est ultricies integer quis auctor elit sed. Tempus imperdiet nulla " +
                    "malesuada pellentesque elit. Massa eget egestas purus viverra accumsan.\n" +

                    "Tincidunt id aliquet risus feugiat in ante. Dui accumsan sit amet nulla facilisi morbi. " +
                    "Ultricies leo integer malesuada nunc vel. Elementum curabitur vitae nunc sed. Nulla facilisi " +
                    "cras fermentum odio. Praesent elementum facilisis leo vel fringilla est ullamcorper. Ut tortor " +
                    "pretium viverra suspendisse potenti nullam ac tortor. Imperdiet proin fermentum leo vel orci " +
                    "porta non pulvinar neque. In fermentum et sollicitudin ac orci. Nisi vitae suscipit tellus " +
                    "mauris a diam maecenas sed enim. Commodo quis imperdiet massa tincidunt nunc pulvinar sapien. " +
                    "Nisl nunc mi ipsum faucibus vitae aliquet nec ullamcorper. Laoreet non curabitur gravida arcu. " +
                    "Metus dictum at tempor commodo ullamcorper a lacus vestibulum. Eu lobortis elementum nibh " +
                    "tellus molestie nunc non.";

            try {
                assertEquals(expected, mtxLoremIpsum.getParagraphs(2));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }

        @Test
        public void testGetParagraphs_tooSmall() {
            assertEquals(
                    "Minimum number of lorem ipsum paragraphs is 1",
                    assertThrows(
                            IndexOutOfBoundsException.class,
                            () -> mtxLoremIpsum.getParagraphs(-1)
                    ).getMessage()
            );
        }

        @Test
        public void testGetParagraphs_tooBig() {
            assertEquals(
                    "Max number of lorem ipsum paragraphs is " + MtxLoremIpsum.MAX_LOREMS,
                    assertThrows(
                            IndexOutOfBoundsException.class,
                            () -> mtxLoremIpsum.getParagraphs(84)
                    ).getMessage()
            );
        }
    }

    @Test
    public void testIterator() {
        String[] expected = {"Lorem", "ipsum", "dolor", "sit", "amet,"};
        int testLength = 5;

        int idx = 0;
        for (String testIpsum : mtxLoremIpsum) {
            if (idx >= testLength) {
                break;
            }

            assertEquals(expected[idx], testIpsum);
            idx ++;
        }
    }
}
