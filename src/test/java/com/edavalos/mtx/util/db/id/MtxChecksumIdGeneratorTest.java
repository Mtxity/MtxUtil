package com.edavalos.mtx.util.db.id;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Nested
    public class TestGetChecksumDigit {

        @Test
        public void testGetChecksumDigit_knownExample() {
            // From example in Wikipedia page for Luhn Algorithm
            // https://en.wikipedia.org/wiki/Luhn_algorithm
            int[] payload = {7,9,9,2,7,3,9,8,7,1,0};
            int expected = 4;
            assertEquals(expected, MtxChecksumIdGenerator.getChecksumDigit(payload));
        }

        @Test
        public void testGetChecksumDigit_detectSwitchedDigits() {
            int[] value1 = {1,3,5,7,9,0};
            int[] value2 = {1,3,7,5,9,0};
            assertNotEquals(
                    MtxChecksumIdGenerator.getChecksumDigit(value1),
                    MtxChecksumIdGenerator.getChecksumDigit(value2)
            );
        }

        @Test
        public void testGetChecksumDigit_detectDuplicateDigits() {
            int[] value1 = {1,3,5,7,9,0};
            int[] value2 = {1,1,3,5,7,9,0};
            assertNotEquals(
                    MtxChecksumIdGenerator.getChecksumDigit(value1),
                    MtxChecksumIdGenerator.getChecksumDigit(value2)
            );
        }

        @Test
        public void testGetChecksumDigit_detectMissingDigits() {
            int[] value1 = {1,3,5,7,9,0};
            int[] value2 = {1,5,7,9,0};
            assertNotEquals(
                    MtxChecksumIdGenerator.getChecksumDigit(value1),
                    MtxChecksumIdGenerator.getChecksumDigit(value2)
            );
        }
    }
}
