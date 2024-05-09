package com.edavalos.mtx.util.db.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxChecksumIterativeIdGeneratorTest {
    private MtxChecksumIterativeIdGenerator mtxChecksumIterativeIdGenerator;

    @Test
    public void testGetInitialDigits() {
        mtxChecksumIterativeIdGenerator = new MtxChecksumIterativeIdGenerator(3);

        int i = 0;
        while (i <= 99) {
            int[] digits = mtxChecksumIterativeIdGenerator.getInitialDigits(2);
            int nextVal = (digits[0] * 10) + digits[1];
            assertEquals(i, nextVal);
            i ++;
        }

        int[] digits = mtxChecksumIterativeIdGenerator.getInitialDigits(2);
        int nextVal = (digits[0] * 10) + digits[1];
        assertEquals(0, nextVal);
    }
}
