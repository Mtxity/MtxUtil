package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxMapSetTest {
    private MtxMapSet<Double> mtxMapSet;

    @Test
    public void testConstructor_empty() {
        mtxMapSet = new MtxMapSet<>();
        assertEquals(0, mtxMapSet.size());
        assertTrue(mtxMapSet.setContents.isEmpty());
    }

    // @TODO: Make sure unordered results don't fail
    @Test
    public void testConstructor_withContents() {
        mtxMapSet = new MtxMapSet<>(2.5, 3.5, 4.5);
        assertEquals(3, mtxMapSet.size());
        assertFalse(mtxMapSet.setContents.isEmpty());
        assertArrayEquals(new Double[] {2.5, 3.5, 4.5}, mtxMapSet.toArray());
    }

    @Test
    public void testSize() {
        mtxMapSet = new MtxMapSet<>();
        assertEquals(0, mtxMapSet.size());

        mtxMapSet.add(1.1);
        assertEquals(1, mtxMapSet.size());
    }

    @Test
    public void testIsEmpty() {
        mtxMapSet = new MtxMapSet<>();
        assertTrue(mtxMapSet.isEmpty());

        mtxMapSet.add(1.1);
        assertFalse(mtxMapSet.isEmpty());
    }

    @Test
    public void testContains() {
        mtxMapSet = new MtxMapSet<>();
        assertFalse(mtxMapSet.contains(1.1));

        mtxMapSet.add(1.1);
        assertTrue(mtxMapSet.contains(1.1));
    }

    // @TODO: Make sure unordered results don't fail
    @Test
    public void testToArray() {
        Double[] array = new Double[] {1.1, 2.2, 3.3};
        mtxMapSet = new MtxMapSet<>(array);
        assertArrayEquals(array, mtxMapSet.toArray());
    }

    @Test
    public void testAdd_newElement() {
        mtxMapSet = new MtxMapSet<>();
        assertTrue(mtxMapSet.isEmpty());

        assertTrue(mtxMapSet.add(1.1));
        assertFalse(mtxMapSet.isEmpty());
        assertTrue(mtxMapSet.contains(1.1));
        assertArrayEquals(new Double[] {1.1}, mtxMapSet.toArray());

        assertEquals(1, mtxMapSet.size());
    }

    @Test
    public void testAdd_existingElement() {
        mtxMapSet = new MtxMapSet<>(1.1);
        assertFalse(mtxMapSet.add(1.1));

        assertEquals(1, mtxMapSet.size());
    }

    @Test
    public void testRemove_elementFound() {
        mtxMapSet = new MtxMapSet<>(1.1);
        assertTrue(mtxMapSet.remove(1.1));

        assertEquals(0, mtxMapSet.size());
    }

    @Test
    public void testRemove_elementNotFound() {
        mtxMapSet = new MtxMapSet<>(1.1);
        assertFalse(mtxMapSet.remove(1.2));

        assertEquals(1, mtxMapSet.size());
    }

    @Nested
    class RemoveAndReturnTests {

        @BeforeEach
        public void setUp() {
            // @TODO: Make these default values consts
            mtxMapSet = new MtxMapSet<>(1.1, 2.2, 3.3);
        }

        @Test
        public void testRemoveAndReturn_elementFound_first() {
            Double element = 1.1;
            assertEquals(3, mtxMapSet.size());

            assertEquals(element, mtxMapSet.removeAndReturn(element));
            assertEquals(2, mtxMapSet.size());
        }

        @Test
        public void testRemoveAndReturn_elementFound_middle() {
            Double element = 2.2;
            assertEquals(3, mtxMapSet.size());

            assertEquals(element, mtxMapSet.removeAndReturn(element));
            assertEquals(2, mtxMapSet.size());
        }

        @Test
        public void testRemoveAndReturn_elementNotFound() {
            Double element = 4.4;
            assertEquals(3, mtxMapSet.size());

            assertNull(mtxMapSet.removeAndReturn(element));
            assertEquals(3, mtxMapSet.size());
        }

        @Test
        public void testRemoveAndReturn_elementNull() {
            Double element = null;
            assertEquals(3, mtxMapSet.size());

            assertNull(mtxMapSet.removeAndReturn(element));
            assertEquals(3, mtxMapSet.size());
        }
    }
}
