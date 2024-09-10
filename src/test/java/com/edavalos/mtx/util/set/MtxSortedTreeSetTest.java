package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxSortedTreeSetTest {
    private static final Double[] SORTED_VALS = {1.1, 2.2, 3.3, 4.4, 5.5};
    private static final Double[] UNSORTED_VALS = {3.3, 5.5, 2.2, 1.1, 4.4};
    private static final Comparator<Double> COMPARATOR = Comparator.naturalOrder();

    private MtxSortedTreeSet<Double> mtxSortedTreeSet;

    @Nested
    class ConstructorTests {

        @Test
        public void testConstructor_empty() {
            mtxSortedTreeSet = new MtxSortedTreeSet<>(COMPARATOR);
            assertTrue(mtxSortedTreeSet.isEmpty());
            assertEquals("()", mtxSortedTreeSet.toString());
            assertEquals(0, mtxSortedTreeSet.setContents.size());
        }

        @Test
        public void testConstructor_withContents() {
            mtxSortedTreeSet = new MtxSortedTreeSet<>(COMPARATOR, 10.0, 20.0, 30.0);
            assertFalse(mtxSortedTreeSet.isEmpty());
            assertEquals("(10.0, 20.0, 30.0)", mtxSortedTreeSet.toString());
            assertEquals(3, mtxSortedTreeSet.setContents.size());
        }

        @Test
        public void testConstructor_withUnsortedContents() {
            mtxSortedTreeSet = new MtxSortedTreeSet<>(COMPARATOR, UNSORTED_VALS);
            assertFalse(mtxSortedTreeSet.isEmpty());
            assertArrayEquals(SORTED_VALS, mtxSortedTreeSet.toArray());
            assertEquals("(1.1, 2.2, 3.3, 4.4, 5.5)", mtxSortedTreeSet.toString());
            assertEquals(5, mtxSortedTreeSet.setContents.size());
        }
    }

    @Nested
    class AddTests {

        @BeforeEach
        public void setUp() {
            mtxSortedTreeSet = new MtxSortedTreeSet<>(COMPARATOR);
        }

        @Test
        public void testAdd_inOrder() {
            for (int i = 0; i < SORTED_VALS.length; i++) {
                double val = SORTED_VALS[i];
                assertTrue(mtxSortedTreeSet.add(val));
                assertFalse(mtxSortedTreeSet.add(val));
            }
            assertArrayEquals(SORTED_VALS, mtxSortedTreeSet.toArray());
        }

        @Test
        public void testAdd_outOfOrder() {
            for (int i = 0; i < UNSORTED_VALS.length; i++) {
                double val = UNSORTED_VALS[i];
                assertTrue(mtxSortedTreeSet.add(val));
                assertFalse(mtxSortedTreeSet.add(val));
            }
            assertArrayEquals(SORTED_VALS, mtxSortedTreeSet.toArray());
        }

        @Test
        public void testAdd_nullElement() {
            assertFalse(mtxSortedTreeSet.add(null));
        }
    }

    @Nested
    class RemoveTests {

        @BeforeEach
        public void setUp() {
            mtxSortedTreeSet = new MtxSortedTreeSet<>(COMPARATOR, SORTED_VALS);
        }

        @Test
        public void testRemove_elementInSet() {
            assertTrue(mtxSortedTreeSet.remove(2.2));
            assertEquals("(1.1, 3.3, 4.4, 5.5)", mtxSortedTreeSet.toString());
        }

        @Test
        public void testRemove_elementNotInSet() {
            assertFalse(mtxSortedTreeSet.remove(6.6));
            assertEquals("(1.1, 2.2, 3.3, 4.4, 5.5)", mtxSortedTreeSet.toString());
        }

        @Test
        public void testRemove_nullElement() {
            assertFalse(mtxSortedTreeSet.remove(null));
        }
    }

    @Nested
    class RemoveAndReturnTests {

        @BeforeEach
        public void setUp() {
            mtxSortedTreeSet = new MtxSortedTreeSet<>(COMPARATOR, SORTED_VALS);
        }

        @Test
        public void testRemove_elementInSet() {
            Double element = 2.2;
            assertEquals(element, mtxSortedTreeSet.removeAndReturn(element));
            assertEquals("(1.1, 3.3, 4.4, 5.5)", mtxSortedTreeSet.toString());
        }

        @Test
        public void testRemove_elementNotInSet() {
            assertNull(mtxSortedTreeSet.removeAndReturn(6.6));
            assertEquals("(1.1, 2.2, 3.3, 4.4, 5.5)", mtxSortedTreeSet.toString());
        }

        @Test
        public void testRemove_nullElement() {
            assertNull(mtxSortedTreeSet.removeAndReturn(null));
        }
    }

    @Test
    public void testClear() {
        mtxSortedTreeSet = new MtxSortedTreeSet<>(COMPARATOR, SORTED_VALS);
        mtxSortedTreeSet.clear();
        assertTrue(mtxSortedTreeSet.isEmpty());
    }
}
