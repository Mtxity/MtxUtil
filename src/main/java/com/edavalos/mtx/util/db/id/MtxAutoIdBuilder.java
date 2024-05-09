package com.edavalos.mtx.util.db.id;

import java.math.BigInteger;

public interface MtxAutoIdBuilder {
    String getNextId();

    int getIdLength();

    BigInteger getTotalUniqueIds();
}
