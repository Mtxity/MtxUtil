package com.edavalos.mtx.util.hash.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class MtxEncryptor {
    private static final String AES = "AES";
    // We are using a Block cipher (CBC mode)
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

    private MtxEncryptor() { }

    public static String encrypt(String stringToEncrypt, String key) {

    }

    private static SecretKey createAesKey(String keyLiteral) throws NoSuchAlgorithmException {
        SecureRandom securerandom = new SecureRandom();
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
    
    public static byte[] do_AESEncryption(
            String plainText,
            SecretKey secretKey,
            byte[] initializationVector)
            throws Exception
    {
        Cipher cipher
                = Cipher.getInstance(
                AES_CIPHER_ALGORITHM);

        IvParameterSpec ivParameterSpec
                = new IvParameterSpec(
                initializationVector);

        cipher.init(Cipher.ENCRYPT_MODE,
                secretKey,
                ivParameterSpec);

        return cipher.doFinal(
                plainText.getBytes());
    }
}
