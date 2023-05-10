package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxUuidGeneratorTest {
    private MtxUuidGenerator mtxUuidGenerator;

    @Nested
    class TestGetNextUuid {
        private final int TIMES_TO_TEST = 75; // Had to decrease this number because I have a slow computer :(

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
    public void testGetVersion() {
        for (MtxUuidGenerator.MtxUuidVersion version : MtxUuidGenerator.MtxUuidVersion.values()) {
            mtxUuidGenerator = new MtxUuidGenerator(version);
            assertEquals(version, mtxUuidGenerator.getVersion());
        }
    }

    @Test
    public void testGetByteArrayFromHashedNamespace_NoAlgFound() {
        String nonexistentHashingAlgorithm = "SHB";
        assertThrows(IllegalArgumentException.class, () -> MtxUuidGenerator.getByteArrayFromHashedNamespace(nonexistentHashingAlgorithm));
    }

    @Test
    public void testSetVariantAndVersion() {
        byte[] testByteArray = new byte[16];
        testByteArray[6] &= 0x0f;
        testByteArray[6] |= 0x60;
        testByteArray[8] &= 0x0f;
        testByteArray[8] |= 0x20;
        assertFalse(MtxUuidGenerator.isUuidValid(testByteArray));

        MtxUuidGenerator.setVariantAndVersion(testByteArray, MtxUuidGenerator.MtxUuidVersion.RANDOMLY_GENERATED);
        assertTrue(MtxUuidGenerator.isUuidValid(testByteArray));
    }
}
