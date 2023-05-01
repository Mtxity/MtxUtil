package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxBitColumnTest {
    private MtxBitColumn mtxBitColumn;

    @BeforeEach
    public void setUp() {
        mtxBitColumn = new MtxBitColumn();
    }

    @Test
    public void testSetAndGetBit() {
        for (int i = 0; i < 31; i++) {
            assertFalse(mtxBitColumn.getBit(i));
        }

        int[] bitsToTest = new int[] {3, 6, 10, 17, 24, 25, 30};
        for (int bitToTest : bitsToTest) {
            assertFalse(mtxBitColumn.setBit(bitToTest, true));
        }

        for (int i = 0; i < 31; i++) {
            if (Arrays.binarySearch(bitsToTest, i) < 0) {
                assertFalse(mtxBitColumn.getBit(i));
            } else {
                assertTrue(mtxBitColumn.getBit(i));
            }
        }

        bitsToTest = new int[] {3, 6, 10, 17, 25, 30};
        assertTrue(mtxBitColumn.setBit(24, false));

        for (int i = 0; i < 31; i++) {
            if (Arrays.binarySearch(bitsToTest, i) < 0) {
                assertFalse(mtxBitColumn.getBit(i));
            } else {
                assertTrue(mtxBitColumn.getBit(i));
            }
        }
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
}
