package com.edavalos.mtx.util.db.var;

import org.junit.jupiter.api.Nested;
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

    @Test
    public void testOf() {
        mtxOVar = MtxOptionalVar.of(SAMPLE_STR);

        assertEquals(SAMPLE_STR, mtxOVar.getValue());
    }

    @Test
    public void testGetValue_hasValue() {
        mtxOVar = new MtxOptionalVar<>(SAMPLE_STR);

        assertFalse(mtxOVar.isEmpty());
        assertEquals(SAMPLE_STR, mtxOVar.getValue());
    }

    @Test
    public void testGetValue_empty() {
        mtxOVar = MtxOptionalVar.empty();

        assertTrue(mtxOVar.isEmpty());
        String errorMsg = assertThrows(
                NoSuchElementException.class,
                () -> mtxOVar.getValue()
        ).getMessage();
        assertEquals("No value present", errorMsg);
    }

    @Nested
    class TestEquals {

        @Test
        public void testEquals_differentTypes() {
            MtxOptionalVar<String> oVar = new MtxOptionalVar<>(SAMPLE_STR);
            MtxValidatedVar<String> vVar = new MtxValidatedVar<>(SAMPLE_STR);

            assertFalse(oVar.equals(vVar));
        }

        @Test
        public void testEquals_bothEmpty() {
            MtxOptionalVar<String> oVar1 = MtxOptionalVar.empty();
            MtxOptionalVar<Integer> oVar2 = MtxOptionalVar.empty();

            assertTrue(oVar1.equals(oVar2));
        }

        @Test
        public void testEquals_otherEmpty() {
            MtxOptionalVar<String> oVar1 = MtxOptionalVar.of(SAMPLE_STR);
            MtxOptionalVar<Integer> oVar2 = MtxOptionalVar.empty();

            assertFalse(oVar1.equals(oVar2));
        }

        @Test
        public void testEquals_thisEmpty() {
            MtxOptionalVar<String> oVar1 = MtxOptionalVar.empty();
            MtxOptionalVar<Integer> oVar2 = MtxOptionalVar.of(10);

            assertFalse(oVar1.equals(oVar2));
        }

        @Test
        public void testEquals_differentInnerTypes() {
            MtxOptionalVar<String> oVar1 = MtxOptionalVar.of(SAMPLE_STR);
            MtxOptionalVar<Integer> oVar2 = MtxOptionalVar.of(10);

            assertFalse(oVar1.equals(oVar2));
        }

        @Test
        public void testEquals_differentInnerValues() {
            MtxOptionalVar<String> oVar1 = MtxOptionalVar.of(SAMPLE_STR);
            MtxOptionalVar<String> oVar2 = MtxOptionalVar.of("Other String");

            assertFalse(oVar1.equals(oVar2));
        }

        @Test
        public void testEquals_sameInnerValues() {
            MtxOptionalVar<String> oVar1 = MtxOptionalVar.of(SAMPLE_STR);
            MtxOptionalVar<String> oVar2 = MtxOptionalVar.of(SAMPLE_STR);

            assertTrue(oVar1.equals(oVar2));
        }
    }
}
