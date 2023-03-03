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
//        try {
//            SecretKey key = MtxEncryptor.getNewSecretKey(keyString);
////            SecretKey key = MtxEncryptor.getNewSecretKey();
//            assertEquals(test, MtxEncryptor.decrypt(MtxEncryptor.encrypt(test, key), key));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        try {
            SecretKey key = MtxEncryptor.getNewSecretKey();
            byte[] initializationVector = MtxEncryptor.createInitializationVector();
            byte[] cipherText = MtxEncryptor.encrypt(test, key, initializationVector);
            String decryptedText = MtxEncryptor.decrypt(cipherText, key, initializationVector);
            assertEquals(test, decryptedText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
