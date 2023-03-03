package com.edavalos.mtx.util.hash.encrypt;

import javax.crypto.SecretKey;

/**
 * An object to represent an encryption key needed to encrypt & decrypt strings using MtxEncryptor.
 * Holds the two values needed: a SecretKey and an initialization vector byte array.
 */
public record MtxEncryptionKey(SecretKey key, byte[] initializationVector) {

}
