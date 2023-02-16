package com.edavalos.mtx.util.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MtxSha256Hash extends MtxHash {
    private static final String HASHING_ALGORITHM_NAME = "SHA-256";
    private static final int AMOUNT_OF_TIMES_TO_HASH = 5000;

    private MessageDigest sha256MessageDigest;

    MtxSha256Hash(String newSalt) throws NoSuchAlgorithmException {
        super(newSalt);
    }

    protected void initialize() throws NoSuchAlgorithmException {
        this.sha256MessageDigest = MessageDigest.getInstance(HASHING_ALGORITHM_NAME);
    }

    @Override
    public String encrypt(String secret) {
        String hash = secret + super.salt;
        for (int i = 0; i < AMOUNT_OF_TIMES_TO_HASH; i++) {
            hash = this.hash(hash + secret + super.salt);
        }
        return hash;
    }

    // Based on https://www.baeldung.com/sha-256-hashing-java#message-digest
    private String hash(String stringToHash) {
        byte[] encodedHash = this.sha256MessageDigest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));

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
