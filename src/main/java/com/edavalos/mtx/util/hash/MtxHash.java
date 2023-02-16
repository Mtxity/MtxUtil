package com.edavalos.mtx.util.hash;

import java.security.NoSuchAlgorithmException;

public abstract class MtxHash {
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
