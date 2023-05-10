package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxUnsignedIntegerTest {
    private static final int VALID_UINT = 5;
    private static final String VALID_UINT_STR = "5";

    private MtxUnsignedInteger mtxUInt;

    @Nested
    class TestConstructor {
        private static final int INVALID_UINT = -5;
        private static final String INVALID_UINT_STR_IS_NEGATIVE = "-5";
        private static final String INVALID_UINT_STR_NOT_A_UINT = "word";

        @Test
        public void testConstructor_validUint() {
            mtxUInt = new MtxUnsignedInteger(VALID_UINT);
            assertEquals(VALID_UINT, mtxUInt.intValue());
        }

        @Test
        public void testConstructor_validStr() {
            mtxUInt = new MtxUnsignedInteger(VALID_UINT_STR);
            assertEquals(VALID_UINT, mtxUInt.intValue());
        }

        @Test
        public void testConstructor_invalidUint() {
            assertThrows(NumberFormatException.class, () -> new MtxUnsignedInteger(INVALID_UINT));
        }

        @Test
        public void testConstructor_invalidStr_isNegativeUint() {
            assertThrows(NumberFormatException.class, () -> new MtxUnsignedInteger(INVALID_UINT_STR_IS_NEGATIVE));
        }

        @Test
        public void testConstructor_invalidStr_notAUint() {
            assertThrows(NumberFormatException.class, () -> new MtxUnsignedInteger(INVALID_UINT_STR_NOT_A_UINT));
        }
    }

    @Test
    public void testByteValue() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        byte expected = 5;

        assertEquals(expected, mtxUInt.byteValue());
    }

    @Test
    public void testShortValue() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        short expected = 5;

        assertEquals(expected, mtxUInt.shortValue());
    }

    @Test
    public void testIntValue() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        int expected = 5;

        assertEquals(expected, mtxUInt.intValue());
    }

    @Test
    public void testLongValue() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        long expected = 5;

        assertEquals(expected, mtxUInt.longValue());
    }

    @Test
    public void testFloatValue() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        float expected = 5.0f;

        assertEquals(expected, mtxUInt.floatValue());
    }

    @Test
    public void testDoubleValue() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        double expected = 5.0;

        assertEquals(expected, mtxUInt.doubleValue());
    }

    @Test
    public void testToString() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);

        assertEquals(VALID_UINT_STR, mtxUInt.toString());
    }

    @Test
    public void testEquals_true() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        MtxUnsignedInteger otherUInt = new MtxUnsignedInteger(VALID_UINT);

        assertTrue(mtxUInt.equals(otherUInt));
    }

    @Test
    public void testEquals_false() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        MtxUnsignedInteger otherUInt = new MtxUnsignedInteger("6");

        assertFalse(mtxUInt.equals(otherUInt));
    }

    @Test
    public void testEquals_differentTypes() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        Double sampleDouble = new Double(VALID_UINT);

        assertFalse(mtxUInt.equals(sampleDouble));
    }

    @Test
    public void testHashCode() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        int expectedHashCode = 5;

        assertEquals(expectedHashCode, mtxUInt.hashCode());
    }

    @Test
    public void testCompareTo() {
        mtxUInt = new MtxUnsignedInteger(VALID_UINT);
        MtxUnsignedInteger otherUInt = new MtxUnsignedInteger(VALID_UINT);
        int expectedResult = 0;

        assertEquals(expectedResult, mtxUInt.compareTo(otherUInt));
    }

    @Test
    public void testCompare() {
        assertEquals(0, MtxUnsignedInteger.compare(VALID_UINT, VALID_UINT));
    }

    @Test
    public void testParseUnsignedInt() {
        try {
            mtxUInt = MtxUnsignedInteger.parseUnsignedInt(VALID_UINT_STR);
        } catch (NumberFormatException e) {
            fail();
        }
    }
}
