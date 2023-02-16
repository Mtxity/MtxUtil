package com.edavalos.mtx.util.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxHashmapHashTest {
    private static final String SAMPLE_SALT = "Salty";

    private MtxHashmapHash mtxHashmapHash;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        mtxHashmapHash = new MtxHashmapHash(SAMPLE_SALT);
    }

    @Test
    public void testEncrypt() {
        String samplePassword = "password";
        String samplePasswordHashed = "1366985316";

        assertEquals(samplePasswordHashed, mtxHashmapHash.encrypt(samplePassword));
    }

    @Test
    public void testEqualsEncrypted() {
        String samplePassword = "12345";

        assertTrue(mtxHashmapHash.equalsEncrypted(mtxHashmapHash.encrypt(samplePassword), samplePassword));
    }
}
