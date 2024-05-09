package com.edavalos.mtx.util.db.id;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MtxChecksumIdGeneratorTest {
    private MtxChecksumIdGenerator mtxChecksumIdGenerator;

    @Test
    public void testConstructor_lengthTooLong() {
        String expectedErrMsg = "Length of ID too long. Cannot exceed 64.";
        String actualErrMsg = assertThrows(
                IllegalArgumentException.class,
                () -> new MtxChecksumIdGenerator(70)
        ).getMessage();
        assertEquals(expectedErrMsg, actualErrMsg);
    }

    @Test
    public void testConstructor_lengthTooShort() {
        String expectedErrMsg = "Length of ID too short. Must be at least 3.";
        String actualErrMsg = assertThrows(
                IllegalArgumentException.class,
                () -> new MtxChecksumIdGenerator(2)
        ).getMessage();
        assertEquals(expectedErrMsg, actualErrMsg);
    }

    @Test
    public void testGetNextId() {
        int length = 5;
        mtxChecksumIdGenerator = new MtxChecksumIdGenerator(length);

        List<String> generatedIds = new ArrayList<>();
        for (int i = 0; i < 45; i++) {
            String newId = mtxChecksumIdGenerator.getNextId();
            generatedIds.add(newId);
            assertEquals(length, newId.length());
        }

        Set<String> comparisonSet = new HashSet<>(generatedIds);
        assertEquals(generatedIds.size(), comparisonSet.size());
    }

    @Test
    public void testGetLength() {
        int length = 5;
        mtxChecksumIdGenerator = new MtxChecksumIdGenerator(length);

        assertEquals(length, mtxChecksumIdGenerator.getIdLength());
    }

    @Test
    public void testGetTotalUniqueIds() {
        int length = 5;
        mtxChecksumIdGenerator = new MtxChecksumIdGenerator(length);

        assertEquals(BigInteger.valueOf(10).pow(length), mtxChecksumIdGenerator.getTotalUniqueIds());
    }
}
