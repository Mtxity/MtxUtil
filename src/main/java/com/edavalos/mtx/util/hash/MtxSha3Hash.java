package com.edavalos.mtx.util.hash;

import java.security.NoSuchAlgorithmException;

public final class MtxSha3Hash extends MtxMessageDigestHash {
    MtxSha3Hash(String newSalt) throws NoSuchAlgorithmException {
        super(newSalt, MtxHash.MdHashingAlgorithm.SHA3);
    }
}
