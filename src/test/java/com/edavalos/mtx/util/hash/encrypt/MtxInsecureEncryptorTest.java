package com.edavalos.mtx.util.hash.encrypt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public final class MtxInsecureEncryptorTest {
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
    public void testEncrypt_oneValue() {
        assertNotEquals(LOREM_IPSUM, MtxInsecureEncryptor.encrypt(LOREM_IPSUM));
    }

    @Test
    public void testEncrypt_manyValues() {
        for (String string : STRING_TESTS) {
            assertNotEquals(string, MtxInsecureEncryptor.encrypt(string));
        }
    }

    @Test
    @DisplayName("Ensure that a string encrypted over and over will never be its original value")
    public void testEncrypt_manyTimes() {
        String originalString = "Random start string.";
        int timesToEncrypt = 200;

        String encryptedString = originalString;
        for (int i = 0; i < timesToEncrypt; i++) {
            encryptedString = MtxInsecureEncryptor.encrypt(encryptedString);
            assertNotEquals(originalString, encryptedString, "on iteration " + i);
        }
    }

    @Test
    public void testEncryptDecrypt_oneValue() {
        assertEquals(LOREM_IPSUM, MtxInsecureEncryptor.decrypt(MtxInsecureEncryptor.encrypt(LOREM_IPSUM)));
    }

    @Test
    public void testEncryptDecrypt_manyValues() {
        for (String string : STRING_TESTS) {
            assertEquals(string, MtxInsecureEncryptor.decrypt(MtxInsecureEncryptor.encrypt(string)));
        }
    }

    @Test
    @DisplayName("Ensure that if a string is encrypted n times, it will return to its original value after decrypting it n times")
    public void testEncryptDecrypt_manyTimes() {
        String originalString = "Random start string.";
        int timesToEncrypt = 200;

        String encryptedString = originalString;
        for (int i = 0; i < timesToEncrypt; i++) {
            encryptedString = MtxInsecureEncryptor.encrypt(encryptedString);
        }
        for (int j = 0; j < timesToEncrypt; j++) {
            encryptedString = MtxInsecureEncryptor.decrypt(encryptedString);
        }

        assertEquals(originalString, encryptedString);
    }
}
