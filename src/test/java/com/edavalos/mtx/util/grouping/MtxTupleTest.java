package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxTupleTest {
    private static final char SAMPLE_ELEMENT_0 = 'a';
    private static final char SAMPLE_ELEMENT_1 = 'b';
    private static final char SAMPLE_ELEMENT_2 = 'c';
    private static final char SAMPLE_ELEMENT_3 = 'd';
    private static final char SAMPLE_ELEMENT_4 = 'e';
    private static final int SAMPLE_LENGTH = 5;

    private MtxTuple<Character> mtxTuple;

    @BeforeEach
    public void setUp() {
        mtxTuple = new MtxTuple<>(
                SAMPLE_ELEMENT_0,
                SAMPLE_ELEMENT_1,
                SAMPLE_ELEMENT_2,
                SAMPLE_ELEMENT_3,
                SAMPLE_ELEMENT_4
        );
    }

    @Test
    public void testConstructor_givenContents() {
        assertEquals(SAMPLE_LENGTH, mtxTuple.size());

        assertEquals(SAMPLE_ELEMENT_0, mtxTuple.getAt(0));
        assertEquals(SAMPLE_ELEMENT_1, mtxTuple.getAt(1));
        assertEquals(SAMPLE_ELEMENT_2, mtxTuple.getAt(2));
        assertEquals(SAMPLE_ELEMENT_3, mtxTuple.getAt(3));
        assertEquals(SAMPLE_ELEMENT_4, mtxTuple.getAt(4));

        assertThrows(IndexOutOfBoundsException.class, () -> mtxTuple.getAt(5));
    }

    @Test
    public void testConstructor_givenSize() {
        int arbitrarySize = 12;
        mtxTuple = new MtxTuple<>(arbitrarySize);

        assertEquals(arbitrarySize, mtxTuple.size());

        for (int i = 0; i < arbitrarySize; i++) {
            assertNull(mtxTuple.getAt(i));
        }

        assertThrows(IndexOutOfBoundsException.class, () -> mtxTuple.getAt(arbitrarySize + 1));
    }

    @Test
    public void testSize() {
        assertEquals(SAMPLE_LENGTH, mtxTuple.size());

        int arbitrarySize = 24;
        for (int i = 0; i < arbitrarySize; i++) {
            mtxTuple = new MtxTuple<>(i);
            assertEquals(i, mtxTuple.size());
        }
    }

    @Test
    public void testSetAt_inBounds() {
        char newElem = 'x';
        char[] sampleElems = {
                SAMPLE_ELEMENT_0,
                SAMPLE_ELEMENT_1,
                newElem,
                SAMPLE_ELEMENT_3,
                SAMPLE_ELEMENT_4
        };

        mtxTuple.setAt(newElem, 2);

        for (int i = 0; i < SAMPLE_LENGTH; i++) {
            assertEquals(sampleElems[i], mtxTuple.getAt(i));
        }
    }

    @Test
    public void testSetAt_outOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxTuple.setAt('y', SAMPLE_LENGTH + 1));
    }

    @Test
    public void testGetAt_inBounds() {
        char[] sampleElems = {
                SAMPLE_ELEMENT_0,
                SAMPLE_ELEMENT_1,
                SAMPLE_ELEMENT_2,
                SAMPLE_ELEMENT_3,
                SAMPLE_ELEMENT_4
        };

        for (int i = 0; i < SAMPLE_LENGTH; i++) {
            assertEquals(sampleElems[i], mtxTuple.getAt(i));
        }
    }

    @Test
    public void testGetAt_outOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxTuple.getAt(SAMPLE_LENGTH + 1));
    }

    @Test
    public void testIterator() {
        char[] sampleElems = {
                SAMPLE_ELEMENT_0,
                SAMPLE_ELEMENT_1,
                SAMPLE_ELEMENT_2,
                SAMPLE_ELEMENT_3,
                SAMPLE_ELEMENT_4
        };
        int idx = 0;

        for (char c : mtxTuple) {
            assertEquals(sampleElems[idx], c);
            idx ++;
        }
    }

    @Nested
    public class EqualsTests {

        @Test
        public void testEquals_otherTupleNull() {
            assertFalse(mtxTuple.equals(null));
        }

        @Test
        public void testEquals_otherTupleNotATuple() {
            assertFalse(mtxTuple.equals(new StringBuilder("lol")));
        }

        @Test
        public void testEquals_otherTupleWrongSize() {
            MtxTuple<Character> otherTuple = new MtxTuple<>(
                    SAMPLE_ELEMENT_0,
                    SAMPLE_ELEMENT_1,
                    SAMPLE_ELEMENT_2
            );
            assertFalse(mtxTuple.equals(otherTuple));
        }

        @Test
        public void testEquals_notEqual() {
            MtxTuple<Character> otherTuple = new MtxTuple<>(
                    SAMPLE_ELEMENT_0,
                    SAMPLE_ELEMENT_1,
                    'x',
                    SAMPLE_ELEMENT_3,
                    SAMPLE_ELEMENT_4
            );
            assertFalse(mtxTuple.equals(otherTuple));
        }

        @Test
        public void testEquals_equal() {
            MtxTuple<Character> otherTuple = new MtxTuple<>(
                    SAMPLE_ELEMENT_0,
                    SAMPLE_ELEMENT_1,
                    SAMPLE_ELEMENT_2,
                    SAMPLE_ELEMENT_3,
                    SAMPLE_ELEMENT_4
            );
            assertTrue(mtxTuple.equals(otherTuple));
        }
    }

    @Test
    public void testToString() {
        String mtxTupleAsString = "<" + SAMPLE_ELEMENT_0 + " : " +
                                        SAMPLE_ELEMENT_1 + " : " +
                                        SAMPLE_ELEMENT_2 + " : " +
                                        SAMPLE_ELEMENT_3 + " : " +
                                        SAMPLE_ELEMENT_4 + ">";
        assertEquals(mtxTupleAsString, mtxTuple.toString());
    }
}
