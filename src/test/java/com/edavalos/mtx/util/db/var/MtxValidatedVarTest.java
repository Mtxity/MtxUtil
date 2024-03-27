package com.edavalos.mtx.util.db.var;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxValidatedVarTest {
    private final String SAMPLE_STR = "SAMPLE_STRING";

    private MtxValidatedVar<String> mtxVVar;

    @Nested
    class TestConstructor {

        @Test
        public void testConstructor_withValue() {
            mtxVVar = new MtxValidatedVar<>(SAMPLE_STR);

            assertEquals(SAMPLE_STR, mtxVVar.getValue());
            assertTrue(mtxVVar.isValid());
        }

        @Test
        public void testConstructor_noValue() {
            mtxVVar = new MtxValidatedVar<>();

            assertNull(mtxVVar.getValue());
            assertTrue(mtxVVar.isValid());
        }

        @Test
        public void testConstructor_validated() {
            mtxVVar = new MtxValidatedVar<>(SAMPLE_STR, false);

            assertFalse(mtxVVar.isValid());
        }
    }

    @Nested
    class TestGetValue {

        @Test
        public void testGetValue_validated() {
            mtxVVar = new MtxValidatedVar<>(SAMPLE_STR, true);

            assertDoesNotThrow(() -> mtxVVar.getValue());
            assertEquals(SAMPLE_STR, mtxVVar.getValue());
        }

        @Test
        public void testGetValue_invalidated() {
            mtxVVar = new MtxValidatedVar<>(SAMPLE_STR, false);

            IllegalStateException ise = assertThrows(IllegalStateException.class, () -> mtxVVar.getValue());
            assertEquals(MtxValidatedVar.ERROR_MSG, ise.getMessage());
        }
    }

    @Test
    public void testSetValue() {
        String newVal = "RANDOM";

        mtxVVar = new MtxValidatedVar<>(SAMPLE_STR, false);
        assertDoesNotThrow(() -> mtxVVar.setValue(newVal));
        mtxVVar.validate();

        assertEquals(newVal, mtxVVar.getValue());
    }

    @Nested
    class TestIsValid {

        @Test
        public void testValidate() {
            mtxVVar = new MtxValidatedVar<>(SAMPLE_STR, false);

            assertFalse(mtxVVar.isValid());
            mtxVVar.validate();
            assertTrue(mtxVVar.isValid());
        }

        @Test
        public void testInvalidate() {
            mtxVVar = new MtxValidatedVar<>(SAMPLE_STR, true);

            assertTrue(mtxVVar.isValid());
            mtxVVar.invalidate();
            assertFalse(mtxVVar.isValid());
        }
    }

    @Nested
    class TestEquals {

        @Test
        public void testEquals_differentTypes() {
            MtxValidatedVar<String> vVar = new MtxValidatedVar<>(SAMPLE_STR);
            MtxLockingVar<String> lVar = new MtxLockingVar<>(SAMPLE_STR);

            assertFalse(vVar.equals(lVar));
        }

        @Test
        public void testEquals_bothInvalidated() {
            MtxValidatedVar<String> vVar1 = new MtxValidatedVar<>(SAMPLE_STR, false);
            MtxValidatedVar<String> vVar2 = new MtxValidatedVar<>(SAMPLE_STR + "_2", false);

            assertTrue(vVar1.equals(vVar2));
        }

        @Test
        public void testEquals_thisNull() {
            MtxValidatedVar<String> vVar1 = new MtxValidatedVar<>(null);
            MtxValidatedVar<String> vVar2 = new MtxValidatedVar<>(SAMPLE_STR);

            assertFalse(vVar1.equals(vVar2));
        }

        @Test
        public void testEquals_otherNull() {
            MtxValidatedVar<String> vVar1 = new MtxValidatedVar<>(SAMPLE_STR);
            MtxValidatedVar<String> vVar2 = new MtxValidatedVar<>(null);

            assertFalse(vVar1.equals(vVar2));
        }

        @Test
        public void testEquals_bothNull() {
            MtxValidatedVar<String> vVar1 = new MtxValidatedVar<>(null);
            MtxValidatedVar<String> vVar2 = new MtxValidatedVar<>(null);

            assertTrue(vVar1.equals(vVar2));
            assertTrue(vVar2.equals(vVar1));
        }

        @Test
        public void testEquals_true() {
            MtxValidatedVar<String> vVar1 = new MtxValidatedVar<>(SAMPLE_STR, true);
            MtxValidatedVar<String> vVar2 = new MtxValidatedVar<>(SAMPLE_STR, true);

            assertTrue(vVar1.equals(vVar2));
            assertTrue(vVar2.equals(vVar1));
        }

        @Test
        public void testEquals_false() {
            MtxValidatedVar<String> vVar1 = new MtxValidatedVar<>(SAMPLE_STR, true);
            MtxValidatedVar<String> vVar2 = new MtxValidatedVar<>(SAMPLE_STR + "_2", true);

            assertFalse(vVar1.equals(vVar2));
            assertFalse(vVar2.equals(vVar1));
        }
    }
}
