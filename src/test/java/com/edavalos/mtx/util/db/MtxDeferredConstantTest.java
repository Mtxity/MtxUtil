package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxDeferredConstantTest {
    private final double SAMPLE_DOUBLE = 3.5;

    private MtxDeferredConstant<Double> mtxConst;

    @BeforeEach
    public void setUp() {
        mtxConst = new MtxDeferredConstant<>();
    }

    @Nested
    public class TestSetValue {

        @Nested
        public class TestSetValue_throwException {

            @Test
            public void testSetValue_valueNotSetYet() {
                try {
                    assertTrue(mtxConst.setValue(SAMPLE_DOUBLE));
                } catch (UnsupportedOperationException e) {
                    fail();
                }
            }

            @Test
            public void testSetValue_valueAlreadySet() {
                mtxConst.setValue(SAMPLE_DOUBLE);

                assertThrows(UnsupportedOperationException.class, () -> mtxConst.setValue(SAMPLE_DOUBLE));
                assertThrows(UnsupportedOperationException.class, () -> mtxConst.setValue(9.5));
                assertThrows(UnsupportedOperationException.class, () -> mtxConst.setValue(null));
            }
        }

        @Nested
        public class TestSetValue_dontThrowException {
            @BeforeEach
            public void setUp() {
                mtxConst = new MtxDeferredConstant<>(false);
            }

            @Test
            public void testSetValue_valueNotSetYet() {
                assertTrue(mtxConst.setValue(SAMPLE_DOUBLE));
            }

            @Test
            public void testSetValue_valueAlreadySet() {
                mtxConst.setValue(SAMPLE_DOUBLE);

                try {
                    assertFalse(mtxConst.setValue(SAMPLE_DOUBLE));
                    assertFalse(mtxConst.setValue(9.5));
                    assertFalse(mtxConst.setValue(null));
                } catch (UnsupportedOperationException e) {
                    fail();
                }
            }
        }
    }

    @Nested
    public class TestGetValue {

        @Test
        public void testGetValue_valueNotSetYet() {
            assertNull(mtxConst.getValue());
        }

        @Test
        public void testGetValue_valueAlreadySet() {
            mtxConst.setValue(SAMPLE_DOUBLE);

            assertEquals(SAMPLE_DOUBLE, mtxConst.getValue());
        }
    }

    @Test
    public void testIsValueSet() {
        assertFalse(mtxConst.isValueSet());

        mtxConst.setValue(SAMPLE_DOUBLE);
        assertTrue(mtxConst.isValueSet());
    }

    @Test
    public void testIsValueSet_setNullValue() {
        assertFalse(mtxConst.isValueSet());

        mtxConst.setValue(null);
        assertTrue(mtxConst.isValueSet());
    }

    @Test
    public void testThrowsException() {
        assertTrue(mtxConst.throwsException());

        mtxConst = new MtxDeferredConstant<>(false);
        assertFalse(mtxConst.throwsException());
    }
}
