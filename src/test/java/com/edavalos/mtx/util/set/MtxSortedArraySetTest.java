package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

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

        @Test
        public void testConstructor_withContents() {
            mtxSortedArraySet = new MtxSortedArraySet<>(COMPARATOR, 10.0, 20.0, 30.0);
            assertFalse(mtxSortedArraySet.isEmpty());
            assertEquals("(10.0, 20.0, 30.0)", mtxSortedArraySet.toString());
            assertEquals(MtxArraySet.STARTING_SIZE + 3, mtxSortedArraySet.setContents.length);
        }
    }
}
