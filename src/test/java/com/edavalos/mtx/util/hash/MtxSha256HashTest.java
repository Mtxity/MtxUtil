package com.edavalos.mtx.util.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxSha256HashTest {
    private static final String SAMPLE_SALT = "Salty";

    private MtxSha256Hash mtxSha256Hash;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        mtxSha256Hash = new MtxSha256Hash(SAMPLE_SALT);
    }

    @Test
    public void testEncrypt() {
        String samplePassword = "password";
        String samplePasswordHashed = "5b4987b0233c1643ea1e95436f2d802ee4b1f99394758f6b25e153bfbe6ca52e";

        assertEquals(samplePasswordHashed, mtxSha256Hash.encrypt(samplePassword));
    }

    @Test
    public void testEqualsEncrypted() {
        String samplePassword = "12345";

        assertTrue(mtxSha256Hash.equalsEncrypted(mtxSha256Hash.encrypt(samplePassword), samplePassword));
    }
}
