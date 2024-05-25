package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxMapSetTest {
    private MtxMapSet<Double> mtxMapSet;

    @Test
    public void testConstructor_empty() {
        mtxMapSet = new MtxMapSet<>();
        assertEquals(0, mtxMapSet.size());
        assertTrue(mtxMapSet.setContents.isEmpty());
    }

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

    @Test
    public void testToArray() {
        Double[] array = new Double[] {1.1, 2.2, 3.3};
        mtxMapSet = new MtxMapSet<>(array);
        assertArrayEquals(array, mtxMapSet.toArray());
    }
}
