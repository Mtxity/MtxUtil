package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxUuidGeneratorTest {
    private MtxUuidGenerator mtxUuidGenerator;

    @Nested
    class TestGetNextUuid {
        private static final int TIMES_TO_TEST = 75; // Had to decrease this number because I have a slow computer :(

        @Test
        public void testGetNextUuid_timeBased() {
            mtxUuidGenerator = new MtxUuidGenerator(MtxUuidGenerator.MtxUuidVersion.TIME_BASED);

            List<UUID> generatedUUIDs = new ArrayList<>();
            for (int i = 0; i < TIMES_TO_TEST; i++) {
                generatedUUIDs.add(mtxUuidGenerator.getNextUuid());
            }

            Set<UUID> comparisonSet = new HashSet<>(generatedUUIDs);
            assertEquals(generatedUUIDs.size(), comparisonSet.size());
        }

        @Test
        public void testGetNextUuid_nameBased_MD5() {
            mtxUuidGenerator = new MtxUuidGenerator(MtxUuidGenerator.MtxUuidVersion.NAME_BASED_MD5);

            List<UUID> generatedUUIDs = new ArrayList<>();
            for (int i = 0; i < TIMES_TO_TEST; i++) {
                generatedUUIDs.add(mtxUuidGenerator.getNextUuid());
            }

            Set<UUID> comparisonSet = new HashSet<>(generatedUUIDs);
            assertEquals(generatedUUIDs.size(), comparisonSet.size());
        }

        @Test
        public void testGetNextUuid_nameBased_SHA1() {
            mtxUuidGenerator = new MtxUuidGenerator(MtxUuidGenerator.MtxUuidVersion.NAME_BASED_SHA1);

            List<UUID> generatedUUIDs = new ArrayList<>();
            for (int i = 0; i < TIMES_TO_TEST; i++) {
                generatedUUIDs.add(mtxUuidGenerator.getNextUuid());
            }

            Set<UUID> comparisonSet = new HashSet<>(generatedUUIDs);
            assertEquals(generatedUUIDs.size(), comparisonSet.size());
        }

        @Test
        public void testGetNextUuid_random() {
            mtxUuidGenerator = new MtxUuidGenerator();

            List<UUID> generatedUUIDs = new ArrayList<>();
            for (int i = 0; i < TIMES_TO_TEST; i++) {
                generatedUUIDs.add(mtxUuidGenerator.getNextUuid());
            }

            Set<UUID> comparisonSet = new HashSet<>(generatedUUIDs);
            assertEquals(generatedUUIDs.size(), comparisonSet.size());
        }
    }

    @Test
    public void testGetNextId() {
        mtxUuidGenerator = new MtxUuidGenerator(MtxUuidGenerator.MtxUuidVersion.RANDOMLY_GENERATED);

        List<String> generatedIds = new ArrayList<>();
        for (int i = 0; i < 45; i++) {
            String newId = mtxUuidGenerator.getNextId();
            generatedIds.add(newId);
            assertDoesNotThrow(() -> UUID.fromString(newId));
        }

        Set<String> comparisonSet = new HashSet<>(generatedIds);
        assertEquals(generatedIds.size(), comparisonSet.size());
    }

    @Test
    public void testGetVersion() {
        for (MtxUuidGenerator.MtxUuidVersion version : MtxUuidGenerator.MtxUuidVersion.values()) {
            mtxUuidGenerator = new MtxUuidGenerator(version);
            assertEquals(version, mtxUuidGenerator.getVersion());
        }
    }

    @Test
    public void testGetByteArrayFromHashedNamespace_NoAlgFound() {
        String nonexistentHashingAlgorithm = "SHB";
        assertThrows(IllegalArgumentException.class, () -> MtxUuidGenerator.getByteArrayFromHashedNamespace(
                nonexistentHashingAlgorithm
        ));
    }

    @Test
    public void testSetVariantAndVersion() {
        byte[] testByteArray = new byte[16];
        testByteArray[6] &= 0x0f;
        testByteArray[6] |= 0x60;
        testByteArray[8] &= 0x3f;
        testByteArray[8] |= 0x20;
        assertFalse(MtxUuidGenerator.isUuidValid(testByteArray));

        MtxUuidGenerator.setVariantAndVersion(testByteArray, MtxUuidGenerator.MtxUuidVersion.RANDOMLY_GENERATED);
        assertTrue(MtxUuidGenerator.isUuidValid(testByteArray));
    }

    @Nested
    class TestIsUuidValid {
        private static final byte[] BYTE_ARRAY_WRONG_LENGTH = new byte[32];
        private static final byte[] BYTE_ARRAY_WRONG_VERSION = new byte[16];
        static {
            BYTE_ARRAY_WRONG_VERSION[6] &= 0x0f;
            BYTE_ARRAY_WRONG_VERSION[6] |= 0x60;
        }

        @Test
        public void testIsUuidValid_wrongLength() {
            assertFalse(MtxUuidGenerator.isUuidValid(BYTE_ARRAY_WRONG_LENGTH));
        }

        @Test
        public void testIsUuidValid_wrongVersion() {
            assertFalse(MtxUuidGenerator.isUuidValid(BYTE_ARRAY_WRONG_VERSION));
        }
    }
}
