package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.Test;

public final class MtxIdGeneratorTest {
    /**
     * NOTES:
     * Format | Number of unique
     */
    private MtxIdGenerator mtxIdGenerator;

    @Test
    public void testGetNextId() {
        mtxIdGenerator = new MtxIdGenerator("NLN");
        for (int i = 0; i < 2600; i++) {
            System.out.println(mtxIdGenerator.getNextId());
        }

        System.out.println("--+--");

        mtxIdGenerator = new MtxIdGenerator("NALN");
        for (int i = 0; i < 93600; i++) {
            System.out.println(mtxIdGenerator.getNextId());
        }
    }
}
