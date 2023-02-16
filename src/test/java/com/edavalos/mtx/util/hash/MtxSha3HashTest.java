package com.edavalos.mtx.util.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxSha3HashTest {
    private static final String SAMPLE_SALT = "Salty";

    private MtxSha3Hash mtxSha3Hash;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        mtxSha3Hash = new MtxSha3Hash(SAMPLE_SALT);
    }

    @Test
    public void testEncrypt() {
        String samplePassword = "password";
        String samplePasswordHashed = "221982189b862f74b9fda332ef34daf35089b6629f2564701c94871810d7dc0f";

        assertEquals(samplePasswordHashed, mtxSha3Hash.encrypt(samplePassword));
    }

    @Test
    public void testEqualsEncrypted() {
        String samplePassword = "12345";

        assertTrue(mtxSha3Hash.equalsEncrypted(mtxSha3Hash.encrypt(samplePassword), samplePassword));
    }
}
