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

    @Test
    public void testPow() {
        MtxMatrix m = new MtxMatrix(
                new int[]{1, 2},
                new int[]{3, 4}
        );
        MtxMatrix expectedPow0 = new MtxMatrix(
                new int[]{1, 0},
                new int[]{0, 1}
        );
        assertEquals(expectedPow0, MtxMatrix.pow(m, 0));
        assertEquals(m, MtxMatrix.pow(m, 1));

        MtxMatrix expectedPow2 = new MtxMatrix(
                new int[]{7, 10},
                new int[]{15, 22}
        );
        assertEquals(expectedPow2, MtxMatrix.pow(m, 2));
    }

    @Test
    public void testPow_negativePower_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> mtxMatrix.pow(-1));
        assertThrows(IllegalArgumentException.class, () -> MtxMatrix.pow(mtxMatrix, -1));
    }

    @Test
    public void testPow_nonSquare_throwsException() {
        MtxMatrix nonSquare = new MtxMatrix(2, 3);
        assertThrows(IllegalArgumentException.class, () -> nonSquare.pow(2));
        assertThrows(IllegalArgumentException.class, () -> MtxMatrix.pow(nonSquare, 2));
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


    @Test
    public void testCopy() {
        MtxMatrix copy = mtxMatrix.copy();
        assertEquals(mtxMatrix, copy);
        copy.setValueAt(0, 0, 99);
        assertFalse(mtxMatrix.equals(copy));

        MtxMatrix m = new MtxMatrix(
                new int[]{1, 2, 3},
                new int[]{4, 5, 6}
        );
        MtxMatrix expected = new MtxMatrix(
                new int[]{1, 2, 3},
                new int[]{4, 5, 6}
        );
        assertEquals(expected, m.copy());
    }

    @Test
    public void testTranspose() {
        MtxMatrix m = new MtxMatrix(
                new int[]{1, 2, 3},
                new int[]{4, 5, 6}
        );
        MtxMatrix expectedT = new MtxMatrix(
                new int[]{1, 4},
                new int[]{2, 5},
                new int[]{3, 6}
        );
        assertEquals(expectedT, m.transpose());
        assertEquals(expectedT, MtxMatrix.transpose(m));
    }

    @Test
    public void testTranspose_nonSquare() {
        MtxMatrix m = new MtxMatrix(
                new int[]{1, 2, 3},
                new int[]{4, 5, 6}
        );
        MtxMatrix t = m.transpose();
        assertEquals(1, t.getValueAt(0, 0));
        assertEquals(4, t.getValueAt(0, 1));
        assertEquals(2, t.getValueAt(1, 0));
        assertEquals(5, t.getValueAt(1, 1));
        assertEquals(3, t.getValueAt(2, 0));
        assertEquals(6, t.getValueAt(2, 1));
        assertEquals(t, MtxMatrix.transpose(m));
    }

    @Test
    public void testDotProduct() {
        MtxMatrix m1 = new MtxMatrix(
                new int[]{1,2,3},
                new int[]{4,5,6}
        );
        MtxMatrix m2 = new MtxMatrix(
                new int[]{7,8},
                new int[]{9,10},
                new int[]{11,12}
        );

        MtxMatrix expectedProduct = new MtxMatrix(
                new int[]{58,64},
                new int[]{139,154}
        );
        MtxMatrix realProduct = MtxMatrix.dotProduct(m1, m2);

        assertEquals(expectedProduct, realProduct);
    }

    @Test
    public void testDotProduct_differingRowsAndColumns() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> MtxMatrix.dotProduct(
                new MtxMatrix(2, 4),
                new MtxMatrix(3, 3)
        ));
        assertEquals(String.format(MtxMatrix.ERROR_DIFFERING_ROWS_COLS, 4, 3), iae.getMessage());
    }

    @Test
    public void testGetIdentityMatrix() {
        MtxMatrix i1 = new MtxMatrix(
                new int[]{1}
        );
        MtxMatrix i2 = new MtxMatrix(
                new int[]{1,0},
                new int[]{0,1}
        );
        MtxMatrix i3 = new MtxMatrix(
                new int[]{1,0,0},
                new int[]{0,1,0},
                new int[]{0,0,1}
        );
        MtxMatrix i4 = new MtxMatrix(
                new int[]{1,0,0,0},
                new int[]{0,1,0,0},
                new int[]{0,0,1,0},
                new int[]{0,0,0,1}
        );

        // test new results
        assertEquals(i1, MtxMatrix.getIdentityMatrix(1));
        assertEquals(i2, MtxMatrix.getIdentityMatrix(2));
        assertEquals(i3, MtxMatrix.getIdentityMatrix(3));
        assertEquals(i4, MtxMatrix.getIdentityMatrix(4));

        // test cached results
        assertEquals(i1, MtxMatrix.getIdentityMatrix(1));
        assertEquals(i2, MtxMatrix.getIdentityMatrix(2));
        assertEquals(i3, MtxMatrix.getIdentityMatrix(3));
        assertEquals(i4, MtxMatrix.getIdentityMatrix(4));
    }

    @Test
    public void testIsSquare_true() {
        assertTrue(mtxMatrix.isSquare());
        assertTrue(new MtxMatrix(2, 2).isSquare());
    }

    @Test
    public void testIsSquare_false() {
        assertFalse(new MtxMatrix(2, 3).isSquare());
    }

    @Test
    public void testIsSymmetric() {
        MtxMatrix sym = new MtxMatrix(
                new int[]{1, 2, 3},
                new int[]{2, 5, 6},
                new int[]{3, 6, 9}
        );
        assertTrue(sym.isSymmetric());
        assertFalse(mtxMatrix.isSymmetric());
    }

    @Test
    public void testIsSymmetric_notSquare() {
        MtxMatrix nonSym = new MtxMatrix(
                new int[]{1, 2, 3}
        );
        assertFalse(nonSym.isSymmetric());
    }

    @Test
    public void testIsIdentity() {
        MtxMatrix id = MtxMatrix.getIdentityMatrix(3);
        assertTrue(id.isIdentity());
        assertFalse(mtxMatrix.isIdentity());
        assertFalse(new MtxMatrix(0, 0).isIdentity());
        assertFalse(new MtxMatrix(0, 1).isIdentity());
    }

    @Test
    public void testIsZero() {
        MtxMatrix zero = new MtxMatrix(3, 3);
        assertTrue(zero.isZero());
        assertFalse(mtxMatrix.isZero());
    }

    @Test
    public void testIsDiagonal() {
        MtxMatrix diag = new MtxMatrix(
                new int[]{1, 0, 0},
                new int[]{0, 5, 0},
                new int[]{0, 0, 9}
        );
        assertTrue(diag.isDiagonal());
        assertFalse(mtxMatrix.isDiagonal());
        assertFalse(new MtxMatrix(0, 0).isDiagonal());
        assertFalse(new MtxMatrix(0, 1).isDiagonal());
    }

    @Test
    public void testTrace() {
        assertEquals(15, mtxMatrix.trace());
        assertEquals(15, MtxMatrix.trace(mtxMatrix));
        assertEquals(5, new MtxMatrix(
                new int[]{5, 2},
                new int[]{3, 0}
        ).trace());
    }

    @Test
    public void testTrace_nonSquare() {
        MtxMatrix nonSquare = new MtxMatrix(2, 3);
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, nonSquare::trace);
        assertEquals(MtxMatrix.ERROR_NOT_SQUARE, iae.getMessage());

        IllegalArgumentException iaeStatic = assertThrows(IllegalArgumentException.class, () -> MtxMatrix.trace(nonSquare));
        assertEquals(MtxMatrix.ERROR_NOT_SQUARE, iaeStatic.getMessage());
    }

    @Test
    public void testMinor() {
        MtxMatrix matrix = new MtxMatrix(3, 3);
        int[][] values = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                matrix.setValueAt(r, c, values[r][c]);
            }
        }
        MtxMatrix result = matrix.minor(1, 1);

        assertEquals(1, result.getValueAt(0, 0));
        assertEquals(3, result.getValueAt(0, 1));
        assertEquals(7, result.getValueAt(1, 0));
        assertEquals(9, result.getValueAt(1, 1));
    }

    @Test
    public void testDeterminant() {
        assertEquals(1, new MtxMatrix().determinant());
        assertEquals(1, new MtxMatrix(new int[]{1}).determinant());
        assertEquals(-2, new MtxMatrix(
                new int[]{1, 2},
                new int[]{3, 4}
        ).determinant());
        assertEquals(-306, new MtxMatrix(
                new int[]{6, 1, 1},
                new int[]{4, -2, 5},
                new int[]{2, 8, 7}
        ).determinant());
        assertEquals(-306, MtxMatrix.determinant(new MtxMatrix(
                new int[]{6, 1, 1},
                new int[]{4, -2, 5},
                new int[]{2, 8, 7}
        )));
    }

    @Test
    public void testDeterminant_nonSquare() {
        MtxMatrix nonSquare = new MtxMatrix(2, 3);
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, nonSquare::determinant);
        assertEquals(MtxMatrix.ERROR_NOT_SQUARE, iae.getMessage());

        IllegalArgumentException iaeStatic = assertThrows(IllegalArgumentException.class, () -> MtxMatrix.determinant(nonSquare));
        assertEquals(MtxMatrix.ERROR_NOT_SQUARE, iaeStatic.getMessage());
    }
}
