package com.edavalos.mtx.util.hash.encrypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public final class MtxEncryptorTest {

    @Test
    public void test_poc() {
        String test = "test test test t";
        String key = "keyz";
        try {
            assertEquals(test, MtxEncryptor.decrypt(MtxEncryptor.encrypt(test, key), key));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
