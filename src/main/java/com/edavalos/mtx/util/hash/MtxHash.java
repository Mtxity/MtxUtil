package com.edavalos.mtx.util.hash;

import java.security.NoSuchAlgorithmException;

public abstract class MtxHash {
    public enum MdHashingAlgorithm {
        SHA256("SHA-256", 5),
        SHA3("SHA3-256", 2),
        MD5("MD5", 9),
        OTHER(null, 20);

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

    protected final MtxMessageDigestHash.MdHashingAlgorithm existingAlg;
    protected final String salt;

    MtxHash(String newSalt, MtxMessageDigestHash.MdHashingAlgorithm algorithm) throws NoSuchAlgorithmException {
        this.existingAlg = algorithm;
        this.initialize();
        this.salt = this.encrypt(newSalt);
    }

    protected abstract void initialize() throws NoSuchAlgorithmException;

    public abstract String encrypt(String secret);

    public boolean equalsEncrypted(String hash, String secret) {
        return this.encrypt(secret).equals(hash);
    }
}
