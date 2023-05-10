package com.edavalos.mtx.util.db;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.UUID;

public final class MtxUuidGenerator {
    public enum MtxUuidVersion {
        TIME_BASED(1),
        NAME_BASED_MD5(3),
        NAME_BASED_SHA1(5),
        RANDOMLY_GENERATED(4);

        private final int vNum;

        MtxUuidVersion(int version) {
            this.vNum = version;
        }

        public int getVersion() {
            return this.vNum;
        }
    }

    private static final int STANDARD_UUID_VARIANT = 0x80;
    private static final SecureRandom NUMBER_GENERATOR = new SecureRandom();

    private final MtxUuidVersion version;

    public MtxUuidGenerator(MtxUuidVersion uuidVersion) {
        this.version = uuidVersion;
    }

    public MtxUuidGenerator() {
        this(MtxUuidVersion.RANDOMLY_GENERATED);
    }

    public MtxUuidVersion getVersion() {
        return this.version;
    }

    public UUID getNextUuid() {
        byte[] newUuid = switch (this.version) {
            case TIME_BASED -> null;
            case NAME_BASED_MD5 -> null;
            case NAME_BASED_SHA1 -> null;
            case RANDOMLY_GENERATED -> getRandomByteArray();
        };

        newUuid = setVariantAndVersion(newUuid, this.version);
        assert isUuidValid(newUuid) : "Error occurred while generating next UUID";

        return new UUID(getMostSigBits(newUuid), getLeastSigBits(newUuid));
    }

    private static byte[] getRandomByteArray() {
        byte[] randomBytes = new byte[16];
        NUMBER_GENERATOR.nextBytes(randomBytes);

        return randomBytes;
    }

    protected static byte[] setVariantAndVersion(byte[] uuid, MtxUuidVersion version) {
        uuid[6]  &= 0x0f;                  // clear version
        uuid[6]  |= version.getVersion();  // set version
        uuid[8]  &= 0x3f;                  // clear variant
        uuid[8]  |= STANDARD_UUID_VARIANT; // set to standard variant

        return uuid;
    }

    private static long getMostSigBits(byte[] uuid) {
        assert uuid.length == 16 : "uuid must be 16 bytes in length";
        long msb = 0;
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (uuid[i] & 0xff);
        }
        return msb;
    }

    private static long getLeastSigBits(byte[] uuid) {
        assert uuid.length == 16 : "uuid must be 16 bytes in length";
        long lsb = 0;
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (uuid[i] & 0xff);
        }
        return lsb;
    }

    protected static boolean isUuidValid(byte[] uuid) {
        if (uuid.length != 16) {
            return false;
        }

        int version = (int)((getMostSigBits(uuid) >> 12) & 0x0f);
        if (version < 1 || version > 5) {
            return false;
        }

        long leastSigBits = getLeastSigBits(uuid);
        int variant = (int) ((leastSigBits >>> (64 - (leastSigBits >>> 62))) & (leastSigBits >> 63));
        if (variant != 0 && variant != 2 && variant != 6 && variant != 7) {
            return false;
        }

        return true;
    }
}
