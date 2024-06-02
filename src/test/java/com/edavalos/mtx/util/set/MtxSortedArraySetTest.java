package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxSortedArraySetTest {
    private static final double[] SORTED_VALS = {1.1, 2.2, 3.3, 4.4, 5.5};
    private static final double[] UNSORTED_VALS = {3.3, 5.5, 2.2, 1.1, 4.4};
    private static final Comparator<Double> COMPARATOR = Comparator.naturalOrder();

    private MtxSortedArraySet<Double> mtxSortedArraySet;

    @Nested
    class ConstructorTests {

        @Test
        public void testConstructor_empty() {
            mtxSortedArraySet = new MtxSortedArraySet<>(COMPARATOR);
            assertTrue(mtxSortedArraySet.isEmpty());
            assertEquals("()", mtxSortedArraySet.toString());
            assertEquals(MtxArraySet.STARTING_SIZE, mtxSortedArraySet.setContents.length);
        }

        @Test
        public void testConstructor_withSize() {
            mtxSortedArraySet = new MtxSortedArraySet<>(COMPARATOR, 5);
            assertTrue(mtxSortedArraySet.isEmpty());
            assertEquals("()", mtxSortedArraySet.toString());
            assertEquals(5, mtxSortedArraySet.setContents.length);
        }

        // @TODO: Fix this unit test
        @Test
        public void testConstructor_withContents() {
            mtxSortedArraySet = new MtxSortedArraySet<>(COMPARATOR, 10.0, 20.0, 30.0);
            assertFalse(mtxSortedArraySet.isEmpty());
            assertEquals("(10.0, 20.0, 30.0)", mtxSortedArraySet.toString());
            assertEquals(MtxArraySet.STARTING_SIZE + 3, mtxSortedArraySet.setContents.length);
        }
    }

    @Nested
    class SortTests {

        @BeforeEach
        public void setUp() {
            mtxSortedArraySet = new MtxSortedArraySet<>(COMPARATOR, 3);
            mtxSortedArraySet.nextIndex = 3;
        }

        @Test
        public void testSort_oneElement() {
            mtxSortedArraySet = new MtxSortedArraySet<>(COMPARATOR, 1);
            mtxSortedArraySet.nextIndex = 0;
            mtxSortedArraySet.setContents[0] = 1.0;

            mtxSortedArraySet.sort();
            assertArrayEquals(new Object[]{1.0}, mtxSortedArraySet.setContents);
        }

        @Test
        public void testSort_noSorting() {
            mtxSortedArraySet.setContents[0] = 1.0;
            mtxSortedArraySet.setContents[1] = 3.0;
            mtxSortedArraySet.setContents[2] = 5.0;

            mtxSortedArraySet.sort();
            assertArrayEquals(new Object[]{1.0, 3.0, 5.0}, mtxSortedArraySet.setContents);
        }

        @Test
        public void testSort_withSorting_oddNumber() {
            mtxSortedArraySet.setContents[0] = 5.0;
            mtxSortedArraySet.setContents[1] = 1.0;
            mtxSortedArraySet.setContents[2] = 3.0;

            mtxSortedArraySet.sort();
            assertArrayEquals(new Object[]{1.0, 3.0, 5.0}, mtxSortedArraySet.setContents);
        }

        @Test
        public void testSort_withSorting_evenNumber() {
            mtxSortedArraySet = new MtxSortedArraySet<>(COMPARATOR, 4);
            mtxSortedArraySet.nextIndex = 4;

            mtxSortedArraySet.setContents[0] = 8.0;
            mtxSortedArraySet.setContents[1] = 1.0;
            mtxSortedArraySet.setContents[2] = 3.0;
            mtxSortedArraySet.setContents[3] = 5.0;

            mtxSortedArraySet.sort();
            assertArrayEquals(new Object[]{1.0, 3.0, 5.0, 8.0}, mtxSortedArraySet.setContents);
        }

        @Test
        public void testSort_empty() {
            mtxSortedArraySet.nextIndex = 0;

            mtxSortedArraySet.sort();
            assertArrayEquals(new Object[]{null, null, null}, mtxSortedArraySet.setContents);
        }
    }
}
