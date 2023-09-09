package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MtxLimitedEditsVarTest {
    private static final int EDITS_ALLOWED = 5;

    private MtxLimitedEditsVar<Integer> mtxLimitedEditsVar;

    @BeforeEach
    public void setUp() {
        mtxLimitedEditsVar = new MtxLimitedEditsVar<>(EDITS_ALLOWED);
    }

    @Test
    public void testSetValue() {
        for (int i = 0; i < 5; i++) {
            assertDoesNotThrow(() -> mtxLimitedEditsVar.setValue(0));
        }

        String errorMsg = assertThrows(
                UnsupportedOperationException.class,
                () -> mtxLimitedEditsVar.setValue(0)
        ).getMessage();

        assertEquals(
                String.format(MtxLimitedEditsVar.ERROR_NO_EDITS_LEFT, EDITS_ALLOWED),
                errorMsg
        );
    }

    @Test
    public void testSetValue_null() {
        String errorMsg = assertThrows(
                NullPointerException.class,
                () -> mtxLimitedEditsVar.setValue(null)
        ).getMessage();

        assertEquals(MtxVersionedVar.ERROR_NULL, errorMsg);
    }

    @Test
    public void testTotalEditsAllowed() {
        assertEquals(EDITS_ALLOWED, mtxLimitedEditsVar.totalEditsAllowed());
    }

    @Test
    public void testEditsLeft() {
        assertEquals(EDITS_ALLOWED, mtxLimitedEditsVar.editsLeft());

        for (int i = 0; i < 5; i++) {
            assertEquals(EDITS_ALLOWED - i, mtxLimitedEditsVar.editsLeft());
            mtxLimitedEditsVar.setValue(i);
        }

        assertEquals(0, mtxLimitedEditsVar.editsLeft());
    }
}
