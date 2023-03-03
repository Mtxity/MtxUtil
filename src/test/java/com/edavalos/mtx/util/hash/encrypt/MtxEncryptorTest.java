package com.edavalos.mtx.util.hash.encrypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxEncryptorTest {
    private static final String SEED = "Random Value";
    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private static final String[] STRING_TESTS = {
            "Str",
            "Stri",
            "Strin",
            "String",
            "String ",
            "String t",
            "String to",
            "String to ",
            "String to t",
            "String to te",
            "String to tes",
            "String to test",
            "String to test ",
            "String to test a",
            "String to test an",
            "String to test and",
            "String to test and ",
            "String to test and v",
            "String to test and va",
            "String to test and val",
            "String to test and vali",
            "String to test and valid",
            "String to test and valida",
            "String to test and validat",
            "String to test and validate",
    };

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

    @Test
    public void testEncrypt_oneValue_noSeed() {
        try {
            MtxEncryptionKey key = MtxEncryptor.generateNewKey();
            byte[] encrypted = MtxEncryptor.encrypt(LOREM_IPSUM, key);
            String decrypted = MtxEncryptor.decrypt(encrypted, key);
            assertNotEquals(LOREM_IPSUM, new String(encrypted));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncryptDecrypt_oneValue_noSeed() {
        try {
            MtxEncryptionKey key = MtxEncryptor.generateNewKey();
            byte[] encrypted = MtxEncryptor.encrypt(LOREM_IPSUM, key);
            String decrypted = MtxEncryptor.decrypt(encrypted, key);
            assertEquals(LOREM_IPSUM, decrypted);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncrypt_oneValue_hasSeed() {
        try {
            MtxEncryptionKey key = MtxEncryptor.generateNewKey(SEED);
            byte[] encrypted = MtxEncryptor.encrypt(LOREM_IPSUM, key);
            String decrypted = MtxEncryptor.decrypt(encrypted, key);
            assertNotEquals(LOREM_IPSUM, new String(encrypted));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncryptDecrypt_oneValue_hasSeed() {
        try {
            MtxEncryptionKey key = MtxEncryptor.generateNewKey(SEED);
            byte[] encrypted = MtxEncryptor.encrypt(LOREM_IPSUM, key);
            String decrypted = MtxEncryptor.decrypt(encrypted, key);
            assertEquals(LOREM_IPSUM, decrypted);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncryptDecrypt_manyValues_noSeed() {
        try {
            MtxEncryptionKey key;
            for (String test : STRING_TESTS) {
                key = MtxEncryptor.generateNewKey();
                byte[] encrypted = MtxEncryptor.encrypt(test, key);
                String decrypted = MtxEncryptor.decrypt(encrypted, key);
                assertEquals(test, decrypted);
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncryptDecrypt_manyValues_hasSeed() {
        try {
            MtxEncryptionKey key;
            for (String test : STRING_TESTS) {
                key = MtxEncryptor.generateNewKey(SEED);
                byte[] encrypted = MtxEncryptor.encrypt(test, key);
                String decrypted = MtxEncryptor.decrypt(encrypted, key);
                assertEquals(test, decrypted);
            }
        } catch (Exception e) {
            fail();
        }
    }
}
