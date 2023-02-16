package com.edavalos.mtx.util.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MtxMD5Hash extends MtxHash {
    private static final String HASHING_ALGORITHM_NAME = "MD5";
    private static final int AMOUNT_OF_TIMES_TO_HASH = 9000;

    private MessageDigest md5MessageDigest;

    MtxMD5Hash(String newSalt) throws NoSuchAlgorithmException {
        super(newSalt);
    }

    protected void initialize() throws NoSuchAlgorithmException {
        this.md5MessageDigest = MessageDigest.getInstance(HASHING_ALGORITHM_NAME);
    }

    @Override
    public String encrypt(String secret) {
        String hash = secret + super.salt;
        for (int i = 0; i < AMOUNT_OF_TIMES_TO_HASH; i++) {
            hash = this.hash(hash + secret + super.salt);
        }
        return hash;
    }

    // Based on https://www.baeldung.com/java-md5#md5-using-messagedigest-class
    private String hash(String stringToHash) {
        byte[] encodedHash = this.md5MessageDigest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte hash : encodedHash) {
            String hex = Integer.toHexString(0xff & hash);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
