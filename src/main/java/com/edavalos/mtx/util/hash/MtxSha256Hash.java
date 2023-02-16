package com.edavalos.mtx.util.hash;

import java.security.NoSuchAlgorithmException;

public final class MtxSha256Hash extends MtxMessageDigestHash {
    MtxSha256Hash(String newSalt) throws NoSuchAlgorithmException {
        super(newSalt, MtxMessageDigestHash.MdHashingAlgorithm.SHA256);
    }
}
