package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxSortedLinkedSetTest {
    private MtxSortedLinkedSet<Integer> mtxSortedLinkedSet;

    @BeforeEach
    public void setUp() {
        mtxSortedLinkedSet = new MtxSortedLinkedSet<>(Integer.class, Comparator.naturalOrder());
    }

    @Test
    public void testConstructor_initialContents() {
        mtxSortedLinkedSet = new MtxSortedLinkedSet<>(
                Integer.class,
                Comparator.naturalOrder(),
                1,
                3,
                4,
                3
        );

        int[] values = {1, 3, 4};
        for (int value : values) {
            assertTrue(mtxSortedLinkedSet.contains(value));
        }
        assertEquals(values.length, mtxSortedLinkedSet.size());
    }

    @Test
    public void testAdd_singleElement_sorted() {
        assertTrue(mtxSortedLinkedSet.add(9));
        assertTrue(mtxSortedLinkedSet.add(12));
        assertTrue(mtxSortedLinkedSet.add(3));
        assertTrue(mtxSortedLinkedSet.add(15));
        assertTrue(mtxSortedLinkedSet.add(6));

        assertFalse(mtxSortedLinkedSet.add(3));
        assertFalse(mtxSortedLinkedSet.add(6));
        assertFalse(mtxSortedLinkedSet.add(9));

        String expected = "(3, 6, 9, 12, 15)";
        assertEquals(expected, mtxSortedLinkedSet.toString());

        assertTrue(mtxSortedLinkedSet.remove(12));
        expected = "(3, 6, 9, 15)";
        assertEquals(expected, mtxSortedLinkedSet.toString());

        assertTrue(mtxSortedLinkedSet.add(18));
        assertTrue(mtxSortedLinkedSet.add(12));
        expected = "(3, 6, 9, 12, 15, 18)";
        assertEquals(expected, mtxSortedLinkedSet.toString());
    }

    @Test
    public void testAdd_multipleElements_sorted() {
        assertTrue(mtxSortedLinkedSet.add(
                9,
                12,
                3,
                15,
                6
        ));

        String expected = "(3, 6, 9, 12, 15)";
        assertEquals(expected, mtxSortedLinkedSet.toString());

        assertTrue(mtxSortedLinkedSet.remove(12));
        expected = "(3, 6, 9, 15)";
        assertEquals(expected, mtxSortedLinkedSet.toString());

        assertTrue(mtxSortedLinkedSet.add(18, 12));
        expected = "(3, 6, 9, 12, 15, 18)";
        assertEquals(expected, mtxSortedLinkedSet.toString());

    }
}
