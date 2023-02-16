package com.edavalos.mtx.util.hash;

import java.security.NoSuchAlgorithmException;

public final class MtxMD5Hash extends MtxMessageDigestHash {
    MtxMD5Hash(String newSalt) throws NoSuchAlgorithmException {
        super(newSalt, MtxMessageDigestHash.MdHashingAlgorithm.MD5);
    }
}
