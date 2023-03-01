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

    private MtxEncryptor() { }

    public static String encrypt(String stringToEncrypt, String key) throws Exception {
        SecretKey symmetricKey = createAesKey(key);

        byte[] initializationVector = createInitializationVector();
        byte[] cipherText = do_AESEncryption(stringToEncrypt, symmetricKey, initializationVector);
        return new String(cipherText);
    }

    public static String decrypt(String stringToDecrypt, String key) throws Exception {
        SecretKey symmetricKey;
        symmetricKey = createAesKey(key);

        return do_AESDecryption(
                stringToDecrypt.getBytes(),
                symmetricKey,
                createInitializationVector());
    }

    private static SecretKey createAesKey(String keyLiteral) throws NoSuchAlgorithmException {
        SecureRandom securerandom = new SecureRandom(keyLiteral.getBytes());
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);

        keygenerator.init(256, securerandom);

        return keygenerator.generateKey();
    }

    private static byte[] createInitializationVector() {
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);

        return initializationVector;
    }

    private static byte[] do_AESEncryption(
            String plainText,
            SecretKey secretKey,
            byte[] initializationVector) throws Exception
    {
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        return cipher.doFinal(plainText.getBytes());
    }

    private static String do_AESDecryption(
            byte[] cipherText,
            SecretKey secretKey,
            byte[] initializationVector)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }
}
