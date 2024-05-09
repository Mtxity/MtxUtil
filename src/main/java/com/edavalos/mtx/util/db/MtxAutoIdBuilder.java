package com.edavalos.mtx.util.db;

import java.math.BigInteger;

public interface MtxAutoIdBuilder {
    String getNextId();

    int getIdLength();

    BigInteger getTotalUniqueIds();
}
