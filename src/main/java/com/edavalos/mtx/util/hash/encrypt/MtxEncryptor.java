package com.edavalos.mtx.util.hash.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// Based on: https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/
public final class MtxEncryptor {
    private static final String AES = "AES";
    // We are using a Block cipher (CBC mode)
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final int BITS = 256;

    private MtxEncryptor() { }

    public static byte[] encrypt(String stringToEncrypt, SecretKey key, byte[] initVec) throws Exception {
        return do_AESEncryption(stringToEncrypt, key, initVec);
    }

    public static String decrypt(byte[] stringToDecrypt, SecretKey key, byte[] initVec) throws Exception {
        return do_AESDecryption(
                stringToDecrypt,
                key,
                initVec
        );
    }

    public static SecretKey getNewSecretKey(String seed) throws NoSuchAlgorithmException {
        return createAesKey(seed);
    }

    public static SecretKey getNewSecretKey() throws NoSuchAlgorithmException {
        return createAesKey();
    }

    private static SecretKey createAesKey(String keySeed) throws NoSuchAlgorithmException {
        SecureRandom securerandom = new SecureRandom(keySeed.getBytes());
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);

        keygenerator.init(BITS, securerandom);

        return keygenerator.generateKey();
    }

    private static SecretKey createAesKey() throws NoSuchAlgorithmException {
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);

        keygenerator.init(BITS, securerandom);

        return keygenerator.generateKey();
    }

    public static byte[] createInitializationVector() {
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);

        return initializationVector;
    }

    private static byte[] do_AESEncryption(String plainText, SecretKey secretKey, byte[] initializationVector) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        return cipher.doFinal(plainText.getBytes());
    }

    private static String do_AESDecryption(byte[] cipherText, SecretKey secretKey, byte[] initializationVector) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }
}
