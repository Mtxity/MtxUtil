package com.edavalos.mtx.util.db.var;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxOptionalVarTest {
    private final String SAMPLE_STR = "SAMPLE_STRING";

    private MtxOptionalVar<String> mtxOVar;

    @Test
    public void testConstructor() {
        mtxOVar = new MtxOptionalVar<>(SAMPLE_STR);

        assertEquals(SAMPLE_STR, mtxOVar.getValue());
    }
}
