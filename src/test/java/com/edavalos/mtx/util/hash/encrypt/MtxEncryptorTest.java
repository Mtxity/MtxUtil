package com.edavalos.mtx.util.hash.encrypt;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public final class MtxEncryptorTest {

    @Test
    public void test_poc() {
        String test = "test test test t";
        String keyString = "keyz";
        try {
            MtxEncryptionKey key = MtxEncryptor.generateNewKey(keyString);
            byte[] cipherText = MtxEncryptor.encrypt(test, key);
            String decryptedText = MtxEncryptor.decrypt(cipherText, key);
            assertEquals(test, decryptedText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
