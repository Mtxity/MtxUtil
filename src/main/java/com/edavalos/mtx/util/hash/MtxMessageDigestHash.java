package com.edavalos.mtx.util.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MtxMessageDigestHash extends MtxHash {
    public enum MdHashingAlgorithm {
        SHA256("SHA-256", 5),
        SHA3("SHA3-256", 2),
        MD5("MD5", 9);

        private final String name;
        private final int degree;

        MdHashingAlgorithm(String name, int degreeInThousands) {
            this.name = name;
            this.degree = degreeInThousands;
        }

        public String getName() {
            return this.name;
        }

        public int getDegree() {
            return this.degree;
        }
    }

    private int degreeToHash;
    private MessageDigest messageDigest;

    MtxMessageDigestHash(String newSalt, MdHashingAlgorithm algorithmToUse) throws NoSuchAlgorithmException {
        super(newSalt, algorithmToUse);
    }

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
