package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxHashSetTest {
    private MtxHashSet<Double> mtxHashSet;

    @BeforeEach
    public void setUp() {
        mtxHashSet = new MtxHashSet<>(Double.class);
    }

    @Test
    public void testConstructor_initialContents() {
        mtxHashSet = new MtxHashSet<>(Double.class,
                3.0,
                4.0,
                1.0
        );

        double[] values = {1.0, 3.0, 4.0};
        for (double value : values) {
            assertTrue(mtxHashSet.contains(value));
        }
        assertEquals(values.length, mtxHashSet.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, mtxHashSet.size());

        mtxHashSet.add(2.0);
        mtxHashSet.add(3.0);
        mtxHashSet.add(4.0);
        mtxHashSet.add(4.0);
        mtxHashSet.add(2.0);

        assertEquals(3, mtxHashSet.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mtxHashSet.isEmpty());

        mtxHashSet.add(5.0);
        assertFalse(mtxHashSet.isEmpty());

        mtxHashSet.remove(4.0);
        assertFalse(mtxHashSet.isEmpty());

        mtxHashSet.remove(5.0);
        assertTrue(mtxHashSet.isEmpty());
    }

    @Test
    public void testToArray() {
        assertEquals(0, mtxHashSet.toArray().length);

        mtxHashSet.add(1.1);
        mtxHashSet.add(1.1);
        mtxHashSet.add(1.2);
        mtxHashSet.add(1.3);
        Double[] expectedValues = {1.1, 1.2, 1.3};
        Double[] actualValues = mtxHashSet.toArray();

        assertEquals(expectedValues.length, actualValues.length);

        assertFalse(arrayContainsValue(expectedValues, 1.4));
        for (Double expectedValue : expectedValues) {
            assertTrue(arrayContainsValue(actualValues, expectedValue));
        }
        assertFalse(arrayContainsValue(actualValues, 1.4));
        for (Double actualValue : actualValues) {
            assertTrue(arrayContainsValue(expectedValues, actualValue));
        }
    }

    private boolean arrayContainsValue(Double[] array, Double value) {
        for (Double element : array) {
            if (Objects.equals(element, value)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testClear() {
        assertEquals(0, mtxHashSet.size());
        assertEquals("()", mtxHashSet.toString());

        mtxHashSet.add(3.5);
        assertEquals(1, mtxHashSet.size());
        assertEquals("(3.5)", mtxHashSet.toString());

        mtxHashSet.clear();
        assertEquals(0, mtxHashSet.size());
        assertEquals("()", mtxHashSet.toString());
    }

    @Test
    public void testHashCode() {
        mtxHashSet.add(1.0);
        mtxHashSet.add(2.0);
        mtxHashSet.add(3.0);

        MtxSet<Double> otherHashSet = new MtxHashSet<>(Double.class);
        otherHashSet.add(3.0);
        otherHashSet.add(2.0);
        otherHashSet.add(1.0);

        assertEquals(otherHashSet.hashCode(), mtxHashSet.hashCode());
    }

    @Test
    public void testEquals() {
        mtxHashSet.add(1.0);
        mtxHashSet.add(2.0);
        mtxHashSet.add(3.0);

        MtxSet<Double> otherHashSet = new MtxHashSet<>(Double.class);
        assertFalse(mtxHashSet.equals(otherHashSet));

        otherHashSet.add(3.0);
        otherHashSet.add(2.0);
        otherHashSet.add(4.0);
        assertFalse(mtxHashSet.equals(otherHashSet));

        otherHashSet.remove(4.0);
        otherHashSet.add(1.0);
        assertTrue(mtxHashSet.equals(otherHashSet));

        otherHashSet.remove(2.0);
        assertFalse(mtxHashSet.equals(otherHashSet));

        assertFalse(mtxHashSet.equals(2.0));
    }

    @Test
    public void testToString() {
        assertEquals("()", mtxHashSet.toString());

        mtxHashSet.add(3.0);
        assertEquals("(3.0)", mtxHashSet.toString());

        mtxHashSet.add(6.0);
        mtxHashSet.add(9.0);
        mtxHashSet.add(9.0);
        mtxHashSet.remove(6.0);
        mtxHashSet.remove(3.0);
        mtxHashSet.remove(2.0);
        mtxHashSet.add(6.0);
        mtxHashSet.add(3.0);
        mtxHashSet.add(6.0);

        assertTrue(mtxHashSet.toString().contains("3.0"));
        assertTrue(mtxHashSet.toString().contains("6.0"));
        assertTrue(mtxHashSet.toString().contains("9.0"));

        assertTrue(mtxHashSet.toString().contains("("));
        assertTrue(mtxHashSet.toString().contains(")"));
        assertTrue(mtxHashSet.toString().contains(","));
    }

    @Test
    public void testContains() {
        mtxHashSet.add(0.1);
        mtxHashSet.add(0.2);
        mtxHashSet.add(0.3);
        mtxHashSet.remove(0.25);
        mtxHashSet.remove(0.3);

        assertTrue(mtxHashSet.contains(0.1));
        assertTrue(mtxHashSet.contains(0.2));
        assertFalse(mtxHashSet.contains(0.3));
        assertFalse(mtxHashSet.contains(0.4));
    }

    @Test
    public void testContainsAll() {
        mtxHashSet.add(0.1);
        mtxHashSet.add(0.2);
        mtxHashSet.add(0.3);
        mtxHashSet.remove(0.25);
        mtxHashSet.remove(0.3);

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
        assertTrue(mtxHashSet.containsAll(inclusiveList));
        assertTrue(mtxHashSet.containsAll(inclusiveList.toArray(new Double[0])));
        assertFalse(mtxHashSet.containsAll(nonInclusiveList));
        assertFalse(mtxHashSet.containsAll(nonInclusiveList.toArray(new Double[0])));
    }

    @Test
    public void testAdd_singleElement() {
        int numberOfTests = 10;
        for (double i = 0.0; i < numberOfTests; i++) {
            assertTrue(mtxHashSet.add(i));
            assertFalse(mtxHashSet.add(i));
        }
    }

    @Test
    public void testAdd_multipleElements() {
        int numberOfTests = 10;
        for (double i = 0.0; i < numberOfTests; i++) {
            assertTrue(mtxHashSet.add(
                    i,
                    i + 0.1,
                    i + 0.2,
                    i + 0.3
            ));
            assertFalse(mtxHashSet.add(
                    i + 0.4,
                    i + 0.5,
                    i + 0.3
            ));
            assertEquals((int) ((i + 1) * 6), mtxHashSet.size());
        }
    }

    @Test
    public void testAdd_nullElement() {
        Double nullElement = null;
        assertFalse(mtxHashSet.add(nullElement));
    }

    @Test
    public void testAddAll_allNewElements() {
        assertTrue(mtxHashSet.addAll(new Double[] {2.54, 3.65, 4.76}));

        for (double d : new double[] {2.54, 3.65, 4.76}) {
            assertTrue(mtxHashSet.contains(d));
        }
    }

    @Test
    public void testAddAll_oneExistingElement() {
        mtxHashSet.add(2.54);
        assertFalse(mtxHashSet.addAll(new Double[] {2.54, 3.65, 4.76}));

        for (double d : new double[] {3.65, 4.76}) {
            assertFalse(mtxHashSet.contains(d));
        }
    }

    @Test
    public void testRemove() {
        int numberOfTests = 10;
        for (double i = 0.0; i < numberOfTests; i++) {
            mtxHashSet.add(i);
        }

        for (double j = 0; j < numberOfTests; j++) {
            assertFalse(mtxHashSet.remove(j + 0.5));
            assertTrue(mtxHashSet.remove(j));
            assertFalse(mtxHashSet.remove(j));
        }
    }
}
