package com.edavalos.mtx.util.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MtxMessageDigestHash extends MtxHash {
    private int degreeToHash;
    private MessageDigest messageDigest;

    MtxMessageDigestHash(String newSalt, MdHashingAlgorithm algorithmToUse) throws NoSuchAlgorithmException {
        super(newSalt, algorithmToUse);
    }

    @Override
    protected void initialize() throws NoSuchAlgorithmException {
        this.degreeToHash = super.existingAlg.getDegree();
        this.messageDigest = MessageDigest.getInstance(super.existingAlg.getName());
    }

    @Override
    public String encrypt(String secret) {
        String hash = secret + super.salt;
        for (int i = 0; i < degreeToHash * 1000; i++) {
            hash = this.hash(hash + secret + super.salt);
        }
        return hash;
    }

    private String hash(String stringToHash) {
        byte[] encodedHash = this.messageDigest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));

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
