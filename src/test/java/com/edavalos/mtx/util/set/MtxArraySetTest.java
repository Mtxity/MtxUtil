package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxArraySetTest {
    private MtxArraySet<String> mtxArraySet;

    @Test
    public void testAdd() {
        mtxArraySet = new MtxArraySet<>();
        String[] expected1 = new String[MtxArraySet.STARTING_SIZE];
        for (int i = 0; i < MtxArraySet.STARTING_SIZE; i++) {
            expected1[i] = String.valueOf(i);

            assertEquals(i, mtxArraySet.nextIndex);
            assertTrue(mtxArraySet.add(String.valueOf(i)));
            assertEquals(expected1.length, mtxArraySet.setContents.length);
            assertArrayEquals(expected1, mtxArraySet.setContents);
        }

        String[] expected2 = new String[MtxArraySet.STARTING_SIZE * 2];
        System.arraycopy(expected1, 0, expected2, 0, MtxArraySet.STARTING_SIZE);
        for (int i = MtxArraySet.STARTING_SIZE; i < 15; i++) {
            expected2[i] = String.valueOf(i);

            assertEquals(i, mtxArraySet.nextIndex);
            assertTrue(mtxArraySet.add(String.valueOf(i)));
            assertEquals(expected2.length, mtxArraySet.setContents.length);
            assertArrayEquals(expected2, mtxArraySet.setContents);
        }
    }

    @Test
    public void testSize() {
        mtxArraySet = new MtxArraySet<>();
        for (int i = 0; i < 12; i++) {
            assertEquals(i, mtxArraySet.size());
            mtxArraySet.add(String.valueOf(i));
        }
    }

    @Nested
    class RemoveTests {

        @BeforeEach
        public void setUp() {
            mtxArraySet = new MtxArraySet<>("x", "y", "z");
        }

        @Test
        public void testRemove_elementFound() {
            assertEquals(3, mtxArraySet.nextIndex);
            assertEquals("x", mtxArraySet.setContents[0]);
            assertEquals("y", mtxArraySet.setContents[1]);
            assertEquals("z", mtxArraySet.setContents[2]);

            assertTrue(mtxArraySet.remove("x"));

            assertEquals(2, mtxArraySet.nextIndex);
            assertEquals("z", mtxArraySet.setContents[0]);
            assertEquals("y", mtxArraySet.setContents[1]);
            assertNull(mtxArraySet.setContents[2]);
        }

        @Test
        public void testRemove_elementNotFound() {
            assertFalse(mtxArraySet.remove("w"));
        }

        @Test
        public void testRemove_elementNull_noNullsInSet() {
            assertFalse(mtxArraySet.remove(null));
        }

        @Test
        public void testRemove_elementNull_hasNullsInSet() {
            mtxArraySet.nextIndex ++;
            mtxArraySet.setContents[3] = null;
            assertTrue(mtxArraySet.remove(null));
        }
    }
}
