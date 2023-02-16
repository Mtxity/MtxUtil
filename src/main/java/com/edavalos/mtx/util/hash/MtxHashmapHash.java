package com.edavalos.mtx.util.hash;

import java.security.NoSuchAlgorithmException;

public final class MtxHashmapHash extends MtxHash {
    private int degreeToHash;

    MtxHashmapHash(String newSalt) throws NoSuchAlgorithmException {
        super(newSalt, MtxHash.MdHashingAlgorithm.OTHER);
    }

    @Override
    protected void initialize() {
        this.degreeToHash = super.existingAlg.getDegree();
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
        int h;
        int hash = (stringToHash == null) ? 0 : (h = stringToHash.hashCode()) ^ (h >>> 16);
        return String.valueOf(hash);
    }
}
