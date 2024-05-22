package com.edavalos.mtx.util.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxArraySetTest {
    private MtxArraySet<String> mtxArraySet;

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
}
