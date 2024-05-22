package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxArraySetTest {
    private MtxArraySet<String> mtxArraySet;

    @Nested
    class ConstructorTests {

        @Test
        public void testConstructor_withStartingSize() {
            mtxArraySet = new MtxArraySet<>(5);
            assertArrayEquals(new String[]{null, null, null, null, null}, mtxArraySet.setContents);
            assertEquals(0, mtxArraySet.nextIndex);
        }

        @Test
        public void testConstructor_withStartingSize_negativeValue() {
            String expectedMessage = "MtxArraySet size cannot be negative";
            String actualMessage = assertThrows(
                    NegativeArraySizeException.class,
                    () -> mtxArraySet = new MtxArraySet<>(-5)
            ).getMessage();
            assertEquals(expectedMessage, actualMessage);
        }

        @Test
        public void testConstructor_withContents() {
            mtxArraySet = new MtxArraySet<>("a", "b");
            assertArrayEquals(
                    new String[]{"a", "b", null, null, null, null, null, null, null, null},
                    mtxArraySet.setContents
            );
            assertEquals(2, mtxArraySet.nextIndex);
        }

        @Test
        public void testConstructor_default() {
            mtxArraySet = new MtxArraySet<>();
            assertArrayEquals(new String[]{null, null, null, null, null, null, null, null}, mtxArraySet.setContents);
            assertEquals(0, mtxArraySet.nextIndex);
        }
    }

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

    @Nested
    class RemoveAndReturnTests {

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

            assertEquals("x", mtxArraySet.removeAndReturn("x"));

            assertEquals(2, mtxArraySet.nextIndex);
            assertEquals("z", mtxArraySet.setContents[0]);
            assertEquals("y", mtxArraySet.setContents[1]);
            assertNull(mtxArraySet.setContents[2]);
        }

        @Test
        public void testRemove_elementNotFound() {
            assertNull(mtxArraySet.removeAndReturn("w"));
        }

        @Test
        public void testRemove_elementNull() {
            assertNull(mtxArraySet.removeAndReturn(null));
        }
    }

    @Test
    public void testClear() {
        mtxArraySet = new MtxArraySet<>();
        mtxArraySet.add("1");
        mtxArraySet.add("2");
        mtxArraySet.add("3");
        mtxArraySet.add("4");

        assertEquals(4, mtxArraySet.nextIndex);
        assertArrayEquals(new String[]{"1", "2", "3", "4", null, null, null, null}, mtxArraySet.setContents);

        mtxArraySet.clear();
        assertEquals(0, mtxArraySet.nextIndex);
        assertArrayEquals(new String[]{null, null, null, null, null, null, null, null}, mtxArraySet.setContents);
    }

    @Test
    public void testIsEmpty() {
        mtxArraySet = new MtxArraySet<>();
        assertTrue(mtxArraySet.isEmpty());

        mtxArraySet.add("t");
        assertFalse(mtxArraySet.isEmpty());

        mtxArraySet.remove("t");
        assertTrue(mtxArraySet.isEmpty());
    }

    @Nested
    class ContainsTests {

        @BeforeEach
        public void setUp() {
            mtxArraySet = new MtxArraySet<>("a", "b", "c");
        }

        @Test
        public void testContains_containsElement() {
            assertTrue(mtxArraySet.contains("b"));
        }

        @Test
        public void testContains_doesNotContainElement() {
            assertFalse(mtxArraySet.contains("d"));
        }

        @Test
        public void testContains_nullElement() {
            assertTrue(mtxArraySet.contains(null));
        }
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_empty() {
            mtxArraySet = new MtxArraySet<>();

            assertEquals("()", mtxArraySet.toString());
        }

        @Test
        public void testToString_oneItem() {
            mtxArraySet = new MtxArraySet<>("i");

            assertEquals("(i)", mtxArraySet.toString());
        }

        @Test
        public void testToString_multipleItems() {
            mtxArraySet = new MtxArraySet<>("i", "j", "k");

            assertEquals("(i, j, k)", mtxArraySet.toString());
        }
    }

    @Test
    public void testHashCode() {
        mtxArraySet = new MtxArraySet<>();
        mtxArraySet.add("1");
        mtxArraySet.add("2");
        mtxArraySet.add("3");

        MtxArraySet<String> otherArraySet = new MtxArraySet<>("1", "2", "3");

        assertEquals(otherArraySet.hashCode(), mtxArraySet.hashCode());
    }
}
