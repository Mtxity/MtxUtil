package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxMatrixTest {
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
    public class ToStringTests {

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
    public class SetAndGetValueAtTests {
        private static final int[][] EQUIVALENT_MATRIX = new int[][]{
                new int[]{1, 2, 3},
                new int[]{4, 5, 6},
                new int[]{7, 8, 9}
        };

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

    @Nested
    public class AddSubtractTests {
        private static final int[][] OTHER_MATRIX = new int[][]{
                new int[]{9, 8, 7},
                new int[]{6, 5, 4},
                new int[]{3, 2, 1}
        };
        private static final int[][] SUM_MATRIX = new int[][]{
                new int[]{10, 10, 10},
                new int[]{10, 10, 10},
                new int[]{10, 10, 10}
        };
        private static final int[][] DIFFERENCE_MATRIX = new int[][]{
                new int[]{-8, -6, -4},
                new int[]{-2, 0, 2},
                new int[]{4, 6, 8}
        };

        @Test
        public void testAdd() {
            mtxMatrix.add(new MtxMatrix(OTHER_MATRIX));

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    assertEquals(SUM_MATRIX[r][c], mtxMatrix.getValueAt(r, c));
                }
            }
        }

        @Test
        public void testSubtract() {
            mtxMatrix.subtract(new MtxMatrix(OTHER_MATRIX));

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    assertEquals(DIFFERENCE_MATRIX[r][c], mtxMatrix.getValueAt(r, c));
                }
            }
        }

        @Test
        public void testAddAndSubtractTogether() {
            MtxMatrix m = new MtxMatrix(OTHER_MATRIX);
            MtxMatrix mPlusSum = MtxMatrix.addTogether(
                    m,
                    new MtxMatrix(SUM_MATRIX)
            );
            MtxMatrix mPlusSumMinusSum = MtxMatrix.subtractSecondFromFirst(
                    mPlusSum,
                    new MtxMatrix(SUM_MATRIX)
            );

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    assertEquals(m.getValueAt(r, c), mPlusSumMinusSum.getValueAt(r, c));
                }
            }
        }

        @Test
        public void testAddOrSubtract_differentRowsAndCols() {
            MtxMatrix moreRows = new MtxMatrix(
                    new int[]{1,2,3},
                    new int[]{4,5,6},
                    new int[]{7,8,9},
                    new int[]{10,11,12}
            );
            MtxMatrix moreCols = new MtxMatrix(
                    new int[]{1,2,3,4},
                    new int[]{4,5,6,7},
                    new int[]{7,8,9,10}
            );

            IndexOutOfBoundsException rowTooBig = assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> mtxMatrix.add(moreRows)
            );
            IndexOutOfBoundsException colTooBig = assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> mtxMatrix.subtract(moreCols)
            );

            assertEquals(String.format(MtxMatrix.ERROR_DIFFERING_ROWS, "add"), rowTooBig.getMessage());
            assertEquals(String.format(MtxMatrix.ERROR_DIFFERING_COLS, "subtract"), colTooBig.getMessage());
        }
    }

    @Test
    public void testScale() {
        int scalar = 2;
        MtxMatrix product = new MtxMatrix(
                new int[]{2, 4, 6},
                new int[]{8, 10, 12},
                new int[]{14, 16, 18}
        );

        mtxMatrix.scale(scalar);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertEquals(product.getValueAt(r, c), mtxMatrix.getValueAt(r, c));
            }
        }
    }

    @Nested
    public class EqualsTests {
        private static final MtxMatrix MATRIX_DIFFERING_ROWS = new MtxMatrix(
                new int[]{1,2,3},
                new int[]{4,5,6},
                new int[]{7,8,9},
                new int[]{10,11,12}
        );
        private static final MtxMatrix MATRIX_DIFFERING_COLS = new MtxMatrix(
                new int[]{1,2,3,4},
                new int[]{4,5,6,7},
                new int[]{7,8,9,10}
        );
        private static final MtxMatrix MATRIX_ALMOST_EQ = new MtxMatrix(
                new int[]{1,2,3},
                new int[]{4,5,7},
                new int[]{7,8,9}
        );
        private static final MtxMatrix MATRIX_EQ = new MtxMatrix(
                new int[]{1,2,3},
                new int[]{4,5,6},
                new int[]{7,8,9}
        );

        @Test
        public void testEquals_notAnMtxMatrix() {
            assertFalse(mtxMatrix.equals(null));
            assertFalse(mtxMatrix.equals(new int[3][3]));
        }

        @Test
        public void testEquals_differentRowsAndCols() {
            assertFalse(mtxMatrix.equals(MATRIX_DIFFERING_ROWS));
            assertFalse(mtxMatrix.equals(MATRIX_DIFFERING_COLS));
        }

        @Test
        public void testEquals_notEqual() {
            assertFalse(mtxMatrix.equals(MATRIX_ALMOST_EQ));
        }

        @Test
        public void testEquals_equal() {
            assertTrue(mtxMatrix.equals(MATRIX_EQ));
        }
    }
}
