package com.edavalos.mtx.util.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxMD5HashTest {
    private static final String SAMPLE_SALT = "Salty";

    private MtxMD5Hash mtxMd5Hash;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        mtxMd5Hash = new MtxMD5Hash(SAMPLE_SALT);
    }

    @Test
    public void testEncrypt() {
        String samplePassword = "password";
        String samplePasswordHashed = "630b9d11b18049dee5359ad75898cd74";

        assertEquals(samplePasswordHashed, mtxMd5Hash.encrypt(samplePassword));
    }

    @Test
    public void testEqualsEncrypted() {
        String samplePassword = "12345";

        assertTrue(mtxMd5Hash.equalsEncrypted(mtxMd5Hash.encrypt(samplePassword), samplePassword));
    }
}
