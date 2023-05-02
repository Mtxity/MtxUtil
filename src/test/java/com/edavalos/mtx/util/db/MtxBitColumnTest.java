package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxBitColumnTest {
    private MtxBitColumn mtxBitColumn;

    @BeforeEach
    public void setUp() {
        mtxBitColumn = new MtxBitColumn();
    }

    @Test
    public void testSetAndGetBit() {
        for (int i = 0; i < 32; i++) {
            assertFalse(mtxBitColumn.getBit(i));
        }

        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            assertFalse(mtxBitColumn.setBit(bitToTest, true));
        }

        for (int i = 0; i < 32; i++) {
            if (Arrays.binarySearch(bitsToTest, i) < 0) {
                assertFalse(mtxBitColumn.getBit(i));
            } else {
                assertTrue(mtxBitColumn.getBit(i));
            }
        }

        bitsToTest = new int[] {3, 6, 10, 17, 25, 30};
        assertTrue(mtxBitColumn.setBit(24, false));

        for (int i = 0; i < 32; i++) {
            if (Arrays.binarySearch(bitsToTest, i) < 0) {
                assertFalse(mtxBitColumn.getBit(i));
            } else {
                assertTrue(mtxBitColumn.getBit(i));
            }
        }

        assertFalse(mtxBitColumn.setBit(31, true));
        assertTrue(mtxBitColumn.getBit(31));
        assertTrue(mtxBitColumn.getBit(30));
        assertFalse(mtxBitColumn.getBit(29));
    }

    @Test
    public void testConvertToAndFromBooleanArray() {
        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            assertFalse(mtxBitColumn.setBit(bitToTest, true));
        }

        assertEquals(
                mtxBitColumn.getAllBits(),
                mtxBitColumn.convertBooleanArrayToInt(
                        mtxBitColumn.convertIntToBooleanArray(
                                mtxBitColumn.getAllBits()
                        )
                )
        );

        assertEquals(
                mtxBitColumn.getAllBits(),
                mtxBitColumn.convertBooleanArrayToInt(
                        mtxBitColumn.convertIntToBooleanArray(
                                mtxBitColumn.convertBooleanArrayToInt(
                                        mtxBitColumn.convertIntToBooleanArray(
                                                mtxBitColumn.getAllBits()
                                        )
                                )
                        )
                )
        );
    }

    @Test
    public void testConvertToAndFromBooleanArray_fromConstructor() {
        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            assertFalse(mtxBitColumn.setBit(bitToTest, true));
        }
        int expectedBits = mtxBitColumn.getAllBits();
        mtxBitColumn = new MtxBitColumn(mtxBitColumn.convertIntToBooleanArray(expectedBits));
        assertEquals(expectedBits, mtxBitColumn.getAllBits());
    }

    @Test
    public void testGetBit_columnOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxBitColumn.getBit(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxBitColumn.getBit(32));
    }

    @Test
    public void testSetBit_columnOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxBitColumn.setBit(-1, true));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxBitColumn.setBit(32, true));
    }

    @Test
    public void testConvertBooleanArrayToInt() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            boolean[] booleans = new boolean[5];
            mtxBitColumn.convertBooleanArrayToInt(booleans);
        });
    }

    @Test
    public void testGetAllBitsAsArray() {
        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            assertFalse(mtxBitColumn.setBit(bitToTest, true));
        }

        boolean[] bitArray = mtxBitColumn.getAllBitsAsArray();
        assertEquals(32, bitArray.length);

        for (int i = 0; i < bitArray.length; i++) {
            assertEquals(mtxBitColumn.getBit(i), bitArray[i]);
        }
    }

    @Test
    public void testSetAllBits_fromInt() {
        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            mtxBitColumn.setBit(bitToTest, true);
        }
        int value = mtxBitColumn.getAllBits();
        MtxBitColumn otherBitCol = new MtxBitColumn();
        otherBitCol.setAllBits(value);

        assertEquals(mtxBitColumn.getAllBits(), otherBitCol.getAllBits());
        assertArrayEquals(mtxBitColumn.getAllBitsAsArray(), otherBitCol.getAllBitsAsArray());
    }

    @Test
    public void testSetAllBits_fromArray() {
        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            mtxBitColumn.setBit(bitToTest, true);
        }
        boolean[] value = mtxBitColumn.getAllBitsAsArray();
        MtxBitColumn otherBitCol = new MtxBitColumn();
        otherBitCol.setAllBits(value);

        assertEquals(mtxBitColumn.getAllBits(), otherBitCol.getAllBits());
        assertArrayEquals(mtxBitColumn.getAllBitsAsArray(), otherBitCol.getAllBitsAsArray());
    }

    @Test
    public void testClear() {
        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            mtxBitColumn.setBit(bitToTest, true);
        }

        mtxBitColumn.clear();
        for (int i = 0; i < 32; i++) {
            assertFalse(mtxBitColumn.getBit(i));
        }
        assertEquals(0, mtxBitColumn.getAllBits());
    }
}
