package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        @Test
        public void testSetValue_valueNotSetYet() {
            try {
                mtxConst.setValue(SAMPLE_DOUBLE);
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
}
