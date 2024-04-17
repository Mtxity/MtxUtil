package com.edavalos.mtx.util.db.var;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxOptionalVarTest {
    private final String SAMPLE_STR = "SAMPLE_STRING";

    private MtxOptionalVar<String> mtxOVar;

    @Test
    public void testConstructor() {
        mtxOVar = new MtxOptionalVar<>(SAMPLE_STR);

        assertEquals(SAMPLE_STR, mtxOVar.getValue());
    }

    @Test
    public void testEmpty() {
        mtxOVar = MtxOptionalVar.empty();

        assertTrue(mtxOVar.isEmpty());
        assertThrows(NoSuchElementException.class, () -> mtxOVar.getValue());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(new MtxOptionalVar<Integer>(null).isEmpty());
        assertTrue(MtxOptionalVar.empty().isEmpty());
        assertFalse(new MtxOptionalVar<Integer>(5).isEmpty());
    }
}
