package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MtxMatrixTest {
    private static final int[][] EQUIVALENT_MATRIX = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 9}
    };

    private MtxMatrix mtxMatrix;

    @BeforeEach
    public void setUp() {
        mtxMatrix = new MtxMatrix(
                new int[]{1,2,3},
                new int[]{4,5,6},
                new int[]{7,8,9}
        );
    }

    @Nested
    public class TestToString {

        @Test
        public void testToString_sameLengths() {
            String expected = "[ [1, 2, 3],\n" +
                              "  [4, 5, 6],\n" +
                              "  [7, 8, 9] ]";
            assertEquals(expected, mtxMatrix.toString());
        }

        @Test
        public void testToString_differentLengths() {
            String expected = "[ [1,   25,  3 ],\n" +
                              "  [487, 5,   62],\n" +
                              "  [70,  812, 9 ] ]";
            assertEquals(expected, new MtxMatrix(
                    new int[]{1,25,3},
                    new int[]{487,5,62},
                    new int[]{70,812,9}
            ).toString());
        }

        @Test
        public void testToString_negatives() {
            String expected = "[ [1,   -25, 3 ],\n" +
                              "  [487, 5,   62],\n" +
                              "  [-70, 812, -9] ]";
            assertEquals(expected, new MtxMatrix(
                    new int[]{1,-25,3},
                    new int[]{487,5,62},
                    new int[]{-70,812,-9}
            ).toString());
        }

        @Test
        public void testToString_empty() {
            String expected = "[ [0, 0, 0],\n" +
                              "  [0, 0, 0],\n" +
                              "  [0, 0, 0] ]";
            assertEquals(expected, new MtxMatrix(3, 3).toString());
        }

        @Test
        public void testToString_blank() {
            String expected = "[ ]";
            assertEquals(expected, new MtxMatrix(0, 0).toString());
        }
    }

    @Nested
    public class TestSetAndGetValueAt {

        @Test
        public void testGetValueAt() {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    assertEquals(EQUIVALENT_MATRIX[r][c], mtxMatrix.getValueAt(r, c));
                }
            }
        }

        @Test
        public void testSetValueAt() {
            MtxMatrix otherMatrix = new MtxMatrix(3, 3);
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    otherMatrix.setValueAt(r, c, EQUIVALENT_MATRIX[r][c]);
                }
            }

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    assertEquals(EQUIVALENT_MATRIX[r][c], otherMatrix.getValueAt(r, c));
                }
            }
        }

        @Test
        public void testValidateIndices_negativeIndex() {
            IndexOutOfBoundsException negativeRowEx = assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> mtxMatrix.getValueAt(-1, 1)
            );
            IndexOutOfBoundsException negativeColEx = assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> mtxMatrix.getValueAt(1, -1)
            );

            assertEquals(MtxMatrix.ERROR_NEGATIVE_IDX, negativeRowEx.getMessage());
            assertEquals(MtxMatrix.ERROR_NEGATIVE_IDX, negativeColEx.getMessage());
        }

        @Test
        public void testValidateIndices_outOfBoundsIndex() {
            IndexOutOfBoundsException rowTooBig = assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> mtxMatrix.getValueAt(3, 1)
            );
            IndexOutOfBoundsException colTooBig = assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> mtxMatrix.getValueAt(1, 3)
            );

            assertEquals(MtxMatrix.ERROR_ROW_TOO_BIG, rowTooBig.getMessage());
            assertEquals(MtxMatrix.ERROR_COL_TOO_BIG, colTooBig.getMessage());
        }
    }
}
