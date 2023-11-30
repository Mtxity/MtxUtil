package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxLinkedSetTest {
    private MtxLinkedSet<Double> mtxLinkedSet;

    @BeforeEach
    public void setUp() {
        mtxLinkedSet = new MtxLinkedSet<>(Double.class);
    }

    @Test
    public void testConstructor_initialContents() {
        mtxLinkedSet = new MtxLinkedSet<>(Double.class,
                1.0,
                3.0,
                4.0,
                3.0
        );

        double[] values = {1.0, 3.0, 4.0};
        for (double value : values) {
            assertTrue(mtxLinkedSet.contains(value));
        }
        assertEquals(values.length, mtxLinkedSet.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, mtxLinkedSet.size());

        mtxLinkedSet.add(2.0);
        mtxLinkedSet.add(3.0);
        mtxLinkedSet.add(4.0);
        mtxLinkedSet.add(4.0);
        mtxLinkedSet.add(2.0);
        mtxLinkedSet.remove(3.0);

        assertEquals(2, mtxLinkedSet.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mtxLinkedSet.isEmpty());

        mtxLinkedSet.add(5.0);
        assertFalse(mtxLinkedSet.isEmpty());

        mtxLinkedSet.remove(4.0);
        assertFalse(mtxLinkedSet.isEmpty());

        mtxLinkedSet.remove(5.0);
        assertTrue(mtxLinkedSet.isEmpty());
    }

    @Test
    public void testToArray() {
        assertEquals(0, mtxLinkedSet.toArray().length);

        mtxLinkedSet.add(1.1);
        mtxLinkedSet.add(1.1);
        mtxLinkedSet.add(1.2);
        mtxLinkedSet.add(1.3);
        Double[] expectedValues = {1.1, 1.2, 1.3};
        Double[] actualValues = mtxLinkedSet.toArray();

        assertEquals(expectedValues.length, actualValues.length);
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }

    @Test
    public void testClear() {
        assertEquals(0, mtxLinkedSet.size());
        assertEquals("()", mtxLinkedSet.toString());

        mtxLinkedSet.add(3.5, 4.5, 5.5);
        assertEquals(3, mtxLinkedSet.size());
        assertEquals("(3.5, 4.5, 5.5)", mtxLinkedSet.toString());

        mtxLinkedSet.clear();
        assertEquals(0, mtxLinkedSet.size());
        assertEquals("()", mtxLinkedSet.toString());
    }

    @Test
    public void testHashCode() {
        mtxLinkedSet.add(1.0);
        mtxLinkedSet.add(2.0);
        mtxLinkedSet.add(3.0);

        MtxSet<Double> otherHashSet = new MtxHashSet<>(Double.class);
        otherHashSet.add(3.0);
        otherHashSet.add(2.0);
        otherHashSet.add(1.0);

        assertEquals(otherHashSet.hashCode(), mtxLinkedSet.hashCode());
    }

    @Test
    public void testEquals() {
        mtxLinkedSet.add(1.0);
        mtxLinkedSet.add(2.0);
        mtxLinkedSet.add(3.0);

        MtxSet<Double> otherHashSet = new MtxHashSet<>(Double.class);
        assertFalse(mtxLinkedSet.equals(otherHashSet));

        otherHashSet.add(3.0);
        otherHashSet.add(2.0);
        otherHashSet.add(4.0);
        assertFalse(mtxLinkedSet.equals(otherHashSet));

        otherHashSet.remove(4.0);
        otherHashSet.add(1.0);
        assertTrue(mtxLinkedSet.equals(otherHashSet));

        otherHashSet.remove(2.0);
        assertFalse(mtxLinkedSet.equals(otherHashSet));

        assertFalse(mtxLinkedSet.equals(2.0));
    }

    @Test
    public void testToString() {
        assertEquals("()", mtxLinkedSet.toString());

        mtxLinkedSet.add(3.0);
        assertEquals("(3.0)", mtxLinkedSet.toString());

        mtxLinkedSet.add(6.0);
        mtxLinkedSet.add(9.0);
        mtxLinkedSet.add(9.0);
        mtxLinkedSet.remove(6.0);
        mtxLinkedSet.remove(3.0);
        mtxLinkedSet.remove(2.0);
        mtxLinkedSet.add(6.0);
        mtxLinkedSet.add(3.0);
        mtxLinkedSet.add(6.0);

        String expected = "(9.0, 6.0, 3.0)";
        assertEquals(expected, mtxLinkedSet.toString());
    }

    @Test
    public void testContains() {
        mtxLinkedSet.add(0.1);
        mtxLinkedSet.add(0.2);
        mtxLinkedSet.add(0.3);
        mtxLinkedSet.remove(0.25);
        mtxLinkedSet.remove(0.3);

        assertTrue(mtxLinkedSet.contains(0.1));
        assertTrue(mtxLinkedSet.contains(0.2));
        assertFalse(mtxLinkedSet.contains(0.3));
        assertFalse(mtxLinkedSet.contains(0.4));
    }

    @Test
    public void testContainsAll() {
        mtxLinkedSet.add(0.1);
        mtxLinkedSet.add(0.2);
        mtxLinkedSet.add(0.3);
        mtxLinkedSet.remove(0.25);
        mtxLinkedSet.remove(0.3);

        List<Double> inclusiveList = new ArrayList<>() {
            {
                add(0.1);
                add(0.2);
            }
        };
        List<Double> nonInclusiveList = new ArrayList<>() {
            {
                add(0.1);
                add(0.3);
            }
        };
        assertTrue(mtxLinkedSet.containsAll(inclusiveList));
        assertTrue(mtxLinkedSet.containsAll(inclusiveList.toArray(new Double[0])));
        assertFalse(mtxLinkedSet.containsAll(nonInclusiveList));
        assertFalse(mtxLinkedSet.containsAll(nonInclusiveList.toArray(new Double[0])));
    }

    @Test
    public void testAdd_singleElement() {
        int numberOfTests = 10;
        for (double i = 0.0; i < numberOfTests; i++) {
            assertTrue(mtxLinkedSet.add(i));
            assertFalse(mtxLinkedSet.add(i));
        }
        assertEquals(numberOfTests, mtxLinkedSet.size());
    }

    @Test
    public void testAdd_multipleElements() {
        int numberOfTests = 10;
        for (double i = 0.0; i < numberOfTests; i++) {
            assertTrue(mtxLinkedSet.add(
                    i,
                    i + 0.1,
                    i + 0.2,
                    i + 0.3
            ));
            assertFalse(mtxLinkedSet.add(
                    i + 0.4,
                    i + 0.5,
                    i + 0.3
            ));
            assertEquals((int) ((i + 1) * 6), mtxLinkedSet.size());
        }
    }

    @Test
    public void testRemove() {
        int numberOfTests = 10;
        for (double i = 0.0; i < numberOfTests; i++) {
            mtxLinkedSet.add(i);
        }

        for (double j = 0; j < numberOfTests; j++) {
            assertFalse(mtxLinkedSet.remove(j + 0.5));
            assertTrue(mtxLinkedSet.remove(j));
            assertFalse(mtxLinkedSet.remove(j));
        }
    }
}
