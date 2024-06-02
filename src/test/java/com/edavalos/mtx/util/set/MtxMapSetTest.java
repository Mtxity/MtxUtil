package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxMapSetTest {
    private static final double VAL1 = 1.1;
    private static final double VAL2 = 2.2;
    private static final double VAL3 = 3.3;

    private MtxMapSet<Double> mtxMapSet;

    @Test
    public void testConstructor_empty() {
        mtxMapSet = new MtxMapSet<>();
        assertEquals(0, mtxMapSet.size());
        assertTrue(mtxMapSet.setContents.isEmpty());
    }

    @Test
    public void testConstructor_withContents() {
        Double[] array = new Double[] {2.5, 3.5, 4.5};
        mtxMapSet = new MtxMapSet<>(4.5, 3.5, 2.5);
        assertEquals(3, mtxMapSet.size());
        assertFalse(mtxMapSet.setContents.isEmpty());
        assertSetEquals(array, mtxMapSet.toArray());
    }

    @Test
    public void testSize() {
        mtxMapSet = new MtxMapSet<>();
        assertEquals(0, mtxMapSet.size());

        mtxMapSet.add(VAL1);
        assertEquals(1, mtxMapSet.size());
    }

    @Test
    public void testIsEmpty() {
        mtxMapSet = new MtxMapSet<>();
        assertTrue(mtxMapSet.isEmpty());

        mtxMapSet.add(VAL1);
        assertFalse(mtxMapSet.isEmpty());
    }

    @Test
    public void testContains() {
        mtxMapSet = new MtxMapSet<>();
        assertFalse(mtxMapSet.contains(VAL1));

        mtxMapSet.add(VAL1);
        assertTrue(mtxMapSet.contains(VAL1));
    }

    @Test
    public void testToArray() {
        Double[] array = new Double[] {VAL2, VAL1, VAL3};
        mtxMapSet = new MtxMapSet<>(array);
        assertSetEquals(array, mtxMapSet.toArray());
    }

    @Test
    public void testAdd_newElement() {
        mtxMapSet = new MtxMapSet<>();
        assertTrue(mtxMapSet.isEmpty());

        assertTrue(mtxMapSet.add(VAL1));
        assertFalse(mtxMapSet.isEmpty());
        assertTrue(mtxMapSet.contains(VAL1));
        assertArrayEquals(new Double[] {VAL1}, mtxMapSet.toArray());

        assertEquals(1, mtxMapSet.size());
    }

    @Test
    public void testAdd_existingElement() {
        mtxMapSet = new MtxMapSet<>(VAL1);
        assertFalse(mtxMapSet.add(VAL1));

        assertEquals(1, mtxMapSet.size());
    }

    @Test
    public void testAddAll_allNewElements() {
        mtxMapSet = new MtxMapSet<>();
        assertTrue(mtxMapSet.addAll(new Double[] {2.54, 3.65, 4.76}));

        for (double d : new double[] {2.54, 3.65, 4.76}) {
            assertTrue(mtxMapSet.contains(d));
        }
    }

    @Test
    public void testAddAll_oneExistingElement() {
        mtxMapSet = new MtxMapSet<>();
        mtxMapSet.add(2.54);
        assertFalse(mtxMapSet.addAll(new Double[] {2.54, 3.65, 4.76}));

        for (double d : new double[] {3.65, 4.76}) {
            assertFalse(mtxMapSet.contains(d));
        }
    }

    @Test
    public void testRemove_elementFound() {
        mtxMapSet = new MtxMapSet<>(VAL1);
        assertTrue(mtxMapSet.remove(VAL1));

        assertEquals(0, mtxMapSet.size());
    }

    @Test
    public void testRemove_elementNotFound() {
        mtxMapSet = new MtxMapSet<>(VAL1);
        assertFalse(mtxMapSet.remove(1.2));

        assertEquals(1, mtxMapSet.size());
    }

    @Nested
    class RemoveAndReturnTests {

        @BeforeEach
        public void setUp() {
            mtxMapSet = new MtxMapSet<>(VAL1, VAL2, VAL3);
        }

        @Test
        public void testRemoveAndReturn_elementFound_first() {
            Double element = VAL1;
            assertEquals(3, mtxMapSet.size());

            assertEquals(element, mtxMapSet.removeAndReturn(element));
            assertEquals(2, mtxMapSet.size());
        }

        @Test
        public void testRemoveAndReturn_elementFound_middle() {
            Double element = VAL2;
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

    @Test
    public void testClear() {
        mtxMapSet = new MtxMapSet<>(VAL1, VAL2);
        assertEquals(2, mtxMapSet.size());
        assertFalse(mtxMapSet.isEmpty());

        mtxMapSet.clear();
        assertEquals(0, mtxMapSet.size());
        assertTrue(mtxMapSet.isEmpty());
    }

    @Test
    public void testGetHashCode() {
        Object[] array = new Object[] {1, "2", 3.3, new MtxMapSet<>()};
        for (Object object : array) {
            assertEquals(object.hashCode(), new MtxMapSet<>().getHashCode(object));
        }
    }

    private boolean assertSetEquals(Object[] expectedSetArray, Object[] actualSetArray) {
        if (expectedSetArray.length != actualSetArray.length) {
            fail("Expected Set length (" + expectedSetArray.length +
                    ") is different than Actual Set length (" + actualSetArray.length + ")");
            return false; // Return value should be ignored
        }

        Set<Object> expected = Set.of(expectedSetArray);
        Set<Object> actual = Set.of(actualSetArray);
        assertEquals(expected, actual);
        return expected.equals(actual);
    }
}
