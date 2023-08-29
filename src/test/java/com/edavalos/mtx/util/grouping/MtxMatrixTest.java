package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
